import Controller.ClienteController;
import Controller.ConexaoBD;
import Controller.ContaController;
import Controller.EmailController;
import Controller.EnderecoController;
import Controller.OperacoesController;
import Controller.TelefoneController;
import DAO.ClienteDAO;
import DAO.ContaBancariaDAO;
import Modelos.Classes_casodeuso.Agencia;
import Modelos.Classes_casodeuso.Banco;
import Modelos.Classes_casodeuso.ContaBancaria;
import Modelos.Classes_casodeuso.Investimento;
import Modelos.Classes_casodeuso.Transacao;
import Modelos.Classes_genericas.Bairro;
import Modelos.Classes_genericas.Cidade;
import Modelos.Classes_genericas.Cliente;
import Modelos.Classes_genericas.Endereco;
import Modelos.Classes_genericas.Logradouro;
import Modelos.Classes_genericas.Tipologra;
import Modelos.Classes_genericas.UF;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static EnderecoController enderecoController = new EnderecoController();
    private static ClienteController clienteController = new ClienteController();
    private static TelefoneController telefoneController = new TelefoneController();
    private static EmailController emailController = new EmailController();
    private static ContaController contaController = new ContaController();
    
    private static OperacoesController operacoesController = new OperacoesController();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--------------------------------------------");
            System.out.println("\t    CONTA BANCARIA");
            System.out.println("--------------------------------------------");
            System.out.println("1- Cadastrar Cliente");
            System.out.println("2- Criar conta");
            System.out.println("3- Consultar cliente");
            System.out.println("4- Consultar conta");
            System.out.println("5- Realizar investimento");
            System.out.println("6- Consultar investimento");
            System.out.println("7- Realizar transacao");
            System.out.println("8- Consultar transacao");
            System.out.println("0- Sair");
            System.out.println("---------------------------------------------");
            System.out.print("Escolha uma opçao: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            if (opcao == 0) {
                System.out.println("Encerrando o sistema...");
                break;
            }

            switch (opcao) {
                case 1: cadastrarCliente(scanner); break;
                case 2: criarConta(scanner); break;
                case 3: consultarCliente(scanner); break;
                case 4: consultarConta(scanner); break;
                case 5: realizarInvestimento(scanner); break;
                case 6: consultarInvestimentos(scanner); break;
                case 7: realizarTransacao(scanner); break;
                case 8: consultarTransacoes(scanner); break;
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }



    private static void cadastrarCliente(Scanner scanner) {
        System.out.println("\n[ CADASTRO DE CLIENTE ]");
        
        System.out.print("UF (Sigla): ");
        UF uf = new UF(0, scanner.nextLine());
        System.out.print("Cidade: ");
        Cidade cidade = new Cidade(0); cidade.setCidade(scanner.nextLine());
        System.out.print("Bairro: ");
        Bairro bairro = new Bairro(); bairro.setBairro(scanner.nextLine());
        System.out.print("Tipo do Logradouro (Rua, Avenida...): ");
        Tipologra tipoLogra = new Tipologra(); tipoLogra.setTipologra(scanner.nextLine());
        System.out.print("Nome do Logradouro: ");
        Logradouro logradouro = new Logradouro(); logradouro.setLogradouro(scanner.nextLine());
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        
        Endereco end = enderecoController.cadastrarEnderecoCompleto(uf, cidade, bairro, tipoLogra, logradouro, cep);
        if (end == null) {
            System.out.println("Erro ao cadastrar endereço, cancelando cadastro do cliente.");
            return;
        }

        Cliente novoCliente = new Cliente(0);
        novoCliente.setEndereco(end);
        System.out.print("Nome do Cliente: ");
        novoCliente.setNomecliente(scanner.nextLine());
        System.out.print("CPF: ");
        novoCliente.setCPF(scanner.nextLine());
        System.out.print("Número da Casa: ");
        novoCliente.setNumero(scanner.nextLine());
        System.out.print("Complemento: ");
        novoCliente.setComplemento(scanner.nextLine());

        int idCriado = clienteController.inserirNovoCliente(novoCliente); 
        
        if (idCriado == 0) {
            System.out.println("Falha ao cadastrar o cliente. Cancelando inserção de contatos...");
            return; // Para tudo e volta para o menu
        }
        

        String respFone;
        do {
            System.out.print("Deseja cadastrar um Telefone? (S/N): ");
            respFone = scanner.nextLine();
            if (respFone.equalsIgnoreCase("S")) {
                System.out.print("DDI (ex: +55): "); String dddi = scanner.nextLine();
                System.out.print("DDD (ex: 45): "); String ddd = scanner.nextLine();
                System.out.print("Número: "); String num = scanner.nextLine();
                telefoneController.cadastrarTelefoneCliente(dddi, ddd, num, idCriado);
            }
        } while (respFone.equalsIgnoreCase("S"));

        String respEmail;
        do {
            System.out.print("Deseja cadastrar um E-mail? (S/N): ");
            respEmail = scanner.nextLine();
            if (respEmail.equalsIgnoreCase("S")) {
                System.out.print("E-mail: "); String emailTxt = scanner.nextLine();
                emailController.cadastrarEmailCliente(emailTxt, idCriado);
            }
        } while (respEmail.equalsIgnoreCase("S"));
    }

    private static void consultarCliente(Scanner scanner) {
        System.out.println("\n[ CONSULTAR CLIENTE ]");
        System.out.print("Digite o CPF do cliente: ");
        String cpfConsulta = scanner.nextLine();
        clienteController.consultarEExibirInformacoes(cpfConsulta);
    }


    private static void criarConta(Scanner scanner) {
        System.out.println("\n[ CRIAR CONTA BANCÁRIA ]");
        System.out.print("Digite o CPF do titular: ");
        String cpfConta = scanner.nextLine();
        int idTitular = buscarIdDoClientePorCpf(cpfConta);
        
        if(idTitular == 0) {
            System.out.println("Cliente não encontrado com esse CPF.");
            return;
        }

        System.out.print("Número da Nova Conta (ID da conta, ex: 1001): ");
        int idConta = scanner.nextInt(); scanner.nextLine();
        
        System.out.print("Código do Banco (ex: 104): ");
        Banco banco = new Banco(scanner.nextInt()); scanner.nextLine();
        System.out.print("Nome do Banco (ex: Caixa): ");
        banco.setNomeBanco(scanner.nextLine());
        
        System.out.print("Número da Agência (ex: 1122): ");
        Agencia agencia = new Agencia(scanner.nextInt()); scanner.nextLine();
        System.out.print("Tipo da Agência (Número ex: 1 para Física): ");
        agencia.setTipoAgencia(scanner.nextLine());
        
        System.out.print("ID do Endereço da Agência (ex: 1): ");
        int idEnderecoAgencia = scanner.nextInt(); scanner.nextLine();
        
        agencia.setEndereco(new Endereco(null, idEnderecoAgencia));
        
        // Passamos o idConta para o Controller!
        contaController.abrirNovaContaCompleta(idConta, banco, agencia, new Cliente(idTitular));
    }

    private static void consultarConta(Scanner scanner) {
        System.out.println("\n[ CONSULTAR CONTA ]");
        ContaBancaria contaInfo = validarAcessoSeguro(scanner);
        if(contaInfo != null) {
            System.out.println("\n--- DADOS DA CONTA ---");
            System.out.println("ID Conta: " + contaInfo.getIdConta());
            System.out.println("ID Cliente Vinculado: " + contaInfo.getCliente().getIdCliente());
            System.out.println("Código do Banco: " + contaInfo.getAgencia().getBanco().getCodBanco());
            System.out.println("Número da Agência: " + contaInfo.getAgencia().getIdAgencia());
            System.out.println("SALDO ATUAL: R$ " + String.format("%.2f", contaInfo.getSaldo()));
        }
    }


    private static void realizarTransacao(Scanner scanner) {
        System.out.println("\n[ REALIZAR TRANSAÇÃO ]");
        ContaBancaria conta = validarAcessoSeguro(scanner);
        
        if (conta != null) {
            System.out.println("\nSaldo Disponível: R$ " + String.format("%.2f", conta.getSaldo()));
            
            System.out.println("Qual o tipo da movimentação?");
            System.out.println("1 - ENTRADA (Soma no Saldo)");
            System.out.println("2 - SAÍDA (Diminui do Saldo)");
            System.out.print("Opção: ");
            int tipoMovimentacao = scanner.nextInt(); scanner.nextLine();
            
            System.out.print("Descrição da transação (ex: Recebimento PIX): ");
            String tipoTrans = scanner.nextLine();
            
            System.out.print("Valor (ex: 100,50): ");
            double valorTrans = scanner.nextDouble(); scanner.nextLine();
            
            System.out.print("Data (DD/MM/AAAA): ");
            String dataTrans = scanner.nextLine();
            
            // Repassa para o Controller
            operacoesController.realizarTransacao(tipoTrans, tipoMovimentacao, valorTrans, dataTrans, conta);
        }
    }

    private static void consultarTransacoes(Scanner scanner) {
        System.out.println("\n[ CONSULTAR TRANSAÇÕES ]");
        ContaBancaria conta = validarAcessoSeguro(scanner);
        if (conta != null) {
            List<Transacao> trans = operacoesController.consultarTransacoes(conta.getIdConta());
            if(trans.isEmpty()) {
                System.out.println("Nenhuma transação encontrada.");
            } else {
                for(Transacao t : trans) {
                    System.out.println("> [" + t.getDataTransacao() + "] " + t.getTipoTransacao().getTipotransacao() + " - Valor: R$ " + t.getValorTransacao());
                }
            }
        }
    }

    private static void realizarInvestimento(Scanner scanner) {
        System.out.println("\n[ REALIZAR INVESTIMENTO ]");
        ContaBancaria conta = validarAcessoSeguro(scanner);
        
        if (conta != null) {
            System.out.println("\nSaldo Disponível: R$ " + String.format("%.2f", conta.getSaldo()));
            System.out.print("Tipo do Investimento (ex: CBD): ");
            String tipoInv = scanner.nextLine();
            
            System.out.print("Valor a investir: ");
            double valorInv = scanner.nextDouble(); scanner.nextLine();
            
            System.out.print("Data (DD/MM/AAAA): ");
            String dataInv = scanner.nextLine();
            
            operacoesController.realizarInvestimento(tipoInv, valorInv, dataInv, conta);
        }
    }

    private static void consultarInvestimentos(Scanner scanner) {
        System.out.println("\n[ CONSULTAR INVESTIMENTOS ]");
        ContaBancaria conta = validarAcessoSeguro(scanner);
        if (conta != null) {
            List<Investimento> invs = operacoesController.consultarInvestimentos(conta.getIdConta());
            if(invs.isEmpty()) {
                System.out.println("Nenhum investimento encontrado.");
            } else {
                for(Investimento i : invs) {
                    System.out.println("> [" + i.getDataInvestimento() + "] " + i.getTipoInvestimento().getTipoinvestimento() + " - Valor: R$ " + i.getValorInvestimento());
                }
            }
        }
    }


    private static ContaBancaria validarAcessoSeguro(Scanner scanner) {
        System.out.print("ID da Conta: ");
        int idConta = scanner.nextInt(); scanner.nextLine();
        System.out.print("CPF do Titular: ");
        String cpf = scanner.nextLine();

        try (Connection conn = ConexaoBD.conectar()) {
            ContaBancariaDAO dao = new ContaBancariaDAO(conn);
            ContaBancaria conta = dao.buscarContaValidandoCPF(idConta, cpf);
            if(conta == null) {
                System.out.println("ACESSO NEGADO: Conta não encontrada ou o CPF informado não pertence ao titular.");
            }
            return conta;
        } catch (Exception e) {
            System.err.println("Erro na validação: " + e.getMessage());
            return null;
        }
    }

    private static int buscarIdDoClientePorCpf(String cpf) {
        try (Connection conn = ConexaoBD.conectar()) {
            ClienteDAO dao = new ClienteDAO(conn);
            Cliente c = dao.buscarClientePorCPF(cpf);
            return c != null ? c.getIdCliente() : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n Bem-vindo a lista 2 (parte 2) do TP3. Os exercícios estão colocados em sequência. \n Digite qualquer tecla para continuar ...");
        scanner.next();

        System.out.println("As ilhas Faroe te contrataram para implementar o sistema de caixas bancários(ATM) da ilha.\n" +
                "A moeda local se chama BIT e eles possuem as seguintes cédulas: B$50,B$10,B$5 e B$1.  \n" +
                "\n" +
                "Exitem dois tipos de clientes no Banco: Clientes Standart e Clientes VIP.\n" +
                "Cada cliente deverá ter os seguintes atributos: nome(String),email(List<String>), segurosocial(String), Logradouro, Numero, Bairro, Cidade, zip-code e saldo(BigDecimal).\n" +
                "\n" +
                "\n" +
                "Utilizando composição E/OU herança (como achar melhor) mapeie esse cenário.\n");

        System.out.println("Implemente o método para depositar Bits. Esse método deve validar valores negativos e zero.\n");

        ClienteStandard clienteStandard = new ClienteStandard(
                new BigDecimal("100"),
                "João Silva",
                "123-45-6789",
                "Rua A",
                "123",
                "Bairro A",
                "Cidade A",
                12345,
                new ArrayList<String>() {{
                    add("joao@email.com");
                }}
        );

        ClienteVip clienteVip = new ClienteVip(
                new BigDecimal("200"),
                scanner,
                "Maria Santos",
                "987-65-4321",
                "Rua B",
                "456",
                "Bairro B",
                "Cidade B",
                54321,
                new ArrayList<String>() {{
                    add("maria@email.com");
                }}
        );

        System.out.println("Cliente Padrão:");
        clienteStandard.Sacar(new BigDecimal("70.50"));
        clienteStandard.Sacar(new BigDecimal("50"));
        clienteStandard.Sacar(new BigDecimal("85"));
        System.out.println("SALDO: " + clienteStandard.getSaldo());

        System.out.println("\nCliente VIP:");
        clienteVip.Sacar(new BigDecimal("30.25"));
        clienteVip.Sacar(new BigDecimal("10"));
        clienteVip.Sacar(new BigDecimal("48"));
        System.out.println("SALDO: " + clienteVip.getSaldo());
    }

    public static class Cliente
    {
        private String nome, seguroSocial, logadouro, numero, bairro, cidade;
        private List<String> email;
        private int zipCode;
        BigDecimal saldo;

        public Cliente(BigDecimal saldoinicial, String nome, String seguroSocial, String logadouro, String numero, String bairro, String cidade, int zipCode, List<String> email) {
            this.saldo = saldoinicial;
            this.nome = nome;
            this.seguroSocial = seguroSocial;
            this.logadouro = logadouro;
            this.numero = numero;
            this.bairro = bairro;
            this.cidade = cidade;
            this.zipCode = zipCode;
            this.email = email;
        }

        public void Depositar(BigDecimal valorDeposito){
            if (valorDeposito.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Você não pode depositar nada ou um valor negativo.");
            } else {
                this.saldo = this.saldo.add(valorDeposito);
                System.out.println("Depósito de " + valorDeposito + "Bits realizado com sucesso!");
            }
        }
        public void Sacar(BigDecimal valorSaque) {
            if(valorSaque.compareTo(BigDecimal.ZERO) <= 0){
                System.out.println("Você não pode sacar um valor negativo ou 0.");
            } else if(valorSaque.compareTo(getSaldo()) > 0) {
                System.out.println("Saldo insuficiente.");
            } else {
                int valorSaqueInt = valorSaque.setScale(0, RoundingMode.HALF_UP).intValue();

                int notas50 = valorSaqueInt / 50;
                int notas10 = (valorSaqueInt % 50) / 10;
                int notas5 = (valorSaqueInt % 10) / 5;
                int notas1 = valorSaqueInt % 5;
                this.saldo = this.saldo.subtract(valorSaque);

                System.out.println(notas50 + " nota de B$50, " + notas10 + " notas de B$10, " + notas5 + " notas de B$5 e " + notas1 +" notas de B$1.");
            }

        }
        public BigDecimal getSaldo() {
            return saldo;
        }
    }

    public static class ClienteStandard extends Cliente
    {
        public ClienteStandard(BigDecimal saldoInicial, String nome, String seguroSocial, String logadouro, String numero, String bairro, String cidade, int zipCode, List<String> email) {
            super(saldoInicial, nome, seguroSocial, logadouro, numero, bairro, cidade, zipCode, email);
        }

        public void Sacar(BigDecimal valorSaque){
            super.Sacar(valorSaque);
        }

    }

    public static class ClienteVip extends Cliente
    {
        Scanner scanner = new Scanner(System.in);
        public ClienteVip(BigDecimal saldoInicial, Scanner scanner, String nome, String seguroSocial, String logadouro, String numero, String bairro, String cidade, int zipCode, List<String> email) {
            super(saldoInicial, nome, seguroSocial, logadouro, numero, bairro, cidade, zipCode, email);
        }

        public void Sacar(BigDecimal valorSaque) {
            if(valorSaque.compareTo(BigDecimal.ZERO) <= 0){
                System.out.println("Você não pode sacar um valor negativo ou 0.");
            } else if(valorSaque.compareTo(getSaldo()) > 0) {
                System.out.println("Saldo insuficiente.");
            } else {
                int valorSaqueInt = valorSaque.setScale(0, RoundingMode.HALF_UP).intValue();
                if(valorSaqueInt < 50){
                    System.out.println("\n Você deseja continuar com o saque de menor numero de notas ou só com notas de B$5 e B$1?");

                    System.out.println("Primeira opção digite 1 / Segunda opção digite 2");
                    String opcao = scanner.nextLine();

                    if(opcao.equals("1")){
                        super.Sacar(valorSaque);
                    } else if ((opcao.equals("2"))) {
                        int notas5 = valorSaqueInt / 5;
                        int notas1 = valorSaqueInt % 5;

                        this.saldo = this.saldo.subtract(valorSaque);
                        System.out.println("0 nota de B$50, 0 notas de B$10, " + notas5 + " notas de B$5 e " + notas1 +" notas de B$1.");


                        } else {
                            System.out.println("Erro, essa opção não é válida!");
                        }

                } else {
                    super.Sacar(valorSaque);
                }
            }
        }
    }
}
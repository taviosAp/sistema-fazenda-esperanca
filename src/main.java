import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<String> funcionarios = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n1 - Cadastrar Funcionário");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {
                cadastrarFuncionario();
            }

        } while (opcao != 0);
    }

    static void cadastrarFuncionario() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        funcionarios.add(nome);

        System.out.println("Funcionário cadastrado!");
        // ola
    }
}
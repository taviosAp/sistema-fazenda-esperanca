import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Funcionario> funcionarios = new ArrayList<>();
    static ArrayList<Talhao> talhoes = new ArrayList<>();
    static ArrayList<Trator> tratores = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n=== SISTEMA FAZENDA ===");
            System.out.println("1 - Cadastrar Funcionário");
            System.out.println("2 - Cadastrar Talhão");
            System.out.println("3 - Cadastrar Trator");
            System.out.println("4 - Listar Funcionários");
            System.out.println("5 - Listar Talhões");
            System.out.println("6 - Listar Tratores");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarFuncionario();
                case 2 -> cadastrarTalhao();
                case 3 -> cadastrarTrator();
                case 4 -> listarFuncionarios();
                case 5 -> listarTalhoes();
                case 6 -> listarTratores();
            }

        } while (opcao != 0);
    }
}
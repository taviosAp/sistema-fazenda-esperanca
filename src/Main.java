import java.util.Scanner;

public class Main {

    // Arrays para guardar os dados na memória
    static Funcionario[] equipe = new Funcionario[100];
    static int totalFuncionarios = 0;

    static Talhao[] talhoes = new Talhao[50];
    static int totalTalhoes = 0;

    static Trator[] frota = new Trator[30];
    static int totalTratores = 0;

    static Registro[] registros = new Registro[200];
    static int totalRegistros = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menuPrincipal();
    }

    static void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n===== SISTEMA FAZENDA ESPERANCA =====");
            System.out.println("1. Cadastros");
            System.out.println("2. Registrar Colheita");
            System.out.println("3. Relatorios");
            System.out.println("4. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1: menuCadastros(); break;
                case 2: registrarColheita(); break;
                case 3: System.out.println("(Encontro 3)"); break;
                case 4: System.out.println("Encerrando..."); break;
                default: System.out.println("Opcao invalida!");
            }
        } while (opcao != 4);
    }

    static void menuCadastros() {
        int opcao;
        do {
            System.out.println("\n===== CADASTROS =====");
            System.out.println("1. Cadastrar Funcionario");
            System.out.println("2. Cadastrar Talhao");
            System.out.println("3. Cadastrar Trator");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1: cadastrarFuncionario(); break;
                case 2: cadastrarTalhao(); break;
                case 3: cadastrarTrator(); break;
                case 4: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida!");
            }
        } while (opcao != 4);
    }

    static void cadastrarFuncionario() {
        if (totalFuncionarios >= equipe.length) {
            System.out.println("Limite atingido!");
            return;
        }
        Funcionario f = new Funcionario();
        System.out.print("Matricula: ");
        f.matricula = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome: ");
        f.nome = scanner.nextLine();
        System.out.print("Tipo (Diarista/Fixo): ");
        f.tipoContrato = scanner.nextLine();
        equipe[totalFuncionarios] = f;
        totalFuncionarios++;
        System.out.println("Funcionario cadastrado!");
    }

    static void cadastrarTalhao() {
        if (totalTalhoes >= talhoes.length) {
            System.out.println("Limite atingido!");
            return;
        }
        Talhao t = new Talhao();
        System.out.print("Codigo: ");
        t.codigo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome: ");
        t.nome = scanner.nextLine();
        System.out.print("Variedade: ");
        t.variedade = scanner.nextLine();
        System.out.print("Estimativa de producao (litros): ");
        t.estimativaProducao = scanner.nextDouble();
        talhoes[totalTalhoes] = t;
        totalTalhoes++;
        System.out.println("Talhao cadastrado!");
    }

    static void cadastrarTrator() {
        if (totalTratores >= frota.length) {
            System.out.println("Limite atingido!");
            return;
        }
        Trator tr = new Trator();
        scanner.nextLine();
        System.out.print("Placa: ");
        tr.placa = scanner.nextLine();
        System.out.print("Capacidade maxima (litros): ");
        tr.capacidadeMaxima = scanner.nextDouble();
        frota[totalTratores] = tr;
        totalTratores++;
        System.out.println("Trator cadastrado!");
    }

    static void registrarColheita() {
        if (totalRegistros >= registros.length) {
            System.out.println("Limite de registros atingido!");
            return;
        }

        Registro r = new Registro();

        System.out.print("Data: ");
        scanner.nextLine();
        r.data = scanner.nextLine();

    // 🔍 FUNCIONÁRIO
        System.out.print("Matricula do funcionario: ");
        int mat = scanner.nextInt();

        boolean encontrouFuncionario = false;
        for (int i = 0; i < totalFuncionarios; i++) {
            if (equipe[i].matricula == mat) {
                encontrouFuncionario = true;
                break;
            }
        }

        if (!encontrouFuncionario) {
            System.out.println("Funcionario nao encontrado!");
            return;
        }

        r.matriculaFuncionario = mat;

    // 🔍 TALHÃO
        System.out.print("Codigo do talhao: ");
        int cod = scanner.nextInt();

        boolean encontrouTalhao = false;
        for (int i = 0; i < totalTalhoes; i++) {
            if (talhoes[i].codigo == cod) {
                encontrouTalhao = true;
                break;
            }
        }

        if (!encontrouTalhao) {
            System.out.println("Talhao nao encontrado!");
            return;
        }

        r.codigoTalhao = cod;

    // 🔍 TRATOR
        scanner.nextLine();
        System.out.print("Placa do trator: ");
        String placa = scanner.nextLine();

        Trator tratorEncontrado = null;
        for (int i = 0; i < totalTratores; i++) {
            if (frota[i].placa.equals(placa)) {
                tratorEncontrado = frota[i];
                break;
            }
        }

        if (tratorEncontrado == null) {
            System.out.println("Trator nao encontrado!");
            return;
        }

        r.placaTrator = placa;

    // 📦 LITROS
        System.out.print("Quantidade de litros: ");
        double litros = scanner.nextDouble();

        if (litros > tratorEncontrado.capacidadeMaxima) {
            System.out.println("Capacidade do trator excedida!");
            return;
        }

        r.litros = litros;

    // 📍 DESTINO
        scanner.nextLine();
        System.out.print("Destino (Terreiro/Secador): ");
        r.destino = scanner.nextLine();

    // ✅ SALVAR
        registros[totalRegistros] = r;
        totalRegistros++;

        System.out.println("Registro realizado com sucesso!");
    }
}
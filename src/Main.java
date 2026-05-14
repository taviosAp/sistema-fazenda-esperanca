import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

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
        carregarDados();
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
                case 4:
                    salvarDados();
                    System.out.println("Encerrando...");
                    break;
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

        // VERIFICA MATRICULA DUPLICADA
        for (int i = 0; i < totalFuncionarios; i++) {
            if (equipe[i].matricula == f.matricula) {
                System.out.println("ERRO: Ja existe um funcionario com essa matricula!");
                return;
            }
        }

        System.out.print("Nome: ");
        f.nome = scanner.nextLine();
        System.out.print("Tipo (Diarista/Fixo): ");
        f.tipoContrato = scanner.nextLine();
        while (!f.tipoContrato.equals("Diarista") && !f.tipoContrato.equals("Fixo")) {
            System.out.print("Invalido! Digite Diarista ou Fixo: ");
            f.tipoContrato = scanner.nextLine();
        }
        equipe[totalFuncionarios] = f;
        totalFuncionarios++;
        salvarDados();
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

        // VERIFICA CODIGO DUPLICADO
        for (int i = 0; i < totalTalhoes; i++) {
            if (talhoes[i].codigo == t.codigo) {
                System.out.println("ERRO: Ja existe um talhao com esse codigo!");
                return;
            }
        }

        System.out.print("Nome: ");
        t.nome = scanner.nextLine();
        System.out.print("Variedade: ");
        t.variedade = scanner.nextLine();
        System.out.print("Estimativa de producao (litros): ");
        t.estimativaProducao = scanner.nextDouble();
        talhoes[totalTalhoes] = t;
        totalTalhoes++;
        salvarDados();
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

        // VERIFICA PLACA DUPLICADA
        for (int i = 0; i < totalTratores; i++) {
            if (frota[i].placa.equals(tr.placa)) {
                System.out.println("ERRO: Ja existe um trator com essa placa!");
                return;
            }
        }

        System.out.print("Capacidade maxima (litros): ");
        tr.capacidadeMaxima = scanner.nextDouble();
        frota[totalTratores] = tr;
        totalTratores++;
        salvarDados();
        System.out.println("Trator cadastrado!");
    }

    static void registrarColheita() {
        if (totalRegistros >= registros.length) {
            System.out.println("Limite de registros atingido!");
            return;
        }

        Registro r = new Registro();

        scanner.nextLine();
        System.out.print("Data: ");
        r.data = scanner.nextLine();

        // FUNCIONÁRIO
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

        // TALHÃO
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

        // TRATOR
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

        // LITROS
        System.out.print("Quantidade de litros: ");
        double litros = scanner.nextDouble();
        if (litros > tratorEncontrado.capacidadeMaxima) {
            System.out.println("Capacidade do trator excedida!");
            return;
        }
        r.litros = litros;

        // DESTINO
        scanner.nextLine();
        System.out.print("Destino (Terreiro/Secador): ");
        r.destino = scanner.nextLine();
        if (!r.destino.equals("Terreiro") && !r.destino.equals("Secador")) {
            System.out.println("ERRO: Destino invalido! Digite Terreiro ou Secador.");
            return;
        }

        // SALVAR
        registros[totalRegistros] = r;
        totalRegistros++;
        salvarDados();
        System.out.println("Registro realizado com sucesso!");
    }

    static void salvarDados() {
        try {
            FileWriter fw = new FileWriter("src/funcionarios.txt");
            for (int i = 0; i < totalFuncionarios; i++) {
                fw.write(equipe[i].matricula + ";" + equipe[i].nome + ";" + equipe[i].tipoContrato + "\n");
            }
            fw.close();

            fw = new FileWriter("src/talhoes.txt");
            for (int i = 0; i < totalTalhoes; i++) {
                fw.write(talhoes[i].codigo + ";" + talhoes[i].nome + ";" + talhoes[i].variedade + ";" + talhoes[i].estimativaProducao + "\n");
            }
            fw.close();

            fw = new FileWriter("src/tratores.txt");
            for (int i = 0; i < totalTratores; i++) {
                fw.write(frota[i].placa + ";" + frota[i].capacidadeMaxima + "\n");
            }
            fw.close();

            fw = new FileWriter("src/registros.txt");
            for (int i = 0; i < totalRegistros; i++) {
                fw.write(registros[i].data + ";" + registros[i].matriculaFuncionario + ";" + registros[i].codigoTalhao + ";" + registros[i].placaTrator + ";" + registros[i].litros + ";" + registros[i].destino + "\n");
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar dados!");
        }
    }

    static void carregarDados() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/funcionarios.txt"));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                Funcionario f = new Funcionario();
                f.matricula = Integer.parseInt(partes[0]);
                f.nome = partes[1];
                f.tipoContrato = partes[2];
                equipe[totalFuncionarios] = f;
                totalFuncionarios++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Nenhum dado de funcionarios encontrado.");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/talhoes.txt"));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                Talhao t = new Talhao();
                t.codigo = Integer.parseInt(partes[0]);
                t.nome = partes[1];
                t.variedade = partes[2];
                t.estimativaProducao = Double.parseDouble(partes[3]);
                talhoes[totalTalhoes] = t;
                totalTalhoes++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Nenhum dado de talhoes encontrado.");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/tratores.txt"));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                Trator tr = new Trator();
                tr.placa = partes[0];
                tr.capacidadeMaxima = Double.parseDouble(partes[1]);
                frota[totalTratores] = tr;
                totalTratores++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Nenhum dado de tratores encontrado.");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/registros.txt"));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                Registro r = new Registro();
                r.data = partes[0];
                r.matriculaFuncionario = Integer.parseInt(partes[1]);
                r.codigoTalhao = Integer.parseInt(partes[2]);
                r.placaTrator = partes[3];
                r.litros = Double.parseDouble(partes[4]);
                r.destino = partes[5];
                registros[totalRegistros] = r;
                totalRegistros++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Nenhum dado de registros encontrado.");
        }
    }
}
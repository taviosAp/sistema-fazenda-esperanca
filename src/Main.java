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
                case 3: menuRelatorios(); break;
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
            System.out.println("4. Remover Cadastro");
            System.out.println("5. Voltar");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1: cadastrarFuncionario(); break;
                case 2: cadastrarTalhao(); break;
                case 3: cadastrarTrator(); break;
                case 4: menuRemover(); break;
                case 5: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida!");
            }
        } while (opcao != 5);
    }

    static void menuRemover() {
        int op;

        do {
            System.out.println("\n=== REMOVER CADASTRO ===");
            System.out.println("1. Remover Funcionario");
            System.out.println("2. Remover Talhao");
            System.out.println("3. Remover Trator");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");

            op = scanner.nextInt();

            switch (op) {
                case 1: removerFuncionario(); break;
                case 2: removerTalhao(); break;
                case 3: removerTrator(); break;
                case 4: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida!");
            }

        } while (op != 4);
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

    static void relatorioFuncionarios() {
        System.out.println("\n=== PRODUCAO POR FUNCIONARIO ===");

        for (int i = 0; i < totalFuncionarios; i++) {
            double total = 0;

            for (int j = 0; j < totalRegistros; j++) {
                if (registros[j].matriculaFuncionario == equipe[i].matricula) {
                    total += registros[j].litros;
                }
            }

            System.out.println(equipe[i].nome + " -> " + total + " litros");
        }
    }

    static void removerFuncionario() {
        if (totalFuncionarios == 0) {
            System.out.println("Nao ha funcionarios cadastrados!");
            return;
        }

        System.out.print("Digite a matricula do funcionario: ");
        int mat = scanner.nextInt();

        int pos = -1;

        for (int i = 0; i < totalFuncionarios; i++) {
            if (equipe[i].matricula == mat) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Funcionario nao encontrado!");
            return;
        }

        // Verifica se tem registro
        for (int i = 0; i < totalRegistros; i++) {
            if (registros[i].matriculaFuncionario == mat) {
                System.out.println("ERRO: Funcionario possui registros!");
                return;
            }
        }

        for (int i = pos; i < totalFuncionarios - 1; i++) {
            equipe[i] = equipe[i + 1];
        }

        totalFuncionarios--;
        salvarDados();

        System.out.println("Funcionario removido!");
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

    static void relatorioTalhoes() {
        System.out.println("\n=== PRODUCAO POR TALHAO ===");

        for (int i = 0; i < totalTalhoes; i++) {
            double total = 0;

            for (int j = 0; j < totalRegistros; j++) {
                if (registros[j].codigoTalhao == talhoes[i].codigo) {
                    total += registros[j].litros;
                }
            }

            System.out.println(talhoes[i].nome + " -> " + total + " litros");
        }
    }

    static void removerTalhao() {
        if (totalTalhoes == 0) {
            System.out.println("Nao ha talhoes cadastrados!");
            return;
        }

        System.out.print("Digite o codigo do talhao: ");
        int cod = scanner.nextInt();

        int pos = -1;

        for (int i = 0; i < totalTalhoes; i++) {
            if (talhoes[i].codigo == cod) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Talhao nao encontrado!");
            return;
        }

        // Verifica se tem registro
        for (int i = 0; i < totalRegistros; i++) {
            if (registros[i].codigoTalhao == cod) {
                System.out.println("ERRO: Talhao possui registros!");
                return;
            }
        }

        for (int i = pos; i < totalTalhoes - 1; i++) {
            talhoes[i] = talhoes[i + 1];
        }

        totalTalhoes--;
        salvarDados();

        System.out.println("Talhao removido!");
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

    static void relatorioDestino() {
        System.out.println("\n=== TERREIRO VS SECADOR ===");

        double terreiro = 0;
        double secador = 0;

        for (int i = 0; i < totalRegistros; i++) {
            if (registros[i].destino.equals("Terreiro")) {
                terreiro += registros[i].litros;
            } else if (registros[i].destino.equals("Secador")) {
                secador += registros[i].litros;
            }
        }

        System.out.println("Terreiro: " + terreiro + " litros");
        System.out.println("Secador: " + secador + " litros");
    }

    static void removerTrator() {
        if (totalTratores == 0) {
            System.out.println("Nao ha tratores cadastrados!");
            return;
        }

        scanner.nextLine();
        System.out.print("Digite a placa do trator: ");
        String placa = scanner.nextLine();

        int pos = -1;

        for (int i = 0; i < totalTratores; i++) {
            if (frota[i].placa.equals(placa)) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Trator nao encontrado!");
            return;
        }

        // Verifica se tem registro
        for (int i = 0; i < totalRegistros; i++) {
            if (registros[i].placaTrator.equals(placa)) {
                System.out.println("ERRO: Trator possui registros!");
                return;
            }
        }

        for (int i = pos; i < totalTratores - 1; i++) {
            frota[i] = frota[i + 1];
        }

        totalTratores--;
        salvarDados();

        System.out.println("Trator removido!");
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
        totalFuncionarios = 0;
        totalTalhoes = 0;
        totalTratores = 0;
        totalRegistros = 0;
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
    
    static void menuRelatorios() {
        int op;

        do {
            System.out.println("\n=== RELATORIOS ===");
            System.out.println("1. Producao por funcionario");
            System.out.println("2. Producao por talhao");
            System.out.println("3. Terreiro vs Secador");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");

            op = scanner.nextInt();

            switch (op) {
                case 1: relatorioFuncionarios(); break;
                case 2: relatorioTalhoes(); break;
                case 3: relatorioDestino(); break;
                case 4: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida!");
            }

        } while (op != 4);
    }
}
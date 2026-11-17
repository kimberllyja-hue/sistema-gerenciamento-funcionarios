package view;

import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.Funcionario;
import model.FuncionarioCLT;
import model.FuncionarioComissionado;
import model.FuncionarioPJ;
import service.FuncionarioService;

import java.util.List;
import java.util.Scanner;

public class FuncionarioView {
    private final FuncionarioService service;
    private final Scanner scanner;

    public FuncionarioView(FuncionarioService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            try {
                executarOpcao(opcao);
            } catch (FuncionarioJaCadastradoException | FuncionarioNaoEncontradoException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        System.out.println("Sistema encerrado.");
    }

    private void exibirMenu() {
        System.out.println("\n=== SISTEMA DE FUNCIONÁRIOS E FOLHA DE PAGAMENTO ===");
        System.out.println("1 - Cadastrar funcionário");
        System.out.println("2 - Listar todos os funcionários");
        System.out.println("3 - Buscar funcionário");
        System.out.println("4 - Remover funcionário");
        System.out.println("5 - Exibir dados completos de um funcionário");
        System.out.println("6 - Gerar relatório de salários");
        System.out.println("7 - Calcular total da folha de pagamento");
        System.out.println("0 - Sair");
    }

    private void executarOpcao(int opcao) throws FuncionarioJaCadastradoException, FuncionarioNaoEncontradoException {
        switch (opcao) {
            case 1 -> cadastrarFuncionario();
            case 2 -> listarFuncionarios();
            case 3 -> buscarFuncionarioMenu();
            case 4 -> removerFuncionario();
            case 5 -> exibirDadosCompletos();
            case 6 -> System.out.println("\n" + service.gerarRelatorio());
            case 7 -> System.out.printf("Total da folha de pagamento: R$ %.2f%n", service.calcularTotalFolhaPagamento());
            case 0 -> { }
            default -> System.out.println("Opção inválida.");
        }
    }

    private void cadastrarFuncionario() throws FuncionarioJaCadastradoException {
        System.out.println("Tipo: 1 - CLT | 2 - PJ | 3 - Comissionado");
        int tipo = lerInteiro("Informe o tipo: ");
        int codigo = lerInteiro("Código: ");
        String nome = lerTexto("Nome: ");
        String cpf = lerTexto("CPF (11 dígitos): ");

        Funcionario funcionario;
        if (tipo == 1) {
            double salario = lerDouble("Salário-base: ");
            double beneficios = lerDouble("Adicional de benefícios: ");
            funcionario = new FuncionarioCLT(codigo, nome, cpf, salario, beneficios);
        } else if (tipo == 2) {
            double valorProjeto = lerDouble("Valor por projeto: ");
            int projetos = lerInteiro("Quantidade de projetos: ");
            funcionario = new FuncionarioPJ(codigo, nome, cpf, valorProjeto, projetos);
        } else if (tipo == 3) {
            double salario = lerDouble("Salário-base: ");
            double percentual = lerDouble("Percentual de comissão (0 a 100): ");
            FuncionarioComissionado com = new FuncionarioComissionado(codigo, nome, cpf, salario, percentual);
            double vendas = lerDouble("Total de vendas no período: ");
            com.registrarVendas(vendas);
            funcionario = com;
        } else {
            throw new IllegalArgumentException("Tipo de funcionário inválido.");
        }

        service.cadastrar(funcionario);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private void listarFuncionarios() {
        List<Funcionario> lista = service.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        for (Funcionario f : lista) {
            System.out.println(f.exibirDados());
        }
    }

    private void buscarFuncionarioMenu() throws FuncionarioNaoEncontradoException {
        System.out.println("Buscar por: 1 - Código | 2 - CPF | 3 - Nome");
        int modo = lerInteiro("Escolha: ");
        Funcionario encontrado;
        if (modo == 1) {
            encontrado = service.buscarFuncionario(lerInteiro("Código: "));
        } else if (modo == 2) {
            encontrado = service.buscarFuncionario(lerTexto("CPF: "));
        } else if (modo == 3) {
            String nome = lerTexto("Nome: ");
            boolean exata = lerTexto("Busca exata? (s/n): ").equalsIgnoreCase("s");
            encontrado = service.buscarFuncionario(nome, exata);
        } else {
            throw new IllegalArgumentException("Modo de busca inválido.");
        }
        System.out.println(encontrado.exibirDados());
    }

    private void removerFuncionario() throws FuncionarioNaoEncontradoException {
        int codigo = lerInteiro("Código do funcionário a remover: ");
        service.remover(codigo);
        System.out.println("Funcionário removido com sucesso.");
    }

    private void exibirDadosCompletos() throws FuncionarioNaoEncontradoException {
        int codigo = lerInteiro("Código do funcionário: ");
        Funcionario f = service.buscarFuncionario(codigo);
        System.out.println("=== DADOS DO FUNCIONÁRIO ===");
        System.out.println(f.exibirDados());
        System.out.printf("Salário-base: R$ %.2f%n", f.getSalarioBase());
        System.out.printf("Remuneração calculada: R$ %.2f%n", f.calcularRemuneracao());
    }

    private int lerInteiro(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Digite um número inteiro válido: ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private double lerDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.nextLine();
            System.out.print("Digite um número válido: ");
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    private String lerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
package service;

import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.Funcionario;
import repository.FuncionarioRepository;

import java.util.List;

public class FuncionarioService {
    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repositório não pode ser nulo.");
        }
        this.repository = repository;
    }

    public void cadastrar(Funcionario funcionario) throws FuncionarioJaCadastradoException {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário é obrigatório.");
        }

        boolean codigoExiste = repository.buscarPorCodigo(funcionario.getCodigo()).isPresent();
        if (codigoExiste) {
            throw new FuncionarioJaCadastradoException(
                    "Já existe um funcionário cadastrado com o código: " + funcionario.getCodigo()
            );
        }

        boolean cpfExiste = repository.listarTodos().stream()
                .anyMatch(f -> f.getCpf().equals(funcionario.getCpf()));
        if (cpfExiste) {
            throw new FuncionarioJaCadastradoException(
                    "Já existe um funcionário cadastrado com o CPF: " + funcionario.getCpf()
            );
        }

        repository.salvar(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return repository.listarTodos();
    }

    // Polimorfismo por sobrecarga (Overload) - busca por código
    public Funcionario buscarFuncionario(int codigo) throws FuncionarioNaoEncontradoException {
        return repository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException(
                        "Funcionário não encontrado com código: " + codigo
                ));
    }

    // Polimorfismo por sobrecarga (Overload) - busca por CPF
    public Funcionario buscarFuncionario(String cpf) throws FuncionarioNaoEncontradoException {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório para busca.");
        }
        String cpfLimpo = cpf.replaceAll("\\D", "");
        return repository.listarTodos().stream()
                .filter(f -> f.getCpf().equals(cpfLimpo))
                .findFirst()
                .orElseThrow(() -> new FuncionarioNaoEncontradoException(
                        "Funcionário não encontrado com CPF: " + cpf
                ));
    }

    // Polimorfismo por sobrecarga (Overload) - busca por nome (exata ou parcial)
    public Funcionario buscarFuncionario(String nome, boolean buscaExata) throws FuncionarioNaoEncontradoException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório para busca.");
        }
        String termo = nome.trim();
        return repository.listarTodos().stream()
                .filter(f -> buscaExata
                        ? f.getNome().equalsIgnoreCase(termo)
                        : f.getNome().toLowerCase().contains(termo.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new FuncionarioNaoEncontradoException(
                        "Funcionário não encontrado com nome: " + nome
                ));
    }

    public void remover(int codigo) throws FuncionarioNaoEncontradoException {
        boolean removido = repository.removerPorCodigo(codigo);
        if (!removido) {
            throw new FuncionarioNaoEncontradoException(
                    "Não é possível remover: funcionário inexistente com código: " + codigo
            );
        }
    }

    public double calcularTotalFolhaPagamento() {
        return repository.listarTodos().stream()
                .mapToDouble(Funcionario::calcularRemuneracao)
                .sum();
    }

    public String gerarRelatorio() {
        List<Funcionario> funcionarios = repository.listarTodos();
        if (funcionarios.isEmpty()) {
            return "Nenhum funcionário cadastrado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE FUNCIONÁRIOS E FOLHA DE PAGAMENTO ===\n");
        for (Funcionario f : funcionarios) {
            sb.append(f.exibirDados()).append("\n");
        }
        sb.append(String.format("Total da Folha de Pagamento: R$ %.2f", calcularTotalFolhaPagamento()));
        return sb.toString();
    }
}
package model;

public abstract class Funcionario implements Identificavel {
    private int codigo;
    private String nome;
    private String cpf;
    private double salarioBase;

    protected Funcionario(int codigo, String nome, String cpf, double salarioBase) {
        setCodigo(codigo);
        setNome(nome);
        setCpf(cpf);
        setSalarioBase(salarioBase);
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("Código deve ser maior que zero.");
        }
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || !cpf.replaceAll("\\D", "").matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Informe 11 dígitos.");
        }
        this.cpf = cpf.replaceAll("\\D", "");
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("Salário-base não pode ser negativo.");
        }
        this.salarioBase = salarioBase;
    }

    public abstract double calcularRemuneracao();

    public String exibirDados() {
        return String.format(
                "Código: %d | Nome: %s | CPF: %s | Tipo: %s | Remuneração: R$ %.2f",
                codigo, nome, cpf, getClass().getSimpleName(), calcularRemuneracao()
        );
    }
}
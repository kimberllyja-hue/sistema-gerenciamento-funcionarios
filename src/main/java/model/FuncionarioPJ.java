package model;

public class FuncionarioPJ extends Funcionario {
    private double valorPorProjeto;
    private int projetosRealizados;

    public FuncionarioPJ(int codigo, String nome, String cpf, double valorPorProjeto, int projetosRealizados) {
        super(codigo, nome, cpf, 0.0);
        setValorPorProjeto(valorPorProjeto);
        setProjetosRealizados(projetosRealizados);
    }

    public double getValorPorProjeto() {
        return valorPorProjeto;
    }

    public void setValorPorProjeto(double valorPorProjeto) {
        if (valorPorProjeto < 0) {
            throw new IllegalArgumentException("Valor por projeto não pode ser negativo.");
        }
        this.valorPorProjeto = valorPorProjeto;
    }

    public int getProjetosRealizados() {
        return projetosRealizados;
    }

    public void setProjetosRealizados(int projetosRealizados) {
        if (projetosRealizados < 0) {
            throw new IllegalArgumentException("Quantidade de projetos não pode ser negativa.");
        }
        this.projetosRealizados = projetosRealizados;
    }

    @Override
    public double calcularRemuneracao() {
        return valorPorProjeto * projetosRealizados;
    }
}
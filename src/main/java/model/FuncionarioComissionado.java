package model;

public class FuncionarioComissionado extends Funcionario implements Tributavel {
    private double percentualComissao;
    private double totalVendas;

    public FuncionarioComissionado(int codigo, String nome, String cpf, double salarioBase, double percentualComissao) {
        super(codigo, nome, cpf, salarioBase);
        setPercentualComissao(percentualComissao);
        this.totalVendas = 0.0;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        if (percentualComissao < 0 || percentualComissao > 100) {
            throw new IllegalArgumentException("Percentual de comissão deve estar entre 0 e 100.");
        }
        this.percentualComissao = percentualComissao;
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public void registrarVendas(double valorVenda) {
        if (valorVenda < 0) {
            throw new IllegalArgumentException("Valor de vendas não pode ser negativo.");
        }
        this.totalVendas += valorVenda;
    }

    @Override
    public double calcularTributos() {
        return getSalarioBase() * 0.05;
    }

    @Override
    public double calcularRemuneracao() {
        double comissao = totalVendas * (percentualComissao / 100.0);
        return getSalarioBase() + comissao - calcularTributos();
    }
}
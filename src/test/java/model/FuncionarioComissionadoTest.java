package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioComissionadoTest {

    @Test
    void deveCalcularRemuneracaoComComissaoSobreVendas() {
        // salarioBase = 2000, percentualComissao = 10%, totalVendas = 15000 -> 2000 + 1500 - 100 (tributo 5% de 2000) = 3400
        FuncionarioComissionado com = new FuncionarioComissionado(3, "Beatriz Melo", "11122233344", 2000.0, 10.0);
        com.registrarVendas(15000.0);

        assertEquals(3, com.getCodigo());
        assertEquals(100.0, com.calcularTributos(), 0.001);
        assertEquals(3400.0, com.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveRejeitarPercentualOuVendasInvalidas() {
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioComissionado(3, "Beatriz", "11122233344", 2000.0, 150.0));
        FuncionarioComissionado com = new FuncionarioComissionado(3, "Beatriz", "11122233344", 2000.0, 10.0);
        assertThrows(IllegalArgumentException.class, () -> com.registrarVendas(-200.0));
    }
}
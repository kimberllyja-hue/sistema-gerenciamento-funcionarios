package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioPJTest {

    @Test
    void deveCalcularRemuneracaoPorProjetos() {
        FuncionarioPJ pj = new FuncionarioPJ(2, "Carlos Lima", "98765432100", 2500.0, 3);

        assertEquals(2, pj.getCodigo());
        assertEquals(7500.0, pj.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveRejeitarValoresInvalidos() {
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioPJ(2, "Carlos", "98765432100", -500.0, 2));
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioPJ(2, "Carlos", "98765432100", 2500.0, -1));
    }
}
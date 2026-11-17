package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioCLTTest {

    @Test
    void deveCalcularRemuneracaoComBeneficiosETributos() {
        FuncionarioCLT clt = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 600.0);

        assertEquals(1, clt.getCodigo());
        assertEquals(400.0, clt.calcularTributos(), 0.001);
        assertEquals(4200.0, clt.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveRejeitarDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(1, "", "12345678901", 4000.0, 600.0));
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(1, "Ana", "123", 4000.0, 600.0));
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(1, "Ana", "12345678901", -100.0, 600.0));
        assertThrows(IllegalArgumentException.class, () ->
                new FuncionarioCLT(1, "Ana", "12345678901", 4000.0, -50.0));
    }
}
package exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExcecoesPersonalizadasTest {

    @Test
    void deveCriarExcecoesComMensagem() {
        FuncionarioNaoEncontradoException naoEncontrado =
                new FuncionarioNaoEncontradoException("Funcionário não encontrado");
        FuncionarioJaCadastradoException jaCadastrado =
                new FuncionarioJaCadastradoException("Funcionário já cadastrado");

        assertEquals("Funcionário não encontrado", naoEncontrado.getMessage());
        assertEquals("Funcionário já cadastrado", jaCadastrado.getMessage());
    }
}
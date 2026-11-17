package repository;

import model.Funcionario;
import model.FuncionarioCLT;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioRepositoryTest {

    @Test
    void deveSalvarBuscarEListarFuncionarios() {
        FuncionarioRepository repo = new FuncionarioRepository();
        Funcionario f1 = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 500.0);

        repo.salvar(f1);

        Optional<Funcionario> encontrado = repo.buscarPorCodigo(1);
        assertTrue(encontrado.isPresent());
        assertEquals("Ana Souza", encontrado.get().getNome());
        assertEquals(1, repo.listarTodos().size());
    }

    @Test
    void deveRemoverFuncionarioPorCodigo() {
        FuncionarioRepository repo = new FuncionarioRepository();
        Funcionario f1 = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 500.0);
        repo.salvar(f1);

        boolean removido = repo.removerPorCodigo(1);

        assertTrue(removido);
        assertTrue(repo.buscarPorCodigo(1).isEmpty());
        assertEquals(List.of(), repo.listarTodos());
    }
}
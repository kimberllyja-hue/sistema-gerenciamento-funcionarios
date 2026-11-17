package service;

import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.Funcionario;
import model.FuncionarioCLT;
import model.FuncionarioComissionado;
import model.FuncionarioPJ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.FuncionarioRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioServiceTest {

    private FuncionarioService service;

    @BeforeEach
    void setUp() {
        FuncionarioRepository repository = new FuncionarioRepository();
        service = new FuncionarioService(repository);
    }

    @Test
    void deveCadastrarEListarFuncionarios() throws FuncionarioJaCadastradoException {
        Funcionario clt = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 600.0);
        service.cadastrar(clt);

        List<Funcionario> lista = service.listarTodos();
        assertEquals(1, lista.size());
        assertEquals("Ana Souza", lista.get(0).getNome());
    }

    @Test
    void deveLancarExcecaoAoCadastrarCodigoOuCpfDuplicado() throws FuncionarioJaCadastradoException {
        Funcionario f1 = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 600.0);
        Funcionario f2 = new FuncionarioPJ(1, "Carlos Lima", "98765432100", 2500.0, 2);
        Funcionario f3 = new FuncionarioComissionado(2, "Beatriz Melo", "12345678901", 2000.0, 10.0);

        service.cadastrar(f1);

        assertThrows(FuncionarioJaCadastradoException.class, () -> service.cadastrar(f2));
        assertThrows(FuncionarioJaCadastradoException.class, () -> service.cadastrar(f3));
    }

    @Test
    void deveBuscarPorSobrecargaDeCodigoCpfENome() throws Exception {
        Funcionario clt = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 600.0);
        service.cadastrar(clt);

        assertEquals(clt, service.buscarFuncionario(1));
        assertEquals(clt, service.buscarFuncionario("12345678901"));
        assertEquals(clt, service.buscarFuncionario("Ana Souza", true));
        assertEquals(clt, service.buscarFuncionario("ana", false));
    }

    @Test
    void deveLancarExcecaoAoBuscarOuRemoverInexistente() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario(99));
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("00000000000"));
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("Ninguem", true));
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.remover(99));
    }

    @Test
    void deveRemoverFuncionarioComSucesso() throws Exception {
        Funcionario clt = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 600.0);
        service.cadastrar(clt);

        service.remover(1);

        assertTrue(service.listarTodos().isEmpty());
    }

    @Test
    void deveCalcularTotalFolhaPagamentoComPolimorfismo() throws FuncionarioJaCadastradoException {
        Funcionario clt = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 600.0); // 4200.0
        Funcionario pj = new FuncionarioPJ(2, "Carlos Lima", "98765432100", 2500.0, 2); // 5000.0
        FuncionarioComissionado com = new FuncionarioComissionado(3, "Beatriz Melo", "11122233344", 2000.0, 10.0);
        com.registrarVendas(10000.0); // 2000 + 1000 - 100 = 2900.0

        service.cadastrar(clt);
        service.cadastrar(pj);
        service.cadastrar(com);

        assertEquals(12100.0, service.calcularTotalFolhaPagamento(), 0.001);
        String relatorio = service.gerarRelatorio();
        assertTrue(relatorio.contains("Ana Souza"));
        assertTrue(relatorio.contains("Carlos Lima"));
        assertTrue(relatorio.contains("Beatriz Melo"));
    }
}
package repository;

import model.Funcionario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FuncionarioRepository {
    private final Map<Integer, Funcionario> funcionariosPorCodigo = new HashMap<>();

    public void salvar(Funcionario funcionario) {
        funcionariosPorCodigo.put(funcionario.getCodigo(), funcionario);
    }

    public Optional<Funcionario> buscarPorCodigo(int codigo) {
        return Optional.ofNullable(funcionariosPorCodigo.get(codigo));
    }

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionariosPorCodigo.values());
    }

    public boolean removerPorCodigo(int codigo) {
        return funcionariosPorCodigo.remove(codigo) != null;
    }
}
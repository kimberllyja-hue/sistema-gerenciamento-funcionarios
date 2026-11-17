import model.Funcionario;
import model.FuncionarioCLT;
import model.FuncionarioComissionado;
import model.FuncionarioPJ;
import repository.FuncionarioRepository;
import service.FuncionarioService;
import view.FuncionarioView;

public class Main {
    public static void main(String[] args) {
        FuncionarioRepository repository = new FuncionarioRepository();
        FuncionarioService service = new FuncionarioService(repository);

        try {
            Funcionario clt = new FuncionarioCLT(1, "Ana Souza", "12345678901", 4000.0, 600.0);
            Funcionario pj = new FuncionarioPJ(2, "Carlos Lima", "98765432100", 2500.0, 2);
            FuncionarioComissionado com = new FuncionarioComissionado(3, "Beatriz Melo", "11122233344", 2000.0, 10.0);
            com.registrarVendas(10000.0);

            service.cadastrar(clt);
            service.cadastrar(pj);
            service.cadastrar(com);

            System.out.println("=== DEMONSTRAÇÃO DE POLIMORFISMO ===");
            for (Funcionario f : service.listarTodos()) {
                System.out.println(f.getNome() + " -> Remuneração: R$ " + String.format("%.2f", f.calcularRemuneracao()));
            }
        } catch (Exception e) {
            System.out.println("Erro ao inicializar dados: " + e.getMessage());
        }

        FuncionarioView view = new FuncionarioView(service);
        view.iniciar();
    }
}
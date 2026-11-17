# Sistema de Gerenciamento de Funcionários e Folha de Pagamento

Projeto em Java (Maven + JUnit 5) desenvolvido com Programação Orientada a Objetos, arquitetura em camadas e TDD.

## Funcionalidades
- Cadastro de funcionários (CLT, PJ e Comissionado).
- Listagem de todos os funcionários cadastrados.
- Busca de funcionários por código, CPF ou nome (sobrecarga de métodos).
- Remoção de funcionários cadastrados.
- Cálculo de remuneração por categoria e exibição dos dados completos.
- Geração de relatório de salários e cálculo do total da folha de pagamento.

## Arquitetura em Camadas
- `model`: `Funcionario` (abstrata), `FuncionarioCLT`, `FuncionarioPJ`, `FuncionarioComissionado`, `Identificavel`, `Tributavel`.
- `repository`: `FuncionarioRepository` (armazenamento em memória).
- `service`: `FuncionarioService` (regras de negócio, folha de pagamento, relatórios e sobrecarga de `buscarFuncionario`).
- `exception`: `FuncionarioNaoEncontradoException`, `FuncionarioJaCadastradoException`.
- `view`: `FuncionarioView` (interação via console).
- `Main`: ponto de entrada e demonstração polimórfica.

## Como Executar o Projeto
No IntelliJ IDEA, abra `Main.java` e clique no botão verde **Run 'Main.main()'**, ou pelo terminal na raiz do projeto:

```bash
mvn compile exec:java -Dexec.mainClass="Main"
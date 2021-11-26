#FASE 1

## Índice

Os pontos seguintes são apresentados de forma cronológica do desenvolvimento do projeto:

- Abordagem inicial à implementação;
- Criação e implementação da classe `FileReader`;
- Criação e implementação da classe `NetworkManager`;
- Criação de métodos para cálculo de métricas e para funcionalidades do grafo na classe `NetworkManager`;
- Implementação da classe `FileReader`, para importação de datasets e construção dos respetivos grafos;
- Criação e implementação da classe `GraphAdjacencyList`, para utilização da estrutura de dados "lista de adjacências";
- Criação da classe `NetworkManagerTest`;
- Implementação dos testes unitários na classe `NetworkManagerTest`;
- Início do desenvolvimento do _mockup_ de baixa fidelidade da interface gráfica idealizada.

## Introdução

Na fase de planeamento do projeto decidimos ter uma visão geral da aplicação a desenvolver - não só da 1ª fase - para pudermos estruturar, desde muito cedo, o que iríamos implementar.

Para a primeira fase, concordamos que o melhor seria criar uma classe que implementasse todas as funcionalidades que podíamos vir a utilizar. Isto facilitou a nossa abordagem aos testes, pois estava
tudo alocado à mesma classe. Esta classe (`NetworkManager`) teria apenas um atributo - o grafo - e todas as funcionalidades teriam por base o mesmo.

Definimos logo desde início, todos os métodos que iríamos necessitar na classe `NetworkManager`, os seus parâmetros de entrada e o que iriam retornar. Desta forma o trabalho dos elementos do grupo
não estava dependente de terceiros.

Uma das primeiras tarefas essenciais que decidimos realizar foi a importação de _datasets_, visto que alimentam a aplicação e é preciso ter atenção à sua elaboração, pois não poderá haver erros.

A distribuição de tarefas entre os elementos do grupo foi atribuída de forma uniforme excluindo a importação de _datasets_.

Para os testes unitários, cada elemento ficou responsável por testar os métodos que cada um produziu.

## Criação da classe `FileReader`

Esta classe recebe uma pasta como parâmetro no construtor que corresponde ao _dataset_ que queremos importar.
Um dataset corresponde ao conjunto dos seguintes ficheiros: `name.txt`, `weight.txt`, `xy.txt` e `routes_*.txt`. Visto que o ficheiro `routes_*.txt` tem um nome dinâmico, decidimos especificar
também qual o ficheiro específico que queríamos utilizar (argumento no construtor).

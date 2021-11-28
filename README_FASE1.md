# Fase 1 - Resumo do Desenvolvimento

## Cronologia

Os pontos seguintes do desenvolvimento do projeto são apresentados de forma cronológica:

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

Na fase de planeamento do projeto decidimos ter uma visão geral da aplicação a desenvolver - não só da 1ª fase - para podermos estruturar, desde muito cedo, o que iríamos implementar.

Para a primeira fase, concordamos que o melhor seria criar uma classe que implementasse todas as funcionalidades que poderíamos vir a utilizar. Isto facilitou a nossa abordagem aos testes, pois estava
tudo alocado à mesma classe. Esta classe (`NetworkManager`) teria apenas um atributo - o grafo - e todas as funcionalidades teriam por base o mesmo.

Definimos, desde início, todos os métodos que iríamos necessitar na classe `NetworkManager`, os seus parâmetros de entrada e o que iriam retornar. Desta forma, o trabalho de cada um dos elementos do grupo
não estava dependente de terceiros.

Uma das primeiras tarefas essenciais que decidimos realizar foi a importação de _datasets_, visto que alimentam a aplicação e é preciso ter atenção à sua elaboração, pois não poderá haver erros.

A distribuição de tarefas entre os elementos do grupo foi atribuída de forma uniforme excluindo a importação de _datasets_.

Para os testes unitários, cada elemento ficou responsável por testar os métodos que cada um produziu.

## Classe `FileReader`

Esta classe recebe um caminho de uma pasta como parâmetro no construtor que corresponde ao _dataset_ que queremos importar.
Um dataset corresponde ao conjunto dos seguintes ficheiros: `name.txt`, `weight.txt`, `xy.txt` e `routes_*.txt`. Visto que o ficheiro `routes_*.txt` tem um nome dinâmico, decidimos especificar
também qual o ficheiro que queríamos utilizar (argumento no construtor).

Existe uma função privada genérica nesta classe "`readFile(String fileName)`" que lê qualquer ficheiro de texto dentro de um dataset e devolve uma `Collection<String>`. Esta função é, também, responsável por ignorar comentários no momento da leitura.
Para cada ficheiro, foram criados métodos específicos: `readName()`, `readWeight()`, `readXY()` e `readRoutes()`. Todos estes métodos iteram a função genérica `readFile(String fileName)`.

Visto que o ficheiro de rotas é uma matriz de distâncias de um grafo bidirecional, basta apenas ler o triângulo superior/inferior da matriz, pois a distância entre dois Hubs A --> B é a mesma distância entre B --> A (matriz **simétrica**). Esta pequena alteração pode ter efeito na performance da aplicação, principalmente para os datasets com centenas de Hubs, devido à complexidade quadrática de leitura O(n²) - n sendo o número de Hubs.

## Classe `NetworkManager`

Tal como referimos na introdução, como a classe `NetworkManager` está responsável por praticamente todas as funcionalidades - incluíndo a leitura de ficheiros - no construtor criamos uma instância da classe `FileReader` e preenchemos imediatamente o grafo. Para isto ser possível, é preciso que a classe `NetworkManager` receba os mesmos argumentos da classe `FileReader` (path do dataset e ficheiro específico das routes).

As funções que existem nesta classe foram distribuídas de uma forma relativamente uniforme, ficando cada membro do grupo com um conjunto de funcionalidades específicas. De seguida, apresentamos as funções que cada um realizou na `NetworkManager`:

Alexandre:
- public Graph<Hub,Route> **getGraph**()
- public void setCoordinates(SmartGraphPanel<Hub, Route> graphView)
- private void **createVertices**(List<Hub> hubs)
- private void **createEdges**(List<Route> routes)
- public Vertex<Hub> **createVertex**(Hub hub) throws InvalidVertexException
- public Edge<Route,Hub> **createEdge**(Route route) throws InvalidEdgeException
- public Vertex<Hub> **removeVertex**(Hub hub) throws InvalidVertexException
- public Edge<Route,Hub> **removeEdge**(Route route) throws InvalidEdgeException
- public boolean **isIsolated**(Hub hub)
- public List<Hub> **getIsolatedHubs**()
- public int **countIsolatedHubs**()
- public int **components**()

Rafael:
- public List<Hub> **getHubs**()
- public List<Route> **getRoutes**()
- public Hub **getHub**(String name)
- public Vertex<Hub> **getVertex**(Hub hub)
- public Vertex<Hub> **getVertex**(String name)
- public Map<Hub,Integer> **getCentrality**()
- public List<Hub> **top5Centrality**()
- public boolean **areNeighbors**(Hub origin, Hub destination)

Daniel:
- public Route **getRoute**(Hub origin, Hub destination)
- public Route **getRoute**(String origin, String destination)
- public Edge<Route,Hub> **getEdge**(Route route)
- public List<Hub> **getNeighbors**(Hub hub)
- public int **countNeighbors**(Hub hub)
- public int **countNeighbors**(String name)

Henrique:
- public List<Hub> **shortestPath**(Hub origin, Hub destination)
- public List<Hub> **breadthFirstSearch**(Hub root)
- public List<Hub> **depthFirstSearch**(Hub root)
- private Vertex<Hub> **findMinVertex**(Set<Vertex<Hub>> unvisited, Map<Vertex<Hub>,Double> costs)
- public double **minimumCostPath**(Vertex<Hub> origin, Vertex<Hub> destination, List<Vertex<Hub>> localsPath)
- private void **dijkstra**(Vertex<Hub> orig, Map<Vertex<Hub>, Double> costs, Map<Vertex<Hub>, Vertex<Hub>> predecessors)
- public int **shortestPathTotalDistance**(Hub origin, Hub destination)

## Classe `NetworkManagerTest`

Para testar as funções desenvolvidas na classe `NetworkManager`, criámos uma diretoria de testes para o package em que estamos interessados e criámos a classe `NetworkManagerTest`.

Sabendo que as funcionalidades que o grafo apresenta não são alteradas após uma diferente implementação (i.e. `GraphEdgeList` e `GraphAdjacencyList`), avançámos imediatamente com os testes.

Achámos que o melhor seria criar uma função setUp() (before each) para criar uma nova instância de NetworkManager, de modo que o grafo no início de cada teste fosse exatamente o mesmo. 

Como cada elemento ficou responsável por testar cada uma das suas funções, a distribuição dos testes foi, mais uma vez, uniforme. Definimos um padrão para o nome dos testes, em que o nome do teste seria "checkMethod()", sendo Method() o nome do método que estaríamos a testar:

Alexandre:
- checkCreateVertex()
- checkCreateEdge()
- checkRemoveVertex()
- checkRemoveEdge()
- checkIsIsolated()
- checkComponents()
- checkCountIsolated()
- checkGetIsolatedHubs()

Rafael:
- checkTop5HubsCentrality()
- checkGetHub()
- checkGetVertex()

Daniel:
- checkGetRoute()
- checkGetNeighbors()
- checkCountNeighbors()

Henrique:
- checkShortestPathTotalDistance()
- verifyAreHubsInSameComponent()
- checkShortestPath()
- checkDepthFirstSearchAndBreadthFirstSearch()
- checkReplaceVertex()
- checkReplaceEdge()


## Classe `GraphAdjacencyList`

Todo o desenvolvimento da aplicação para a primeira fase foi realizado através da implementação da interface Graph que já existia - `GraphEdgeList` - pois sabíamos que esta implementação estava correta e que os testes que estaríamos a realizar seriam de acordo com o expectável.

Após a concretização dos testes unitários, decidimos, novamente, distribuir de forma uniforme as funções que ainda não tinham implementação na classe `GraphAdjacencyList`:

Alexandre:
- public int **numVertices**()
- public int **numEdges**()
- public Collection<Vertex<V>> **vertices**()
- public Collection<Edge<E, V>> **edges**()
- public Collection<Edge<E, V>> **incidentEdges**(Vertex<V> v) throws InvalidVertexException

Rafael:
- public Vertex<V> **insertVertex**(V vElement) throws InvalidVertexException
- public Edge<E, V> **insertEdge**(Vertex<V> u, Vertex<V> v, E edgeElement) throws InvalidVertexException, InvalidEdgeException
- public Edge<E, V> **insertEdge**(V vElement1, V vElement2, E edgeElement) throws InvalidVertexException, InvalidEdgeException

Daniel:
- public Vertex<V> **opposite**(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException
- public V **removeVertex**(Vertex<V> v) throws InvalidVertexException
- public E **removeEdge**(Edge<E, V> e) throws InvalidEdgeException

Henrique:
- public V **replace**(Vertex<V> v, V newElement) throws InvalidVertexException
- public E **replace**(Edge<E, V> e, E newElement) throws InvalidEdgeException

## Mockup

Em último lugar, realizámos um mockup de média fidelidade, relativamente à aplicação que idealizámos:
![](mockup_PA.png)

Como é possível observar na imagem acima, as métricas do grafo são constantemente exibidas na parte inferior do ecrã, enquanto que outro tipo de ações estão na parte superior.

Ao clicarmos num vértice ou numa aresta, no lado direito do ecrã são apresentados todos os detalhes relativamente ao elemento.
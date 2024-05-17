import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    Node[] nodes;
    Edge[] edges;

    public Graph() {
        this.nodes = new Node[0];
        this.edges = new Edge[0];
    }

    public void addEdge(int id1, int id2, int weight) {
        Node v1 = findNode(id1);
        Node v2 = findNode(id2);
        for (Edge edge : edges) {
            if ((edge.v1 == v1 && edge.v2 == v2) || (edge.v1 == v2 && edge.v2 == v1)) {
                System.out.println("Krawędź już istnieje.");
                return;
            }
        }

        if (v1 != null && v2 != null) {
            edges = Arrays.copyOf(edges, edges.length + 1);
            edges[edges.length - 1] = new Edge(v1, v2, weight);
            System.out.println("Dodano nową Krawędź o wierzchołkach (które mają id) v1: " + v1.id + " v2: " + v2.id + " weight: " + weight);
        } else {
            System.out.println("Nie ma Node, który połączyłby się z tą krawędzią");
        }
    }

    public Edge findEdge(int id1, int id2) {
        for (Edge edge : edges) {
            if ((edge.v1.id == id1 && edge.v2.id == id2) || (edge.v1.id == id2 && edge.v2.id == id1)) {
                return edge;
            }
        }
        return null;
    }

    public Edge[] getEdgeConnection(int id) {
        Edge[] edgesTemp = new Edge[0];
        for (Edge edge : edges) {
            if (edge.v1.id == id || edge.v2.id == id) {
                edgesTemp = Arrays.copyOf(edgesTemp, edgesTemp.length + 1);
                edgesTemp[edgesTemp.length - 1] = edge;
            }
        }
        return edgesTemp;
    }

    public void removeNodes(int id) {
        Node node = findNode(id);
        if (node != null) {
            Edge[] edgesRemoval = getEdgeConnection(id);
            for (Edge edge : edgesRemoval) {
                System.out.println("Usunięto krawędź z wagą " + edge.weight + " i id1: " + edge.v1.id + " oraz id2: " + edge.v2.id);
                edges = Arrays.stream(edges).filter(e -> e != edge).toArray(Edge[]::new);
            }
            nodes = Arrays.stream(nodes).filter(n -> n.id != id).toArray(Node[]::new);
            System.out.println("usunięto wierzchołek z id: " + id);
        } else {
            System.out.println("Wierzchołek z id: " + id + " nie istnieje");
        }
    }

    public void removeEdge(int id1, int id2) {
        Edge edge = findEdge(id1, id2);
        if (edge != null) {
            edges = Arrays.stream(edges).filter(e -> e != edge).toArray(Edge[]::new);
            System.out.println("Usunięto krawędź przyczepionej do wierzchołków o id1: " + id1 + " id2: " + id2);
        } else {
            System.out.println("Nie znaleziono krawędzi o wierzchołkach o id1: " + id1 + " id2: " + id2);
        }
    }

    public void addNode(int id) {
        if (findNode(id) == null) {
            nodes = Arrays.copyOf(nodes, nodes.length + 1);
            nodes[nodes.length - 1] = new Node(id);
            System.out.println("Dodano nowy NODE z id: " + id);
        } else {
            System.out.println("Node z id: " + id + " już istnieje");
        }
    }

    public Node findNode(int id) {
        for (Node node : nodes) {
            if (node.id == id) {
                return node;
            }
        }
        return null;
    }

    public int dijkstra(int sourceId, int destinationId) {
        Node source = findNode(sourceId);
        Node destination = findNode(sourceId);

        if (source == null) {
            System.out.println("Nie istnieje wierzchołek który ma być źródłem o id: " + sourceId);
            return -1;
        }
        if (destination == null) {
            System.out.println("Nie istnieje wierzchołek który ma być celem o id: " + sourceId);
            return -1;
        }

        int[] distance = new int[nodes.length];
        boolean[] visited = new boolean[nodes.length];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[getIndex(source)] = 0;

        while (true) {
            int minDistance = Integer.MAX_VALUE;
            Node u = null;
            int uIndex = -1;
            for (int i = 0; i < nodes.length; i++) {
                if (!visited[i] && distance[i] < minDistance) {
                    minDistance = distance[i];
                    u = nodes[i];
                    uIndex = i;
                }
            }
            if (u == null) {
                break;
            }
            if (u.id == destinationId) {
                return distance[uIndex];
            }

            visited[uIndex] = true;

            Edge[] edges = getEdgeConnection(u.id);
            for (Edge edge : edges) {
                Node v = (edge.v1 == u) ? edge.v2 : edge.v1;
                int vIndex = getIndex(v);
                int alt = distance[uIndex] + edge.weight;
                if (alt < distance[vIndex]) {
                    distance[vIndex] = alt;
                }
            }
        }

        return distance[getIndex(destination)];
    }

    private int getIndex(Node node) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == node) {
                return i;
            }
        }
        return -1;
    }
}

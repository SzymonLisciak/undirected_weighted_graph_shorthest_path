import java.lang.reflect.Array;
import java.util.*;
public class Graph {
    List<Node> nodes;
    List<Edge> edges;

    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
    public void addEdge(int id1, int id2, int weight){
        Node v1 = findNode(id1);
        Node v2 = findNode(id2);
        if (v1 != null && v2 != null) {
            edges.add(new Edge(v1, v2, weight));
            System.out.println("Dodano nową Krawędź o wartościach v1: " + v1 + " " + "v2: " + v2 + " " + "weight: " + weight);
        }
            else {
            System.out.println("Taka Krawędź już istnieje");
        }

    }

    public Edge findEdge(int id1, int id2) {
        if (this.edges != null) {
            for (int i = 0; i < this.edges.size(); i++) {
                if (id1 == this.edges.get(i).v1.id && id2 == this.edges.get(i).v2.id) {
                    return edges.get(i);
                }
            }

        }
        return null;
    }
    public List<Edge> getEdgeConnection(int id){
        List<Edge> edgesTemp = new ArrayList<Edge>();

        if (edges != null){
            int i = 0;
            for (i = 0; i < edges.size(); i++){
                if (id == edges.get(i).v1.id || id == edges.get(i).v2.id){
                    edgesTemp.add(edges.get(i));
                }
            }
        }
        else{
            return null;
        }

        return edgesTemp;
    }

    public void removeNodes(int id) {
        Node node = findNode(id);
        if (node != null) {
            List<Edge> edgesRemoval = getEdgeConnection(id);
            Edge edgeLog;
                if (edgesRemoval != null) {
                    for (int i = 0; i < edgesRemoval.size(); i++) {
                        System.out.println("Usunięto krawędź z wagą" + edgesRemoval.get(i).weight + " i id1: " + edgesRemoval.get(i).v1.id + "oraz id2" + edgesRemoval.get(i).v2.id);
                        edges.remove(edgesRemoval.get(id));

                    }
                }
                nodes.remove(node);
                System.out.println("usunięto wierzchołek z id: " + id);
        }
        else {
            System.out.println("Wierzchołek z id: " + id + " " + "nie istnieje");
        }

    }

    public void removeEdge(int id1, int id2) {
        if (findEdge(id1, id2) != null) {
            edges.remove(findEdge(id1, id2));
            System.out.println("Usunięto krawędź przyczepionej do wierzchołków o id1 :" + id1 + " " + "id2: " + id2);
        }
        else{
            System.out.println("Nie znaleziono krawędzi o wierzchołkah o id1 :" + id1 + " " + "id2: " + id2);
        }
    }

    public void addNode(int id) {
        if (findNode(id) == null) {
            this.nodes.add(new Node(id));
            System.out.println("Dodano nowy NODE z id:" + id);
        }
        else {
            System.out.println("Node z id: " + id + " " + "Już istnieje");
        }
    }

    public Node findNode(int id) {
        for (int i = 0; i < this.nodes.size(); i++) {
            if(this.nodes.get(i).id == id) {
                return nodes.get(id);
            }
        }
        return null;
    }
}
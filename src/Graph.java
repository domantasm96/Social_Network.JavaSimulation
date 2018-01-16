import java.util.Stack;

public class Graph {
    private int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * Create an empty graph with V vertices.
     * @throws java.lang.IllegalArgumentException if V < 0
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * Return the number of vertices in the graph.
     */
    public int V() { return V; }

    /**
     * Return the number of edges in the graph.
     */
    public int E() { return E; }


    /**
     * Add the undirected edge v-w to graph.
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (w < 0 || w >= V) throw new IndexOutOfBoundsException();
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }


    /**
     * Return the list of neighbors of vertex v as in Iterable.
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<Integer> adj(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj[v];
    }


    /**
     * Return a string representation of the graph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        String vertex_id;
        String edge_id;
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            vertex_id = Operations.people_data.get(v).split(",")[0];
            s.append(v  + ". " + vertex_id + ": ");
            for (int w : adj[v]) {
                edge_id = Operations.people_data.get(w).split(",")[0];
                s.append(edge_id + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}

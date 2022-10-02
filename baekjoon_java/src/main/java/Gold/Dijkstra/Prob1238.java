package Gold.Dijkstra;


import java.util.*;
import java.io.*;

public class Prob1238 {

    static int n;
    static int m;
    static int x;

    static List<Edge>[] edges;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            edges[s].add(new Edge(d, t));
        }

        int[] distToHome = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            distToHome[i] = Integer.MAX_VALUE;
        }
        dijkstraToHome(x, distToHome);

        int ans = Integer.MIN_VALUE;
        for (int i = 1; i < n + 1; i++) {
//            ans = Math.max(ans, dijkstra(i));
            if (i == x) {
                continue;
            }
            System.out.println("dijkstra(i)) = " + dijkstra(i) + distToHome[i]);
        }

//        System.out.println(ans);
    }

    public static void dijkstraToHome(int node, int[] dist) {
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.t - e2.t;
            }
        });

        queue.offer(new Edge(node, 0));
        dist[node] = 0;

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int curD = edge.d;
            int curT = edge.t;

            List<Edge> adjEdges = edges[curD];
            for (Edge adj : adjEdges) {
                int nextD = adj.d;
                int nextT = adj.t;

                if (dist[nextD] > curT + nextT) {
                    dist[nextD] = curT + nextT;
                    queue.offer(adj);
                }
            }

        }
    }

    public static int dijkstra(int node) {
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.t - e2.t;
            }
        });
        int[] dist = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        queue.offer(new Edge(node, 0));
        dist[node] = 0;

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int curD = edge.d;
            int curT = edge.t;

            if (curD == x) {
                return dist[x];
            }

            List<Edge> adjEdges = edges[curD];
            for (Edge adj : adjEdges) {
                int nextD = adj.d;
                int nextT = adj.t;

                if (dist[nextD] > curT + nextT) {
                    dist[nextD] = curT + nextT;
                    queue.offer(adj);
                }
            }

        }

        return dist[x];
    }

    public static class Edge{
        int d;
        int t;

        public Edge(int d, int t) {
            this.d = d;
            this.t = t;
        }
    }
}

package Gold.Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4:
 *
 * Solution: Dijkstra
 * -> But, Floyd-Warshall(DP) 가 더 빠름
 */
public class Prob11404_dijkstra {

    static int n;
    static int m;
    static int[][] dij;
    static List<Edge>[] edges;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        dij = new int[n + 1][n + 1];
        edges = new List[n + 1];
        for (int y = 0; y < n + 1; y++) {
            for (int x = 0; x < n + 1; x++) {
                dij[y][x] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
        }

        m = Integer.parseInt(br.readLine());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int ts = Integer.parseInt(st.nextToken());
            int td = Integer.parseInt(st.nextToken());
            int tCost = Integer.parseInt(st.nextToken());

            edges[ts].add(new Edge(ts, td, tCost));
        }

        for (int y = 1; y < n + 1; y++) {
            dijkstra(y);
            for(int x = 1; x < n + 1; x++){
                if (dij[y][x] == Integer.MAX_VALUE) {
                    ans.append("0 ");
                } else {
                    ans.append(dij[y][x] + " ");
                }
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static void dijkstra(int src) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.cost - e2.cost;
            }
        });
        queue.offer(new Edge(0, src, 0));
        while (!queue.isEmpty()) {
            Edge cur = queue.poll();
            int curS = cur.s;
            int curD = cur.d;
            int curCost = cur.cost;

            if (curCost > dij[src][curD]) {
                continue;
            }
            dij[src][curD] = curCost;

            List<Edge> adjEdges = edges[curD];
            for (Edge next : adjEdges) {
                int nextS = next.s; // == curD
                int nextD = next.d;
                int nextCost = next.cost + curCost; // curD -> nextD 까지 거리 + src -> curD 까지 거리

                if (dij[src][nextD] > nextCost) {
                    dij[src][nextD] = nextCost;
                    queue.offer(new Edge(curD, nextD, nextCost));
                }
            }
        }
    }

    static class Edge{
        int s;
        int d;
        int cost;

        public Edge(int s, int d, int cost) {
            this.s = s;
            this.d = d;
            this.cost = cost;
        }
    }
}

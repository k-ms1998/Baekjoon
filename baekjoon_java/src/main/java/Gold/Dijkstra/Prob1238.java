package Gold.Dijkstra;

import java.util.*;
import java.io.*;

/**
 * Gold 3
 *
 * https://www.acmicpc.net/problem/1238
 *
 * Solution: Dijkstra
 */
public class Prob1238 {

    static int n;
    static int m;
    static int x;

    static List<Edge>[] edges;

    /**
     * 모든 노드에서 x 로 가는 최단 거리를 구할려면 x 만큼 다익스트르라를 돌려야됨
     * But, 만약에 s -> x 라는 edge 를 x -> s 로 가는 edge 로 변환하면, 한번만 다익트르라를 돌리고 각 노드로 부터 x 까지의 최단 거리를 구할 수 있음
     */
    static List<Edge>[] edgesReverse;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        edgesReverse = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
            edgesReverse[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            edges[s].add(new Edge(d, t));
            edgesReverse[d].add(new Edge(s, t));
        }

        /**
         * distToHome = x -> 집
         * dist = 집 -> x
         */
        int[] distToHome = dijkstra(x, edges);
        int[] dist = dijkstra(x,edgesReverse);

        int ans = Integer.MIN_VALUE;
        for (int i = 1; i < n + 1; i++) {
            ans = Math.max(ans, dist[i] + distToHome[i]);
        }

        System.out.println(ans);
    }

    public static int[] dijkstra(int node, List<Edge>[] curEdges) {
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
        queue.offer(new Edge(x, 0));
        dist[x] = 0;

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int curD = edge.d;
            int curT = edge.t;

            List<Edge> adjEdges = curEdges[curD];
            for (Edge adj : adjEdges) {
                int nextD = adj.d;
                int nextT = adj.t;

                if (dist[nextD] > curT + nextT) {
                    dist[nextD] = curT + nextT;
                    queue.offer(new Edge(nextD, curT + nextT));
                }
            }

        }

        return dist;
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

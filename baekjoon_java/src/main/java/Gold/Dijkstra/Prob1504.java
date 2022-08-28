package Gold.Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/1504
 *
 * Solution: Dijkstra
 */
public class Prob1504 {

    static int n;
    static int e;
    static List<Edge>[] edges;
    static int v1;
    static int v2;

    static int MAX_COST = 200000001;
    static int ans = MAX_COST;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges[a].add(new Edge(b, c)); // a -> b
            edges[b].add(new Edge(a, c)); // b -> a
        }

        st = new StringTokenizer(br.readLine());
        v1 = Integer.parseInt(st.nextToken());
        v2 = Integer.parseInt(st.nextToken());

        /**
         * 가능한 경로:
         * 1. 1 -> v1 -> v2 -> n
         * 2. 1 -> v2 -> v1 -> n
         *
         * shortestA = 1에서 모든 정점까지의 최단 거리
         * shortestB = v1에서 모든 정점까지의 최단 거리
         * shortestC = v2에서 모든 정점까지의 최단 거리
         */
        int[] shortestA = new int[n + 1];
        int[] shortestB = new int[n + 1];
        int[] shortestC = new int[n + 1];
        dijkstra(1, shortestA);
        dijkstra(v1, shortestB);
        dijkstra(v2, shortestC);

        int ansA = 0;
        if (shortestA[v1] == MAX_COST || shortestC[n] == MAX_COST) {
            ansA = MAX_COST;
        } else {
            ansA = shortestA[v1] + shortestB[v2] + shortestC[n];   // 1 -> v1 + v1 -> v2 + v2 -> n
        }

        int ansB = 0;
        if (shortestA[v2] == MAX_COST || shortestB[n] == MAX_COST) {
            ansB = MAX_COST;
        } else {
            ansB = shortestA[v2] + shortestC[v1] + shortestB[n];  // (1 -> v1) + (v2 -> v1) + (v2 -> n)
        }

        System.out.println(Math.min(ansA,ansB) < MAX_COST ? Math.min(ansA,ansB) : -1);
    }

    public static void dijkstra(int src, int[] visited) {
        for (int i = 1; i < n + 1; i++) {
            visited[i] = MAX_COST;
        }
        visited[src] = 0;

        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.cost - e2.cost;
            }
        });
        queue.add(new Edge(src, 0));

        while (!queue.isEmpty()) {
            Edge curEdge = queue.poll();
            int curD = curEdge.d;
            int curCost = curEdge.cost;

            if (curCost > visited[curD]) {
                continue;
            }
            List<Edge> adjEdges = edges[curD];
            for (Edge edge : adjEdges) {
                int nextD = edge.d;
                int nextEdgeCost = edge.cost;

                int nextCost = curCost + nextEdgeCost;
                if (visited[nextD] > nextCost) {
                    visited[nextD] = nextCost;
                    queue.offer(new Edge(nextD, nextCost));
                }
            }
        }

    }

    static class Edge{
        int d;
        int cost;

        public Edge(int d, int cost) {
            this.d = d;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "{ " + d + ", " + cost + " }";
        }
    }
}

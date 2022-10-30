package Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 4(서강그라운드)
 *
 * https://www.acmicpc.net/problem/14938
 *
 * Solution: Dijkstra
 */
public class Prob14938 {

    static int n, m, r;
    static int[] items;
    static List<Edge>[] edges;

    static int[][] dist;

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        items = new int[n + 1];
        edges = new List[n + 1];
        dist = new int[n + 1][n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            items[i] = Integer.parseInt(st.nextToken());
            edges[i] = new ArrayList<>();
            for (int j = 1; j < n + 1; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[src].add(new Edge(dst, cost));
            edges[dst].add(new Edge(src, cost));
        }

        /**
         * 시작 지점이 안주어졌기 떄문에 모든 노드에 대해서 최단거리 탐색
         */
        for(int i = 1; i < n + 1; i++){
            dijkstra(i);
            int cnt = 0;
            for (int j = 1; j < n + 1; j++) {
                if (dist[i][j] <= m) {
                    cnt += items[j];
                }
            }
            ans = Math.max(ans, cnt);
        }

        System.out.println(ans);
    }

    public static void dijkstra(int start) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.cost - e2.cost;
            }
        });
        queue.offer(new Edge(start, 0));
        dist[start][start] = 0;

        while (!queue.isEmpty()) {
            Edge curEdge = queue.poll();
            int curSrc = curEdge.dst;

            List<Edge> adjEdges = edges[curSrc];
            for (Edge adj : adjEdges) {
                int nextDst = adj.dst;
                int nextCost = adj.cost + curEdge.cost;

                if (dist[start][nextDst] > nextCost && nextCost <= m) {
                    dist[start][nextDst] = nextCost;
                    queue.offer(new Edge(nextDst, nextCost));
                }
            }
        }

    }

    public static class Edge{
        int dst;
        int cost;

        public Edge(int dst, int cost) {
            this.dst = dst;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "dst=" + dst +
                    ", cost=" + cost +
                    '}';
        }
    }
}
/*
5 5 4
5 7 8 2 3
1 4 5
5 2 4
3 2 3
1 2 3
 */

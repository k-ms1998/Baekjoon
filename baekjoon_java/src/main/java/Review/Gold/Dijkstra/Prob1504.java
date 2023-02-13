package Review.Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 4(특정한 최단 경로)
 *
 * https://www.acmicpc.net/problem/1504
 *
 * Solution: Dijkstra
 * 1. 정점 1에서 시작해서 무조건 v1, v2를 거쳐서 정점 n에 도달
 *  1 -> v1 -> v2 -> n OR 1 -> v2 -> v1 -> n
 * 2. 1에서 모든 정점까지의 최단 거리, v1에서 모든 정점까지의 최단 거리, v2에서 모든 정점까지의 최단 거리 계산
 *  -> 1, v1, v2에서 시작할때 각각 다익스티라로 최단 거리 계산
 * 3. 정답은 Min((1->v1) + (v1->v2) + (v2->n), (1->v2) + (v2->v1) + (v1->n))
 */
public class Prob1504 {

    static int n, e;
    static int v1, v2;
    static List<Edge>[] edges;
    /**
     * dist[3][n+1]:
     * dist[0] => 정점 1부터 모든 정점까지의 최단 거리
     * dist[1] => 정점 v1부터 모든 정점까지의 최단 거리
     * dist[2] => 정점 v2부터 모든 정점까지의 최단 거리
     */
    static int[][] dist;
    static final int INF = 100000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        dist = new int[3][n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
            dist[0][i] = INF;
            dist[1][i] = INF;
            dist[2][i] = INF;
        }

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges[a].add(new Edge(a, b, c));
            edges[b].add(new Edge(b, a, c));
        }

        st = new StringTokenizer(br.readLine());
        v1 = Integer.parseInt(st.nextToken());
        v2 = Integer.parseInt(st.nextToken());

        dijkstra(1, 0);
        dijkstra(v1, 1);
        dijkstra(v2, 2);

        int distA = dist[0][v1] + dist[1][v2] + dist[2][n];
        int distB = dist[0][v2] + dist[2][v1] + dist[1][n];

        int ans = Math.min(distA, distB);
        System.out.println(ans >= INF ? -1: ans);
    }

    public static void dijkstra(int start, int idx) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.d - o2.d;
            }
        });

        dist[idx][start] = 0;
        queue.offer(new Node(start, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int num = node.num;
            int d = node.d;

            if (d > dist[idx][num]) {
                continue;
            }

            for (Edge edge : edges[num]) {
                int adj = edge.b;
                int adjCost = edge.cost;
                if (dist[idx][adj] > adjCost + d) {
                    dist[idx][adj] = adjCost + d;
                    queue.offer(new Node(adj, adjCost + d));
                }
            }
        }
    }

    public static class Edge {
        int a;
        int b;
        int cost;

        public Edge(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }

    public static class Node{
        int num;
        int d;

        public Node(int num, int d) {
            this.num = num;
            this.d = d;
        }
    }
}

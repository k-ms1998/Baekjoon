package Gold.Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/1753
 *
 * Solution: Dijkstra
 */
public class Prob1753 {

    static int v;
    static int e;
    static int k;
    static List<Edge>[] edges;
    static int MAX_COST;

    static int[] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        k = Integer.parseInt(br.readLine());

        dist = new int[v + 1];
        edges = new List[v + 1];
        MAX_COST = e * 10 + 1;
        for (int i = 0; i < v + 1; i++) {
            dist[i] = MAX_COST;
            edges[i] = new ArrayList<>();
        }
        dist[k] = 0;

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int ts = Integer.parseInt(st.nextToken());
            int td = Integer.parseInt(st.nextToken());
            int tc = Integer.parseInt(st.nextToken());

            edges[ts].add(new Edge(td, tc));
        }

        dijkstra();

        StringBuilder ans = new StringBuilder();
        for (int i = 1; i < v + 1; i++) {
            if (dist[i] == MAX_COST) {
                ans.append("INF\n");
            } else {
                ans.append(dist[i] + "\n");
            }
        }

        System.out.println(ans);
    }

    static void dijkstra() {
        /**
         * PriorityQueue 로 MinHeap 을 유지해야 시간 단축
         */
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.cost - e2.cost;
            }
        });
        /**
         * null -> k
         * k -> k 와 인접한 노드들
         */
        queue.offer(new Edge(k, 0));

        while (!queue.isEmpty()) {
            Edge curEdge = queue.poll();
            int curD = curEdge.d;
            int curCost = curEdge.cost;

            if(curCost > dist[curD]){
                continue;
            }

            List<Edge> tmpEdges = edges[curD];
            for (Edge edge : tmpEdges) {
                int nextCost = curCost +  edge.cost;
                if (nextCost < dist[edge.d]) {
                    dist[edge.d] = nextCost;
                    queue.offer(new Edge(edge.d, nextCost));
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
            return "Edge{" +
                    ", d=" + d +
                    ", cost=" + cost +
                    '}';
        }
    }
}

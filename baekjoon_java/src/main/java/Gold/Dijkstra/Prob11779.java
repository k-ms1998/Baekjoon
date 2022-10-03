package Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 3(최소비용 구하기 2)
 *
 * https://www.acmicpc.net/problem/11779
 *
 * Solution: Dijkstra
 */
public class Prob11779 {

    static int n;
    static int m;
    static List<Edge>[] edges;
    static List<Edge>[] edgesReverse;
    static int src;
    static int dst;

    static int[] dist;
    static Stack<Integer> visitedNodes = new Stack<>();

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        edges = new List[n + 1];
        edgesReverse = new List[n + 1];
        dist = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
            edgesReverse[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            edges[s].add(new Edge(d, t));
            edgesReverse[d].add(new Edge(s, t));
        }

        st = new StringTokenizer(br.readLine());
        src = Integer.parseInt(st.nextToken());
        dst = Integer.parseInt(st.nextToken());

        /**
         * src -> dst 최단 거리 구하기
         */
        dijkstra();
        /**
         * 경로 역추적
         */
        trackEdge(dst, dist[dst]);

        ans.append(dist[dst]).append("\n");
        ans.append(visitedNodes.size()).append("\n");
        while (!visitedNodes.isEmpty()) {
            ans.append(visitedNodes.pop()).append(" ");
        }

        System.out.println(ans);
    }

    public static void dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.t - e2.t;
            }
        });

        dist[src] = 0;
        pq.offer(new Edge(src, 0));

        while (!pq.isEmpty()) {
            Edge curEdge = pq.poll();
            int curD = curEdge.d;
            int curT = curEdge.t;

            if (curD == dst) {
                return;
            }

            List<Edge> adjEdges = edges[curD];
            for (Edge adj : adjEdges) {
                int nextD = adj.d;
                int nextT = adj.t;

                int nextDist = curT + nextT;
                if (dist[nextD] > nextDist) {
                    dist[nextD] = nextDist;
                    pq.offer(new Edge(nextD, nextDist));
                }
            }
        }
    }

    public static void trackEdge(int node, int minDist) {
        visitedNodes.push(node);
        if (minDist == 0 && node == src) {
            return;
        }

        List<Edge> adjEdges = edgesReverse[node];
        for (Edge adj : adjEdges) {
            int prevD = adj.d;
            int prevT = adj.t;

            int prevCost = minDist - prevT;
            if (dist[prevD] == prevCost) {
                trackEdge(prevD, prevCost);
                break;
            }
        }
    }

    public static class Edge {
        int d;
        int t;

        public Edge(int d, int t) {
            this.d = d;
            this.t = t;
        }
    }
}

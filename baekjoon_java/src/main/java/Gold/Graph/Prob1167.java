package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 2
 *
 * https://www.acmicpc.net/problem/1167
 *
 * Solution: Graph
 */
public class Prob1167 {

    static int v;

    static List<Edge>[] tree;
    static int[] dist;

    static int ans = 0;
    static int idx = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        v = Integer.parseInt(br.readLine());
        tree = new List[v + 1];

        for (int i = 1; i < v + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int curV = Integer.parseInt(st.nextToken());
            tree[curV] = new ArrayList<>();
            while (true) {
                int nextD = Integer.parseInt(st.nextToken());
                if (nextD == -1) {
                    break;
                }
                int nextCost = Integer.parseInt(st.nextToken());
                tree[curV].add(new Edge(nextD, nextCost));
            }
        }

//        for (int i = 1; i < v + 1; i++) {
//            System.out.println("tree[" + i + "] = " + tree[i]);
//        }

        initDist(1);
        initDist(idx);

        System.out.println(ans);
    }

    public static void initDist(int s) {
        dist = new int[v + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(s);

        boolean visited[] = new boolean[v + 1];
        visited[s] = true;
        while(!queue.isEmpty()){
            int node = queue.poll();

            List<Edge> adjEdges = tree[node];
            for (Edge edge : adjEdges) {
                int nextD = edge.d;
                if (!visited[nextD]) {
                    visited[nextD] = true;
                    queue.offer(nextD);
                    dist[nextD] = dist[node] + edge.cost;
                    if (ans < dist[nextD]) {
                        ans = dist[nextD];
                        idx = nextD;
                    }
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
                    "d=" + d +
                    ", cost=" + cost +
                    '}';
        }
    }
}

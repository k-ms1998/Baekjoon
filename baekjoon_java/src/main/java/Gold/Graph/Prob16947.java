package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(서울 지하철 2호선)
 *
 * https://www.acmicpc.net/problem/16947
 *
 * Solution: DFS + BFS
 * 1. 순환선을 구하기 위해서 싸이클을 구한다. (DFS)
 * 2. 싸이클에 속한 역들로부터 연결된 역들까지의 최단 거리를 구한다. (BFS)
 */
public class Prob16947 {

    static int n;
    static List<Edge>[] edges;
    static int[] dist;
    static boolean[] visited;
    static Deque<Edge> queue = new ArrayDeque<>();
    static final int INF = 100000000;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        edges = new List[n + 1];
        dist = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
            dist[i] = INF;
        }
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edges[a].add(new Edge(b, 0));
            edges[b].add(new Edge(a, 0));
        }

        for (int i = 1; i < n + 1; i++) {
            visited = new boolean[n + 1];
            if (findCycle(i, 0, i)) {
                break;
            }
        }
        findDist();

        for (int i = 1; i < n + 1; i++) {
            ans.append(dist[i]).append(" ");
        }
        System.out.println(ans);
    }

    public static boolean findCycle(int cur, int prev, int start) {
        if (cur == start && prev != 0) {
            queue.offer(new Edge(cur, 0));
            dist[cur] = 0;
            return true;
        }

        if (visited[cur]) {
            return false;
        }
        visited[cur] = true;
        List<Edge> adjEdges = edges[cur];
        for (Edge edge : adjEdges) {
            if (edge.d != prev) {
                if (findCycle(edge.d, cur, start)) {
                    queue.offer(new Edge(cur, 0));
                    dist[cur] = 0;
                    return true;
                }
            }
        }

        return false;
    }
    public static void findDist(){
        while(!queue.isEmpty()){
            Edge curEdge = queue.poll();
            int d = curEdge.d;
            int curDist = curEdge.curDist;

            List<Edge> adjEdges = edges[d];
            for (Edge edge : adjEdges) {
                if (dist[edge.d] > curDist + 1) {
                    dist[edge.d] = curDist + 1;
                    queue.offer(new Edge(edge.d, curDist + 1));
                }
            }
        }
    }

    public static class Edge{
        int d;
        int curDist;

        public Edge(int d, int curDist){
            this.d = d;
            this.curDist = curDist;
        }
    }
}
package Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 3(사회망 서비스(SNS))
 *
 * https://www.acmicpc.net/problem/2533
 *
 * Solution: DP + DFS
 */
public class Prob2533 {

    static int n;
    static Edge[] edges;

    static int[][] dp;
    static boolean[] visited;

    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        edges = new Edge[n + 1];
        dp = new int[n + 1][2];
        visited = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            edges[i] = new Edge(i, new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            edges[u].adj.add(v);
            edges[v].adj.add(u);
        }

        initDp();
        dfs(1);
        System.out.println(Math.min(dp[1][0], dp[1][1]));
//        printDp();
    }

    public static void initDp() {
        for (int i = 1; i < n + 1; i++) {
            dp[i][0] = 0;
            dp[i][1] = 1;
        }
    }

    public static void dfs(int cur) {
        visited[cur] = true;

        for (Integer next : edges[cur].adj) {
            if(visited[next]){
                continue;
            }
            dfs(next);
            dp[cur][0] += dp[next][1];
            dp[cur][1] += Math.min(dp[next][0], dp[next][1]);
        }

    }
    public static void findAns() {
        for (int i = 0; i < n + 1; i++) {
            ans = Math.min(ans, Math.min(dp[i][0], dp[i][1]));
        }
    }


    public static void printDp() {
        for (int i = 0; i < n + 1; i++) {
            System.out.println("i = " + i + ", : [ " +  dp[i][0] + " , " + dp[i][1] + " ]");
        }
        System.out.println("===============================");
    }

    static class Edge{
        int num;
        List<Integer> adj;

        public Edge(int num, List<Integer> adj) {
            this.num = num;
            this.adj = adj;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "num=" + num +
                    ", adj=" + adj +
                    '}';
        }
    }
}

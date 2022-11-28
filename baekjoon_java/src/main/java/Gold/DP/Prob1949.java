package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 2(우수 마을)
 *
 * https://www.acmicpc.net/problem/1949
 *
 * Solution: DP + Tree
 */
public class Prob1949 {

    static int n;
    static int[] population;
    static List<Integer>[] edges;

    static int[][] dp;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        population = new int[n + 1];
        edges = new List[n + 1];
        dp = new int[n + 1][2];
        visited = new boolean[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            population[i] = Integer.parseInt(st.nextToken());
            edges[i] = new ArrayList<>();
            dp[i][1] = population[i];
        }


        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edges[a].add(b);
            edges[b].add(a);
        }

        dfs(1);
        /**
         * 1을 루트로 하는 트리에서, 리프 노드에서 루트까지 올라오는 트리로 주민의 최대를 계산 했기 때문에,
         * 주민의 최댓값은 dp[1][0] 또는 dp[1][1]에 저장되어 있음
         */
        System.out.println(Math.max(dp[1][0], dp[1][1]));
    }

    /**
     * dp[node][0] -> node를 추가하지 않았을때의 최댓값
     * dp[node][1] -> node를 추가했을때의 최댓값
     *
     * 임의로 아무 노드로 부터 시작해서, 트리인것처럼 인접한 노드들로 이동
     * 이때, 리프노드까지 도달한 후, 위로 올라가면서 최대 주민의 수 계산
     */
    public static void dfs(int node) {
        visited[node] = true;

        List<Integer> adjEdges = edges[node];
        for (Integer adj : adjEdges) {
            /**
             * 마을들은 모두 양방향으로 연결되어 있는데, 직전에 방문한 마을은 다시 방문할 필요가 없기 때문에 continue
             */
            if (visited[adj]) {
                continue;
            }
            dfs(adj);
            /**
             * dp[node][0] -> node 를 추가하지 않기 때문에, adj 가 추가되어 있어도, 추가되어 있지 않아도 상관 X
             *  -> 그러므로, dp[adj][0] 이랑 dp[adj][1] 중에서 더 큰 값을 더해줌
             * dp[node][1] -> node 를 추가하기 때문에, 무조건 adj 는 추가 되어 있지 않아야 되기 때문에 dp[adj][0] 추가
             */
            dp[node][0] += Math.max(dp[adj][0], dp[adj][1]);
            dp[node][1] += dp[adj][0];
        }
//        System.out.println("dp["+node+"][0] = " + dp[node][0] + ", dp["+node+"][1] = " + dp[node][1]);
    }
}
package Gold.Graph.TopologicalSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 3 (ACM Craft)
 *
 * https://www.acmicpc.net/problem/1005
 *
 * Solution: Topological Sort + Dp
 */
public class Prob1005 {

    static int w;
    static int[] cost;
    static List<Integer>[] jobs;

    static int[] inDegreeCount;
    static Deque<Integer> queue;
    static int[] dp;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int[] cost = new int[n + 1];
            jobs = new List[n + 1];
            inDegreeCount = new int[n + 1];
            dp = new int[n + 1];
            for (int i = 1; i < n + 1; i++) {
                cost[i] = Integer.parseInt(st.nextToken());
                jobs[i] = new ArrayList<>();
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                jobs[a].add(b);
                inDegreeCount[b]++;
            }

            w = Integer.parseInt(br.readLine());

            /**
             * Topological Sort
             */
            queue = new ArrayDeque<>();
            for (int i = 1; i < n + 1; i++) {
                if (inDegreeCount[i] == 0) {
                    queue.offer(i);
                    dp[i] = cost[i];
                }
            }

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                if (cur == w) {
                    /**
                     * 현재 노드의 W 이면, 더 이상 탐색할 필요 X
                     */
                    continue;
                }

                /**
                 * job 는 cur 이후에 완료되는 작업(즉, cur 는 job 이전에 완료되는 작업)
                 * dp[job] 에는 job 을 수행하고 그 다음 작업으로 넘어갈 수 있는 시간 저장
                 * job 이전에 수행해야되는 작업들이 모두 완료되어야 job 을 수행할 수 있으므로,
                 * 이전에 수행한 작업들 중 완료시간이 가장 나중인 값 + job 을 수행하는데 걸리는 시간을 dp[job]에 저장
                 *  -> dp[job] = Math.max(dp[job], dp[cur] + cost[job]);
                 */
                for (Integer job : jobs[cur]) {
                    inDegreeCount[job]--;
                    dp[job] = Math.max(dp[job], dp[cur] + cost[job]);
                    if (inDegreeCount[job] == 0) {
                        queue.offer(job);
                    }
                }
            }
            ans.append(dp[w]).append("\n");

        }
        System.out.println(ans);
    }
}

package Gold.Graph.TopologicalSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/2056
 *
 * Solution: Topological
 */
public class Prob2056_topological {

    static int n;
    static List<Integer>[] jobs;

    static int[] inDegreeCount;
    static int[] jobInfo;
    static int[] dp;

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        jobs = new List[n + 1];
        jobInfo = new int[n + 1];
        dp = new int[n + 1];
        inDegreeCount = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            jobs[i] = new ArrayList<>();
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());

            int jobCost = Integer.parseInt(st.nextToken());
            jobInfo[i] = jobCost;
            dp[i] = jobCost;

            int cnt = Integer.parseInt(st.nextToken());
            inDegreeCount[i] = cnt;
            for (int j = 0; j < cnt; j++) {
                int s = Integer.parseInt(st.nextToken());
                jobs[s].add(i);
            }

            if (cnt == 0) {
                queue.offer(i);
            }
        }

        /**
         * Topological Sort
         */
        while (!queue.isEmpty()) {
            int curJob = queue.poll();

            List<Integer> nextJobs = jobs[curJob];
            for (Integer nextJob : nextJobs) {
                /**
                 * 작업 3을 하기 전에 작업 1, 2를 완료해야되는 경우:
                 * 작업 1이 시간 10에 끝나고, 작업 2가 시간 15에 끝나고, 작업 3을 수행하는데 시간이 5가 걸릴때,
                 *  -> 작업을 끝내는 시간은 20(15 + 5) 가 됨
                 *      -> Because, 작업 3을 시작하기 전에 작업 1과 2 모두 끝난 시점이어야 하는데, 그 시점이 시간 15일때이기 때문에
                 */
                dp[nextJob] = Math.max(dp[nextJob], dp[curJob] + jobInfo[nextJob]);
                inDegreeCount[nextJob]--;
                if (inDegreeCount[nextJob] == 0) {
                    queue.offer(nextJob);
                }
            }

        }

        for (int i = 1; i < n + 1; i++) {
            ans = Math.max(ans, dp[i]);
        }
        System.out.println(ans);
    }

}

package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 3(게임 개발)
 *
 * https://www.acmicpc.net/problem/1516
 *
 * Solution: Topological Sort (위상 정렬)
 */
public class Prob1516 {

    static int n;
    static List<Integer>[] edges;
    static int[] costs;
    static int[] inCount;
    static int[] finished;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        edges = new List[n + 1];
        costs = new int[n + 1];
        inCount = new int[n + 1];
        finished = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            costs[i] = cost;
            finished[i] = cost;
            while (true) {
                int num = Integer.parseInt(st.nextToken());
                if (num == -1) {
                    break;
                }
                edges[num].add(i);
                inCount[i]++;
            }
        }
        topologicalSort();
        for (int i = 1; i < n + 1; i++) {
            ans.append(finished[i]).append("\n");
        }
        System.out.println(ans);
    }

    /**
     * 하나의 빌딩을 짓기 전에 미리 완공한 빌딩들이 필수로 있음
     * -> 빌딩을 짓는 순서가 정해져 있음 => 위상 정렬
     * 이때, 하나의 빌딩을 짓기 완료한 시간 = 자기 자신을 짓는데 걸리는 시간 + 앞서 지어야 되는 빌딩들이 다 지어지는데 걸리는 최대 시간
     */
    public static void topologicalSort() {
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i < n + 1; i++) {
            if (inCount[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            List<Integer> adjEdges = edges[cur];
            for (Integer adj : adjEdges) {
                inCount[adj]--;
                finished[adj] = Math.max(finished[adj], costs[adj] + finished[cur]);
                if (inCount[adj] == 0) {
                    queue.offer(adj);
                }
            }
        }
    }
}

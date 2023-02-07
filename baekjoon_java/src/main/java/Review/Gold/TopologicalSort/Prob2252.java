package Review.Gold.TopologicalSort;

import java.io.*;
import java.util.*;

/**
 * Gold 3(줄 세우기)
 *
 * https://www.acmicpc.net/problem/2252
 *
 * Solution: Topological Sort
 * 단순 위상 정렬 문제
 */
public class Prob2252 {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Integer>[] edges = new List[n + 1];
        int[] inCount = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            /*
            a -> b
             */
            edges[a].add(b);
            inCount[b]++;
        }

        StringBuilder ans = new StringBuilder();
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i < n + 1; i++) {
            if (inCount[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            ans.append(cur).append(" ");

            for (int adj : edges[cur]) {
                inCount[adj]--;
                if (inCount[adj] == 0) {
                    queue.offer(adj);
                }
            }
        }

        System.out.println(ans);
    }
}

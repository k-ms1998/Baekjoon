package Silver;

import java.io.*;
import java.util.*;
import java.util.Map.*;

/**
 * Sivler 1
 */
public class Prob1325 {

    public void solve() throws IOException {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        Map<Integer, List<Integer>> edges = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            List<Integer> edge = new ArrayList();
            if (edges.containsKey(b)) {
                edge = edges.get(b);
            }
            edge.add(a);
            edges.put(b, edge);
        }

        int max = 0;
        Integer[] hackableCnt = new Integer[n + 1];
        for (int i = 1; i <= n; i++) {
            Boolean[] visited = new Boolean[n+1];
            for(int j = 1; j <= n; j++){
                visited[j] = false;
            }
//            System.out.println("dsf(edges, " + i + ") = " + dfs(edges, visited, i, 0));
            int cnt = dfs(edges, visited, i, 0);
            if (cnt > max) {
                max = cnt;
            }
            hackableCnt[i] = cnt;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (hackableCnt[i] == max) {
                sb.append(i + " ");
            }
        }
        System.out.println(sb);
    }

    private static int dfs(Map<Integer, List<Integer>> edges, Boolean[] visited, int src, int cnt) {
        visited[src] = true;
        if (edges.containsKey(src)) {
            List<Integer> edge = edges.get(src);
            for (Integer e : edge) {
                if (!visited[e]) {
                    cnt = dfs(edges, visited, e, cnt + 1);
                }
            }
        }

        return cnt;
    }
}

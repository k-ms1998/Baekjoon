package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5:
 *
 * https://www.acmicpc.net/problem/13023
 *
 * Solution: DFS
 */
public class Prob13023 {

    static int n;
    static int m;
    static Boolean[] visited;
    static Map<Integer, List<Integer>> edges = new HashMap<>();
    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        visited = new Boolean[n];
        for (int i = 0; i < n; i++) {
            visited[i] = false;
            edges.put(i, new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());


            List<Integer> tmpEdgesA = edges.get(a);
            tmpEdgesA.add(b);
            edges.put(a, tmpEdgesA);

            List<Integer> tmpEdgesB = edges.get(b);
            tmpEdgesB.add(a);
            edges.put(b, tmpEdgesB);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                visited[j] = false;
            }
            dfs(i, 1);
            if (ans == 1) {
                break;
            }
        }

        System.out.println(ans);
    }

    static void dfs(int cur, int len) {
        if (len == 5) {
            ans = 1;
            return;
        }


        List<Integer> adjEdges = edges.get(cur);
        for (Integer adj : adjEdges) {
            if(visited[adj]){
                continue;
            }
            visited[adj] = true;
            dfs(adj, len + 1);
            /**
             * !! 다시 false 로 해줌으로써, 모든 경로를 탐색할 수 있게 끔 만들어줌 !!
             */
            visited[adj] = false;
        }

    }
}

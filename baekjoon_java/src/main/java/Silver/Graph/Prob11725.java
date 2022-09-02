package Silver.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Silver 2
 *
 * https://www.acmicpc.net/problem/11725
 *
 * Solution: BFS
 */
public class Prob11725 {

    static int n;
    static List<Integer>[] tree;
    static int[] parent;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        tree = new List[n + 1];
        parent = new int[n + 1];
        visited = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            tree[s].add(v);
            tree[v].add(s);
        }

        calculateParent();

        StringBuilder ans = new StringBuilder();
        for (int i = 2; i < n + 1; i++) {
            ans.append(parent[i]);
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static void calculateParent() {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(1);

        visited[1] = true;
        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int next : tree[node]) {
                if(!visited[next]){
                    visited[next] = true;
                    parent[next] = node;
                    queue.offer(next);
                }
            }
        }

    }
}

package Gold.Tree;

import java.io.*;
import java.util.*;

/**
 * Gold 4(가장 가까운 공통 조상)
 *
 * https://www.acmicpc.net/problem/3584
 *
 * Solution: DFS
 */
public class Prob3584 {

    static int[] parent;
    static boolean[] visited;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());

            parent = new int[n + 1];
            for (int i = 1; i < n + 1; i++) {
                parent[i] = i;
            }
            for (int i = 0; i < n - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                parent[b] = a;
            }

            visited = new boolean[n + 1];
            st = new StringTokenizer(br.readLine());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());
            /**
             * 1. 하나의 노드가 루트까지 갈때 거쳐가는 모든 노드들 확인
             * 2. 다른 노드가 루트까지 갈때, 다른 노드가 방문한 첫번째 노드가 나오면 멈춤 -> 해당 노드가 가장 가까운 공통 조상
             */
            findVisited(nodeA);
            findAnswer(nodeB);
        }

        System.out.println(ans);
    }

    public static void findVisited(int node) {
        visited[node] = true;
        if (parent[node] == node) {
            return;
        }

        findVisited(parent[node]);
    }

    public static void findAnswer(int node) {
        if (visited[node]) {
            ans.append(node).append("\n");
            return;
        }

        findAnswer(parent[node]);
    }

}


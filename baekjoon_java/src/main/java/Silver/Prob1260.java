package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Silver 2 : DFS & BFS
 * 문제
 * 그래프를 DFS로 탐색한 결과와 BFS로 탐색한 결과를 출력하는 프로그램을 작성하시오.
 * 단, 방문할 수 있는 정점이 여러 개인 경우에는 정점 번호가 작은 것을 먼저 방문하고, 더 이상 방문할 수 있는 점이 없는 경우 종료한다. 정점 번호는 1번부터 N번까지이다.
 *
 * 입력
 * 첫째 줄에 정점의 개수 N(1 ≤ N ≤ 1,000), 간선의 개수 M(1 ≤ M ≤ 10,000), 탐색을 시작할 정점의 번호 V가 주어진다.
 * 다음 M개의 줄에는 간선이 연결하는 두 정점의 번호가 주어진다. 어떤 두 정점 사이에 여러 개의 간선이 있을 수 있다. 입력으로 주어지는 간선은 양방향이다.
 *
 * 출력
 * 첫째 줄에 DFS를 수행한 결과를, 그 다음 줄에는 BFS를 수행한 결과를 출력한다. V부터 방문된 점을 순서대로 출력하면 된다.
 */
public class Prob1260 {

    public static Map<Integer, List<Integer>> edges = new HashMap<>();
    public static int v;
    public static int e;
    public static int s;
    public static boolean[] visited;
    public static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] conditions = br.readLine().split(" ");
        v = Integer.valueOf(conditions[0]);
        e = Integer.valueOf(conditions[1]);
        s = Integer.valueOf(conditions[2]);

        for (int i = 0; i < v; i++) {
            edges.put(i+1, new ArrayList<>());
        }

        for (int i = 0; i < e; i++) {
            String[] edge = br.readLine().split(" ");
            int src = Integer.valueOf(edge[0]);
            int dst = Integer.valueOf(edge[1]);

            List<Integer> valueSrc = edges.get(src);
            valueSrc.add(dst);
            edges.put(src, valueSrc);

            List<Integer> valueDst = edges.get(dst);
            valueDst.add(src);
            edges.put(dst, valueDst);
        }
        for (int i = 1; i < v + 1; i++) {
            /**
             * 인접 노드들을 노드의 숫자가 작은 순서대로 정렬
             */
            Collections.sort(edges.get(i));
        }

        visited = new boolean[v+1];
        for(int i = 0; i < v + 1; i++){
            visited[i] = false;
        }
        ans = new StringBuilder();
        dfs(visited, s);
        System.out.println(ans);

        for(int i = 0; i < v + 1; i++){
            visited[i] = false;
        }
        ans = new StringBuilder();
        bfs(visited, s);
        System.out.println(ans);
    }

    public static void dfs(boolean[] visited, int cur) {
        if (visited[cur] == true) {
            return;
        }

        ans.append(cur + " ");
        visited[cur] = true;
        List<Integer> adj = edges.get(cur);
        for (Integer d : adj) {
            dfs(visited, d);
        }
    }

    private static void bfs(boolean[] visited, int cur) {
        Deque<Integer> nextNodes = new ArrayDeque<>();
        nextNodes.offer(cur);

        while (!nextNodes.isEmpty()) {
            int nextNode = nextNodes.poll();
            if (visited[nextNode] == true) {
                continue;
            }
            visited[nextNode] = true;
            ans.append(nextNode + " ");
            List<Integer> adj = edges.get(nextNode);
            for (Integer d : adj) {
                if (visited[d] == false) {
                    nextNodes.offer(d);
                }
            }
        }
    }
}
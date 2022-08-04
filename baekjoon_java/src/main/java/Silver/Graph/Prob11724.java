package Silver.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Silver 2
 *
 * 문제
 * 방향 없는 그래프가 주어졌을 때, 연결 요소 (Connected Component)의 개수를 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 정점의 개수 N과 간선의 개수 M이 주어진다. (1 ≤ N ≤ 1,000, 0 ≤ M ≤ N×(N-1)/2)
 * 둘째 줄부터 M개의 줄에 간선의 양 끝점 u와 v가 주어진다. (1 ≤ u, v ≤ N, u ≠ v) 같은 간선은 한 번만 주어진다.
 *
 * 출력
 * 첫째 줄에 연결 요소의 개수를 출력한다.
 *
 */
public class Prob11724 {

    static Integer[] parent;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parent = new Integer[n + 1];
        visited = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            parent[i] = i;
            visited[i] = false;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            /**
             * 각 노드의 최상단 Parent 찾기
             */
            int parentU = findParent(u);
            int parentV = findParent(v);

            /**
             * Union
             */
            if (parentU < parentV) {
                parent[parentU] = parentV;
            } else {
                parent[parentV] = parentU;
            }
        }
        
        int cnt = 0;
        for (int i = 1; i < n + 1; i++) {
            /**
             * 각 노드의 최상단 Parent 찾기
             * 최상단 Parent 의 수 == Tree 들의 수
             */
            int cur = findParent(i);
            if (!visited[cur]) {
                visited[cur] = true;
                cnt++;
            }
        }
        System.out.println(cnt);
    }

    public static int findParent(int n) {
        if (parent[n] == n) {
            return n;
        }
        int curParent = findParent(parent[n]);
        parent[n] = curParent;
        return curParent;
    }
}
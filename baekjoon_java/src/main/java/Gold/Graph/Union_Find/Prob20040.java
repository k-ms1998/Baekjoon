package Gold.Graph.Union_Find;

import java.io.*;
import java.util.*;

/**
 * Gold 4 (사이클 게임)
 *
 * https://www.acmicpc.net/problem/20040
 *
 * Solution: Union-Fid
 */
public class Prob20040 {

    static int n;
    static int m;

    static int[] parent;

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int i = 1; i < m + 1; i++) {
            st = new StringTokenizer(br.readLine());

            /**
             * ans != 0 -> 이미 사이클 존재
             *  => 그러므로, 더 이상 확인할 필요 X
             */
            if (ans != 0) {
                continue;
            }

            /**
             * 입력을 받고, 각 노드의 최상단 부모를 찾아서 사이클인지 아닌지 확인
             * p1 과 p2 의 최상단 부모(root)가 같으면, 사이클 존재
             * ex:
             * 0 1
             * 1 2
             * 2 3
             * 5 4  -> 0 - 1 - 2 - 3 && 4 - 5 가 연결된 상태 => 0, 1, 2, 3의 root == 3, 4, 5의 root == 4
             * 0 4  -> 0 이랑 4 연결 -> 연결 전에는 서로의 root 가 다르기 때문에 사이클 X
             *
             * ex:
             * 0 1
             * 1 2
             * 1 3  -> 0 - 1 - 2 - 3 연결된 상태 => 0, 1, 2, 3의 root == 3
             * 0 3  -> 0이랑 3 연결 -> 연결 전에 서로의 root 가 같음 => 사이클 O
             * 4 5
             */
            int p1 = Integer.parseInt(st.nextToken());
            int p2 = Integer.parseInt(st.nextToken());

            int root1 = findParent(p1);
            int root2 = findParent(p2);

            /**
             * 사이클인지 판별
             */
            if (root1 == root2) {
                ans = i;
            }

            /**
             * 최상단 부모 업데이트
             */
            parent[root1] = root2;
        }

        System.out.println(ans);
    }

    public static int findParent(int node) {
        if (parent[node] == node) {
            return node;
        }

        int nextParent = findParent(parent[node]);
        parent[node] = nextParent;
        return nextParent;
    }
}

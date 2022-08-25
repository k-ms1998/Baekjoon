package Gold.Graph.Union_Find;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/1717
 *
 * Solution: Union-Find
 * Classic Union-Find Problem
 * 1. 자기 자신의 부모를 저장하는 parent 선언 & 초기화 (초기에 자기 자신의 parent 는 자기 자신으로 설정)
 * 2. command 가 0인지 1인지에 따라서 로직 실행
 *
7 8
0 1 3
1 1 7
0 7 6
1 7 1
0 3 7
0 4 2
0 1 1
1 1 1
 */
public class Prob1717 {

    static int n;
    static int m;
    static int[] parent;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            parent[i] = i;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());

            if (command == 0) {
                union(nodeA, nodeB);
            } else {
                int rootA = findParent(nodeA);
                int rootB = findParent(nodeB);

                if (rootA == rootB) {
                    ans.append("YES");
                } else {
                    ans.append("NO");
                }
                ans.append("\n");
            }
        }

        System.out.println(ans);
    }

    public static int findParent(int c) {
        int cParent = parent[c];
        if (cParent == c) {
            return c;
        }

        /**
         * 실시간으로 parent 를 업데이트 시켜서 시간 절약(이렇게 하지 않으면 시간초과 발생)
         */
        parent[c] = findParent(cParent);
        return parent[c];
    }

    public static void union(int nodeA, int nodeB) {
        int rootA = findParent(nodeA);
        int rootB = findParent(nodeB);

        /**
         * 서로 최상위 부모가 다를때만 합침으로써 시간 절약
         */
        if(rootA != rootB){
            /**
             * 이때, rootB 가 아닌 nodeB 의 parent 를 rootA 로 업데이트 시켜주면 오답
             * Because, nodeB 의 parent 를 업데이트 시키면 parent[nodeB] == rootA, parent[rootB] == rootB
             * 그러므로, nodeB 는 rootA 연결되지만, rootD 는 rootA 랑 연결 X => 오답 발생
             * But, rootB 의 parent 를 업데이트 시키면, parent[rootB] = rootA, parent[nodeB] = rootB
             * => 그러므로, nodeB rootB 와 연결 && rootB 는 rootA 랑 연결 => nodeB -> rootB -> rootA 모두 연결됨 => 정답 O
             */
            parent[rootB] = rootA;
        }
    }
}

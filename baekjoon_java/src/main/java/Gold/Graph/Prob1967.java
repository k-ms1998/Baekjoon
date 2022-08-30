package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/1967
 *
 * Solution: Graph
 */
public class Prob1967 {

    static int n;

    /**
     * isNotLeaf: 리프 노드이면 false, 리프 노드이면 true
     * dist: 루트 노두(1)에서 모든 노드까지의 거리
     * parentArr: 각 노드의 부모 저장
     */
    static boolean[] isNotLeaf;
    static int[] dist;
    static int[] parentArr;
    static boolean[] visited;

    static int ans = 0;
    static int idx = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        isNotLeaf = new boolean[n + 1];
        dist = new int[n + 1];
        parentArr = new int[n + 1];

        parentArr[1] = 1;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            isNotLeaf[parent] = true;
            dist[child] = dist[parent] + cost;
            parentArr[child] = parent;

            /**
             * 루트에서 거리가 가장 긴 값 저장 & 해당 노드가 몇번째 노드인지 저장
             */
            if (ans < dist[child]) {
                ans = dist[child];
                idx = child;
            }
        }

        /**
         * 루트에서 가장 지름이 긴 노드까지 가는데 거치는 노드들 찾기
         */
         visited = new boolean[n + 1];
         visited[1] = true;
         visitParents(idx);
         int idxDist = dist[idx];
        /**
         * 가장 긴 지름은 Leaf Node 들 간의 거리가 될 것
         * 이때, 하나의 Leaf Node 는 루트로 부터 가장 먼 idx 노드가 될덧
         * 그러므로, 가장 긴 지름은 idx 노드에서 가장 멀리 떨어져 있는 노드
         * (모든 루프 노드 간의 거리를 구해봐도 되지만, 시간이 너무 오래 걸림 O(n^2))
         */
        for(int j = 1; j < n + 1; j++){
            /**
             * idx 노드에서 가장 멀리 떨어져 있는 leafNode 구하기 & 지름 구하기
             */
             if (isNotLeaf[j] || j == idx) {
                 continue;
             }

             /**
              * 노드 idx 와 노드 j의 공통 부모 찾기
              */
             int commonParent = findCommonParent(j);
             /**
              * 지름 = 루트에서 idx 까지의 거리 + 루트에서 j 까지의 거리 - 2*루트에서 공동 부모까지의 거리
              */
             int curDist = idxDist + dist[j] - 2 * dist[commonParent];

             ans = Math.max(ans, curDist);
         }

        System.out.println(ans);
    }

    public static void visitParents(int node) {
        if (node != 1) {
            visited[node] = true;
            visitParents(parentArr[node]);
        }
    }

    public static int findCommonParent(int node) {
        if (visited[node] == true) {
            return node;
        }

        return findCommonParent(parentArr[node]);
    }
}

package Gold;

import java.io.*;
import java.util.*;

/**
 *
 * Gold 4(거짓말)
 * 
 * https://www.acmicpc.net/problem/1043
 *
 * Solution: Union-Find
 * 1. 각 파티마다, 파티에 있는 사람들끼리 Union-Find로 집합하기
 * 2. 이때, 파티에 속한 단 한명이라도 진실을 알고 있으면, 해당 파티/집합에 속한 사람들도 모두 진실을 알고 있음
 * 3. 집합 후, 다시 한번 모든 파티를 다 돌면서 구성원 중 진실을 알고 있는 사람이 있는지 확인
 * 4, 아무도 진실을 모르면 ans 증가
 */
public class Prob1043 {

    static int n, m;
    static boolean[] truth;
    static int[] parent;
    static int[][] party;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        truth = new boolean[n + 1];
        parent = new int[n + 1];
        party = new int[m + 1][51];
        for (int i = 1; i < n + 1; i++) {
            parent[i] = i;
        }

        st = new StringTokenizer(br.readLine());
        int truthCount = Integer.parseInt(st.nextToken());
        for (int i = 0; i < truthCount; i++) {
            int t = Integer.parseInt(st.nextToken());
            truth[t] = true;
        }

        for (int i = 1; i < m + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int peopleCount = Integer.parseInt(st.nextToken());
            if(peopleCount >= 1){
                int prev = Integer.parseInt(st.nextToken());
                party[i][0] = prev;
                int prevRoot = findRoot(prev);
                for (int j = 1; j < peopleCount; j++) {
                    /**
                     * 각 파티 구성원에 대해서 서로 연결 시키기
                     * 단 한명이라도 진실을 알고 있으면, 해당 파티에 있는 모든 사람들이 진실을 알고 있음
                     */
                    int cur = Integer.parseInt(st.nextToken());
                    party[i][j] = cur;

                    int curRoot = findRoot(cur);

                    parent[curRoot] = prevRoot;
                    parent[cur] = prevRoot;
                    if (truth[prevRoot] || truth[curRoot]) {
                        truth[prevRoot] = true;
                        truth[curRoot] = true;
                    }
                }
            }
        }

        int ans = 0;
        for (int i = 1; i < m + 1; i++) {
            for(int j = 0; j < 51; j++){
                if (party[i][j] == 0) {
                    ++ans;
                    break;
                }

                int root = findRoot(party[i][j]);
                if (truth[root]) {
                    break;
                }
            }
        }

        System.out.println(ans);
    }

    public static int findRoot(int node) {
        if (parent[node] == node) {
            return node;
        }

        int nextParent = findRoot(parent[node]);
        parent[node] = nextParent;
        return nextParent;
    }
}

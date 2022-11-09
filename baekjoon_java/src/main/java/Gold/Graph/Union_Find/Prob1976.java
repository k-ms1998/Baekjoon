package Gold.Graph.Union_Find;

import java.io.*;
import java.util.*;

/**
 * Gold 4(여행 가자)
 *
 * https://www.acmicpc.net/problem/1976
 *
 * Solution: Union-Find
 */
public class Prob1976 {

    static int n, m;
    static int[] parents;

    static boolean impossible = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        parents = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            parents[i] = i;
        }
        m = Integer.parseInt(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < n + 1; j++) {
                int num = Integer.parseInt(st.nextToken());
                if (num == 1) {
                    int rootA = findParent(i);
                    int rootB = findParent(j);

                    parents[j] = rootA;
                    parents[rootB] = rootA;
                }
            }
        }

        /**
         * 여행 계획을 순서대로 탐색 했을때,
         * 현재 여행지의 parent 가 직전 여행지의 parent 와 같으면 도달 가능
         */
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int parentS = findParent(s);
        for(int i = 1; i < m; i++){
            int d = Integer.parseInt(st.nextToken());
            int parentD = findParent(d);
            if (parentS != parentD) {
                impossible = true;
                break;
            }

            parentS = parentD;
        }

        System.out.println(impossible ? "NO" : "YES");
    }

    public static int findParent(int node) {
        if (parents[node] == node) {
            return node;
        }

        int parent = findParent(parents[node]);
        parents[node] = parent;
        return parent;
    }
}

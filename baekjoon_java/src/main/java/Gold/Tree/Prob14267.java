package Gold.Tree;

import java.io.*;
import java.util.*;

/**
 * Gold 4(회사 문화 1)
 *
 * https://www.acmicpc.net/problem/14267
 *
 * Solution: Tree
 * 1. 각 노드마다 상사로부터 받은 칭찬 저장
 * 2. 각 노드에서 직속 부하로 이동하면서 본인이 가진 칭찬의 값을 직속 부하의 값에 더하기
 * 3. 첫번째 노드부터 탐색해서 총 받은 칭찬의 수 출력
 */
public class Prob14267 {

    static int n, m;
    static List<Integer>[] edges;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        dp = new int[n + 1];
        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }

        int root = 0;
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            int parent = Integer.parseInt(st.nextToken());
            if (parent == -1) {
                root = i;
            }else{
                edges[parent].add(i);
            }
        }


        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());
            dp[num] += value;
        }

        updateDp(root);

        StringBuilder ans = new StringBuilder();
        for(int i = 1; i < n + 1; i++){
            ans.append(dp[i]).append(" ");
        }
        System.out.println(ans);
    }

    public static void updateDp(int node) {
        for(int next : edges[node]){
            dp[next] += dp[node];
            updateDp(next);
        }
    }
}

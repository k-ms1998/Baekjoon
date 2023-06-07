package Gold.Tree;

import java.io.*;
import java.util.*;

/**
 * Gold 2(뉴스 전하기)
 *
 * https://www.acmicpc.net/problem/1135
 *
 * Solution: 트리 DP
 */
public class Prob1135 {

    static int n;
    static List<Integer>[] edges;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        edges = new List[n + 1];
        dp = new int[n];
        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }

        int root = 0;
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            int parent = Integer.parseInt(st.nextToken());
            if(parent == -1){
                root = i;
            }else{
                edges[parent].add(i);
            }
        }

        findAnswer(root);
        System.out.println(dp[root]);
        for (int i = 0; i < n; i++) {
            System.out.println(i + ":" + dp[i]);
        }
    }

    public static int findAnswer(int node) {

        /*
        tmp에는 현재 node의 각 서브트리에 있는 모든 노드들을 탐색하는데 드는 시간들이 저장
            0
          1   2
            3   4
          이고, node = 2일때, tmp = {0, 0}
               node = 0일때, tmp = {0, 2}
         */
        List<Integer> tmp = new ArrayList<>();
        for (int next : edges[node]) {
            tmp.add(findAnswer(next));
        }

        /*
        tmp가 큰 순서로 정렬
        tmp가 클수록 해당 서브트리의 모든 노드들을 탐색하는데 걸리는 시간이 큼
        그러므로, 해당 서브트리를 먼저 확인해야 총 걸리는 시간을 단축시킬 수 있음
        
        node = 0, tmp = {2, 0} 일때:
        tmp가 2인 서브트리 먼저 탐색 -> 2 + 1(현재 노드에서 tmp=2인 서브트리의 루트 노드에 뉴스를 전달하는데 걸리는 시간)
        그 다음으로 tmp가 0인 서브트리 탐색 -> 0 + 2(현재 노드에서 tmp 2인 서브트리의 루트 노드한테 먼저 뉴스 전한 후에 tmp=0인 서브트리의 루트노드에 뉴스를 전하기 때문에 걸리는 시간 = 2)

        dp[node] = Math.max(2 + 1, 0 + 2)
        dp[node] = 3
         */
        Collections.sort(tmp, Collections.reverseOrder());
        for(int i = 0; i < tmp.size(); i++){
            dp[node] = Math.max(dp[node], (i + 1) + tmp.get(i));
        }

        return dp[node];
    }
}

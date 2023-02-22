package Gold.Graph.Union_Find;

import java.io.*;
import java.util.*;

/**
 * Gold 4(트리)
 *
 * https://www.acmicpc.net/problem/4803
 *
 * Solution: Union-Find
 * 1. 간선을 입력 받을때, Union-Find 로 같은 트리인지 아닌지 판별 후, 합치기
 * 2. 두 정점을 연결 했을때 사이클이 되면, 사이클이라는 것을 알려주는 것이 중요 -> 해당 정점들의 root 를 0으로 설정
 */
public class Prob4803 {

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        int caseCnt = 0;
        while(true){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            if(n == 0 && m == 0){
                break;
            }

            ++caseCnt;
            int[] parent = new int[n + 1];
            for(int i = 1; i < n + 1; i++){
                parent[i] = i;
            }

            /**
             * 두 개의 정점을 입력 받아서, 두 정점을 연결
             * 이때, 두 정점의 root 를 찾음
             * 만약 서로 root 가 다르면, 각각 다른 트리였으므로, 두 정점을 연결 했을때 사이클 X
             * 만약 서로 같은 root 를 가지고 있으면, 두 정점을 연결 했을때 사이클이 됨
             *  => 1 -> 2, 1 -> 3 일때 2랑 3의 root 는 둘 다 1이되고, 2 -> 3 을 연결하면 사이클 발생 
             */
            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                int rootA = findRoot(a, parent);
                int rootB = findRoot(b, parent);

                /**
                 * - root 가 0이면 사이클
                 *  -> 그러므로, rootA 또는 rootB 중 하나라도 0이면, 정점 a랑 b를 연결하면 사이클이 됨
                 * - 정점 a, b가 같은데 root를 가지고 있으면 사이클
                 * 
                 * 사이클이 되면 해당 노드들의 root 를 0으로 업데이트해서, 사이클이라는 것을 알려줌
                 */
                if(rootA != rootB && rootA != 0 && rootB != 0){
                    parent[b] = rootA;
                    parent[rootB] = rootA;
                }else{
                    parent[a] = 0;
                    parent[b] = 0;
                    parent[rootA] = 0;
                    parent[rootB] = 0;
                }
            }

            int rootCnt = 0;
            boolean[] rootVisited = new boolean[n + 1];
            for(int i = 1; i < n + 1; i++){
                int curRoot = findRoot(i, parent);
                /**
                 * 현재 노드의 root를 이미 포함 시켰으면, 다시 포함 시키면 중목 -> 트리 개수 증가 X
                 * 현재 노드의 root가 0이면 사이클 -> 트리 개수 증가 X
                 */
                if(!rootVisited[curRoot] && curRoot != 0){
                    rootVisited[curRoot] = true;
                    rootCnt++;
                }
            }

            ans.append(String.format("Case %d: ", caseCnt));
            if(rootCnt == 0){
                ans.append("No trees.");
            }else if(rootCnt == 1){
                ans.append("There is one tree.");
            }else{
                ans.append(String.format("A forest of %d trees.", rootCnt));
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static int findRoot(int node, int[] parent){
        if(parent[node] == node || node == 0){
            return node;
        }

        parent[node] = findRoot(parent[node], parent);
        return parent[node];
    }

}
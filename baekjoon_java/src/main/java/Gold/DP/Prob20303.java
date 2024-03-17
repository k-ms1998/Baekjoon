package Gold.DP;

import java.io.*;
import java.util.*;

public class Prob20303 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int nodeCount, edgeCount, maxSteal;
    static int[] candy;

    static int[] parent;
    static int[] totalCandy;
    static int[] totalPeople;

    public static void main(String args[]) throws IOException{
        st = new StringTokenizer(br.readLine());

        nodeCount = Integer.parseInt(st.nextToken());
        edgeCount = Integer.parseInt(st.nextToken());
        maxSteal = Integer.parseInt(st.nextToken());

        candy = new int[nodeCount + 1];
        parent = new int[nodeCount + 1];
        totalCandy = new int[nodeCount + 1];
        totalPeople = new int[nodeCount + 1];

        st = new StringTokenizer(br.readLine());
        for(int idx = 1; idx < nodeCount + 1; idx++){
            candy[idx] = Integer.parseInt(st.nextToken());
            parent[idx] = idx;
        }

        for(int edge = 0; edge < edgeCount; edge++){
            st = new StringTokenizer(br.readLine());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());

            int rootA = findRoot(nodeA);
            int rootB = findRoot(nodeB);

            if(rootA != rootB){
                parent[nodeB] = rootA;
                parent[rootB] = rootA;
            }
        }

        Set<Integer> roots = new HashSet<>();
        for(int idx = 1; idx < nodeCount + 1; idx++){
            int root = findRoot(idx);
            totalCandy[root] += candy[idx];
            totalPeople[root]++;
            roots.add(root);
        }


        int[][] dp = new int[roots.size() + 1][maxSteal];
        int idx = 1;
        for(int node : roots){
            int curCandy = totalCandy[node];
            int curPeople = totalPeople[node];
            for(int p = 1; p < maxSteal; p++){
                if(curPeople <= p){
                    dp[idx][p] = Math.max(dp[idx-1][p], dp[idx - 1][p - curPeople] + curCandy);
                }else{
                    dp[idx][p] = dp[idx -1][p];
                }
            }
            idx++;
        }

        System.out.println(dp[roots.size()][maxSteal - 1]);
    }

    public static int findRoot(int node){
        if(parent[node] == node){
            return node;
        }

        return parent[node] = findRoot(parent[node]);
    }

}
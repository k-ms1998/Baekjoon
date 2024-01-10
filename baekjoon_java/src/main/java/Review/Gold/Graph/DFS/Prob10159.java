package Review.Gold.Graph.DFS;

import java.io.*;
import java.util.*;

public class Prob10159 {

    static List<Integer>[] edges;
    static List<Integer>[] edgesR;
    static int[] cnt;
    static boolean[] v;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        edges = new List[n + 1];
        edgesR = new List[n + 1];
        cnt = new int[n + 1];

        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
            edgesR[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edges[a].add(b);
            edgesR[b].add(a);
        }

        StringBuilder ans = new StringBuilder();
        for(int i = 1; i < n + 1; i++){
            v = new boolean[n + 1];
            findRoot(i, i, edges);
            findRoot(i, i, edgesR);
            ans.append(n - cnt[i] - 1).append("\n");
        }

        System.out.println(ans);
    }

    public static void findRoot(int start, int node, List<Integer>[] e){
        if(start != node){
            cnt[start]++;
        }

        for(int next : e[node]){
            if(!v[next]){
                v[next] = true;
                findRoot(start, next, e);
            }
        }
    }
}
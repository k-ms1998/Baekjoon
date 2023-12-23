package Review.Gold.Union_Find;

import java.io.*;
import java.util.*;

public class Prob20040 {

    static int n, m;
    static int[] parent;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[n];
        for(int i = 0; i < n; i++){
            parent[i] = i;
        }

        int answer = 0;
        for(int i = 1; i < m + 1; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int rootA = findRoot(a);
            int rootB = findRoot(b);

            if(rootA == rootB){
                answer = i;
                break;
            }else{
                parent[b] = rootA;
                parent[rootB] = rootA;
            }

        }


        System.out.println(answer);
    }

    public static int findRoot(int node){
        if(parent[node] == node){
            return parent[node];
        }

        int next = findRoot(parent[node]);
        parent[node] = next;
        return parent[node];
    }
}
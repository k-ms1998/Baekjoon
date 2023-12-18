package Review.Gold.Floyd;

import java.io.*;
import java.util.*;

public class Prob1613 {

    static int n, k;
    static int[][] dist;

    static final int INF = 1_000_000_000;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dist = new int[n + 1][n + 1];
        for(int i = 0; i < n + 1; i++){
            Arrays.fill(dist[i], INF);
        }

        for(int i = 0; i < k; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            dist[a][b] = 1;
        }
        floyd();

        StringBuilder ans = new StringBuilder();
        int s = Integer.parseInt(br.readLine());
        for(int i = 0; i < s; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(dist[a][b] == INF && dist[b][a] == INF){
                ans.append(0).append("\n");
            } else if (dist[a][b] != INF) {
                ans.append(-1).append("\n");
            } else {
                ans.append(1).append("\n");
            }
        }

        System.out.println(ans);
    }

    public static void floyd(){
        for(int mid = 1; mid < n + 1; mid++){
            for(int start = 1; start < n + 1; start++){
                for(int dest = 1; dest < n + 1; dest++){
                    if(mid == start || start == dest || dest == mid){
                        continue;
                    }
                    dist[start][dest] = Math.min(dist[start][dest], dist[start][mid] + dist[mid][dest]);
                }
            }
        }
    }
}















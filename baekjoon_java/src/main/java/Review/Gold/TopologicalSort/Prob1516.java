package Review.Gold.TopologicalSort;

import java.io.*;
import java.util.*;

public class Prob1516 {

    static int n;
    static int[] time;
    static int[] dist;
    static int[] inCount;
    static List<Integer>[] edges;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        time = new int[n + 1];
        dist = new int[n + 1];
        inCount = new int[n + 1];
        edges = new List[n + 1];

        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }

        for(int i = 1; i < n + 1; i++){
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            time[i] = t;
            while(true){
                int prev = Integer.parseInt(st.nextToken());
                if(prev == -1){
                    break;
                }

                edges[prev].add(i);
                inCount[i]++;
            }
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for(int i = 1; i < n + 1; i++){
            if(inCount[i] == 0){
                queue.offer(i);
                dist[i] = time[i];
            }
        }

        while(!queue.isEmpty()){
            int cur = queue.poll();

            for(int next : edges[cur]){
                dist[next] = Math.max(dist[next], dist[cur]);
                inCount[next]--;
                if(inCount[next] == 0){
                    queue.offer(next);
                    dist[next] += time[next];
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        for(int i = 1; i < n + 1; i++){
            ans.append(dist[i]).append("\n");
        }

        System.out.println(ans);
    }

}

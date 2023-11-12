package Gold.Graph.TopologicalSort;

import java.io.*;
import java.util.*;

/**
 * Gold 1(최종 순위)
 *
 * https://www.acmicpc.net/problem/3665
 *
 * Solution: 위상 정렬(Topological Sort)
 */
public class Prob3665{

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0){
            int n = Integer.parseInt(br.readLine()); // 2 <= n <= 500
            int[] arr = new int[n + 1];
            boolean[][] edges = new boolean[n + 1][n + 1];

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < n; i++){
                arr[i] = Integer.parseInt(st.nextToken());
            }
            for(int i = 0; i < n - 1; i++){
                int a = arr[i];
                for(int j = i + 1; j < n; j++){
                    int b = arr[j];
                    edges[a][b] = true;
                }
            }

            boolean isImpossible = false;
            int m = Integer.parseInt(br.readLine());
            for(int j = 0; j < m; j++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if(edges[a][b]){
                    edges[a][b] = false;
                    edges[b][a] = true;
                }else{
                    edges[a][b] = true;
                    edges[b][a] = false;
                }
            }


            if(isImpossible){
                System.out.println("IMPOSSIBLE");
            }else{
                int[] inCount = new int[n + 1];
                for(int y = 1; y < n + 1; y++){
                    for(int x = 1; x < n + 1; x++){
                        if(edges[y][x]){
                            inCount[x]++;
                        }
                    }
                }

                boolean flag = true;
                for(int y = 1; y < n + 1; y++){
                    if(inCount[y] == 0){
                        flag = false;
                        break;
                    }
                }

                if(flag){
                    System.out.println("IMPOSSIBLE");
                }else{
                    StringBuilder route = new StringBuilder();
                    Deque<Integer> queue = new ArrayDeque<>();
                    for(int i = 1; i < n + 1; i++){
                        if(inCount[i] == 0){
                            queue.offer(i);
                        }
                    }

                    boolean[] visited = new boolean[n + 1];
                    boolean invalid = false;
                    while(!queue.isEmpty()){
                        if(queue.size() > 1){
                            invalid = true;
                            break;
                        }
                        int node = queue.poll();
                        visited[node] = true;
                        route.append(node).append(" ");

                        for(int next = 1; next < n + 1; next++){
                            if(edges[node][next]){
                                inCount[next]--;
                                edges[node][next] = false;
                                if(inCount[next] == 0){
                                    queue.offer(next);
                                }
                            }
                        }
                    }

                    for(int i = 1; i < n + 1; i++){
                        if(!visited[i]){
                            invalid = true;
                            break;
                        }
                    }

                    if(invalid){
                        System.out.println("IMPOSSIBLE");
                    }else{
                        System.out.println(route);
                    }
                }
            }
        }

    }
}

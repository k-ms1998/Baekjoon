package Gold.Graph;

import java.io.*;
import java.util.*;

public class Prob1707 {

    static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int k = Integer.parseInt(br.readLine());
        while (k-- > 0) {
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            List<Integer>[] edges = new List[v + 1];
            int[] visited = new int[v + 1];
            for(int i = 1; i < v + 1; i++){
                edges[i] = new ArrayList<>();
                visited[i] = -1;
            }

            for (int i = 0; i < e; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                edges[a].add(b);
                edges[b].add(a);
            }

            boolean flag = false;
            for(int i = 1; i < v + 1; i++){
                if(flag){
                    break;
                }
                if(visited[i] == -1){
                    Deque<Info> queue = new ArrayDeque<>();
                    queue.offer(new Info(i, 0));
                    visited[i] = 0;
                    while(!queue.isEmpty() && !flag){
                        Info info = queue.poll();
                        int node = info.num;
                        int group = info.group;

                        for(int next : edges[node]){
                            if(visited[next] == -1){
                                visited[next] = (group + 1) % 2;
                                queue.offer(new Info(next, (group + 1) % 2));
                            }else{
                                if(visited[next] == group){
                                    flag = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            answer.append(!flag ? "YES" : "NO").append("\n");
        }

        System.out.println(answer);
    }

    public static class Info{
        int num;
        int group;

        public Info(int num, int group){
            this.num = num;
            this.group = group;
        }
    }

}

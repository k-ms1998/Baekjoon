package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 4(어드밴처 게임)
 *
 * https://www.acmicpc.net/problem/2310
 *
 * Solution: BFS (But, 시간단축을 위해 DFS 시도)
 */
public class Prob2310_bfs {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n == 0) {
                break;
            }

            int[] cost = new int[n + 1];
            List<Integer>[] edges = new List[n + 1];
            int[] dist = new int[n + 1];
            for(int i = 0; i < n + 1; i++){
                edges[i] = new ArrayList<>();
                dist[i] = Integer.MIN_VALUE;
            }
            for (int i = 1; i < n + 1; i++) {
                st = new StringTokenizer(br.readLine());
                String type = st.nextToken();
                int amount = Integer.parseInt(st.nextToken());
                if(type.equals("L")){
                    cost[i] = amount;
                }else if(type.equals("T")){
                    cost[i] = -amount;
                }
                while(true){
                    int adj = Integer.parseInt(st.nextToken());
                    if(adj == 0){
                        break;
                    }
                    edges[i].add(adj);
                }
            }
            edges[0].add(1);

            boolean flag = false;
            Deque<Info> queue = new ArrayDeque<>();
            queue.offer(new Info(0, 0));
            while (!queue.isEmpty()) {
                Info info = queue.poll();
                int node = info.node;
                int c = info.c;

                if (node == n) {
                    flag = true;
                    break;
                }

                for(int adj : edges[node]){
                    if(cost[adj] == 0){
                        if(dist[adj] < c){
                            dist[adj] = c;
                            queue.offer(new Info(adj, c));
                        }
                    } else if (cost[adj] > 0) {
                        int tmpC = c;
                        if (tmpC < cost[adj]) {
                            tmpC = cost[adj];
                        }
                        if(dist[adj] < tmpC){
                            dist[adj] = tmpC;
                            queue.offer(new Info(adj, tmpC));
                        }
                    } else {
                        int tmpC = c + cost[adj];
                        if(tmpC >= 0){
                            if(dist[adj] < tmpC){
                                dist[adj] = tmpC;
                                queue.offer(new Info(adj, tmpC));
                            }
                        }
                    }
                }
            }

            ans.append(flag ? "Yes\n" : "No\n");
        }

        System.out.println(ans);
    }

    public static class Info{
        int node;
        int c;

        public Info(int node, int c) {
            this.node = node;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "node=" + node +
                    ", c=" + c +
                    '}';
        }
    }
}

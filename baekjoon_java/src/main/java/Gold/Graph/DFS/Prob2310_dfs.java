package Gold.Graph.DFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4(어드밴처 게임)
 *
 * https://www.acmicpc.net/problem/2310
 *
 * Solution: DFS
 * DFS: 376ms, BFS: 1048ms
 */
public class Prob2310_dfs {

    static boolean[] visited;
    static List<Integer>[] edges;
    static int[] cost;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n == 0) {
                break;
            }

            visited = new boolean[n + 1];
            edges = new List[n + 1];
            cost = new int[n + 1];
            for(int i = 0; i < n + 1; i++){
                edges[i] = new ArrayList<>();
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

            ans.append(dfs(0, n, 0) ? "Yes\n" : "No\n");
        }

        System.out.println(ans);
    }

    public static boolean dfs(int node, int target, int money) {
        if(money < 0){
            return false;
        }
        if (node == target) {
            return true;
        }

        for(int adj : edges[node]){
            if(!visited[adj]){
                visited[adj] = true;

                boolean flag = dfs(adj, target, cost[adj] > 0 ? Math.max(money, cost[adj]) : money + cost[adj]);
                if(flag){
                    return true;
                }
                visited[adj] = false;
            }
        }

        return false;
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

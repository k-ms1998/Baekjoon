package Gold.Tree.MST;

import java.io.*;
import java.util.*;

/**
 * Gold 4(전력난)
 *
 * https://www.acmicpc.net/problem/6497
 *
 * Solution: MST(Minimum Spanning Tree)
 * 1. 간선을 가중치가 작은 순으로 정렬
 * 2. 각 간선을 추가하면서, 노드끼리 연결이 되어있는지 아닌지 확인
 * 3. 연결 여부는 union-find 로 판별
 */
public class Prob6497 {

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());

            if (m == 0 && n == 0) {
                break;
            }

            int[] parent = new int[m];
            for (int i = 0; i < m; i++) {
                parent[i] = i;
            }
            Edge[] edges = new Edge[n];
            long totalCost = 0L;
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());

                totalCost += z;
                edges[i] = new Edge(x, y, z);
            }
            Arrays.sort(edges, new Comparator<Edge>(){
                @Override
                public int compare(Edge e1, Edge e2) {
                    return e1.cost - e2.cost;
                }
            });

            long minCost = 0L;
            for (Edge edge : edges) {
                int s = edge.s;
                int d = edge.d;
                int cost = edge.cost;

                int rootS = findRoot(parent, s);
                int rootD = findRoot(parent, d);

                if(rootS != rootD){
                    parent[d] = rootS;
                    parent[rootD] = rootS;
                    minCost += cost;
                }
            }
            ans.append((totalCost - minCost)).append("\n");
        }

        System.out.println(ans);
    }


    public static int findRoot(int[] parent, int node) {
        if (parent[node] == node) {
            return node;
        }

        int nextParent = findRoot(parent, parent[node]);
        parent[node] = nextParent;
        return nextParent;
    }

    public static class Edge{
        int s;
        int d;
        int cost;

        public Edge(int s, int d, int cost) {
            this.s = s;
            this.d = d;
            this.cost = cost;
        }
    }
}

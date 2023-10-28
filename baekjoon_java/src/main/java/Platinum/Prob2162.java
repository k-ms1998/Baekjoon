package Platinum;

import java.io.*;
import java.util.*;

/**
 * Platinum 5(선분 그룹)
 *
 * https://www.acmicpc.net/problem/2162
 *
 * Solution: CCW + Union-Find
 */
public class Prob2162 {

    static int n; // 1 <= n <= 3000, 1 <= n^2 <= 9,000,000
    static Edge[] edges;
    static int[] parent;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        edges = new Edge[n];
        parent = new int[n];
        for(int i = 0; i < n; i++){
            parent[i] = i;
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            edges[i] = new Edge(x1, y1, x2, y2);
        }

        for(int i = 0; i < n; i++){
            Edge e1 = edges[i];
            int x1 = e1.x1;
            int y1 = e1.y1; // A
            int x2 = e1.x2;
            int y2 = e1.y2; // B
            for(int j = i + 1; j < n; j++){
                Edge e2 = edges[j];
                int x3 = e2.x1;
                int y3 = e2.y1; // C
                int x4 = e2.x2;
                int y4 = e2.y2; // D

                int resA = ccw(x1, y1, x2, y2, x3, y3); // A->B->C
                int resB = ccw(x1, y1, x2, y2, x4, y4); // A->B->D
                int resC = ccw(x3, y3, x4, y4, x1, y1); // C->D->A
                int resD = ccw(x3, y3, x4, y4, x2, y2); // C->D->B

                int first = resA * resB;
                int second = resC * resD;
                if(first == 0 && second == 0){
                    boolean flag = false;
                    if(Math.min(x1, x2) <= Math.max(x3, x4) && Math.min(x3, x4) <= Math.max(x1, x2)
                            && Math.min(y1, y2) <= Math.max(y3, y4) && Math.min(y3, y4) <= Math.max(y1, y2)) {
                        flag = true;
                    }

                    if(flag){
                        int rootA = findRoot(i);
                        int rootB = findRoot(j);

                        if(rootA != rootB){
                            parent[j] = rootA;
                            parent[rootB] = rootA;
                        }
                    }

                }else{
                    if(first <= 0 && second <= 0){
                        int rootA = findRoot(i);
                        int rootB = findRoot(j);

                        if(rootA != rootB){
                            parent[j] = rootA;
                            parent[rootB] = rootA;
                        }
                    }
                }
            }
        }

        Set<Integer> roots = new HashSet<>();
        int[] cnt = new int[n];
        for(int i = 0; i < n; i++){
            int root = findRoot(i);
            roots.add(root);
            cnt[root]++;
        }

        int maxSize = 0;
        for(int i = 0; i < n; i++){
            maxSize = Math.max(maxSize, cnt[i]);
        }

        StringBuilder ans = new StringBuilder();
        ans.append(roots.size()).append("\n");
        ans.append(maxSize).append("\n");

        System.out.println(ans);
    }

    public static int findRoot(int node){
        if(parent[node] == node){
            return parent[node];
        }

        int next = findRoot(parent[node]);
        parent[node] = next;
        return parent[node];
    }

    public static int ccw(int x1, int y1, int x2, int y2, int x3, int y3){
        long a = (x1*y2) + (x2*y3) + (x3*y1);
        long b = (x2*y1) + (x3*y2) + (x1*y3);

        return a == b ? 0 : a > b ? 1 : -1;
    }

    public static class Edge{
        int x1;
        int y1;
        int x2;
        int y2;

        public Edge(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

    }
}
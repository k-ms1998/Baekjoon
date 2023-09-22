package Gold.Graph.Kruskal;

import java.io.*;
import java.util.*;

/**
 * Gold 3(Watering the Fields)
 *
 * https://www.acmicpc.net/problem/10021
 *
 * Solution: Kruskal + Union-find
 * 1. 모든 좌표들끼리 서로 잇는 간선들을 생성
 * 2. 각 간선들을 정렬
 * 3. 간선들을 순서대로 탐색
 *  -> c보다 큰 값들 중에서 아직 연결되지 않은 노드들이 있으면 간선을 추가(Kruskal + Union-find)
 */
public class Prob10021 {

    static int n, c;
    // 1 <= n <= 2000; 1 <= n^2 <= 4_000_000
    static List<Point> points = new ArrayList<>();
    static List<Edge> edges = new ArrayList<>();
    static int[] parent;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        parent = new int[n];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            points.add(new Point(x, y));
            parent[i] = i;
        }

        for(int i = 0; i < n; i++){
            Point a = points.get(i);
            int ax = a.x;
            int ay = a.y;
            for(int j = i + 1; j < n; j++){
                Point b = points.get(j);
                int bx = b.x;
                int by = b.y;

                int d = (ax - bx) * (ax - bx) + (ay - by) * (ay - by);
                if(d < c){
                    continue;
                }
                edges.add(new Edge(i, j, d));
            }
        }

        Collections.sort(edges, new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.d - e2.d;
            }
        });

        int answer = 0;
        for(Edge e : edges){
            int a = e.a;
            int b = e.b;
            int d = e.d;

            int rootA = findRoot(a);
            int rootB = findRoot(b);
//            System.out.println(rootA + ", " + rootB);
            if(rootA != rootB){
                parent[b] = rootA;
                parent[rootB] = rootA;
                answer += d;
            }
        }

        int flag = 0;
        boolean[] check = new boolean[n];
        for(int i = 0; i < n; i++){
            int root = findRoot(i);
            if(!check[root]){
                flag++;
                check[root] = true;
            }
        }

        System.out.println(flag == 1 ? answer : -1);
    }

    public static int findRoot(int node) {
        if(parent[node] == node){
            return node;
        }

        int next = findRoot(parent[node]);
        parent[node] = next;
        return parent[node];
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static class Edge{
        int a;
        int b;
        int d;

        public Edge(int a, int b, int d){
            this.a = a;
            this.b = b;
            this.d = d;
        }
    }

}

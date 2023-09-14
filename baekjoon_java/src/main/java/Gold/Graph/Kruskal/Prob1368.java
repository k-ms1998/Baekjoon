package Gold.Graph.Kruskal;

import java.io.*;
import java.util.*;

/**
 * Gold 2(물대기)
 *
 * https://www.acmicpc.net/problem/1368
 *
 * Solution: Kruskal + Union
 * 1. 일반 크루스칼 문제랑 같ㅇ,ㅁ
 *  -> 각 간선들을 가중치가 낮은 순으로 정렬 후, 낮은 순으로 확인해서 노드들 연결
 * 2. !! 노드와 노드 끼리 잇는 간선들 + 하나의 노드에 우물을 파는 것도 간선이라고 생각 !!
 *  -> 논은 1~N, 우물은 0번 노드로 생각
 *    -> 각 논을 잇는 간선은 문제에 나와있는대로 간선 생성
 *    -> 우물과 논을 잇는 간선 = 각 노드에 우물을 팔때는 드는 비용을 가중치로 갖는 간선
 *      ex:
 *      4
 *      5
 *      4
 *      4
 *      3
 *      0 2 2 2
 *      2 0 3 3
 *      2 3 0 4
 *      2 3 4 0 -> 우물과 논을 잇는 간선들 = (0, 1, 5), (0, 2, 4), (0, 3, 4), (0, 4, 3)
 */
public class Prob1368 {

    static int n;
    static int[] well;
    static int[][] dist;
    static List<Edge> edges = new ArrayList<>();
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        well = new int[n + 1];
        dist = new int[n + 1][n + 1];
        parent = new int[n + 1];

        for(int i = 1; i < n + 1; i++){
            well[i] = Integer.parseInt(br.readLine());
            parent[i] = i;
        }

        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j < n + 1; j++){
                dist[i][j] = Integer.parseInt(st.nextToken());
                if(i != j){
                    edges.add(new Edge(i, j, dist[i][j]));
                }
            }
        }

        for(int i = 1; i < n + 1; i++){
            edges.add(new Edge(0, i, well[i]));
        }

        Collections.sort(edges, new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.d - e2.d;
            }
        });

        int answer = 0;
        for(Edge e : edges){
            int src = e.src;
            int dst = e.dst;
            int d = e.d;

            int rootSrc = findRoot(src);
            int rootDst = findRoot(dst);

            if(rootSrc != rootDst){
                parent[dst] = rootSrc;
                parent[rootDst] = rootSrc;
                answer += d;
            }
        }

        System.out.println(answer);
    }

    public static int findRoot(int node) {
        if (parent[node] == node) {
            return node;
        }

        int next = findRoot(parent[node]);
        parent[node] = next;
        return next;
    }

    public static class Edge{
        int src;
        int dst;
        int d;

        public Edge(int src, int dst, int d) {
            this.src = src;
            this.dst = dst;
            this.d = d;

        }
    }

}

package Gold.Graph.Kruskal;

import java.io.*;
import java.util.*;

/**
 * Gold 3(학교 탐방하기)
 *
 * https://www.acmicpc.net/problem/13418
 *
 * Solution: Kruskal
 * '그 다음 모든 건물을 방문하는 데 필요한 최소한의 길을 선택하여, 해당 길을 통해서만 건물들을 소개하기로 하였다'
 * => 모든 건물들을 잇는 최소 갯수의 길만 찾으면 된다 => MST(최소 신장 트리) => Kruskal
 *
 * 0. 간선들을 가중치가 큰 순서대로 정렬
 *
 * 1.피로도가 최고로 높은 길
 * = 최악의 경우
 * = 가중치가 큰 순서의 간선들부터 연결
 *
 * 2. 피로도가 최소인 길
 * = 최고의 경우
 * =가중치가 작은 순서의 간선들부터 연결
 *
 * 시간단축:
 * 입구까지 노드리고 했을때, 총 노드의 갯수 = n + 1
 * MST는 무조건 (노드의 갯수 - 1) 만큼의 간선만 필요 -> n 개의 간선 연결
 * -> 그러므로, n개의 간선이 연결되면 탐색 종료
 *  -> 하나의 간선에서 인접한 두 노드에 대해서, 서로 이미 연결되어 있지 않은 간선이면 간선 연결 => edgeCnt++
 */
public class Prob13418 {

    static int n, m;
    static List<Edge> edges = new ArrayList<>();
    static int minC = Integer.MAX_VALUE;
    static int maxC = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for(int i = 0; i < m + 1; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges.add(new Edge(a, b, (c + 1) % 2));

        }

        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return e2.c - e1.c;
            }
        });
        findMax();
        findMin();

        System.out.println(maxC*maxC - minC*minC);

    }

    public static void findMax() {
        int[] parent = new int[n + 1];
        for(int i = 0; i < n + 1; i++){
            parent[i] = i;
        }

        int edgeCnt = 0;
        int cnt = 0;
        for(int i = 0; i < m + 1; i++){
            if(edgeCnt >= n){
                break;
            }
            Edge e = edges.get(i);
            int a = e.a;
            int b = e.b;
            int c = e.c;

            int rootA = findRoot(parent, a);
            int rootB = findRoot(parent, b);
            if (rootA != rootB) {
                edgeCnt++;
                parent[rootB] = rootA;
                parent[b] = rootA;
                cnt += c;
            }
        }

        maxC = cnt;
    }

    public static void findMin() {
        int[] parent = new int[n + 1];
        for(int i = 0; i < n + 1; i++){
            parent[i] = i;
        }

        int edgeCnt = 0;
        int cnt = 0;
        for(int i = m; i >= 0; i--){
            if(edgeCnt >= n){
                break;
            }
            Edge e = edges.get(i);
            int a = e.a;
            int b = e.b;
            int c = e.c;

            int rootA = findRoot(parent, a);
            int rootB = findRoot(parent, b);
            if (rootA != rootB) {
                edgeCnt++;
                parent[rootB] = rootA;
                parent[b] = rootA;
                cnt += c;
            }
        }

        minC = cnt;
    }

    public static int findRoot(int[] parent, int node) {
        if(parent[node] == node){
            return node;
        }

        int next = findRoot(parent, parent[node]);
        parent[node] = next;

        return next;
    }



    public static class Edge{
        int a;
        int b;
        int c;

        public Edge(int a, int b, int c) {
            this. a = a;
            this.b = b;
            this.c = c;
        }
    }

}

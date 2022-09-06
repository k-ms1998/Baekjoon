package Gold.Graph.Kruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/1647
 *
 * Solution: Kruskal(MST)
 * 1. 모든 도시를 잇는 도로들의 유지비 합이 최소 => Kruskal
 * 2. 도시를 두개로 분할 => Kruskal 로 최소 합으로 모든 마을을 연결시키고, 가장 가중치가 큰 값의 도로를 제거
 *                      -> 도시가 두개로 분할되면서 각각 최소의 가중치의 합으로 연결 유지
 */
public class Prob1647 {

    static int n;
    static int m;

    static int[] parent;
    static PriorityQueue<Edge> roads;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            parent[i] = i;
        }

        /**
         * 가중치가 가장 작은 것부터 가져와서 두 개의 도시 연결 시도
         * 1. 간선들을 가중치가 오름차순이 되도록 정렬
         * 2. 가중치가 가장 앞에 있는 Min Heap 으로 간선들 유지(PriorityQueue)
         */
        roads =  new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.c - e2.c;
            }
        });
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            roads.offer(new Edge(a, b, c));
        }
//        System.out.println("roads = " + roads);

        int connectedNode = 1;
        int cnt = 0;
        int lastC = 0;
        while (!roads.isEmpty()) {
            Edge edge = roads.poll();

            int rootA = findParent(edge.a);
            int rootB = findParent(edge.b);

            if (rootA != rootB) {
                /**
                 * 두 도시가 연결되어 있지 않으면 합치기(Union)
                 */
                parent[rootA] = rootB;
                int curC = edge.c;
                cnt += curC;
                lastC = curC;
                connectedNode++;
                /**
                 * 연결된 노드의 수가 전체 노드의 수이면 멈춰서 시간 절약
                 * -> 모든 노드가 연결되어 있으면 다른 간선들을 더 이상 확인할 필요 X
                 * 이때, 무조건 connectedNode 는 1로 초기화
                 * Because, 초기 상태에 간선을 추가하면 연결되는 노드가 하나가 아니라 2개가 되기 때문에
                 */
                if (connectedNode == n) {
                    break;
                }
            }
        }

        System.out.println(cnt - lastC);
    }

    /**
     * 노드의 최상위 부모 찾기
     * @param node
     * @return
     */
    public static int findParent(int node) {
        if (parent[node] == node) {
            return node;
        }

        int tmpParent = findParent(parent[node]);
        parent[node] = tmpParent;
        return tmpParent;
    }

    static class Edge{
        int a;
        int b;
        int c;

        public Edge(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        @Override
        public String toString() {
            return "Edge = { " + a + "->" + b + ": " + c + " }";
        }
    }
}

package Gold.Graph.Kruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4 (별자리 만들기)
 *
 * https://www.acmicpc.net/problem/4386
 *
 * Solution: Kruskal(MST)
 * 1 <= n <= 100 이므로, 이중 for 문으로 모든 노드들을 탐색해서 거리를 구해도 시간이 오래 걸리지 않음
 */
public class Prob4386 {

    static int n;
    static Node[] nodes;

    static PriorityQueue<Edge> edges;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        nodes = new Node[n];
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            /**
             * 노드 입력
             */
            double tmpX = Double.parseDouble(st.nextToken());
            double tmpY = Double.parseDouble(st.nextToken());
            
            nodes[i] = new Node(tmpX, tmpY);
            parent[i] = i;
        }

        /**
         * Kruskal 로 풀기 위해서는 간선들을 가중치가 오름차순으로 정렬 후 모든 간선들을 탐색
         * (Min Heap 을 유지해서 poll() 하는 것도 똑같은 효과)
         */
        edges = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return (int) (e1.cost - e2.cost);
            }
        });

        /**
         * 1 <= n <= 100 이므로, 이중 for 문으로 모든 노드들을 탐색해서 거리를 구해도 시간이 오래 걸리지 않음
         */
        for (int i = 0; i < n; i++) {
            Node nodeA = nodes[i];
            for (int j = i + 1; j < n; j++) {
                Node nodeB = nodes[j];
                double dist = calculateDist(nodeA.x, nodeA.y, nodeB.x, nodeB.y);
                edges.offer(new Edge(i, j, dist));
            }
        }

        /**
         * Kruskal
         * 이때, 연결된 노드의 갯수가 총 노드의 갯수와 동일하면 더 이상 간선들을 탐색하지 않아도 됨(connectedCnt == n)
         *  -> BREAK
         */
        int connectedCnt = 1;
        double ans = 0.0;
        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int nodeA = edge.a;
            int nodeB = edge.b;

            int rootA = findParent(nodeA);
            int rootB = findParent(nodeB);

            if (rootA != rootB) {
                parent[rootA] = rootB;
                ans += edge.cost;
                connectedCnt++;
            }

            if (connectedCnt == n) {
                break;
            }
        }

        System.out.println(String.format("%.2f", ans));
    }

    public static int findParent(int node) {
        if (parent[node] == node) {
            return node;
        }

        int p = findParent(parent[node]);
        parent[node] = p;
        return p;
    }

    public static double calculateDist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    static class Node {
        double x;
        double y;

        public Node( double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Edge{
        int a;
        int b;
        double cost;

        public Edge(int a, int b, double cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }
}

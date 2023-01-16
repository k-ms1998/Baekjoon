package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 2(전기가 부족해)
 *
 * https://www.acmicpc.net/problem/10423
 *
 * Solution: MST
 * 1. 각 건물들을 연결시키는 간선들을 가중치가 작은 순으로 정렬
 * 2. 인접한 두개의 건물 모두 다 전기가 연결되어 있으면 continue
 * 3. 아닐 경우, 각 건물에 대해서 parent 찾아서 업데이트 (Union-Find)
 * 4. 둘 중 하나가 전기에 연결되어 있으면 인접한 다른 건물도 전기에 연결됨을 저장(powerPlants 업데이트)
 */
public class Prob10423 {

    static int n, m, k;
    static boolean[] powerPlants;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            parent[i] = i;
        }

        st = new StringTokenizer(br.readLine());
        powerPlants = new boolean[n + 1];
        for (int i = 1; i < k + 1; i++) {
            int powerPlant = Integer.parseInt(st.nextToken());
            powerPlants[powerPlant] = true;
        }

        PriorityQueue<Edge> edges = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.w - e2.w;
            }
        });
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            edges.offer(new Edge(u, v, w));
        }

        int ans = 0;
        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int u = edge.u;
            int v = edge.v;
            int w = edge.w;

            int rootU = findRoot(u);
            int rootV = findRoot(v);
            if (powerPlants[rootU] && powerPlants[rootV]) {
                continue;
            }

            if (rootV != rootU) {
                if(powerPlants[rootU] || powerPlants[rootV]){
                    powerPlants[rootU] = true;
                    powerPlants[rootV] = true;
                    powerPlants[u] = true;
                    powerPlants[v] = true;
                }
                parent[rootU] = rootV;
                parent[u] = rootV;
                ans += w;
            }
        }

        System.out.println(ans);
    }

    public static int findRoot(int node) {
        if (parent[node] == node) {
            return node;
        }

        int nextNode = findRoot(parent[node]);
        parent[node] = nextNode;
        return nextNode;
    }

    public static class Edge{
        int u;
        int v;
        int w;

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
}

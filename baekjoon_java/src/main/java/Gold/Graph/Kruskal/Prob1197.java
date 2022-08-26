package Gold.Graph.Kruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/1197
 *
 * Solution: Kruskal(MST)
 */
public class Prob1197 {

    static int v;
    static int e;
    static int[] parent;
    static PriorityQueue<Edge> edges;

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        parent = new int[v + 1];
        for (int i = 1; i < v + 1; i++) {
            parent[i] = i;
        }
        /**
         * Edge 들을 가중치가 가장 작은 값으로 정렬
         * (Min Heap으로 유지해서 항상 가장 맨 앞의 값을 pop 하는 것도 동일한 효과)
         */
        edges = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.cost - e2.cost;
            }
        });

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int ts = Integer.parseInt(st.nextToken());
            int td = Integer.parseInt(st.nextToken());
            int tCost = Integer.parseInt(st.nextToken());

            edges.offer(new Edge(ts, td, tCost));
        }

        int cnt = 0;
        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int curS = edge.s;
            int curD = edge.d;

            if (parent[curS] == parent[curD]) {
                continue;
            }

            int rootS = findParent(curS);
            int rootD = findParent(curD);

            /**
             * 두 노드의 최상위 부모가 같으면 이미 연결되어 있으므로 무시
             * 같지 않으면, 연결해야될 대상이므로 업데이트
             * 이때, 가중치가 가장 작은 간서들부터 연결을 시작했기 떄문에 모든 노드들을 연결하는데 최소한의 비용으로 연결할 수 있음
             */
            if (rootS != rootD) {
                /**
                 * 이때, rootD 가 아닌 curD 의 parent 를 rootS 로 업데이트 시켜주면 오답
                 * Because, curD 의 parent 를 업데이트 시키면 parent[curD] == rootS, parent[rootD] == rootD
                 * 그러므로, curD 는 rootS와는 연결되지만, rootD 는 rootS 랑 연결 X => 오답 발생
                 * But, rootD 의 parent 를 업데이트 시키면, parent[rootD] = rootS, parent[curD] = rootD
                 * => 그러므로, curD는 rootD 와 연결 && rootD 는 rootS 랑 연결 => curD -> rootD -> rootS 모두 연결됨 => 정답 O
                 */
                parent[rootD] = rootS;
                ans += edge.cost;
                cnt++;
            }

            if (cnt >= v - 1) {
                break;
            }
        }

        System.out.println(ans);
    }

    /**
     * 최상위 부모찾기
     * @param c
     * @return
     */
    public static int findParent(int c) {
        if (parent[c] == c) {
            return c;
        }

        /**
         * 실시간으로 부모를 업데이트 해줘서, 나중에 다시 최상위 부모를 찾을때 시간 절약
         */
        parent[c] = findParent(parent[c]);
        return parent[c];
    }


    static class Edge{
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

package Gold.Graph.Kruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/1922
 *
 * Solution: Kruskal(MST: 최소 스패닝 트리) + Greedy
 * 1. 간선들을 가중치가 오름차순으 되도록 정렬
 * 2. 간선들을 가중치가 가장 작은 순서대로 확인
 * 3. 노드A 와 노드B 가 이미 연결되어 있는지 확인(서로의 최상위 parent 가 같으면 연결되어 있음)
 */
public class Prob1922 {

    static int n;
    static int m;
    static PriorityQueue<Edge> edges;
    static int[] parent;
    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        parent = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            parent[i] = i;
        }

        /**
         * 1. 간선들을 가중치가 오름차순으 되도록 정렬
         * List: Collections.sort()
         * PriorityQueue: 가중치가 작은 간선들을 순서대로 탐색하기 때문에, 우선순위 큐로 Min Heap 을 유지해서 하나씩 탐색하는 것이 List 를 오름차순 정렬해서 순서대로 탐색하는 것과 같은 효과
         */
        edges = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.cost - e2.cost;
            }
        });
        m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int ts = Integer.parseInt(st.nextToken());
            int td = Integer.parseInt(st.nextToken());
            int tCost = Integer.parseInt(st.nextToken());

            edges.offer(new Edge(ts, td, tCost));
        }

        /**
         * 2. 간선들을 가중치가 가장 작은 순서대로 확인
         */
        int cnt = 0;
        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int curS = edge.s;
            int curD = edge.d;
            int curCost = edge.cost;

            /**
             * 3. curS 와 curD 가 이미 연결되어 있는지 확인
             * 각각의 최상위 parent 를 구해서, 같을 경우(rootS == rootD) 이미 서로 연결되어 있기 때문에 무시
             * 다를 경우(rootS != rootD), 간선을 추가하고, parent 를 업데이트 시켜서 서로 연결 해주기
             * !! 중요: 이때, 서로의 root 의 parent 를 업데이트 시켜줘야됨 !!
             */
            int rootS = findRoot(curS);
            int rootD = findRoot(curD);

            if (rootS != rootD) {
                /**
                 * 이때, rootD 가 아닌 curD 의 parent 를 rootS 로 업데이트 시켜주면 오답
                 * Because, curD 의 parent 를 업데이트 시키면 parent[curD] == rootS, parent[rootD] == rootD
                 * 그러므로, curD 는 rootS와는 연결되지만, rootD 는 rootS 랑 연결 X => 오답 발생
                 * But, rootD 의 parent 를 업데이트 시키면, parent[rootD] = rootS, parent[curD] = rootD
                 * => 그러므로, curD는 rootD 와 연결 && rootD 는 rootS 랑 연결 => curD -> rootD -> rootS 모두 연결됨 => 정답 O
                 */
                parent[rootD] = rootS;
                ans += curCost;
                cnt++;
            }

            /**
             * 시간 단축을 위한 요소:
             * 모든 노드들을 연결할때, 최소 비용이 들기 위해서는 사용되는 간선이 n-1 개 이어야 합니다
             * 그러므로, 이미 사용된 간선이 n-1개이면, 더 이상 나머지 간선들을 탐색하지 않아도 됨 => break
             */
            if (cnt >= n - 1) {
                break;
            }
        }

        System.out.println(ans);

    }

    public static int findRoot(int c) {
        if (parent[c] == c) {
            return c;
        }

        parent[c] = findRoot(parent[c]);
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

        @Override
        public String toString() {
            return "Edge{" +
                    "s=" + s +
                    ", d=" + d +
                    ", cost=" + cost +
                    '}';
        }
    }
}

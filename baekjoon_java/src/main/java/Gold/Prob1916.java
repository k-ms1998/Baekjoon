package Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5
 *
 * 문제
 * N개의 도시가 있다. 그리고 한 도시에서 출발하여 다른 도시에 도착하는 M개의 버스가 있다.
 * 우리는 A번째 도시에서 B번째 도시까지 가는데 드는 버스 비용을 최소화 시키려고 한다. A번째 도시에서 B번째 도시까지 가는데 드는 최소비용을 출력하여라. 도시의 번호는 1부터 N까지이다.
 *
 * 입력
 * 첫째 줄에 도시의 개수 N(1 ≤ N ≤ 1,000)이 주어지고 둘째 줄에는 버스의 개수 M(1 ≤ M ≤ 100,000)이 주어진다
 * 그리고 셋째 줄부터 M+2줄까지 다음과 같은 버스의 정보가 주어진다. 먼저 처음에는 그 버스의 출발 도시의 번호가 주어진다. 
 * 그리고 그 다음에는 도착지의 도시 번호가 주어지고 또 그 버스 비용이 주어진다. 버스 비용은 0보다 크거나 같고, 100,000보다 작은 정수이다.
 *
 * 그리고 M+3째 줄에는 우리가 구하고자 하는 구간 출발점의 도시번호와 도착점의 도시번호가 주어진다. 출발점에서 도착점을 갈 수 있는 경우만 입력으로 주어진다.
 *
 * 출력
 * 첫째 줄에 출발 도시에서 도착 도시까지 가는데 드는 최소 비용을 출력한다.
 * 
 * Solution: Dijkstra
 * !! 주의 사항 !!
 * 1. 문제를 보면, 방향은 단방향이다.
 * 2. PriorityQueue 를 사용하지 않으면 시간초과 발생
 */
public class Prob1916 {

    static int n;
    static int m;
    static Map<Integer, List<Edge>> edges = new HashMap<>();
    static Integer[] dist;
    static int src;
    static int dst;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.valueOf(br.readLine());
        dist = new Integer[n + 1];
        for (int i = 0; i < n + 1; i++) {
            dist[i] = Integer.MAX_VALUE;
            edges.put(i, new ArrayList<>());
        }

        m = Integer.valueOf(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            List<Edge> curEdge = edges.get(s);
            curEdge.add(new Edge(s, d, cost));
            edges.put(s, curEdge);
        }

        st = new StringTokenizer(br.readLine());
        src = Integer.parseInt(st.nextToken());
        dst = Integer.parseInt(st.nextToken());

        dist[src] = 0;
        dij();
        System.out.println(dist[dst]);
    }

    public static void dij() {
        /**
         * - 우선 순위 큐를 이용해서 거리가 최소인 값이 가장 앞에 있도록 Min Heap 유지
         * -!! Comparator 을 이용 !!
         */
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge o1, Edge o2) {
                if (o1.cost > o2.cost) {
                    return 1;
                } else if (o1.cost < o2.cost) {
                    return -1;
                } else {
                    return 0;
                }

            }
        });
        for (Edge e : edges.get(src)) {
            queue.offer(e);
        }

        while (!queue.isEmpty()) {
            Edge curNode = queue.poll();
            int curS = curNode.s;
            int curD = curNode.d;
            int curCost = curNode.cost;

            /**
             * 현재 curS -> curD 까지의 거리가 이미 src(시작점) -> curD 까지의 거리보다 크면:
             * curS -> curD 가는 간선을 더 이상 검사할 필요 X && curD와 인접한 노드들도 거리가 현재보다 더 단축 되지 않기 때문에 인접 노드들도 검사할 필요 X
             * => continue
             */
            if (curCost > dist[curD]) {
                continue;
            }
            dist[curD] = curCost;

            List<Edge> adjEdges = edges.get(curD);
            for (Edge edge : adjEdges) {
                int d = edge.d;
                int cost = edge.cost;
                int currentCost = dist[curD] + cost;

                if (currentCost < dist[d]) {
                    dist[d] = currentCost;
                    queue.offer(new Edge(curD, d, currentCost));
                }
            }
        }
    }

    public static class Edge {
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
            return "{ " + s + ", " + d + ", " + cost + " }";
        }
    }
}

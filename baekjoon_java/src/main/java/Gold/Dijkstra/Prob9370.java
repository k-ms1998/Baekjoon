package Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 2(미확인 도착지)
 *
 * https://www.acmicpc.net/problem/9370
 *
 * Solution: 다익스트라
 * 1. s 시작 지점부터 도착 지점들까지의 최단 거리 구하기 -> 다익스트라
 * 2. 최단 거리를 구할때, g-h 구간을 거치는지 안거치는지 확인
 * 3. 구간을 거치는지 확인하기 위해서는 간선을 입력 받을때, 가중치를 변경해서 저장함
 *  3-1. g-h 구간의 간선은 2 * 가중치 - 1 저장, 그외 모든 간선은 2 * 가중치로 저장
 *  3-2. 이렇게하면, g-h 구간을 거치치 않은 간선은 무조건 가중치가 짝수이고, g-h 구간을 거치면 홀수가됨
 *  3-3. 그리고, 원래 가중치가 같은 두개 이상의 간선이 있을때, g-h는  2*가중치하고 1을 빼기 때문에, 가중치가 같은 간선들에 대해서 g-h를 거치게 됨
 *      3-3-1. ex: [1]-(cost=2)-> [2] <-(cost=2)-[3] 일때, 노드 2로 도달하는 최단거리는 2인데, 가능한 방법은 두 가지이다. 1->2 && 3->2.
 *              이때, g-h 가 2-3 구간일때, g-h 구간을 지나도록 하기 위해서는 해당 구간은 2*가중치 - 1을 해주고, 그외는 2*가중치를 해준다
 *                  => [1]-(cost=4)-> [2] <-(cost=3)-[3] 이렇게 변환하면, 노드2로 도달하는 최단거리는 3이며, g-h 구간인 2-3 구간도 지나게 된다.
 */
public class Prob9370 {

    static int n, m, t;
    static int s, g, h;
    static int[] destinations;

    static List<Edge>[] edges;
    static int[] dist;
    static final int INF = 1000000000;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            edges = new List[n + 1];
            dist = new int[n + 1];
            for (int i = 1; i < n + 1; i++) {
                edges[i] = new ArrayList<>();
                dist[i] = INF;
            }
            dist[s] = 0;
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int src = Integer.parseInt(st.nextToken());
                int dst = Integer.parseInt(st.nextToken());
                int cost = 2*Integer.parseInt(st.nextToken());

                if((src == g && dst == h) || (src == h && dst == g)){
                   --cost;
                }
                edges[src].add(new Edge(dst, cost));
                edges[dst].add(new Edge(src, cost));

            }

            destinations = new int[t];
            for(int i = 0; i < t; i++){
                destinations[i] = Integer.parseInt(br.readLine());
            }
            Arrays.sort(destinations);

            dijkstra();
            for (int i = 0; i < t; i++) {
                /**
                 * g-h 구간을 지났으면, 거리는 무조건 홀수이고, 해당 구간을 지나지 않았으면 거리는 무조건 짝수인것을 이용해서 답 추출
                 */
                if (dist[destinations[i]] % 2 == 1) {
                    ans.append(destinations[i]).append(" ");
                }
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static void dijkstra() {
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                if (e1.cost == e2.cost) {
                    return e1.dst - e2.dst;
                }
                return e1.cost - e2.cost;
            }
        });
        queue.offer(new Edge(s, 0));

        while (!queue.isEmpty()) {
            Edge curEdge = queue.poll();
            int curSrc = curEdge.dst;
            int curCost = curEdge.cost;

            List<Edge> adjEdge = edges[curSrc];
            for (Edge edge : adjEdge) {
                int nextDst = edge.dst;
                int nextCost = edge.cost;

                if (dist[nextDst] > curCost + nextCost) {
                    dist[nextDst] = curCost + nextCost;
                    queue.offer(new Edge(nextDst, curCost + nextCost));
                }
            }
        }
    }

    static class Edge{
        int dst;
        int cost;

        public Edge(int dst, int cost) {
            this.dst = dst;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "dst=" + dst +
                    ", cost=" + cost +
                    '}';
        }
    }
}
/*
2
5 4 2
1 2 3
1 2 6
2 3 2
3 4 4
3 5 3
5
4
6 9 2
2 3 1
1 2 1
1 3 3
2 4 4
2 5 5
3 4 3
3 6 2
4 5 4
4 6 3
5 6 7
5
6

1
5 4 2
1 2 3
1 2 1
2 3 1
3 4 1
2 5 1
4
5
 */

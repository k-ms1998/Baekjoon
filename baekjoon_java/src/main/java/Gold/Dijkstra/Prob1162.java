package Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 1(도로포장)
 *
 * https://www.acmicpc.net/problem/1162
 * 
 * Solution: Dijkstra
 * 시작 지점 1부터 각 노드까지의 최단거리를 구하는 방식이 일반적인 다익스트라랑 유사한 문제이지만, 각 노드까지의 거리에 포장한 도로의 갯수까지 고려해서 거리 저장
 * 즉, 일반 다익스트라는 1차원 배열에 최단 거리들을 저장하지만, 해당 문제는 포장한 도로의 갯수까지 고려해서 2차원 배열에 저장
 */
public class Prob1162 {

    static int n, m, k;
    static long[][] dist;
    static List<Edge>[] edges;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        dist = new long[n + 1][k + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
            if (i == 1) {
                Arrays.fill(dist[i], 0);
            }else{
                Arrays.fill(dist[i], Long.MAX_VALUE);
            }
        }
        for(int i = 1; i < m + 1; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long d = Long.parseLong(st.nextToken());

            /*
            양방향
             */
            edges[a].add(new Edge(b, d, 0));
            edges[b].add(new Edge(a, d, 0));
        }

        /**
         * 다익스트라로 풀이
         */
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return (int)(e1.cost - e2.cost);
            }
        });
        queue.offer(new Edge(1, 0, 0));
        long ans = Long.MAX_VALUE;
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int src = edge.dst;
            long cost = edge.cost;
            int cnt = edge.cnt;

            if (src == n) {
                ans = cost;
                break;
            }

            /*
            시간 초과를 방지하기 위해서, 현재 src까지의 거리가 이미 cost보다 작으면 더 이상 탐색할 필요 X
             */
            if (dist[src][cnt] < cost) {
                continue;
            }

            for (Edge next : edges[src]) {
                int dst = next.dst;
                long nextCost = next.cost;
                /**
                 * 현재까지 포장한 도로의 갯수가 k개 미만이면, 도로를 더 포장해도 되기 때문에 현재 도로를 포장했을때의 경우를 추가
                 */
                if (cnt + 1 <= k) {
                    if (dist[dst][cnt + 1] > cost){
                        dist[dst][cnt + 1] = cost;
                        queue.offer(new Edge(dst, cost, cnt + 1));
                    }
                }

                long nextTotalCost = cost + nextCost;
                if (dist[dst][cnt] > nextTotalCost){
                    dist[dst][cnt] = nextTotalCost;
                    queue.offer(new Edge(dst, nextTotalCost, cnt));
                }
            }
        }

        System.out.println(ans);
    }

    public static class Edge{
        int dst;
        long cost;
        int cnt;

        public Edge(int dst, long cost, int cnt){
            this.dst = dst;
            this.cost = cost;
            this.cnt = cnt;
        }
    }
}

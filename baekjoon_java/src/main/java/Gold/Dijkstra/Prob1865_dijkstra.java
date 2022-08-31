package Gold.Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Prob1865_dijkstra {

    static int n;
    static int m;
    static int w;

    static List<Edge>[] roads;
    static List<Edge>[] wormHoles;

    static StringBuffer ans = new StringBuffer();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int tc = Integer.parseInt(br.readLine());
        for (int i = 0; i < tc; i++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            roads = new List[n + 1];
            wormHoles = new List[n + 1];
            for (int j = 0; j < n + 1; j++) {
                roads[j] = new ArrayList<>();
                wormHoles[j] = new ArrayList<>();
            }
            /**
             * S와 E는 연결된 지점의 번호, T는 이 도로를 통해 이동하는데 걸리는 시간
             */
            for (int j = 0; j < m; j++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int t = Integer.parseInt(st.nextToken());

                roads[s].add(new Edge(e, t));
                roads[e].add(new Edge(s, t));
            }

            /**
             * S는 시작 지점, E는 도착 지점, T는 줄어드는 시간
             */
            for (int j = 0; j < w; j++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int t = Integer.parseInt(st.nextToken());

                wormHoles[s].add(new Edge(e, -t));
            }

            boolean timeTravel = false;
            for (int j = 1; j < n + 1; j++) {
                if (dijkstra(j)) {
                    timeTravel = true;
                    ans.append("YES");
                    break;
                }
            }

            if (!timeTravel) {
                ans.append("NO");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    /**
     * 기본 다익스트라 알고리즘
     */
    public static boolean dijkstra(int src) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.t - e2.t;
            }
        });
        queue.offer(new Edge(src, 0));

        int[] dist = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;
        while(!queue.isEmpty()){
            Edge curEdge = queue.poll();
            int curE = curEdge.e;
            int curT = curEdge.t;

            if (curE == src) {
                if (curT < 0) {
                    return true;
                }
            }
            if (curT > dist[curE]) {
                continue;
            }

            List<Edge> adjWormHoles = wormHoles[curE];
            for (Edge edge : adjWormHoles) {
                int nextE = edge.e;
                int nextT = edge.t;

                if (dist[nextE] > curT + nextT) {
                    /**
                     * dist[nextE] 의 값이 0 이상일때만 값 업데이트 시켜줌
                     * 안그러면, 음수 싸이클이 생겨서 무한 루프가 됨
                     */
                    if(dist[nextE] >= 0){
                        dist[nextE] = curT + nextT;
                        queue.offer(new Edge(nextE, curT + nextT));
                    }
                }
            }

            List<Edge> adjRoads = roads[curE];
            for (Edge edge : adjRoads) {
                int nextE = edge.e;
                int nextT = edge.t;

                if (dist[nextE] > curT + nextT) {
                    dist[nextE] = curT + nextT;
                    queue.offer(new Edge(nextE, curT + nextT));
                }
            }
        }



        return false;
    }

    static class Edge{
        int e;
        int t;

        public Edge(int e, int t) {
            this.e = e;
            this.t = t;
        }

        @Override
        public String toString() {
            return "{ e = " + e + ", t = " + t + " }";
        }
    }
}

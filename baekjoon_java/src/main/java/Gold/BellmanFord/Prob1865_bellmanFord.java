package Gold.BellmanFord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 3
 *
 *  https://www.acmicpc.net/problem/1865
 *
 *  Solution: Bellman-Ford
 *
 */
public class Prob1865_bellmanFord {

    static int n;
    static int m;
    static int w;

    static List<Edge>[] roads;

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
            for (int j = 0; j < n + 1; j++) {
                roads[j] = new ArrayList<>();
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

                /**
                 * 웜홀도 roads 에 추가
                 */
                roads[s].add(new Edge(e, -t));
            }

            if (bellmanFord()) {
                ans.append("YES");
            } else {
                ans.append("NO");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static boolean bellmanFord() {
        int[] dist = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            dist[i] = 10001;
        }
        dist[1] = 0;

        /**
         * (정점의 개수 - 1)번 동안 최단거리 초기화 작업을 반복
         */
        for (int i = 1; i < n; i++) {
            /**
             * 모든 간선들(roads) 확인 & 거리 업데이트
             */
            for (int j = 1; j < n + 1; j++) {
                List<Edge> adjEdges = roads[j];
                for(Edge edge : adjEdges){
                    int curE = edge.e;
                    int curT = edge.t;

                    if (dist[curE] > dist[j] + curT) {
                        dist[curE] = dist[j] + curT;
                    }
                }
            }
        }

        /**
         * 음의 싸이클 존재하는지 확인
         * -> 거리가 감소하면 음의 싸이클 존재
         */
        for (int j = 1; j < n + 1; j++) {
            List<Edge> adjEdges = roads[j];

            for(Edge edge : adjEdges){
                int curE = edge.e;
                int curT = edge.t;

                if (dist[curE] > dist[j] + curT) {
                    return true;
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

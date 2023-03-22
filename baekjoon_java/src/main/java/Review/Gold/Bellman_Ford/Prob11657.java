package Review.Gold.Bellman_Ford;

import java.io.*;
import java.util.*;

/**
 * Gold 4(타임머신)
 *
 * https://www.acmicpc.net/problem/11657
 *
 * Solution: Bellman-Ford
 *
 * 1. 모든 간선을 n번 만큼 업데이트
 *  -> a->b로 간선을 확인할때, a까지의 거리가 INF이면, 아직 노드 a를 방문하지 않은 상태이기 때문에 건너뛰기
 *  -> a->b로 간선을 확인할때, a까지의 거리가 INF가 아니면, b까지의 거리 = a 까지의 거리 + a->b 간선의 가중치
 * 2. 다시 한번 모드 간선 확인
 *  -> 거리가 줄어드는 노드가 있으면 음의 싸이클
 */
public class Prob11657 {

    static int n, m;
    static long[] dist;
    static final long INF = 100000000L;
    static List<Edge> edges = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dist = new long[n + 1];
        Arrays.fill(dist, INF);

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges.add(new Edge(a, b, c));
        }

        /*
        모든 간선을 노드의 개수번 만큼 탐색
         */
        dist[1] = 0;
        for(int i = 1; i < n + 1; i++){
            for(Edge edge : edges){
                int a = edge.a;
                int b = edge.b;
                int c = edge.c;


                if(dist[a] != INF){
                    dist[b] = Math.min(dist[b], dist[a] + c);
                }
            }
        }

        /*
        마지막으로 모든 간선들을 다시 한번 탐색
        만약, 최단거리가 줄어들면, 음의 싸이클 존재
         */
        boolean isCycle =  false;
        for(Edge edge : edges){
            int a = edge.a;
            int b = edge.b;
            int c = edge.c;

            if(dist[a] != INF){
                if(dist[b] > dist[a] + c){
                    isCycle = true;
                    break;
                }
            }
        }

        if(isCycle){
            System.out.println("-1");
        }else{
            StringBuilder ans = new StringBuilder();
            for(int  i = 2; i < n + 1; i++){
                ans.append(dist[i] == INF ? -1 : dist[i]).append("\n");
            }
            System.out.println(ans);
        }
    }

    public static class Edge{
        int a;
        int b;
        int c;

        public Edge(int a, int b, int c){
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
}

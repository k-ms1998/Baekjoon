package Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 2(네트워크 복구)
 *
 * https://www.acmicpc.net/problem/2211
 *
 * Solution: Dijkstra
 */
public class Prob2211 {

    static int n, m;
    static List<Edge>[] edges;

    static int[] dist;

    static List<Edge> conn = new ArrayList<>();
    static StringBuilder ans = new StringBuilder();
    static int[] path;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        dist = new int[n + 1];
        path = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE;
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges[a].add(new Edge(a, b, c));
            edges[b].add(new Edge(b, a, c));
        }

        dijkstra();
         ans.append(n-1).append("\n");
         for(int i = 2; i < n + 1; i++){
             ans.append(i).append(" ").append(path[i]).append("\n");
         }

        System.out.println(ans);

    }

    public static void dijkstra(){
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.cost - e2.cost;
            }
        });
        queue.offer(new Edge(1, 0));
        dist[1] = 0;

        while(!queue.isEmpty()){
            Edge curEdge = queue.poll();
            int curSrc = curEdge.dst;
            int curCost = curEdge.cost;

            List<Edge> adjEdges = edges[curSrc];
            for(Edge adj : adjEdges){
                int nextDst = adj.dst;
                int nextCost = adj.cost;

                /**
                 * 최단 거리가 업데이트될때 마다, 직전 노드를 저장하고 있으므로, 사용된 간선들의 추적이 가능해진다
                 */
                if(dist[nextDst] > curCost + nextCost){
                    path[nextDst] = curSrc;
                    dist[nextDst] = curCost + nextCost;
                    queue.offer(new Edge(nextDst, curCost + nextCost));
                }
            }
        }
    }

    public static void printDist(){
        for(int i = 1; i < n + 1; i++){
            System.out.print(dist[i] + " ");
        }
        System.out.println();
    }

    public static class Edge{
        int src;
        int dst;
        int cost;

        public Edge(int dst, int cost){
            this.dst = dst;
            this.cost = cost;
        }


        public Edge(int src, int dst, int cost){
            this.src = src;
            this.dst = dst;
            this.cost = cost;
        }

        @Override
        public String toString(){
            return "Edge:{src="+src+", dst="+dst+", cost="+cost+"}";
        }
    }
}
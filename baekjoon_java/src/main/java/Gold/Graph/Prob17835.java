package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 2(면접보는 승범이네)
 *
 * https://www.acmicpc.net/problem/17835
 *
 * Solution: 다익스트라
 * 1. 도시에서 면접정까지의 거리를 계산해서 거리가 가장 먼 도시 찾기
 * 2. 이때, 주어지는 단방향 간선들의 방향을 반대로하고, 각 면접장에서 도시까지의 거리들을 계산
 * -> 왜냐하면, 만약 문제 그대로 주어진 방향대로 해서 각 도시로부터 면접장까지의 거리를 최단거리르를 구할려고 하면, 현재 도시로 부터 모든 면접장까지의 거리를 확인해야함
 *  -> 하지만, 반대로 면접장들로부터 시작해서 도시까지의 최단거리를 구하면 시작 지점이 여러개(모든 면접장들)이 되는 단순한 다익스트라 문제가 됨
 *      -> 이렇게 하기 위해서는 문제에서 주어진 간선들의 방향도 모두 반대로 해주면 됨
 * 3. 다익스트라를 써서 각 면접장들로부터 도시까지의 최단거리들을 구했을때, 그 중에 최대값과 최대값을 가진 도시만 찾으면 끝
 */
public class Prob17835 {

    static int n, m, k;
    static long[] dist;
    static List<Edge>[] edges;
    static final long INF = Long.MAX_VALUE;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dist = new long[n + 1];
        edges = new List[n + 1];
        for(int i = 0; i < n + 1; i++){
            dist[i] = INF;
            edges[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges[v].add(new Edge(u, c));
        }

        Deque<Edge> queue = new ArrayDeque<>();
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < k; i++){
            int p = Integer.parseInt(st.nextToken());
            queue.offer(new Edge(p, 0));
            dist[p] = 0;
        }

        while(!queue.isEmpty()){
            Edge e = queue.poll();
            int node = e.node;
            long c = e.c;

            for(Edge edge : edges[node]){
                int next = edge.node;
                long nc = edge.c;

                if(dist[next] > c + nc){
                    dist[next] = c + nc;
                    queue.offer(new Edge(next, c + nc));
                }
            }
        }

        int ansN = 0;
        long ansC = 0;
        for(int i = 1; i < n + 1; i++){
            if(dist[i] > ansC && dist[i] != INF){
                ansN = i;
                ansC = dist[i];
            }
        }

        System.out.println(ansN + "\n" + ansC);
    }

    public static class Edge{
        int node;
        long c;

        public Edge(int node, long c){
            this.node = node;
            this.c = c;
        }
    }
}
package Review.Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 3(파티)
 *
 * https://www.acmicpc.net/problem/1238
 *
 * Solution: 다익스트라
 */
public class Prob1238_normal {

    static int n, m, x;
    static List<Edge>[] edges;
    static final int INF = 100000000;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            edges[src].add(new Edge(dst, t));
        }

        int answer = 0;

        int[] timeX = new int[n + 1];
        Arrays.fill(timeX, INF);
        Deque<Edge> queueX = new ArrayDeque<>();
        queueX.offer(new Edge(x, 0));
        timeX[x] = 0;
        while(!queueX.isEmpty()){
            Edge e = queueX.poll();
            int node = e.node;
            int dist = e.dist;

            for(Edge edge : edges[node]){
                int next = edge.node;
                int nextD = edge.dist + dist;

                if(timeX[next] > nextD){
                    timeX[next] = nextD;
                    queueX.offer(new Edge(next, nextD));
                }
            }
        }
        // printArr(timeX);

        for(int i = 1; i < n + 1; i++){
            if(i == x){
                continue;
            }
            int[] time = new int[n + 1];
            Arrays.fill(time, INF);
            Deque<Edge> queue = new ArrayDeque<>();
            queue.offer(new Edge(i, 0));
            while(!queue.isEmpty()){
                Edge e = queue.poll();
                int node = e.node;
                int dist = e.dist;
                if(node == x){
                    continue;
                }

                for(Edge edge : edges[node]){
                    int next = edge.node;
                    int nextD = edge.dist + dist;

                    if(time[next] > nextD){
                        time[next] = nextD;
                        queue.offer(new Edge(next, nextD));
                    }

                }
            }
            // System.out.println(i + "->" + time[x]);
            answer = Math.max(answer, timeX[i] + time[x]);
        }

        System.out.println(answer);
    }

    public static void printArr(int[] arr){
        for(int i = 1; i < n + 1; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println("\n----------");
    }

    public static class Edge{
        int node;
        int dist;

        public Edge(int node, int dist){
            this.node = node;
            this.dist = dist;
        }
    }
}
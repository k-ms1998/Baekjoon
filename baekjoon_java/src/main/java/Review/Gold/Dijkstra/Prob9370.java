package Review.Gold.Dijkstra;

import java.io.*;
import java.util.*;

public class Prob9370 {

    static final int INF = 100_000_000;
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        while(T-- > 0){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()); // 교차로의 수 (1 <= n <= 2_000) == node
            int m = Integer.parseInt(st.nextToken()); // 도로로의 수 (1 <= m <= 50_000)
            int t = Integer.parseInt(st.nextToken()); // 목적지 후보의 수 (1 <= t <= 100)

            int[] fds = new int[n + 1];
            boolean[] status = new boolean[n + 1];
            List<Edge>[] edges = new List[n + 1];
            for(int i = 0; i < n + 1; i++){
                fds[i] = INF;
                edges[i] = new ArrayList<>();
            }

            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()); // 출발지점
            int g = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken()); // g랑 h 사이의 교차로를 지났음

            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken()); // a랑 b 사이에 길이가 d인 양방향 간선

                edges[a].add(new Edge(b, d));
                edges[b].add(new Edge(a, d));
            }

            findDist(fds, status, s, edges, g, h);

            List<Integer> list = new ArrayList<>();
            for(int i = 0; i < t; i++){
                int pd = Integer.parseInt(br.readLine()); // 목적지 후보들
                if(status[pd]){
                    list.add(pd);
                }
            }

            Collections.sort(list);
            for(int node : list){
                ans.append(node).append(" ");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static void findDist(int[] arr, boolean[] status, int start, List<Edge>[] edges, int g, int h){
        Deque<Edge> queue = new ArrayDeque<>();
        queue.offer(new Edge(start, 0));
        arr[start] = 0;

        while(!queue.isEmpty()){
            Edge edge = queue.poll();
            int node = edge.next;
            int dist = edge.dist;
            boolean used = edge.used;

            for(Edge e : edges[node]){
                int next = e.next;
                int nextD = e.dist;

                boolean nextU = (node == g && next == h) || (node == h && next == g) || used;
                if(arr[next] > dist + nextD){
                    arr[next] = dist + nextD;
                    status[next] = nextU;
                    queue.offer(new Edge(next, dist + nextD, nextU));
                }else if(arr[next] == dist + nextD && nextU && !status[next]){
                    status[next] = nextU;
                    queue.offer(new Edge(next, dist + nextD, nextU));
                }
            }
        }
    }

    public static class Edge{
        int next;
        int dist;
        boolean used;

        public Edge(int next, int dist){
            this.next = next;
            this.dist = dist;
            this.used = false;
        }

        public Edge(int next, int dist, boolean used){
            this.next = next;
            this.dist = dist;
            this.used = used;
        }
    }
}
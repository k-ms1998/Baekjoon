package Review.Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 3(최소비용 구하기 2)
 *
 * https://www.acmicpc.net/problem/11779
 *
 * Solution: Dijkstra
 */
public class Prob11779 {

    static int n, m;
    static List<Edge>[] edges;
    static int[] path;
    static int start, end;

    static int[] count;
    static int[] dist;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        edges = new List[n + 1];
        path = new int[n + 1];
        count = new int[n + 1];
        dist = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
            path[i] = i;
            dist[i] = Integer.MAX_VALUE;
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[src].add(new Edge(dst, cost, 0));
        }

        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        dijkstra();
        findAnswer();
        System.out.println(ans);
    }

    public static void findAnswer(){
        /*
        - dist[end]: start 에서 end 까지의 최단거리
        - count[end]: start 에서 end 까지 최단거리로 갈때, 거쳐간 노드들의 갯수
         */
        ans.append(dist[end]).append("\n");
        ans.append(count[end]).append("\n");
        findPath(end);
    }

    /**
     * 역추적:
     * 최종 도착 노드로 부터 시작해서, 해당 노드를 방문하기 직전의 노드를 따라감
     * 시작 노드가 나올때까지 따라감
     */
    public static void findPath(int node){
//        System.out.println("node = " + node);
//        stack.push(node);
        if (node == start) {
            ans.append(node).append(" ");
            return;
        }

        findPath(path[node]);
        ans.append(node).append(" ");
    }

    public static void dijkstra(){
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2){
                return e1.cost - e2.cost;
            }
        });
        queue.offer(new Edge(start, 0, 1));
        dist[start] = 0;

        while(!queue.isEmpty()){
            Edge cur = queue.poll();
            int d = cur.dst;
            int c = cur.cost;
            int cnt = cur.cnt;

            if (d == end) {
                return;
            }

            List<Edge> adjEdges = edges[d];
            for(Edge adj : adjEdges){
                int nextDst = adj.dst;
                int nextCost = adj.cost;

                /**
                 * 특정 노드까지의 거리가 줄어들때, 해당 노드로 들어오는 직전 노드를 path 에 저장
                 * 또한, 해당 노드를 최단거리로 도달하기 위해서 앞서 거친 노드들의 갯수를 count 에 저장
                 * 이렇게 함으로써, 특정 노드를 최단거리로 갈때 직전에 방문한 노드와, 거쳐간 총 노드들의 갯수를 바로 알 수 있음
                 */
                if (dist[nextDst] > c + nextCost) {
                    dist[nextDst] = c + nextCost;
                    path[nextDst] = d;
                    count[nextDst] = cnt + 1;
                    queue.offer(new Edge(nextDst, c + nextCost, cnt + 1));
                }
            }
        }


    }

    public static class Edge{
        int dst;
        int cost;
        int cnt;

        public Edge(int dst, int cost, int cnt) {
            this.dst = dst;
            this.cost = cost;
            this.cnt = cnt;
        }
    }
}

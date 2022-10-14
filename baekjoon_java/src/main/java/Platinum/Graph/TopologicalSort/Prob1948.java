package Platinum.Graph.TopologicalSort;

import java.io.*;
import java.util.*;

/**
 * Platinum 5(임계경로)
 *
 * https://www.acmicpc.net/problem/1948
 *
 * Solution: TopologicalSort + 역추적
 * 1. 도착 노드에 모든 사람들이 만나는데 걸리는 시간은 시작 노드에서 도착노드에 도착하는 경로들 중에서 값이 가장 큰 값입니다.
 *  -> Because, 해당 노드에 모든 사람들이 도착할때까지 기다려야 되므로, 모두 도착한 시간은 가장 마지막으로 도착한 사람의 시간이기 때문입니다.
 * 2. 그러므로, 도착 노드에서 도착 노드로 진입하는 모든 노드들로 부터 도착을 했을때의 시간들을 비교
 *  -> 즉, 도착 노드 이전에 미리 방문해야되는 노드들 존재 => Topological Sort
 * 3. 도착 노드에 도달하는데 가장 오래 걸리는 시간을 계산하기 위해서는, 시작 노드에서 각 노드까지 걸리는 시간들의 최대값을 구합니다.
 * 4. 총 방문한 도로들으 계산하기 위해서는 도착 노드에서 시작 노드까지 경로를 보면서 역추적
 *  -> 이때, 도착 노드까지 최대로 시간이 걸리는 경로가 여러가지일 수 있기 때문에, 이 부분도 생각하면서 경로 역추적
 *      -> 경로는 여러가지이지만, 겹치는 도로가 부분적으로 있을 수 있음. 이때, 겹치는 도로를 중복해서 카운트하지 않도록 방문한 도로인지 아닌지 확인
 */
public class Prob1948 {

    static int n;
    static List<Edge>[] edges;
    static int m;
    static int start;
    static int end;

    static List<Edge>[] reverseEdges;
    static boolean[][] visited;
    static int[] inCount;
    static int[] dist;

    static int cnt = 0;
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        dist = new int[n + 1];
        inCount = new int[n + 1];
        edges = new List[n + 1];
        reverseEdges = new List[n + 1];
        visited = new boolean[n + 1][];
        for(int i = 1; i < n + 1; i++){
            edges[i] =  new ArrayList<>();
            reverseEdges[i] = new ArrayList<>();
            visited[i] = new boolean[n + 1];
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());;

            edges[src].add(new Edge(dst, cost));
            reverseEdges[dst].add(new Edge(src, cost));
            inCount[dst]++;
        }

        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        topological();
        trackEdge(end, dist[end]);

        ans.append(dist[end]).append("\n").append(cnt);

        System.out.println(ans);
    }

    /**
     * 위상 정렬
     */
    public static void topological(){
        Deque<Edge> queue = new ArrayDeque<>();
        queue.offer(new Edge(start, 0));
        while(!queue.isEmpty()){
            Edge cur = queue.poll();
            int curD = cur.dst;
            int curC = cur.cost;

            if(curD == end){
                return;
            }

            List<Edge> adjEdges = edges[curD];
            for(Edge adj: adjEdges){
                int nextD = adj.dst;
                int nextC = adj.cost;
                dist[nextD] = Math.max(dist[nextD], curC + nextC);
                inCount[nextD]--;
                if(inCount[nextD] == 0){
                    queue.offer(new Edge(nextD, dist[nextD]));
                }
            }
        }

    }

    /**
     * 경로 역추적
     */
    public static void trackEdge(int node, int dis){
        if(dis == 0){
            return;
        }
        List<Edge> adjEdges = reverseEdges[node];
        for(Edge adj: adjEdges){
            int prevNode = adj.dst;
            int prevCost = adj.cost;

            if(visited[node][prevNode] || visited[prevNode][node]){
                continue;
            }else if(dis - prevCost == dist[prevNode]){
                ++cnt;
                visited[node][prevNode] = true;
                trackEdge(prevNode, dis-prevCost);
            }
        }

    }

    public static class Edge{
        int dst;
        int cost;

        public Edge(int dst, int cost){
            this.dst = dst;
            this.cost = cost;
        }
    }
}
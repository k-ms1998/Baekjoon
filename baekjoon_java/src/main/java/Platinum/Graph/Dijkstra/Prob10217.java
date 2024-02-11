package Platinum.Graph.Dijkstra;

import java.io.*;
import java.util.*;

public class Prob10217 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int airportTotal;
    static int maxCost;
    static int ticketsTotal;
    static int targetAirport;

    static List<Edge>[] edges;
    static int[][] travelInfo;
    static int[] maxCostByNode;

    static final int INF = 100_000_001;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 1; testCase <= T; testCase++){
            st = new StringTokenizer(br.readLine());
            airportTotal = Integer.parseInt(st.nextToken());
            maxCost = Integer.parseInt(st.nextToken());
            ticketsTotal = Integer.parseInt(st.nextToken());

            targetAirport = airportTotal;
            travelInfo = new int[airportTotal + 1][maxCost + 1];
            maxCostByNode = new int[airportTotal + 1];

            edges = new List[airportTotal + 1];
            for(int idx = 0; idx < airportTotal + 1; idx++){
                edges[idx] = new ArrayList<>();
            }

            for(int ticket = 0; ticket < ticketsTotal; ticket++){
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());

                if(cost >= maxCost){
                    continue;
                }
                edges[start].add(new Edge(end, cost, time));
            }
            for(int idx = 0; idx < airportTotal + 1; idx++){
                Collections.sort(edges[idx], new Comparator<Edge>(){
                    @Override
                    public int compare(Edge e1, Edge e2){
                        if(e1.time == e2.time){
                            return e1.cost - e2.cost;
                        }

                        return e1.time - e2.time;
                    }
                });
            }

            PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>(){
                @Override
                public int compare(Edge e1, Edge e2){
                    if(e1.time == e2.time){
                        return e1.cost - e2.cost;
                    }

                    return e1.time - e2.time;
                }
            });
            queue.offer(new Edge(1, 0, 0));
            Arrays.fill(travelInfo[0], 0);
            for(int idx = 0; idx < airportTotal + 1; idx++){
                Arrays.fill(travelInfo[idx], INF);
            }

            int answer = INF;
            while(!queue.isEmpty()){
                Edge edge = queue.poll();
                int node = edge.node;
                int cost = edge.cost;
                int time = edge.time;

                if(node == targetAirport){
                    answer = Math.min(answer, time);
                    break;
                }

                for(Edge nextEdge : edges[node]){
                    int nNode = nextEdge.node;
                    int nCost = nextEdge.cost;
                    int nTime = nextEdge.time;

                    int nextCost = cost + nCost;
                    int nextTime = time + nTime;
                    if(nextCost > maxCost){
                        continue;
                    }

                    if(travelInfo[nNode][nextCost] > nextTime) {
                        int curMaxCostByNode = maxCostByNode[nNode];
                        int curMaxTimeByNode = travelInfo[nNode][curMaxCostByNode]; // 현재 사용된 비용이 nNode에서 사용된 비용보다 높을때 -> 비용도 더 많이 드는데 시간도 더 오래 걸리면 확인할 필요 X
                        if(nextCost <= curMaxCostByNode ||
                                (nextCost > curMaxCostByNode && curMaxTimeByNode > nextTime)){
                            travelInfo[nNode][nextCost] = nextTime;
                            maxCostByNode[nNode] = nextCost;
                            queue.offer(new Edge(nNode, nextCost, nextTime));
                        }
                    }
                }
            }

            sb.append(answer == INF ? "Poor KCM" : answer).append("\n");
        }

        System.out.println(sb);
    }

    public static class Edge{
        int node;
        int cost;
        int time;

        public Edge(int node, int cost, int time){
            this.node = node;
            this.cost = cost;
            this.time = time;
        }
    }
}

package Platinum.Graph.Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Platinum 5 : Dijkstra's Algorithm
 *
 * 문제
 * 요즘 많은 자동차에서는 GPS 네비게이션 장비가 설치되어 있다. 네비게이션은 사용자가 입력한 출발점과 도착점 사이의 최단 경로를 검색해 준다.
 * 하지만, 교통 상황을 고려하지 않고 최단 경로를 검색하는 경우에는 극심한 교통 정체를 경험할 수 있다.
 * 상근이는 오직 자기 자신만 사용 가능한 네비게이션을 만들고 있다. 이 네비게이션은 절대로 최단 경로를 찾아주지 않는다. 항상 거의 최단 경로를 찾아준다.
 * 거의 최단 경로란 최단 경로에 포함되지 않는 도로로만 이루어진 경로 중 가장 짧은 것을 말한다.
 * 예를 들어, 도로 지도가 아래와 같을 때를 생각해보자. 원은 장소를 의미하고, 선은 단방향 도로를 나타낸다. 시작점은 S, 도착점은 D로 표시되어 있다.
 * 굵은 선은 최단 경로를 나타낸다. (아래 그림에 최단 경로는 두 개가 있다)거의 최단 경로는 점선으로 표시된 경로이다.
 * 이 경로는 최단 경로에 포함되지 않은 도로로 이루어진 경로 중 가장 짧은 경로이다. 거의 최단 경로는 여러 개 존재할 수도 있다.
 * 예를 들어, 아래 그림의 길이가 3인 도로의 길이가 1이라면, 거의 최단 경로는 두 개가 된다. 또, 거의 최단 경로가 없는 경우도 있다.
 *
 * 입력
 * 입력은 여러 개의 테스트 케이스로 이루어져 있다. 각 테스트 케이스의 첫째 줄에는 장소의 수 N (2 ≤ N ≤ 500)과 도로의 수 M (1 ≤ M ≤ 104)가 주어진다.
 * 장소는 0부터 N-1번까지 번호가 매겨져 있다. 둘째 줄에는 시작점 S와 도착점 D가 주어진다. (S ≠ D; 0 ≤ S, D < N) 다음 M개 줄에는 도로의 정보 U, V, P가 주어진다.
 * (U ≠ V ; 0 ≤ U, V < N; 1 ≤ P ≤ 103) 이 뜻은 U에서 V로 가는 도로의 길이가 P라는 뜻이다.
 * U에서 V로 가는 도로는 최대 한 개이다. 또, U에서 V로 가는 도로와 V에서 U로 가는 도로는 다른 도로이다.
 *
 * 입력의 마지막 줄에는 0이 두 개 주어진다.
 *
 * 출력
 * 각 테스트 케이스에 대해서, 거의 최단 경로의 길이를 출력한다. 만약, 거의 최단 경로가 없는 경우에는 -1을 출력한다.
 *
 * Solution:
 * Dijkstra's Algorithm
 * 
 * 1. src -> dst 의 최단 거리 찾기 => Dijkstra's Algorithm
 * 2. 최단 거리가 가능한 경로 찾기
 *  -> Edge 들을 입력 받을때, dst -> src 로 역추적 할 수 있도록 reverseEdges 에 v->u 가 되는 Edge 추가
 * 3. 최단 거리가 가능한 Edge 들을 사용하지 않고 최단 경로 계산 => 거의 최단 경로
 */
public class Prob5719 {

    static int n;
    static int m;
    static int src;
    static int dst;

    /**
     * edges: u->v 로 가는 가중치 p를 가진 간선
     * reverseEdges: v->u로 가는 가중치 p를 가진 간선 => src -> dst 의 최단 경로르 역추적 하기 위함
     * dist: src 에서 각 노드까지의 최단 거리 저장
     * shortestPath : 최단 경로를 이룰떄 사용되는 간선들을 파악하기 위함
     * MAX_DIST: 노드간의 최대 거리
     */
    static Map<Integer, List<Edge>> edges;
    static Map<Integer, List<Edge>> reverseEdges;
    static Integer[] dist;
    static boolean[] shortestPath;
    static int MAX_DIST;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String[] conditions = br.readLine().split(" ");
            n = Integer.valueOf(conditions[0]);
            m = Integer.valueOf(conditions[1]);

            if (n == 0 && m == 0) {
                break;
            }

            String[] srcDst = br.readLine().split(" ");
            src = Integer.valueOf(srcDst[0]);
            dst = Integer.valueOf(srcDst[1]);

            /**
             * 초기화
             */
            MAX_DIST = 1000 * n + 1;
            dist = new Integer[n];
            shortestPath = new boolean[m];
            edges = new HashMap<>();
            reverseEdges = new HashMap<>();
            for (int i = 0; i < n; i++) {
                edges.put(i, new ArrayList<>());
                reverseEdges.put(i, new ArrayList<>());
                dist[i] = MAX_DIST;
            }
            dist[src] = 0;

            for (int i = 0; i < m; i++) {
                shortestPath[i] = false;

                String[] edgeSplit = br.readLine().split(" ");
                int u = Integer.valueOf(edgeSplit[0]);
                int v = Integer.valueOf(edgeSplit[1]);
                int p = Integer.valueOf(edgeSplit[2]);

                List<Edge> uEdges = edges.get(u);
                uEdges.add(new Edge(i, u, v, p));
                edges.put(u, uEdges);

                List<Edge> vEdges = reverseEdges.get(v);
                vEdges.add(new Edge(i, u, v, p));
                reverseEdges.put(v, vEdges);
            }

            /**
             * 1. dij()에서 다익스트라로 src -> dst 의 최단 거리 계산
             * 2. findShortestRoute() 에서 src -> dst 의 최단 거리로 가는 경로들 계산
             * 3. dist 초기화
             * 4. dij()를 통해 다신 한번 최단 경로 계산
             *  -> 이때, findShortestRoute() 에서 최단 경로에 사용되는 간선들을 제외하고 계산
             *      => 거의 최단 경로 계산
             * 5. 답 추출 
             *  -> src -> dst 의 거리가 MAX_VALUE 이면 거의 최단 경로 존재 X
             *      => -1 출력
             */
            dij();
            findShortestRoute();
            if(dist[dst] == MAX_DIST){
                sb.append(-1 + "\n");
                continue;
            }

            dist = new Integer[n];
            for (int i = 0; i < n; i++) {
                dist[i] = MAX_DIST;
            }
            dist[src] = 0;

            dij();
            int ans = -1;
            if (dist[dst] != MAX_DIST) {
                ans = dist[dst];
            }
            sb.append(ans + "\n");

        }
        System.out.println(sb);
    }

    public static void dij() {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(src);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            List<Edge> connectedEdges = edges.get(currentNode);

            for (Edge edge : connectedEdges) {
                int curV = edge.getV();
                int curP = edge.getP();
                int curNum = edge.getNum();

                int curCost = dist[currentNode] + curP;
                /**
                 * 현재 계산한 거리가 더 작고, 현재 간선이 최단 경로에 사용되지 않는 간선일때
                 */
                if (dist[curV] > curCost && !shortestPath[curNum]) {
                    dist[curV] = curCost;
                    queue.add(curV);
                }
            }
        }
    }

    public static void findShortestRoute() {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(dst);
        
        while (!queue.isEmpty()) {
            Integer currentNode = queue.poll();
            List<Edge> connectedEdges = reverseEdges.get(currentNode);
            
            /**
             * 최단 거리는 만드는 경로가 여러개가 있을 수 있기 때문에 모든 간선을 확인해야됨
             */
            for (Edge edge : connectedEdges) {
                int curU = edge.getU();
                int curP = edge.getP();
                int curNum = edge.getNum();

                /**
                 * prevCost = dist[curU] + curP => src -> u 의 거리 + u -> v 의 거리
                 * curCost = src -> v 까지의 최단 거리
                 */
                int prevCost = dist[curU] + curP;
                int curCost = dist[currentNode];
                /**
                 * prevCost == curCost => 최단 경로를 만다는데 u -> v 인 간선이 사용됨
                 */
                if (prevCost == curCost && !shortestPath[curNum]) {
                    shortestPath[curNum] = true;
                    queue.add(curU);
                }
            }
        }
    }

    public static class Edge {
        /**
         * num: 간선의 번호 => 어느 간선이 판별하게 해줌 => 최단 경로에 어떤 간선이 쓰이는지 파악하기 위해 사용
         * u: 간선의 src
         * v: 간선의 dst
         * p: 간선의 가중치
         */
        int num;
        int u;
        int v;
        int p;

        public Edge(int num, int u, int v, int p) {
            this.num = num;
            this.u = u;
            this.v = v;
            this.p = p;
        }

        public int getNum() {
            return this.num;
        }

        public int getU() {
            return this.u;
        }

        public int getV() {
            return this.v;
        }

        public int getP() {
            return this.p;
        }

        @Override
        public String toString() {
            return "{ " + num + " : " + u + ", " + v + ", " + p + " }";
        }
    }
}
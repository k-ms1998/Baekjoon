package Gold;

import java.io.*;
import java.util.*;
import java.util.Map.*;

/**
 * Gold 3: Kruskal Algorithm
 *
 * 문제
 * 황선자씨는 우주신과 교감을 할수 있는 채널러 이다. 하지만 우주신은 하나만 있는 것이 아니기때문에 황선자 씨는 매번 여럿의 우주신과 교감하느라 힘이 든다. 이러던 와중에 새로운 우주신들이 황선자씨를 이용하게 되었다.
 * 하지만 위대한 우주신들은 바로 황선자씨와 연결될 필요가 없다. 이미 황선자씨와 혹은 이미 우주신끼리 교감할 수 있는 우주신들이 있기 때문에 새로운 우주신들은 그 우주신들을 거쳐서 황선자 씨와 교감을 할 수 있다.
 * 우주신들과의 교감은 우주신들과 황선자씨 혹은 우주신들 끼리 이어진 정신적인 통로를 통해 이루어 진다. 하지만 우주신들과 교감하는 것은 힘든 일이기 때문에 황선자씨는 이런 통로들이 긴 것을  좋아하지 않는다.
 * 왜냐하면 통로들이 길 수록 더 힘이 들기 때문이다.
 * 또한 우리들은 3차원 좌표계로 나타낼 수 있는 세상에 살고 있지만 우주신들과 황선자씨는 2차원 좌표계로 나타낼 수 있는 세상에 살고 있다. 통로들의 길이는 2차원 좌표계상의 거리와 같다.
 * 이미 황선자씨와 연결된, 혹은 우주신들과 연결된 통로들이 존재한다. 우리는 황선자 씨를 도와 아직 연결이 되지 않은 우주신들을 연결해 드려야 한다.
 * 새로 만들어야 할 정신적인 통로의 길이들이 합이 최소가 되게 통로를 만들어 “빵상”을 외칠수 있게 도와주자.
 *
 * 입력
 * 첫째 줄에 우주신들의 개수(N<=1,000) 이미 연결된 신들과의 통로의 개수(M<=1,000)가 주어진다.
 * 두 번째 줄부터 N개의 줄에는 황선자를 포함하여 우주신들의 좌표가 (0<= X<=1,000,000), (0<=Y<=1,000,000)가 주어진다. 그 밑으로 M개의 줄에는 이미 연결된 통로가 주어진다.
 * 번호는 위의 입력받은 좌표들의 순서라고 생각하면 된다. 좌표는 정수이다.
 *
 * 출력
 * 첫째 줄에 만들어야 할 최소의 통로 길이를 출력하라. 출력은 소수점 둘째짜리까지 출력하여라.
 * 
 * Solution:
 * Kruskal Algorithm
 * 1. 모든 노드가 부모를 가르킬수 있도록 노드 생성 (초기값은 자기 자신)
 * 2. 이미 연결된 노드들까리는 연결
 *  -> 연결된 노드는 같은 부모를 가르키도록 설정
 * 3. 모든 좌표들끼리의 거리 계산해서 간선 생성
 * 4. 거리가 짧은 순으로 정렬
 * 5. 거리가 짧은 간선 순서로 탐색 시작
 *  -> 서로 인접한 노드가 이미 연결이 안되어 있으면 연결
 *      -> 이때, 두개의 노드가 이미 연결이 되어있지 않다는 것(인접 노드 끼리 서로 도달이 불가능한 상태)은 둘이 다른 Root 를 가르키고 있음
 *          -> Root 노드는 자신이 가르키는 부모가 자기 자신임 (== 부모가 없음 == 루트 노드)
 *      -> 연결이 안되어 있으면 간선의 가중치를 최종값에 추가
 * 6. 모든 간선들을 탐색
 */
public class Prob1774 {

    public static Ship[] ships;
    public static List<Edge> edges = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] conditions = br.readLine().split(" ");
        int n = Integer.valueOf(conditions[0]);
        int m = Integer.valueOf(conditions[1]);

        ships = new Ship[n + 1];
        ships[0] = new Ship(-1, -1, -1, -1);
        for (int i = 1; i < n + 1; i++) {
            String[] coOrd = br.readLine().split(" ");
            int x = Integer.valueOf(coOrd[0]);
            int y = Integer.valueOf(coOrd[1]);
            ships[i] = new Ship(x, y, i, i);
        }
        for (int i = 0; i < m; i++) {
            String[] connected = br.readLine().split(" ");
            int nodeA = Integer.valueOf(connected[0]);
            int nodeB = Integer.valueOf(connected[1]);

            int rootA = findRoot(ships[nodeA].getNode());
            int rootB = findRoot(ships[nodeB].getNode());

            if (rootA == rootB) {
                continue;
            } else {
                ships[rootB].setParent(rootA);
            }
        }

        /**
         * 좌표들끼리의 거리 계산
         */
        for (int i = 1; i < n + 1; i++) {
            for (int j = i + 1; j < n + 1; j++) {
                Ship shipA = ships[i];
                Ship shipB = ships[j];

                double ax = shipA.getX();
                double ay = shipA.getY();
                double bx = shipB.getX();
                double by = shipB.getY();

                Double dist = Math.sqrt((ax - bx) * (ax - bx) + (ay - by) * (ay - by));
                edges.add(new Edge(i, j, dist));
            }
        }

        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.getDistance().compareTo(o2.getDistance());
            }
        });

        /**
         * 가중치가 짧은 간선들 순서로 탐색 시작
         */
        Double ans = 0.0;
        for (Edge edge : edges) {
            int nodeA = edge.getSrc();
            int nodeB = edge.getDst();
            Double dist = edge.getDistance();

            /**
             * 인접한 노드들의 루트 찾기
             */
            int rootA = findRoot(ships[nodeA].getNode());
            int rootB = findRoot(ships[nodeB].getNode());

            if (rootA == rootB) {
                continue;
            } else {
                /**
                 * UNION
                 */
                ships[rootB].setParent(ships[rootA].getNode());
            }
            ans += dist;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%.2f", ans));
        System.out.println(sb);

    }

    public static int findRoot(int node) {
        Ship ship = ships[node];
        if(ship.getNode() == ship.getParent()){
            return ship.getNode();
        }
        int finalRoot = findRoot(ship.getParent());
        ships[node].setParent(finalRoot);
        return finalRoot;
    }

    public static class Edge {
        int src;
        int dst;
        Double distance;

        public Edge(int src, int dst, Double distance) {
            this.src = src;
            this.dst = dst;
            this.distance = distance;
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public int getDst() {
            return dst;
        }

        public void setDst(int dst) {
            this.dst = dst;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "{ " + src + "," + dst + ", " + distance + " }";
        }
    }

    public static class Ship{
        double x;
        double y;
        int node;
        int parent;

        public Ship(double x, double y, int node, int parent) {
            this.x = x;
            this.y = y;
            this.node = node;
            this.parent = parent;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public int getNode() {
            return node;
        }

        public int getParent() {
            return parent;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setNode(int node) {
            this.node = node;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "{ " + x + "," + y + "," + node + "," + parent + " }";
        }
    }
}

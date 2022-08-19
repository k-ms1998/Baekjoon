package Gold.Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

/**
 * Gold 4: Dijkstra algorithm
 * 
 * 문제
 * 최흉최악의 해커 yum3이 네트워크 시설의 한 컴퓨터를 해킹했다!
 * 이제 서로에 의존하는 컴퓨터들은 점차 하나둘 전염되기 시작한다. 어떤 컴퓨터 a가 다른 컴퓨터 b에 의존한다면, b가 감염되면 그로부터 일정 시간 뒤 a도 감염되고 만다.
 * 이때 b가 a를 의존하지 않는다면, a가 감염되더라도 b는 안전하다.
 * 최흉최악의 해커 yum3이 해킹한 컴퓨터 번호와 각 의존성이 주어질 때, 해킹당한 컴퓨터까지 포함하여 총 몇 대의 컴퓨터가 감염되며 그에 걸리는 시간이 얼마인지 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 테스트 케이스의 개수가 주어진다. 테스트 케이스의 개수는 최대 100개이다. 각 테스트 케이스는 다음과 같이 이루어져 있다.
 * <p>
 * 첫째 줄에 컴퓨터 개수 n, 의존성 개수 d, 해킹당한 컴퓨터의 번호 c가 주어진다(1 ≤ n ≤ 10,000, 1 ≤ d ≤ 100,000, 1 ≤ c ≤ n).
 * 이어서 d개의 줄에 각 의존성을 나타내는 정수 a, b, s가 주어진다(1 ≤ a, b ≤ n, a ≠ b, 0 ≤ s ≤ 1,000). 이는 컴퓨터 a가 컴퓨터 b를 의존하며, 컴퓨터 b가 감염되면 s초 후 컴퓨터 a도 감염됨을 뜻한다.
 * 각 테스트 케이스에서 같은 의존성 (a, b)가 두 번 이상 존재하지 않는다.
 *
 * 출력
 * 각 테스트 케이스마다 한 줄에 걸쳐 총 감염되는 컴퓨터 수, 마지막 컴퓨터가 감염되기까지 걸리는 시간을 공백으로 구분지어 출력한다.
 */
public class Prob10282 {

    public static Map<Integer, List<Entry<Integer, Integer>>> edges = new HashMap<>();
    public static int MAX_S = 10000*1001; // n의 최대 * (s의 최대 + 1)

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.valueOf(br.readLine());

        for (int i = 0; i < t; i++) {
            String[] conditions = br.readLine().split(" ");
            int n = Integer.valueOf(conditions[0]);
            int d = Integer.valueOf(conditions[1]);
            int c = Integer.valueOf(conditions[2]);

            edges = new HashMap<>();
            for (int j = 1; j < n + 1; j++) {
                edges.put(j, new ArrayList<>());
            }

            for (int j = 0; j < d; j++) {
                String[] edge = br.readLine().split(" ");
                int a = Integer.valueOf(edge[0]);
                int b = Integer.valueOf(edge[1]);
                int s = Integer.valueOf(edge[2]);

                List<Entry<Integer, Integer>> currentEdge = edges.get(b);
                currentEdge.add(Map.entry(a, s));
                edges.put(b, currentEdge);
            }

            /**
             * 배열 dij 에 시작 노드부터 모든 노드까지의 거리 저장
             * -> 초기화 값으로는 최댓값 저장
             */
            Integer[] dij = new Integer[n + 1];
            for (int j = 0; j < n + 1; j++) {
                dij[j] = MAX_S;
            }
            dij[c] = 0;

            dijkstra(dij, c);
            int cnt = 0;
            int ans = 0;
            for (Integer totalTime : dij) {
                /**
                 * 특정 노드까지의 거리/시간이 최댓값 X => 한번이라도 방문한 적 O => 감염 대상 O
                 */
                if(totalTime != MAX_S){
                    ans = totalTime > ans ? totalTime : ans;
                    cnt++;
                }
            }
            System.out.println(cnt + " " + ans);
        }
    }

    /**
     * Solution: Dijkstra Algorithm
     * => 시작 노드에서 부터 도달 가능한 모든 노드들 간의 거리 계산
     * 1. 시작 노드 S 에서 모든 노드까지의 거리를 무한대 라고 배열에 초기화
     * 2. S의 인접 노드들 검사
     * 3. 인접 노드들까지의 거리와 배열에 저장된 값 중 작은 값을 배열에 저장
     * 4. 인접한 노드들의 인접 노드들 검사
     * 5. 더 이상 검사를 하지 않아도 될때까지 3~4 반복 -> 해당 문제에서는 더 이상 짧아 지는 거리가 없을때
     * @param dij
     * @param c
     */
    public static void dijkstra(Integer[] dij, int c) {
        Deque<Integer> nextNodes = new ArrayDeque<>();
        nextNodes.offer(c);

        while (!nextNodes.isEmpty()) {
            int currentNode = nextNodes.poll();
            int currentCost = dij[currentNode];

            List<Entry<Integer, Integer>> connectedNodes = edges.get(currentNode);
            for (Entry<Integer, Integer> connectedNode : connectedNodes) {
                int adjNode = connectedNode.getKey();
                int adjCost = connectedNode.getValue();

                /**
                 * 거리가 줄어 들었을때 큐에 검색할 노드 추가
                 * => 노드 A -> B 까지의 거리가 줄어 들었으면, B와 인접한 노드들의 거리도 줄어들 수 있기 때문에 계산의 대상이 됨
                 */
                if(dij[adjNode] > currentCost + adjCost){
                    dij[adjNode] = currentCost + adjCost;
                    nextNodes.offer(adjNode);
                }
            }
        }
    }
}


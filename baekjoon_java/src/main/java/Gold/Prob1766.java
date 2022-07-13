package Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 2
 *
 * 문제
 * 민오는 1번부터 N번까지 총 N개의 문제로 되어 있는 문제집을 풀려고 한다. 문제는 난이도 순서로 출제되어 있다. 즉 1번 문제가 가장 쉬운 문제이고 N번 문제가 가장 어려운 문제가 된다.
 *
 * 어떤 문제부터 풀까 고민하면서 문제를 훑어보던 민오는, 몇몇 문제들 사이에는 '먼저 푸는 것이 좋은 문제'가 있다는 것을 알게 되었다.
 * 예를 들어 1번 문제를 풀고 나면 4번 문제가 쉽게 풀린다거나 하는 식이다. 민오는 다음의 세 가지 조건에 따라 문제를 풀 순서를 정하기로 하였다.
 *
 *  1.  N개의 문제는 모두 풀어야 한다.
 *  2. 먼저 푸는 것이 좋은 문제가 있는 문제는, 먼저 푸는 것이 좋은 문제를 반드시 먼저 풀어야 한다.
 *  3. 가능하면 쉬운 문제부터 풀어야 한다.
 * 예를 들어서 네 개의 문제가 있다고 하자. 4번 문제는 2번 문제보다 먼저 푸는 것이 좋고, 3번 문제는 1번 문제보다 먼저 푸는 것이 좋다고 하자.
 * 만일 4-3-2-1의 순서로 문제를 풀게 되면 조건 1과 조건 2를 만족한다. 하지만 조건 3을 만족하지 않는다. 4보다 3을 충분히 먼저 풀 수 있기 때문이다.
 * 따라서 조건 3을 만족하는 문제를 풀 순서는 3-1-4-2가 된다.
 *
 * 문제의 개수와 먼저 푸는 것이 좋은 문제에 대한 정보가 주어졌을 때, 주어진 조건을 만족하면서 민오가 풀 문제의 순서를 결정해 주는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 문제의 수 N(1 ≤ N ≤ 32,000)과 먼저 푸는 것이 좋은 문제에 대한 정보의 개수 M(1 ≤ M ≤ 100,000)이 주어진다.
 * 둘째 줄부터 M개의 줄에 걸쳐 두 정수의 순서쌍 A,B가 빈칸을 사이에 두고 주어진다. 이는 A번 문제는 B번 문제보다 먼저 푸는 것이 좋다는 의미이다.
 *
 * 항상 문제를 모두 풀 수 있는 경우만 입력으로 주어진다.
 *
 * 출력
 * 첫째 줄에 문제 번호를 나타내는 1 이상 N 이하의 정수들을 민오가 풀어야 하는 순서대로 빈칸을 사이에 두고 출력한다.
 *
 * Solution: !! 최소힙(Min Heap) + 위상 정렬(Topological Sort) !!
 * !! 위상 정렬(Topological Sort)이란?
 * -> 순서가 정해져 있는 작업들을 순서대로 수행 하는 것
 * -> 시간 복잡도 = O(V+E) => V == 노드(Vertex)의 갯수, E == 간선(Edge)의 갯수
 * -> !!수행 순서 : !!
 * 1. 진입 차수가 0인 노드들을 큐에 삽입
 * 2. 큐에서 노드를 꺼내서 해당 노드와 연결된 간선들 제거(+ 필요시 인접 노드의 진입 차수 업데이트)
 * 3. 연결된 간선들을 제거 후, 인접 노드들 중 진입 차수가 0인 노드들은 큐에 삽입
 * 4. 큐가 Empty 일때까지 1~3 과정 반복
 * 
 * -> 결과:
 * 1. If, 모든 노드들을 방문하기 전에 큐가 Empty가 되면 싸이클(Cycle) 존재
 * !! 2. If, 모든 노드들을 방문했다면, 큐에서 꺼낸 순서가 위상 정렬로 작업들을 수행한 결과 !!
 *
 */
public class Prob1766 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String conditions = br.readLine();
        String[] conditionsSplit = conditions.split(" ");
        int n = Integer.valueOf(conditionsSplit[0]);
        int m = Integer.valueOf(conditionsSplit[1]);

        /**
         * queue : 앞으로 탐색 할 큐를 보관 -> 탐색할 큐는 문제의 3번 규칙에 의해서 숫자가 작은 숫자를 먼저 탐색해야 됨 -> 그러므로, PriorityQueue 로 Min Heap 을 구현해서 최소값 탐색을 쉽게 함
         * inDegree : 각 노드(문제)마다 진입 차수를 저장 (진입 차수 == 노드로 들어오는 간선의 갯수)
         * priorities : 각 노드(문제)마다 나중에 풀어야되는 문제들 저장 -> (K, v) -> K = 현재 문제, V = 현재 문제보다 나중에 풀어야되는 문제 -> 즉, V에 있는 문제들보다 K 문제를 먼저 풀어야 됨
         */
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        Integer[] inDegree = new Integer[n + 1];
        Map<Integer, List<Integer>> priorities = new HashMap<>();
        /**
         * 초기화
         */
        for (int i = 1; i <= n; i++) {
            priorities.put(i, new ArrayList<>());
            inDegree[i] = 0;
        }

        /**
         * B보다 먼저 풀어야되는 A 문제들 입력
         * && 입력에 따라 inDegree 랑 priorities 업데이트
         */
        for (int i = 0; i < m; i++) {
            String orders = br.readLine();
            String[] ordersSplit = orders.split(" ");
            int k = Integer.valueOf(ordersSplit[0]);
            int v = Integer.valueOf(ordersSplit[1]);

            List<Integer> values = priorities.get(k);
            /**
             * 자신보다 나중에 풀어야될 문제들 업데이트
             * 나중에 풀어야될 문제들의 진입 차수 업데이트
             */
            values.add(v);
            inDegree[v] += 1;
        }

        StringBuilder ans = new StringBuilder();
        /**
         * 1. 큐에 진입 차수가 0인 문제들 삽입
         * 진입 차수가 0인 노드(문제)는 자신보다 먼저 풀어여되는 문제가 없는 것이므로 진입 차수가 0인 노드(문제)들에 대해서 검사
         */
        for (int i = 1; i <= n; i++) {
            if(inDegree[i] == 0){
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            /**
             * 2. 큐에서 최소값(숫자가 가장 작은 문제) pop
             */
            int curNode = queue.poll();
            /**
             * 3. 큐에서 pop 되는 순서대로가 위상 정렬된 순서 == 문제를 풀게되는 순서
             */
            ans.append(curNode + " ");

            /**
             * 4. 현재 노드(문제)가 가르키고 있는 노드(문제)들 가져오고
             * 5. 가르키고 있는 해당 간선 제거
             * 해당 문제에서는 실제 간선 자체를 지우지는 않고, 인접 노드(문제)의 진입 차수를 1씩 감소
             * 이때, 해당 간선이 지워져서 진입 차수가 0이 된 노드가 있으면, 이제 해당 노드(문제)도 더 이상 자신보다 먼저 풀어야되는 문제 존재 X
             * 그러므로, 해당 노드(문제)도 큐에 삽입
             */
            List<Integer> outNodes = priorities.get(curNode);
            outNodes.forEach(e -> {
                inDegree[e]--;
                if(inDegree[e] == 0){
                    queue.add(e);
                }
            });
            /**
             * 6. 큐에 노드(문제)가 더 이상 없을때까지 2~5 과정 반복
             */
        }

        System.out.println(ans);
    }
}

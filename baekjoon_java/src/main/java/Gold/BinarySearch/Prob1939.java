package Gold.BinarySearch;

import java.io.*;
import java.util.*;
import java.util.Map.*;

/**
 * Gold 4
 *
 * 문제
 * N(2 ≤ N ≤ 10,000)개의 섬으로 이루어진 나라가 있다. 이들 중 몇 개의 섬 사이에는 다리가 설치되어 있어서 차들이 다닐 수 있다.
 * 영식 중공업에서는 두 개의 섬에 공장을 세워 두고 물품을 생산하는 일을 하고 있다. 물품을 생산하다 보면 공장에서 다른 공장으로 생산 중이던 물품을 수송해야 할 일이 생기곤 한다.
 * 그런데 각각의 다리마다 중량제한이 있기 때문에 무턱대고 물품을 옮길 순 없다. 만약 중량제한을 초과하는 양의 물품이 다리를 지나게 되면 다리가 무너지게 된다.
 * 한 번의 이동에서 옮길 수 있는 물품들의 중량의 최댓값을 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 N, M(1 ≤ M ≤ 100,000)이 주어진다. 다음 M개의 줄에는 다리에 대한 정보를 나타내는 세 정수 A, B(1 ≤ A, B ≤ N), C(1 ≤ C ≤ 1,000,000,000)가 주어진다.
 * 이는 A번 섬과 B번 섬 사이에 중량제한이 C인 다리가 존재한다는 의미이다. 서로 같은 두 섬 사이에 여러 개의 다리가 있을 수도 있으며, 모든 다리는 양방향이다.
 * 마지막 줄에는 공장이 위치해 있는 섬의 번호를 나타내는 서로 다른 두 정수가 주어진다. 공장이 있는 두 섬을 연결하는 경로는 항상 존재하는 데이터만 입력으로 주어진다.
 *
 * 출력
 * 첫째 줄에 답을 출력한다.
 */
public class Prob1939 {

    /**
     * 가장 간단한 해결 방법은 운송 가능한 최대 중량 값(w)을 1부터 시작해서, 1씩 증가시키면서 시작 노드에서 도착 노드에 도착할 수 있는 것인지 확인하는 것 입니다.
     * 시작 노드에서 BFS로 간선들을 탐색하고, 탐색 했을때 간선의 가중치가 w 값보다 크면 해당 경로로 이동 가능함
     * w 값으로 도착 노드에 도달 가능하면 w 값을 1 증가시켜서 다시 한번 확인
     * => But, 이런식으로 하면 최대 가중치 값(m) * 간선의 갯수 만큼 확인해야되기 때문에 시간 초과 발생
     * => 그러므로, w 값을 이분 탐색으로 업데이트 시켜줌 -> 걸리는 시간 = log m * 간선의 갯수
     */
    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String conditions = br.readLine();
        String[] conditionsSplit = conditions.split(" ");
        int n = Integer.valueOf(conditionsSplit[0]);
        int m = Integer.valueOf(conditionsSplit[1]);

        /**
         * 초기화 && 간선들 입력
         */
        Map<Integer, List<Entry<Integer, Integer>>> edges = new HashMap<>();
        int minW = 0;
        int maxW = 0;
        for (int i = 0; i < m; i++) {
            String edgesLine = br.readLine();
            String[] edgesSplit = edgesLine.split(" ");
            int nodeA = Integer.valueOf(edgesSplit[0]);
            int nodeB = Integer.valueOf(edgesSplit[1]);
            int weight = Integer.valueOf(edgesSplit[2]);
            if (weight > maxW) {
                maxW = weight;
            }

            Entry<Integer, Integer> valueA = Map.entry(nodeB, weight);
            List<Entry<Integer, Integer>> entriesA = new ArrayList<>();
            if (edges.containsKey(nodeA)) {
                entriesA = edges.get(nodeA);
            }
            entriesA.add(valueA);
            edges.put(nodeA, entriesA);

            Entry<Integer, Integer> valueB = Map.entry(nodeA, weight);
            List<Entry<Integer, Integer>> entriesB = new ArrayList<>();
            if (edges.containsKey(nodeB)) {
                entriesB = edges.get(nodeB);
            }
            entriesB.add(valueB);
            edges.put(nodeB, entriesB);
        }

        String facLine = br.readLine();
        String[] facLineSplit = facLine.split(" ");
        int src = Integer.valueOf(facLineSplit[0]);
        int dst = Integer.valueOf(facLineSplit[1]);

        int ans = 0;
        while (minW <= maxW) {
            int midW = (maxW + minW) / 2;
            if (!bfs(edges, n, src, dst, midW)) {
                ans = maxW;
                maxW = midW - 1;
            } else {
                minW = midW + 1;
            }
        }

        System.out.println(ans);
    }

    private boolean bfs(Map<Integer, List<Entry<Integer, Integer>>> edges, int n, int src, int dst, int midW) {
        Queue<Integer> nextNode = new LinkedList<>();
        nextNode.add(src);

        Boolean[] visited = new Boolean[n];
        for(int i = 0; i < n; i++){
            visited[i] = false;
        }
        visited[src - 1] = true;
        while (!nextNode.isEmpty()) {
            Integer node = nextNode.poll(); // poll() -> Retrieves and removes the head of this queue, or returns null if this queue is empty.
            if (node.equals(dst)) {
                return true;
            }

            List<Entry<Integer, Integer>> edge = edges.get(node);
            for (Entry<Integer, Integer> e : edge) {
                Integer dstNode = e.getKey();
                Integer w = e.getValue();
                /**
                 * midW == 현재 확인 중인 중량 값, w는 현재 간선의 가중치 -> w가 midW 값보다 크면 해당 간선을 통해서 midW의 중량을 싣고 다리를 건널수 있다는 뜻
                 */
                if (!visited[dstNode-1] && midW <= w) {
                    visited[dstNode-1] = true;
                    nextNode.add(dstNode);
                }
            }
        }

        /**
         * 도착 노드에 도달했는지 확인
         */
        return visited[dst-1];
    }
}

package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Silver 3
 * <p>
 * 신종 바이러스인 웜 바이러스는 네트워크를 통해 전파된다. 한 컴퓨터가 웜 바이러스에 걸리면 그 컴퓨터와 네트워크 상에서 연결되어 있는 모든 컴퓨터는 웜 바이러스에 걸리게 된다.
 * <p>
 * 예를 들어 7대의 컴퓨터가 <그림 1>과 같이 네트워크 상에서 연결되어 있다고 하자.
 * 1번 컴퓨터가 웜 바이러스에 걸리면 웜 바이러스는 2번과 5번 컴퓨터를 거쳐 3번과 6번 컴퓨터까지 전파되어 2, 3, 5, 6 네 대의 컴퓨터는 웜 바이러스에 걸리게 된다.
 * 하지만 4번과 7번 컴퓨터는 1번 컴퓨터와 네트워크상에서 연결되어 있지 않기 때문에 영향을 받지 않는다.
 * <p>
 * <p>
 * <p>
 * 어느 날 1번 컴퓨터가 웜 바이러스에 걸렸다. 컴퓨터의 수와 네트워크 상에서 서로 연결되어 있는 정보가 주어질 때, 1번 컴퓨터를 통해 웜 바이러스에 걸리게 되는 컴퓨터의 수를 출력하는 프로그램을 작성하시오.
 * <p>
 * 입력
 * 첫째 줄에는 컴퓨터의 수가 주어진다. 컴퓨터의 수는 100 이하이고 각 컴퓨터에는 1번 부터 차례대로 번호가 매겨진다.
 * 둘째 줄에는 네트워크 상에서 직접 연결되어 있는 컴퓨터 쌍의 수가 주어진다. 이어서 그 수만큼 한 줄에 한 쌍씩 네트워크 상에서 직접 연결되어 있는 컴퓨터의 번호 쌍이 주어진다.
 * <p>
 * 출력
 * 1번 컴퓨터가 웜 바이러스에 걸렸을 때, 1번 컴퓨터를 통해 웜 바이러스에 걸리게 되는 컴퓨터의 수를 첫째 줄에 출력한다.
 */
public class Prob2606 {

    public static int start = 1;
    public static int n;
    public static int edgeCnt;
    public static int ans = -1;
    public static Map<Integer, List<Integer>> edges = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.valueOf(br.readLine());
        edgeCnt = Integer.valueOf(br.readLine());

        for (int i = 1; i < n + 1; i++) {
            edges.put(i, new ArrayList<>());
        }

        for (int i = 0; i < edgeCnt; i++) {
            String[] edgeSplit = br.readLine().split(" ");
            int src = Integer.valueOf(edgeSplit[0]);
            int dst = Integer.valueOf(edgeSplit[1]);

            List<Integer> srcValue = edges.get(src);
            srcValue.add(dst);
            edges.put(src, srcValue);

            List<Integer> dstValue = edges.get(dst);
            dstValue.add(src);
            edges.put(dst, dstValue);
        }

        /**
         * DFS 또는 BFS 로 탐색해도 상관은 없지만, 방문한 컴퓨터들의 갯수를 더 쉽게 추적하기 위해 BFS로 풀이
         */
        bfs();
        System.out.println(ans);
    }

    public static void bfs() {
        Deque<Integer> nextNode = new ArrayDeque<>();
        boolean[] visited = new boolean[n+1];
        for (int i = 0; i < n + 1; i++) {
            visited[i] = false;
        }

        nextNode.offer(start);
        while (!nextNode.isEmpty()) {
            /**
             * 해당 컴퓨터를 방문하고, visited[컴퓨터]의 값을 true로 바꾸고, ans의 값을 증가시키는 시점을 제대로 하는게 중요
             * -> if 1<->2 && 2<->3 && 1<->3 인 경우:
             *      -> ans의 값을 언제 증가 시키는지에 따라 중복으로 ans의 값이 증가할 수도 있음
             *          -> 1->2, 1->3 으로 갈때 ans의 값을 증가시키고, 2->3으로 갈때는 ans의 값이 증가하지 않도록 주의(Becuase,이미 3은 방문한 것으로 고려했기 때문에)
             */
            int currentNode = nextNode.poll();
            if (visited[currentNode] == false) {
                ans++;
            }
            visited[currentNode] = true;
            List<Integer> adj = edges.get(currentNode);
            for (Integer node : adj) {
                if (visited[node] == false) {
                    nextNode.offer(node);
//                    ans++;
                }
            }

        }
    }
}

package Gold.Graph.TopologicalSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 3 (줄 세우기)
 *
 * https://www.acmicpc.net/problem/2252
 *
 * Solution: Topological Sort
 * 위상 정렬(Topological Sort)은 그래프에서 선후관계 조건이 있을 때 이를 고려해서 노드의 순서를 정렬할 수 있습니다.
 */
public class Prob2252 {

    static int n;
    static int m;

    /**
     * nodes: a 학생이 b 학생보다 앞에 있어야되면, nodes[a] 에 b 저장
     * inDegreeCount: 각 노드의 진입 차수 저장
     */
    static List<Integer>[] nodes;
    static int[] inDegreeCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        nodes = new List[n + 1];
        inDegreeCount = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            nodes[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            /**
             * a가 b 보다 앞에 있어야됨
             */
            nodes[a].add(b);
            /**
             * b의 진입차수 증가 -> b의 진입차수 == b보다 앞서 있어야되는 학생의 수
             */
            inDegreeCount[b]++;
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i < n + 1; i++) {
            /**
             * 진입 차수가 0인 학생들 먼저 queue에 추가
             *  -> 진입 차수가 0인 학생 == 자기 자신보다 앞에 있어야될 필요가 있는 학생 X
             */
            if (inDegreeCount[i] == 0) {
                queue.offer(i);
            }
        }

        StringBuffer ans = new StringBuffer();
        while (!queue.isEmpty()) {
            int curNode = queue.poll();
            ans.append(curNode).append(" ");

            /**
             * curNode 는 nodes[curNode] 에 저장된 학생들보다 앞에 있어야됨
             * queue 에는 진입 차수가 0 인 학생들이 들어 있음 -> curNode 보다 앞에 있어야되는 학생들은 더 이상 존재 X
             *  -> curNode 의 자리가 결정됨
             *      -> nodes[curNode] 에 있는 학생들은 curNode 의 자리가 정해졌기 때문에 더 이상 curNode 는 고려 대상 X
             *          -> 진입차수 값을 감소 시킴
             *  ex:
             *  1 -> 3, 2 -> 3 => 3의 진입 차수는 2
             *  만약, 1의 위치가 정해지면, 3 보다 먼저 와야되는 학생은 학생 2만 남음
             *      -> 3의 진입 차수는 1이 됨
             */
            for (Integer edge : nodes[curNode]) {
                inDegreeCount[edge]--;
                if (inDegreeCount[edge] == 0) {
                    queue.offer(edge);
                }
            }
        }
        System.out.println(ans);
    }
}

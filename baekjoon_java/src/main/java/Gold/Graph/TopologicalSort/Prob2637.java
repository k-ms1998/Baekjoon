package Gold.Graph.TopologicalSort;

import java.io.*;
import java.util.*;

/**
 * Gold 2(장난감 조립)
 *
 * https://www.acmicpc.net/problem/2637
 *
 * Solution: Topological Sort
 * 장난감 완제품을 만들기 위해서는 필요되는 기본 부품들과 중간 부품들이 존재
 * 중간 부품들은 다른 중간 부품들 또는 기본 부품들로 만들어야 함
 * 그러므로, 제품을 만들기 위해서는 우선순위 존재 => Topological Sort(위상 정렬)
 *
 * 이때, 각 기본 부품이 총 몇개 있는지 확인해야 함으로, 완제품에서 시작해서 기본 부품으로 가는 그래프 생성
 * 즉, 완제품의 진입차수가 0이 되도록하는 그래프 생성
 *
 */
public class Prob2637 {

    static int n, m;
    static List<Edge>[] edges;
    static int[] inCount;
    static int[] count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        /**
         * 입력 및 그래프 생성
         */
        edges = new List[n + 1];
        inCount = new int[n + 1];
        count = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            edges[x].add(new Edge(y, k));
            inCount[y]++;
        }

        /**
         * 진입 차수가 0인 노드 찾기 (== 완제품)
         */
        List<Integer> base = new ArrayList<>();
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i < n + 1; i++) {
            if (inCount[i] == 0) {
                queue.offer(i);
                count[i] = 1;
            }
        }
        /**
         * 그래프 생성
         */
        while (!queue.isEmpty()) {
            int cur = queue.poll();

            /*
            현재 노드로 부터 나가는 간선이 없으면 기본 제품
            base 는 기본 제품들을 저장하고 있는 리스트
             */
            if (edges[cur].size() == 0) {
                base.add(cur);
            }else{
                for (Edge edge : edges[cur]) {
                    /*
                    next: cur를 만드는데 필요한 이전 부품
                    cnt: 필요한 next의 부품의 갯수
                    count[next] += (cur 의 갯수 * 필요한 next 의 부품의 갯수)
                     */
                    int next = edge.dst;
                    int cnt = edge.cnt;
                    inCount[next]--;
                    count[next] += (count[cur] * cnt);
                    if (inCount[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        /*
        기본 부품을 번호가 낮은 순으로 출력해야 하므로 정렬
         */
        Collections.sort(base);
        for (Integer b : base) {
            ans.append(b).append(" ").append(count[b]).append("\n");
        }
        System.out.println(ans);
    }

    public static class Edge{
        int dst;
        int cnt;

        public Edge(int dst, int cnt) {
            this.dst = dst;
            this.cnt = cnt;
        }
    }
}

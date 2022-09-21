package Gold.Sort.Sweeping;

import java.io.*;
import java.util.*;

/**
 * Gold 3(수상 택시)
 *
 * https://www.acmicpc.net/problem/2836
 *
 * Solution: Sort + Sweeping
 * 
 * 해당 문제는 마지막에 무조건 M 좌표로 이동을 해야되기 때문에, 정주행하는 경로들은 무시하고 , 역방향으로 이동하는 경로들에 대해서만 고려하고,
 * 역방향으로 이동하는 경로들에 대해서 겹치는 선분 문제와 동일하게 풀 수 있다는 것을 알아채는게 핵심
 */
public class Prob2836 {

    static int n;
    static long m;
    static PriorityQueue<Edge> edges;

    static long ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /**
         * Sweeping 을 하기위해서 시작 지점이 가장 빠른 경로 순으로 정렬
         * (Min Heap 으로 유지해서 첫번째 원소를 pop 해서 풀이하는 것도 동일한 효과)
         */
        edges = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return (int) (e1.s - e2.s);
            }
        });

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            long tmpS = Integer.parseInt(st.nextToken());
            long tmpE = Integer.parseInt(st.nextToken());
            /**
             * 어차피 정주행(s < e)하는 경로는 고려하지 0 -> M 가는 길에 해결 가능하기 때문에 고려하지 않아도 됨
             * 반면, 역주행(s > e)하는 경로는 0 -> M 가는 길과 반대 방향으로 왔다 갔다 해야되기 때문에 고려해야됨
             *  -> 이때, 역주행하는 경로 중에서 겹치는 부분이 경로끼리는 한번에 해결하는게 시간 단축됨
             *      -> 겹치는 선분 문제랑 똑같이 해결하면 됨
             *      -> ex: 4 -> 2, 3 -> 1 이 있으면, 4 -> 2 랑 3 -> 1 을 따로 왔다 갔다하는 것 보다는, 4 -> 1을 한 번 왔다 갔다하면 해결 가능하면서도 시간 단축 ( (4-2)*2 + (3-1)*2 vs. (4-1)*2 )
             */
            if (tmpS > tmpE) {
                /**
                 * 어차피 역주행하는 경로들만 고려하기 때문에, 시작 지점과 도착 지점을 바꿔서 생각해도 상관 X
                 * -> 풀이에 더 쉽게하기 위해서 더 작은 값인 도착지점이 Edge.s가 되도록 함
                 */
                edges.offer(new Edge(tmpE, tmpS));
            }
        }
//        System.out.println("edges = " + edges);

        /**
         * 겹치는 선분들 찾기
         * minS: 지금까지 나온 경로들 중 가장 빨리 시작하는 지점
         * maxD: 지금까지 나온 경로들 중 가장 늦게 도착하는 지점
         */
        ans = m;
        long minS = 0L;
        long maxD = 0L;
        while (!edges.isEmpty()) {
            Edge curEdge = edges.poll();
            /**
             * 현재 경로의 시작 지점이 지금까지 나온 경로들의 도착 지점보다 앞에 있으면 겹치는 경로
             */
            if (curEdge.s <= maxD) {
                /**
                 * edges 는 시작하는 지점이 가장 앞에 있도록 유지되기 때문에, 현재 경로보다 뒤에 있는 경로들의 시작 지점은 무조건 현재 경로의 시작 지점보다 나중에 시작함
                 *  -> 시작 지점을 업데이트 해줄 필요 X
                 */
                maxD = Math.max(maxD, curEdge.d);
            } else {
                /**
                 * 겹치지 않으면:
                 * 현재까지 겹치는 선분들의 시작 지점과 끝나는 지점이 총 겹치는 길이
                 * -> 해당 길이만큼 왔다 갔다 하기 때문에, 해당 길이 * 2 한 만큼 ans 업데이트
                 * 
                 * 다음으로 겹치는 경로들을 확인하기 위해 minS, maxD 업데이트
                 */
                ans += 2 * (maxD - minS);
                minS = curEdge.s;
                maxD = curEdge.d;
            }
        }
        ans += 2 * (maxD - minS);

        System.out.println(ans);
    }

    static class Edge{
        long s;
        long d;

        public Edge(long s, long d){
            this.s = s;
            this.d = d;
        }

        @Override
        public String toString() {
            return "Edge{" + "s =" + s + ", d =" + d + "}";
        }
    }
}

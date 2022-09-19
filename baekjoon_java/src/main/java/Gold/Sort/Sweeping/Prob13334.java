package Gold.Sort.Sweeping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 2(철로)
 *
 * https://www.acmicpc.net/problem/13334
 *
 * Solution: Sort + Priority Queue + Sweeping
 */
public class Prob13334 {

    static int n;
    static List<Edge> edges = new ArrayList<>();
    static long d;

    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            long h = Long.parseLong(st.nextToken());
            long o = Long.parseLong(st.nextToken());
            if (h > o) {
                edges.add(new Edge(o, h));
            } else {
                edges.add(new Edge(h, o));
            }
        }
        d = Long.parseLong(br.readLine());

        /**
         * 시간표 짜기 문제랑 비슷:
         * h(선분이 시작하는 지점) == 강의 시작 시간, o(선분이 끝나는 지점) == 강의 끝나는 시간이라고 생각
         * 선분이 끝나는 지점(강의가 끝나는 시간)으로 오름차순 정렬
         * 선분이 끝나는 지점이 같으면 선분이 시작하는 지점(강의가 시작하는 시간)으로 정렬
         */
        Collections.sort(edges, new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                if (e1.o == e2.o) {
                    return (int)(e1.h - e2.h);
                }

                return (int) (e1.o - e2.o);
            }
        });

        /**
         * queue 에는 선분의 시작 지점 저장
         *  -> Min Heap 의 첫번째 값은 현재 queue 에 저장된 값들 중에서 선분의 시작 시점이 가장 빠른 지점을 저장하고 있음
         *      -> 모든 edge 들을 순서대로 탐색하면서, i번째 edge 의 선분이 끝나는 시점에서 d를 뺀 값이 queue 의 Min Heap 값보다 적거나 같은지 확인
         *          -> 작거나 같으면 Min Heap 보다 선분이 늦게 끝나는 저짐들은 모두 d 의 범위 안에 들어가는 값들임
         *              -> 이때, queue 에서 Min Heap 외의 값들은 모두 선문이 시작하는 시점이 Min Heap 보다 늦은 지점에서 시작
         *                  -> 그러므로, Min Heap 의 값만 확인하면됨
         * -> 이때, 어차피 선분이 끝나는 지점으로 정렬을 했기 때문에, i번째 이후로 오는 강의들은 모두 선분이 끝나는 지점이 i보다 늦게 끝남
         *  -> 그러므로, i번째 선분이 시작하는 지점만 있어도 i+1번째의 선분이 끝나는 지점 - d ~ i+1번째의 선분이 끝나는 지점을 잇는 선분안에 있는지 확인 가능
         *      -> Because, 끝나는 지점은 무조건 i+1이 i보다 같거나 같음
         *          -> 그러므로, i 의 시작하는 지점이 i+1번째의 선분이 끝나는 지점 - d ~ i+1번째의 선분이 끝나는 지점을 잇는 선분안에 있으면 i의 끝나는 지점도 해당 선분안에 무조건 들어가 있음
         */
        PriorityQueue<Long> queue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            Edge curEdge = edges.get(i);
            /**
             * 현재 edge 의 길이가 d보다 크면 queue 에 추가할 필요가 없음
             *  -> 그러므로, d보다 작거나 같을때만 queue 에 추가
             */
            if (curEdge.o - curEdge.h <= d) {
                queue.add(curEdge.h);
            }

            while (!queue.isEmpty()) {
                Long minLeft = queue.peek();
                if (minLeft >= curEdge.o - d) {
                    break;
                }
                /**
                 * minLeft < curEdge.o - d 이면:
                 * 더 이상 curEdge.o - d ~ d 까지 잇는 선분에 존재하지 않음
                 * queue 에서 삭제
                 */
                queue.poll();
            }

            /**
             * 위의 과정을 거치고 queue 에 남은 값들은 모두 curEdge.o - d ~ d 까지 잇는 선분에 존재
             *  -> queue 의 크기 == 해당 선분에 포함되는 강의 수 (사람의 수)
             *      -> 해당 값의 최댓값이 최종 정답
             */
            ans = Math.max(ans, queue.size());
        }

        System.out.println(ans);
    }

    static class Edge{
        long h;
        long o;

        public Edge(long h, long o) {
            this.h = h;
            this.o = o;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "h=" + h +
                    ", o=" + o +
                    '}';
        }
    }
}

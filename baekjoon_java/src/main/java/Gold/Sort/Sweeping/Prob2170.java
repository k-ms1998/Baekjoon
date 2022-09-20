package Gold.Sort.Sweeping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5(선 긋기)
 *
 * https://www.acmicpc.net/problem/2170
 *
 * Solution: Sort + Sweeping
 */
public class Prob2170 {

    static int n;
    static List<Line> lines = new ArrayList<>();

    static long ans = 0L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            lines.add(new Line(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())));
        }

        /**
         * Sweeping 을 하기 위해서 정렬
         * 선의 시작 지점이 빠른 순으로 정렬
         * Because, 현재 선이 앞서 탐색한 선들과 겹치는지 확인 하기 위해서는 현재 선의 시작 지점이 앞서 나온 선들 중에서 가장 늦게 끝나는 지점보다 같거나 작으면 됨
         *  -> And, 시작 지점으로 정렬하면, 현재 선의 시작 지점은 무조건 앞서 나온 선들의 시작 지점보다 나중에 시작함으로, 앞서 나온 선들의 시작 지점을 고려할 필요 X
         *      -> 1 ~ 3, 4 ~ 5, 1 ~ 6 선분이 있을때 정렬 후: 1 ~ 3, 1 ~ 6, 4 ~ 5
         * -> If, 선의 끝 지점이 빠른 순으로 정렬하면:
         *      -> 1 ~ 3, 4 ~ 5, 1 ~ 6 선분이 있을때, 1 ~ 3 과 4 ~ 5 는 겹치지 않지만, 1 ~ 6 은 1 ~ 3 과 4 ~ 5와 모두 겹치기 때문에 1 ~ 3 와 4 ~ 5 선들을 다시 한번 탐색해서 1 ~ 6과 겹치는지 확인해야함
         *          -> 시간 초과 발생 (O(n^2))
         */
        Collections.sort(lines, new Comparator<Line>() {
            @Override
            public int compare(Line l1, Line l2) {
                if (l1.s == l2.s) {
                    return (int) (l1.d - l2.d);
                }

                return (int) (l1.s - l2.s);
            }
        });

        Line firstLine = lines.get(0);
        long s = firstLine.s;
        long d = firstLine.d;
        for (int i = 1; i < n; i++) {
            /**
             * d : 현재까지 나온 선들 중에서 끝나는 시점이 가장 나중에인 지점 저장
             */
            Line curLine = lines.get(i);
            if (curLine.s <= d) {
                d = Math.max(curLine.d, d);
            } else {
                ans += (d - s);
                s = curLine.s;
                d = curLine.d;
            }
        }
        ans += (d - s);

        System.out.println(ans);
    }

    static class Line{
        long s;
        long d;

        public Line(long s, long d) {
            this.s = s;
            this.d = d;
        }

        @Override
        public String toString() {
            return "{ s = " + s + ": d = " + d + " }";
        }
    }
}

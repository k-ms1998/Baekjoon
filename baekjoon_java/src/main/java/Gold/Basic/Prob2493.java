package Gold.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5
 *
 * Solution : Stack
 */
public class Prob2493 {

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        Stack<Tower> beam = new Stack<>();
        st = new StringTokenizer(br.readLine());
        /**
         * ex: 6 9 5 7 4
         * next = 6, beam = empty -> "0", beam.append(6)
         * next = 9, beam = 6 -> beam.peek().h (== 6) < next(== 9) -> beam.pop() -> beam = empty -> "0", beam.append(9)
         * next = 5, beam = 9 -> beam.peek().h (== 9) > next(== 5) -> "2" (== beam.peek().idx), beam.append(5), break
         * next = 7, beam = 9, 5
         *  -> beam.peek().h (== 5) < next (== 7) -> beam.pop() -> beam = 9 -> beam.peek().h (== 9) > next(== 7) -> "2" (== beam.peek().idx), beam.append(7), break
         * next = 4, beam = 9, 7 -> beam.peek().h (== 7) > next(== 4) -> "4" (== beam.peek().idx), beam.append(4), break
         *
         * end
         *
         * ans = 0 0 2 2 4
         */
        for (int i = 0; i < n; i++) {
            int next = Integer.parseInt(st.nextToken());

            if (beam.isEmpty()) {
                ans.append("0 ");
            } else {
                while (!beam.isEmpty()) {
                    Tower prevTower = beam.peek();
                    int prevIdx = prevTower.idx;
                    int prevH = prevTower.h;
                    if (prevH < next) {
                        beam.pop();
                    } else {
                        ans.append(prevIdx + " ");
                        break;
                    }
                }

                if (beam.isEmpty()) {
                    ans.append("0 ");
                }
            }

            beam.push(new Tower(i+1, next));
        }
        System.out.println(ans);
    }

    static class Tower {
        int idx;
        int h;

        public Tower(int idx, int h) {
            this.idx = idx;
            this.h = h;
        }

        @Override
        public String toString() {
            return "Tower{" +
                    "idx=" + idx +
                    ", h=" + h +
                    '}';
        }
    }
}
//5
//6 2 3 4 5

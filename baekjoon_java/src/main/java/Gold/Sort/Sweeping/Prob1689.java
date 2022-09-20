package Gold.Sort.Sweeping;

import java.io.*;
import java.util.*;

/**
 * Gold 4(겹치는 선분)
 *
 * https://www.acmicpc.net/problem/1689
 *
 * Solution: Sort + Sweeping
 */
public class Prob1689 {

    static int n;
    static List<Line> lines = new ArrayList<>();

    static int ans = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            lines.add(new Line(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())));
        }
        /**
         * 선분의 시작 지점을 오름차순으로 정렬
         */
        Collections.sort(lines, new Comparator<Line>(){
            @Override
            public int compare(Line l1, Line l2) {
                if (l1.s == l2.s) {
                    return (int) (l1.e - l2.e);
                }

                return (int) (l1.s - l2.s);
            }
        });

        /**
         * queue 에는 현재까지 나온 선분들의 종료 시점을 Min Heap 으로 저장
         * 만약, 현재 선분의 시작 지점이 앞서 나온 선분들의 종료 지점과 작아야 겹치게 됨
         * 현재 선분의 시작 시점이 앞서 나온 선분둘의 종료 시점보다 크거나 같으면 겹치지 않음
         *  -> 그러면 앞으로 나올 선분들도 해당 종료 시점보다 같거나 나중에 시작함
         *      -> 해당 종료 시점을 queue 에서 pop
         */
        PriorityQueue<Long> queue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            Line curLine = lines.get(i);
            long curS = curLine.s;
            long curE = curLine.e;
            queue.offer(curE);
            while (!queue.isEmpty()) {
                long minE = queue.peek();
                if (minE > curS) {
                    break;
                }
                queue.poll();
            }
//            System.out.println("queue = " + queue);
            ans = Math.max(ans, queue.size());
        }

        System.out.println(ans);
    }

    static class Line{
        long s;
        long e;

        public Line(long s, long e) {
            this.s = s;
            this.e = e;
        }
    }
}

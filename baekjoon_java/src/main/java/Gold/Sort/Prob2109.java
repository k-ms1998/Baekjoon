package Gold.Sort;

import java.io.*;
import java.util.*;

/**
 * Gold 3(순회 강연)
 *
 * https://www.acmicpc.net/problem/2109
 *
 * Solution: PriorityQueue
 */
public class Prob2109 {

    static int n;
    static PriorityQueue<Lecture> lectures;
    static int lastDay = 0;
    static boolean[] paid;


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        lectures = new PriorityQueue<>(new Comparator<Lecture>() {
            @Override
            public int compare(Lecture o1, Lecture o2) {
                return o2.p - o1.p;
            }
        });
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            lectures.offer(new Lecture(p, d));
            lastDay = Math.max(lastDay, d);
        }

        long ans = 0;
        paid = new boolean[lastDay + 1];
        while (!lectures.isEmpty()) {
            Lecture lecture = lectures.poll();
            int p = lecture.p;
            int d = lecture.d;
            /**
             * 현재 강의는 d 번째 날 이전에 강의를 하면 됨
             * 즉, 첫번째 날 ~ d 번째 날 아무때나 강의를 하면 됨
             * 이때, d에서 시작해서 1까지 역순으로 탐색하는 것이 중요
             */
            for (int day = d; day >= 1; day--) {
                if(paid[day]){
                    continue;
                }

                ans += p;
                paid[day] = true;
                break;
            }
        }

        System.out.println(ans);
    }

    public static class Lecture{
        int p;
        int d;

        public Lecture(int p, int d) {
            this.p = p;
            this.d = d;
        }

        @Override
        public String toString() {
            return "Lecture:[p=" + p + ", d=" + d + "]";
        }
    }
}

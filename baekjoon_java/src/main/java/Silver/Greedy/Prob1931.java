package Silver.Greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Silver 1
 *
 * 문제
 * 한 개의 회의실이 있는데 이를 사용하고자 하는 N개의 회의에 대하여 회의실 사용표를 만들려고 한다.
 * 각 회의 I에 대해 시작시간과 끝나는 시간이 주어져 있고, 각 회의가 겹치지 않게 하면서 회의실을 사용할 수 있는 회의의 최대 개수를 찾아보자.
 * 단, 회의는 한번 시작하면 중간에 중단될 수 없으며 한 회의가 끝나는 것과 동시에 다음 회의가 시작될 수 있다. 회의의 시작시간과 끝나는 시간이 같을 수도 있다.
 * 이 경우에는 시작하자마자 끝나는 것으로 생각하면 된다.
 *
 * 입력
 * 첫째 줄에 회의의 수 N(1 ≤ N ≤ 100,000)이 주어진다.
 * 둘째 줄부터 N+1 줄까지 각 회의의 정보가 주어지는데 이것은 공백을 사이에 두고 회의의 시작시간과 끝나는 시간이 주어진다.
 * 시작 시간과 끝나는 시간은 231-1보다 작거나 같은 자연수 또는 0이다.
 *
 * 출력
 * 첫째 줄에 최대 사용할 수 있는 회의의 최대 개수를 출력한다.
 *
 * Solution: Greedy
 * => 회의 종료 시간이 가장 빠른 순서대로 회의실 배정해주기
 */
public class Prob1931 {

    static List<Time> times = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            times.add(new Time(s, d));
        }

        /**
         * 종료 시간이 빠르 순서대로 정렬
         * 종료 시간이 같으면 시작 시간이 빠른 순서대로 정렬
         */
        Collections.sort(times, new Comparator<Time>() {
            @Override
            public int compare(Time t1, Time t2) {
                if (t1.d > t2.d) {
                    return 1;
                } else if (t1.d < t2.d) {
                    return -1;
                } else {
                    if (t1.s > t2.s) {
                        return 1;
                    } else if (t1.s < t2.s) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });

        int cnt = 0;
        int prevEnd = 0;
        for (int i = 0; i < n; i++) {
            /**
             * 직전 회의 종료 시간보다 늦게 시간하는 회의 중에서 가장 빨리 시작하는 회의 찾기 => 겹치지 않음
             * -> 이때, 정렬을 회의 시간이 가장 빨리 끝나는 순으로 정렬 했기 때문에 겹치지 않은 것만 확인하면 됨
             */
            Time curTime = times.get(i);
            int curS = curTime.s;
            int curD = curTime.d;
            if (curS >= prevEnd) {
                prevEnd = curD;
                cnt++;
            }
        }

        System.out.println(cnt);
    }

    static class Time{
        int s;
        int d;

        public Time(int s, int d) {
            this.s = s;
            this.d = d;
        }

        @Override
        public String toString() {
            return "{ " + this.s + " : " + this.d + " }";
        }
    }
}

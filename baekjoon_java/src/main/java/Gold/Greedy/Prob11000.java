package Gold.Greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5
 *
 * 문제
 * 수강신청의 마스터 김종혜 선생님에게 새로운 과제가 주어졌다.
 * 김종혜 선생님한테는 Si에 시작해서 Ti에 끝나는 N개의 수업이 주어지는데, 최소의 강의실을 사용해서 모든 수업을 가능하게 해야 한다.
 * 참고로, 수업이 끝난 직후에 다음 수업을 시작할 수 있다. (즉, Ti ≤ Sj 일 경우 i 수업과 j 수업은 같이 들을 수 있다.)
 * 수강신청 대충한 게 찔리면, 선생님을 도와드리자!
 *
 * 입력
 * 첫 번째 줄에 N이 주어진다. (1 ≤ N ≤ 200,000)
 * 이후 N개의 줄에 Si, Ti가 주어진다. (0 ≤ Si < Ti ≤ 109)
 *
 * 출력
 * 강의실의 개수를 출력하라.
 *
 * Solution: Sort + Greedy + PriorityQueue
 *
10
1 3
2 4
2 5
5 6
6 7
7 11
8 11
9 11
10 11
11 15
=> answer = 4
 */
public class Prob11000 {

    static int n;
    static int ans = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        List<Time> times = new ArrayList<>();
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            times.add(new Time(s, d));
        }

        /**
         * 강의 시작 시간이 빠른 순으로 정렬
         */
        Collections.sort(times, new Comparator<Time>(){
            @Override
            public int compare(Time t1, Time t2) {
                if (t1.s == t2.s) {
                    if (t1.d > t2.d) {
                        return 1;
                    } else if (t1.d < t2.d) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else if (t1.s > t2.s) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        /**
         * queue 에는 Min Heap 으로 각 강의의 종료 시간을 저장 -> 종료 시간이 가장 빠른 강의가 Min Heap 으로 유지
         * !! queue 애는 현재 겹치는 강의들의 종료 시간들을 Min Heap 으로 저장 중 !!
         */
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Time time : times) {
            /**
             * 시작 시간이 작은 순서대로 Queue에 넣기 시작 -> Greedy
             */
            int curS = time.s;
            int curD = time.d;

            /**
             * Queue 에 값들이 저장되어 있을때:
             * 현재 강의가 queue 에 있는 강의들과 시간이 겹치는 경우 break
             * 겹치지 않는 경우 queue 에서 poll
             *
             * 이전 강의의 종료 시간이, 현재 강의의 시간 시간보다 늦으면 겹치게 됨
             */
            while (!queue.isEmpty()) {
                if (queue.peek() > curS) {
                    /**
                     * 현재 강의의 시작 시간이 현재 진행 중인 강의들 중에서 종료 시간이 가장 빠른 시간보다 먼저 시작함
                     * -> 현재 강의가 앞선 강의들 보다 먼저 시작함
                     *  -> 그러므로, 앞선 강의들과 시간이 겹침
                     *      -> break
                     */
                    break;
                } else {
                    /**
                     * 현재 강의의 시작 시간이 현재 진행 중인 강의들 중에서 종료 시간이 가장 빠른 시간보다 늦게 시작함
                     * -> 현재 강의 이후로 나오는 강의들은 모두 현재 강의 보다 시작시간이 늦음
                     *  -> 그러므로, queue 에 있는 강의들 중에서 종료 시간이 가장 빠른 시간과 겹치는 경우 발생 X
                     *          => 즉, 현재 강의 포함 앞으로 검사할 강의들과 겹치지는 경우가 발생하지 않기 때문에 remove 해줌
                     */
                    queue.poll();

                }
            }
            /**
             * 현재 강의 넣어주기
             */
            queue.offer(curD);
//            System.out.println("queue = " + queue.size());
            /**
             * queue 의 크기 == 현재 겹치는 강의 수 == 필요한 강의실의 수
             */
            ans = Math.max(ans, queue.size());
        }

        System.out.println(ans);
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
            return "Time{" +
                    "s=" + s +
                    ", d=" + d +
                    '}';
        }
    }
}
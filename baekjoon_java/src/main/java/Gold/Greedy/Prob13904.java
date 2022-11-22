package Gold.Greedy;

import java.io.*;
import java.util.*;

/**
 * Gold 3(과제)
 *
 * https://www.acmicpc.net/problem/13904
 *
 * Solution: Greedy + PriorityQueue
 * 풀이 방식은 2109(순회 강연) 문제와 동일
 */
public class Prob13904 {

    static int n;
    static PriorityQueue<Info> assignments;

    static int lastDay = 0;
    static int[] scores;
    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        /**
         * 과제들을 점수가 높은 순으로 정렬/높은 순으로 뽑을수 있도록 Max Heap 으로 유지
         */
        assignments = new PriorityQueue<>(new Comparator<Info>(){
            @Override
            public int compare(Info i1, Info i2) {
                return i2.score - i1.score;
            }
        });
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int day = Integer.parseInt(st.nextToken());
            int score = Integer.parseInt(st.nextToken());
            /*
            마지막 날짜가 언제인지 모르기 때문에 입력마다 확인 후 업데이트
             */
            lastDay = Math.max(lastDay, day);

            assignments.offer(new Info(day, score));
        }
        scores = new int[lastDay + 1];

        /**
         * 점수가 큰 순서대로 과제 뽑아오기
         */
        while (!assignments.isEmpty()) {
            Info cur = assignments.poll();
            int day = cur.day;
            int score = cur.score;

            /*
            현재 과제는 day 이전에 완성하면 됨
            그렇기 때문에, day 부터 시작해서 첫번째날 까지 탐색하면서 해당 과제를 완료시킬 수 있는 가장 좋은 날짜 찾기
             */
            for (int i = day; i >= 1; i--) {
                if (scores[i] < score) {
                    scores[i] = score;
                    ans += score;
                    break;
                }
            }
        }

        System.out.println(ans);
    }

    static public class Info{
        int day;
        int score;

        public Info(int day, int score) {
            this.day = day;
            this.score = score;
        }
    }
}

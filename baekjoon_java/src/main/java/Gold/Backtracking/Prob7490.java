package Gold.Backtracking;

import java.io.*;
import java.util.*;

/**
 * Gold 5
 *
 * 문제
 * 1부터 N까지의 수를 오름차순으로 쓴 수열 1 2 3 ... N을 생각하자.
 *
 * 그리고 '+'나 '-', 또는 ' '(공백)을 숫자 사이에 삽입하자(+는 더하기, -는 빼기, 공백은 숫자를 이어 붙이는 것을 뜻한다).
 * 이렇게 만든 수식의 값을 계산하고 그 결과가 0이 될 수 있는지를 살피자.
 * N이 주어졌을 때 수식의 결과가 0이 되는 모든 수식을 찾는 프로그램을 작성하라.
 *
 * 입력
 * 첫 번째 줄에 테스트 케이스의 개수가 주어진다(<10).
 * 각 테스트 케이스엔 자연수 N이 주어진다(3 <= N <= 9).
 *
 * 출력
 * 각 테스트 케이스에 대해 ASCII 순서에 따라 결과가 0이 되는 모든 수식을 출력한다.
 * 각 테스트 케이스의 결과는 한 줄을 띄워 구분한다.
 *
 *
 */
public class Prob7490 {

    public static List<StringBuilder> answer = new ArrayList<>();

    public void solve() throws IOException {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();

            answer = new ArrayList<>();
            Deque<String> queue = new ArrayDeque<>();
//            createZero(n, 1, queue);
            createZero2(n, 1, queue); // Improved Logic
            Collections.sort(answer); // ASCII 순서대로 정렬
            for (StringBuilder sb : answer) {
                System.out.println(sb);
            }
            System.out.println();
        }
    }


    private void createZero2(int n, int m, Deque<String> queue) {
        /**
         * 숫자는 1부터 N까지 차례대로 주어지기 때문에 연산자들('+', '-', ' ')민 큐(Queue)에 담으면 됨
         * 큐에 연산자들을 담은 후, 하나씩 추출해서 계산 시작
         * 계산 결과가 0이면 출력
         */
        if (n == m) {
            /**
             * 1부터 시작
             * res는 최종 연산된 값 저장
             * cur에는 현재 더하거나 뺄 값 저장
             * last는 직전 연산자가 더하기였는지 뺼셈 이였는지 저장
             *
             * ex: 1+2 3-4 -> 1+23-4
             * -> 1 => res = 0, cur = 1, last = 1
             * -> + 2 => res = 1, cur = 2, last = 1
             * -> ' ' 3 => res = 1, cur = 23, last = 1
             * -> - 4 => res = 24, cur = 4, last = -1
             * -> end: => res = 20
             */
            int res = 0;
            int cur = 1;
            int last = 1;
            StringBuilder ans = new StringBuilder();
            ans.append(1);
            for (int i = 2; i <= n; i++) {
                if (queue.isEmpty()) {
                    break;
                }

                /**
                 * Operator와 직후의 숫자를 같이 추출
                 */
                String operator = queue.pollFirst();
                ans.append(operator);
                ans.append(i);

                if (operator.equals(" ")) {
                    cur = 10 * cur + i;
                    continue;
                }
                res += (last * cur);
                cur = i;
                last = operator.equals("+") ? 1 : -1;
            }
            res += (cur * last);

            if (res == 0) {
                answer.add(ans);
            }
            return;
        }

        /**
         * Shallow Copy로 queue로 copy해야 계산할때 오류 발생 X
         */
        Deque<String> tmpQueue = new ArrayDeque<>();
        for (String s : queue) {
            tmpQueue.add(s);
        }
        tmpQueue.add("+");
        createZero2(n, m + 1, tmpQueue);

        /**
         * 매번 Shallow Copy로 queue로 copy해야 계산할때 오류 발생 X
         */
        tmpQueue = new ArrayDeque<>();
        for (String s : queue) {
            tmpQueue.add(s);
        }
        tmpQueue.add("-");
        createZero2(n, m + 1, tmpQueue);

        /**
         * 매번 Shallow Copy로 queue로 copy해야 계산할때 오류 발생 X
         */
        tmpQueue = new ArrayDeque<>();
        for (String s : queue) {
            tmpQueue.add(s);
        }
        tmpQueue.add(" ");
        createZero2(n, m + 1, tmpQueue);
    }

    private void createZero(int n, int m,Deque<String> queue) {
        queue.add(String.valueOf(m));

        /**
         * 마지막 숫자까지 도달했을때
         */
        if (n == m) {
            StringBuilder ans = new StringBuilder();
            Deque<String> tmpQueue = new ArrayDeque<>();
            for (String s : queue) {
                tmpQueue.add(s);
            }
            int res = 0;
            int tmp = 0;
            int power = 0;
            while (!tmpQueue.isEmpty()) {
                /**
                 * Queue의 마지막 원소부터 pop하면서 계산
                 * ex: 1 + 2 + 3
                 * => 3, +, 2, +, 1 순서로 pop 됨
                 */
                String s = tmpQueue.pollLast();
                ans.insert(0, s);
                if (s.equals("+") || s.equals("-") || s.equals(" ")) {
                    if (s.equals(" ")) {
                        /**
                         * 1 2 3 -> 1*100 + 2*10 + 3*1
                         */
                        power += 1;
                        continue;
                    } else if (s.equals("+")) {
                        res += tmp;
                    } else {
                        res -= tmp;
                    }
                    tmp = 0;
                    power = 0;
                } else {
                    tmp += (Math.pow(10, power) * Integer.valueOf(s));
                }
            }
            res += tmp;
            if (res == 0) {
                answer.add(ans);
            }
            return;
        }


        /**
         * Shallow Copy로 queue로 copy해야 계산할때 오류 발생 X
         */
        Deque<String> tmpQueue = new ArrayDeque<>();
        for (String s : queue) {
            tmpQueue.add(s);
        }
        tmpQueue.add("+");
        createZero(n, m+1, tmpQueue);

        /**
         * 매번 Shallow Copy로 queue로 copy해야 계산할때 오류 발생 X
         */
        tmpQueue = new ArrayDeque<>();
        for (String s : queue) {
            tmpQueue.add(s);
        }
        tmpQueue.add("-");
        createZero(n, m+1, tmpQueue);

        /**
         * 매번 Shallow Copy로 queue로 copy해야 계산할때 오류 발생 X
         */
        tmpQueue = new ArrayDeque<>();
        for (String s : queue) {
            tmpQueue.add(s);
        }
        tmpQueue.add(" ");
        createZero(n, m+1, tmpQueue);
    }
}

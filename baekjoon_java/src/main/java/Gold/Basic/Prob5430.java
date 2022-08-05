package Gold.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5
 *
 * 문제
 * 선영이는 주말에 할 일이 없어서 새로운 언어 AC를 만들었다. AC는 정수 배열에 연산을 하기 위해 만든 언어이다. 이 언어에는 두 가지 함수 R(뒤집기)과 D(버리기)가 있다.
 * 함수 R은 배열에 있는 수의 순서를 뒤집는 함수이고, D는 첫 번째 수를 버리는 함수이다. 배열이 비어있는데 D를 사용한 경우에는 에러가 발생한다.
 * 함수는 조합해서 한 번에 사용할 수 있다. 예를 들어, "AB"는 A를 수행한 다음에 바로 이어서 B를 수행하는 함수이다. 예를 들어, "RDD"는 배열을 뒤집은 다음 처음 두 수를 버리는 함수이다.
 * 배열의 초기값과 수행할 함수가 주어졌을 때, 최종 결과를 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 테스트 케이스의 개수 T가 주어진다. T는 최대 100이다.
 * 각 테스트 케이스의 첫째 줄에는 수행할 함수 p가 주어진다. p의 길이는 1보다 크거나 같고, 100,000보다 작거나 같다.
 * 다음 줄에는 배열에 들어있는 수의 개수 n이 주어진다. (0 ≤ n ≤ 100,000)
 * 다음 줄에는 [x1,...,xn]과 같은 형태로 배열에 들어있는 정수가 주어진다. (1 ≤ xi ≤ 100)
 * 전체 테스트 케이스에 주어지는 p의 길이의 합과 n의 합은 70만을 넘지 않는다.
 *
 * 출력
 * 각 테스트 케이스에 대해서, 입력으로 주어진 정수 배열에 함수를 수행한 결과를 출력한다. 만약, 에러가 발생한 경우에는 error를 출력한다.
 *
 */
public class Prob5430 {

    static String ERROR = "error";
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.valueOf(br.readLine());
        for (int i = 0; i < t; i++) {
            String func = br.readLine();

            int n = Integer.valueOf(br.readLine());
            Deque<Integer> num = new ArrayDeque<>();

            /**
             * 입력
             */
            String arrStr = br.readLine();
            int curNum = 0;
            for (int j = 0; j < arrStr.length(); j++) {
                if (arrStr.charAt(j) == '[' || arrStr.charAt(j) == ']' || arrStr.charAt(j) == ',') {
                    if (curNum != 0) {
                        num.offer(curNum);
                    }
                    curNum = 0;
                    continue;
                }
                curNum = 10 * curNum + (arrStr.charAt(j) - '0');
            }

            boolean isError = false;
            StringBuilder tmpAns = new StringBuilder();
            boolean reverse = false;
            for (int j = 0; j < func.length(); j++) {
                char cmd = func.charAt(j);
                if (cmd == 'R') {
                    /**
                     * 'R'이 나올때마다 num 을 반전 시켜주면 시간초과
                     * => reverse 라는 변수를 둬서 현재 뒤집은 상태인지 판별
                     */
                    reverse = !reverse;
                }
                if (cmd == 'D') {
                    if (num.isEmpty()) {
                        tmpAns = new StringBuilder();
                        tmpAns.append(ERROR);
                        isError = true;
                        break;
                    }

                    /**
                     * reverse == true => num 이 반전된 상태 => 그러므로 num 을 반전시키기보다는 num 의 마지막 원소 pop() => 수행 시간 감소
                     */
                    if (reverse) {
                        num.pollLast();
                    } else {
                        num.poll();
                    }
                }
            }
            if(!isError){
                tmpAns.append("[");
                /**
                 * reverse == true 이면 num 이 반전된 상태이므로 마지막 원소부터 정답에 추가
                 */
                if (reverse) {
                    while (!num.isEmpty()) {
                        tmpAns.append(num.pollLast());
                        if (!num.isEmpty()) {
                            tmpAns.append(",");
                        }
                    }
                }else{
                    while (!num.isEmpty()) {
                        tmpAns.append(num.poll());
                        if (!num.isEmpty()) {
                            tmpAns.append(",");
                        }
                    }
                }
                tmpAns.append("]");
            }
            ans.append(tmpAns + "\n");
        }
        System.out.println(ans);
    }
}

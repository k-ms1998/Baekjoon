package Gold.Basic;

import java.util.*;
import java.io.*;

/**
 * Gold 4
 *
 * 문제
 * 크기가 N인 수열 A = A1, A2, ..., AN이 있다. 수열의 각 원소 Ai에 대해서 오큰수 NGE(i)를 구하려고 한다.
 * Ai의 오큰수는 오른쪽에 있으면서 Ai보다 큰 수 중에서 가장 왼쪽에 있는 수를 의미한다. 그러한 수가 없는 경우에 오큰수는 -1이다.
 * 예를 들어, A = [3, 5, 2, 7]인 경우 NGE(1) = 5, NGE(2) = 7, NGE(3) = 7, NGE(4) = -1이다.
 * A = [9, 5, 4, 8]인 경우에는 NGE(1) = -1, NGE(2) = 8, NGE(3) = 8, NGE(4) = -1이다.
 *
 * 입력
 * 첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000,000)이 주어진다. 둘째 줄에 수열 A의 원소 A1, A2, ..., AN (1 ≤ Ai ≤ 1,000,000)이 주어진다.
 *
 * 출력
 * 총 N개의 수 NGE(1), NGE(2), ..., NGE(N)을 공백으로 구분해 출력한다.
 *
 * Solution:
 * 1. data의 값을 앞에서 부터 순회
 * 2. 현재의 data값과 stack의 마지막 값을 비교
 * 3. data의 값이 더 크면, stack의 값을 pop && 오큰수를 data값으로 업테이트
 * 4. stack의 마지막 값이 data 값보다 클때까지 stack을 pop && 오큰수 업데이트
 * 5. stack의 마지막 값이 data 값보다 더 커지면, data 값을 stack에 push하고, 해당 data값부터 다시 data 순회
 * 6. 2~5번 반복
 *
 * Test Data:
11
3 2 1 10 9 5 4 8 15 11 13
 */
public class Prob17298 {

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder ans = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        String dataLine = br.readLine();
        String[] dataString = dataLine.split(" ");
        Integer[] data = new Integer[n];
        Integer[] rightBig = new Integer[n];
        for (int i = 0; i < n; i++) {
            data[i] = Integer.valueOf(dataString[i]);
            rightBig[i] = -1;
        }

        Stack<Integer> stack = new Stack<>();
        stack.add(0);
        for (int i = 1; i < n; i++) {
            while (true) {
                if (stack.empty()) {
                    break;
                }

                int topIdx = stack.peek();
                if (data[topIdx] >= data[i]) {
                    break;
                }
                stack.pop();
                rightBig[topIdx] = data[i];
            }

            stack.add(i);
        }

        for (Integer i : rightBig) {
            ans.append(i + " ");
        }
        System.out.println(ans);
    }

    public void solve_deprecated() throws IOException { // 시간 초과 -> O(n^2)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder ans = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        String dataLine = br.readLine();
        String[] dataString = dataLine.split(" ");
        Integer[] data = new Integer[n];
        for (int i = 0; i < n; i++) {
            data[i] = Integer.valueOf(dataString[i]);
        }

        for (int i = 0; i < n; i++) {
            boolean found = false;
            int curData = data[i];
            for (int j = i + 1; j < n; j++) {
                if (curData < data[j]) {
                    found = true;
                    ans.append(data[j]+" ");
                    break;
                }
            }
            if (!found) {
                ans.append("-1 ");
            }
        }

        System.out.println(ans);
    }
}

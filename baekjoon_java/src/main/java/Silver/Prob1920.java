package Silver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Silver 4
 *
 * 문제
 * N개의 정수 A[1], A[2], …, A[N]이 주어져 있을 때, 이 안에 X라는 정수가 존재하는지 알아내는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 자연수 N(1 ≤ N ≤ 100,000)이 주어진다. 다음 줄에는 N개의 정수 A[1], A[2], …, A[N]이 주어진다. 다음 줄에는 M(1 ≤ M ≤ 100,000)이 주어진다.
 * 다음 줄에는 M개의 수들이 주어지는데, 이 수들이 A안에 존재하는지 알아내면 된다. 모든 정수의 범위는 -231 보다 크거나 같고 231보다 작다.
 *
 * 출력
 * M개의 줄에 답을 출력한다. 존재하면 1을, 존재하지 않으면 0을 출력한다.
 */
public class Prob1920 {

    public void solve() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            data.add(sc.nextInt());

        }

        int m = sc.nextInt();

        List<Integer> find = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            find.add(sc.nextInt());
        }

        // 이분 탐색을 하기 위해 data를 정렬
        data.sort((e1, e2) -> {
            if (e1 > e2) {
                return 1;
            }
            return -1;
        });
        
        // 이분 탐색
        for (Integer e : find) {
            int left = 0;
            int right = data.size() - 1;
            int found = 0;
            while (true) {
                if (right-left <= 1) {
                    if (data.get(right).equals(e) || data.get(left).equals(e)) { // equals()로 값 비교하기
                        found = 1;
                    }
                    break;
                }

                int pivot = (left + right) / 2;
                int pivotData = data.get(pivot);
                if (pivotData == e) {
                    found = 1;
                    break;
                } else if (pivotData < e) {
                    left = pivot + 1;
                } else {
                    right = pivot - 1;
                }
            }
            System.out.println(found);
        }
    }
}
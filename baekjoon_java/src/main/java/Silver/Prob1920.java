package Silver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 5
 *
 * 문제
 * 2차원 평면 위의 점 N개가 주어진다. 좌표를 x좌표가 증가하는 순으로, x좌표가 같으면 y좌표가 증가하는 순서로 정렬한 다음 출력하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 점의 개수 N (1 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N개의 줄에는 i번점의 위치 xi와 yi가 주어진다. (-100,000 ≤ xi, yi ≤ 100,000) 좌표는 항상 정수이고, 위치가 같은 두 점은 없다.
 *
 * 출력
 * 첫째 줄부터 N개의 줄에 점을 정렬한 결과를 출력한다.
 */
public class Prob11650 {

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<Map.Entry<Integer, Integer>> data = new ArrayList<>();
        int n = Integer.valueOf(br.readLine());
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            String[] lineSplit = line.split(" ");
            int k = Integer.valueOf(lineSplit[0]);
            int v = Integer.valueOf(lineSplit[1]);
            data.add(Map.entry(k, v));
        }
        Collections.sort(data, (o1, o2) -> {
            if (o1.getKey() > o2.getKey()) {
                return 1;
            } else if (o1.getKey() < o2.getKey()) {
                return -1;
            } else {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        data.forEach(d -> {
            System.out.println(d.getKey() + " " + d.getValue());
        });
    }
}

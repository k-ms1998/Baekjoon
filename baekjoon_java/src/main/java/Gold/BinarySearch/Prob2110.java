package Gold.BinarySearch;

import java.io.*;
import java.util.*;

/**
 * Gold 5
 *
 * 문제
 * 도현이의 집 N개가 수직선 위에 있다. 각각의 집의 좌표는 x1, ..., xN이고, 집 여러개가 같은 좌표를 가지는 일은 없다.
 * 도현이는 언제 어디서나 와이파이를 즐기기 위해서 집에 공유기 C개를 설치하려고 한다.
 * 최대한 많은 곳에서 와이파이를 사용하려고 하기 때문에, 한 집에는 공유기를 하나만 설치할 수 있고, 가장 인접한 두 공유기 사이의 거리를 가능한 크게 하여 설치하려고 한다.
 * C개의 공유기를 N개의 집에 적당히 설치해서, 가장 인접한 두 공유기 사이의 거리를 최대로 하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 집의 개수 N (2 ≤ N ≤ 200,000)과 공유기의 개수 C (2 ≤ C ≤ N)이 하나 이상의 빈 칸을 사이에 두고 주어진다.
 * 둘째 줄부터 N개의 줄에는 집의 좌표를 나타내는 xi (0 ≤ xi ≤ 1,000,000,000)가 한 줄에 하나씩 주어진다.
 *
 * 출력
 * 첫째 줄에 가장 인접한 두 공유기 사이의 최대 거리를 출력한다.
 */
public class Prob2110 {

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String conditions = br.readLine();
        String[] conditionsSplit = conditions.split(" ");
        int n = Integer.valueOf(conditionsSplit[0]);
        int c = Integer.valueOf(conditionsSplit[1]);

        List<Integer> houses = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            houses.add(Integer.valueOf(br.readLine()));
        }
        /**
         * 1. 입력 받은 집의 위치들을 오름차순으로 정렬
         */
        Collections.sort(houses);

        /**
         * 2. 설치할 수 있는 간격의 최소값 & 최대값 구하기 -> min == 1 && max == (마지막 집의 위치 - 첫번쨰 집의 위치)
         */
        int maxGap = houses.get(n - 1) - houses.get(0); // == end
        int minGap = 1;     // == start
        int ansGap = 0;
        while (minGap <= maxGap) {
            /**
             * 3. mid로 max와 min의 중간 값을 설치 가능한 간격으로 두고, 총 C개의 공유기가 설치 가능한지 확인
             */
            int midGap = (maxGap + minGap) / 2;
            int lastInstalled = houses.get(0);
            int curC = 1;
            for (int i = 1; i < n; i++) {
                if (houses.get(i) >= lastInstalled + midGap) {
                    lastInstalled = houses.get(i);
                    curC++;
                }
            }

            /**
             * 4. C개의 공유가 설치 가능하면 minGap의 값을 업데이트; C개의 공유기가 설치가 안되면 maxGap 업데이트 => 이분 탐색
             */
            if (curC >= c) {
                ansGap = midGap;
                minGap = midGap + 1;
            } else {
                maxGap = midGap - 1;
            }
        }

        System.out.println(ansGap);
    }
}
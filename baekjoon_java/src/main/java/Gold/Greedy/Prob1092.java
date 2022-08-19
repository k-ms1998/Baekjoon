package Gold.Greedy;

import javax.script.ScriptContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5: Greedy
 *
 * 문제:
 * 지민이는 항구에서 일한다. 그리고 화물을 배에 실어야 한다.
 * 모든 화물은 박스에 안에 넣어져 있다. 항구에는 크레인이 N대 있고, 1분에 박스를 하나씩 배에 실을 수 있다. 모든 크레인은 동시에 움직인다.
 * 각 크레인은 무게 제한이 있다. 이 무게 제한보다 무거운 박스는 크레인으로 움직일 수 없다.
 * 모든 박스를 배로 옮기는데 드는 시간의 최솟값을 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 N이 주어진다. N은 50보다 작거나 같은 자연수이다.
 * 둘째 줄에는 각 크레인의 무게 제한이 주어진다. 이 값은 1,000,000보다 작거나 같다.
 * 셋째 줄에는 박스의 수 M이 주어진다. M은 10,000보다 작거나 같은 자연수이다.
 * 넷째 줄에는 각 박스의 무게가 주어진다. 이 값도 1,000,000보다 작거나 같은 자연수이다.
 *
 * 출력
 * 첫째 줄에 모든 박스를 배로 옮기는데 드는 시간의 최솟값을 출력한다. 만약 모든 박스를 배로 옮길 수 없으면 -1을 출력한다.
 */
public class Prob1092 {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        /**
         * prevPositions : 각 크레인마다 마지막으로 옮긴 박스의 위치를 담고 있음
         * cranes : 각 크레인마다 옮길 수 있는 최대 무게
         */
        int n = sc.nextInt();
        Integer[] prevPositions = new Integer[n];
        List<Integer> cranes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            prevPositions[i] = -1;
            cranes.add(sc.nextInt());
        }

        /**
         * visited : 각 박스마다 옮겼는지 확인
         * boxes : 각 박스의 무게
         */
        int m = sc.nextInt();
        boolean[] visited = new boolean[m];
        List<Integer> boxes = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            visited[i] = false;
            boxes.add(sc.nextInt());
        }

        /**
         * 내림차순으로 정렬
         */
        Collections.sort(cranes);
        Collections.reverse(cranes);

        Collections.sort(boxes);
        Collections.reverse(boxes);

        /**
         * 가장 무거운 박스가 가장 많은 무게를 들 수 있는 크레인보다 무거우면 해결 불가능
         * => -1 출력
         */
        if (boxes.get(0) > cranes.get(0)) {
            System.out.println(-1);
        } else {
            int cnt = 0;
            int time = 0;

            /**
             * cnt: 배에 옮긴 박스의 갯수
             * 
             * 배에 옮긴 박스의 갯수가 총 박스의 갯수와 같을때까지 반복
             */
            while (cnt < m) {
                /**
                 * 가장 많은 무게를 들 수 있는 크레인부터 남은 박스 중에서 가장 무거운 박스를 들도록 함
                 * 이때, 여러 크레인이 동시에 박스들을 옮길 수 있기 때문에 모든 크레인이 대해서 현재 자신이 옮길 수 있는 가장 무거운 박스를 찾아서 옮기도록 함
                 * => 1분 소모
                 */
                for (int i = 0; i < n; i++) {
                    /**
                     * flag 의 값이 0으면, 크레인이 남은 박스들 중에서 더 이상 옮길 수 있는 박스 존재 X
                     */
                    int flag = 0;
                    /**
                     * 박스의 위치는 0~m 까지 순서대로 존재
                     * prevPositions 에는 각 크레인마다 마지막으로 옮긴 박스의 위치 저장
                     * 박스를 0 번째 인덱스부터 옮길 수 있는 지 확인하기 때문에, 마지막으로 옮긴 박스의 위치 이전 위치에 있는 박스들은 이미 옮긴 상태
                     * => 그러므러, 마지막으로 옮긴 박스 위치 + 1 의 인덱스에 위치한 박스부터 탐색 시작
                     */
                    for(int j = prevPositions[i] + 1; j < m; j++) {
                        /**
                         * 크레인이 옮길 수 있는 박스인지 확인 && 이미 옮긴 박스가 아닌지 확인
                         */
                        if (cranes.get(i) >= boxes.get(j) && visited[j] == false) {
                            visited[j] = true;
                            prevPositions[i] = j;
                            cnt++;
                            flag = 1;
                            break;
                        }
                    }
                    /**
                     * 해당 크레인은 남은 박스들 중에서 더 이상 옮길 수 있는 박스가 없기 때문에, 해당 크레인이 마지막으로 옮긴 박스의 위치를 m으로 업데이트
                     * => 더 이상 해당 크레인에 대해서는 남은 박스들을 옮길 수 있는 확인 X
                     */
                    if (flag == 0) {
                        prevPositions[i] = m;
                    }
                }
                time++;
            }
            System.out.println(time);
        }
    }
}

package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 1
 *
 * 문제
 * 강을 가로지르는 하나의 차선으로 된 다리가 하나 있다. 이 다리를 n 개의 트럭이 건너가려고 한다.
 * 트럭의 순서는 바꿀 수 없으며, 트럭의 무게는 서로 같지 않을 수 있다. 다리 위에는 단지 w 대의 트럭만 동시에 올라갈 수 있다.
 * 다리의 길이는 w 단위길이(unit distance)이며, 각 트럭들은 하나의 단위시간(unit time)에 하나의 단위길이만큼만 이동할 수 있다고 가정한다.
 * 동시에 다리 위에 올라가 있는 트럭들의 무게의 합은 다리의 최대하중인 L보다 작거나 같아야 한다.
 * 참고로, 다리 위에 완전히 올라가지 못한 트럭의 무게는 다리 위의 트럭들의 무게의 합을 계산할 때 포함하지 않는다고 가정한다.
 * 예를 들어, 다리의 길이 w는 2, 다리의 최대하중 L은 10, 다리를 건너려는 트럭이 트럭의 무게가 [7, 4, 5, 6]인 순서대로 다리를 오른쪽에서 왼쪽으로 건넌다고 하자.
 * 이 경우 모든 트럭이 다리를 건너는 최단시간은 아래의 그림에서 보는 것과 같이 8 이다.

 * 다리의 길이와 다리의 최대하중, 그리고 다리를 건너려는 트럭들의 무게가 순서대로 주어졌을 때, 모든 트럭이 다리를 건너는 최단시간을 구하는 프로그램을 작성하라.
 *
 * 입력
 * 입력 데이터는 표준입력을 사용한다. 입력은 두 줄로 이루어진다. 입력의 첫 번째 줄에는 세 개의 정수 n (1 ≤ n ≤ 1,000) , w (1 ≤ w ≤ 100) and L (10 ≤ L ≤ 1,000)이 주어지는데, n은 다리를 건너는 트럭의 수, w는 다리의 길이, 그리고 L은 다리의 최대하중을 나타낸다. 입력의 두 번째 줄에는 n개의 정수 a1, a2, ⋯ , an (1 ≤ ai ≤ 10)가 주어지는데, ai는 i번째 트럭의 무게를 나타낸다.
 *
 * 출력
 * 출력은 표준출력을 사용한다. 모든 트럭들이 다리를 건너는 최단시간을 출력하라.
 */
public class Prob13335 {

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String conditionsLine = br.readLine();
        String[] conditionsSplit = conditionsLine.split(" ");
        int n = Integer.valueOf(conditionsSplit[0]);
        int w = Integer.valueOf(conditionsSplit[1]);
        int l = Integer.valueOf(conditionsSplit[2]);


        String trucksLine = br.readLine();
        String[] trucksSplit = trucksLine.split(" ");

        List<Integer> trucks = new ArrayList<>();
        for (String t : trucksSplit) {
            trucks.add(Integer.valueOf(t));
        }

        int time = 0;
        int curW = 0; // Current Bridge Weight
        int curT = 0; // Number of trucks currently on the bridge
        List<Integer> bridge = new ArrayList<>();
        for (int i = 0; i < w; i++) {
            bridge.add(0);
        }

        /**
         * 1. 매 회차마다 시간 1 증가 && bridge dequeue
         * 2. Dequeue한 값이 0이 아니면 Truck이 다리를 건넜음으로, 현재 다리를 건너고 있는 트럭의 수(curT) 값을 1 감소
         * 3. 다음 순서의 트럭이 다리를에 올라갈 수 있는 지 확인
         * 4. 만약, 다음 순서 트럭이 다리에 올라갈 수 없으면, bridge에 0 추가
         */
        while (!trucks.isEmpty()) {
            Integer removeTruck = bridge.remove(0);
            time += 1;
            curW -= removeTruck;
            if (removeTruck != 0) {
                curT -= 1;
            }

            Integer nextTruck = trucks.get(0);
            if ((nextTruck + curW) <= l && curT < w) {
                trucks.remove(0);
                curW += nextTruck;
                bridge.add(nextTruck);
                curT += 1;
            } else {
                bridge.add(0);
            }
        }

        /**
         * 모든 트럭이 다리에 올라가서 truck이 empty일때, 다리에는 여전히 다리를 건너는 트럭이 있을 수 있음
         * 해당 트럭들이 모두 다리를 건너는데 걸리는 시간 계산
         */
        while (!bridge.isEmpty()) {
            bridge.remove(0);
            time += 1;
        }

        System.out.println(time);
    }

}

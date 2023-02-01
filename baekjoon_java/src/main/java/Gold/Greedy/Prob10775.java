package Gold.Greedy;

import java.io.*;

/**
 * Gold 2(공항)
 *
 * https://www.acmicpc.net/problem/10775
 *
 * Solution: Greedy + Union-Find
 * 1. 각 비행기가 들어오는 순서대로 도킹함
 * 2. 이때, 각 i번째 비행기는 1~g(i) 게이트에 도킹할 수 있음
 * 3. g(i) > g(i+1) 일 수 있기 때문에, i번째 비행기를 g(i) 번째 부터 도킹이 가능한지 확인 (g(i) -> 1)
 * 4. 단순 반복문으로 g(i)부터 1까지 탐색하면서 도킹이 가능한지 확인하면 시간 초과 발생
 *  -> 그러므로 Union-Find로 탐색
 * 5. ex:
 * 만약에 3번 게이트에 이미 비행기가 있고, g(i) 가 3 일때:
 *  1. 3번 게이트부터 도킹 가능한지 탐색
 *  2. 이때, 불가능하면 parent[3]의 값으로 이동해서 도킹 가능한지 확인
 *  3. 도킹이 가능할때까지 2번 반복
 *  4. parent[3]을 업데이트
 *  5. 만약에 0에 도달하면, 더 이상 도킹 가능한 게이트가 없기 때문에 종료
 */
public class Prob10775 {

    static int g, p;
    static int[] docked;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        g = Integer.parseInt(br.readLine());
        p = Integer.parseInt(br.readLine());

        docked = new int[g + 1];
        for (int i = 1; i < g + 1; i++) {
            docked[i] = i;
        }
        int cnt = 0;
        for (int i = 0; i < p; i++) {
            int plane = Integer.parseInt(br.readLine());
            int dock = findRoot(plane);
            if (dock == 0) {
                break;
            }

            /**
             * plane이 dock에 비행기 도킹을 완료 했음
             * 그러므로, 똑같은 값의 plane이 들어오면 dock에 비행기를 도킹할 수 없음
             * docked[plane]의 값을 현재 게이트 번호는 dock의 바로 이전 게이트인 dock - 1을 가르키도록 업데이트
             * 그러면, 똑같은 값의 plane이 들어오면 dock이 아닌 dock - 1 번째 게이트를 확인
             * 그러므로, 모든 게이트를 확인할 필요 X -> 시간 절약
             */
            docked[plane] = dock - 1;
            docked[dock] = dock - 1;
            cnt++;
        }

        System.out.println(cnt);
    }

    public static int findRoot(int node) {
        if (docked[node] == node) {
            return node;
        }

        return docked[node] = findRoot(docked[node]);
    }

}

package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Silver 2
 *
 * 문제
 * 차세대 영농인 한나는 강원도 고랭지에서 유기농 배추를 재배하기로 하였다.
 * 농약을 쓰지 않고 배추를 재배하려면 배추를 해충으로부터 보호하는 것이 중요하기 때문에, 한나는 해충 방지에 효과적인 배추흰지렁이를 구입하기로 결심한다.
 * 이 지렁이는 배추근처에 서식하며 해충을 잡아 먹음으로써 배추를 보호한다.
 * 특히, 어떤 배추에 배추흰지렁이가 한 마리라도 살고 있으면 이 지렁이는 인접한 다른 배추로 이동할 수 있어, 그 배추들 역시 해충으로부터 보호받을 수 있다.
 * 한 배추의 상하좌우 네 방향에 다른 배추가 위치한 경우에 서로 인접해있는 것이다.
 *
 * 한나가 배추를 재배하는 땅은 고르지 못해서 배추를 군데군데 심어 놓았다.
 * 배추들이 모여있는 곳에는 배추흰지렁이가 한 마리만 있으면 되므로 서로 인접해있는 배추들이 몇 군데에 퍼져있는지 조사하면 총 몇 마리의 지렁이가 필요한지 알 수 있다.
 * 예를 들어 배추밭이 아래와 같이 구성되어 있으면 최소 5마리의 배추흰지렁이가 필요하다. 0은 배추가 심어져 있지 않은 땅이고, 1은 배추가 심어져 있는 땅을 나타낸다.
 *
 * 1	1	0	0	0	0	0	0	0	0
 * 0	1	0	0	0	0	0	0	0	0
 * 0	0	0	0	1	0	0	0	0	0
 * 0	0	0	0	1	0	0	0	0	0
 * 0	0	1	1	0	0	0	1	1	1
 * 0	0	0	0	1	0	0	1	1	1
 *
 * 입력
 * 입력의 첫 줄에는 테스트 케이스의 개수 T가 주어진다.
 * 그 다음 줄부터 각각의 테스트 케이스에 대해 첫째 줄에는 배추를 심은 배추밭의 가로길이 M(1 ≤ M ≤ 50)과 세로길이 N(1 ≤ N ≤ 50), 그리고 배추가 심어져 있는 위치의 개수 K(1 ≤ K ≤ 2500)이 주어진다.
 * 그 다음 K줄에는 배추의 위치 X(0 ≤ X ≤ M-1), Y(0 ≤ Y ≤ N-1)가 주어진다. 두 배추의 위치가 같은 경우는 없다.
 *
 * 출력
 * 각 테스트 케이스에 대해 필요한 최소의 배추흰지렁이 마리 수를 출력한다.
 * 
 * Solution:
 * 1. (0, 0)부터 밭을 훑기 시작
 * 2. 배추가 심어진 곳을 발견하면 정지
 * 3. 해당 배추로 부터 인접한 배추들 찾기 -> 인접한 배추들이랑 또 인접한 배추들 찾기 -> 반복
 *  -> DFS
 *  4. 이렇게 찾은 배추들은 모두 하나의 흰지렁이만 있으면 됨
 *  5. 해당 배추들은 더 이상 고려 대상이 아니기 때문에 해당 배추들이 심어져 있는 장소의 값을 0 으로 업데이트
 */
public class Prob1012 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.valueOf(br.readLine());
        for (int i = 0; i < t; i++) {
            String[] conditions = br.readLine().split(" ");
            int m = Integer.valueOf(conditions[0]);
            int n = Integer.valueOf(conditions[1]);
            int k = Integer.valueOf(conditions[2]);

            Integer[][] farm = new Integer[n][m];
            for (int ty = 0; ty < n; ty++) {
                for (int tx = 0; tx < m; tx++) {
                    farm[ty][tx] = 0;
                }

            }

            for (int j = 0; j < k; j++) {
                String[] location = br.readLine().split(" ");
                int x = Integer.valueOf(location[0]);
                int y = Integer.valueOf(location[1]);
                farm[y][x] = 1;
            }

            int cnt = 0;
            for (int ty = 0; ty < n; ty++) {
                for(int tx = 0; tx < m; tx++){
                    /**
                     * 배추가 심어져 있는지 확인
                     */
                    if (farm[ty][tx] == 1) {
                        dfs(farm, n, m, ty, tx);
                        cnt++;
                    }
                }
            }
            System.out.println(cnt);
        }
    }

    public static void dfs(Integer[][] farm, int n, int m, int ty, int tx) {
        /**
         * 해당 배추는 더 이상 확인 할 필요 X -> 해당 배추가 심어진 곳을 0으로 바꿔줌
         */
        farm[ty][tx] = 0;

        /**
         * DFS로 인접한 배추들 모두 찾기
         * -> 배추 A에 인접한 배추 B가 배추 C랑 인접해 있으면 A, B , C 모두 하나의 흰지렁이로 해결 가능
         */
        if (ty + 1 < n) {
            if (farm[ty + 1][tx] == 1) {
                dfs(farm, n, m, ty + 1, tx);
            }
        }
        if (ty - 1 >= 0) {
            if(farm[ty-1][tx] == 1){
                dfs(farm, n, m, ty -1, tx);
            }
        }
        if (tx + 1 < m) {
            if(farm[ty][tx+1] == 1){
                dfs(farm, n, m, ty, tx + 1);
            }
        }
        if (tx - 1 >= 0) {
            if(farm[ty][tx-1] == 1){
                dfs(farm, n, m, ty, tx - 1);
            }
        }
    }
}

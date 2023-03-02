package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 5(톱니바퀴)
 *
 * https://www.acmicpc.net/problem/14891
 * 
 * Solution: 구현
 * 각 톱니바퀴의 정보를 gears[][] 에 저장
 *  -> gears[1][0] == 첫번째 톱니바퀴의 12시 방향 정보, gears[1][2] == 첫번째 톱니바퀴의 3시 방향 정보 ...
 * 시계 방향으로 돌면: gears[n][i] = gears[n][i-1]
 * 반시계 방향으로 돌면: gears[n][i] = gears[n][i+1]
 * 
 * 인접한 톱니바퀴를 돌릴수 있는지 확인
 * 돌리수 있으면, 해당 톱니바퀴로 이동해서 돌려줌
 * 이때, 톱니바퀴는 한번에 다 같이 돌아가기 때문에, 인접한 톱니바퀴 중에서 직전에 확인한 톱니바퀴는 다시 탐색 X
 * ex: 2번째 톱니바퀴를 돌릴고, 조건을 만족해서 1, 3번째 톱니바퀴 모두 돌아가는 상황일때, 2번째 톱니바퀴를 돌리고 1이랑 3번째 톱니바퀴로 이동
 * 1번째에 인접한 톱니바퀴는 2번인데, 이미 2번은 돌렸기 때문에 다시 탐색 X
 * 3번째에 인접한 톱니바퀴는 2, 4번인데, 이미 2번은 돌렸기 때문에 다시 탐색 X, 4번 돌릴수 있는지 확인하고 돌릴수 있으면 돌리기
 *  
 */
public class Prob14891 {

    static int[][] gears = new int[5][8];
    static int k;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int i = 1; i <= 4; i++){
            String cur = br.readLine();
            for(int j = 0; j < 8; j++){
                gears[i][j] = cur.charAt(j) - '0';
            }
        }

        k = Integer.parseInt(br.readLine());
        for(int i = 0; i < k; i++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            moveGear(n, d, 0);
        }

        int ans = 0;
        int point = 1;
        for(int i = 1; i <= 4; i++){
            if(gears[i][0] == 1){
                ans += point;
            }
            point *= 2;
        }
        System.out.println(ans);
    }

    public static void moveGear(int n, int d, int prev){
        int nx1 = n + 1;
        int nx2 = n - 1;

        int right = gears[n][2];
        int left = gears[n][6];

        /*
        현재 톱니바퀴 돌리기
         */
        if(d == -1){
            int first = gears[n][0];
            for(int i = 0; i < 7; i++){
                gears[n][i] = gears[n][i + 1];
            }
            gears[n][7] = first;
        }else{
            int last = gears[n][7];
            for(int i = 7; i >= 1; i--){
                gears[n][i] = gears[n][i - 1];
            }
            gears[n][0] = last;
        }

        /*
        인접한 톱니바퀴도 같이 돌아가야하는 상황인지 확인하고, 같이 돌아야하면 해당 톱니바퀴들도 회전시킴
         */
        if(nx1 <= 4){
            if(gears[nx1][6] != right && nx1 != prev){
                moveGear(nx1, d * (-1), n);
            }
        }
        if(nx2 >= 1){
            if(gears[nx2][2] != left && nx2 != prev){
                moveGear(nx2, d * (-1), n);
            }
        }
    }
}
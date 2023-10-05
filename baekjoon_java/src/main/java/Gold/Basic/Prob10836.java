package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 4(여황벌)
 *
 * https://www.acmicpc.net/problem/10836
 * 
 * Solution: 구현/누적합
 * 1. 2 <= m <= 700, 1 <= n <= 1,000,000 이므로, 아무런 최적화 없이 그대로 하면 m*m*n이 되기 때문에 시간초과 발생
 * 2. 그렇기 때문에 자신이 자라는 정도를 스스로 결정하는 애벌레들을 n번째 날까지 모두 입력에 따라 더해 줌(누적함)
 * 3. 2번을 거치고 나서 나머지 애벌레들은 규칙에 따라서 자라게 하면됨
 *  -> N + m*m 번으로 정답 구할 수 있음
 */
public class Prob10836 {

    static int m, n;
    static int[] sums;
    static int[][] diff;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        diff = new int[m][m];
        sums = new int[2*m - 1];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int zs = Integer.parseInt(st.nextToken());
            int os = Integer.parseInt(st.nextToken());
            int ts = Integer.parseInt(st.nextToken());

            int idx = 0;
            while(zs > 0){
                sums[idx] += 0;
                zs--;
                idx++;
            }
            while(os > 0){
                sums[idx] += 1;
                os--;
                idx++;
            }
            while(ts > 0){
                sums[idx] += 2;
                ts--;
                idx++;
            }
        }


        int midI = (m + 1)/2;
        diff[0][0] = sums[midI];
        for(int y = 0; y < m; y++){
            diff[y][0] = sums[m - 1 - y] + 1;
        }
        for(int x = 0; x < m; x++){
            diff[0][x] = sums[m - 1 + x] + 1;
        }

        StringBuilder ans = new StringBuilder();
        for(int y = 0; y < m; y++){
            for(int x = 0; x < m; x++){
                if(x != 0 && y != 0){
                    diff[y][x] = Math.max(diff[y-1][x-1], Math.max(diff[y-1][x], diff[y][x-1]));
                }
                ans.append(diff[y][x]).append(" ");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }
}
package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(원판 돌리기)
 *
 * https://www.acmicpc.net/problem/17822
 *
 * Solution: 구현
 */
public class Prob17822 {

    static int n, m, t;
    static int[][] board;
    static boolean[][] removed;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        board = new int[n + 1][m];
        removed = new boolean[n + 1][m];
        for(int i = 1; i < n + 1; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < m; j++){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < t; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            for(int j = 1; j < n + 1; j++){
                if(j % x == 0){
                    int[] tmp = new int[m];
                    boolean[] removedTmp =  new boolean[m];
                    for(int r = 0; r < m; r++){
                        // 숫자들을 회전 시킬때, 해당 숫자가 제거 됐는지도 위치가 변하기 때문에 removed도 같이 업데이트 해줘야 됨
                        if(d == 0){ // 시계 방향
                            tmp[(r + k) % m] = board[j][r];
                            removedTmp[(r + k) % m] = removed[j][r];
                        }else{
                            tmp[(r - (k % m) + m) % m] = board[j][r];
                            removedTmp[(r - (k % m) + m) % m] = removed[j][r];
                        }
                    }
                    for(int r = 0; r < m; r++){
                        board[j][r] = tmp[r];
                        removed[j][r] = removedTmp[r];
                    }
                }
            }
            checkAdj(); // 인접한 숫자들 확인하기
        }

        int answer = 0;
        for(int i = 1; i < n + 1; i++){
            for(int j = 0; j < m; j++){
                if(!removed[i][j]){
                    answer += board[i][j];
                }
            }
        }
        System.out.println(answer);
    }

    public static void checkAdj(){
        boolean[][] removedTmp = new boolean[n + 1][m];

        boolean flag = true;
        for (int i = 1; i < n + 1; i++) {
            for(int j = 0; j < m; j++){
                int nx1 = j - 1 < 0 ? m - 1 : j - 1;
                int nx2 = j + 1 >= m ? 0 : j + 1;
                if(board[i][j] == board[i][nx1] && !removed[i][j] && !removed[i][nx1]){
                    flag = false;
                    removedTmp[i][j] = true;
                    removedTmp[i][nx1] = true;
                }
                if(board[i][j] == board[i][nx2]  && !removed[i][j] && !removed[i][nx2]){
                    flag = false;
                    removedTmp[i][j] = true;
                    removedTmp[i][nx2] = true;
                }
            }
        }

        for (int j = 0; j < m; j++) {
            for(int i = 1; i < n + 1; i++){
                int ny1 = i - 1;
                int ny2 = i + 1;
                if(ny1 > 1){
                    if(board[i][j] == board[ny1][j] && !removed[i][j] && !removed[ny1][j]){
                        flag = false;
                        removedTmp[i][j] = true;
                        removedTmp[ny1][j] = true;

                    }
                }
                if(ny2 <= n){
                    if(board[i][j] == board[ny2][j] && !removed[i][j] && !removed[ny2][j]){
                        flag = false;
                        removedTmp[i][j] = true;
                        removedTmp[ny2][j] = true;
                    }

                }
            }
        }
        // 위에서 실시간으로 removed를 바로 업데이트 하면, 인접한 숫자들을 구할때 영향을 미치기 때문에 나중에 한번에 업데이트
        // ex: 1 2 2 2 가 있고 m = 1일때, m = 0이랑 m = 2 확인
        // 만약 실시간으로 업데이트 하면, m = 2가 됐을때 m = 3이 같지만 무시하게 됨
        for(int i = 1; i < n + 1; i++){
            for(int j = 0; j < m; j++){
                if(removedTmp[i][j]){
                    removed[i][j] = true;
                    board[i][j] = 0;
                }
            }
        }

       if(flag){
           int cnt = 0;
           int sum = 0;
           for(int i = 1; i < n + 1; i++){
               for(int j = 0; j < m; j++){
                   if(!removed[i][j]){
                       cnt++;
                       sum += board[i][j];
                   }
               }
           }

           for(int i = 1; i < n + 1; i++){
               for(int j = 0; j < m; j++){
                   if(!removed[i][j]){
                       if(cnt * board[i][j] > sum){
                           board[i][j]--;
                       }else if(cnt * board[i][j] < sum){
                           board[i][j]++;
                       }
                   }
               }
           }
       }
    }
}

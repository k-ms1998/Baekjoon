package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 2(성냥개비)
 *
 * https://www.acmicpc.net/problem/3687
 * 
 * Solution: DP
 */
public class Prob3687 {

    static int[] req = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};

    /*
    성냥 개비 0개 또는 1개로는 숫자를 만들 수 없기 떄문에 -1
    성냥개비 2개로 만들수 있는 가장 작은 수 = 1
    성냥개비 3개로 만들수 있는 가장 작은 수 = 7
    성냥개비 4개로 만들수 있는 가장 작은 수 = 4
    성냥개비 5개로 만들수 있는 가장 작은 수 = 2
    성냥개비 6개로 만들수 있는 가장 작은 수 = 0
    성냥개비 7개로 만들수 있는 가장 작은 수 = 8
    => 이 배열을(arr)를 통해 현재 만들어진 숫자들에(minDp) 새로운 숫자 추가
     */
    static int[] arr= {-1, -1, 1,7,4,2,0,8};
    static long[] maxDp;
    static long[] minDp;
    static final int INF = 10000000;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0){
            int n = Integer.parseInt(br.readLine());
            maxDp = new long[n + 1];
            minDp = new long[101];
            Arrays.fill(minDp, Long.MAX_VALUE);

            minDp[2]=1; // 성냥개비 2개로 만들수 있는 가장 작은 수 = 1
            minDp[3]=7; // 성냥개비 3개로 만들수 있는 가장 작은 수 = 7
            minDp[4]=4; // 성냥개비 4개로 만들수 있는 가장 작은 수 = 4
            minDp[5]=2; // 성냥개비 5개로 만들수 있는 가장 작은 수 = 2
            minDp[6]=6; // 성냥개비 6개로 만들수 있는 가장 작은 수 = 6 (숫자는 0으로 시작할 수 없기 때문에 0이 아니라 6이 됨)
            minDp[7]=8; // 성냥개비 7개로 만들수 있는 가장 작은 수 = 8
            minDp[8]=10; // 성냥개비 8개로 만들수 있는 가장 작은 수 = 10

            for(int i = 9; i < n + 1; i++){
                /*
                 성냥개비로 숫자 0~9를 만드는데 필요한 성냥개비의 수즌 2이상 7이하
                 -> 0 = 6개, 1 = 1개, 2 = 5개, 3 = 5개, 4 = 4개, 5 = 5개, 6 = 6개, 7 = 3개, 8 = 7개, 9 = 6개
                 */
                for(int j = 2; j <= 7; j++){ 
                    /*
                    현재 사용해야되는 성냥개비의 수 = i개
                    dp[i] = 10 * dp[i-j] + arr[j]
                    => dp[i-j]는 성냥개비 i-j개를 써서 만들 수 있는 가장 작은 수
                        arr[j]는 성냥개비 j를 써서 만들 수 있는 가장 작은 수
                        => 성냥개비 i-j개를 써서 만들 수 있는 가장 작은 수 뒤에 성냥개비 j를 써서 만들 수 있는 가장 작은 수를 추가해서 성냥개비 i개를 써서 만들 수 있는 가장 작은 수 구하기
                     */
                    int idx = i - j;
                    minDp[i] = Math.min(minDp[i], 10 * minDp[idx] + arr[j]);
                }
            }

            String max = "";
            if(n % 2 == 0){
                max = findMax(n/2);
            }else{
                max = findMax((n-3)/2);
                String tmp = "7";
                tmp += max;
                max = tmp;
            }


            ans.append(minDp[n]).append(" ").append(max).append("\n");
        }


        System.out.println(ans);
    }

    public static long findNum(long num){

        return String.valueOf(num).length();
    }

    public static String findMax(int num){
        String tmp = "";

        for(int i = 0; i < num; i++){
            tmp += "1";
        }

        return tmp;
    }


}
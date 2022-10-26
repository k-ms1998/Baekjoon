package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(저울)
 *
 * https://www.acmicpc.net/problem/2437
 *
 * Solution: 수학
 */
public class Prob2437 {

    static int n;
    static int[] num;

    static long ans = 0L;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        num = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            num[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(num);

        if(num[0] > 1){
            /**
             * 가장 작은 값이 1보다 크면, 1부터 만들 수 없으므로 답은 1
             */
            ans = 1;
        }else{
            /**
             * 1. 0~i번째 인덱스의 합 + 1 값을 구하고, 해당 값과 i + 1 번째의 값과 비교함
             * 2-1. 합 + 1이 i + 1 번째 값보다 크거나 같으면, i + 1 인덱스로 이동해서  1번 반복
             *  -> 0~i 번째까지 숫자들로, 1~합 사이의 모든 값들은 만들 수 있음
             *      -> 그러므로, i + 1 숫자도 추가를 하면, 1 ~ i + 1 까지의 합도 만들 수 있음
             *      -> ex) 0 ~ i 까지의 합: sum= 10, i + 1번째 값: num = 5
             *          -> 0~i 번째까지의 합으로 1~10 까지 만들 수 있으므로, num 까지 이용해서 11, 12, 13, 14, 15 까지도 만들 수 있음 -> (5 + 6, 5 + 7, 5 + 8, 5 + 9, 5 + 10)
             *              -> 그러므로, 다음 인덱스로 넘어감
             * 2-2. 합 + 1이 i + 1 번째 값보다 작으면, 답이 합 + 1
             *      -> ex) 0 ~ i 까지의 합: sum= 10, i + 1번째 값: num = 15
             *          -> 0~i 번째까지의 합으로 1~10 까지 만들 수 있는, 1~10까지의 숫자랑 12를 조합했을때, 11, 12, 13, 14 숫자들은 만들 수 있는 방법이 없음 -> 그러므로, 답은 11
             *
             */
            int curNum = num[0];
            for(int i = 1; i < n; i++){
                if(curNum + 1 < num[i]){
                    break;
                }
                curNum += num[i];
            }
            ans = curNum + 1;
        }

        System.out.println(ans);
    }
}
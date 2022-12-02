package Gold.DP;

import java.io.*;
import java.util.*;

public class Prob2011 {

    static int[] num;

    static int[] dp;
    static final int MOD = 1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String row = br.readLine();
        int size = row.length();
        int reduce = 0;
        num = new int[size + 1];
        boolean invalid = false;
        for (int i = 1; i < size + 1; i++) {
            num[i - reduce] = Integer.parseInt(String.valueOf(row.charAt(i - 1)));
            /**
             * 알파벳은 1~26 까지 가능
             * 그러므로, 만약 0을 입력 받았으면, 바로 직전의 숫자가 무조거 1 또는 2이어야 함 -> 0을 직전에 나왔던 숫자(1 또는 2)랑 합치기 => (1 -> 10 OR 2 -> 20)
             * 그 외의 경우는 모두 잘못된 암호 -> invalid = true
             */
            if (num[i - reduce] == 0) {
                if (i == 0) {
                    invalid = true;
                    break;
                } else {
                    if (num[i - reduce - 1] <= 2 && num[i - reduce - 1] >= 1) {
                        num[i - reduce - 1] *= 10;
                        reduce++;
                    } else {
                        invalid = true;
                        break;
                    }
                }
            }
        }

        if (invalid) {
            System.out.println(0);
        } else {
            size -= reduce;
            dp = new int[size + 1];
            dp[0] = 1;
            dp[1] = 1;
            /**
             * 1. 앞서 나온 숫자가 10이거나 현재 숫자가 10이면 지금까지의 암호의 현재 숫자를 추가하는 방법 밖에 없기 때문에 dp[i] = dp[i-1]
             * 2. 직전에 나온 숫자 + 현재 숫자로 만든 숫자가 26 이하이면 지금까지의 암호의 현재 숫자를 추가하거나, 직전 숫자와 조합을 할 수 있기 때문에(ex: (1, 1) -> (1, 12), (1, 1, 2)) dp[i] = dp[i-1] + dp[i-2]
             * 3. 직전에 나온 숫자 + 현재 숫자로 만든 숫자가 26을 넘으면 지금까지의 암호의 현재 숫자를 추가하는 방법 밖에 없기 때문에 dp[i] = dp[i - 1]
             */
            for (int i = 2; i < size + 1; i++) {
                if (num[i - 1] % 10 == 0 || num[i] % 10 == 0) {
                    dp[i] = dp[i - 1] % MOD;
                } else if (10 * num[i - 1] + num[i] <= 26) {
                    dp[i] = dp[i - 1] % MOD + dp[i - 2] % MOD;
                } else {
                    dp[i] = dp[i - 1] % MOD;
                }
            }

            System.out.println(dp[size]);
        }
    }
}

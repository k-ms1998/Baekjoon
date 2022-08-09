package Gold.BruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * Solution: BruteForce
 */
public class Prob1107 {

    static int s = 100;
    static int n;
    static String nString;
    static int m;
    static boolean[] buttons;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        nString = br.readLine();
        n = Integer.valueOf(nString);
        m = Integer.valueOf(br.readLine());


        buttons = new boolean[10];
        for (int i = 0; i < 10; i++) {
            buttons[i] = false;
        }
        if (m > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < m; i++) {
                int broken = Integer.parseInt(st.nextToken());
                buttons[broken] = true;
            }
        }

        /**
         * 1. n 보다 큰 숫자 중에서, 고장나지 않은 버튼으로 누를 수 있는 n이랑 가장 가까운 숫자 구하기
         */
        int closeN1 = 1000000;
        for(int i = n; i <  1000000; i++){
            String str = String.valueOf(i);
            int size = str.length();
            boolean valid = true;
            for (int j = 0; j < size; j++) {
                int curDigit = str.charAt(j) - '0';
                if (buttons[curDigit]) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                closeN1 = i;
                break;
            }
        }

        /**
         * 1. n 보다 작은 숫자 중에서, 고장나지 않은 버튼으로 누를 수 있는 n이랑 가장 가까운 숫자 구하기
         */
        int closeN2 = -100000;
        for(int i = n; i >=0 ; i--){
            String str = String.valueOf(i);
            int size = str.length();

            boolean valid = true;
            for (int j = 0; j < size; j++) {
                int curDigit = str.charAt(j) - '0';
                if (buttons[curDigit]) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                closeN2 = i;
                break;
            }
        }

        /**
         * 3. 두개의 채널에 도달하기 위해 눌러야하는 버튼의 횟수
         */
        int diff1 = Math.abs(n - closeN1) + String.valueOf(closeN1).length();
        int diff2 = Math.abs(n - closeN2) + String.valueOf(closeN2).length();

        /**
         * 4. 시작 채널(100)에서 +/- 로만 이동했을때, 숫자 버튼으로 근접한 채널 도달 후 +/- 로 이동했을때 중 눌러야 되는 버튼 횟수의 최솟값 구하기 == 답
         */
        int ans1 = Math.abs(n - s);
        int ans2 = diff1 > diff2 ? diff2 : diff1;
        System.out.println(ans1 > ans2 ? ans2 : ans1);
    }
}
//1000
//2
//0 1

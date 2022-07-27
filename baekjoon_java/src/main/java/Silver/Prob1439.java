package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Silver 5: Greedy
 *
 * 문제
 * 다솜이는 0과 1로만 이루어진 문자열 S를 가지고 있다. 다솜이는 이 문자열 S에 있는 모든 숫자를 전부 같게 만들려고 한다.
 * 다솜이가 할 수 있는 행동은 S에서 연속된 하나 이상의 숫자를 잡고 모두 뒤집는 것이다. 뒤집는 것은 1을 0으로, 0을 1로 바꾸는 것을 의미한다.
 * <p>
 * 예를 들어 S=0001100 일 때,
 * <p>
 * 전체를 뒤집으면 1110011이 된다.
 * 4번째 문자부터 5번째 문자까지 뒤집으면 1111111이 되어서 2번 만에 모두 같은 숫자로 만들 수 있다.
 * 하지만, 처음부터 4번째 문자부터 5번째 문자까지 문자를 뒤집으면 한 번에 0000000이 되어서 1번 만에 모두 같은 숫자로 만들 수 있다.
 * <p>
 * 문자열 S가 주어졌을 때, 다솜이가 해야하는 행동의 최소 횟수를 출력하시오.
 * <p>
 * 입력
 * 첫째 줄에 문자열 S가 주어진다. S의 길이는 100만보다 작다.
 * <p>
 * 출력
 * 첫째 줄에 다솜이가 해야하는 행동의 최소 횟수를 출력한다
 */
public class Prob1439 {

    static String[] s;
    static int size;
    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        s = br.readLine().split("");
        size = s.length;

        String[] ones = new String[size];
        for (int i = 0; i < size; i++) {
            ones[i] = s[i];
        }
        int onesAns = flip(ones, "1");

        ans = 0;
        String[] zeros = new String[size];
        for (int i = 0; i < size; i++) {
            zeros[i] = s[i];
        }
        int zerosAns =  flip(zeros, "0");

        ans = zerosAns < onesAns ? zerosAns : onesAns;
        System.out.println(ans);
    }

    public static int flip(String[] arr, String target) {
        int cnt = 0;
        for(int i = 0; i < size; i++){
            int connectedCnt = 1;
            if (!arr[i].equals(target)) {
                cnt++;
                arr[i] = target;
                for (int j = i + 1; j < size; j++) {
                    if (!arr[j].equals(target)) {
                        arr[j] = target;
                        connectedCnt++;
                        continue;
                    } else {
                        break;
                    }
                }
            }
            i += connectedCnt - 1;
        }

        return cnt;
    }
}

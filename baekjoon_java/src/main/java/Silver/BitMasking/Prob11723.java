package Silver.BitMasking;

import java.io.*;
import java.util.*;

/**
 * 비트연산자 참고:
 * https://myeongju00.tistory.com/30
 * https://loosie.tistory.com/238
 * https://hailey-v.tistory.com/23
 * https://eunhee-programming.tistory.com/66
 *
 * Silver 5(집합)
 *
 * https://www.acmicpc.net/problem/11723
 *
 * Solution: 비트연산자
 */
public class Prob11723 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int m = Integer.parseInt(br.readLine());
        int[] num = new int[21];
        int bit = 0;
        StringBuilder ans = new StringBuilder();

        while(m-- > 0){
            st = new StringTokenizer(br.readLine());
            String command = st.nextToken();

            if(command.equals("add")){
                /**
                 * OR(|) 연산자 => 둘중 하나라도 1이면 1 반환 => (1 | 1) = 1, (1 | 0) = 1, (0 | 0) = 0
                 * bit = 10000. num = 3 -> 01000
                 * (10000) | (01000) = 11000 => 3변 원소가 add 됨 (3번 원소는 왼쪽에서부터 3번; 인덱스는 0부터 시작)
                 */
                int x = Integer.parseInt(st.nextToken());
                bit = bit | (1 << x);
            } else if (command.equals("remove")) {
                /**
                 * AND(&) 연산자 => 둘 다 1일때 1 반환 => (1 & 1) = 1, (1 & 0) = 0, (0 & 0) = 0
                 * bit = 11000, num = 3 -> 01000; ~(01000) = 10111
                 * (11000) & (10111) = 10000 => 3번 원소가 remove 됨 (3번 원소는 왼쪽에서부터 3번; 인덱스는 0부터 시작)
                 */
                int x = Integer.parseInt(st.nextToken());
                bit = bit & ~(1 << x);
            } else if (command.equals("check")) {
                /**
                 * AND(&) 연산자 => 둘 다 1일때 1 반환 => (1 & 1) = 1, (1 & 0) = 0, (0 & 0) = 0
                 * bit = 11000, num = 3 -> 01000
                 * (11000) & (01000) = 01000 -> 위에서 (1 << num) 한 값과 같음 -> check 했을때 1 반환
                 *
                 * bit = 10000, num = 3 -> 01000
                 * (10000) & (01000) = 00000 -> 위에서 (1 << num) 한 값과 다름 -> check 했을때 0 반환
                 */
                int x = Integer.parseInt(st.nextToken());
                boolean check = (bit & (1 << x)) == (1 << x);
                ans.append(check ? 1 : 0).append("\n");
            } else if (command.equals("toggle")) {
                /**
                 * XOR(^) 연산자 => 서로 다를때 1 반환 -> (1 ^ 1) = 0, (0 ^ 0) = 0, (1 ^ 0) = 1
                 * bit = 11000, num = 3 -> 01000
                 * (11000) ^ (01000) = 10000
                 */
                int x = Integer.parseInt(st.nextToken());
                bit = bit ^ (1 << x);
            } else if (command.equals("all")) {
                /**
                 * 이진수의 뺄셈: 10000(2) - 1(10) = 10000(2) - 00001(2) = 1111(2)
                 */
                bit = (1 << 22) - 1;
            } else if (command.equals("empty")) {
                bit = 0;
            }
        }

        System.out.println(ans);
    }
}

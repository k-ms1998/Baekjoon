package Gold.BitMasking;

import java.io.*;
import java.util.*;

/**
 * Gold 4(가르침)
 * 
 * https://www.acmicpc.net/problem/1062
 * 
 * Solution:조합론+비트마스킹(Optional) (비트마스킹을 사용하지 않고,각 알파벳을 사용했는지 배열을 사용해서 풀이 가능)
 * 1. 총 k 개로 조합할 수 있는 앞파벳들 찾기
 * 2. 각 조합에 사용된 알파벳들로 만들 수 있는 단어들 찾기
 *
 * 참고: 'a'-'a' = 0, 'z'-'b' = 1, 'z'-'a' = 25
 * 비트마스킹을 이용해서 앞바벳들의 조합 찾기
 * ex: abcde 의 알파벳 조합 = 00000000000000000000011111
 */
public class Prob1062 {

    static int n, k;
    static int answer = 0;
    static String[] words;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        words = new String[n];
        for(int i = 0; i < n; i++){
            words[i] = br.readLine();
        }

        // 각 단어는 'anta'로 시작하고 'tica'로 끝나기 때문에 무조건 'a', 'c', 'i', 'n', 't' 를 포함하고 있아야함 -> k가 무조건 5이상이어야 단어를 만들 수 있음
        if (k >= 5) {
            int INIT_BIT = (1 << ('a' - 'a')) | (1 << ('c' - 'a')) | (1 << ('i' - 'a'))
                    | (1 << ('n' - 'a')) | (1 << ('t' - 'a'));
            findComb(INIT_BIT, 0, 5); // 시간 단축을 위해서 조합이 'a', 'c', 'i', 'n', 't' 를 포함하고 있는 초기 상태에서 시작
        }

        System.out.println(answer);
    }

    public static void findComb(int bit, int idx, int depth) {
        if(depth >= k){

            int cnt = 0;
            for(int i = 0; i < n; i++){
                boolean flag = true;
                /*
                각 단어에 대해서, 처음 4자리는 무조건 'anta', 마지막 4자리는 무조건 'tica' 이므로, 처음과 마지막 4자리는 확인 X
                 
                bit & (1 << ('a' - 'a')) == (1 << ('a' - 'a')) => bit에 'a' 가 포함되어 있음  
                 
                 */
                for(int j = 4; j < words[i].length() - 4; j++){
                    // bit 에 (1 << (words[i].charAt(j) - 'a')) 가 포함되어 있지 않기 때문에, bit 의 조합으로는 words[i]를 만들 수 없음  
                    if ((bit & (1 << (words[i].charAt(j) - 'a'))) != (1 << (words[i].charAt(j) - 'a'))) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    ++cnt;
                }
            }

            answer = Math.max(answer, cnt);
            return;
        }

        for(int i = idx; i < 26; i++){
            /**
             * AND(&) -> 둘다 1이면 1반환 -> (1 | 1) = 1, (1 | 0) = 0, (0 | 0) = 0
             * (11000) & (01000) = (01000) => 그러므로, (11000)에 (01000)이 포함되어 있음
             *
             *
             * OR(|) -> 둘 중 하나라도 1이면 1반환 -> (1 | 1) = 1, (1 | 0) = 1, (0 | 0) = 0
             * (10000) | (01000) = (11000)
             */
            if((bit & (1 << i)) != (1 << i)){ // bit 에 포함되어 있지 않으면 추가하기
                findComb(bit | (1 << i), i + 1, depth + 1);
            }
        }
    }
}

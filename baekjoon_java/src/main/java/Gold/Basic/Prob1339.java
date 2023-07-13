package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 4(단어 수학)
 *
 * https://www.acmicpc.net/problem/1339
 *
 * Solution: Greedy
 * 1. 알파벳 중에서 가장 많이 나타나는 알파벳부터 가장 큰 수를 지정해줌 (ex: A가 가장 많이 나타나면 9 지정, 그 다음으로 많이 나타나는 알파벳이 C이면 C에는 8 지정 ...) => 그리디
 * -> !! 중요 !!
 *  -> 만약에 문자가 ACDEB이면, A는 10000번, C는 1000번, D는 100번, E는 10번, B는 1번 나타난것으로 생각
 * EX:
 * ACDEB ->, A는 10000번, C는 1000번, D는 100번, E는 10번, B는 1번 나타난것으로 생각
 * GCF -> G는 100번, C는 10번, F는 1번 나타난것으로 생각
 * ACDEB + GCF => A는 10000번, C는 1010번, D는 100번, G는 100번, E는 10번, B는 1번, F는 1번 나타남
 *              => A는 9, C는 8, D는 7, G는 6, E는 5, B는 4, F는 3로 결정 (그리디)
 *              => 10000*9 + 1010*8 +100*7 + 100*6 + 10*5 + 1*4 + 1*3
 *                  = 90000 + 8080 + 700 + 600 + 100 + 40 + 3 + 2
 *                  = 99437
 */
public class Prob1339 {

    static int[] alpha = new int[26];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        for(int i = 0; i < n; i++){
            String cur = br.readLine();
            int p = 1;
            for(int j = cur.length() - 1; j >= 0; j--){
                int idx = cur.charAt(j) - 'A';
                alpha[idx] += p;
                p *= 10;
            }
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        for(int i = 0; i < 26; i++){
            if(alpha[i] != 0){
                queue.offer(alpha[i]);
            }
        }

        int value = 9;
        int answer = 0;
        while(!queue.isEmpty()){
            int count = queue.poll();
            answer += (count * value);
            value--;
        }

        System.out.println(answer);
    }

}

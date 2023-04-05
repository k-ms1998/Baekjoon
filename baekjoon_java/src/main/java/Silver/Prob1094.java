package Silver;

import java.io.*;


/**
 * Silver 5(막대기)
 *
 * https://www.acmicpc.net/problem/1094
 *
 * Solution: 이진수
 * 64cm의 막대기를 시작으로, 모든 막대기들을 절반으로만 자를 수 있음
 * => 64, 32, 16, 8, 4, 2, 1 크기의 막대기를 모두 만들 수 있음
 * 그러므로, 각 막대기를 하나씩 사용하면 64이하의 모든 숫자를 만들 수 있음
 * 
 * 목표 크기 x를 이진수로 변환하면 답을 쉽게 구할 수 있음
 */
public class Prob1094 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int answer = 0;
        while(n >  0){
            int rem = n % 2;
            if(rem == 1){
                answer++;
            }
            n /= 2;
        }

        System.out.println(answer);
    }
}

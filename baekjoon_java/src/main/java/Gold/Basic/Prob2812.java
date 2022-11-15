package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(크게 만들기)
 * 
 * https://www.acmicpc.net/problem/2812
 * 
 * Solution: Deque
 */
public class Prob2812 {

    static int n, k;
    static int[] num;
    static Deque<Integer> queue = new ArrayDeque<>();

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int size = n - k;
        String numStr = br.readLine();
        num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(String.valueOf(numStr.charAt(i)));
        }

        /**
         * 숫자의 순서도 중요하기 때문에, 모든 숫자들을 순서대로 탐색하면서 Deque 에 추가하고 제거하면서 답을 구한다
         */
        for(int i = 0; i < n; i++){
            int remainder = n - i;
            /**
             * 가장 큰 숫자들이 무조건 앞에 있어야 제일 큰 값을 구할 수 있으므로,
             * 현재 숫자보다 먼저 나온 숫자들 중에서 현재 숫자보다 작은 숫자들을 제거해줌
             * 이때, 최종 정답은 n-k 자릿수를 가진 숫자인데, 현재 숫자보다 작은 숫자들을 지웠을때, 아직 탐색하지 않은 남은 숫자들로 n-k 자릿수를 만들 수 없는 경우에는 제거하는 것을 멈춤
             */
            while (!queue.isEmpty()) {
                if(remainder <= size - queue.size()){
                    break;
                }
                if (queue.peekLast() < num[i]) {
                    queue.pollLast();
                } else {
                    break;
                }
            }
            if(queue.size() < size){
                queue.offer(num[i]);
            }
        }

        /**
         * 정답은 최대 500,000 자릿수의 숫자일 수 있기 때문에, int 또는 long 의 범위를 벗어남으로 String 으로 출력
         */
        while (!queue.isEmpty()) {
            ans.append(queue.pollFirst());
        }
        System.out.println(ans);
    }
}
/*
11 5
41772520061
 */

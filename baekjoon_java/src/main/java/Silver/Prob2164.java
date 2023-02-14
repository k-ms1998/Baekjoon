package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 4(카드 2)
 *
 * https://www.acmicpc.net/problem/2164
 */
public class Prob2164 {

    public static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        Deque<Integer> queue = new ArrayDeque<>();
        for(int i = 1; i < n + 1; i++){
            queue.offer(i);
        }

        int flag = 0;
        int popped = 0;
        while(!queue.isEmpty()){
            popped = queue.poll();
            if(flag % 2 == 1){
                queue.offer(popped);
            }
            ++flag;
        }

        System.out.println(popped);
    }
}
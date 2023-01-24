package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 3(1로 만들기)
 *
 * https://www.acmicpc.net/problem/1463
 *
 * Solution: BFS
 */
public class Prob1463 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] cnt = new int[n + 1];
        Arrays.fill(cnt, Integer.MAX_VALUE);

        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(n, 0));
        cnt[n] = 0;
        while (!queue.isEmpty()) {
            Info cur = queue.poll();
            int num = cur.num;
            int c = cur.c;
            if (num <= 1) {
                break;
            }

            if (num % 3 == 0) {
                int next = num / 3;
                if (next > 0) {
                    if (cnt[next] > c + 1) {
                        cnt[next] = c + 1;
                        queue.offer(new Info(next, c + 1));
                    }
                }
            }
            if (num % 2 == 0) {
                int next = num / 2;
                if(next > 0){
                    if (cnt[next] > c + 1) {
                        cnt[next] = c + 1;
                        queue.offer(new Info(next, c + 1));
                    }
                }
            }
            int next = num - 1;
            if(next > 0){
                if (cnt[next] > c + 1) {
                    cnt[next] = c + 1;
                    queue.offer(new Info(next, c + 1));
                }
            }
        }

        System.out.println(cnt[1]);
    }

    static class Info{
        int num;
        int c;

        public Info(int num, int c) {
            this.num = num;
            this.c = c;
        }
    }
}

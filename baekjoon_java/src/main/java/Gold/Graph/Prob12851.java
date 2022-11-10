package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 4(숨바꼭질 2)
 *
 * https://www.acmicpc.net/problem/12851
 *
 * Solution: BFS
 */
public class Prob12851 {

    static int s, d;
    static int[] dist;
    static int SIZE;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        if (d <= s) {
            ans.append(s - d).append("\n");
            ans.append(1);
        }else{
            SIZE = 2 * d + 1;
            dist = new int[SIZE];
            for (int i = 0; i < SIZE; i++) {
                dist[i] = Integer.MAX_VALUE;
            }
            
            Deque<Info> queue = new ArrayDeque<>();
            queue.offer(new Info(s, 0));
            dist[s] = 0;
            while (!queue.isEmpty()) {
                Info cur = queue.poll();
                int x = cur.x;
                int time = cur.time;

                if (x == d) {
                    break;
                }

                int[] nx = new int[3];
                nx[0] = x - 1;
                nx[1] = x + 1;
                nx[2] = 2 * x;
                for (int i = 0; i < 3; i++) {
                    if (nx[i] < 0 || nx[i] >= SIZE) {
                        continue;
                    }

                    if (dist[nx[i]] > time + 1) {
                        dist[nx[i]] = time + 1;
                        queue.offer(new Info(nx[i], time + 1));
                    }
                }
            }

            ans.append(dist[d]).append("\n");
            ans.append(backTrace());
        }
        System.out.println(ans);
    }

    /**
     * 역추적
     */
    public static int backTrace() {
        int cnt = 0;
        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(d, dist[d]));

        while (!queue.isEmpty()) {
            Info cur = queue.poll();
            int x = cur.x;
            int time = cur.time;

            if (x == s) {
                cnt++;
                continue;
            }

            int[] nx = new int[2];
            int tmpSize = 2;
            if (x % 2 == 0) {
                nx = new int[3];
                nx[2] = x / 2;
                tmpSize = 3;
            }
            nx[0] = x - 1;
            nx[1] = x + 1;
            for (int i = 0; i < tmpSize; i++) {
                if (nx[i] < 0 || nx[i] >= SIZE) {
                    continue;
                }

                if (dist[nx[i]] == time - 1) {
                    queue.offer(new Info(nx[i], time - 1));
                }
            }
        }

        return cnt;
    }

    public static class Info {
        int x;
        int time;

        public Info(int x, int time) {
            this.x = x;
            this.time = time;
        }
    }
}

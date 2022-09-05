package Silver.DP;

import java.io.IOException;
import java.util.*;

/**
 * Silver 1
 *
 * https://www.acmicpc.net/problem/12852
 *
 * Rules:
 * X가 3으로 나누어 떨어지면, 3으로 나눈다.
 * X가 2로 나누어 떨어지면, 2로 나눈다.
 * 1을 뺀다.
 *
 * Solution: DP(BFS)
 *
 */
public class Prob12852 {

    static int n;

    static int[] count;
    static int[] prevArr;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        count = new int[n + 1];
        prevArr = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            count[i] = Integer.MAX_VALUE;
        }

        bfs();

        StringBuffer ans = new StringBuffer();
        ans.append(count[1] + "\n");
        Stack<Integer> stack = new Stack<>();
        int tmpNum = 1;
        while (true) {
            if (tmpNum == n) {
                stack.push(tmpNum);
                break;
            }
            stack.push(tmpNum);
            tmpNum = prevArr[tmpNum];
        }

        while (!stack.isEmpty()) {
            ans.append(stack.pop());
            ans.append(" ");
        }

        System.out.println(ans);
    }

    public static void bfs() {
        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(n, 0));
        count[n] = 0;

        while (!queue.isEmpty()) {
            Info info = queue.poll();
            int curNum = info.num;
            int curCnt = info.cnt;

            if (curNum == 1) {
                break;
            }

            if (curNum % 3 == 0) {
                int nx = curNum / 3;
                if (count[nx] > curCnt + 1) {
                    count[nx] = curCnt + 1;
                    queue.offer(new Info(nx, curCnt + 1));
                    prevArr[nx] = curNum;
                }
            }

            if (curNum % 2 == 0) {
                int nx = curNum / 2;
                if (count[nx] > curCnt + 1) {
                    count[nx] = curCnt + 1;
                    queue.offer(new Info(nx, curCnt + 1));
                    prevArr[nx] = curNum;
                }
            }

            if(curNum - 1 >= 1){
                int nx = curNum - 1;
                if (count[nx] >= curCnt + 1) {
                    count[nx] = curCnt + 1;
                    queue.offer(new Info(nx, curCnt + 1));
                    prevArr[nx] = curNum;
                }
            }

        }
    }

    static class Info{
        int num;
        int cnt;

        public Info(int num, int cnt) {
            this.num = num;
            this.cnt = cnt;
        }
    }
}

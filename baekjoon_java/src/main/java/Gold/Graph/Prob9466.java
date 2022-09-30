package Gold.Graph;

import java.util.*;
import java.io.*;

/**
 * Gold 3(텀 프로젝트)
 *
 * https://www.acmicpc.net/problem/9466
 *
 * Solution: DFS
 */
public class Prob9466 {

    static int n;
    static int[] num;
    static boolean[] isTeam;
    static boolean[] visited;
    static int cnt;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            n = Integer.parseInt(br.readLine());
            num = new int[n + 1];
            isTeam = new boolean[n + 1];
            visited = new boolean[n + 1];
            cnt = 0;

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < n + 1; i++) {
                num[i] = Integer.parseInt(st.nextToken());
            }

            for(int i = 1; i < n + 1; i++){
                checkCycle(i);
            }

            System.out.println(n - cnt);
        }
    }

    public static void checkCycle(int cur) {
        visited[cur] = true;

        int next = num[cur];
        if (visited[next]) {
            if (!isTeam[next]) {
                cnt++;
                while (cur != next) {
                    cnt++;
                    next = num[next];
                }
            }
        } else {
            checkCycle(next);
        }

        isTeam[cur] = true;
    }
}

package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 4(단어 섞기)
 *
 * https://www.acmicpc.net/problem/9177
 *
 * Solution: BFS
 */
public class Prob9177 {

    static boolean found = false;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for(int i = 1; i <= t; i++){
            found = false;
            String row  = br.readLine();
            String[] arr = row.split(" ");

            Deque<int[]> queue = new ArrayDeque<>();
            boolean[][] check = new boolean[201][201];
            queue.add(new int[]{0, 0, 0});
            check[0][0] = true;

            while(!queue.isEmpty()){
                int[] q = queue.poll();
                int a = q[0];
                int b = q[1];
                int c = q[2];

                if(c == arr[2].length()){
                    found = true;
                    break;
                }

                if(a < arr[0].length() && !check[a+1][b] && arr[0].charAt(a) == arr[2].charAt(c)) {
                    queue.add(new int[] {a+1, b, c+1});
                    check[a+1][b] = true;
                }

                if(b< arr[1].length() && !check[a][b+1] && arr[1].charAt(b) == arr[2].charAt(c)) {
                    queue.add(new int[] {a, b+1, c+1});
                    check[a][b+1] = true;
                }
            }

            ans.append(String.format("Data set %d: ", i)).append(found ? "yes" : "no").append("\n");
        }

        System.out.println(ans);
    }

    public static class Info{
        int cur;
        int idx;

        public Info(int cur, int idx){
            this.cur = cur;
            this.idx = idx;
        }
    }

}
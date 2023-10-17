package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * Gold 2(교환)
 *
 * https://www.acmicpc.net/problem/1039
 *
 * Solution: DFS(Bruteforce)
 */
public class Prob1039 {

    static int n, k;
    static int size;
    static int[] org;
    static boolean[][] visited = new boolean[10][1000001];
    static int answer = -1;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        String s = st.nextToken();
        n = Integer.parseInt(s);
        k = Integer.parseInt(st.nextToken());

        size = s.length();
        org = new int[size];
        int p = 10;
        for(int i = size - 1; i >= 0; i--){
            int cur = n % 10;
            org[i] = cur;
            n /= p;
        }

        findAnswer(0, org);

        System.out.println(answer);
    }

    public static void findAnswer(int depth, int[] arr){
        if(arr[0] == 0){
            return;
        }
        int num = 0;
        for(int i = 0; i < size; i++){
            num = 10 * num + arr[i];
        }


        if(depth >= k){
            answer = Math.max(answer, num);

            return;
        }

        if(visited[depth][num]){
            return;
        }

        visited[depth][num] = true;


        for(int i = 0; i < size - 1; i++){
            int a = arr[i];
            for(int j = i + 1; j < size; j++){
                int b = arr[j];

                arr[j] = a;
                arr[i] = b;

                findAnswer(depth + 1, arr);

                arr[j] = b;
                arr[i] = a;
            }
        }
    }
}
package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 3(N과 M(3))
 *
 * https://www.acmicpc.net/problem/15651
 * 
 * Solution: DFS
 * 1. 단순 조합론 문제
 * 2. 1부터 N까지 순서대로 숫자를 추가해서 수열을 만들기 떄문에 사전 순으로 오름차순으로 수열이 생성됨
 */
public class Prob15651 {

    static int n, m;
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for(int i = 1; i < n + 1; i++){
            findAnswer(1, String.valueOf(i));
        }

        System.out.println(ans);

    }

    public static void findAnswer(int depth, String cur){
        if(depth == m){
            ans.append(cur).append("\n");
            return;
        }

        for(int i = 1; i < n + 1; i++){
            findAnswer(depth + 1, cur + " " + i);
        }
    }
}
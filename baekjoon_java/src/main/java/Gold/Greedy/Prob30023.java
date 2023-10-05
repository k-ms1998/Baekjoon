package Gold.Greedy;

import java.io.*;
import java.util.*;

/**
 * Gold 5(전구 상태 바꾸기)
 *
 * https://www.acmicpc.net/problem/30023
 *
 * Solution: Greedy
 */
public class Prob30023 {


    static int n;
    static int[] org;
    static int[] orgA;
    static int[] orgB;
    static final int INF = 100000000;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        org = new int[n];
        orgA = new int[n];
        orgB = new int[n];

        String s = br.readLine();
        for(int i = 0; i < n; i++){
            char c = s.charAt(i);
            if(c == 'R'){
                org[i] = 0;
            }else if(c == 'G'){
                org[i] = 1;
            }else{
                org[i] = 2;
            }

            orgA[i] = org[i];
            orgB[i] = org[i];
        }


        orgA[0] = (orgA[0] + 1) % 3;
        orgA[1] = (orgA[1] + 1) % 3;
        orgA[2] = (orgA[2] + 1) % 3;

        orgB[0] = (orgB[0] + 2) % 3;
        orgB[1] = (orgB[1] + 2) % 3;
        orgB[2] = (orgB[2] + 2) % 3;

        int answerA = findAnswer(org, 0);
        int answerB = findAnswer(orgA, 1);
        int answerC = findAnswer(orgB, 2);

        int answer = Math.min(answerA, Math.min(answerB, answerC));

        System.out.println(answer == INF ? -1 : answer);
    }

    public static int findAnswer(int[] bulb, int cnt){
        for(int i = 1; i < n - 2; i++){
            if(bulb[i] != bulb[i-1]){
                while(bulb[i] != bulb[i-1]){
                    cnt++;
                    bulb[i] = (bulb[i] + 1) % 3;
                    bulb[i + 1] = (bulb[i + 1] + 1) % 3;
                    bulb[i + 2] = (bulb[i + 2] + 1) % 3;
                }
            }
        }

        return bulb[n-3] == bulb[n-2] && bulb[n-2] == bulb[n-1] ? cnt : INF;
    }
}

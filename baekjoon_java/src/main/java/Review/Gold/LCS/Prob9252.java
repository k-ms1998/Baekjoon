package Review.Gold.LCS;

import java.io.*;
import java.util.*;

public class Prob9252 {

    static int[][] dp;
    static char[] arrA;
    static char[] arrB;
    static int sizeA, sizeB;
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String strA = br.readLine();
        String strB = br.readLine();

        sizeA = strA.length();
        sizeB = strB.length();

        arrA = new char[sizeA + 1];
        for(int i = 0; i < sizeA; i++){
            arrA[i + 1] = strA.charAt(i);
        }
        arrB = new char[sizeB + 1];
        for(int i = 0; i < sizeB; i++){
            arrB[i + 1] = strB.charAt(i);
        }

        dp = new int[sizeB + 1][sizeA + 1];
        for(int y = 1; y < sizeB + 1; y++){
            char b = arrB[y];
            for(int x = 1; x < sizeA + 1; x++){
                char a = arrA[x];
                if(a == b){
                    dp[y][x] = dp[y-1][x-1] + 1;
                }else{
                    dp[y][x] = Math.max(dp[y-1][x], dp[y][x - 1]);
                }
            }
        }

        ans.append(dp[sizeB][sizeA]).append("\n");
        findRoute(sizeA, sizeB, "");

        System.out.println(ans);
    }

    public static void findRoute(int x, int y, String route){
        if(dp[y][x] == 0){
            for(int i = route.length() - 1; i >= 0; i--){
                ans.append(route.charAt(i));
            }
            return;
        }

        if(dp[y-1][x] == dp[y][x]){
            findRoute(x, y - 1, route);
        }else if(dp[y][x - 1] == dp[y][x]){
            findRoute(x - 1, y, route);
        }else{
            findRoute(x - 1, y - 1, route + String.valueOf(arrA[x]));
        }


    }

}

package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 5(부분 문자열)
 *
 * https://www.acmicpc.net/problem/6550
 */
public class Prob6550 {

    public static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while(true){
            String curRow = br.readLine();
            if(curRow == null){
                break;
            }
            String[] rowSplit = curRow.split(" ");
            String strA = rowSplit[0];
            String strB = rowSplit[1];

            if(isSubString(strA, strB)){
                ans.append("Yes").append("\n");
            }else{
                ans.append("No").append("\n");
            }
        }

        System.out.println(ans);
    }

    public static boolean isSubString(String strA, String strB){
        int idxA = 0;

        char curStrA = strA.charAt(idxA);
        for(int i = 0; i < strB.length(); i++){
            if(curStrA == strB.charAt(i)){
                idxA++;
                if(idxA == strA.length()){
                    return true;
                }
                curStrA = strA.charAt(idxA);
            }
        }

        return idxA == strA.length() ? true : false;
    }

}
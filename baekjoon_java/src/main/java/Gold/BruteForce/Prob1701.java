package Gold.BruteForce;

import java.io.*;
import java.util.*;

public class Prob1701 {

    static String str;
    static int size;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        str = br.readLine();
        size = str.length();

        int left = 0;
        int right = size;
        while(left <= right){
            int mid = (left + right)/2;

            if(checkString(mid)){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        System.out.println(left - 1);
    }

    public static boolean checkString(int target){
        Set<String> set = new HashSet<>();
        for(int i = 0; i < size; i++){
            if(i + target > size){
                break;
            }

            String cur = str.substring(i, i + target);
            if(set.contains(cur)){
                return true;
            }
            set.add(cur);
        }

        return false;
    }

}
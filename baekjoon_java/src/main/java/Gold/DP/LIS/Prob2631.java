package Gold.DP.LIS;

import java.io.*;
import java.util.*;

public class Prob2631 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int size;
    static int[] lis;
    static int lastIdx = 0;

    public static void main(String[] args) throws IOException{
        size = Integer.parseInt(br.readLine());

        lis = new int[size];

        for(int idx = 0; idx < size; idx++) {
            int num = Integer.parseInt(br.readLine());
            if(lastIdx == 0) {
                lastIdx++;
                lis[0] = num;
                continue;
            }


            int lastNum = lis[lastIdx - 1];
            if(lastNum < num) {
                lis[lastIdx] = num;
                lastIdx++;
            }else {
                int left = 0;
                int right = lastIdx - 1;

                while(left < right) {
                    int mid = (left + right) / 2;
                    int midNum = lis[mid];

                    if(midNum < num) {
                        left = mid + 1;
                    }else {
                        right = mid;
                    }
                }

                lis[left] = num;
            }

        }

        System.out.println(size - lastIdx);
    }

}
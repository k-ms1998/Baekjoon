package Gold;
import java.io.*;
import java.util.*;

public class Prob2632 {

    static int target;
    static int n, m;
    static int[] cutN;
    static int[] cutM;
    static int[] sumM;
    static int[] sumN;
    static int[] cntM;
    static int[] cntN;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        target = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        cutM = new int[m];
        sumM = new int[m];
        for(int i = 0; i < m; i++){
            int cur = Integer.parseInt(br.readLine());
            if(i == 0){
                sumM[i] = cur;
            }else{
                sumM[i] = sumM[i - 1] + cur;
            }
        }

        cutN = new int[n];
        sumN = new int[n];
        for(int i = 0; i < n; i++){
            int cur = Integer.parseInt(br.readLine());
            if(i == 0){
                sumN[i] = cur;
            }else{
                sumN[i] = sumN[i - 1] + cur;
            }
        }

        cntM = new int[target + 1];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < m - 1; j++){
                int cur = sumM[j];
                if(cur <= target){
                    cntM[cur]++;
                }
            }
            if(m == 1){
                break;
            }
            int diff = sumM[m - 1] - sumM[m - 2];
            for(int j = m - 1; j >= 1; j--){
                sumM[j] = sumM[j - 1] + diff;
            }
            sumM[0] = diff;
        }

        cntN = new int[target + 1];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n - 1; j++){
                int cur = sumN[j];
                if(cur <= target){
                    cntN[cur]++;
                }
            }
            if(n == 1){
                break;
            }
            int diff = sumN[n - 1] - sumN[n - 2];
            for(int j = n - 1; j >= 1; j--){
                sumN[j] = sumN[j - 1] + diff;
            }
            sumN[0] = diff;
        }

        int answer = 0;
        cntM[0] = 1;
        cntN[0] = 1;
        if(sumM[m-1] <= target){
            cntM[sumM[m-1]] = 1;
        }
        if(sumN[n-1] <= target){
            cntN[sumN[n-1]] = 1;
        }
        for(int i = 0; i <= target/2; i++){
            answer += (cntM[i] * cntN[target - i] + cntM[target-i] * cntN[i]);
        }
        if(target % 2 == 0){
            int mid = target / 2;
            answer -= cntM[mid] * cntN[mid];
        }

        System.out.println(answer);
    }

}
package Gold.BinarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 */
public class Prob2470 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] num = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(num);

        int l = 0;
        int r = n - 1;
        int numA = num[0];
        int numB = num[n-1];
        int diff = Integer.MAX_VALUE;
        while (l < r) {
            int sum = num[l] + num[r];
            int curDiff = Math.abs(sum);

            if (curDiff < diff) {
                diff = curDiff;
                numA = num[l];
                numB = num[r];
            }

            if (sum > 0) {
                r--;
            } else if (sum < 0) {
                l++;
            } else {
                break;
            }
        }

        System.out.println(numA + " " + numB);
    }
}

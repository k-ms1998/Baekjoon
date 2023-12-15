package Review.Gold.Bruteforce;

import java.io.*;
import java.util.*;

public class Prob2143 {

    static long t;
    static int n, m;
    static int[] arr1;
    static int[] arr2;
    static long[] sums1;
    static long[] sums2;
    static Map<Long, Long> map1 = new HashMap<>();
    static Map<Long, Long> map2 = new HashMap<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        t = Integer.parseInt(br.readLine());

        n = Integer.parseInt(br.readLine());
        arr1 = new int[n + 1];
        sums1 = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= n; i++){
            arr1[i] = Integer.parseInt(st.nextToken());
            sums1[i] = sums1[i-1] + arr1[i];
        }

        m = Integer.parseInt(br.readLine());
        arr2 = new int[m + 1];
        sums2 = new long[m + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= m; i++){
            arr2[i] = Integer.parseInt(st.nextToken());
            sums2[i] = sums2[i-1] + arr2[i];
        }

        long answer = 0;
        for(int i = 1; i <= n; i++){
            for(int j = i; j <= n; j++){
                long sum = sums1[j] - sums1[i] + arr1[i];
                long value = map1.getOrDefault(sum, 0L);
//                System.out.println("sum = " + sum);
                map1.put(sum, value + 1);
            }
        }
        for(int i = 1; i <= m; i++){
            for(int j = i; j <= m; j++){
                long sum = sums2[j] - sums2[i] + arr2[i];
                long value = map2.getOrDefault(sum, 0L);
                map2.put(sum, value + 1);
            }
        }
//        System.out.println("map1 = " + map1);
//        System.out.println("map2 = " + map2);
        for (long k1 : map1.keySet()) {
            long v1 = map1.get(k1);
            long k2 = t - k1;
            long v2 = map2.getOrDefault(k2, 0L);

            answer += (v1 * v2);
        }

        System.out.println(answer);
    }
}

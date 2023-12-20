package Gold.Greedy;

import java.io.*;
import java.util.*;

public class Prob2141 {

    static int n;
    static Village[] v;
    static Long INF = Long.MAX_VALUE;
    static long[] sum;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        v = new Village[n];
        sum = new long[n];
        long totalA = 0L;
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            long tmpX = Long.parseLong(st.nextToken());
            long tmpA = Long.parseLong(st.nextToken());

            totalA += tmpA;
            v[i] = new Village(tmpX, tmpA);
        }
        Arrays.sort(v, new Comparator<Village>(){
            @Override
            public int compare(Village v1, Village v2){
                return v1.x < v2.x ? -1 : v1.x == v2.x ? 0 : 1;
            }
        });

        int idx = 0;
        for(int i = 0; i < n; i++){
            if(i == 0){
                sum[i] = v[i].a;
            }else{
                sum[i] = sum[i - 1] + v[i].a;
            }
            if(2*sum[i] >= totalA){
                idx = i;
                break;
            }
        }

        System.out.println(v[idx].x);
    }

    public static class Village{
        long x;
        long a;

        public Village(long x, long a){
            this.x = x;
            this.a = a;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", a=" + a + "}";
        }
    }
}
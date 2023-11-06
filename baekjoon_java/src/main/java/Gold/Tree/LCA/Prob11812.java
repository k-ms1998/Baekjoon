package Gold.Tree.LCA;

import java.io.*;
import java.util.*;

/**
 * Gold 3(K진 트리)
 *
 * https://www.acmicpc.net/problem/11812
 *
 * Solution: LCA
 */
public class Prob11812{

    static long n;
    static int k, q;
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Long.parseLong(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        for(int i = 0; i < q; i++){
            st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long y = Long.parseLong(st.nextToken());
            if(k==1){
                ans.append(Math.abs(x - y)).append("\n");
                continue;
            }

            long hx = findH(x);
            long hy = findH(y);
            int cnt = 0;
            while(hx != hy){
                if(hx > hy){
                    hx--;
                    x = findParent(x);
                }else{
                    hy--;
                    y = findParent(y);
                }
                cnt++;
            }

            while(x != y){
                x = findParent(x);
                y = findParent(y);
                cnt += 2;
            }
            ans.append(cnt).append("\n");
        }

        System.out.println(ans);
    }

    static long findParent(long num) {
        return ((num - 2)/k) + 1;
    }

    public static long findH(long num){
        if(num==1){
            return 1;

        }

        long cur = 1L;
        long h = 1L;
        long e = k;
        while(cur < num) {
            cur += e;
            e *= k;
            h++;
        }

        return h;
    }
}
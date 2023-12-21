package Gold.Greedy;

import java.io.*;
import java.util.*;

public class Prob2258 {

    static int n, m;
    static Meat[] meats;
    static Meat[] sums;
    static final Long INF = 2_147_483_648L;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int maxPrice = 0;
        meats = new Meat[n];
        sums = new Meat[n];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken()); // 무게
            int p = Integer.parseInt(st.nextToken()); // 가격

            meats[i] = new Meat(w, p);
            sums[i] = new Meat(0,0);
        }
        Arrays.sort(meats, new Comparator<Meat>(){
            @Override
            public int compare(Meat m1, Meat m2){
                if(m1.p == m2.p){
                    return m2.w - m1.w;
                }

                return m1.p - m2.p;
            }
        });


        long answer = INF;

        sums[0] = meats[0];
        for(int i = 1; i < n; i++){
            sums[i].w = sums[i-1].w + meats[i].w;
            if(meats[i].p == meats[i - 1].p){
                sums[i].p = sums[i-1].p + meats[i].p;
            }else{
                sums[i].p = meats[i].p;
            }
        }
        for(int i = 0; i < n; i++){
            if(sums[i].w >= m){
                answer = Math.min(answer, sums[i].p);
            }
        }

        System.out.println(answer == INF ? -1 : answer);
    }



    public static class Meat{
        int w;
        int p;

        public Meat(int w, int p){
            this.w = w;
            this.p = p;
        }

        @Override
        public String toString(){
            return "{w=" + w + ", p=" + p + "}";
        }
    }
}
/*
10 14
2 3
2 4
2 5
3 1
1 3
7 9
7 3
8 4
10 3
3 10
*/

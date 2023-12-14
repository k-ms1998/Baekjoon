package Review.Gold.Greedy;

import java.io.*;
import java.util.*;

public class Prob2109 {

    static int n;
    static int[] total;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        PriorityQueue<Info> queue = new PriorityQueue<>(new Comparator<Info>(){
            @Override
            public int compare(Info i1, Info i2){
                return i2.p - i1.p;
            }
        });
        int maxD = 0;
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            maxD = Math.max(maxD, d);
            queue.offer(new Info(p, d));
        }

        total = new int[maxD + 1];
        while(!queue.isEmpty()){
            Info info = queue.poll();
            int p = info.p;
            int d = info.d;

            for(int i = d; i > 0; i--){
                if(total[i] < p){
                    total[i] = p;
                    // System.out.println(info);
                    break;
                }
            }
        }

        for(int i = 2; i < maxD + 1; i++){
            total[i] += total[i-1];
        }

        System.out.println(total[maxD]);
    }

    public static class Info{
        int p;
        int d;

        public Info(int p, int d){
            this.p = p;
            this.d = d;
        }

        @Override
        public String toString(){
            return "{p=" + p + ", d=" + d + "}";
        }
    }

}
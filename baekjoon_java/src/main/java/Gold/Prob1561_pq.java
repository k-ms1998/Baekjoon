package Gold;

import java.io.*;
import java.util.*;

public class Prob1561_pq {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] rides = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            rides[i] = Integer.parseInt(st.nextToken());
        }


        int ans = 0;
        if(n > m){
            PriorityQueue<Info> queue = new PriorityQueue<>(new Comparator<Info>(){
                @Override
                public int compare(Info i1, Info i2){
                    if (i1.start == i2.start) {
                        return i1.num - i2.num;
                    }
                    return i1.start - i2.start;
                }
            });
            /*
            Init
             */
            for (int i = 0; i < m; i++) {
                queue.offer(new Info(1 + rides[i], 1 + 2 * rides[i], rides[i], i));
            }

            int nIdx = m;
            int mIdx = m;
            while (nIdx < n) {
                Info cur = queue.poll();
                int start = cur.start;
                int end = cur.end;
                int length = cur.length;
                int num = cur.num;
//                System.out.println("cur = " + cur);
                nIdx++;
                if (nIdx == n) {
                    ans = num + 1;
                    break;
                }
                queue.offer(new Info(end, end + length, length, num));
            }
        }else{
            ans = n;
        }

        System.out.println(ans);
    }

    public static class Info{
        int start;
        int end;
        int length;
        int num;

        public Info(int start, int end, int length, int num) {
            this.start = start;
            this.end = end;
            this.length = length;
            this.num = num;
        }

        @Override
        public String toString(){
            return "{start="+start+",end="+end+",length="+length+",num="+num+"}";
        }
    }
}

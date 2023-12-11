package Gold.Greedy;

import java.io.*;
import java.util.*;

public class Prob1911 {

    static int n, l;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());

        PriorityQueue<Info> queue = new PriorityQueue<>(new Comparator<Info>(){
            @Override
            public int compare(Info i1, Info i2){
                if(i1.start == i2.start){
                    return i1.end - i2.end;
                }

                return i1.start - i2.start;
            }
        });
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            queue.offer(new Info(a, b));
        }

        int answer = 0;
        int last = 0;
        while(!queue.isEmpty()){
            Info cur = queue.poll();
            int s = Math.max(last, cur.start);
            int e = cur.end;
            int curL = e - s;
            int cnt = curL / l;
            int rem = curL % l;
            if(rem > 0){
                cnt++;
            }

            last = s + l * cnt;
            answer += cnt;
        }

        System.out.println(answer);
    }

    public static class Info{
        int start;
        int end;

        public Info(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
}
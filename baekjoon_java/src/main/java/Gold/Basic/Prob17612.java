package Gold.Basic;

import java.io.*;
import java.util.*;

public class Prob17612 {

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        PriorityQueue<Checkout> queue = new PriorityQueue<>(new Comparator<>(){
            @Override
            public int compare(Checkout c1, Checkout c2){
                if(c1.time == c2.time){
                    return c1.num - c2.num;
                }

                return c1.time - c2.time;
            }
        });
        for(int i = 0; i < k; i++){
            queue.offer(new Checkout(i, 0, 0));
        }


        List<Checkout> done = new ArrayList<>();
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            Checkout cur = queue.poll();
            queue.offer(new Checkout(cur.num, id, cur.time + w));
            if(cur.id != 0){
                done.add(cur);
            }
        }

        while(!queue.isEmpty()){
            Checkout cur = queue.poll();
            if(cur.id != 0){
                done.add(cur);
            }
        }
        Collections.sort(done, new Comparator<Checkout>(){
            @Override
            public int compare(Checkout c1, Checkout c2){
                if(c1.time == c2.time){
                    return c2.num - c1.num;
                }

                return c1.time - c2.time;
            }
        });

        long answer = 0;
        long cnt = 1;
        for(Checkout fin : done){
            answer += cnt * fin.id;
            ++cnt;
        }

        System.out.println(answer);
    }

    public static class Checkout{
        int num;
        int id;
        int time;

        public Checkout(int num, int id, int time){
            this.num = num;
            this.id = id;
            this.time = time;
        }
    }
}
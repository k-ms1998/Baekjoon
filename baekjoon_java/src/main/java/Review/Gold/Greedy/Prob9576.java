package Review.Gold.Greedy;

import java.io.*;
import java.util.*;

public class Prob9576{

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        for(int tc = 0; tc < t; tc++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            boolean[] used = new boolean[n + 1];
            List<Info> list = new ArrayList<>();
            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                list.add(new Info(a, b));
            }
            Collections.sort(list, new Comparator<Info>(){
               @Override
               public int compare(Info i1, Info i2){
                   if(i1.b == i2.b){
                       return i1.a - i2.a;
                   }

                   return i1.b - i2.b;
               }
            });

            int answer = 0;
            for(Info info : list){
                for(int i = info.a; i <= info.b; i++){
                    if(!used[i]){
                        answer++;
                        used[i] = true;
                        break;
                    }
                }
            }

            System.out.println(answer);
        }

    }

    public static class Info{
        int a;
        int b;

        public Info(int a, int b){
            this.a = a;
            this.b = b;
        }
    }
}
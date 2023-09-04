package Gold.Sort;

import java.io.*;
import java.util.*;

/**
 * Gold 4(전화번호 목록)
 *
 * https://www.acmicpc.net/problem/5052
 *
 * Solution: 정렬
 */
public class Prob5052_sort {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0){
            boolean flag = false;
            int n = Integer.parseInt(br.readLine());
            List<String> numbers = new ArrayList<>();
            for(int i = 0; i < n; i++){
                numbers.add(br.readLine());
            }
            Collections.sort(numbers); // 오름차순으로 정렬
            for(int i = 0; i < n - 1; i++){
                String a = numbers.get(i);
                String b = numbers.get(i + 1);

                if(b.startsWith(a)){
                    flag = true;
                    break;
                }
            }

            if(flag){
                ans.append("NO").append("\n");
            }else{
                ans.append("YES").append("\n");
            }

        }
        System.out.println(ans);
    }
}

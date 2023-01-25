package Review.Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 5(AC)
 *
 * https://www.acmicpc.net/problem/5430
 *
 * Solution: Deque
 * 1. Reverse 작업을 수행할때, 남은 숫자들을 모두 뒤집으면 시간 초과 발생
 *  -> 그러므로, 덱을 사용하고, 숫자를 앞에서 꺼낼지 뒤에서 꺼낼지에 따라서 덱의 앞 또는 뒤에서 숫자 빼냄
 */
public class Prob5430 {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            String command = br.readLine();
            int n = Integer.parseInt(br.readLine());
            String num = br.readLine();
            Deque<Integer> numbers = new ArrayDeque<>();
            int curNum = 0;
            for (int i = 0; i < num.length(); i++) {
                char cur = num.charAt(i);
                if (cur == '[' || cur == ']' || cur == ',') {
                    if (curNum != 0) {
                        numbers.offer(curNum);
                    }
                    curNum = 0;
                    continue;
                }
                curNum = 10 * curNum + Integer.parseInt(String.valueOf(num.charAt(i)));
            }

            boolean reverse = false;
            boolean isError = false;
            for(int i = 0; i < command.length(); i++){
                char c = command.charAt(i);
                if(c == 'R'){
                    reverse = reverse ? false : true;
                }else{
                    if (numbers.isEmpty()) {
                        isError = true;
                        break;
                    }
                    if(reverse){
                        numbers.pollLast();
                    }else{
                        numbers.pollFirst();
                    }

                }
            }
            if(isError){
                ans.append("error");
            }else{
                ans.append("[");
                while(!numbers.isEmpty()){
                    if(reverse){
                        ans.append(numbers.pollLast());
                    }else{
                        ans.append(numbers.pollFirst());
                    }

                    if(numbers.isEmpty()){
                        break;
                    }
                    ans.append(",");
                }
                ans.append("]");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }
}

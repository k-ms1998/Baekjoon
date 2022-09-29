package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(후위 표기식)
 *
 * https://www.acmicpc.net/problem/1918
 *
 * Solution: 후위 표기식 + Stack
 */
public class Prob1918 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /**
         * infix: 중위 표기식 -> a+b
         * postfix: 후위 표기식 -> ab+
         */
        String[] infix = br.readLine().split("");
        int arrSize = infix.length;

        Stack<String> stack = new Stack<>();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < arrSize; i++) {
            String cur = infix[i];
            /**
             * 현재 문자가 "+", "-", "*", "/" 인지, "(", ")" 인지, 알파벳인지에 따라 나눈다
             * 1. "+", "-", "*", "/" 이면 stack 에서 제일 위에 있는 값과 우선순위 비교
             *  -> 현재문자가 stack 의 가장 위에 있는 값보다 우선순위가 낮으면 stack 을 pop 하고, pop한 값을 출력
             *  
             * 2. "(", ")" 일때:
             *  -> "(" 이면 stack 에 push 하기
             *  -> ")" 이면 stack 에서 "(" 가 나올때까지 모든 값들을 pop 해서 출력하기
             *  
             * 3. 알파벳이면 그대로 출력
             */
            if (cur.equals("+") || cur.equals("-") || cur.equals("*") || cur.equals("/")) {
                if (stack.isEmpty()) {
                    stack.push(cur);
                    continue;
                }

                int curRank = getRank(cur);
                while (!stack.isEmpty()) {
                    String inStack = stack.peek();
                    int inStackRank = getRank(inStack);
                    if (inStackRank >= curRank) {
                        ans.append(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(cur);

            } else if (cur.equals("(") || cur.equals(")")) {
                if (cur.equals("(")) {
                    stack.push(cur);
                    continue;
                } else if (cur.equals(")")) {
                    while (!stack.isEmpty()) {
                        String popped = stack.pop();
                        if (!(popped.equals("(") || popped.equals(")"))) {
                            ans.append(popped);
                        } else if(popped.equals("(")) {
                            break;
                        }
                    }
                }
            } else {
                ans.append(cur);
            }
        }
        while (!stack.isEmpty()) {
            String popped = stack.pop();
            if(!(popped.equals("(") || popped.equals(")"))){
                ans.append(popped);
            }
        }

        System.out.println(ans);

    }

    public static int getRank(String exp) {
        if (exp.equals("+") || exp.equals("-")) {
            return 1;
        } else if (exp.equals("*") || exp.equals("/")) {
            return 2;
        } else {
            return 0;
        }
    }
}

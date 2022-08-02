package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Silver 3
 */
public class Prob17413 {

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] str = br.readLine().split("");
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < str.length; i++) {
            if (str[i].equals("<")) {
                while (!stack.isEmpty()) {
                    ans.append(stack.pop());
                }
                while (!str[i].equals(">")) {
                    ans.append(str[i++]);
                }
                ans.append(str[i]);
            } else if (str[i].equals(" ")) {
                while (!stack.isEmpty()) {
                    ans.append(stack.pop());
                }
                ans.append(str[i]);
            } else {
                stack.push(str[i]);
            }
        }
        while (!stack.isEmpty()) {
            ans.append(stack.pop());
        }

        System.out.println(ans);
    }
}

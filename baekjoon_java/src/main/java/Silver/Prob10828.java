package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Silver 4
 */
public class Prob10828 {

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Scanner 로 입력을 받으면 시간초과 -> 꼭 BufferedReader 로 입력받기

        int n = Integer.parseInt(br.readLine());
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            String commandLine = br.readLine();
            String[] commandSplit = commandLine.split(" ");
            String command = commandSplit[0];

            if (command.equals("push")) {
                stack.push(Integer.valueOf(commandSplit[1]));
            } else if (command.equals("pop")) {
                System.out.println(stack.empty() == false ? stack.pop() : -1);
            } else if (command.equals("size")) {
                System.out.println(stack.size());
            } else if (command.equals("empty")) {
                System.out.println(stack.empty() == true ? 1 : 0);
            } else if (command.equals("top")) {
                System.out.println(stack.empty() == false ? stack.peek() : -1);
            }
        }
    }
}

package Gold.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/5639
 *
 * Solution: Tree + PostOrder
 */
public class Prob5639 {

    static Map<Integer, Node> tree = new HashMap<>();
    static int root = -1;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        tree.put(-1, new Node(-1, -1, -1));
        while (true) {
            String input = br.readLine();
            if (input == null || input.equals("")) {
                break;
            }
            int curNum = Integer.parseInt(input);
            Node curNode = new Node(curNum, -1, -1);
            if (root == -1) {
                root = curNum;
                tree.put(curNum, curNode);
                continue;
            }
            Node parentNode = tree.get(root);
            while(true){
                if (parentNode.num > curNum) {
                    if (parentNode.left == -1) {
                        parentNode.left = curNum;
                        tree.put(curNum, curNode);
                        break;
                    } else {
                        parentNode = tree.get(parentNode.left);
                    }
                }else {
                    if (parentNode.right == -1) {
                        parentNode.right = curNum;
                        tree.put(curNum, curNode);
                        break;
                    } else {
                        parentNode = tree.get(parentNode.right);
                    }
                }
            }
        }
//        System.out.println("tree = " + tree);

        postOrder(root);
        System.out.println(ans);
    }

    static public void postOrder(int cur) {
        Node curNode = tree.get(cur);
        if (curNode.num == -1) {
            return;
        }

        postOrder(curNode.left);
        postOrder(curNode.right);
        ans.append(curNode.num + "\n");

    }

    static class Node{
        int num;
        int left;
        int right;

        public Node(int num, int left, int right) {
            this.num = num;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "num=" + num +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}

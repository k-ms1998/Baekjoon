package Silver;

import java.io.*;
import java.util.*;
import java.util.Map.*;

/**
 * Silver 1
 * <p>
 * 이진 트리를 입력받아 전위 순회(preorder traversal), 중위 순회(inorder traversal), 후위 순회(postorder traversal)한 결과를 출력하는 프로그램을 작성하시오.
 * <p>
 * 전위 순회한 결과 : ABDCEFG // (루트) (왼쪽 자식) (오른쪽 자식)
 * 중위 순회한 결과 : DBAECFG // (왼쪽 자식) (루트) (오른쪽 자식)
 * 후위 순회한 결과 : DBEGFCA // (왼쪽 자식) (오른쪽 자식) (루트)
 * 가 된다.
 * <p>
 * 입력
 * 첫째 줄에는 이진 트리의 노드의 개수 N(1 ≤ N ≤ 26)이 주어진다.
 * 둘째 줄부터 N개의 줄에 걸쳐 각 노드와 그의 왼쪽 자식 노드, 오른쪽 자식 노드가 주어진다.
 * 노드의 이름은 A부터 차례대로 알파벳 대문자로 매겨지며, 항상 A가 루트 노드가 된다. 자식 노드가 없는 경우에는 .으로 표현한다.
 * <p>
 * 출력
 * 첫째 줄에 전위 순회, 둘째 줄에 중위 순회, 셋째 줄에 후위 순회한 결과를 출력한다. 각 줄에 N개의 알파벳을 공백 없이 출력하면 된다.
 */
/*

7
A B C
B D .
C E F
E . .
F . G
D . .
G . .

Result:
전위 순회한 결과 : ABDCEFG // (루트) (왼쪽 자식) (오른쪽 자식)
중위 순회한 결과 : DBAECFG // (왼쪽 자식) (루트) (오른쪽 자식)
후위 순회한 결과 : DBEGFCA // (왼쪽 자식) (오른쪽 자식) (루트)
 */
public class Prob1991 {

    static public Map<String, Entry<String, String>> tree = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.valueOf(br.readLine());
        for (int i = 0; i < n; i++) {
            String nodes = br.readLine();
            String[] nodesSplit = nodes.split(" ");

            String curNode = nodesSplit[0];
            String leftChild = nodesSplit[1];
            String rightChild = nodesSplit[2];

            Entry<String, String> children = Map.entry(leftChild, rightChild);
            tree.put(curNode, children);
        }

        StringBuilder preOrderSb = new StringBuilder();
        preOrder("A", preOrderSb);
        System.out.println(preOrderSb);

        StringBuilder inOrderSb = new StringBuilder();
        inOrder("A", inOrderSb);
        System.out.println(inOrderSb);

        StringBuilder postOrderSb = new StringBuilder();
        postOrder("A", postOrderSb);
        System.out.println(postOrderSb);
    }

    private static void preOrder(String curNode, StringBuilder preOrderSb) {
        if (curNode.equals(".")) {
            return;
        }
        Entry<String, String> childNodes = tree.get(curNode);

        String left = childNodes.getKey();
        String right = childNodes.getValue();

        preOrderSb.append(curNode);
        preOrder(left, preOrderSb);
        preOrder(right, preOrderSb);
    }

    private static void inOrder(String curNode, StringBuilder inOrderSb) {
        if (curNode.equals(".")) {
            return;
        }
        Entry<String, String> childNodes = tree.get(curNode);

        String left = childNodes.getKey();
        String right = childNodes.getValue();

        inOrder(left, inOrderSb);
        inOrderSb.append(curNode);
        inOrder(right, inOrderSb);
    }

    private static void postOrder(String curNode, StringBuilder postOrderSb) {
        if (curNode.equals(".")) {
            return;
        }
        Entry<String, String> childNodes = tree.get(curNode);

        String left = childNodes.getKey();
        String right = childNodes.getValue();

        postOrder(left, postOrderSb);
        postOrder(right, postOrderSb);
        postOrderSb.append(curNode);
    }
}

package Gold.Tree;

import java.io.*;
import java.util.*;
import java.util.Map.*;

/**
 * Gold 2
 *
 * 문제
 * 이진트리를 다음의 규칙에 따라 행과 열에 번호가 붙어있는 격자 모양의 틀 속에 그리려고 한다. 이때 다음의 규칙에 따라 그리려고 한다.
 *  1. 이진트리에서 같은 레벨(level)에 있는 노드는 같은 행에 위치한다.
 *  2. 한 열에는 한 노드만 존재한다.
 *  3. 임의의 노드의 왼쪽 부트리(left subtree)에 있는 노드들은 해당 노드보다 왼쪽의 열에 위치하고, 오른쪽 부트리(right subtree)에 있는 노드들은 해당 노드보다 오른쪽의 열에 위치한다.
 *  4. 노드가 배치된 가장 왼쪽 열과 오른쪽 열 사이엔 아무 노드도 없이 비어있는 열은 없다.
 * 이와 같은 규칙에 따라 이진트리를 그릴 때 각 레벨의 너비는 그 레벨에 할당된 노드 중 가장 오른쪽에 위치한 노드의 열 번호에서 가장 왼쪽에 위치한 노드의 열 번호를 뺀 값 더하기 1로 정의한다.
 * 트리의 레벨은 가장 위쪽에 있는 루트 노드가 1이고 아래로 1씩 증가한다.
 *
 * 우리는 주어진 이진트리를 위의 규칙에 따라 그릴 때에 너비가 가장 넓은 레벨과 그 레벨의 너비를 계산하려고 한다.
 * 위의 그림의 예에서 너비가 가장 넓은 레벨은 3번째와 4번째로 그 너비는 18이다. 너비가 가장 넓은 레벨이 두 개 이상 있을 때는 번호가 작은 레벨을 답으로 한다.
 * 그러므로 이 예에 대한 답은 레벨은 3이고, 너비는 18이다.
 * 임의의 이진트리가 입력으로 주어질 때 너비가 가장 넓은 레벨과 그 레벨의 너비를 출력하는 프로그램을 작성하시오
 *
 * 입력
 * 첫째 줄에 노드의 개수를 나타내는 정수 N(1 ≤ N ≤ 10,000)이 주어진다.
 * 다음 N개의 줄에는 각 줄마다 노드 번호와 해당 노드의 왼쪽 자식 노드와 오른쪽 자식 노드의 번호가 순서대로 주어진다.
 * 노드들의 번호는 1부터 N까지이며, 자식이 없는 경우에는 자식 노드의 번호에 -1이 주어진다.
 *
 * 출력
 * 첫째 줄에 너비가 가장 넓은 레벨과 그 레벨의 너비를 순서대로 출력한다. 너비가 가장 넓은 레벨이 두 개 이상 있을 때에는 번호가 작은 레벨을 출력한다.
 *
 *
 * Solution:
 * 트리 중위 순회의 특징을 활용
 * 트리를 중위 순회로 탐색하면, x축으로 봤을때 가장 왼쪽에 있는 노드에서 시작해서 오른쪽으로 순차적으로 방문한다
 * 해당 예제에서는 8,4,2,14,9,18,15,5,10,1,16,11,6,12,3,17,19,13,7 순서대로 방문하게 된다
 * 그러므로, 각 노드의 인덱스를 쉽게 구할 수 있음
 * 인덱스를 구하는 동시에, 해당 노드의 높이도 같이 저장함
 * 모든 노드의 인덱스와 높이를 구하고 나서, 각 높이에서 최대 너비를 구하고, 최종적으로 너비가 가장 큰 값을 출력
 *
 */
public class Prob2250 {

    static public Map<Integer, Entry<Integer, Integer>> tree = new HashMap<>();
    static public Queue<Integer> inOrderNodes = new ArrayDeque<>();
    static public Map<Integer, Integer> height = new HashMap<>();
    static public Map<Integer, Integer> parent = new HashMap<>();
    static public Map<Integer, List<Integer>> heightList = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.valueOf(br.readLine());
        for (int i = 0; i < n; i++) {
            String nodes = br.readLine();
            String[] nodesSplit = nodes.split(" ");

            int curNode = Integer.valueOf(nodesSplit[0]);
            int leftChild = Integer.valueOf(nodesSplit[1]);
            int rightChild = Integer.valueOf(nodesSplit[2]);

            Entry<Integer, Integer> children = Map.entry(leftChild, rightChild);

            if(leftChild != -1){
                parent.put(leftChild, curNode);
            }
            if(rightChild != -1){
                parent.put(rightChild, curNode);
            }
            tree.put(curNode, children);
        }

        /**
         * 루트 노드부터 탐색을 시작해야 제대로 인덱스 값을 구할 수 있음
         */
        int root = -1;
        for (int i = 1; i <= n; i++) {
            if (!parent.containsKey(i)) {
                root = i;
                break;
            }
        }
        /**
         * 루트 노드부터 중위 순회 시작
         */
        inOrder(root, 1);

        int idx = 1;
        Map<Integer, Integer> nodesIdx = new HashMap<>();
        while (!inOrderNodes.isEmpty()) {
            int currentNode = inOrderNodes.poll();
            nodesIdx.put(currentNode, idx++);
        }

        int ansH = 1;
        int ansDiff = 0;
        Iterator<Entry<Integer, List<Integer>>> iter = heightList.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Integer, List<Integer>> entry = iter.next();
            List<Integer> nodes = entry.getValue();

            int minIdx = 100000000;
            int maxIdx = 0;
            for (Integer node : nodes) {
                int currentIdx = nodesIdx.get(node);
                if (minIdx > currentIdx) {
                    minIdx = currentIdx;
                }
                if (maxIdx < currentIdx) {
                    maxIdx = currentIdx;
                }
            }

            int currentDiff = maxIdx - minIdx + 1;
            if (currentDiff > ansDiff) {
                ansDiff = currentDiff;
                ansH = entry.getKey();
            }
        }

        System.out.println(ansH + " " + ansDiff);
    }

    private static void inOrder(int curNode, int h) {
        if (curNode == -1) {
            return;
        }

        Entry<Integer, Integer> childNodes = tree.get(curNode);
        int leftNode = childNodes.getKey();
        int rightNode = childNodes.getValue();

        inOrder(leftNode, h+1);

        inOrderNodes.add(curNode);
        height.put(curNode, h);
        List<Integer> currentHeightNodes = new ArrayList<>();
        if (heightList.containsKey(h)) {
            currentHeightNodes = heightList.get(h);
        }
        currentHeightNodes.add(curNode);
        heightList.put(h, currentHeightNodes);

        inOrder(rightNode,h+1);

    }

}

package Gold.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 2(트리의 순회)
 *
 * https://www.acmicpc.net/problem/2263
 *
 * Solution: Tree Traversal
 *
 * 인오더: 중위순회 (left -> root -> right)
 * 포스트오더: 후위순회 (left -> right -> root)
 * 프리오더: 전위순회 (root -> left -> right)
 *
 */
public class Prob2263 {

    static int n;
    static int[] inOrder;
    static int[] postOrder;

    /**
     * postOrder 의 값이 inOrder 에서 몇번째 인덱스에 저장되있는지 저장
     * ex:
     * inOrder: 4 5 6
     * postOrder: 4 6 5
     * index: [1, 3, 2]     (인덱스를 1부터 시작)
     * 
     */
    static int[] index;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        inOrder = new int[n + 1];
        postOrder = new int[n + 1];

        index = new int[n + 1];

        /**
         * 인오더: 중위순회 (left -> root -> right)
         * 포스트오더: 후위순회 (left -> right -> root)
         * 프리오더: 전위순회 (root -> left -> right)
         */
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            int num = Integer.parseInt(st.nextToken());
            inOrder[i] = num;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            int num = Integer.parseInt(st.nextToken());
            postOrder[i] = num;

            for(int j = 1; j < n + 1; j++){
                if (num == inOrder[j]) {
                    index[i] = j;
                    break;
                }
            }
        }
        createPreOrder(1, n, 1, n);

        System.out.println(ans);
    }

    public static void createPreOrder(int postS, int postE, int inS, int inE) {
        if (postS > postE || inS > inE) {
            return;
        }

        /**
         * 0. 현재 서브트리의 루트노드는 postOrder 의 마지막 인덱스의 값임
         * 1. 해당 루트노드가 inOrder 에서 몇번째 인덱스에 있는지 확인(index[] 에서 가져오면 됨)
         * 2. 해당 인덱스 기준, 왼쪽의 값들은 현재 루트노드의 왼쪽 서브트리임. 오른쪽의 값들은 현재 루트노드의 오른쪽 서브트리임
         * 3. 각 왼쪽, 오른쪽 서브트리에 대해서 0~3 반복
         */
        int curRoot = postOrder[postE];
        ans.append(curRoot).append(" ");
        int curIdx = index[postE];

        int leftSubTreeSize = curIdx - inS;
        int rightSubTreeSize = inE - curIdx;

        int rightSubTreePostEnd = postE - 1;
        int leftSubTreePostEnd = rightSubTreePostEnd - rightSubTreeSize;

        if(leftSubTreeSize > 0){
            createPreOrder(leftSubTreePostEnd - leftSubTreeSize + 1, leftSubTreePostEnd, inS, curIdx - 1);
        }
        if(rightSubTreeSize > 0){
            createPreOrder(rightSubTreePostEnd - rightSubTreeSize + 1, rightSubTreePostEnd, curIdx + 1, inE);
        }
    }
}
/*
4
4 3 2 1
4 3 2 1
-> 1 2 3 4

4
1 2 3 4
4 3 2 1
-> 1 2 3 4
 */

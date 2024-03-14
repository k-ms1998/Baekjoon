package Gold.Tree;

import java.io.*;
import java.util.*;

public class Prob4256 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static Node[] tree;
    static int[] pre;
    static int[] preIdx;
    static int[] in;
    static int[] inIdx;

    static int root;
    static StringBuilder curSb;

    public static void main(String args[]) throws IOException{
        int T = Integer.parseInt(br.readLine());

        for(int testCase = 1; testCase <= T; testCase++){
            size = Integer.parseInt(br.readLine());
            curSb = new StringBuilder();

            tree = new Node[size + 1];
            for(int idx = 0; idx < size + 1; idx++){
                tree[idx] = new Node(idx, 0, 0);
            }

            pre = new int[size];
            preIdx = new int[size + 1];
            in = new int[size];
            inIdx = new int[size + 1];

            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < size; idx++){
                pre[idx] = Integer.parseInt(st.nextToken());
                preIdx[pre[idx]] = idx;
            }

            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < size; idx++){
                in[idx] = Integer.parseInt(st.nextToken());
                inIdx[in[idx]] = idx;
            }

            root = pre[0];
            int rootIdx = inIdx[root];
            createTree(0, size, rootIdx);
            createPostOrder(root);

            sb.append(curSb).append("\n");
        }

        System.out.println(sb);
    }

    public static int createTree(int left, int right, int mid){
        // System.out.printf("left=%d, right=%d, mid=%d, tIdx=%d\n", left, right, mid, tIdx);
        if(left > right || mid < 0 || mid >= size){
            return 0;
        }
        if(left == right){
            System.out.println("FLAG");

            return 0;
        }
        int curNum = in[mid];


        int leftNode = 0;
        int nextLeft = size + 1;
        for(int idx = left; idx < mid; idx++){
            int num = in[idx];
            nextLeft = Math.min(nextLeft, preIdx[num]);
        }
        if(nextLeft < size){
            int leftNum = pre[nextLeft];
            int leftMidIdx = inIdx[leftNum];
            leftNode = createTree(left, mid, leftMidIdx);
        }

        int rightNode = 0;
        int nextRight = size + 1;
        for(int idx = mid + 1; idx < right; idx++){
            int num = in[idx];
            nextRight = Math.min(nextRight, preIdx[num]);
        }
        if(nextRight < size){
            int rightNum = pre[nextRight];
            int rightMidIdx = inIdx[rightNum];
            rightNode = createTree(mid + 1, right, rightMidIdx);
        }


        tree[curNum].left = leftNode;
        tree[curNum].right = rightNode;

        return curNum;
    }

    public static void createPostOrder(int num){
        Node node = tree[num];
        if(node.left != 0){
            createPostOrder(node.left);
        }
        if(node.right != 0){
            createPostOrder(node.right);
        }
        curSb.append(num).append(" ");
    }

    public static class Node{
        int num;
        int left;
        int right;

        public Node(int num, int left, int right){
            this.num = num;
            this.left = left;
            this.right = right;
        }
    }

}
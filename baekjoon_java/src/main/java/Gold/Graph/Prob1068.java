package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/1068
 *
 * Solution: Tree + DFS
 */
public class Prob1068 {
    static int n;
    static Node[] tree;
    static int remove;
    static int root;
    static int leafNodeCnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /**
         * Node 는 현재 노드의 노드 번호, 부모, 자식들을 가지고 있음
         * tree 의 각 인덱스는 노드 번호랑 일치 -> idx == 1 => Node 1 저장
         */
        n = Integer.parseInt(br.readLine());
        tree = new Node[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new Node(i, -1, new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            /**
             * 입력 받은 parent 를 기준으로 노드들의 정보 업데이트
             * i 가 부모 parent 를 가지고 있으면, 부모인 parent 는 i 를 자식으로 가지고 있음
             */
            int parent = Integer.parseInt(st.nextToken());
            tree[i].parent = parent;
            if (parent == -1) {
                /**
                 * !! 중요 !!
                 * 인덱스 0인 노드가 무조건 루트 노드 X
                 * => 부모가 -1 인 노드가 루트 노드
                 */
                root = i;
                continue;
            }
            tree[parent].child.add(i);
        }

        remove = Integer.parseInt(br.readLine());

        /**
         * 노드 제거 && 리프 노드 계산
         */
        if(remove != root){
            removeNode();
            countLeafNodeDfs(root);
        }

        System.out.println(leafNodeCnt);
    }

    public static void removeNode() {
        /**
         * 지울 노드의 부모의 child 에서 지울 노드 삭제
         */
        List<Integer> parentChildren = tree[tree[remove].parent].child;
        List<Integer> updatedChildren = new ArrayList<>();
        for (Integer parentChild : parentChildren) {
            if (parentChild == remove) {
                continue;
            }
            updatedChildren.add(parentChild);
        }

        tree[tree[remove].parent].child = updatedChildren;
    }

    public static void countLeafNodeDfs(int cur) {
        List<Integer> children = tree[cur].child;
        if (children.size() == 0) {
            leafNodeCnt++;
            return;
        }

        for (Integer child : children) {
            countLeafNodeDfs(child);
        }
    }

    static class Node{
        int num;
        int parent;
        List<Integer> child;

        public Node(int num, int parent, List<Integer> child) {
            this.num = num;
            this.parent = parent;
            this.child = child;
        }
    }
}
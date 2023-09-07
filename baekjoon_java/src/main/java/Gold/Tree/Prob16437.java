package Gold.Tree;

import java.io.*;
import java.util.*;

/**
 * Gold 3(양 구출 작전)
 *
 * https://www.acmicpc.net/problem/16437
 *
 * Solution: 트리 순회
 * 0. dp[i]의 값은 i번째 섬의 양의 수(이때, 늑대이면 음수로 저장)
 * 1. 루트에서 시작해서 리프노드까지 가기
 * 2. 각 리프노드에서 다시 위로 올라오면서 각 노드 방문
 *  -> 이때, 리프노드의 value 값이 음수이면 늑대이므로 무시
 * 3. 현재 노드의 dp 값이 양수이면 늑대보다 양이 더 많은 것이기 떄문에 현재 노드의 부모 노드로
 * 4. 1번 섬에 도달할때까지 부모 노드로 이동
 */
public class Prob16437 {

    static int n;
    static Node[] island;
    static long[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        island = new Node[n + 1];
        dp = new long[n + 1];
        for(int i = 1; i < n + 1; i++){
            island[i] = new Node(i, i, 0, new ArrayList<>());

        }
        for(int i = 2; i < n + 1; i++){
            st = new StringTokenizer(br.readLine());
            String t = st.nextToken();
            int a = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            if(t.equals("W")){
                island[i].value = -a;
            }else{
                island[i].value = a;
            }

            island[i].parent = p;
            island[p].children.add(i);
            dp[i] = island[i].value;
        }

        move(1);

        System.out.println(dp[1]);
    }

    public static void move(int num){
        for (int child : island[num].children) {
            move(child);
        }

        int parent = island[num].parent;
        if(num != 1 && dp[num] > 0){
            dp[parent] += dp[num];
        }
    }


    public static class Node{
        int num;
        int parent;
        int value;
        List<Integer> children;

        public Node(int num, int parent, int value, List<Integer> children){
            this.num = num;
            this.parent = parent;
            this.value = value;
            this.children = children;
        }
    }
}
/*
4
S 100 3
W 50 1
S 10 1

7
S 100 1
S 100 1
W 100 1
S 1000 2
W 1000 2
S 900 6
 */
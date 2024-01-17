package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * 1. 시작 칸에 말 4개
 *  1-1. 말은 게임판에 그려진 화살표의 방향대로만 이동 가능
 *      1-1-1. 파란색 칸에서 이동을 시작하면 파란색 화살표를 타야함
 *      1-1-2. 이동하는 도중이거나 파란색이 아닌 칸에서 이동을 시작하면 빨간색 화살표를 타야한다
 *  1-2. 말이 도착칸으로 이동하면 주사위의 수와 상관 없이 이동을 멈춘다
 *
 * 2. 게임은 10개의 턴으로 이루어짐
 *  2-1. 매턴마다 1~5가 적힌 주사위를 굴림 -> 도착 칸에 있지 않은 말을 하나 골라 해당 수만큼 움직인다
 *  2-2. 말이 이동을 마치는 칸에 다른 말이 있으면 해당 말은 고를 수 없음(이동을 마치는 칸이 도착칸이 아닐때)
 *  2-3. 말이 이동을 마칠 때마다 칸에 적혀있는 수가 점수에 추가된다
 *
 * 3. 주사위를 10번 굴렸을때 나오는 값을 미리 알고 있을때 얻을 수 있는 점수의 최댓값을 출력
 */
public class Prob17825 {

    static Node[] nodes = new Node[42];
    static int[] dice = new int[10];
    static int[] pawn = new int[4]; // 말의 위치 저장

    static int answer = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        initBoard();

        st = new StringTokenizer(br.readLine());
        for(int idx = 0; idx < 10; idx++){
            dice[idx] = Integer.parseInt(st.nextToken());
        }

        // cycleNodes(0);
        // cycleNodes(nodes[5].blue);
        // cycleNodes(nodes[10].blue);
        // cycleNodes(nodes[15].blue);

        findAnswer(0, 0);

        System.out.println(answer);
    }

    // Debug: 윷놀이 판이 제대로 만들어졌는지 하나의 노드에서 도착 노드까지 순환하기
    public static void cycleNodes(int node){
        System.out.println("node:" + nodes[node] + ", points=" + nodes[node].points);
        if(nodes[node].blue == -1 && nodes[node].red == -1){
            System.out.println("----------");
            return;
        }

        cycleNodes(nodes[node].red);
    }

    // 모든 경우의 수 확인하기
    public static void findAnswer(int depth, int score){
        if(depth >= 10){
            answer = Math.max(answer, score);
            return;
        }

        for(int i = 0; i < 4; i++){
            int tmp = checkDst(pawn[i], 0, dice[depth]);
            if(tmp != -1){
                int cur = pawn[i];
                pawn[i] = tmp;
                findAnswer(depth + 1, score + nodes[tmp].points);
                pawn[i] = cur;
            }
        }

    }

    /*
     * 현재 노드를 주사위 숫자만큼 움직였을때 해당 칸에 다른 말이 있는지 없는지 확인하기
     * 다른 말이 있으면 -1 반환; 다른 말이 없으면 해당 노드의 id값 반환
     */
    public static int checkDst(int node, int depth, int target){
        if(depth >= target || (nodes[node].blue == -1 && nodes[node].red == -1)){
            if(nodes[node].blue == -1 && nodes[node].red == -1){
                return node;
            }else{
                for(int i = 0; i < 4; i++){
                    if(pawn[i] == node){
                        return -1;
                    }
                }

                return node;
            }
        }

        if(depth == 0 && nodes[node].blue != -1){
            return checkDst(nodes[node].blue, depth + 1, target);
        }else{
            return checkDst(nodes[node].red, depth + 1, target);
        }
    }

    // 윷놀이 판 만들기
    public static void initBoard(){
        // 총 칸의 수 = 33 (시작칸, 도착칸 포함)
        nodes[0] = new Node(0, 0, -1, 1); // 시작칸
        nodes[1] = new Node(1, 2, -1, 2);
        nodes[2] = new Node(2, 4, -1, 3);
        nodes[3] = new Node(3, 6, -1, 4);
        nodes[4] = new Node(4, 8, -1, 5);
        nodes[5] = new Node(5, 10, 21, 6); //blue
        nodes[6] = new Node(6, 12, -1, 7);
        nodes[7] = new Node(7, 14, -1, 8);
        nodes[8] = new Node(8, 16, -1, 9);
        nodes[9] = new Node(9, 18, -1, 10);
        nodes[10] = new Node(10, 20, 27, 11); // blue
        nodes[11] = new Node(11, 22, -1, 12);
        nodes[12] = new Node(12, 24, -1, 13);
        nodes[13] = new Node(13, 26, -1, 14);
        nodes[14] = new Node(14, 28, -1, 15);
        nodes[15] = new Node(15, 30, 29, 16); // blue
        nodes[16] = new Node(16, 32, -1, 17);
        nodes[17] = new Node(17, 34, -1, 18);
        nodes[18] = new Node(18, 36, -1, 19);
        nodes[19] = new Node(19, 38, -1, 20);
        nodes[20] = new Node(20, 40, -1, 32); // 도착칸 바로 직전 칸
        nodes[21] = new Node(21, 13, -1, 22);
        nodes[22] = new Node(22, 16, -1, 23);
        nodes[23] = new Node(23, 19, -1, 24);
        nodes[24] = new Node(24, 25, -1, 25);
        nodes[25] = new Node(25, 30, -1, 26);
        nodes[26] = new Node(26, 35, -1, 20);

        nodes[27] = new Node(27, 22, -1, 28);
        nodes[28] = new Node(28, 24, -1, 24);

        nodes[29] = new Node(29, 28, -1, 30);
        nodes[30] = new Node(30, 27, -1, 31);
        nodes[31] = new Node(31, 26, -1, 24);

        nodes[32] = new Node(32, 0, -1, -1); // 도착칸
    }

    public static class Node{
        int id;
        int points;
        int blue;
        int red;

        public Node(int id, int points, int blue, int red){
            this.id = id;
            this.points = points;
            this.blue = blue;
            this.red = red;
        }

        @Override
        public String toString(){
            return "{id=" + id + ", points=" + points + ", blue=" + blue + ", red=" + red + "}";
        }
    }
}
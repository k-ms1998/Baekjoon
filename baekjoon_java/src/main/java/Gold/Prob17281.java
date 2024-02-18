package Gold;

import java.io.*;
import java.util.*;

/**
 * 1. 두 팀이 공격과 수비를 번갈아 하는 게임
 *  1-1. 9명으로 이루어져 있음
 * 2. 총 N이닝 동안 게임을 진행
 *  2-1. 한 이닝에 3아웃이 발생하면 이닝이 종료
 *  2-2. 한 이닝이 끝나면 공격과 수비를 바꾼다
 *  2-3. 타순은 이닝이 변경되어도 순서를 유지한다
 *      2-3-1. 2이닝에 6번타자가 마지막 타자였으면 3이닝은 7번타자부터 시작
 * 3. 두 팀은 경이가 시작하기 전까지 타자가 타석하는 순서를 정한다
 *  3-1. 경기 중에는 바꿀 수 없다
 *  3-2. 9번 타자까지 갔는데 3아웃이 아니면 다시 1번 타자부터 다시 친다
 * 4. 타자가 공을 쳐서 얻을 수 있는 결과는 안타, 2루타, 3루타, 홀런 그리고 아웃
 *  4-1. 안타(1) = 타자와 모든 주자가 한 루씩 진루
 *  4-2. 2루타(2) = 타자와 모든 주자가 2 루씩 진루
 *  4-3. 3루타(3) = 타자와 모든 주자가 3 루씩 진루
 *  4-4. 홈런(4) = 타자와 모든 주자가 홈까지 진루
 *  4-5. 아웃(0) = 모든 주자는 진루하지 못하고, 공격 팀의 아웃이 하나 증가
 * 5. 타순을 정하기
 *  5-1. 총 1~9번까지의 선수가 있음
 *  5-2. 1번 선수는 무조건 4번 타자로 정해져 있음
 *  5-3. 각 선수가 각 이닝에서 어떤 결과를 얻는지 미리 알고 있을때, 가장 맍은 득점을 하는 타순을 찾고 점수를 출력
 *  5-4. 각 이닝마다 차례대로 선수를 배치하기 -> 8!
 *      5-4-1. 각 경우의 수 마다 게임을 진행 후 점수 구하기
 */
public class Prob17281 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int totalInning;
    static int[][] results;

    static int[] selected = new int[9];
    static boolean[] selectedCheck = new boolean[9];
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        totalInning = Integer.parseInt(br.readLine());

        results = new int[totalInning][9];
        for(int inning = 0; inning < totalInning; inning++){
            st = new StringTokenizer(br.readLine());
            for(int idx = 0; idx < 9; idx++){
                int num = Integer.parseInt(st.nextToken());
                results[inning][idx] = num;
            }
        }

        findPermutation(0);
        System.out.println(answer);
    }

    public static void findPermutation(int selectIdx){
        if(selectIdx == 9){
            playGame();
            return;
        }

        if(selectIdx == 3){
            selected[selectIdx] = 0;
            findPermutation(selectIdx + 1);
        }else{
            for(int idx = 1; idx < 9; idx++){
                if(!selectedCheck[idx]){
                    selectedCheck[idx] = true;
                    selected[selectIdx] = idx;
                    findPermutation(selectIdx + 1);
                    selectedCheck[idx] = false;
                }
            }
        }
    }

    public static void playGame(){
        int score = 0;
        int inning = 0;
        int out = 0;
        int pIdx = 0;
        int[] base = {-1, -1, -1};
        while(inning < totalInning){
            int player = selected[pIdx];
            if(out == 3){
                inning++;
                out = 0;
                base[2] = -1;
                base[1] = -1;
                base[0] = -1;
                continue;
            }
            int curResult = results[inning][player];
            if(curResult == 0){
                out++;
            }else if(curResult == 1){
//                if(selected[0] == 4 && selected[1] == 5 && selected[2] == 6){
//                    System.out.printf("pIdx=%d, score=%d, player=%d, base={%d, %d, %d}\n", pIdx, score, player, base[0], base[1], base[2]);
//                }
                if(base[2] != -1){
                    score++;
                }
                base[2] = base[1];
                base[1] = base[0];
                base[0] = player;
            }else if(curResult == 2){
                if(base[2] != -1){
                    score++;
                }
                if(base[1] != -1){
                    score++;
                }
                base[2] = base[0];
                base[1] = player;
                base[0] = -1;
            }else if(curResult == 3){
                if(base[2] != -1){
                    score++;
                }
                if(base[1] != -1){
                    score++;
                }
                if(base[0] != -1){
                    score++;
                }
                base[2] = player;
                base[1] = -1;
                base[0] = -1;
            }else if(curResult == 4){
                if(base[2] != -1){
                    score++;
                }
                if(base[1] != -1){
                    score++;
                }
                if(base[0] != -1){
                    score++;
                }
                score++;

                base[2] = -1;
                base[1] = -1;
                base[0] = -1;
            }
//            if(selected[0] == 4 && selected[1] == 5 && selected[2] == 6){
//                System.out.printf("score=%d, player=%d, base={%d, %d, %d}\n", score, player, base[0], base[1], base[2]);
//            }
            pIdx = (pIdx + 1) % 9;
        }

//        if(score == 5){
//            System.out.println("score=" + score);
//            for(int idx = 0; idx < 9; idx++){
//                System.out.print(results[1][idx] + " ");
//            }
//            System.out.println("\n-----");
//        }
        answer = Math.max(answer, score);
    }
}

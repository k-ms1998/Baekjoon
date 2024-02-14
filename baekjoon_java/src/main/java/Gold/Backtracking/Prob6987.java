package Gold.Backtracking;

import java.io.*;
import java.util.*;

/**
 * 1. 각 국가별로 5번의 경기를 치른다
 * 2. 결과가 가능한지 불가능한지 판별
 * 3. 다음 과정을 통해 가능한지 판단
 *  3-1. 모든 팀이 정확히 5경기씩 진행 했어야함 -> 아닐 경우 불가능
 *  3-2. 백트래킹으로 모든 경우 확인
 *      3-2-1. 각 팀은 다른 5개의 팀과 정확히 1번씩 경기를 함
 *      3-2-2. 각 경기에서 현재 팀이 승, 무, 패를 했는지 각 경우로 나눠서 모든 경우를 확인
 *      3-2-3. 이때 가장 마지막 팀까지 탐색을 완료하면 가능한 경우
 */
public class Prob6987 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static Result[] results;
    static int[] answer = new int[4];

    public static void main(String[] args) throws IOException{

        for(int idx = 0; idx < 4; idx++) {
            results = new Result[6];

            st = new StringTokenizer(br.readLine());
            for(int team = 0; team < 6; team++) {
                results[team] = new Result(
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
            }

            boolean impossible = false;
            for(int team = 0; team < 6; team++){
                if(results[team].w + results[team].d + results[team].l != 5){
                    impossible = true;
                    break;
                }
            }

            if(!impossible){
                findAnswer(idx, 0, 1, 0);
            }
        }

        sb.append(answer[0]).append(" ")
                .append(answer[1]).append(" ")
                .append(answer[2]).append(" ")
                .append(answer[3]);

        System.out.println(sb);
    }

    public static boolean findAnswer(int idx, int curTeam, int target, int counter) {
        // 마지막 팀까지 탐색을 완료한 경우 -> 가능한 경우
        if(curTeam == 6) {
            answer[idx] = 1;
            return true;
        }
        
        // 현재 팀이 모든 팀들을 상대 했을때 -> 탐색 할 다음 팀을 변경
        if(target == 6){
            return findAnswer(idx, curTeam + 1, curTeam + 2, counter);
        }

        // curTeam 승리
        if(results[curTeam].w > 0 && results[target].l > 0) {
            results[curTeam].w--;
            results[target].l--;
            // 각 팀은 다른 하나의 팀과 정확히 한번만 경기를 함 -> 그러므로 재귀 호출 할때 상대 팀을 변경해줌
            if(findAnswer(idx, curTeam, target + 1, counter + 1)){
                return true;
            }
            results[curTeam].w++;
            results[target].l++;
        }

        // 무승부
        if(results[curTeam].d > 0 && results[target].d > 0) {
            results[curTeam].d--;
            results[target].d--;
            // 각 팀은 다른 하나의 팀과 정확히 한번만 경기를 함 -> 그러므로 재귀 호출 할때 상대 팀을 변경해줌
            if(findAnswer(idx, curTeam, target + 1, counter + 1)){
                return true;
            }
            results[curTeam].d++;
            results[target].d++;
        }

        // curTeam 패배
        if(results[curTeam].l > 0 && results[target].w > 0) {
            results[curTeam].l--;
            results[target].w--;
            // 각 팀은 다른 하나의 팀과 정확히 한번만 경기를 함 -> 그러므로 재귀 호출 할때 상대 팀을 변경해줌
            if(findAnswer(idx, curTeam, target + 1, counter + 1)){
                return true;
            }
            results[curTeam].l++;
            results[target].w++;
        }

        return false;
    }


    public static class Result{
        int w;
        int d;
        int l;

        public Result(int w, int d, int l) {
            this.w = w;
            this.d = d;
            this.l = l;
        }

        @Override
        public String toString() {
            return String.format("{w=%d, d=%d, l=%d}", w, d, l);
        }
    }

}
/*
5 0 0 3 0 2 2 0 3 0 0 5 4 0 1 1 0 4
4 1 0 3 0 2 3 2 0 2 0 3 0 0 5 1 1 3
5 0 0 4 0 1 2 2 1 2 0 3 1 0 4 0 0 5
5 0 0 3 1 1 2 1 2 2 0 3 0 0 5 1 0 4

5 0 0 3 0 2 2 0 3 0 0 5 4 0 1 1 0 4
4 1 0 3 0 2 3 2 0 2 0 3 0 0 5 1 1 3
5 0 0 4 0 1 2 2 1 2 0 3 1 0 4 0 0 5
0 5 0 0 5 0 1 4 0 3 2 0 4 0 1 3 0 2

6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
*/
package Gold.Greedy;

import java.io.*;
import java.util.*;

/**
 * Gold 5(전구와 스위치)
 *
 * https://www.acmicpc.net/problem/2138
 *
 * Solution: Greedy
 */
public class Prob2138 {

    static int n;
    static int[] org; // 현재 상태 그대로
    static int[] orgB; // 현재 상태에서 첫번째 스위치를 눌렀을때
    static int[] target;
    static int INF = 100000000;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        org = new int[n];
        orgB = new int[n];
        target = new int[n];

        String orgS = br.readLine();
        for(int i = 0; i < n; i++){
            org[i] = Integer.parseInt(String.valueOf(orgS.charAt(i)));
            orgB[i] = org[i];
        }
        orgB[0] = (orgB[0] + 1) % 2;
        orgB[1] = (orgB[1] + 1) % 2; // 첫번째 스위치를 누르면 0번째와 1번째 전구의 상태에가 변함

        String targetS = br.readLine();
        for(int i = 0; i < n; i++){
            target[i] = Integer.parseInt(String.valueOf(targetS.charAt(i)));
        }

        int answerA = findAnswer(org,0);
        int answerB = findAnswer(orgB,1); // 첫번째 스위치를 누른 상태이기 때문에 cnt 가 1에서 시작

        int answer = Math.min(answerA, answerB);
        System.out.println(answer == INF ? -1 : answer);
    }

    public static int findAnswer(int[] cur, int cnt) {
        for(int i = 1; i < n; i++){
            /*
            (전구는 0번째부터 시작해서 n-1번째까지 있다고 할때)
            1. 현재 i번째에서 i-1번째 상태를 확인
            2. i-1번째 상태를 확인 했을때 target과 다르면 i번째 스위치를 눌러서 i-1번째의 상태가 target과 같도록 바꿔줌
                -> 이때, 이렇게 해서 i번째의 상태가 변해서 target과 달라지더라도 i + 1번째로 갔을때 다시 i번째(i+1-1) 상태를 보고 i+1번째 스위치를 눌러서 i번째 상태가 target과 같도록 바꿔줄수 있음
                    -> 즉, i-1번째 상태를 바꾸기 위해 i번째 상태가 달라져도, i + 1번째 에서 만회할 수 있다고 생각하면 됨
            3. 마지막 스위치에 도달할때까지 1~2번 반복
            4. 이때, 마지막 전구의 상태가 target과 같은지 확인
                -> 1~2번을 반복하면서 앞에서 부터 target과 상태가 같도록 했기 때문에 0번째부터 n-2 번째는 무조건 target과 상태가 같음
                    -> 그러므로 마지막 전구의 상태만 같은지 확인하면 target과 완전히 일치하는지 알 수 있음
            
             */
            if (cur[i - 1] != target[i - 1]) {
                cnt++;
                cur[i - 1] = (cur[i - 1] + 1) % 2;
                cur[i] = (cur[i] + 1) % 2;
                if(i + 1 < n){
                    cur[i + 1] = (cur[i + 1] + 1) % 2;
                }
            }
        }

        return cur[n - 1] == target[n - 1] ? cnt : INF;
    }
}

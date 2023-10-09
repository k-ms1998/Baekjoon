package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(동전 분배)
 *
 * https://www.acmicpc.net/problem/1943
 * 
 * Solution:조합 + 방문 확인
 * -> visited를 통해 이미 확인한 금액의 합에 대해서는 다시 확인 X -> 시간 초과 문제 해결
 */
public class Prob1943 {

    static boolean[] visited = new boolean[100001];

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder ans = new StringBuilder();

        int t = 3;
        while(t-- > 0){
            int n = Integer.parseInt(br.readLine());
            int sum = 0;
            visited = new boolean[100001];
            List<Integer> coinsMin = new ArrayList<>();


            for(int i = 0; i < n; i++){
                st = new StringTokenizer(br.readLine());
                int amount = Integer.parseInt(st.nextToken());
                int cnt = Integer.parseInt(st.nextToken());

                sum += amount * cnt;
                for(int j = 0; j < cnt; j++){
                    coinsMin.add(amount);
                }
            }

            Collections.sort(coinsMin);

            if(sum % 2 == 1){
                ans.append(0).append("\n");
            }else{
                int target = sum / 2;
                ans.append(findAnswer(0, 0, target, coinsMin) ? 1 : 0).append("\n");
            }
        }

        System.out.println(ans);
    }

    public static boolean findAnswer(int idx, int cur, int target, List<Integer> coins){
        if(visited[cur] && cur > 0){
            return false;
        }

        visited[cur] = true;
        if(cur > target){
            return false;
        }
        if(cur == target){
            return true;
        }

        for(int i = idx; i < coins.size(); i++){
            boolean res = findAnswer(idx + 1, cur + coins.get(i), target, coins);
            if(res){
                return true;
            }
        }

        return false;
    }
}
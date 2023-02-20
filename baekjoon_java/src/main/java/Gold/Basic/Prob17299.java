package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(오등큰수)
 *
 * https://www.acmicpc.net/problem/17299
 *
 * Solution:
 * 1. 입력 받을때, 각 숫자가 몇번 등장하는 저장
 * 2. 입력이 끝난 후, 마지막 원소부터 탐색 시작
 * 3. 각 원소를 탐색할때, 등장하는 횟수가 가장 많은 노드를 업데이트
 * 4. 현재 원소가 등장하는 횟수가 이전에 나온 노드들보다 등장한 횟수보다 많거나 같으면, 현재 원소 기준 오른쪽 탐색할 필요 X
 * 5. 현재 원소가 등장하는 횟수가 이전에 나온 노드들보디 등장한 횟수보다 작으면, 현재 원소 기준 오른쪽 노드들 탐색
 */
public class Prob17299 {

    static int n;
    static int[] num;
    static int[] count;
    static int[] result;
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        num = new int[n + 1];
        count = new int[1000001];
        result = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            int cur = Integer.parseInt(st.nextToken());
            count[cur]++;
            num[i] = cur;
        }

        /*
        maxNode = 등장한 횟수가 가장 많은 노드 저장
         */
        Node maxNode = new Node(num[n], count[num[n]]);
        for(int i = n; i >= 1; i--){
            int curCnt = count[num[i]];
            /*
            등장횟수가 같을때도 maxNode 업데이트 -> 등장횟수가 같더라도 가장 왼쪽에 있는 노드들 maxNode에 저장하고 있음
             */
            if(curCnt >= maxNode.cnt){
                result[i] = -1;
                maxNode = new Node(num[i], curCnt);
            }else{
                /*
                현재 노드부터 등장 횟수가 자신보다 많은 노드가 나올때까지 오른쪽에 있는 노드들 검사
                 */
                for(int j = i + 1; j < n + 1; j++){
                    int rightNum = num[j];
                    if(curCnt < count[rightNum]){
                        result[i] = rightNum;
                        break;
                    }
                }
            }
        }

        for(int i = 1; i < n + 1; i++){
            ans.append(result[i]).append(" ");
        }
        System.out.println(ans);
    }

    public static class Node{
        int value;
        int cnt;

        public Node(int value, int cnt){
            this.value = value;
            this.cnt = cnt;
        }
    }
}
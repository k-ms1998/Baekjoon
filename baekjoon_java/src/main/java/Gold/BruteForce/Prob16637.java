package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * Gold 3(괄호 추가하기)
 *
 * https://www.acmicpc.net/problem/16637
 *
 * Solution: BruteForce
 * 1. 수식을 숫자와 연산자 분리해서 저장(nums, exp) -> 이떄, 문제의 조건에 의해 nums는 항상 n/2+1 개의 원소, exp는 n/2개의 원소를 가지고 있음
 * 2. 각 연산자를 기준으로 좌우에 있는 숫자들을 괄호로 묶을지 말지 정하기 (비트마스킹)
 *  -> ex:  5
 *          8*3+5
 *          => nums = {8, 3, 5}
 *          => exp = {*, +}
 *              => 비트마스크: 00~11
 *                  => 비트마스크: 01 => 8 * (3 + 5)
 *                  => 이때, 두번 연속 1일 수는 없다 -> 비트마스크 11: => (8*(3) + 5) 이런 이상한 수식이 되기 때문에
 *  3. 완료된 수식 계산하기:
 *  ex:
 *  7
 *  8*3+5+2 => nums = {8, 3, 5, 2}, exp = {*, + , +}
 *  bit = 010, i = 1 => 8 * (3 + 5) + 2
 *  i = 0 -> queue = {8, *}, nums = {8, 3, 5, 2}
 *  i = 1 -> queue = {8, *}, nums = {8, 3, 8, 2}
 *  i = 2 -> queue = {8, *, 8, +}, nums - {8, 3, 8, 2}
 *  마지막으로, for 문밖에서 마지막 nums 추가 -> queue = {8, *, 8, +, 2}
 *  => 차례대로 계산시 8*8+2 = 64 + 2 = 66
 */
public class Prob16637 {

    static int n;
    static String original;
    static int[] nums;
    static char[] exp;
    static int answer = -Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        nums = new int[n/2 + 1];
        exp = new char[n/2];
        original = br.readLine();

        int idxN = 0;
        int idxC = 0;
        for(int i = 0; i < n; i++){
            char c = original.charAt(i);
            if(c >= '0' && c <= '9'){
                nums[idxN] = Integer.parseInt(String.valueOf(c));
                idxN++;
            }else{
                exp[idxC] = c;
                idxC++;
            }
        }

        for(int bit = 0; bit < (1 << n/2); bit++){
            if(checkBit(bit)){
                continue;
            }
            int[] tmpNums = new int[n/2 + 1];
            for (int i = 0; i < n / 2 + 1; i++) {
                tmpNums[i] = nums[i];
            }

            Deque<String> queue = new ArrayDeque<>();
            for(int i = 0; i < n/2; i++){
                /**
                 * 7
                 * 8*3+5+2 => nums = {8, 3, 5, 2}, exp = {*, + , +}
                 * bit = 010, i = 1 => 8 * (3 + 5) + 2
                 * i = 0 -> queue = {8, *}, nums = {8, 3, 5, 2}
                 * i = 1 -> queue = {8, *}, nums = {8, 3, 8, 2}
                 * i = 2 -> queue = {8, *, 8, +}, nums - {8, 3, 8, 2}
                 * 마지막으로, for 문밖에서 마지막 nums 추가 -> queue = {8, *, 8, +, 2}
                 * => 차례대로 계산시 8*8+2 = 64 + 2 = 66
                 */
                if((bit & (1 << i)) == (1 << i)){
                    int tmp = 0;
                    if(exp[i] == '+'){
                        tmp = nums[i] + nums[i + 1];
                    }else if(exp[i] == '-'){
                        tmp = nums[i] - nums[i + 1];
                    }else{
                        tmp = nums[i] * nums[i + 1];
                    }
                    tmpNums[i + 1] = tmp;

                }else{
                    queue.offer(String.valueOf(tmpNums[i]));
                    queue.offer(String.valueOf(exp[i]));

                }
            }
            queue.offer(String.valueOf(tmpNums[n / 2]));
            int total = Integer.parseInt(queue.poll());
            while (!queue.isEmpty()) {
                String tmpExp = queue.poll();
                int tmpNum = Integer.parseInt(queue.poll());
                if(tmpExp.equals("+")){
                    total += tmpNum;
                }else if(tmpExp.equals("-")){
                    total -= tmpNum;
                }else{
                    total *= tmpNum;
                }
            }

            answer = Math.max(answer, total);
        }

        System.out.println(answer);
    }

    public static boolean checkBit(int bit){
        for(int i = 1; i <= n/2; i++){
            if((bit & (1 << i - 1)) == (1 << i - 1) && (bit & (1 << i)) ==  (1 << i)){
                return true;
            }
        }

        return false;
    }

}

package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(IQ Test)
 *
 * https://www.acmicpc.net/problem/1111
 *
 * Solution: Math
 * 연립 방정식으로 해결
 * 주어진 숫자가 n1, n2, n3, n4 일때:
 * n2 = a * n1 + b;
 * n3 = a * n2 + b;
 * n4 = a * n3 + b;
 *
 * => (n3 - n2) = (n2 - n1) * a
 *  => (n3 - n2) / (n2 - n1) = a (!! 주의사항: (n2 - n1)이 0일때는 확인해줘야 함 -> (n2 - n1) = 0 이면 n2 == n1 => 그러므로, a = 1, b = 0 !!)
 * => (n2 - a * n1) = b
 *
 * 연립방정식으로 이용해서 a랑 b를 구할 수 있음
 * 이렇게 구한 a랑 b를 이용해서 주어진 수열의 모든 숫자들을 만들 수 있으면 해당 수열의 마지막 수도 구할 수 있음
 * -> 못구하면 "B"
 *
 * 그리고, 마지막 수가 여러개인 경우는 수열이 하나만 주어졌던가, 아니면 수열이 2개인데 두 개의 숫자가 서로 다른 경우
 */
public class Prob1111 {

    static int n;
    static int[] num;
    static int lastNumber = 101;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        num =  new int[n];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            num[i] = Integer.parseInt(st.nextToken());
        }

        String ans = "";
        if(n > 2){
            int n1 = num[0];
            int n2 = num[1];
            int n3 = num[2];


            int d1 = n2 - n1;
            int d2 = n3 - n2;
            if(d1 == 0){
                findAnswer(0, 1, 0);
            }else{
                /**
                 * 1 4 13 40:
                 * 4  = 1*a + b
                 * 13 = 4*a + b
                 * => 13 - 4 = (4 - 1) * a
                 *  => (n3 - n2) = (n2 - n1) * a
                 *      => (n3 - n2) / (n2 - n1) = a
                 *          => (d1 / d2) = a;
                 */

                int a = d2 / d1;
                int b = n2 - n1 * a; // n2 = n1 * a + b => n2 - n1 * a = b
                findAnswer(0, a, b);
            }

            if(lastNumber == 101){
                ans = "B";
            }else{
                ans = String.valueOf(lastNumber);
            }
        }else{
            if(n == 2){
                if (num[0] == num[1]) {
                    ans = String.valueOf(num[0]);
                }else{
                    ans = "A";
                }
            }else{
                ans = "A";
            }
        }

        System.out.println(ans);

    }

    public static void findAnswer(int depth, int a, int b) {
        if(depth >= n - 1){
            int cur = num[depth];
            lastNumber = a*cur + b;
            return;
        }

        int cur = num[depth];
        int next = num[depth + 1];
        if(cur*a + b == next){
            findAnswer(depth + 1, a, b);
        }
    }
}
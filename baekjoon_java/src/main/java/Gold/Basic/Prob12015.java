package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(가장 긴 증가하는 부분 수열 2)
 * 
 * https://www.acmicpc.net/problem/12015
 * 
 * Solution: 이분 탐색
 * 1. 입력받은 수열을 처음부터 탐색하면서 새로운 배열(lis)에 추가
 * 2. lis 의 마지막 원소가 현재 수열의 값보다 작으면, 현재 값을 lis 에 추가
 *  2-1. Else, lis 의 값 중 하나를 현재 값으로 대체한다
 *      2-1-1. 이때, lis 에서 대체될 값은 현재 값보다 큰 값중 가장 작은 값이다
 *          -> ex: lis 가 10 20 30 일때, 현재 값이 15이면 lis 는 10 15 20이 된다
 *              ->이때, 20이 10보다 크고 30보다 작은 어떤 수가 되더라도 여전히 길이가 3인 lis 가 유지가 된다
 *              -> 그러므로, 현재까지 나온 숫자들 중에서 가장 긴 증가하는 길이의 수열도 만족하고, 현재 값 이후로 나오는 숫자들에 대해서도 중가하는 경우도 고려할 수 있게 됨
 *                  -> lis 가 10 20 30에서 10 15 30이 됐고, 현재 값은 25이면, lis 는 10 15 25 가 됨
 *                      -> 이 상태에서 현재 값이 28이면, lis 는 10 15 25 28이 됨
 *                      => !! lis 에 저장된 값들이 가장 긴 증가하는 부분 수열이 아닐 경우가 큼 !! 숫자들은 아니더라도, 길이는 항상 유지되기 때문에 해당 문제를 푸는데 적합
 *      2-1-2. 대체할 값을 찾기 위해 이분탐색을 이용
 */
public class Prob12015 {

    static int n;
    static int[] num;

    static int[] lis;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        num = new int[n];
        lis = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            num[i] = Integer.parseInt(st.nextToken());
        }


        int lisIdx = 0;
        lis[0] = num[0];
        for(int i = 1; i < n; i++){
            if(lis[lisIdx] < num[i]){
                ++lisIdx;
                lis[lisIdx] = num[i];
            }else{
                int replaceIdx = replace(num[i], lisIdx);
                lis[replaceIdx] = num[i];
            }
        }

        System.out.println(lisIdx + 1);
    }

    public static int replace(int replacement, int size){
        int l = 0;
        int r = size;
        while(l < r){
            int mid = (l + r)/2;
            if(lis[mid] < replacement){
                l = mid + 1;
            }else{
                r = mid;
            }
        }

        return r;
    }
}
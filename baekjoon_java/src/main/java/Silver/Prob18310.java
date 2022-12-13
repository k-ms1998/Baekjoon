package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 3(안테나)
 *
 * https://www.acmicpc.net/problem/18310
 * 
 * Solution: 정렬
 * 1. 집들의 위치를 정렬
 * 2. 안테나로 부터의 거리가 최소가 되기 위해서는 중간에 있는 집에 안테나를 설치
 * 3. 집의 갯수가 짝수일때는 중간값이 2개. 이때, 어떤 값을 골라도 최소값은 같기 때문에 중간값 중 작은 값 출력
 */
public class Prob18310 {

    static int n;
    static int[] houses;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        houses = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            houses[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(houses);

        int h1 = (n-1) / 2;
        System.out.println(houses[h1]);
    }
}

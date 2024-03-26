package Review.Gold.DP;

import java.io.*;
import java.util.*;

/**
 * 1. N개의 앱이 활성화 되어 있다
 * 2. 각 앱은 m만큼의 메모리를 사용하고 있다 & 비활성화 후 다시 실행하고자 할때 c만큼의 비용 발생
 * 3. 사용자가 새로운 앱 B를 실행하고자 하여 추가로 M만큼의 메모리가 필요하다
 * 4. 비활성화 했을 경우의 비용 c의 합을 최소화해서 M만큼의 메모리를 확보하기
 * 5. 1 <= N <= 100, 1 <= M <= 10_000_000, 1 <= m <= 10_000_000, 0 <= c <= 100
 */
public class Prob7579 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int appCount, target;
	static int[] appM;
	static int[] appC;
	
	static int[][] dp;
	
	static int maxCost = 0;
	static final int INF = 1_000_000_000;
	
	public static void main(String[] args) throws IOException{
		st = new StringTokenizer(br.readLine());
		appCount = Integer.parseInt(st.nextToken());
		target = Integer.parseInt(st.nextToken());
		
		appM = new int[appCount + 1];
		appC = new int[appCount + 1];
		st = new StringTokenizer(br.readLine());
		for(int app = 1; app <= appCount; app++) {
			appM[app] = Integer.parseInt(st.nextToken());
		}
		
		st = new StringTokenizer(br.readLine());
		for(int app = 1; app <= appCount; app++) {
			appC[app] = Integer.parseInt(st.nextToken());
			maxCost += appC[app];
		}
		
		dp = new int[appCount + 1][10001];
		for(int app = 1; app <= appCount; app++) {
			int curM = appM[app];
			int curC = appC[app];
			for(int cost = 0; cost <= 10000; cost++) {
				if(cost < curC) {
					dp[app][cost] = dp[app - 1][cost];
				}else {
					int prevC = cost - curC;
					dp[app][cost] = Math.max(dp[app - 1][cost], dp[app - 1][prevC] + curM);
				}
			}
		}
		
		int answer = maxCost;
		for(int cost = 0; cost <= 10000; cost++) {
			for(int app = 1; app <= appCount; app++) {
				if(dp[app][cost] >= target) {
					answer = Math.min(answer,  cost);
					break;
				}
			}
			if(answer < maxCost) {
				break;
			}
		}
		
		System.out.println(answer);
	}

}
/*
5 5
1 1 1 1 1
3 0 3 5 4
*/
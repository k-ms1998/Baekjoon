package 백준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 어항을 한 번 정리하는 과정은 다음과 같이 이루어져 있다
 *  1-1. 물고기의 수가 가장 적은 어항에 물고리를 한 마리 넣는다
 *      1-1-1. 만약 이러한 어항이 여러개이면 이러한 모든 어항에 한마리씩 넣기
 *  1-2. 어항을 쌓는다
 *      1-2-1. 가장 왼쪽에 있는 어항을 오른쪽의 어항 위에 놓는다
 *  1-3. 2개 이상 쌓여 있는 어항을 모두 시계 방향으로 90도 회전해서 또 오른쪽으로 쌓는다
 *  1-4. 공중 부양 작업이 모두 끝나면 어항에 있는 물고기의 수를 조절
 *      1-4-1. 모든 인접한 어항에 대해서 물고기 수의 차이를 구한다
 *      1-4-2. d = 이 차이를 5로 나눈 몫
 *      1-4-3. d가 0보다 크면 두 어항 중 물고기의 수가 많은 곳에서 d 마리를 적은 곳으로 보냄
 *      1-4-4. 이 과정은 모두 동시에 이루어짐
 *  1-5. 이제 다시 어항을 바닥에 일려로 놓는다
 *      1-5-1. 가장 왼쪽에 있는 어항들부터 바닥에 일자로 놓는다
 *  1-6. 더 이상 공중부양을 작업을 못할때까지 왼쪽에 N/2 줄의 어항을 공중부양 시킨다
 *  1-7. 더 이상 공중부양을 못하면 1-4랑 동일하게 어항에 있는 물고기의 수를 조절한다
 *  1-8. 1-5와 동일하게 다시 어항을 바닥에 일렬로 놓는다
 * 2. 물고기가 가장 많이 들어있는 어항과 가장 적게 들어있는 어행의 물고기 수 차이가 k이하가 도려면 어항을 몇 번 정리해야 하는지 구한다
 */
public class Prob23291 {
    
    static int n, k;
    static long[] fish;
    static long[][] fish2d;
    
    static final int[] dx = {1, 0, -1, 0};
    static final int[] dy = {0, 1, 0, -1};
    
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        fish = new long[n];
        st = new StringTokenizer(br.readLine());
        for(int idx = 0; idx < n; idx++){
            fish[idx] = Integer.parseInt(st.nextToken());
        }
        
        int answer = 0;
        while(true) {
        	if(checkFish()) {
        		break;
        	}
        	findSmallest();
        	
            fish2d = new long[n][n];
            for(int idx = 0; idx < n; idx++){
                fish2d[n-1][idx] = fish[idx];
            }
            fish2d[n-2][1] = fish2d[n-1][0];
            fish2d[n-1][0] = 0;
            rotate1(1);
            
            moveFish();
            
            fish2dTofish();
            
            fish2d = new long[n][n];
            for(int idx = 0; idx < n; idx++){
                fish2d[n-1][idx] = fish[idx];
            }
            rotate2(n, 1, 0);
            
            moveFish();
            
            fish2dTofish();
            
            answer++;
        }
//        printFish();
        System.out.println(answer);   
    }
    
    public static boolean checkFish() {
    	long maxFish = 0;
    	long minFish = Long.MAX_VALUE;
    	for(int idx = 0; idx < n; idx++) {
    		maxFish = Math.max(maxFish, fish[idx]);
    		minFish = Math.min(minFish, fish[idx]);
    	}
    	
    	return (maxFish - minFish) <= k;
    }
    
    public static void findSmallest(){
        long minFish = Long.MAX_VALUE;
        for(int idx = 0; idx < n; idx++){
            minFish = Math.min(minFish, fish[idx]);
        }
        
        for(int idx = 0; idx < n; idx++){
            if(minFish == fish[idx]){
                fish[idx]++;
            }
        }
    }
    
    public static void rotate1(int start){
//        printFish2d();
        for(int x = 0; x < n; x++){
            if(fish2d[n-1][x] > 0){
                start = x;
                break;
            }
        }
        int h = 0;
        int end = start;
        for(int x = start; x < n; x++){
            int cnt = 0;
            for(int y = n - 1; y >= 0; y--){
                if(fish2d[y][x] > 0){
                    cnt++;
                }else{
                    break;
                }
            }
            if(cnt >= 2){
                h = Math.max(h, cnt);
                end = x;
            }else{
                break;
            }
        }
        int w = end - start + 1;
        if(end + h >= n){
            return;
        }        
        
        long[][] tmpO = new long[h][w];
        long[][] tmpR = new long[w][h];
        int startY = n - h;
        for(int y = startY; y < n; y++){
            for(int x = start; x <= end; x++){
                tmpO[y - startY][x-start] = fish2d[y][x];
                fish2d[y][x] = 0;
            }
        }
        
        for(int y = 0; y < w; y++){
            for(int x = 0; x < h; x++){
                tmpR[y][x] = tmpO[h - 1 - x][y];
            }
        }
        
        int fStartX = end + 1;
        int fStartY = n - w - 1;
        for(int y = 0; y < w; y++){
            for(int x = 0; x < h; x++){
                fish2d[y + fStartY][x + fStartX] = tmpR[y][x];
                    
            }
        }
        rotate1(fStartX);
    }
    
    public static void moveFish(){
        boolean[][] v = new boolean[n][n];
        long[][] diffArr = new long[n][n];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                if(fish2d[y][x] > 0 && !v[y][x]){
                    v[y][x] = true;
                    for(int dir = 0; dir < 4; dir++){
                        int nx = x + dx[dir];
                        int ny = y + dy[dir];
                        
                        if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                            continue;
                        }
                        
                        if(fish2d[ny][nx] > 0 && !v[ny][nx]){
                        	long diff = Math.abs(fish2d[ny][nx] - fish2d[y][x]) / 5;
                            if(fish2d[ny][nx] > fish2d[y][x]){
                                diffArr[ny][nx] -= diff;
                                diffArr[y][x] += diff;
                            }else{
                                diffArr[ny][nx] += diff;
                                diffArr[y][x] -= diff;
                            }
                        }
                    }
                }
            }
        }
        
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                fish2d[y][x] += diffArr[y][x];
            }
        }
//        printFish2d();
    }
    
    public static void fish2dTofish(){
        int idx = 0;
        for(int x = 0; x < n; x++){
            for(int y = n - 1; y >= 0; y--){
                if(fish2d[y][x] > 0){
                    fish[idx] = fish2d[y][x];
                    idx++;
                }else{
                    break;
                }
            }
        }
//        printFish();
    }
    
    public static void rotate2(int l, int h, int depth) {
//    	printFish2d();
    	if(depth >= 2) {
    		return;
    	}
    	int start = 0;
    	for(int x = 0; x < n; x++) {
    		if(fish2d[n-1][x] > 0) {
    			start = x;
    			break;
    		}
    	}
    	int end = l/2 + start;
    	int w = l/2;
    	
    	int startH = n - h;
    	long[][] tmpO = new long[h][w];
    	long[][] tmpR = new long[h][w];
    	for(int x = start; x < end; x++) {
    		for(int y = startH; y < n; y++) {
    			tmpO[y-startH][x - start] = fish2d[y][x];
    			fish2d[y][x] = 0;
    		}
    	}

    	for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                tmpR[y][x] = tmpO[h - 1 - y][w - 1 - x]; // 180도 회전
            }
        }
        
        int fStartX = end;
        int fStartY = n - 2*h;
        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                fish2d[y + fStartY][x + fStartX] = tmpR[y][x];
                    
            }
        }
    	rotate2(l/2, 2 * h, depth + 1);   	
    }
    
    public static void printFish2d(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.printf("%2d ", fish2d[y][x]);
            }
            System.out.println();
        }
        System.out.println("-----------");
    }
    
    public static void printFish(){
        for(int x = 0; x < n; x++){
            System.out.printf("%d ", fish[x]);
        }
        System.out.println("\n-----------");
    }
    
}
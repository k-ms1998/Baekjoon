package Gold.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/14719
 *
 * Solution:
 * 
 * 1. 각 높이 별로 블록이 쌓여 있는 인덱스 값들을 저장 => levels[1] 에는 높이 1에서 블록들이 존재하는 인덱스 값들을 저장하고 있음
 * 2. 각 높이 별로 물이 고일 수 있는 양을 계산
 * ex:
 * 4 4
 * 3 0 1 4
 * =>
 * y = 4: 0 0 0 1
 * y = 3: 1 0 0 1
 * y = 2: 1 0 0 1
 * y = 1: 1 0 1 1 (0 == 빈 공간, 1 == 블록)
 * -> y == 1 일때, 물이 고일 수 있는 곳 = 인덱스 1이랑 3 사이 => 그러므로 물이 고일 수 있는 양 = 1
 *    y == 2 일때, 물이 고일 수 있는 곳 = 인덱스 1이랑 4 사이 => 그러므로 물이 고일 수 있는 양 = 2
 *    y == 3 일때, 물이 고일 수 있는 곳 = 인덱스 1이랑 4 사이 => 그러므로 물이 고일 수 있는 양 = 2
 *    y == 4 일때, 물이 고일 수 있는 곳 X => 그러므로 물이 고일 수 있는 양 = 0
 *    => ans = 1 + 2 + 2 + 0 = 5
 */
public class Prob14719 {

    static int h;
    static int w;
    static List<Integer>[] levels;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        levels = new List[h + 1];
        for (int i = 0; i <= h; i++) {
            levels[i] = new ArrayList<>();
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < w; i++) {
            int curH = Integer.parseInt(st.nextToken());
            for (int y = 1; y <= curH; y++) {
                levels[y].add(i);
            }
        }

        int ans = 0;
        for (int y = 1; y <= h; y++) {
            List<Integer> curLevel = levels[y];
            int curSize = curLevel.size();
            if(curSize >= 2){
                int prevH = curLevel.get(0);
                for (int x = 1; x < curSize; x++) {
                    int nextH = curLevel.get(x);
                    ans += (nextH - prevH - 1);
                    prevH = nextH;
                }
            }

        }

        System.out.println(ans);
    }
}
/*
4 8
2 0 1 2 0 3 0 2
 */

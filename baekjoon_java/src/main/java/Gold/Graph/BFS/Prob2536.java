package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/*
 * 1. nxm 크기의 격자가 있다 (n=높이, m=너비)
 * 	1-2. 가장 왼쪽아래에 있는 점 = (1,1), 가장 오른쪽위에 있는 점 = (m, n)
 * 	1-3. 가장 왼쪽위에 있는 점 = (1,1), 가장 오른쪽아래에 있는 점 = (m, n) 이 되도록 변경
 * 2. 총 k개의 버스가 있다
 * 	2-1. 각 버스는 두개의 좌표를 일직선으로 운행한다
 * 	2-2. (col, row)로 주어진다
 * 	2-3. 버스는 모든 교차점에서 정차한다
 * 3. 출발점에서 도착점까지 버스만을 이용해서 움직일때 필요한 최소의 버스의 수를 구하기
 *  3-1.무조건 한 가지 이상의 방법이 존재 한다
 * -------
 * 1. m,n 크기가 최대 100_000 -> mxn 배열 생성 불가능
 * 2. 각 버스 노선을 하나의 노드로 취급
 * 3. 각 버스 노선마다 서로 연결되어 있는지 확인 -> 연결되어 있으면 두개를 이어주는 간선 생성
 *  3-0. 주의할점 -> 항상 srcCol이 dstCol보다 작고, srcRow가 dstRow보다 작도록 설정 후 비교 -> 안그러면 두 노선의 포함 또는 연결 관계를 제대로 비교하지 못함
 *  3-1. 이때, 하나의 노선이 다른 노선 안에 완전히 포함되어 있으면 무시 -> 무시하지 않으면 메모리 초과 발생
 *  3-2. 다음과 같은 규칙으로 두 개의 노선이 연결되어 있는지 확인
 *      3-2-1. 두 노선이 서로 평행한 경우:
 *          3-2-1-1. 하나의 노선의 시작 지점 또는 도착 지점이 다른 노선 안에 포함되어 있는지 확인
 *      3-2-2. 두 노선이 서로 평행하지 않은 경우:
 *          3-2-2-1. 두 노선이 교착하는 점이 있는지 확인
 */
public class Prob2536 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int width, height;
    static int busCount;

    static Bus[] buses;
    static List<Integer>[] adj;
    static int[] dist;

    static final int INF = 1_000_000_000;

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());

        busCount = Integer.parseInt(br.readLine());
        buses = new Bus[busCount + 1];
        adj = new List[busCount + 1];

        for(int idx = 0; idx < busCount; idx++) {
            st = new StringTokenizer(br.readLine());

            int busNum = Integer.parseInt(st.nextToken());
            int srcCol = Integer.parseInt(st.nextToken());
            int srcRow = Integer.parseInt(st.nextToken());
            int dstCol = Integer.parseInt(st.nextToken());
            int dstRow = Integer.parseInt(st.nextToken());

            buses[busNum] = new Bus(srcCol, srcRow, dstCol, dstRow, srcCol == dstCol ? true : false);
            adj[busNum] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());
        int startCol = Integer.parseInt(st.nextToken());
        int startRow = Integer.parseInt(st.nextToken());
        int endCol = Integer.parseInt(st.nextToken());
        int endRow = Integer.parseInt(st.nextToken());

        for(int busA = 1; busA < busCount + 1; busA++) {
            for(int busB = busA + 1; busB < busCount + 1; busB++) {
                if(isWithin(busA, busB) || isWithin(busB, busA)){
                    continue;
                }
                if(isConnected(busA, busB)) {
                    adj[busA].add(busB);
                    adj[busB].add(busA);
                }
            }
        }

        dist = new int[busCount + 1];
        Arrays.fill(dist, INF);
        List<Integer> startNums = findBus(startCol, startRow);
        List<Integer> endNums = findBus(endCol, endRow);

        boolean[] isEnd = new boolean[busCount + 1];
        for(int endNum : endNums) {
            isEnd[endNum] = true;
        }

        PriorityQueue<Info> queue = new PriorityQueue<>(new Comparator<Info>() {
            @Override
            public int compare(Info i1, Info i2) {
                return i1.d - i2.d;
            }
        });
        for(int idxA = 0; idxA < startNums.size(); idxA++) {
            int startNum = startNums.get(idxA);
            queue.offer(new Info(startNum, 1));
            dist[startNum] = 1;
        }

        int answer = INF;
        while(!queue.isEmpty()) {
            Info info = queue.poll();
            int num = info.num;
            int d = info.d;

            if(isEnd[num]) {
                answer = d;
                break;
            }
            if(dist[num] < d) {
                continue;
            }

            for(int next : adj[num]) {
                if(dist[next] > d + 1) {
                    dist[next] = d + 1;
                    queue.offer(new Info(next, d + 1));
                }
            }
        }

        System.out.println(answer);
    }

    public static List<Integer> findBus(int col, int row) {
        List<Integer> nums = new ArrayList<>();
        for(int idx = 1; idx <= busCount; idx++) {
            int srcCol = Math.min(buses[idx].srcCol, buses[idx].dstCol);
            int srcRow = Math.min(buses[idx].srcRow, buses[idx].dstRow);
            int dstCol = Math.max(buses[idx].srcCol, buses[idx].dstCol);
            int dstRow = Math.max(buses[idx].srcRow, buses[idx].dstRow);
            boolean isVertical = buses[idx].isVertical;

            if(isVertical) {
                if(col == srcCol) {
                    if(srcRow <= row && row <= dstRow) {
                        nums.add(idx);
                    }
                }
            }else {
                if(row == srcRow) {
                    if(srcCol <= col && col <= dstCol) {
                        nums.add(idx);
                    }
                }
            }
        }

        return nums;
    }

    public static boolean isConnected(int busA, int busB){
        int srcColA = Math.min(buses[busA].srcCol, buses[busA].dstCol);
        int srcRowA = Math.min(buses[busA].srcRow, buses[busA].dstRow);
        int dstColA = Math.max(buses[busA].srcCol, buses[busA].dstCol);
        int dstRowA = Math.max(buses[busA].srcRow, buses[busA].dstRow);
        boolean isVerticalA = buses[busA].isVertical;

        int srcColB = Math.min(buses[busB].srcCol, buses[busB].dstCol);
        int srcRowB = Math.min(buses[busB].srcRow, buses[busB].dstRow);
        int dstColB = Math.max(buses[busB].srcCol, buses[busB].dstCol);
        int dstRowB = Math.max(buses[busB].srcRow, buses[busB].dstRow);
        boolean isVerticalB = buses[busB].isVertical;

        boolean isConnected = false;
        if(isVerticalA ^ isVerticalB) { // 둘 중 하나만 수직 -> 즉 서로 평행이 아닐때
            if(isVerticalA) { // A가 수직인 선일때
                // A의 col가 B의 두 col 사이에 있어야함 && B의 row가 A의 두 row 사이에 있어야함
                if(srcColB <= srcColA && srcColA <= dstColB
                        && srcRowA <= srcRowB && srcRowB <= dstRowA) {
                    isConnected = true;
                }
            }else { // B가 수직인 선일때
                // B의 col가 A의 두 col 사이에 있어야함 && A의 row가 B의 두 row 사이에 있어야함
                if(srcColA <= srcColB && srcColB <= dstColA
                        && srcRowB <= srcRowA && srcRowA <= dstRowB) {
                    isConnected = true;
                }
            }
        }else {
            if(!isVerticalA && !isVerticalB) { // 둘 다 수평
                if(srcRowA == srcRowB) {
                    if(srcColA <= srcColB && srcColB <= dstColA
                            || srcColB <= dstColA && dstColA <= dstColB) {
                        isConnected = true;
                    }
                }
            }
            if(isVerticalA && isVerticalB) { // 둘 다 수직
                if(srcColA == srcColB) {
                    if(srcRowA <= srcRowB && srcRowB <= dstRowA
                            || srcRowB <= dstRowA && dstRowA <= dstRowB) {
                        isConnected = true;
                    }
                }
            }
        }

        return isConnected;
    }

    public static boolean isWithin(int outerBus, int innerBus){
        boolean verticalA = buses[outerBus].isVertical;
        boolean verticalB = buses[innerBus].isVertical;
        int srcColA = Math.min(buses[outerBus].srcCol, buses[outerBus].dstCol);
        int srcRowA = Math.min(buses[outerBus].srcRow, buses[outerBus].dstRow);
        int dstColA = Math.max(buses[outerBus].srcCol, buses[outerBus].dstCol);
        int dstRowA = Math.max(buses[outerBus].srcRow, buses[outerBus].dstRow);

        int srcColB = Math.min(buses[innerBus].srcCol, buses[innerBus].dstCol);
        int srcRowB = Math.min(buses[innerBus].srcRow, buses[innerBus].dstRow);
        int dstColB = Math.max(buses[innerBus].srcCol, buses[innerBus].dstCol);
        int dstRowB = Math.max(buses[innerBus].srcRow, buses[innerBus].dstRow);
        if(verticalA ^ verticalB){
            return false;
        }

        if(!verticalA && !verticalB) { // 둘 다 수평
            if(srcRowA == srcRowB) {
                if(srcColA <= srcColB && srcColB <= dstColA
                        && srcColA <= dstColB && dstColB <= dstColA) {
                    return true;
                }
            }
        }
        if(verticalA && verticalB) { // 둘 다 수직
            if(srcColA == srcColB) {
                if(srcRowA <= srcRowB && srcRowB <= dstRowA
                        && srcRowA <= dstRowB && dstRowB <= dstRowA) {
                    return true;
                }
            }
        }

        return false;
    }

    public static class Info{
        int num;
        int d;

        public Info(int num, int d) {
            this.num = num;
            this.d = d;
        }

        @Override
        public String toString(){
            return String.format("[num=%d, d=%d]", num, d);
        }
    }

    public static class Bus{
        int srcCol;
        int srcRow;
        int dstCol;
        int dstRow;
        boolean isVertical;

        public Bus(int srcCol, int srcRow, int dstCol, int dstRow, boolean isVertical) {
            this.srcCol = srcCol;
            this.srcRow = srcRow;
            this.dstCol = dstCol;
            this.dstRow = dstRow;
            this.isVertical = isVertical;
        }

        @Override
        public String toString() {
            return String.format("[(%d,%d) <-> (%d,%d)]", srcCol, srcRow, dstCol, dstRow);
        }
    }
}

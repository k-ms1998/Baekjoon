package Gold;

import java.util.*;
import java.io.*;

public class Prob9328 {
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int h;
    static int w;
    static char[][] grid;

    static Map<String, Integer> keys = new HashMap<>();
    static List<CoOrd> entrance = new ArrayList<>();
    static int cnt = 0;
    static Map<String, List<CoOrd>> toVisit = new HashMap<>();

    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        while(n-- > 0){
            st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            grid = new char[h][w];
            keys = new HashMap<>();
            entrance = new ArrayList<>();
            toVisit = new HashMap<>();
            cnt = 0;
            for(int y = 0; y < h; y++){
                String curRow = br.readLine();
                for(int x = 0; x < w; x++){
                    grid[y][x] = curRow.charAt(x);
                    if (grid[y][x] >= 'A' && grid[y][x] <= 'Z') {
                        keys.put(String.valueOf(grid[y][x]), 0);
                    }
                }
            }
            String keyStr = br.readLine();
            if (!keyStr.equals("0")) {
                String[] keyArr = keyStr.split("");
                for (int i = 0; i < keyArr.length; i++) {
                    keys.put(keyArr[i].toUpperCase(), 1);
                }
            }

            findEntrance();
            for(CoOrd curC : entrance){
                int x = curC.x;
                int y = curC.y;
                findDocuments(x, y);
            }
            ans.append(cnt).append("\n");
//            System.out.println("========================");
        }

        System.out.println(ans);
    }

    public static void findDocuments(int x, int y) {
        Deque<CoOrd> queue = new ArrayDeque<>();
        queue.offer(new CoOrd(x, y));
        grid[y][x] = '*';

        while(!queue.isEmpty()){
            CoOrd cur = queue.poll();
            int cx = cur.x;
            int cy = cur.y;

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx < 0 || nx >= w || ny < 0 || ny >= h) {
                    continue;
                }

                if (grid[ny][nx] == '.') {
                    grid[ny][nx] = '*';
                    queue.offer(new CoOrd(nx, ny));
                } else if (grid[ny][nx] == '$') {
                    ++cnt;
                    grid[ny][nx] = '*';
                    queue.offer(new CoOrd(nx, ny));
                } else if (grid[ny][nx] >= 'a' && grid[ny][nx] <= 'z') {
                    String tmp = String.valueOf(grid[ny][nx]).toUpperCase();
                    keys.put(tmp, 1);
                    grid[ny][nx] = '*';
                    queue.offer(new CoOrd(nx, ny));
                    if (toVisit.containsKey(tmp)) {
                        List<CoOrd> coOrds = toVisit.remove(tmp);
                        for (CoOrd c : coOrds) {
                            grid[c.y][c.x] = '.';
                            queue.offer(new CoOrd(c.x, c.y));
                        }
                    }
                }else if(grid[ny][nx] >= 'A' && grid[ny][nx] <= 'Z'){
                    String tmp = String.valueOf(grid[ny][nx]);
                    if(keys.get(tmp) == 0){
                        if (toVisit.containsKey(tmp)) {
                            List<CoOrd> coOrds = toVisit.get(tmp);
                            coOrds.add(new CoOrd(nx, ny));
                            toVisit.put(tmp, coOrds);
                        } else {
                            List<CoOrd> coOrds = new ArrayList<>();
                            coOrds.add(new CoOrd(nx, ny));
                            toVisit.put(tmp, coOrds);
                        }
                    }else{
//                        System.out.println("grid[ny][nx] = " + grid[ny][nx]);
                        grid[ny][nx] = '.';
                        queue.offer(new CoOrd(nx, ny));

                    }

                }

                Set<String> toVisitKeys = toVisit.keySet();
                toVisitKeys.forEach(k -> {
                    if (keys.get(k) > 0) {
                        List<CoOrd> coOrds = toVisit.get(k);
                        for (CoOrd c : coOrds) {
                            grid[c.y][c.x] = '.';
                            queue.offer(new CoOrd(c.x, c.y));
                        }
                    }
                });
            }
        }
    }

    public static void findEntrance() {
        for(int y = 0; y < h; y++){
            if (grid[y][0] == '.') {
                entrance.add(new CoOrd(0, y));
            }
            if (grid[y][w-1] == '.') {
                entrance.add(new CoOrd(w-1, y));
            }
        }
        for(int x = 0; x < w; x++){
            if (grid[0][x] == '.') {
                entrance.add(new CoOrd(x, 0));
            }
            if (grid[h-1][x] == '.') {
                entrance.add(new CoOrd(x, h-1));
            }
        }
    }

    static class CoOrd{
        int x;
        int y;

        public CoOrd(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "CoOrd=[x="+x+", y="+y+"]";
        }
    }
}
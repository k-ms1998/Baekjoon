package Review.Gold.SegmentTree;

import java.io.*;
import java.util.*;

public class Prob2812 {

    static int n, k, size;
    static Info[] nums;
    static StringBuilder ans = new StringBuilder();
    static Info[] tree = new Info[4*500_001 + 1];

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        size = n - k;

        nums = new Info[n];
        String num = br.readLine();
        for(int i = 0; i < n; i++){
            char c = num.charAt(i);
            nums[i] = new Info(Integer.parseInt(String.valueOf(c)), i);
        }

        createTree(1, 0, n - 1);
        findMax(0, 0, n - size);

        System.out.println(ans);
    }

    public static Info createTree(int idx, int left, int right){
        if(left >= right){
            tree[idx] = nums[left];
            return tree[idx];
        }

        int mid = (left + right) / 2;
        Info a = createTree(2*idx, left, mid);
        Info b = createTree(2*idx + 1, mid + 1, right);

        if(a.num > b.num){
            tree[idx] = a;
        }else if(a.num < b.num){
            tree[idx] = b;
        }else{
            int idxA = a.idx;
            int idxB = b.idx;
            if(idxA < idxB){
                tree[idx] = a;
            }else{
                tree[idx] = b;
            }
        }

        return tree[idx];
    }

    public static Info searchTree(int idx, int left, int right, int targetLeft, int targetRight){
        // System.out.println("left=" + left + ", right=" + right + ", targetLeft=" + targetLeft + ", targetRight=" + targetRight);
        if(left > targetRight || right < targetLeft || left > right){
            return new Info(-1, n + 1);
        }
        if(targetLeft <= left && right <= targetRight){
            return tree[idx];
        }

        int mid = (left + right) / 2;
        Info a = searchTree(2*idx, left, mid, targetLeft, targetRight);
        Info b = searchTree(2*idx + 1, mid + 1, right, targetLeft, targetRight);

        if(a.num > b.num){
            return a;
        }else if(a.num < b.num){
            return b;
        }else{
            int idxA = a.idx;
            int idxB = b.idx;
            if(idxA < idxB){
                return a;
            }else{
                return b;
            }
        }
    }

    public static void findMax(int depth, int left, int right){
        if(depth >= size){
            return;
        }

        Info curMax = searchTree(1, 0, n - 1, left, right);
        int maxNum = curMax.num;
        ans.append(maxNum);

        findMax(depth + 1, curMax.idx + 1, right + 1);
    }

    public static class Info{
        int num;
        int idx;

        public Info(int num, int idx){
            this.num = num;
            this.idx = idx;
        }

        @Override
        public String toString(){
            return "{num=" + num + ", idx=" + idx + "}";
        }
    }
}
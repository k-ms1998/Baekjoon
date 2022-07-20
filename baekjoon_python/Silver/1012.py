#1012번 유기농 배추
#Classic DFS
#배추흰지렁이는 인접한 배추로 옮길 수 있음
#(a,b)에 배추가 있어서 배추흰지렁이를 놓을때, 인접한 (a,b+1)로 지렁이가 움직일 수 있으며, (a,b+1)로 움직인 후 (a,b+1)와 인접한 배추들로도 움직일 수 있다 

import sys
sys.setrecursionlimit(10**6)

def worm(farm, x, y, m, n):
    farm[y][x] = 0
    #print("dfs:", x, y)
    if x-1 >= 0:
       if farm[y][x-1] == 1:
            worm(farm, x-1, y, m, n)
    if y-1 >= 0:
        if farm[y-1][x] == 1:
            worm(farm, x, y-1, m, n)
    if x+1 <= m-1:
        if farm[y][x+1] == 1:
            worm(farm, x+1, y, m, n)
    if y+1 <= n-1:
        if farm[y+1][x] == 1:
            worm(farm, x, y+1, m, n)
    
t = int(input())
for _ in range(t):
    m,n,k = map(int, input().split())
    farm = [[0 for __ in range(m)] for ___ in range(n)]
    for __ in range(k):
        x, y = map(int, input().split())
        farm[y][x] = 1

    ans = 0
    for cy in range(n):
        for cx  in range(m):
            if farm[cy][cx] == 1:
                ans += 1
                worm(farm, cx, cy, m, n)
    #print(farm)
    print(ans)
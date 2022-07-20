import sys
sys.setrecursionlimit(10**6)

def cntIsland(x, y, w, h, myMap):
    myMap[y][x] = 0
    if x-1>=0:
        if myMap[y][x-1] == 1:
             cntIsland(x-1, y, w, h, myMap)
        if y-1 >= 0:
            if myMap[y-1][x-1] == 1:
                cntIsland(x-1, y-1, w, h, myMap)
            
        if y+1 <= h-1:
            if myMap[y+1][x-1] == 1:
                cntIsland(x-1, y+1, w, h, myMap)
                
    if x+1 <= w-1:
        if myMap[y][x+1] == 1:
            cntIsland(x+1, y, w, h, myMap)
        if y-1 >= 0:
            if myMap[y-1][x+1] == 1:
                cntIsland(x+1, y-1, w, h, myMap)
        if y+1 <= h-1:
            if myMap[y+1][x+1] == 1:
                cntIsland(x+1, y+1, w, h, myMap)

    if y-1 >= 0:
        if myMap[y-1][x] == 1:
            cntIsland(x, y-1, w, h, myMap)
    if y+1 <= h-1:
        if myMap[y+1][x] == 1:
            cntIsland(x, y+1, w, h, myMap)
ans = []
while True:
    w, h = map(int, input().split())
    if w == 0 and h == 0:
        break

    myMap = []
    for _ in range(h):
        r = list(map(int, input().split()))
        myMap.append(r)
    cnt = 0
    for y in range(h):
        for x in range(w):
            if myMap[y][x] == 1:
                cnt += 1
                cntIsland(x, y, w, h, myMap)
    ans.append(cnt)

for a in ans:
    print(a)
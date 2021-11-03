import sys

n, m = map(int, sys.stdin.readline().split())
arr = []
for _ in range(n):
    arr.append(list(map(int, sys.stdin.readline().split())))
#DP: (1,1)~(x,y) == dp(x-1,y)+dp(x,y-1)+arr(x,y)-dp(x-1,y-1)
dp = arr
for y in range(n):
    for x in range(n):
        if y == 0 and x > 0:
            dp[y][x] += dp[y][x-1]
            continue
        if x == 0 and y > 0:
            dp[y][x] += dp[y-1][x]
            continue
        if x > 0 and y > 0:
            dp[y][x] += (dp[y-1][x]+dp[y][x-1]-dp[y-1][x-1])

for _ in range(m):
    y2, x2, y1, x1 = map(int, sys.stdin.readline().split())
    x1 -= 1
    y1 -= 1
    x2 -= 1
    y2 -= 1

    dpDst = dp[y1][x1]

    if y2 == 0:
        dpD1 = 0
    else:
        dpD1 = dp[y2-1][x1]
    if x2 == 0:
        dpD2 = 0
    else:
        dpD2 = dp[y1][x2-1]
    if x2 != 0 and y2 != 0:
        dpSrc = dp[y2-1][x2-1]
    else:
        dpSrc = 0

    print(dpDst-dpD1-dpD2+dpSrc)
    # ex:
    # dp => 
    # [1(dpSrc), 3, 6, 10(dpD1)]
    # [3, 8(x2,y2), 15, 24]
    # [6(dpD2), 15, 27, 42(dpDst)(y1,x1)]
    # [10, 24, 42, 64]

    
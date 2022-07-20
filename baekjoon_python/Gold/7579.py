#7579(Gold)

import sys

n, m = map(int, input().split())
a = list(map(int, sys.stdin.readline().split()))
c = list(map(int, sys.stdin.readline().split()))
total_c = sum(c)
dp = [[0 for _ in range(total_c+1)] for __ in range(n+1)]

flag = total_c+1
for y  in range(1,n+1):
    ta = a[y-1]
    tc = c[y-1]
    for x in range(1,total_c+1):
        if tc <= x:
            c1 = dp[y-1][x]
            c2 = dp[y-1][x-tc] + ta
            dp[y][x] = max(c1, c2)
        else:
            dp[y][x] = dp[y-1][x]

        if dp[y][x] >= m:
            if x < flag:
                flag = x

# for ddp in dp:
#     print(ddp)
print(flag)
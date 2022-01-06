def mulMat(matA, matB, prevA, prevB):
    return [matA[0], matB[1]], (matA[0]*matA[1]*matB[1]+prevA+prevB)

n = int(input())

dp_sum = [[0 for _ in range(n)] for __ in range(n)]
dp = [[[] for _ in range(n)] for __ in range(n)]
for y in range(n):
    dp[y][y] = list(map(int, input().split()))

                
for x in range(1,n):
    for y in range(n):
        if x+y == n:
            break
        if x == 1:
            dp[y][x+y], dp_sum[y][x+y] = mulMat(dp[y][x+y-1], dp[y+1][x+y], 0, 0)
        else:
            minMat = []
            minSum = -1
            for t in range(1, x+1):
                if minSum == -1:
                    minMat, minSum = mulMat(dp[y][x+y-t], dp[x+y+1-t][x+y], dp_sum[y][x+y-t], dp_sum[x+y+1-t][x+y])
                else:
                    tmpMat, tmpSum = mulMat(dp[y][x+y-t], dp[x+y+1-t][x+y], dp_sum[y][x+y-t], dp_sum[x+y+1-t][x+y])
                    if minSum > tmpSum:
                        minMat = tmpMat
                        minSum = tmpSum
            dp[y][x+y] = minMat
            dp_sum[y][x+y] = minSum

# for d in dp_sum:
#     print(d)
print(dp_sum[0][n-1])
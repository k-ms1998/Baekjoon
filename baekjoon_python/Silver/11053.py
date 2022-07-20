n = int(input())
arr = list(map(int, input().split()))
dp = [1 for _ in range(n)]

for i in range(n):
    maxDp = 0
    for j in range(0, i):
        if arr[j] < arr[i]:
            if maxDp < dp[j]:
                maxDp = dp[j]
    dp[i] = maxDp+1
#print(arr)
#print(dp)
print(max(dp))
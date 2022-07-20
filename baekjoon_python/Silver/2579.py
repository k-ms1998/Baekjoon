import sys
sys.setrecursionlimit(10**6)

def addSteps(p, n, s, ans, flag, cur):
    #Recursion
    if p >= n-1:
        if p == n-1:      
            return ans.append(cur)
        else:
            return ans.append(-1)

    if flag == 2:
        if p+2 <= n-1:
            addSteps(p+2, n, s, ans, 0, cur)
    elif flag == 1:
        if p+2 <= n-1:
            addSteps(p+2, n, s, ans, 0, cur+s[p+2]) 
        if p+1 <= n-1:
            addSteps(p+1, n, s, ans, 2, cur+s[p+1])  
          
    else:
        if p+2 <= n-1:
            addSteps(p+2, n, s, ans, 0, cur+s[p+2])
        if p+1 <= n-1:
            addSteps(p+1, n, s, ans, 1, cur+s[p+1])


n = int(input())
s = []
dp = [0 for i in range(n)]
for _ in range(n): 
    s.append(int(input()))

# ans = []
# addSteps(-1, n, s, ans, 0, 0)
# print(max(ans))

dp[0] = s[0]
dp[1] = s[1]+s[0]
if s[0]+s[2] > s[1]+s[2]:
    dp[2] = s[0]+s[2]
else:
    dp[2] = s[1]+s[2]
p = 3
for p in range(3,n):
    if dp[p-3]+s[p-1]+s[p] > dp[p-2]+s[p]:
        dp[p] = dp[p-3]+s[p-1]+s[p]
    else:
        dp[p] = dp[p-2]+s[p]
    p += 1

#print(dp)
print(dp[n-1])
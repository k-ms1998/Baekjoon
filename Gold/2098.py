import sys
import copy

maxCost = 1000001
n = int(input())
cost = []
dp = [[0 for _ in range(n)] for __ in range(n)]
for _ in range(n):
    cost.append(list(map(int,sys.stdin.readline().split())))

# print(dp)
ans = 4*maxCost
for s in range(n):
    visited = [s]
    cur = s
    minCost = 0
    tmpCost = copy.deepcopy(cost)
    tmpCost[s][s] = maxCost
    while True:
        # print("v:",visited)
        nextV = tmpCost[cur].index(min(tmpCost[cur]))
        if nextV in visited or tmpCost[cur][nextV] == 0:
            tmpCost[cur][nextV] = maxCost
            continue
        visited.append(nextV)
        minCost += tmpCost[cur][nextV]
        tmpCost[cur][nextV] = maxCost
        cur = nextV
        if len(visited) == n:
            minCost += tmpCost[cur][s]
            break
    # print(visited, minCost)
    if minCost < ans:
        ans = minCost
print(ans)
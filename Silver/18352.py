import sys
import heapq

n, m, k, x = map(int, sys.stdin.readline().split())
q = []
for i in range(n):
    heapq.heappush(q, i+1)

dis = [1e9]*(n)
dis[x-1] = 0

edges = [[] for _ in range(n)]

for _ in range(m):
    s, d = map(int, sys.stdin.readline().split())
    edges[s-1].append(d)
while q:
    u = heapq.heappop(q)
    for e in edges[u-1]:
        if e in q:
            if dis[u-1]+1 < dis[e-1]:
                dis[e-1] = dis[u-1]+1

ans = []
for i in range(len(dis)):
    if dis[i] == k:
        ans.append(i+1)
if len(ans) == 0:
    print(-1)
else:
    #ans.sort()
    for e in ans:
        print(e)


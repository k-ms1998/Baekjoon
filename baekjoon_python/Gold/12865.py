import sys
#DP(Dynamic Programming)
items = []
n, k = map(int, sys.stdin.readline().split())
bag = [[0 for _ in range(k+1)] for __ in range(n+1)]
for _ in range(n):
    items.append(list(map(int, sys.stdin.readline().split())))

for y in range(1,n+1):
    cw, cv = map(int, items[y-1])
    for x in range(1,k+1):
        if cw <= x:
            w1 = bag[y-1][x]
            w2 = cv + bag[y-1][x-cw]
            bag[y][x] = max(w1, w2)
            
        else:
            bag[y][x] = bag[y-1][x]
        
# for b in bag:
#     print(b)
print(bag[n][k])
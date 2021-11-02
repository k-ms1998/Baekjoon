
#Dynamic Programming
n = int(input())
h = []
for _ in range(n):
    c = list(map(int, input().split()))
    h.append(c)

for i in range(1,n):
    h[i][0] += min(h[i-1][1], h[i-1][2])
    h[i][1] += min(h[i-1][0], h[i-1][2])
    h[i][2] += min(h[i-1][0], h[i-1][1])
print(min(h[n-1]))
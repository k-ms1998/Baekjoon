n = int(input())
stu = []
for _ in range(n):
    stu.append(list(map(int, input().split())))

m = 0
pres = 0
for i in range(n):
    cnt = 0
    tmp = []
    for j in range(5):
        for r in range(n):
            if i == r:
                continue
            if stu[i][j] == stu[r][j]:
                if r not in tmp:
                    tmp.append(r)
                    cnt += 1
    if cnt > m:
        m = cnt
        pres = i

print(pres+1)
            
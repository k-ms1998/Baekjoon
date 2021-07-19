def placeGaurds(x, y, m):
    c = [0] * x
    r = [0] * y
    for i in range(x):
        for j in range(y):
            if m[i][j] == 'X':
                c[i] = 1
                r[j] = 1

    c_cnt, r_cnt = (0,0)
    for i in c:
        if i == 0:
            c_cnt += 1
    for i in r:
        if i == 0:
            r_cnt += 1

    
    return max(r_cnt, c_cnt)

x, y = map(int, input().split())
m = []

for i in range(x):
    m.append(input())

print(placeGaurds(x, y, m))

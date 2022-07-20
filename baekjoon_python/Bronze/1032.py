n = int(input())
f = []
for i in range(n):
    f.append(list(input()))

w_len = len(f[0])
for i in range(w_len):
    cmp = f[0][i]
    for j in range(n):
        if cmp == f[j][i]:
            if j+1 == n:
                print(cmp, end='')
                break
        else:
            print('?', end='')
            break
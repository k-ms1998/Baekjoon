n = [x for x in map(int, input().split())]
n.sort()
m = n[2]
while True:
    flag = 0
    for i in range(len(n)):
        if m%n[i] == 0:
            flag += 1
    if flag >= 3:
        print(m)
        break

    m += 1

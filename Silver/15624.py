d = 1000000007
n = int(input())
if n == 0:
    print(0)
else:
    p = 2
    f1 = 0
    f2 = 1
    f3 = f1 + f2
    while p < n:
        f1 = f2
        f2 = f3
        f3 = f1%d+f2%d
        #print(f3)
        p += 1

    print(f3%d)
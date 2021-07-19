def calBinary(a, b):
    ans = []
    idx_end = min(len(a), len(b))
    flag = 0
    for _ in range(max(len(a), len(b))):
        try:
            curA = a.pop()
        except:
            curA = 0

        try:    
            curB = b.pop()
        except:
            curB = 0
        tmpSum = int(curA) + int(curB) + flag

        #print(curA, curB, flag)
        if tmpSum >= 2:
            flag = 1
        else:
            flag = 0
        ans.append(tmpSum%2)

    if flag > 0:
        ans.append(flag)

    return ans

a, b = map(list, input().split())

ans = calBinary(a, b)
flag = 0
#print(ans)
for i in range(len(ans)-1, -1, -1):
    if ans[i] == 0 and flag == 0:
        continue
    flag = 1
    print(ans[i], end="")

if flag == 0:
    print('0')
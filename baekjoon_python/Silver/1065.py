n = int(input())

cnt = 0
for i in range(1, n+1):
    if i < 100:
        cnt += 1
        continue
    
    arr = []
    t = i
    while t > 0:
        arr.append(t%10)
        t = t//10
    
    flag = 0
    for j in range(0, len(arr)-2):
        if arr[j]-arr[j+1] != arr[j+1]-arr[j+2]:
            flag = 1
            break
    if flag == 0:
        cnt += 1

print(cnt)
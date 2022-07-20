def fibo(a,b,n1,n2,cnt):
    #print(b)
    if b > n2:
        return cnt

    if b >= n1 and b <= n2:
        cnt += 1

    return fibo(b,a+b,n1,n2,cnt)


arr = [0 for i in range(1000)]
arr[0] = 1
arr[1] = 2
for i in range(2, len(arr)):
    arr[i] = arr[i-1] + arr[i-2]

while True:
    a, b = map(int, input().split())
    if a==0 and b==0:
        break
    #print(fibo(2,3,a,b,0))
    cnt = 0
    for c in arr:
        if c >= a and c <= b:
            cnt += 1

    print(cnt)
c = int(input())

for _ in range(c):
    arr = list(map(int, input().split()))
    n = arr[0]
    #total = sum(arr) - n
    avg = (sum(arr)-n)/n
    cnt = 0
    for a in arr[1:]:
        #arr에서 첫번째는 학새수를 저장하고 있기 때문에 arr[1]부터 검사 시작
        if a > avg:
            cnt += 1

    print("%.3f%%" %(cnt/n*100))
     

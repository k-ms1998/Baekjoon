n = int(input())

cnt = 1
num = n
while True:
    n_sum = num//10 + num%10
    if (num%10*10) + n_sum%10 == n:
        print(cnt)
        break

    num = num%10*10 + n_sum%10
    cnt += 1
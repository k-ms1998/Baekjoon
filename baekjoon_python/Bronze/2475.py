x = list(map(int, input().split()))

num = 0
for i in x:
    num += i*i
print(num%10)
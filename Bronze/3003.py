correct = [1,1,2,2,2,8]
found = list(map(int, input().split()))

for c,f in zip(correct, found):
    print(c-f, end=" ")
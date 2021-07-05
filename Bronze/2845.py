l, p  = map(int, input().split())
news = [x for x in map(int, input().split())]

total = l*p
for i in news:
    print(i-total, end=" ")
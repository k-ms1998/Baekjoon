t = int(input())

for _ in range(t):
    #n이 호텔의 층(h)와 나누어 떨어지는 값일 때 조심
    h,w,n = map(int, input().split())
    y = n%h if n%h != 0 else h
    x = n//h+1 if n%h !=0 else n//h
    print("%d%02d" %(y,x))
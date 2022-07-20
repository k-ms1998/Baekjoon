import math

t = int(input())

for _ in range(t):
    x1, y1, r1, x2, y2, r2 = map(int, input().split())

    if x1 == x2 and y1 == y2 and r1 == r2:
        print(-1)
        continue

    d1 = math.sqrt((x1-x2)**2 + (y1-y2)**2)
    d2 = r1 + r2
    d3 = r2 - r1 if r2 > r1 else r1 -r2

    if d1 == d2 or d1 == d3:
        #한점에서 만난다 ==> 외접 or 내접
        #외접 => 합 == d1
        #내점 => 차 == d1
        print(1)
    elif d1 > d2 or d1 < d3 or d1 == 0:
        #만나지 않는다
        #합 < d1 or 차 > d1 or d1 == 0
        print(0)
    else:
        #두 점에서 만난다 ==> 차 < d1 < 합
        print(2)


    
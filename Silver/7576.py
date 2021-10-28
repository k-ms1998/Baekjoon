import sys
from collections import deque
sys.setrecursionlimit(10**6)

def ripe(tom, farm, m, n, cnt):
    # for r in farm:
    #     print(r)
    # print("=========", tom)
    tmpTom = deque([])
    while tom:
        tmpCor = tom.popleft()
        tx = tmpCor[0]
        ty = tmpCor[1]
        
        if tx-1 >= 0:
            if farm[ty][tx-1] == 0:
                farm[ty][tx-1] = 1
                tmpTom.append([tx-1, ty])
        if ty-1 >= 0:
            if farm[ty-1][tx] == 0:
                farm[ty-1][tx] = 1
                tmpTom.append([tx, ty-1])
        if tx+1 <= m-1:
            if farm[ty][tx+1] == 0:
                farm[ty][tx+1] = 1
                tmpTom.append([tx+1, ty])
        if ty+1 <= n-1:
            if farm[ty+1][tx] == 0:
                farm[ty+1][tx] = 1
                tmpTom.append([tx, ty+1])

    if tmpTom:
        return ripe(tmpTom, farm, m, n, cnt+1)

    return cnt


m,n = map(int, input().split())
farm = []
tom = deque([])
for i in range(n):
    r = list(map(int, input().split()))
    farm.append(r)
    for j in range(m):
        if r[j] == 1:
            tom.append([j,i])
#함수를 사용해서 재귀로 문제를 풀면, 백준에서 메모리 초과 발생
#예제 && 추가 테스트 케이스 모두 통과
# cnt = ripe(tom, farm, m, n, 0)
# for r in farm:
#     if 0 in r:
#         cnt = 0
#         break
# print(cnt)

cnt = 0
while tom:
    tmpCor = tom.popleft()
    tx = tmpCor[0]
    ty = tmpCor[1]
        
    if tx-1 >= 0:
        if farm[ty][tx-1] == 0:
            farm[ty][tx-1] = farm[ty][tx]+1
            tom.append([tx-1, ty])
    if ty-1 >= 0:
        if farm[ty-1][tx] == 0:
            farm[ty-1][tx] = farm[ty][tx]+1
            tom.append([tx, ty-1])
    if tx+1 <= m-1:
        if farm[ty][tx+1] == 0:
            farm[ty][tx+1] = farm[ty][tx]+1
            tom.append([tx+1, ty])
    if ty+1 <= n-1:
        if farm[ty+1][tx] == 0:
            farm[ty+1][tx] = farm[ty][tx]+1
            tom.append([tx, ty+1])
    

for r in farm:
    if cnt < max(r):
        cnt = max(r)
    if 0 in r:
        cnt = 0
        break
print(cnt-1)
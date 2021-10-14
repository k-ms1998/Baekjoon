def lshift(p, arr, cnt):
    #print("l: ",arr)
    if p != arr[0]:
        arr.append(arr.pop(0))
        cnt = lshift(p, arr, cnt+1)

    return cnt

def rshift(p, arr, cnt):
    #print("r: ", arr)
    if p != arr[0]:
        arr.insert(0,arr.pop())
        cnt = rshift(p, arr, cnt+1)
    return cnt


n, m = map(int, input().split())
pos = list(map(int, input().split()))
arr = [i for i in range(1,n+1)]
cnt = 0
while pos:
    p = pos[0]
    pPos = arr.index(p)
    if p-arr[0]==0:
        arr.pop(0)
        pos.pop(0)
        continue
    elif pPos <= len(arr)-1-pPos:
        cnt += lshift(p, arr, 0)
        continue
    else:
        cnt += rshift(p, arr, 0)
        continue

print(cnt)

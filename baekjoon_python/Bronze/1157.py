w = list(input())

cnt = {}
for c in w:
    if ord(c) >= ord('a') and ord(c) <= ord('z'):
        c = c.upper()
    
    cnt[c] = cnt.get(c, 0) + 1

max_v = max(cnt.values())
res = list(filter(lambda x:cnt[x] == max_v, cnt.keys()))

if len(res) > 1:
    print('?')
else:
    print(res[0])
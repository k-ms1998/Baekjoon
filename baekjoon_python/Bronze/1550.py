h = input()
h_list = list(h)
h_list.reverse()

ans = 0
pow = 1
for cur in h_list:
    if cur == 'A':
        ans += 10*pow
    elif cur == 'B':
        ans += 11*pow
    elif cur == 'C':
        ans += 12*pow
    elif cur == 'D':
        ans += 13*pow
    elif cur == 'E':
        ans += 14*pow
    elif cur == 'F':
        ans += 15*pow
    else:
        ans += int(cur)*pow
    pow *= 16

print(ans)

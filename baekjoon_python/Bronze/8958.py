n = int(input())

for _ in range(n):
    res = list(input())
    total_score = 0
    cur_score = 0
    for curRes in res:
        if curRes == 'O':
            cur_score += 1
            total_score += cur_score
            continue
        else:
            cur_score = 0
    
    print(total_score)
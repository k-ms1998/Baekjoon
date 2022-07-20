def checkLeap(y):
    if y%400 == 0:
        return True
    elif y%100 == 0:
        return False
    elif y%4 == 0:
        return True
    else:
        return False

def calDays(y, m, d):
    days = 0
    m31 = [1,3,5,7,8,10,12]
    for i in range(0, y):
        if checkLeap(i):
            days += 366
        else:
            days += 365
    
    for i in range(1, m):
        if i == 2:
            if checkLeap(y):
                days += 29
            else:
                days += 28
            continue
        
        if i in m31:
            days += 31
        else:
            days += 30
            
    days += d
    return days


y1, m1, d1 = map(int, input().split())
y2, m2, d2 = map(int, input().split())
m31 = [1,3,5,7,8,10,12]

if y2-y1 >= 1001 or (y2-y1 == 1000 and (m2 > m1 or (m2 == m1 and d2 >= d1))):
    print('gg')
else:
    print("D-%d" %(calDays(y2,m2,d2) - calDays(y1,m1,d1)))
"""    dday = 0
    while True:
        if y1 == y2 and m1 == m2 and d1 == d2:
            break

        if y2 - y1 >= 2:
            if checkLeap(y1):
                dday += 366
            else:
                dday += 365
            y1 += 1
            continue
        elif y2 - y1 == 1 and m2 > m1:
            if checkLeap(y1):
                dday += 366
            else:
                dday += 365

            y1 += 1
            continue

        d1 += 1
        dday += 1
        if m1 == 2 and d1 == 29:
            if checkLeap(y1):
                dday += 1
            m1 += 1
            d1 = 1
            continue
        elif m1 in m31 and d1 == 32:
            if m1 == 12:
                y1 += 1
                m1 = 0
            m1 += 1 
            d1 = 1
            continue
        elif m1 not in m31 and d1 == 31:
            m1 += 1
            d1 = 1

    print("D-%d" %dday) """
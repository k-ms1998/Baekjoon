def checkLeap(y):
    if y%400 == 0:
        return True
    elif y%100 == 0:
        return False
    elif y%4 == 0:
        return True
    else:
        return False

curDate = list(input().split())
print(curDate)
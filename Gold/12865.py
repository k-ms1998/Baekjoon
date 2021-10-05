n, k = map(int, input().split())
items = []
max_v = 0
bag = [[0 for _ in range(k+1)] for __ in range(n)]

#input the weight and value of items
for i in range(n):
    items.append(list(map(int, input().split())))

#DP(Dynamic Programming)
for i in range(n):
    for j in range(k+1):
        if items[i][0] > j:
            #Check if the current item exceeds the current allowed weight limit(j)
            if j > 0:
                #If so, replace bag[i][j] with bag[i-1][j] in order to keep the higest allowed value at the most downward and rightward location
                bag[i][j] = bag[i-1][j]
            continue

        #tmp_m = bag[i][j-1]
        #if tmp_m < items[i][1]:
        #    tmp_m = items[i][1]
        #if tmp_m < bag[i-1][j] and i > 0:
        #    tmp_m = bag[i-1][j]
        #if tmp_m < (items[i][1]+bag[i-1][j-items[i][0]]) and i > 0:
        #    tmp_m = items[i][1]+bag[i-1][j-items[i][0]]

        #The max value from items[i][1], bag[i-1][j], items[i][1]+bag[i-1][j-items[i][0]] should go into bag[i][j]
            #Since the most downward and rightward location always contains the highest allowed value, check the value of items[i][1]+bag[i-1][j-items[i][0]]
                #items[i][1] == the value of the currect item && bag[i-1][j-items[i][0]] ==> j-items[i][0] == (the currect allowed weight limit)-(the weight of the currect item)
                #j의 값이 5이고, 현재 item의 무게가 2이면, 최대로 더 추가할 수 있는 무게는 3이므로, 무게가 3일때의 최대 value를 더한 값
        bag[i][j] = max(items[i][1], max(bag[i-1][j], items[i][1]+bag[i-1][j-items[i][0]]))

#for b in bag:
#    print(b)

print(bag[n-1][k])
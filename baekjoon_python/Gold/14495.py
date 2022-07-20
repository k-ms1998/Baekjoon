def pesudoFibo(f1, f2, f3, f4, p, n):
    #print(f1,f2,f3,f4)
    f1 = f2
    f2 = f3
    f3 = f4

    if p < n:
        return pesudoFibo(f1, f2, f3, f3+f1, p+1, n)
    
    return f4


n = int(input())
if n <= 3:
    print(1)
else:
    print(pesudoFibo(1,1,1,2,4,n))

def fibo(a,b,p,n):
    if p == n:
        print(b)
        return
    else:
        fibo(b,a+b,p+1,n)

    return
    

n = int(input())
if n == 0:
    print(0)
else:
    fibo(0,1,1,n)

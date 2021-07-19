def checkPalindrome(num):
    num_l = list(num)
    size = len(num_l)
    
    for i in range(size//2):
        if num_l[i] != num_l[size-1-i]:
            return 'no'
        
    return 'yes'

while True:
    num = input()
    if num == '0':
        break
    print(checkPalindrome(num))
import sys
sys.setrecursionlimit(10**6)

#Find path from starting node to the root
#Add length of path from the two starting nodes, then subtract the number of nodes that they both visit
#If the number of nodes that they both pass are zero, that means they aren't related
def getParent(c, path, tree):
    if tree[c-1]:
        path.append(getParent(tree[c-1][0], path, tree))

    return c

n = int(input())
s,d = map(int, input().split())
m = int(input())
tree =  [[] for _ in range(n)]
for _ in range(m):
    x, y = map(int, input().split())
    tree[y-1].append(x)

sPath = [s]
dPath = [d]
getParent(s, sPath, tree)
getParent(d, dPath, tree)
print(sPath)
print(dPath)

#Start the count from -1 since the paths will include the root node, and both paths will always include to root node if they are related
cnt = -1
for n in sPath:
   if n in dPath:
       cnt += 1
if cnt == -1:
    print(-1)
else:
    #len(path)-1 to not count the starting nodes themselves
    #But, the paths should inlcude starting nodes to account for when the two nodes are directly related
    print((len(sPath)-1)+(len(dPath)-1)-2*cnt) 
    
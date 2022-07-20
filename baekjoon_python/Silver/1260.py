import sys
sys.setrecursionlimit(10**6)

#DFS = Depth First Search
#Therefore, call function dfs everytime a new unvisited node is popped
def dfs(nodes, edges, v, arr):
    if v not in arr:
        arr.append(v)
        nodes[v-1] = -1
    while edges[v-1]:
        v2 = edges[v-1].pop(0)
        if v2 in nodes:
            dfs(nodes, edges, v2, arr)
            
#BFS = Breadth First Search
#Therefore, add nearby nodes to the list first, and then call the function bfs
def bfs(nodes, edges, v, arr):
    if v not in arr:
            arr.append(v)

    while edges[v-1]:
        e = edges[v-1].pop(0)
        nodes.append(e)
        if e not in arr:
            arr.append(e)
    
    if nodes:
        n = nodes.pop(0)
        bfs(nodes, edges, n, arr)
            

n, m, v = map(int, input().split())
nodes = [i for i in range(1, n+1)]
edges = [[] for _ in range(n)]
edgesB = [[] for _ in range(n)]
for _ in range(m):
    e = list(map(int, input().split()))
    edges[e[0]-1].append(e[1])
    edges[e[1]-1].append(e[0])
    edgesB[e[0]-1].append(e[1])
    edgesB[e[1]-1].append(e[0])
for i in range(n):
    edges[i].sort()
    edgesB[i].sort()

dfsArr = []
bfsArr = []
dfs(nodes, edges, v, dfsArr)
bfs([], edgesB, v, bfsArr)

print(*dfsArr)
print(*bfsArr)

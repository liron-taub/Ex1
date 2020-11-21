# Ex1

We were given 3 interfaces that we had to implement: 
node_info, weighted_graph, weighted_graph_algorithms in undirected graph.
In addition, I created a new class called **Edge** - its goal is to access the weights of the edge more conveniently.
In Ex1 we need to save and load graph -
I used **Serializable** because its purpose is to store a particular file inside a file and then return it to the work environment.

### Important functions in GraphDS:

1. getEdge:
- Tests a necessary condition for creating an edge:
 whether the vertices exist for creating an edge
if exist- checks whether another vertex is its neighbor, if it is its neighbor it creates a side is between two vertices.

2. addNode:
- Checks Necessary Condition for adding node :
 Does the vertex I want to add dosnt exist, if not add the vertex.
 3. connect:
- Checking necessary conditions for connect:
That the vertices I test exist, that there is no self-side edge and negative weight.
 Then connects the two vertices to the edge and puts weight in the edge.
 4. removeNode:
 -  Necessary condition for removeNode :
check if  the Graph contains the vertex I want to delete.
Then delete the vertex, if the side of the action is connected you will also delete the sides that are connected to the vertex.
5. removeEdge:
-  A necessary condition for Edge:
check the exists of the edge , if exist  deletes the edge and the record of the edge from the neighbor.
6. compareTo:
psado cood thet learned in the lecture with  Elizabeth .

### Important functions in GraphAlgo:

 1. copy:
 create a deep copy, we will copy the resulting graph fully so that it points to another object in memory , not the Original object memory.
 
 2. shortestPathDist, shortestPath, isConnected :
 check that the graph is undirected , we will use the Dijkstra function learned in the lecture, I implemented with the psado code learned in class by Elizabeth, the algorithm solves the problem of finding the shortest route from a single source for a directed or unintentional graph. Basically for each vertex we will mark whether they visited it or not, if not the distance will be defined as infinity
Complexity is o(| E | × LOG | V |) and since it finds the smallest path size it can also check that the graph is a undirected graph.
 3. save :
 A function that saves our graph as a text document on a computer, I used the **Serializable** class and it will save the number of vertices in the graph, the number of edges, and the number of changes made to the file. The file is saved as a TXT file.
 
 4. load :
 Receives the file we saved and restores it through the **Serializable** class.

#### My project includes test files to all of the mentioned classes and functionality.


#include <iostream>
#include <vector>
#include <queue>
#include <climits>
#include <stack>

using namespace std;

typedef pair<int, int> iPair;

vector<vector<iPair>> graph;

void dijkstra(int source, int vertices) {

    priority_queue<iPair, vector<iPair>, greater<iPair>> pq;


    vector<int> distance(vertices, INT_MAX);


    vector<vector<int>> path(vertices);


    distance[source] = 0;


    pq.push({0, source});

    while (!pq.empty()) {
        int u = pq.top().second;
        pq.pop();

        for (auto neighbor : graph[u]) {
            int v = neighbor.first;
            int weight = neighbor.second;

            if (distance[u] + weight < distance[v]) {
                distance[v] = distance[u] + weight;
                path[v] = path[u];
                path[v].push_back(u);
                pq.push({distance[v], v});
            }
        }
    }


    cout << "Shortest distances and paths from source node " << source << ":\n";
    for (int i = 0; i < vertices; ++i) {
        cout << "Node " << i << ": Distance = " << distance[i] << ", Path = ";

        if (distance[i] == INT_MAX) {
            cout << "No path\n";
        } else {
            stack<int> s;
            for (int node : path[i]) {
                s.push(node);
            }

            cout << source;
            while (!s.empty()) {
                cout << " -> " << s.top();
                s.pop();
            }
            cout << " -> " << i << endl;
        }
    }
}

int main() {
    int vertices, edges;
    cout << "Enter the number of vertices and edges: ";
    cin >> vertices >> edges;

    graph.resize(vertices);

    cout << "Enter the edges and weights (node1 node2 weight):\n";
    for (int i = 0; i < edges; ++i) {
        int node1, node2, weight;
        cin >> node1 >> node2 >> weight;
        graph[node1].push_back({node2, weight});
        graph[node2].push_back({node1, weight});
    }

    int source;
    cout << "Enter the source node: ";
    cin >> source;

    dijkstra(source, vertices);

    return 0;
}

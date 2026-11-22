# Intelligent Route & Delivery Planner

A Java console application for managing delivery locations and routes, exploring a route network, and finding the shortest path between locations.

## Features

- Add, view, search and delete locations
- Add, view and delete routes
- Prevent duplicate locations and routes
- Display the graph using an adjacency list
- Traverse the network using BFS
- Find shortest routes using Dijkstra's algorithm
- Save and load locations and routes using file handling

## Technologies

- Java
- Collections Framework
- Graph Data Structure
- BFS
- Dijkstra's Algorithm
- File Handling

## How to Run

Open the `src` directory in the terminal and run:

```bash
javac *.java
java Main
```

## Data Storage

Locations and routes are stored locally in `locations.txt` and `routes.txt`.

## Project Structure

```text
Intelligent-Route-Delivery-Planner/
├── src/
│   ├── Main.java
│   ├── Location.java
│   ├── LocationManager.java
│   ├── Route.java
│   ├── RouteManager.java
│   ├── Graph.java
│   └── DataManager.java
├── .gitignore
└── README.md
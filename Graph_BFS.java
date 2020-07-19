package Atype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class Graph {
	private int V;		// 노드 개수
	private LinkedList<Integer>[] adj;	// 인접 리스트

	// 생성자
	@SuppressWarnings("unchecked")
	Graph(int v) {
		V = v;
		adj = new LinkedList[V];	// 객체배열 참조
		for(int i=0; i<v; i++)
			adj[i] = new LinkedList<Integer>();	//배열 원소
	}
	
	// 노드 연결 v->w
	void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
	}
	
	// s를 시작 노드로 BFS로 탐색하면서 탐색한 노드를 출력
	void BFS(int s) {
		boolean visited[] = new boolean[V];
		Queue<Integer> Q = new LinkedList<Integer>();
		LinkedList<Integer> result = new LinkedList<Integer>();
		
		// s 노드를 방문한 것으로 표시하고, Q에 삽입
		visited[s] = true;
		Q.add(s);
		
		// Q가 빌 때까지 반복
		while (!Q.isEmpty()) {
			// Q에서 방문노드를 추출
			s = Q.poll();
			result.add(s);
			
			// 방문노드와 인접한 모든 노드를 가져온다.
			Iterator<Integer> i = adj[s].listIterator();
			while (i.hasNext()) {
				int n = i.next();
				// 방문하지 않은 노드이면 방문한 것으로 표시하고, Q에 삽입
				if (!visited[n]) {
					visited[n] = true;
					Q.add(n);
				}
			}
		}
		
		for(int i=0; i<result.size(); i++) {
			Integer n = result.get(i);
			System.out.print(n + " ");
		}
		System.out.println();
	}
} 

public class Graph_BFS {

	
	public static void main(String[] args) {

		Graph g = new Graph(6);
		
		g.addEdge(1, 2);
		g.addEdge(1, 3);
		g.addEdge(2, 4);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 5);
		g.BFS(2);			// 2 1 4 3 5


		/*
		Graph g = new Graph(4);
		
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 0);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		g.addEdge(2, 1);
		g.addEdge(2, 3);
		g.addEdge(3, 3);

		g.BFS(2);			// 2 0 1 3
		*/
	}

}

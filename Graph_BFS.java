package Atype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class Graph {
	private int V;		// ��� ����
	private LinkedList<Integer>[] adj;	// ���� ����Ʈ

	// ������
	@SuppressWarnings("unchecked")
	Graph(int v) {
		V = v;
		adj = new LinkedList[V];	// ��ü�迭 ����
		for(int i=0; i<v; i++)
			adj[i] = new LinkedList<Integer>();	//�迭 ����
	}
	
	// ��� ���� v->w
	void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
	}
	
	// s�� ���� ���� BFS�� Ž���ϸ鼭 Ž���� ��带 ���
	void BFS(int s) {
		boolean visited[] = new boolean[V];
		Queue<Integer> Q = new LinkedList<Integer>();
		LinkedList<Integer> result = new LinkedList<Integer>();
		
		// s ��带 �湮�� ������ ǥ���ϰ�, Q�� ����
		visited[s] = true;
		Q.add(s);
		
		// Q�� �� ������ �ݺ�
		while (!Q.isEmpty()) {
			// Q���� �湮��带 ����
			s = Q.poll();
			result.add(s);
			
			// �湮���� ������ ��� ��带 �����´�.
			Iterator<Integer> i = adj[s].listIterator();
			while (i.hasNext()) {
				int n = i.next();
				// �湮���� ���� ����̸� �湮�� ������ ǥ���ϰ�, Q�� ����
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

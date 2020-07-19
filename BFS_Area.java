package Atype;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class rect {
	int y; 
	int x;
	
	rect(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class BFS_Area {
	static int[][] map;
	static boolean[][] visited;
	static int M, N, K;
	static int count;
	static int[] sum;
	static int[] dx = {0, 0, -1, 1};
	static int[] dy = {-1, 1, 0, 0};
	
	
	// �Է� ��
	// 5 7 3
	// 1 0 2 3
	// 0 1 3 1
	// 3 4 4 5
	// ��� : 3 [1, 7, 13, 0, 0, ...]
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		visited = new boolean[N][M];
		sum = new int[N*M];	// �и��� �� ������ ũ��
		count = 0;			// �и��� ���� ����
		
		// ������ ĥ���� ���� 1�� ��ĥ�ϱ�
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			
			for(int x=x1; x<=x2; x++) {
				for(int y=y1; y<=y2; y++) {
					map[x][y] = 1;
				}
			}
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		
		int c = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(map[i][j] == 0 && !visited[i][j]) {
					bfs(i, j, c);
					count++;	// �������� ���� ����
					c++;
				}
			}
		}
		
		System.out.println(count);
		Arrays.sort(sum, 0, count);
		System.out.println(Arrays.toString(sum));
	}
	
	static public void bfs(int y, int x, int c) {
		Queue<rect> q = new LinkedList<rect>();
		rect r1 = new rect(y, x);
		q.add(r1);
		
		while (!q.isEmpty()) {
			rect r2 = q.poll();
			visited[r2.y][r2.x] = true;
			sum[c] += 1;
			
			// �Ʒ�, ��, ��, �� �������� üũ
			for (int i=0; i<4; i++) {
				int ny = r2.y + dy[i];
				int nx = r2.x + dx[i];
				
				if (ny >= 0 && nx >= 0 && ny < N && nx < M) {
					if (map[ny][nx] == 0 && !visited[ny][nx]) {
						rect r3 = new rect(ny, nx);
						q.add(r3);
						visited[r3.y][r3.x] = true;
					}
				}
			}
		}
	}
	
}

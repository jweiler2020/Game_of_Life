import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLife extends TimerTask
{
	private boolean[][] board;
	private int generation = 0;
	
	public GameOfLife(boolean[][] b)
	{
		int len = b[0].length;
		for(int i = 1; i < b.length; i++)
		{
			if(b[i].length != len)
			{
				System.out.println("Error: Array size mismatch. Exiting program now");
				System.exit(1);
			}
		}
		board = b;
	}

	public void printBoard()
	{
		for(boolean[] i : board)
		{
			for(boolean j : i)
			{
				System.out.print((j ? 1 : 0));
			}
			System.out.println();
		}
	}

	public int getGeneration() { return generation; }

	public void step()
	{
		boolean[][] temp = new boolean[board.length][board[0].length];
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				temp[i][j] = check(i, j);
			}
		}
		board = temp;
		generation++;
	}

	public boolean check(int i, int j)
	{
		int num = count(i, j);
		if(board[i][j])     // The cell is alive
			return (num == 2) || (num == 3);
		else                // The cell is dead
			return num == 3;
	}

	public int count(int i, int j)
	{
		int c = 0;
		for(int h = (i == 0 ? 0 : -1); h <= (i == board.length-1 ? 0 : 1); h++)
		{
			for(int k = (j == 0 ? 0 : -1); k <= (j == board[0].length-1 ? 0 : 1); k++)
			{
				if((h | k) != 0 && board[i+h][j+k])
					c++;
			}
		}
		return c;
	}

	public void run()
	{
		System.out.println("Generation: " + generation);
		printBoard();
		System.out.println();
		step();
	}
	
	public static boolean[] stringArrToBoolArr(char[] arr)
	{
		boolean[] temp = new boolean[arr.length];
		for(int i = 0; i < arr.length; i++)
		{
			temp[i] = arr[i] != '0';
		}
		return temp;
	}

	public static boolean[] charArrToBoolArr(char[] arr, int len)
	{
		boolean[] temp = new boolean[len];
		int maxI = 0;
		for(int i = 0; i < Math.min(arr.length, len); i++)
		{
			temp[i] = arr[i] != '0';
			maxI = i;
		}
		// Fill up array to the length
		for(int i = maxI+1; i < len; i++)
		{
			temp[i] = false;
		}
		return temp;
	}
	
	public static void main(String[] args)
	{
		Scanner kb = new Scanner(System.in);

		// Create the board based on user specifications
		System.out.println("Enter the number of rows you would like");
		int rows = kb.nextInt();
		kb.nextLine();
		System.out.println("Enter a '0' for a dead cell, and a '1' for a live cell");

		boolean[][] board = new boolean[rows][];
		board[0] = stringArrToBoolArr(kb.nextLine().toCharArray());
		int len = board[0].length;
		for(int i = 1; i < rows; i++)
		{
			board[i] = charArrToBoolArr(kb.nextLine().toCharArray(), len);
		}

		System.out.println("Enter how many steps you would like be per second");
		double sps = kb.nextDouble();
		kb.nextLine();

		Timer t = new Timer();
		t.schedule(new GameOfLife(board), 0, (int)(1000/sps));
	}
}

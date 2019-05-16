import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TicTacToe {

	final static int EMPTY = -1;
	final static int O = 0;
	final static int X = 1;

	JFrame frame;
	GamePanel panel;

	int current = 0;
	int xCount;
	int oCount;
//	Point[] winList;
	int[][] board;

	public TicTacToe() {
		frame = new JFrame("3T");
		panel = new GamePanel();
		panel.addMouseListener(new Mouse());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setPreferredSize(new Dimension(400, 400));
		frame.setVisible(true);
		frame.pack();

		board = new int[3][3];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = EMPTY;
			}
		}

//		winList = new Point[3];
//		for (int i = 0; i < winList.length; i++) {
//			winList[i] = new Point(-1, -1);
//		}

//		printBoard();
	}

	public void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[j][i] == EMPTY ? "-" : board[j][i] == X ? "X" : "O");
			}
			System.out.println();
		}
	}

	public void printInfo(Point p) {
		System.out.println("X: " + p.getX());
		System.out.println("Y: " + p.getY());
		System.out.print("turn: ");
		System.out.println(current == X ? "X" : "O");
	}

//	public void printWinList() {
//		for (int i = 0; i < winList.length; i++) {
//			System.out.println("(" + winList[i].getX() + ", " + winList[i].getY() + ")");
//		}
//	}

	public int checkWin() {
		printBoard();
		int win = -1;
		for (int i = 0; i < board.length; i++) {
			if (board[i][0] != -1 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
//				winList[i] = new Point(i, 0);
				win = board[i][0];
			}
		}
		System.out.println("win: " + win);
		for (int i = 0; i < board.length; i++) {
			if (board[0][i] != -1 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
				win = board[0][i];
			}
		}
		System.out.println("win: " + win);
		if (board[0][0] != -1 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			win = board[0][0];
		}
		System.out.println("win: " + win);
		if (board[2][0] != -1 && board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
			win = board[2][0];
		}
		System.out.println("win: " + win);
//		printWinList();
		return win;
	}

	public void restart() {
		frame.dispose();
		new TicTacToe();
	}

	class GamePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawLine(0, panel.getHeight() / 3, panel.getWidth(), panel.getHeight() / 3);
			g2d.drawLine(0, 2 * panel.getHeight() / 3, panel.getWidth(), 2 * panel.getHeight() / 3);

			g2d.drawLine(panel.getWidth() / 3, 0, panel.getWidth() / 3, panel.getHeight());
			g2d.drawLine(2 * panel.getWidth() / 3, 0, 2 * panel.getWidth() / 3, panel.getHeight());

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == -1) {
						// don't draw anything
					} else if (board[i][j] == 0) {
						// o
						g2d.drawOval(5 + i * panel.getWidth() / 3, 5 + j * panel.getHeight() / 3,
								panel.getWidth() / 3 - 15, panel.getHeight() / 3 - 15); // square width
					} else if (board[i][j] == 1) {
						g2d.drawLine(i * panel.getWidth() / 3, j * panel.getHeight() / 3,
								panel.getWidth() / 3 * (i + 1), panel.getHeight() / 3 * (j + 1));

						g2d.drawLine(panel.getWidth() / 3 * (i + 1), j * panel.getHeight() / 3,
								i * panel.getWidth() / 3, panel.getHeight() / 3 * (j + 1));
					}
				}
			}
		}
	}

	class Mouse implements MouseListener {
		Object[] options = { "Yes", "No" };
		int turns;
		int ans;
		boolean gameOver;

		public Mouse() {
			gameOver = false;
			turns = 0;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (turns >= 9 || gameOver) {
				return;
			}
			int x = 3 * e.getX() / panel.getWidth();
			int y = 3 * e.getY() / panel.getHeight();

//			printInfo(e.getPoint());

			if (board[x][y] == -1) {
				board[x][y] = current;
				turns++;
				int winner = checkWin();
				if (winner == X) {
					ans = JOptionPane.showOptionDialog(null, "X WON!\nPlay again?", "Winner!",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); // option
					gameOver = true;
					if (ans == 0) {
						restart();
					}
				} else if (winner == O) {
					ans = JOptionPane.showOptionDialog(null, "O WON!\nPlay again?", "Winner!",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); // option
					gameOver = true;
					if (ans == 0) {
						restart();
					}
				} else if (turns == 9) {
					// cat's game
					ans = JOptionPane.showOptionDialog(null, "Cat's game\nPlay again?", "Cat.",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); // option
					gameOver = true;
					if (ans == 0) {
						restart();
					}
				}
				if (current == O) {
					current = X;
				} else if (current == X) {
					current = O;
				}
			}
//			printBoard();
			panel.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame.setDefaultLookAndFeelDecorated(true);
			new TicTacToe();
		});
	}
}

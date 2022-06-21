import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.AbstractAction;
import javax.swing.SwingConstants;

public class Cat{
	private Font fontOne = new Font("Arial", Font.BOLD, 25);
	private CatCell[] tiles = new CatCell[9];
	private String player = "X";
	private JLabel turnLabel = new JLabel("Turno de "+player, SwingConstants.CENTER);
	private JFrame frame;
	private AbstractAction action;
	private boolean winner = false;
	private MouseListener mouseAction;
	private int puntX = 0, puntO = 0;
	private JLabel xLabel, oLabel;

	public Cat(){
		xLabel = new JLabel(""+puntX);
		oLabel = new JLabel(""+puntO);

		mouseAction = new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				newGame();
			}
		};

		action = new AbstractAction(""){
			public void actionPerformed(ActionEvent e){
				for (CatCell t : tiles){
					if(e.getSource() == (CatCell)t){
						if(t.isTaken()) return;
						t.setTile(player);
					}
				}
				switchPlayer();
				checkWinner();
			}
		};

		for (int i=0; i<9; i++){
			tiles[i] = new CatCell(action);
		} 
	}

	public void newGame(){
		//frame.removeMouseListener(mouseAction);
		for (CatCell cell : tiles) cell.setTile("");
		player = "X";
		winner = false;
		for(CatCell cell : tiles) cell.setAction(action);
		updateLabel();
	}

	public void switchPlayer(){
		player = player=="X"?"O":"X";
		updateLabel();
	}

	public void updateLabel(){
		if (winner == false) turnLabel.setText("Turno de "+player);
	}

	public void checkWinner(){
		int[][] winStates = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}}; 
		ArrayList<Integer> xCells = new ArrayList<>();
		ArrayList<Integer> oCells = new ArrayList<>();

		for(int i = 0; i < 9; i++){
			if (tiles[i].getText().equals("X"))
				xCells.add(i);
			if (tiles[i].getText().equals("O"))
				oCells.add(i);
		}
		
		for(int i = 0; i<winStates.length; i++){
			if (xCells.contains(winStates[i][0]) && 
				xCells.contains(winStates[i][1]) && 
				xCells.contains(winStates[i][2])){
					turnLabel.setText("El ganador es X");
					puntX++;
					xLabel.setText(""+puntX);
					winner = true;
			}
		}

		for(int i = 0; i<winStates.length; i++){
			if (oCells.contains(winStates[i][0]) && 
				oCells.contains(winStates[i][1]) && 
				oCells.contains(winStates[i][2])){
					turnLabel.setText("El ganador es O");
					puntO++;
					oLabel.setText(""+puntO);
					winner = true;
				}
		}

		if(winner == false){
			int cont = 0;
			for (CatCell cell : tiles){
				if (cell.isTaken()) cont++;
			}
			if(cont == 9){
				turnLabel.setText("Gato encerrado");
				endGame();
			}
		}

		if(winner) endGame();
	}

	public void endGame(){
		for (CatCell cell : tiles){
			cell.setAction(new AbstractAction(cell.getTile()){
				@Override
				public void actionPerformed(ActionEvent e){
					
				}
			});
		}

		frame.addMouseListener(mouseAction);
	}

	public void initGUI(){
		frame = new JFrame("TIC TAC TOE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);

		turnLabel.setFont(fontOne);
		turnLabel.setForeground(new Color(0x505751));

		xLabel.setFont(fontOne);
		xLabel.setForeground(new Color(0x505751));

		oLabel.setFont(fontOne);
		oLabel.setForeground(new Color(0xF4E9D1));

		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.setBackground(new Color(0x0BA291));
		menuPanel.setPreferredSize(new Dimension(400,40));
		menuPanel.add(turnLabel, BorderLayout.CENTER);
		// menuPanel.add(xLabel, BorderLayout.WEST);
		// menuPanel.add(oLabel, BorderLayout.EAST);


		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(3,3,5,5));
		gamePanel.setPreferredSize(new Dimension(400,400));
		gamePanel.setBackground(new Color(0x0BA291));

		for (int i=0; i<9; i++) gamePanel.add(tiles[i]);

		frame.add(gamePanel, BorderLayout.CENTER);
		frame.add(menuPanel, BorderLayout.NORTH);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Cat().initGUI();
			}
		});
	}
}
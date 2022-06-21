import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import javax.swing.AbstractAction;


public class CatCell extends JButton{
	private Font fontOne = new Font("Arial", Font.BOLD, 70);
	private Color darkGreen = new Color(0x0BA291);
	private Color green = new Color(0x15BDAC);
	private Color xColor = new Color(0x505751);
	private Color oColor = new Color(0xF4E9D1);
	private String tile = "";
	
	public CatCell(String name){
		super(name);
		customize();
	}

	public CatCell(AbstractAction action){
		super(action);
		customize();
	}

	private void customize(){
		setFont(fontOne);
		setContentAreaFilled(false);
		setFocusable(false);
		setBorderPainted(false);
	}

	public void setTile(String p){
		this.tile = p;
		setText(p);
		setForeground(p=="X"?xColor:oColor);
		this.repaint();
	}

	public String getTile(){
		return tile;
	}

	public boolean isTaken(){
		return tile!="";
	}

	@Override
    public void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(darkGreen);
        } else if (getModel().isRollover()) {
            g.setColor(darkGreen);
        } else {
            g.setColor(green);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
} 
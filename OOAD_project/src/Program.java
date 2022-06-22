
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

public class Program extends JFrame implements Serializable
{

    public Program()
    {
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        JButton newButton = new JButton("New");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        tools.add(newButton);
        tools.add(saveButton);
        tools.add(loadButton);

        Board chess_board = new Board();
        this.add(chess_board.getThe_board());
        this.add(tools,BorderLayout.PAGE_START);

        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        newButton.addActionListener(e -> chess_board.set_boardsquare_image());
        saveButton.addActionListener(e -> chess_board.save_file());
        loadButton.addActionListener(e -> chess_board.load_file());
    }

    public static void main(String args[])
    {
        new Program();
    }
}

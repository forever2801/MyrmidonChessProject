import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class Board implements ActionListener, Serializable
{
    private Location the_clicked_location;
    private JPanel the_board = new JPanel(new GridLayout(6, 7));
    private JButton[][] board_squares = new JButton[6][7];
    private Location[][] board_location = new Location[6][7];

    private boolean state = true;

    private int intial_row = 0;
    private int intial_column = 0;

    private int player_turn = 1; // 0 = player1, 1 = player2

    private ChessPiece initial_piece;

    private static int TRANSFORM = 1; //halt

    public Board()
    {
        boardCreation();
    }

    public void boardCreation()
    {
        int counter = 0;
        int counter2 = 0;
        while (counter < 6) //row
        {
            while (counter2 < 7) //column
            {
                board_squares[counter][counter2] = new JButton(); //create button
                board_squares[counter][counter2].setBackground(Color.WHITE); //white tiles
                chess_image(counter, counter2);
                the_board.add(board_squares[counter][counter2]);
                board_squares[counter][counter2].addActionListener(this); //for actionPerformed
                counter2 = counter2 + 1;
            }
            counter2 = 0;
            counter = counter + 1;
        }
    }

    public void chess_image(int row, int column)
    {
        int player = 1;
        int player2 = 2;
        Image red_plus = new ImageIcon(this.getClass().getResource("redplus.jpg")).getImage();
        red_plus = red_plus.getScaledInstance(85, 85, 4);
        Image red_triangle = new ImageIcon(this.getClass().getResource("redtriangle.jpg")).getImage();
        red_triangle = red_triangle.getScaledInstance(85, 85, 4);
        Image red_chevron = new ImageIcon(this.getClass().getResource("redchevron.jpg")).getImage();
        red_chevron = red_chevron.getScaledInstance(85, 85, 4);
        Image red_sun = new ImageIcon(this.getClass().getResource("redsun.jpg")).getImage();
        red_sun = red_sun.getScaledInstance(85, 85, 4);

        Image blue_plus = new ImageIcon(this.getClass().getResource("blueplus.jpg")).getImage();
        blue_plus = blue_plus.getScaledInstance(85, 85, 4);
        Image blue_triangle = new ImageIcon(this.getClass().getResource("bluetriangle.jpg")).getImage();
        blue_triangle = blue_triangle.getScaledInstance(85, 85, 4);
        Image blue_chevron = new ImageIcon(this.getClass().getResource("bluechevron.jpg")).getImage();
        blue_chevron = blue_chevron.getScaledInstance(85, 85, 4);
        Image blue_sun = new ImageIcon(this.getClass().getResource("bluesun.jpg")).getImage();
        blue_sun = blue_sun.getScaledInstance(85, 85, 4);

        if ((row == 5 && column == 0) || (row == 5 && column == 6))
        {
            ChessPiece plus = new Plus(row,column,player,red_plus);
            board_squares[row][column].setIcon(new ImageIcon(red_plus));
            board_location[row][column] = new Location(row,column,plus);

        }
        else if ((row == 5 && column == 1) || (row == 5 && column == 5))
        {
            ChessPiece triangle = new Triangle(row,column,player,red_triangle);
            board_squares[row][column].setIcon(new ImageIcon(red_triangle));
            board_location[row][column] = new Location(row,column,triangle);
        }
        else if ((row == 5 && column == 2) || (row == 5 && column == 4))
        {
            ChessPiece chevron = new Chevron(row,column,player,red_chevron);
            board_squares[row][column].setIcon(new ImageIcon(red_chevron));
            board_location[row][column] = new Location(row,column,chevron);
        }
        else if (row == 5 && column == 3)
        {
            ChessPiece sun = new Sun(row,column,player,red_sun);
            board_squares[row][column].setIcon(new ImageIcon(red_sun));
            board_location[row][column] = new Location(row,column,sun);
        }
        else if ((row == 0 && column == 0) || (row == 0 && column == 6))
        {
            ChessPiece plus = new Plus(row,column,player2,blue_plus);
            board_squares[row][column].setIcon(new ImageIcon(blue_plus));
            board_location[row][column] = new Location(row,column,plus);
        }
        else if ((row == 0 && column == 1) || (row == 0 && column == 5))
        {
            ChessPiece triangle = new Triangle(row,column,player2,blue_triangle);
            board_squares[row][column].setIcon(new ImageIcon(blue_triangle));
            board_location[row][column] = new Location(row,column,triangle);
        }
        else if ((row == 0 && column == 2) || (row == 0 && column == 4))
        {
            ChessPiece chevron = new Chevron(row,column,player2,blue_chevron);
            board_squares[row][column].setIcon(new ImageIcon(blue_chevron));
            board_location[row][column] = new Location(row,column,chevron);
        }
        else if (row == 0 && column == 3)
        {
            ChessPiece sun = new Sun(row,column,player2,blue_sun);
            board_squares[row][column].setIcon(new ImageIcon(blue_sun));
            board_location[row][column] = new Location(row,column,sun);
        }
        else
        {
            board_location[row][column] = new Location(row,column);
        }
    }



    public JPanel getThe_board() //getter for private JPanel the_board
    {
        return the_board;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton clicked_button = new JButton();
        Object source = e.getSource();
        if (source instanceof JButton)
        {
            clicked_button = (JButton) source;
        }
        int counter = 0;
        int counter2 = 0;
        while(counter < 6)
        {
            while(counter2 < 7)
            {
                if(board_squares[counter][counter2] == clicked_button)
                {
                    the_clicked_location = board_location[counter][counter2]; //become the peseudo clicked button anchor location
                    ChessPiece p = the_clicked_location.getP();
                    check_piece(p,counter,counter2);
                }
                counter2 = counter2 + 1;
            }
            counter2 = 0;
            counter = counter + 1;
        }
    }

    public void check_piece(ChessPiece p,int row , int column) //need to make it until it take up liao need put back down
    {
        if ((state == true)  && (p == null)) //have piece in place + able to shift
        {
            setWarningMsg("There is no Piece Here!");
        }
        else if((state == true) && (p != null)) //no piece in place
        {
            initial_press(p,row,column);
        }
        else
        {
            final_press(initial_piece,row,column);
        }
    }


    public void initial_press(ChessPiece p, int row, int column)
    {
        if(p.getPlayer() != player_turn)
        {
            setWarningMsg("Not Player" + " " + (player_turn) + " " + "Turn!");
        }
        else
        {
            initial_piece = p;
            state = false;
            int counter = 0;

            intial_row = row;
            intial_column = column;
            ArrayList<Location> possible_movement = p.possible_movement(); //location here have them
            while(counter < possible_movement.size())
            {
                if(board_location[possible_movement.get(counter).getLocation_row()][possible_movement.get(counter).getLocation_coloum()].getP() != null)
                {
                    if(board_location[possible_movement.get(counter).getLocation_row()][possible_movement.get(counter).getLocation_coloum()].getP().getPlayer() != player_turn)
                    {
                        board_squares[possible_movement.get(counter).getLocation_row()][possible_movement.get(counter).getLocation_coloum()].setBackground(Color.GREEN);
                    }
                }
                else
                {
                    board_squares[possible_movement.get(counter).getLocation_row()][possible_movement.get(counter).getLocation_coloum()].setBackground(Color.GREEN);
                }
                counter = counter + 1;
            }
        }
    }

    public void final_press(ChessPiece p, int row , int column)
    {
        boolean yes = true;
        int counter = 0;
        int counter2 = 0;
        int counter3 = 0;
        ArrayList<Location> possible_movement = p.possible_movement(); //location here have them
        if(intial_row == row && intial_column == column)
        {
            state = true; //put back down
            while(counter < possible_movement.size())
            {
                board_squares[possible_movement.get(counter).getLocation_row()][possible_movement.get(counter).getLocation_coloum()].setBackground(Color.WHITE);
                counter = counter + 1;
            }
        }
        else
        {
            while(counter < possible_movement.size()) //loop the possible_movement arraylist
            {
                if(row == possible_movement.get(counter).getLocation_row() && column == possible_movement.get(counter).getLocation_coloum()) //check user did it press green squares (in this case yes)
                {
                    if(board_location[row][column].getP() != null) //check if it is Own Piece
                    {
                        if(board_location[row][column].getP().getPlayer() == player_turn)
                        {
                            setWarningMsg("Illegal Step - Cannot step on Own Piece!");
                            break;
                        }
                        else
                        {
                            board_location[row][column].setP(p);
                            board_location[row][column].getP().setRow(row);
                            board_location[row][column].getP().setColumn(column);
                            board_location[intial_row][intial_column].setP();
                            board_squares[row][column].setIcon(new ImageIcon(p.getImage()));
                            board_squares[intial_row][intial_column].setIcon(null);
                            while(counter2 < 6)
                            {
                                while(counter3 < 7)
                                {
                                    board_squares[counter2][counter3].setBackground(Color.WHITE);
                                    counter3 = counter3 + 1;
                                }
                                counter3 = 0;
                                counter2 = counter2 + 1;
                            }
                            state = true;
                            initial_piece = null;
                            yes = false;
                            check_winner();
                            transform_piece();
                            break;
                        }
                    }
                    else
                    {
                        board_location[row][column].setP(p);
                        board_location[row][column].getP().setRow(row);
                        board_location[row][column].getP().setColumn(column);
                        board_location[intial_row][intial_column].setP();
                        board_squares[row][column].setIcon(new ImageIcon(p.getImage()));
                        board_squares[intial_row][intial_column].setIcon(null);
                        while(counter2 < 6)
                        {
                            while(counter3 < 7)
                            {
                                board_squares[counter2][counter3].setBackground(Color.WHITE);
                                counter3 = counter3 + 1;
                            }
                            counter3 = 0;
                            counter2 = counter2 + 1;
                        }
                        state = true;
                        initial_piece = null;
                        yes = false;
                        check_winner();
                        transform_piece();
                        break;
                    }
                }
                counter = counter + 1;
            }
            if(yes != false) //check user did it press green squares (in this case no)
            {
                setWarningMsg("Illegal Step - Please Press on Green Squares!");
                state = false;
            }
        }
    }

    public void check_winner()
    {
        boolean got = false;
        int counter = 0;
        int counter2 = 0;
        while(counter < 6)
        {
            while(counter2 < 7)
            {
                ChessPiece lookup_piece = board_location[counter][counter2].getP();
                if(lookup_piece instanceof Sun && lookup_piece.getPlayer() != player_turn) //change to sun
                {
                    got = true;
                }
                counter2 = counter2 + 1;
            }
            counter2 = 0;
            counter = counter + 1;
        }
        if (got == false)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane optionPane = new JOptionPane("Player " + player_turn + " wins",JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionPane.createDialog("Congratulations!");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
        }
        else
        {
            if(player_turn == 1)
            {
                player_turn = 2;
            }
            else
            {
                player_turn = 1;
            }
        }
    }

    public static void setWarningMsg(String text){
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane(text,JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog("Warning!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public void transform_piece()
    {
        int counter = 0;
        int counter2 = 0;
        if(TRANSFORM == 3)
        {
            while(counter < 6)
            {
                while(counter2 < 7)
                {
                    ChessPiece lookup_piece = board_location[counter][counter2].getP();
                    if(lookup_piece instanceof Plus && lookup_piece.getPlayer() == 1) //plus --> triangle
                    {
                        Image red_triangle = new ImageIcon(this.getClass().getResource("redtriangle.jpg")).getImage();
                        red_triangle = red_triangle.getScaledInstance(85, 85, 4);
                        ChessPiece plus_to_triangle = new Triangle(board_location[counter][counter2].getLocation_row(),board_location[counter][counter2].getLocation_coloum(),lookup_piece.getPlayer(),red_triangle);
                        board_location[counter][counter2].setP(plus_to_triangle);
                        board_squares[counter][counter2].setIcon(new ImageIcon(red_triangle));
                    }
                    else if(lookup_piece instanceof Plus && lookup_piece.getPlayer() == 2)
                    {
                        Image blue_triangle = new ImageIcon(this.getClass().getResource("bluetriangle.jpg")).getImage();
                        blue_triangle = blue_triangle.getScaledInstance(85, 85, 4);
                        ChessPiece plus_to_triangle = new Triangle(board_location[counter][counter2].getLocation_row(),board_location[counter][counter2].getLocation_coloum(),lookup_piece.getPlayer(),blue_triangle);
                        board_location[counter][counter2].setP(plus_to_triangle);
                        board_squares[counter][counter2].setIcon(new ImageIcon(blue_triangle));
                    }
                    else if(lookup_piece instanceof Triangle && lookup_piece.getPlayer() == 1) //triangle --> chevron
                    {
                        Image red_chevron = new ImageIcon(this.getClass().getResource("redchevron.jpg")).getImage();
                        red_chevron = red_chevron.getScaledInstance(85, 85, 4);
                        ChessPiece triangle_to_chevron = new Chevron(board_location[counter][counter2].getLocation_row(),board_location[counter][counter2].getLocation_coloum(),lookup_piece.getPlayer(),red_chevron);
                        board_location[counter][counter2].setP(triangle_to_chevron);
                        board_squares[counter][counter2].setIcon(new ImageIcon(red_chevron));
                    }
                    else if(lookup_piece instanceof Triangle && lookup_piece.getPlayer() == 2)
                    {
                        Image blue_chevron = new ImageIcon(this.getClass().getResource("bluechevron.jpg")).getImage();
                        blue_chevron = blue_chevron.getScaledInstance(85, 85, 4);
                        ChessPiece triangle_to_chevron = new Chevron(board_location[counter][counter2].getLocation_row(),board_location[counter][counter2].getLocation_coloum(),lookup_piece.getPlayer(),blue_chevron);
                        board_location[counter][counter2].setP(triangle_to_chevron);
                        board_squares[counter][counter2].setIcon(new ImageIcon(blue_chevron));
                    }
                    else if(lookup_piece instanceof Chevron && lookup_piece.getPlayer() == 1) //chevron --> plus
                    {
                        Image red_plus = new ImageIcon(this.getClass().getResource("redplus.jpg")).getImage();
                        red_plus = red_plus.getScaledInstance(85, 85, 4);
                        ChessPiece chevron_to_plus = new Plus(board_location[counter][counter2].getLocation_row(),board_location[counter][counter2].getLocation_coloum(),lookup_piece.getPlayer(),red_plus);
                        board_location[counter][counter2].setP(chevron_to_plus);
                        board_squares[counter][counter2].setIcon(new ImageIcon(red_plus));
                    }
                    else if(lookup_piece instanceof Chevron && lookup_piece.getPlayer() == 2)
                    {
                        Image blue_plus = new ImageIcon(this.getClass().getResource("blueplus.jpg")).getImage();
                        blue_plus = blue_plus.getScaledInstance(85, 85, 4);
                        ChessPiece chevron_to_plus = new Plus(board_location[counter][counter2].getLocation_row(),board_location[counter][counter2].getLocation_coloum(),lookup_piece.getPlayer(),blue_plus);
                        board_location[counter][counter2].setP(chevron_to_plus);
                        board_squares[counter][counter2].setIcon(new ImageIcon(blue_plus));
                    }
                    counter2 = counter2 + 1;
                }
                counter2 = 0;
                counter = counter + 1;
            }
            TRANSFORM = 1;
        }
        else
        {
            TRANSFORM = TRANSFORM + 1;
        }
    }

    public void save_file()
    {
        Object ob = board_location;
        Object ob_2 = board_squares;
        try
        {
            File file = new File("savefile.txt");
            if(!(file.exists()))
            {
                file.createNewFile();
            }
            FileWriter write = new FileWriter(file);
            BufferedWriter write_file = new BufferedWriter(write);
            PrintWriter printstyle_write_file = new PrintWriter(write_file);
            printstyle_write_file.println(player_turn);
            System.out.println("File written Successfully");
            printstyle_write_file.close();
            write_file.close();
            write.close();
        }
        catch (IOException e)
        {
            System.out.println("File Error!");
        }
        try
        {
            FileOutputStream fileOut = new FileOutputStream("SaveGame.ser");
            ObjectOutputStream  out = new ObjectOutputStream(fileOut);
            out.writeObject(ob);
            out.close();
            fileOut.close();
            FileOutputStream fileOut2 = new FileOutputStream("SaveGame2.ser");
            ObjectOutputStream  out2 = new ObjectOutputStream(fileOut2);
            out.writeObject(ob_2);
            out2.close();
            fileOut2.close();
        }
        catch (IOException i)
        {
            i.printStackTrace();
        }
    }

    public void load_file()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("SaveGame.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object o = in.readObject();
            board_squares = (JButton[][]) o;
            in.close();
            fileIn.close();
            FileInputStream fileIn2 = new FileInputStream("SaveGame2.ser");
            ObjectInputStream in2 = new ObjectInputStream(fileIn2);
            board_location = (Location[][]) o;
            in2.close();
            fileIn2.close();

        }
        catch (IOException | ClassNotFoundException i)
        {
            i.printStackTrace();
        }
    }

    public void set_boardsquare_image()
    {
        int counter = 0;
        int counter2 = 0;
        while(counter < 6)
        {
            while(counter2 < 7)
            {
                board_squares[counter][counter2].setIcon(null);
                board_squares[counter][counter2].setBackground(Color.WHITE);
                chess_image(counter,counter2);
                counter2 = counter2 + 1;
            }
            counter2 = 0;
            counter = counter + 1;
        }
        state = true;
        intial_row = 0;
        intial_column = 0;
        player_turn = 1;
        initial_piece = null;
        TRANSFORM = 1;
    }
}

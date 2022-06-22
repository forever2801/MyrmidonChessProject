import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Triangle extends ChessPiece implements Serializable
{
    public Triangle(int row, int column, int player, Image image)
    {
        super(row,column,player,image);
    }

    public ArrayList<Location> possible_movement()
    {
        ArrayList<Location> possible_movement = new ArrayList<>();
        int possible_row_movement = 1; //max go up to 2
        int possible_column_movement = 1; //max go up to 2
        while((possible_column_movement < 3 && possible_row_movement < 3)){
            if(((column - possible_column_movement) >= 0)&&((row - possible_row_movement) >= 0)){
                possible_movement.add(new Location((row - possible_row_movement) ,(column - possible_column_movement)));
            }
            if(((column - possible_column_movement) >= 0)&&((row + possible_row_movement) <= 5)){
                possible_movement.add(new Location((row + possible_row_movement) ,(column - possible_column_movement)));
            }
            if(((column + possible_column_movement) <= 6)&&(row - possible_row_movement) >= 0){
                possible_movement.add(new Location((row - possible_row_movement) ,(column + possible_column_movement)));
            }
            if(((column + possible_column_movement) <= 6)&&(row + possible_row_movement) <= 5){
                possible_movement.add(new Location((row + possible_row_movement) ,(column + possible_column_movement)));
            }
            possible_column_movement = possible_column_movement + 1;
            possible_row_movement = possible_row_movement + 1;
        }
        return possible_movement;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }

    public int getPlayer()
    {
        return player;
    }

    public Image getImage()
    {
        return image;
    }
}
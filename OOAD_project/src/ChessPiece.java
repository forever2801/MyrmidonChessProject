import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class ChessPiece implements Serializable {
    protected int row;
    protected int column;
    protected int player;
    protected Image image;

    public ChessPiece(int row, int column,int player,Image image)
    {
        this.row = row;
        this.column = column;
        this.player = player;
        this.image = image;
    }

    public abstract ArrayList<Location> possible_movement();
    public abstract int getRow();
    public abstract void setRow(int row);
    public abstract int getColumn();
    public abstract void setColumn(int column);
    public abstract int getPlayer();
    public abstract Image getImage();


}

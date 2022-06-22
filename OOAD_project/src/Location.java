import java.io.Serializable;

public class Location implements Serializable
{
    private int location_row;
    private int location_column;
    private ChessPiece p; //the piece in the board


    public Location(int location_row, int location_column)
    {
        this.location_row = location_row;
        this.location_column = location_column;
    }

    public Location(int location_row, int location_column, ChessPiece p)
    {
        this.location_row = location_row;
        this.location_column = location_column;
        this.p = p;
    }


    public int getLocation_row()
    {
        return location_row;
    }

    public void setLocation_row(int location_row)
    {
        this.location_row = location_row;
    }

    public int getLocation_coloum()
    {
        return location_column;
    }

    public void setLocation_column(int location_column)
    {
        this.location_column = location_column;
    }

    public ChessPiece getP() //return the board_piece( plus ) -->accessor<--
    {
        return p;
    }

    public void setP(ChessPiece p)
    {
        this.p = p;
    }

    public void setP()
    {
        this.p = null;
    }



}

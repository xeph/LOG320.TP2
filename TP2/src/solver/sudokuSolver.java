package solver;

public class sudokuSolver
{
    private byte[][] grid = new byte[9][9];

    public void solve()
    {

    }

    private boolean validateInsert(byte row, byte column, byte number)
    {
        boolean isValid = false;

        isValid = validateRow(row, number);
        isValid = validateColumn(column, number);
        isValid = validateSquare(row, column, number);

        return isValid;
    }

    private boolean validateRow(byte row, byte number)
    {
        for (byte column = 0; column <= 8; column++)
        {
            if (grid[row][column] == number)
                return false;
        }
        return true;
    }

    private boolean validateColumn(byte column, byte number)
    {
        for (byte row = 0; row <= 9; row++)
        {
            if (grid[row][column] == number)
                return false;
        }
        return true;
    }

    private boolean validateSquare(byte row, byte column, byte number)
    {
        byte r = (byte) ((row / 3) * 3);
        byte c = (byte) ((column / 3) * 3);

        for (byte i = 0; i <= 2; i++)
        {
            for (byte j = 0; j <= 2; j++)
            {
                if (grid[r+i][c+j] == number)
                    return false;
            }
        }
        return true;
    }
}

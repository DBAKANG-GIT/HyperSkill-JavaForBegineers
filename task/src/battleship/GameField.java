package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameField {

    public static final int CH_1 = 65;
    private static final int X_CORDINATE = 10;
    private static final int Y_CORDINATE = 10;
    private  final String[][] maskedGameField = new String[X_CORDINATE][Y_CORDINATE];
    private  final String[][] shipLocationGameFieldArray = new String[X_CORDINATE][Y_CORDINATE];
    private  final String[][] gameField2DArray = new String[X_CORDINATE][Y_CORDINATE];
    private ArrayList<Ship> shipArrayList = new ArrayList<>();
    private float area;
    public GameField() {
        resetGrid(gameField2DArray);
        resetGrid(maskedGameField);
        resetGrid(shipLocationGameFieldArray);
    }

    public void resetGrid(String[][] gameField2DArray) {
        // let's loop through array to populate board
        for (int row = 0; row < gameField2DArray.length; row++) {
            for (int col = 0; col < gameField2DArray[row].length; col++) {
                gameField2DArray[row][col] = "~";
            }
        }
    }

    public int getNumberofShipsNotSunk(ArrayList<Ship> ships){
        int eachShip = 0;
        for(Ship ship : ships){
            if(ship.getHealth() != 0){
                eachShip++;
            }
        }
        return eachShip;
    }

    public boolean isCordinateValid(int startPosition, int endPosition, int alternatePostion, String axis) {
        //checks if a given area is available and then return true.

        if (startPosition > endPosition) {
            startPosition = startPosition ^ endPosition;
            endPosition = startPosition ^ endPosition; // now y = x
            startPosition = startPosition ^ endPosition; // now x = y
        }

        for (; (startPosition <= endPosition && startPosition < gameField2DArray.length); startPosition++) {
            if (Objects.equals(axis, "Horizontal")) {
                if (isAnotherShipPresent(startPosition, alternatePostion)) return false;
            }
            else if (Objects.equals(axis, "Vertical") && isAnotherShipPresent(alternatePostion, startPosition)){
                 return false;
            }
        }
        return true;
    }

    private boolean isAnotherShipPresent(int startPosition, int alternatePostion) {
        if (Objects.equals(gameField2DArray[alternatePostion][startPosition-1], "O")) {
            System.out.println("\nError! A ship is already present in that location\n");
            return true;
        }
        if (GameField.isAdjacentToShip(gameField2DArray, alternatePostion, startPosition-1)) {
            System.out.println("\nError! You placed it too close to another one. Try again:\n");
            return true;
        }
        return false;
    }

    public static boolean isAdjacentToShip(String[][] gameField2DArray, int row, int col) {


        String topLeft = null;
        String botLeft = null;
        String top = null;
        String bot = null;
        String topRight = null;
        String botRight = null;
        String midLeft = null;
        String midRight = null;
        try {
            topLeft = gameField2DArray[row - 1][col - 1];
        } catch (ArrayIndexOutOfBoundsException ignored) { //leaving blank
        }
        try {
            top = gameField2DArray[row-1][col];
        } catch (ArrayIndexOutOfBoundsException ignored) { //leaving blank
        }
        try {
            topRight = gameField2DArray[row - 1][col + 1];
        } catch (ArrayIndexOutOfBoundsException ignored) { //leaving blank
        }

        try {
            midLeft = gameField2DArray[row][col - 1];
        } catch (ArrayIndexOutOfBoundsException ignored) { //leaving blank
        }
        try {
            midRight = gameField2DArray[row][col + 1];
        } catch (ArrayIndexOutOfBoundsException ignored) { //leaving blank
        }

        try {
            botLeft = gameField2DArray[row + 1][col - 1];
        } catch (ArrayIndexOutOfBoundsException ignored) { //leaving blank
        }
        try {
            bot = gameField2DArray[row + 1][col];
        } catch (ArrayIndexOutOfBoundsException ignored) { //leaving blank
        }
        try {
            botRight = gameField2DArray[row + 1][col + 1];
        } catch (ArrayIndexOutOfBoundsException ignored) { //leaving blank

        }

        String zero = "O";

        return zero.equals(topLeft) || (zero.equals(top) || (zero.equals(topRight) || (zero.equals(midLeft) || (zero.equals(midRight)
                || (zero.equals(botLeft) || (zero.equals(bot) || (zero.equals(botRight))))))));
    }

    // 2D  integer array with x axis and y axis
    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getxCordinate() {
        return X_CORDINATE;
    }

    public int getyCordinate() {
        return Y_CORDINATE;
    }

    public String[][] getMaskedGameField() {

        return maskedGameField;
    }

    public String[][] getGameField2DArray() {
        return gameField2DArray;
    }

    public String[][] getShipLocationGameField() {
        return shipLocationGameFieldArray;
    }

    public void drawGrid(String[][] gameField2DArray) {
        // let's loop through array to print each row and column

        //loop  to print 1 to 10.
        printColumnNumbers();
        for (int row = 0; row < gameField2DArray.length; row++) {
            //Char 65 dec -> A
            System.out.print((char) (CH_1 + row) + " ");
            for (int col = 0; col < gameField2DArray[row].length; col++) {
                System.out.print(gameField2DArray[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printColumnNumbers() {
        System.out.print("  ");
        for (int i = 1; i <= GameField.Y_CORDINATE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public  boolean areAllShipsSunk(List<Ship> shipArrayList){
        for(Ship ship : shipArrayList){
            if(ship.getHealth() != 0){
                return false;
            }
        }
        return true;
    }

    public List<Ship> getShipArrayList() {
        return shipArrayList;
    }

    public void setShipArrayList(ArrayList<Ship> shipArrayList) {
        this.shipArrayList = shipArrayList;
    }
}










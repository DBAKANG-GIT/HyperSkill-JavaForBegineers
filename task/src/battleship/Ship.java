package battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static battleship.GameField.CH_1;

public class Ship {

    private int health;
    private int xCordinate;
    private int yCordinate;
    private String name;

    public Ship() {
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getxCordinate() {
        return xCordinate;
    }

    public void setxCordinate(int xCordinate) {
        this.xCordinate = xCordinate;
    }

    public int getyCordinate() {
        return yCordinate;
    }

    public void setyCordinate(int yCordinate) {
        this.yCordinate = yCordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ship(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getShipName() {
        return this.name;
    }

    public static boolean fireShot(GameField gameField) throws RuntimeException, IOException {
        String[][] gameField2DArray = gameField.getGameField2DArray();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        // Array of string type to store input
        String coordinate;
        // Reading input a string
        try {
            coordinate = input.readLine();
        } catch (IOException e) {
            throw new IOException("Error in Reading coordinate to fire shot");
        }
        int xCordinate = coordinate.charAt(0) - CH_1;
        coordinate = coordinate.substring(1);
        int yCordinate = Integer.parseInt(coordinate);
        yCordinate = yCordinate - 1;

        while (!isShotCoordinateValid(xCordinate, yCordinate)) {
            try {
                coordinate = input.readLine();
            } catch (IOException e) {
                throw new IOException("Error in Reading coordinate to fire shot");
            }
            xCordinate = coordinate.charAt(0) - CH_1;
            coordinate = coordinate.substring(1);
            yCordinate = Integer.parseInt(coordinate);
            yCordinate = yCordinate - 1;
        }

        //What to if there is a hit
        if (Objects.equals(gameField2DArray[xCordinate][yCordinate], "X")) {
            System.out.println(" Expected hit in " + coordinate);
        }
        else if (Objects.equals(gameField2DArray[xCordinate][yCordinate], "O")) {
            String[][] arr = gameField.getShipLocationGameField();
            String firstLetter = arr[xCordinate][yCordinate];
            reduceShipHealth(gameField, firstLetter);
            gameField2DArray[xCordinate][yCordinate] = "X";
        }
        else {
            gameField2DArray[xCordinate][yCordinate] = "M";
        }
        return isTargetHit(gameField2DArray, xCordinate, yCordinate);
    }

    private static void reduceShipHealth(GameField gameField, String firstLetter) {
        Ship aircraft = gameField.getShipArrayList().get(0);
        Ship battleship = gameField.getShipArrayList().get(1);
        Ship submarine = gameField.getShipArrayList().get(2);
        Ship cruiser = gameField.getShipArrayList().get(3);
        Ship destroyer = gameField.getShipArrayList().get(4);

        switch (firstLetter) {
            //Get the ship to reduce and then reduce the health
            case "A" -> aircraft.setHealth(aircraft.getHealth() - 1);
            case "B" -> battleship.setHealth(battleship.getHealth() - 1);
            case "C" -> cruiser.setHealth(cruiser.getHealth() - 1);
            case "D" -> destroyer.setHealth(destroyer.getHealth() - 1);
            case "S" -> submarine.setHealth(submarine.getHealth() - 1);
            default -> System.out.println("Error in reducing ship health");
        }
    }


    public static boolean isTargetHit(String [][] gameField2DArray, int xCordinate, int yCordinate){
        //What to if there is a hit
        return Objects.equals(gameField2DArray[xCordinate][yCordinate], "X");

    }

    public static boolean isShotCoordinateValid(int xCordinate, int yCordinate){
        if(xCordinate < 0 || xCordinate > 10  || yCordinate < 0 || yCordinate > 9 )
        {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return false;
        }
        else return true;
    }


    public boolean landShip(GameField gameField, Ship ship){
        String[][] shipLocationGameField = gameField.getShipLocationGameField();
        String[][] gameField2DArray = gameField.getGameField2DArray();


        System.out.println("Enter the coordinates of the " + name +  " ("+ health + " cells" +"):\n");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        // Array of string type to store input
        String[] strNums;
        // Reading input a string
        try {
            strNums = input.readLine().split(" ");
        } catch (IOException e) {
            throw new RuntimeException("Error in Reading Landing Coordinates");
        }

        if ((strNums[0].charAt(0) != strNums[1].charAt(0)) &&  strNums[0].charAt(1) != strNums[1].charAt(1)){
            System.out.println("\nError! Wrong ship location! Try again:\n");
            return false;
        }

        //Horizontal Placements
        if (strNums[0].charAt(0) == strNums[1].charAt(0)) {
            char row = strNums[0].charAt(0);
            int x1Cordinate = row - (CH_1); //F
            int y1Coordinate = Integer.parseInt(strNums[0].substring(1));
            int y2Cordinate = Integer.parseInt(strNums[1].substring(1));

            if (y1Coordinate > y2Cordinate){
                y1Coordinate = y1Coordinate ^ y2Cordinate;
                y2Cordinate = y1Coordinate ^ y2Cordinate; // now y = x
                y1Coordinate = y1Coordinate ^ y2Cordinate; // now x = y
            }
            boolean flag =  shipLengthCheckFlag(ship,y1Coordinate, y2Cordinate);
            if(!flag){
                return false;
            }
            if (gameField.isCordinateValid(y1Coordinate, y2Cordinate, x1Cordinate, "Horizontal")) {
                for(int j = y1Coordinate; j <= y2Cordinate; j++){
                    gameField2DArray[x1Cordinate][j-1] = "O";
                    shipLocationGameField[x1Cordinate][j-1] = String.valueOf(ship.getShipName().charAt(0));
                }
            }
            else {
                return false;
            }
        }
        //Vertical Placements
        else if (strNums[0].charAt(1) == strNums[1].charAt(1)) {
            char y1 = strNums[0].charAt(1); //Y1
            int y1Coordinate = Character.getNumericValue(y1);

            char column = strNums[0].charAt(0);
            int x1Cordinate = column - (CH_1); //x1

            char x2 = strNums[1].charAt(0);
            int x2Coordinate = x2 - (CH_1);

            if (x1Cordinate > x2Coordinate){
                x1Cordinate = x1Cordinate ^ x2Coordinate;
                x2Coordinate = x1Cordinate ^ x2Coordinate; // now y = x
                x1Cordinate = x1Cordinate ^ x2Coordinate; // now x = y
            }
            boolean flag =  shipLengthCheckFlag(ship,x1Cordinate, x2Coordinate);
            if(!flag){
                return false;
            }

            if(gameField.isCordinateValid(x1Cordinate, x2Coordinate, y1Coordinate, "Vertical")) {
                for(int i = x1Cordinate; i <= x2Coordinate; i++) {
                    gameField2DArray[i][y1Coordinate - 1] = "O";
                    shipLocationGameField[i][y1Coordinate - 1] = String.valueOf(ship.getShipName().charAt(0));
                }
            }
            else{
                return false;
            }
        }
        return true;
    }


    public boolean shipLengthCheckFlag(Ship ship, int firstValue, int secondValue){
        int difference = (Math.abs(firstValue - secondValue)) + 1;//checking negative values
        switch (ship.getName().toUpperCase()) {
            case "AIRCRAFT CARRIER" -> {
                if (difference != Health.AIRCRAFT.getValue()) {
                    printWrongLengthError(ship);
                    return false;
                }
            }
            case "SUBMARINE" -> {
                if (difference != Health.SUBMARINE.getValue()) {
                    printWrongLengthError(ship);
                    return false;
                }
            }
            case "BATTLESHIP" -> {
                if (difference != Health.BATTLESHIP.getValue()) {
                    printWrongLengthError(ship);
                    return false;
                }
            }
            case "CRUISER" -> {
                if (difference != Health.CRUISER.getValue()) {
                    printWrongLengthError(ship);
                    return false;
                }
            }
            case "DESTROYER" -> {
                if (difference != Health.DESTROYER.getValue()) {
                    printWrongLengthError(ship);
                    return false;
                }
            }
            default -> printWrongLengthError(ship);
        }

        return true;
    }

    private static void printWrongLengthError(Ship ship) {
        System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:\n");
    }


}

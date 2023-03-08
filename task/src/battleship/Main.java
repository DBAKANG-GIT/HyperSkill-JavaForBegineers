package battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // Write your code here
        startProgram();
    }

    public static void startProgram() throws IOException {

        //Placing Player 1
        GameField player1GameField = new GameField();
        Player player1 = new Player(player1GameField, "Player 1");
        ArrayList<Ship> player1Ships;
        player1Ships = landAllShips(player1GameField, player1);
        passGameMove();

        //Placing Player 2
        GameField player2Gamefield = new GameField();
        Player player2 = new Player(player2Gamefield, "player 2");
        ArrayList<Ship>player2Ships;
        player2Ships = landAllShips(player2Gamefield, player2);
        passGameMove();

        while(!player1.isPlayerDead(player1GameField) || !player2.isPlayerDead(player2Gamefield)) {
            attackShip(player1, player1GameField, player2Gamefield, player2Ships);
            attackShip(player2, player2Gamefield, player1GameField, player1Ships);
        }
        String playerName = (player1.isPlayerDead(player1GameField)) ? player1.getPlayerName() : player2.getPlayerName();
        System.out.println(playerName + " is the winner");
    }


    private static void passGameMove() {
        System.out.println("Press Enter and pass the move to another player\n");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // waits for user to press Enter
        System.out.println("...");
    }

    private static ArrayList<Ship>landAllShips(GameField gameField, Player player){
        System.out.println(player.getPlayerName()+ " place your ships on the game field\n");

        gameField.drawGrid(gameField.getGameField2DArray());
        Ship aircraft = new Ship("Aircraft Carrier", 5);
        boolean gameFlag = aircraft.landShip(gameField, aircraft);
        while (!gameFlag) {
            gameFlag = aircraft.landShip(gameField, aircraft);
        }
        gameField.drawGrid(gameField.getGameField2DArray());

        Ship battleship = new Ship("Battleship", 4);
        gameFlag = battleship.landShip(gameField, battleship);
        while (!gameFlag) {
            gameFlag = battleship.landShip(gameField, battleship);
        }
        gameField.drawGrid(gameField.getGameField2DArray());

        Ship submarine = new Ship("Submarine", 3);
        gameFlag = submarine.landShip(gameField, submarine);
        while (!gameFlag) {
            gameFlag = submarine.landShip(gameField, submarine);
        }
        gameField.drawGrid(gameField.getGameField2DArray());

        Ship cruiser = new Ship("Cruiser", 3);
        gameFlag = cruiser.landShip(gameField, cruiser);
        while (!gameFlag) {
            gameFlag = cruiser.landShip(gameField, cruiser);
        }
        gameField.drawGrid(gameField.getGameField2DArray());

        Ship destroyer = new Ship("Destroyer", 2);
        gameFlag = destroyer.landShip(gameField, destroyer);
        while (!gameFlag) {
            gameFlag = destroyer.landShip(gameField, destroyer);
        }
        gameField.drawGrid(gameField.getGameField2DArray());

        ArrayList<Ship> ships = new ArrayList<>();
        addShipsToArray(aircraft, battleship, submarine, cruiser, destroyer, ships);
        gameField.setShipArrayList(ships);


        return ships;

    }


    private static void addShipsToArray(Ship aircraft, Ship battleship, Ship submarine, Ship cruiser, Ship destroyer, ArrayList<Ship> ships) {
        ships.add(aircraft);
        ships.add(battleship);
        ships.add(submarine);
        ships.add(cruiser);
        ships.add(destroyer);
    }

    private static ArrayList<Ship> getAddedShips(ArrayList<Ship> ships) {
        ArrayList<Ship> addedShips = new ArrayList<>();
        addedShips.addAll(ships);
        return addedShips;
    }

    private static void attackShip(Player attackingPlayer, GameField attackingPlayerGameField, GameField defendingPlayerGamefield, ArrayList<Ship> defendingPlayerShips) throws IOException {
            int numOfShipsNotSunk = defendingPlayerGamefield.getNumberofShipsNotSunk(defendingPlayerShips);
            attackingPlayerGameField.drawGrid(attackingPlayerGameField.getMaskedGameField());
            System.out.println("---------------------");
            attackingPlayerGameField.drawGrid(attackingPlayerGameField.getGameField2DArray());
            System.out.println(attackingPlayer.getPlayerName() + ", it's your turn:");

            if (Ship.fireShot(defendingPlayerGamefield)) {
                //Add check here when a ship has been sunck -> You sank a ship! Specify a new target:
                int updatedNoOfShipsLeft = defendingPlayerGamefield.getNumberofShipsNotSunk(defendingPlayerShips);
                if(updatedNoOfShipsLeft == 0){
                    System.out.println( attackingPlayer.getPlayerName() + " You sank the last ship. You won. Congratulations!");
                    return;
                }
                if(updatedNoOfShipsLeft != numOfShipsNotSunk){
                    System.out.println("You sank a ship! Specify a new target: \n");
                    passGameMove();
                }
                else {
                    System.out.println("You hit a ship!");
                    passGameMove();
                }
            } else {
                System.out.println("You missed!");
                passGameMove();
            }

        }


}




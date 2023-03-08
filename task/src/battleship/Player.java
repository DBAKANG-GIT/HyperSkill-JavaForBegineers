package battleship;

public class Player {

    private GameField gameField;
    private String playerName;
    private boolean playerLife;

    public Player(String playerName, boolean playerLife) {
        this.playerName = playerName;
        this.playerLife = playerLife;
    }

    public Player(GameField gameField, String playerName, boolean playerLife) {
        this.gameField = gameField;
        this.playerName = playerName;
        this.playerLife = playerLife;
    }

    public Player(GameField gameField, String name ) {
        this.gameField = gameField;
        this.playerName = name;
    }

    public GameField getGameField() {
        return gameField;
    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isPlayerDead(GameField gameField) {
        return gameField.areAllShipsSunk(gameField.getShipArrayList());
    }

    public void setPlayerLife(boolean playerLife) {
        this.playerLife = playerLife;
    }
}

package battleship;

public enum Health {
    AIRCRAFT("Aircraft Carrier", 5),
    BATTLESHIP("Battleship",4),
    SUBMARINE ("Submarine",3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer",2);

    private final Integer value;
    private final String key;

    Health(String key, Integer value) {
        this.value = value;
        this.key = key;
    }




    public Integer getValue() {
        return value;
    }
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

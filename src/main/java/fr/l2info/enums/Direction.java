package fr.l2info.enums;

/**
 * Direction vers laquelle peut se déplacer une case (UP, DOWN, LEFT, RIGHT)
 **/
public enum Direction {
    UP('z', 0, -1),
    DOWN('s', 0, 1),
    LEFT('q', -1, 0),
    RIGHT('d', 1, 0);

    private final char key;
    private final int x;
    private final int y;

    private Direction(char key, int x, int y) {
        this.key = key;
        this.x = x;
        this.y = y;
    }

    public char getKey() {
        return key;
    }

    public static Direction getFromKey(char key) {
        for (Direction direction : Direction.values()) {
            if (direction.getKey() == key) {
                return direction;
            }
        }
        return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

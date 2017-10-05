package pathfinding;

/**
 * Created by faiter on 10/5/17.
 */
public class Tile {

    private int x;
    private int y;
    private boolean isWalkable;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean isWalkable() {

        return isWalkable;
    }

    public void setWalkable(boolean walkable) {

        isWalkable = walkable;
    }

    public boolean isAdjecentTo(Tile tile){

        if (tile == this) return false;

        return Math.abs(tile.getX()-x) == 1 || Math.abs(tile.getY()-y) == 1;

    }

    @Override
    public String toString() {

        return "Tile{" + "x=" + x + ", y=" + y + '}';
    }

    public static void main(String[] args) {

        Tile tile = new Tile(0, 0);

        Tile tile1 = new Tile(1, 1);

        Tile tile2 = new Tile(2, 2);

        System.out.println(tile.isAdjecentTo(tile2));

    }
}

package pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by faiter on 10/5/17.
 */
public class Testing {

    public static void main(String[] args) {

        List<List<Tile>> tiles = new ArrayList<>();

        for (int y = 0; y < 10; y++) {

            List<Tile> row = new ArrayList<>();

            tiles.add(row);

            for (int x = 0; x < 10; x++) {

                Tile tile = new Tile(x, y);

                row.add(tile);
            }
        }

        tiles.get(0).get(0).setWalkable(true);
        tiles.get(1).get(0).setWalkable(true);
        tiles.get(2).get(0).setWalkable(true);

        for (int i=1; i < tiles.size()-5; i++) {
            tiles.get(2).get(i).setWalkable(true);
        }
        tiles.get(1).get(4).setWalkable(true);
        for (int i = 4; i < 10; i++) {
            tiles.get(1).get(i).setWalkable(true);
        }
        for (int x = 0; x < tiles.size(); x++) {
            for (int y = 0; y < tiles.size(); y++) {
                Tile tile = tiles.get(y).get(x);
                if ( x == 9 && y > 1) tile.setWalkable(true);
            }
        }

        System.out.println(tiles);
        printBoard(tiles);

        List<Tile> path = getPath(tiles); // Builds linkedlist path
        path.forEach(System.out::println);
    }

    public static LinkedList<Tile> getPath(List<List<Tile>> tiles){

        List<Tile> allTiles = new ArrayList<>();
        tiles.forEach(allTiles::addAll);

        LinkedList<Tile> path = new LinkedList<>();

        for (int i = 0; i < allTiles.size(); i++) {

            Tile startTile = allTiles.get(i);

            for (int j = 0; j < allTiles.size(); j++) {

                Tile endTile = allTiles.get(j);

                if (    (startTile.isWalkable() && endTile.isWalkable())
                        && startTile.isAdjecentTo(endTile)
                        && !path.contains(startTile)){

                    path.add(startTile);
                }

            }

        }
        return path;
    }
    public static void printBoard(List<List<Tile>> tiles){

        tiles.forEach(tiles1 -> {

            tiles1.forEach(tile -> {

                if (tile.isWalkable()) System.out.print("O");
                else System.out.print("X");
            });
            System.out.println();

        });

    }
}

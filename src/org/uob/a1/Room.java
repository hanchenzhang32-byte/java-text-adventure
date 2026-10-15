package org.uob.a1;

/**
 * Represents a single room in the game world:
 * - a name to identify it,
 * - a description to show to the player,
 * - a symbol to display on the map,
 * - and its Position on the map.
 */
public class Room {
    private String name;
    private String description;
    private char symbol;
    private Position position;

    public Room(String name, String description, char symbol, Position position) {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public char getSymbol() {
        return symbol;
    }

    public Position getPosition() {
        return position;
    }
}

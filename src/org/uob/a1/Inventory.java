package org.uob.a1;

/**
 * Simple inventory that can hold up to 10 item names.
 * It stores items in a String array and returns them as a single line.
 */
public class Inventory {
    // maximum number of items the player can carry
    private final int MAX_ITEM = 10;

    // array to store item names
    private String[] items;

    // how many items are currently stored
    private int count;

    public Inventory() {
        items = new String[MAX_ITEM];
        count = 0;
    }

    // add an item if there is free space
    public void addItem(String item) {
        if (count < MAX_ITEM) {
            items[count] = item;
            count++;
        }
    }

    // check if an item exists; return its index or -1 if not found
    public int hasItem(String item) {
        for (int i = 0; i < count; i++) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    // remove the item and shift the remaining ones to the left
    public void removeItem(String item) {
        int index = hasItem(item);
        if (index != -1) {
            for (int i = index; i < count - 1; i++) {
                items[i] = items[i + 1];
            }
            count--;
        }
    }

    // return all items as one line, e.g. "note keycard "
    public String displayInventory() {
        String output = "";
        for (int i = 0; i < count; i++) {
            output += items[i] + " ";
        }
        return output;
    }
}

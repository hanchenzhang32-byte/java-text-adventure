package org.uob.a1;

import java.util.Scanner;

/**
 * Main game class: builds the world, then runs a command loop.
 */
public class Game {
    public static void main(String[] args) {

        // create a 5x5 map
        Map gameMap = new Map(5, 5);

        // create all rooms with name, description, symbol and position
        Room cell = new Room("Cell",
                "You are in a small cell with a door to the south and a passage to the east. There is a note on the floor.",
                'c', new Position(1, 1));
        Room garden = new Room("Garden",
                "A small indoor garden. From here you can go south to the control room or west back to the cell.",
                'g', new Position(2, 1));
        Room hallway = new Room("Hallway",
                "A long corridor leading to the control room.",
                'h', new Position(1, 2));
        Room control = new Room("Control Room",
                "You are in the control room. You see consoles and cables.",
                'o', new Position(2, 2));
        Room storage = new Room("Storage",
                "A dusty storage room. There is a closed toolbox here.",
                's', new Position(3, 2));
        Room engine = new Room("Engine Room",
                "You are in the engine room. A panel is open and missing a fuse.",
                'e', new Position(4, 2));
        Room lab = new Room("Lab",
                "You are in a small lab. There is a slot for a keycard here.",
                'l', new Position(2, 3));
        Room quarters = new Room("Quarters",
                "Crew quarters. There is a fuse on the bed.",
                'q', new Position(1, 3));
        Room vent = new Room("Vent Chamber",
                "A narrow chamber with pipes. To the east you see a bright exit.",
                'v', new Position(3, 3));
        Room exitRoom = new Room("Exit",
                "You have reached the exit area. You can go back west.",
                'x', new Position(4, 3));

        // place rooms on the map so the map command can show them
        gameMap.placeRoom(cell.getPosition(), cell.getSymbol());
        gameMap.placeRoom(garden.getPosition(), garden.getSymbol());
        gameMap.placeRoom(hallway.getPosition(), hallway.getSymbol());
        gameMap.placeRoom(control.getPosition(), control.getSymbol());
        gameMap.placeRoom(storage.getPosition(), storage.getSymbol());
        gameMap.placeRoom(engine.getPosition(), engine.getSymbol());
        gameMap.placeRoom(lab.getPosition(), lab.getSymbol());
        gameMap.placeRoom(quarters.getPosition(), quarters.getSymbol());
        gameMap.placeRoom(vent.getPosition(), vent.getSymbol());
        gameMap.placeRoom(exitRoom.getPosition(), exitRoom.getSymbol());

        // player state
        Room currentRoom = cell;
        Inventory inventory = new Inventory();
        Score score = new Score(100);

        // flags to track story progress
        boolean toolboxOpened = false;
        boolean engineDoorUnlocked = false;
        boolean fuseTaken = false;
        boolean engineFixed = false;

        // show starting room
        System.out.println(currentRoom.getDescription());

        Scanner sc = new Scanner(System.in);
        boolean playing = true;

        // main command loop
        while (playing) {
            System.out.print(">> ");
            String command = sc.nextLine().trim().toLowerCase();

            // basic commands
            if (command.equals("quit")) {
                System.out.println("Game over");
                playing = false;

            } else if (command.equals("help")) {
                System.out.println("Valid commands: move <dir>, look, look <thing>, inventory, map, score, open toolbox, take note, take fuse, take wrench, use keycard, insert fuse, quit");

            } else if (command.equals("map")) {
                System.out.print(gameMap.display());

            } else if (command.equals("inventory")) {
                System.out.println("You have: " + inventory.displayInventory());

            } else if (command.equals("score")) {
                System.out.println("SCORE: " + score.getScore());

            } else if (command.equals("look")) {
                System.out.println(currentRoom.getDescription());

            } else if (command.startsWith("look ")) {
                // look at objects in room or items in inventory
                String target = command.substring(5);
                if (inventory.hasItem(target) != -1)
                    System.out.println("You look at the " + target + ". It might be useful later.");
                else if (currentRoom == storage && target.equals("toolbox"))
                    System.out.println(toolboxOpened ? "The toolbox is open." : "A metal toolbox. It seems closed.");
                else if (currentRoom == cell && target.equals("note"))
                    System.out.println("The note says: 'Find the keycard in storage.'");
                else
                    System.out.println("You don't see that here.");

            // movement
            } else if (command.startsWith("move ")) {
                String dir = command.substring(5);
                Room newRoom = currentRoom;

                // all movement rules are hard-coded between rooms
                if (currentRoom == cell) {
                    if (dir.equals("south")) newRoom = hallway;
                    else if (dir.equals("east")) newRoom = garden;

                } else if (currentRoom == garden) {
                    if (dir.equals("west")) newRoom = cell;
                    else if (dir.equals("south")) newRoom = control;

                } else if (currentRoom == hallway) {
                    if (dir.equals("north")) newRoom = cell;
                    else if (dir.equals("east")) newRoom = control;
                    else if (dir.equals("south")) newRoom = quarters;

                } else if (currentRoom == control) {
                    if (dir.equals("north")) newRoom = garden;
                    else if (dir.equals("west")) newRoom = hallway;
                    else if (dir.equals("east")) newRoom = storage;
                    else if (dir.equals("south")) newRoom = lab;

                } else if (currentRoom == storage) {
                    if (dir.equals("west")) newRoom = control;
                    else if (dir.equals("south")) newRoom = vent;
                    else if (dir.equals("east")) {
                        // this door is locked until we use keycard in the lab
                        if (engineDoorUnlocked) newRoom = engine;
                        else System.out.println("The door to the east is locked.");
                    }

                } else if (currentRoom == lab) {
                    if (dir.equals("north")) newRoom = control;
                    else if (dir.equals("west")) newRoom = quarters;
                    else if (dir.equals("east")) newRoom = vent;

                } else if (currentRoom == quarters) {
                    if (dir.equals("north")) newRoom = hallway;
                    else if (dir.equals("east")) newRoom = lab;

                } else if (currentRoom == vent) {
                    if (dir.equals("north")) newRoom = storage;
                    else if (dir.equals("west")) newRoom = lab;
                    else if (dir.equals("east")) newRoom = exitRoom;

                } else if (currentRoom == engine) {
                    if (dir.equals("west")) newRoom = storage;

                } else if (currentRoom == exitRoom) {
                    if (dir.equals("west")) newRoom = vent;
                }

                // if movement succeeded, update room and score
                if (newRoom != currentRoom) {
                    currentRoom = newRoom;
                    score.visitRoom();
                    System.out.println(currentRoom.getDescription());
                } else {
                    System.out.println("You cannot go that way.");
                }

            // room-specific actions
            } else if (command.equals("open toolbox")) {
                // only in Storage and only once
                if (currentRoom == storage && !toolboxOpened) {
                    System.out.println("You open the toolbox and find a keycard.");
                    inventory.addItem("keycard");
                    toolboxOpened = true;
                } else {
                    System.out.println("There is no toolbox here or it's already open.");
                }

            } else if (command.equals("take note")) {
                if (currentRoom == cell && inventory.hasItem("note") == -1) {
                    inventory.addItem("note");
                    System.out.println("You take the note.");
                } else {
                    System.out.println("There is no note here.");
                }

            } else if (command.equals("take fuse")) {
                if (currentRoom == quarters && !fuseTaken) {
                    inventory.addItem("fuse");
                    fuseTaken = true;
                    System.out.println("You take the fuse.");
                } else {
                    System.out.println("There is no fuse here.");
                }

            } else if (command.equals("take wrench")) {
                if (currentRoom == lab && inventory.hasItem("wrench") == -1) {
                    inventory.addItem("wrench");
                    System.out.println("You take the wrench.");
                } else {
                    System.out.println("There is no wrench here.");
                }

            } else if (command.equals("use keycard")) {
                // used in the lab to unlock the engine door
                if (currentRoom == lab && inventory.hasItem("keycard") != -1 && !engineDoorUnlocked) {
                    engineDoorUnlocked = true;
                    score.solvePuzzle();
                    System.out.println("You use the keycard. The door to the east of storage is unlocked.");
                } else {
                    System.out.println("You can't use that here.");
                }

            } else if (command.equals("insert fuse")) {
                // final puzzle in the engine room
                if (currentRoom == engine && inventory.hasItem("fuse") != -1 && !engineFixed) {
                    engineFixed = true;
                    score.solvePuzzle();
                    System.out.println("You insert the fuse. The engine powers on.");
                    System.out.println("Congratulations! You have completed your mission and escaped!");
                    System.out.println("Final Score: " + score.getScore());
                    playing = false;
                    System.out.println("Game over.");
                } else {
                    System.out.println("You can't do that.");
                }

            } else {
                // any unknown command
                System.out.println("I don't understand that command.");
            }
        }

        sc.close();
    }
}

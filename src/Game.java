import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {

    int boardSize = 20;
    int numberOfWood = 40;

    int currentPlayerX;
    int currentPlayerY;

    static final String EMPTY = ".";
    static final String PLAYER = "P";
    static final String WOOD = "W";
    static final String TENT = "T";
    static final String DISCOVERED = "X";

    String[][] board;
    Player player;

    public void runGame() {
        this.board = newGame();
        this.player = new Player();

        Scanner scanner = new Scanner(System.in);
        printBoard(board);
        while(player.isAlive) {
            String input = scanner.nextLine();
            switch (input) {
                case "w":
                case "a":
                case "s":
                case "d":
                    movePlayer(input);
                    break;
                case "i":
                    displayInventory();
                    break;
                case "l":
                    displayPlayerLog();
                    break;

            }



//            player.isAlive = false;
        }

    }

    public String[][] newGame() {
        // create the empty board
        String[][] board = new String[boardSize][boardSize];
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                board[x][y] = EMPTY;
            }
        }

        Random r = new Random();
        int low = 0;
        int high = boardSize - 1;
        this.currentPlayerX = r.nextInt(high-low) + low;
        this.currentPlayerY = r.nextInt(high-low) + low;
        board[this.currentPlayerX][this.currentPlayerY] = TENT;

        // place all the wood
        for(int i = numberOfWood; i != 0; i--) {
            int woodX = r.nextInt(high-low) + low;
            int woodY = r.nextInt(high-low) + low;
            board[woodX][woodY] = WOOD;
        }

        return board;
    }

    public void movePlayer(String direction) {
        String currentSpot = this.board[this.currentPlayerX][this.currentPlayerY];
        if (!currentSpot.equals(TENT)) {
            this.board[this.currentPlayerX][this.currentPlayerY] = DISCOVERED;
        }

        movePlayerDirection(direction);
        currentSpot = this.board[this.currentPlayerX][this.currentPlayerY];

        this.player.moves -= 1;
        if(!currentSpot.equals(EMPTY) && !currentSpot.equals(DISCOVERED)) {
            checkSpot(this.board[this.currentPlayerX][this.currentPlayerY]);
        } else {
            this.board[this.currentPlayerX][this.currentPlayerY] = PLAYER;
            printBoard(this.board);
//            System.out.println("current location: " + currentPlayerX + "," + currentPlayerY);
        }
        if(this.player.moves == 0) {
            this.player.isAlive = false;
            String logMessage = "you starved to death";
            player.playerLog.add(logMessage);
        }
        System.out.println("you have " + player.moves + " steps left");
    }

    public void checkSpot(String spot) {
        this.board[this.currentPlayerX][this.currentPlayerY] = PLAYER;
        printBoard(this.board);
//        System.out.println("current location: " + currentPlayerX + "," + currentPlayerY);

        String logMessage = "";
        switch (spot) {
            case WOOD:
                player.woodCount += 1;
                logMessage = "you picked up 1 wood";
                System.out.println(logMessage);
                break;
            case TENT:
                logMessage = "you made it back to the tent";
                System.out.println(logMessage);
                this.player.moves = 10;
                break;

        }
        if(!logMessage.equals("")) {
            player.playerLog.add(logMessage);
        }
    }

    public void movePlayerDirection(String direction) {
        switch(direction) {
            case "d":
                this.currentPlayerY += 1;
                break;
            case "w":
                this.currentPlayerX -= 1;
                break;
            case "a":
                this.currentPlayerY -= 1;
                break;
            case "s":
                this.currentPlayerX += 1;
                break;
        }
    }

    public void displayInventory() {
        System.out.println("wood:" + player.woodCount);
    }

    public void displayPlayerLog() {
        int i = 1;
        for(String logMessage : player.playerLog) {
            System.out.println(i + ". " + logMessage);
            i += 1;
        }
    }

    public void printBoard(String[][] board) {
        for (int x = 0; x < boardSize; x++) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int y = 0; y < boardSize; y++) {
                stringBuilder.append(board[x][y]).append(" ");
            }
            System.out.println(stringBuilder.toString());
        }
    }
}

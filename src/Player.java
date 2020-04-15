import java.util.ArrayList;
import java.util.List;

public class Player {

    Boolean isAlive = true;
    int lives = 1;
    int moves = 10;

    int currentX;
    int currentY;

    int woodCount;
    List<String> playerLog = new ArrayList();
}

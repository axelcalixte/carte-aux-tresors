package fr.axelcalixte;

import fr.axelcalixte.game.Game;
import fr.axelcalixte.game.Position;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {
  @Test
  void integrationTest() {
    Game gameStart = new Game();
    gameStart.play();

    Game gameEnd = new Game();
    gameEnd.getMap().setTreasures(new HashMap<>());
    gameEnd.getMap().getTreasures().put(new Position(1, 3), 2);
    gameEnd.getAdventurers().get(0).setPosition(new Position(0, 3));
    gameEnd.getAdventurers().get(0).setNbTreasures(3);
    gameEnd.getAdventurers().get(0).setMoves(new LinkedList<>());
    Assertions.assertEquals(gameEnd, gameStart);
  }
}

package fr.axelcalixte.game;

import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdventurerTest {

  private Game game;

  @BeforeEach
  void setup() {
    this.game = new Game();
    this.game.setAdventurers(new ArrayList<>());
    this.game.getMap().setMountains(new HashSet<>());
    this.game
        .getAdventurers()
        .add(
            Adventurer.builder()
                .name("Lara")
                .position(new Position(1, 1))
                .orientation(Orientation.SOUTH)
                .moves(new LinkedList<>(List.of('A')))
                .map(this.game.getMap())
                .build());
  }

  @Test
  void shouldAdvanceInXLookingAtSouth() {
    var expectedPosition = new Position(1, 2);
    Adventurer Lara = this.game.getAdventurers().get(0);
    Lara.setOrientation(Orientation.SOUTH);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldAdvanceInXLookingAtNorth() {
    var expectedPosition = new Position(1, 0);
    Adventurer Lara = this.game.getAdventurers().get(0);
    Lara.setOrientation(Orientation.NORTH);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldAdvanceInYLookingAtWest() {
    var expectedPosition = new Position(0, 1);
    Adventurer Lara = this.game.getAdventurers().get(0);
    Lara.setOrientation(Orientation.WEST);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldAdvanceInYLookingAtEast() {
    var expectedPosition = new Position(2, 1);
    Adventurer Lara = this.game.getAdventurers().get(0);
    Lara.setOrientation(Orientation.EAST);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldNotMoveBeforeMountainAtSouth() {
    var expectedPosition = new Position(1, 1);
    this.game.getMap().getMountains().add(new Position(1, 2));
    Adventurer Lara = game.getAdventurers().get(0);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldNotMoveBeforeMountainAtNorth() {
    var expectedPosition = new Position(1, 1);
    this.game.getMap().getMountains().add(new Position(1, 0));
    Adventurer Lara = game.getAdventurers().get(0);
    Lara.setOrientation(Orientation.NORTH);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldNotMoveBeforeMountainAtEast() {
    var expectedPosition = new Position(1, 1);
    this.game.getMap().getMountains().add(new Position(2, 1));
    Adventurer Lara = game.getAdventurers().get(0);
    Lara.setOrientation(Orientation.EAST);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldNotMoveBeforeMountainAtWest() {
    var expectedPosition = new Position(1, 1);
    this.game.getMap().getMountains().add(new Position(0, 1));
    Adventurer Lara = game.getAdventurers().get(0);
    Lara.setOrientation(Orientation.WEST);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldMoveWithMountainToDiagonal() {
    var expectedPosition = new Position(1, 2);
    this.game.getMap().setMountains(new HashSet<>());
    this.game.getMap().getMountains().add(new Position(2, 2));
    Adventurer Lara = game.getAdventurers().get(0);
    Lara.setOrientation(Orientation.SOUTH);
    Lara.walk();
    Assertions.assertEquals(expectedPosition, Lara.getPosition());
  }

  @Test
  void shouldCollectTreasure() {
    var testMap = this.game.getMap();
    var Lara = game.getAdventurers().get(0);
    testMap.setTreasures(new HashMap<>());
    testMap.getTreasures().put(new Position(1, 2), 1);
    Lara.walk();
    Assertions.assertEquals(0, testMap.getTreasures().size());
    Assertions.assertEquals(1, Lara.getNbTreasures());
  }

  @Test
  void shouldNotCollectATreasureWithoutMoving() {
    var testMap = this.game.getMap();
    var Lara = game.getAdventurers().get(0);
    testMap.setTreasures(new HashMap<>());
    testMap.getTreasures().put(new Position(1, 2), 2);
    testMap.setMountains(new HashSet<>());
    testMap.getMountains().add(new Position(1, 3));
    Lara.walk();
    Lara.walk();
    Assertions.assertEquals(1, testMap.getTreasures().size());
    Assertions.assertEquals(1, Lara.getNbTreasures());
  }

  @Test
  void shouldTurnLeft() {
    var Lara = game.getAdventurers().get(0);

    Lara.setOrientation(Orientation.SOUTH);
    Lara.changeOrientation('G');
    Assertions.assertEquals(Orientation.EAST, Lara.getOrientation());

    Lara.setOrientation(Orientation.EAST);
    Lara.changeOrientation('G');
    Assertions.assertEquals(Orientation.NORTH, Lara.getOrientation());
  }

  @Test
  void shouldTurnRight() {
    var Lara = game.getAdventurers().get(0);

    Lara.setOrientation(Orientation.WEST);
    Lara.changeOrientation('D');
    Assertions.assertEquals(Orientation.NORTH, Lara.getOrientation());

    Lara.setOrientation(Orientation.NORTH);
    Lara.changeOrientation('D');
    Assertions.assertEquals(Orientation.EAST, Lara.getOrientation());
  }
}

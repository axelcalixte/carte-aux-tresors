package fr.axelcalixte.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameTest {

  private final Game game = new Game();

  @Test
  void shouldReadGameInputFile() {
    Assertions.assertNotNull(this.game.getInput());
  }

  @Test
  void shouldHaveRectangularMapAndSameBoundariesAsInput() {
    Assertions.assertNotEquals(
        game.getMap().getBoundaries().getX(), game.getMap().getBoundaries().getY());
    Assertions.assertEquals(3, game.getMap().getBoundaries().getX());
    Assertions.assertEquals(4, game.getMap().getBoundaries().getY());
  }

  @Test
  void shouldHaveTwoMountainsAndSamePositionsAsInput() {
    Assertions.assertEquals(2, game.getMap().getMountains().size());
    Assertions.assertTrue(game.getMap().getMountains().contains(new Position(1, 0)));
    Assertions.assertTrue(game.getMap().getMountains().contains(new Position(2, 1)));
  }

  @Test
  void shouldHaveFiveTreasuresAndSameTreasuresAsInput() {
    Assertions.assertEquals(
        5, game.getMap().getTreasures().values().stream().mapToInt(Integer::valueOf).sum());
    Assertions.assertEquals(
        Map.of(new Position(0, 3), 2, new Position(1, 3), 3), game.getMap().getTreasures());
  }

  @Test
  void shouldHaveLaraAsAdventurer() {
    var Lara =
        Adventurer.builder()
            .name("Lara")
            .position(new Position(1, 1))
            .orientation(Orientation.SOUTH)
            .moves(new LinkedList<>(List.of('A', 'A', 'D', 'A', 'D', 'A', 'G', 'G', 'A')))
            .build();

    Assertions.assertEquals(1, game.getAdventurers().size());
    Assertions.assertEquals(Lara.getName(), game.getAdventurers().get(0).getName());
    Assertions.assertEquals(Lara.getPosition(), game.getAdventurers().get(0).getPosition());
    Assertions.assertEquals(Lara.getOrientation(), game.getAdventurers().get(0).getOrientation());
    Assertions.assertTrue(Lara.getMoves().containsAll(game.getAdventurers().get(0).getMoves()));
  }
}

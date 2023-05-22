package fr.axelcalixte.game;

import java.util.Queue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Adventurer {
  private String name;
  private Queue<Character> moves;
  private Position position;
  private Orientation orientation;
  private int nbTreasures;
  private GameMap map;

  public void walk() {
    boolean canPickUpTreasure = false;
    switch (this.orientation) {
      case NORTH -> {
        if (this.position.getY() - 1 >= 0
            && this.map.getMountains().stream()
                .noneMatch(
                    mountain ->
                        (mountain.getY() == this.position.getY() - 1)
                            && (mountain.getX() == this.position.getX()))) {
          this.position.setY(this.position.getY() - 1);
          canPickUpTreasure = true;
        }
      }
      case SOUTH -> {
        if (this.position.getY() + 1 < this.getMap().getBoundaries().getY()
            && this.map.getMountains().stream()
                .noneMatch(
                    mountain ->
                        (mountain.getY() == this.position.getY() + 1)
                            && (mountain.getX() == this.position.getX()))) {
          this.position.setY(this.position.getY() + 1);
          canPickUpTreasure = true;
        }
      }
      case EAST -> {
        if (this.position.getX() + 1 < this.getMap().getBoundaries().getX()
            && this.map.getMountains().stream()
                .noneMatch(
                    mountain ->
                        (mountain.getX() == this.position.getX() + 1)
                            && (mountain.getY() == this.position.getY()))) {
          this.position.setX(this.position.getX() + 1);
          canPickUpTreasure = true;
        }
      }
      case WEST -> {
        if (this.position.getX() - 1 >= 0
            && this.map.getMountains().stream()
                .noneMatch(
                    mountain ->
                        (mountain.getX() == this.position.getX() - 1)
                            && (mountain.getY() == this.position.getY()))) {
          this.position.setX(this.position.getX() - 1);
          canPickUpTreasure = true;
        }
      }
    }
    this.pickUpTreasure(canPickUpTreasure);
  }

  private void pickUpTreasure(boolean canPickUp) {
    if (!canPickUp) {
      return;
    }
    var treasures = this.getMap().getTreasures();
    var toBeRemoved =
        treasures.keySet().stream().filter(treasure -> treasure.equals(this.position)).findFirst();
    if (toBeRemoved.isPresent()) {
      treasures.put(toBeRemoved.get(), this.getMap().getTreasures().get(toBeRemoved.get()) - 1);
      this.nbTreasures++;
      if (treasures.get(toBeRemoved.get()) == 0) {
        treasures.remove(toBeRemoved.get());
      }
    }
  }

  public void changeOrientation(Character move) {
    int newOrientationValue;
    switch (move) {
      case 'G' -> {
        // Shifting to the right turns the adventurer left.
        newOrientationValue = this.orientation.getIntValue() >> 1;
        if (newOrientationValue == 0) {
          newOrientationValue = 8;
        }
      }
      case 'D' -> {
        // Shifting to the left turns the adventurer right.
        newOrientationValue = this.orientation.getIntValue() << 1;
        if (newOrientationValue == 16) {
          newOrientationValue = 1;
        }
      }
      default -> throw new IllegalStateException("Unexpected enum value: " + move);
    }
    // Shifting to the right turns the adventurer left.
    for (Orientation newOrientation : Orientation.values()) {
      if (newOrientationValue == newOrientation.getIntValue()) {
        this.orientation = newOrientation;
      }
    }
  }

  public void playTurn() {
    var move = this.moves.remove();
    switch (move) {
      case 'A' -> walk();
      case 'G' -> changeOrientation(move);
      case 'D' -> changeOrientation(move);
      default -> throw new IllegalStateException("Unexpected move value: " + move);
    }
  }
}

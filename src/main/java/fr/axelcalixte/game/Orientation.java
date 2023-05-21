package fr.axelcalixte.game;

import lombok.Getter;

public enum Orientation {
  NORTH(8),
  SOUTH(2),
  EAST(1),
  WEST(4);

  @Getter private final int intValue;

  Orientation(int i) {
    this.intValue = i;
  }

  public static Orientation chooseOrientation(String o) throws IllegalStateException {
    return switch (o) {
      case "N" -> Orientation.NORTH;
      case "S" -> Orientation.SOUTH;
      case "E" -> Orientation.EAST;
      case "O" -> Orientation.WEST;
      default -> throw new IllegalStateException("Unexpected enum value: " + o);
    };
  }
}

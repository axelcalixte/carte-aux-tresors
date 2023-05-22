package fr.axelcalixte.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Game {

  private final List<String> input;
  private GameMap map;
  private List<Adventurer> adventurers;

  public Game() {
    this.input = readInput();
    this.generateGame();
  }

  private void generateGame() {
    this.map =
        GameMap.builder()
            .boundaries(parseInputForBoundaries())
            .mountains(parseInputForMountains())
            .treasures(parseInputForTreasures())
            .build();
    this.adventurers = parseInputForAdventurers();
  }

  private List<Adventurer> parseInputForAdventurers() {
    return this.input.stream()
        .filter(line -> line.startsWith("A"))
        .map(
            line -> {
              try (var s = new Scanner(line).useDelimiter(" - ").skip("A")) {
                return Adventurer.builder()
                    .name(s.next())
                    .position(new Position(s.nextInt(), s.nextInt()))
                    .orientation(Orientation.chooseOrientation(s.next()))
                    .moves(new LinkedList<>(s.next().chars().mapToObj(e -> (char) e).toList()))
                    .map(this.map)
                    .build();
              }
            })
        .toList();
  }

  private Map<Position, Integer> parseInputForTreasures() {
    var treasureLines = this.input.stream().filter(line -> line.startsWith("T")).toList();
    Map<Position, Integer> temp = new HashMap<>();
    for (String line : treasureLines) {
      try (var s = new Scanner(line).useDelimiter(" - ").skip("T")) {
        var pos = new Position(s.nextInt(), s.nextInt());
        var count = s.nextInt();
        temp.put(pos, count);
      }
    }
    return temp;
  }

  private Set<Position> parseInputForMountains() {
    return this.input.stream()
        .filter(line -> line.startsWith("M"))
        .map(
            line -> {
              try (Scanner s = new Scanner(line).useDelimiter(" - ").skip("M")) {
                return new Position(s.nextInt(), s.nextInt());
              }
            })
        .collect(Collectors.toSet());
  }

  private Position parseInputForBoundaries() {
    try (Scanner s = new Scanner(input.get(0)).useDelimiter(" - ").skip("C")) {
      return new Position(s.nextInt(), s.nextInt());
    }
  }

  private List<String> readInput() {
    List<String> textInput;
    try {
      textInput = Files.readAllLines(Path.of("src/main/resources/input.txt"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return textInput;
  }

  public void play() {
    int nbTurns = this.getAdventurers().get(0).getMoves().size();
    for (int i = 0; i < nbTurns; i++) {
      for (int j = 0; j < this.getAdventurers().size(); j++) {
        this.getAdventurers().get(j).playTurn();
      }
    }
    writeResultsToFile();
  }

  private void writeResultsToFile() {
    var outputFile = Path.of("src/main/resources/my-output.txt");
    if (!Files.exists(outputFile)) {
      try {
        Files.createFile(outputFile);
      } catch (IOException e) {
        throw new RuntimeException();
      }
    }
    try (var writer = Files.newBufferedWriter(outputFile)) {
      writer.write(
          "C - "
              + this.getMap().getBoundaries().getX()
              + " - "
              + this.getMap().getBoundaries().getY());
      writer.newLine();
      for (Position mountain : this.getMap().getMountains()) {
        writer.write("M - " + mountain.getX() + " - " + mountain.getY());
        writer.newLine();
      }

      for (Position treasure : this.getMap().getTreasures().keySet()) {
        writer.write(
            "T - "
                + treasure.getX()
                + " - "
                + treasure.getY()
                + " - "
                + this.getMap().getTreasures().get(treasure));
        writer.newLine();
      }
      for (Adventurer adventurer : this.getAdventurers()) {
        writer.write(
            "T - "
                + adventurer.getName()
                + " - "
                + adventurer.getPosition().getX()
                + " - "
                + adventurer.getPosition().getY()
                + " - "
                + adventurer.getOrientation().name().charAt(0)
                + " - "
                + adventurer.getNbTreasures());
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<String> getInput() {
    return this.input;
  }

  @Override
  public String toString() {
    return "Game{" + "\ninput=" + input + "\n, map=" + map + "\n, adventurers=" + adventurers + '}';
  }
}

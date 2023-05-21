package fr.axelcalixte.game;

import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameMap {
  private Set<Position> mountains;
  private Map<Position, Integer> treasures;
  private Position boundaries;
}

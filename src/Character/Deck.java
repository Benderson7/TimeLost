package Character;

import java.util.Random;

public interface Deck {
  int BIGMAX = 9;
  int SMALLMAX = 4;

  static int Draw(int max, int boostLimit) {
    int output = 0;
    int boostCounter = 0;
    boolean boosted = true;
    int realMax;
    if (max < 1) { realMax = 1; } else { realMax = max; }
    while (boosted && (boostCounter < boostLimit)) {

      int addition = new Random().nextInt(realMax) + 1;
      output += addition;
      System.out.println("Adding " + addition + " to outcome");

      if (new Random().nextInt(Stat.values().length) == 0) {
        boosted = true;
        boostCounter++;
      } else {
        boosted = false;
      }
    }
    return output;
  }
}

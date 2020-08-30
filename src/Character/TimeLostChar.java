package Character;

import java.util.Random;

public class TimeLostChar {

  public static int BIGDECK = 9;
  public static int SMALLDECK = 4;

  private String name;
  private int health;
  private int maxHealth;
  private int LVL;
  private int focus;
  private int maxFocus;
  private int body;
  private int mind;
  private int soul;
  private int essence;
  private TimeLostItem[] inventory;

  public String getName() { return name; }
  public void setName(String text) {
    name = text;
  }

  public int getHealth() {
    return health;
  }
  public void setHealth(int value) {
    health = value;
  }

  public int getMaxHealth() {
    return maxHealth;
  }
  public void setMaxHealth(int value) {
    maxHealth = value;
  }

  public int getLVL() {
    return LVL;
  }
  public void setLVL(int value) {
    LVL = value;
  }

  public int getFocus() { return focus; }
  public void setFocus(int value) {
    focus = value;
  }

  public int getMaxFocus() { return maxFocus; }
  public void setMaxFocus(int value) {
    maxFocus = value;
  }

  public int getBody() { return body; }
  public void setBody(int selectedButton) {
    body = selectedButton;
  }

  public int getMind() {return mind; }
  public void setMind(int selectedButton) {
    mind = selectedButton;
  }

  public int getSoul() { return soul; }
  public void setSoul(int selectedButton) {
    soul = selectedButton;
  }

  public int getEssence() {return essence; }
  public void setEssence(int selectedButton) {
    essence = selectedButton;
  }

  public TimeLostItem[] getInventory() { return inventory; }
  public void setInventory(TimeLostItem[] inventory) { this.inventory = inventory; }

  public static String action(int boostLimit, int xp, int maxValue) {
    // result of the first draw
    int initialResult = 0;

    // how many times we have boosted
    // string and int representation of the sum of boosts
    // and whether we have started boosting
    int boostCount = 0;
    int boostAcc = 0;
    String boostStringAcc = "";
    boolean boosting = false;

    // ensure deck is valid size
    int deckSize = Math.max(maxValue, 1);

    // ensure boost Limit is valid value
    int maxBoosts = Math.max(boostLimit, 1);

    boolean drawsLeft = true;
    while (drawsLeft) {

      // draw card, add its value to the running total
      int addition = new Random().nextInt(deckSize) + 1;
      if (boosting) {
        boostAcc += addition;
        boostStringAcc += " + " + addition;
      } else {
        initialResult = addition;
        boosting = true;
      }
      drawsLeft = false;

      // if we haven't run out of boosts, then check for it we should boost
      // p(boost) = 1 / # of stats in Character.Stat enum
      if (boostCount < maxBoosts && new Random().nextInt(Stat.values().length) == 0) {
        deckSize = SMALLDECK;
        boostCount++;
        drawsLeft = true;
      }
    }
    // TODO
    int itemsUsedSum = 0;

    // clean up the Boost String builder
    if (boostStringAcc.isEmpty()) {
      boostStringAcc = "0";
    }
    else {
      boostStringAcc = boostStringAcc.substring(3);
    }

    // Returns result as a cleaned up string, of the format:
    // "Result: (Initial Value: x) + (Boosts: y) + (Stat XP: z) + (Items Used Bonus: w) = v"
    // where v = x + y + z + w
    return "Result = (Initial Value: " + initialResult
        + ") + (Boosts: " + boostStringAcc
        + ") + (Stat XP: " + xp
        + ") + (Item Used Bonus: " + itemsUsedSum
        + ") = " + (initialResult + boostAcc + xp + itemsUsedSum);
  }
}

package Character;

public class TLCharacter {
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
}

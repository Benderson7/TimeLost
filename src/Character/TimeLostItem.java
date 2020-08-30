package Character;

import java.util.Objects;

public class TimeLostItem {
  private String name;
  private String description;
  private String stat;
  private String modifier;

  public String getName() { return name; }
  public void setName(String name) {
    this.name = Objects.requireNonNullElse(name, "");
  }
  public String getDescription() { return description; }
  public void setDescription(String description) {
    this.description = Objects.requireNonNullElse(description, "");
  }
  public String getStat() { return stat; }
  public void setStat(String stat) {
    this.stat = Objects.requireNonNullElse(stat, "");
  }
  public String getModifier() { return modifier; }
  public void setModifier(String modifier) {
    this.modifier = Objects.requireNonNullElse(modifier, "");
  }
}

package GUI;

import javax.swing.JButton;
import javax.swing.JPanel;

// Represents the Statchooser window, where the user chooses the stat for their attack
public class StatChooser {

  public JPanel rootPanel;
  private JButton bodyButton;
  private JButton soulButton;
  private JButton mindButton;
  private JButton essenceButton;

  // Tell the given TimeLost object what stat was chosen, and closes itself
  public StatChooser(TimeLost timelost) {
    bodyButton.addActionListener(e -> {
      timelost.attack("Body");
      timelost.closeStatChooser();
    });
    mindButton.addActionListener(e -> {
      timelost.attack("Mind");
      timelost.closeStatChooser();
    });
    soulButton.addActionListener(e -> {
      timelost.attack("Soul");
      timelost.closeStatChooser();
    });
    essenceButton.addActionListener(e -> {
      timelost.attack("Essence");
      timelost.closeStatChooser();
    });
  }
}
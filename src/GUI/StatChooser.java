package GUI;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StatChooser {

  public JPanel rootPanel;
  private JButton bodyButton;
  private JButton soulButton;
  private JButton mindButton;
  private JButton essenceButton;

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
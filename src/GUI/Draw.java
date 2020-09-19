package GUI;

import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import Character.Stat;

// Represents the Draw window, where the user chooses a max value for the draw
public class Draw {

  private JSpinner value;
  private JButton drawButton;
  public JPanel rootPanel;

  // Is given the TimeLost upon creation so that it can tell it what the user set the maxValue to
  public Draw(TimeLost tl) {

    // Tells the TimeLost what the outcome of the draw was
    drawButton.addActionListener(e -> {
      Stat[] stats = new Stat[] {Stat.BODY, Stat.MIND, Stat.SOUL, Stat.ESSENCE};
      Stat stat = stats[new Random().nextInt(4)];
      tl.BattleOutcome.setText("Stat: " + stat.toString() + "\nResult: " + (new Random().nextInt(Math.max((int)value.getValue(), 1)) + 1));
    });
  }
}

package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import Character.Deck;
import Character.Stat;

public class Draw {

  private JSpinner value;
  private JLabel Instructions;
  private JButton drawButton;
  public JPanel rootPanel;

  public Draw(TimeLost tl) {
    TimeLost timelost = tl;

    drawButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int outcome = Deck.Draw((int)value.getValue(), 1);
        Stat[] stats = new Stat[] {Stat.BODY, Stat.MIND, Stat.SOUL, Stat.ESSENCE };
        Stat stat = stats[new Random().nextInt(4)];
        timelost.BattleOutcome.setText("Outcome: " + outcome + " " + stat.toString() + "\n");
      }
    });
  }
}

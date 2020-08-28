package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import Character.Stat;

public class StatChooser {

  private JRadioButton bodyRadioButton;
  private JRadioButton mindRadioButton;
  private JRadioButton soulRadioButton;
  private JRadioButton essenceRadioButton;
  private JButton commitButton;
  public JPanel rootPanel;
  private ButtonGroup StatsGroup;

  public StatChooser(TimeLost timelost) {
    TimeLost tl = timelost;

    commitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        tl.calculateAttack(getStat());
        StatChooser.this.rootPanel.setVisible(false);
      }
    });
  }

  private String getStat() {
    Enumeration<AbstractButton> buttons = StatsGroup.getElements();
    for (int i = 0; i < 4; i++) {
      AbstractButton current = buttons.nextElement();
      if (current.isSelected()) {
        return current.getText();
        }
      }
    return null;
  }
}
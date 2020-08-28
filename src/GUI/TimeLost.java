package GUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Scanner;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import Character.TimeLostChar;
import Character.Stat;

public class TimeLost {

  private JPanel rootPanel;
  private JTextField CharacterName;
  private JSpinner CharacterLVL;
  private JSpinner CharacterHP;
  private JSpinner CharacterFocus;
  private JSpinner CharacterMaxHP;
  private JSpinner CharacterMaxFocus;
  private JLabel Name;
  private JLabel LVL;
  private JLabel HP;
  private JLabel Focus;
  private JLabel MaxHP;
  private JLabel MaxFocus;
  private JPanel BasicInfo;
  private JPanel FocusHP;
  private JPanel NameLVL;
  private JRadioButton Body1;
  private JRadioButton Body2;
  private JRadioButton Body3;
  private JRadioButton Body4;
  private JRadioButton Mind1;
  private JRadioButton Mind2;
  private JRadioButton Mind3;
  private JRadioButton Mind4;
  private JRadioButton Soul1;
  private JRadioButton Essence1;
  private JRadioButton Soul3;
  private JRadioButton Soul2;
  private JRadioButton Soul4;
  private JRadioButton Essence4;
  private JRadioButton Essence3;
  private JRadioButton Essence2;
  private JLabel BodyStat;
  private JLabel MindStat;
  private JLabel SoulStat;
  private JLabel EssenceStat;
  private JButton Attack;
  private JButton Defend;
  private JButton Draw;
  private JButton Counter;
  private JPanel Stats;
  private JPanel AttackPanel;
  private JButton Save;
  public JTextPane BattleOutcome;
  private JPanel NotAttackPanel;
  private JPanel SavePanel;
  private JButton loadButton;
  private JSpinner DrawValue;
  private ButtonGroup EssenceGroup;
  private ButtonGroup SoulGroup;
  private ButtonGroup MindGroup;
  private ButtonGroup BodyGroup;

  private JFrame statChooser;
  private JFrame draw;

  public TimeLost() {
    Save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        save();
      }
    });
    Attack.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openStatChooser();
      }
    });
    Defend.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        defend();
      }
    });
    Counter.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        counter();
      }
    });
    Draw.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openDrawMenu();
      }
    });
    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        load();
      }
    });
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("TimeLost");
    TimeLost tl = new TimeLost();
    frame.setContentPane(tl.rootPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private void openDrawMenu() {
    draw = new JFrame("Choose Max Value");
    draw.setContentPane(new Draw(TimeLost.this).rootPanel);
    draw.pack();
    draw.setVisible(true);
  }

  private void openStatChooser() {
    statChooser = new JFrame("Choose Stat");
    statChooser.setContentPane(new StatChooser(TimeLost.this).rootPanel);
    statChooser.pack();
    statChooser.setVisible(true);
  }

  public void closeStatChooser() {
    statChooser.setVisible(false);
  }

  private void counter() {
    returnOutcome(BodyGroup, Stat.BODY, TimeLostChar.SMALLDECK);
  }

  private void defend() {
    returnOutcome(MindGroup, Stat.MIND, TimeLostChar.SMALLDECK);
  }

  public void attack(String s) {
    ButtonGroup group;
    Stat stat;

    // Determine which stat we care about
    // (currently based on the selection made in StatChooser window)
    // am aware this is non-ideal hardcoding,
    // but it would unnecessarily complicate something that is fairly invariable
    switch (s) {
      case "Body":
        group = BodyGroup;
        stat = Stat.BODY;
        break;
      case "Mind":
        group = MindGroup;
        stat = Stat.MIND;
        break;
      case "Soul":
        group = SoulGroup;
        stat = Stat.SOUL;
        break;
      default:
        group = EssenceGroup;
        stat = Stat.ESSENCE;
        break;
    }
    returnOutcome(group, stat, TimeLostChar.BIGDECK);
  }

  private void returnOutcome(ButtonGroup group, Stat stat, int bigdeck) {
    BattleOutcome.setText("Stat: " + stat + "\n" + TimeLostChar
        .action((int) CharacterLVL.getValue(), getSelectedButton(group), bigdeck));
  }

  private void save() {
    // configure jfc
    JFileChooser jfc = new JFileChooser();
    jfc.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
    jfc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));

    if (jfc.showSaveDialog(rootPanel) == JFileChooser.APPROVE_OPTION) {
      File file = jfc.getSelectedFile();

      if (!file.getName().endsWith(".json")) {
        file = new File(file + ".json");
      }
      try {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        TimeLostChar toSave = buildCharacterFromGUI();
        String str = gson.toJson(toSave);
        FileWriter writer = new FileWriter(file);
        writer.write(str);
        writer.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void load() {
    // configure jfc
    JFileChooser jfc = new JFileChooser();
    jfc.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
    jfc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));

    if (jfc.showOpenDialog(rootPanel) == JFileChooser.APPROVE_OPTION) {
      File file = jfc.getSelectedFile();
      try {
        // convert file into a string
        Scanner scanner = new Scanner(file);
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
          stringBuilder.append(scanner.nextLine());
        }

        // convert string into the class
        Gson gson = new Gson();
        TimeLostChar loaded = gson.fromJson(stringBuilder.toString(), TimeLostChar.class);
        buildGUIFromCharacter(loaded);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private TimeLostChar buildCharacterFromGUI() {
    TimeLostChar output = new TimeLostChar();

    String name;
    try {
      name = CharacterName.getText();
    } catch (Exception e) {
      name = "";
    }
    output.setName(name);
    output.setHealth(parseSpinner(CharacterHP));
    output.setMaxHealth(parseSpinner(CharacterMaxHP));
    output.setLVL(parseSpinner(CharacterLVL));
    output.setFocus(parseSpinner(CharacterFocus));
    output.setMaxFocus(parseSpinner(CharacterMaxFocus));
    output.setBody(getSelectedButton(BodyGroup));
    output.setMind(getSelectedButton(MindGroup));
    output.setSoul(getSelectedButton(SoulGroup));
    output.setEssence(getSelectedButton(EssenceGroup));

    return output;
  }

  private int parseSpinner(JSpinner spinner) {
    try {
      return (int)spinner.getValue();
    } catch (Exception e) {
      return 0;
    }
  }

  private void buildGUIFromCharacter(TimeLostChar character) {
    CharacterName.setText(character.getName());
    CharacterLVL.setValue(character.getLVL());
    CharacterHP.setValue(character.getHealth());
    CharacterMaxHP.setValue(character.getMaxHealth());
    CharacterFocus.setValue(character.getFocus());
    CharacterMaxFocus.setValue(character.getMaxFocus());
    setStatRadioButton(
        BodyGroup,
        character.getBody(),
        new JRadioButton[]{Body1, Body2, Body3, Body4 });
    setStatRadioButton(
        MindGroup,
        character.getMind(),
        new JRadioButton[]{Mind1, Mind2, Mind3, Mind4 });
    setStatRadioButton(
        SoulGroup,
        character.getSoul(),
        new JRadioButton[]{Soul1, Soul2, Soul3, Soul4 });
    setStatRadioButton(
        EssenceGroup,
        character.getEssence(),
        new JRadioButton[]{Essence1, Essence2, Essence3, Essence4 });
  }

  private void setStatRadioButton(ButtonGroup group, int index, JRadioButton[] buttons) {
    group.setSelected(buttons[index].getModel(), true);
  }

  private int getSelectedButton(ButtonGroup group) {
    int output = 0;
    Enumeration<AbstractButton> buttons = group.getElements();
    for (int i = 0; i < 4; i++) {
      AbstractButton current = buttons.nextElement();
      if (current.isSelected()) {
        String num = current.getText(); //getName().charAt(current.getName().length() - 1);
        output = Integer.parseInt(num);
      }
    }
    return output;
  }
}
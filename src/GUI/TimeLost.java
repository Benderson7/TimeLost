package GUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import Character.TimeLostChar;
import Character.TimeLostItem;
import Character.Stat;
import javax.swing.table.DefaultTableModel;

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
  private JButton Save;
  public JTextPane BattleOutcome;
  private JPanel SavePanel;
  private JButton loadButton;
  private JButton SaveAs;
  private JButton New;
  private JTabbedPane tabbedPane1;
  private JPanel CharacterPane;
  private JPanel InventoryPane;
  private JButton AddRow;
  private JPanel TableButtonPanel;
  private JTable InventoryTable;
  private JScrollPane TablePane;
  private JPanel ActionsPanel;
  private ButtonGroup EssenceGroup;
  private ButtonGroup SoulGroup;
  private ButtonGroup MindGroup;
  private ButtonGroup BodyGroup;

  private JFrame statChooser;
  private JFrame draw;
  private DefaultTableModel inventoryTableModel;

  private static JFrame mainFrame;
  private File currentFile;
  private static String defaultName = "unnamed_character";
  private static int defaultTableSize = 14;

  public TimeLost() {
    addListeners();
    setupInventory();
  }

  private void setupInventory() {
    inventoryTableModel = new DefaultTableModel();
    inventoryTableModel.addColumn("Name");
    inventoryTableModel.addColumn("Description");
    inventoryTableModel.addColumn("Stat");
    inventoryTableModel.addColumn("Modifier");
    InventoryTable.getTableHeader().setReorderingAllowed(false);
    inventoryTableModel.setRowCount(defaultTableSize);
    InventoryTable.setModel(inventoryTableModel);
  }

  public static void main(String[] args) {
    mainFrame = new JFrame(defaultName);
    TimeLost tl = new TimeLost();
    mainFrame.setContentPane(tl.rootPanel);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.pack();
    mainFrame.setResizable(false);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setVisible(true);
  }

  private void addListeners() {

    // these listeners literally just see if something in the GUI has changed, to add the * to the
    // frame title. If I know how to make this less abysmal i would
    ChangeListener changeListener = e -> mainFrame.setTitle(appendAsterisks(mainFrame.getTitle()));
    ActionListener actionListener = e -> mainFrame.setTitle(appendAsterisks(mainFrame.getTitle()));
    DocumentListener docChangeListener = new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        mainFrame.setTitle(appendAsterisks(mainFrame.getTitle()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        mainFrame.setTitle(appendAsterisks(mainFrame.getTitle()));
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        mainFrame.setTitle(appendAsterisks(mainFrame.getTitle()));
      }
    };
    CharacterName.getDocument().addDocumentListener(docChangeListener);
    CharacterHP.addChangeListener(changeListener);
    CharacterMaxHP.addChangeListener(changeListener);
    CharacterLVL.addChangeListener(changeListener);
    CharacterFocus.addChangeListener(changeListener);
    CharacterMaxFocus.addChangeListener(changeListener);
    Body1.addActionListener(actionListener);
    Body2.addActionListener(actionListener);
    Body3.addActionListener(actionListener);
    Body4.addActionListener(actionListener);
    Mind1.addActionListener(actionListener);
    Mind2.addActionListener(actionListener);
    Mind3.addActionListener(actionListener);
    Mind4.addActionListener(actionListener);
    Soul1.addActionListener(actionListener);
    Soul2.addActionListener(actionListener);
    Soul3.addActionListener(actionListener);
    Soul4.addActionListener(actionListener);
    Essence1.addActionListener(actionListener);
    Essence2.addActionListener(actionListener);
    Essence3.addActionListener(actionListener);
    Essence4.addActionListener(actionListener);

    // actual functional listeners
    Attack.addActionListener(e -> openStatChooser());
    Defend.addActionListener(e -> defend());
    Counter.addActionListener(e -> counter());
    Draw.addActionListener(e -> openDrawMenu());
    New.addActionListener(e -> newChar());
    Save.addActionListener(e -> {
      if (currentFile != null) {
        save(currentFile);
      } else {
        saveNew();
      }
    });
    SaveAs.addActionListener(e -> {
      saveNew();
    });
    loadButton.addActionListener(e -> load());

    AddRow.addActionListener(e -> inventoryTableModel.setRowCount(inventoryTableModel.getRowCount() + 1));
  }

  private String appendAsterisks(String s) {
    if (!s.endsWith("*")) {
      return s.concat("*");
    }
    return s;
  }

  private void openDrawMenu() {
    draw = new JFrame("Choose Max Value");
    draw.setContentPane(new Draw(TimeLost.this).rootPanel);
    draw.pack();
    draw.setResizable(false);
    draw.setLocationRelativeTo(rootPanel);
    draw.setLocation(draw.getX(), draw.getY() + Math.round(draw.getHeight() / 2) + Math.round(rootPanel.getHeight() / 2));
    draw.setVisible(true);
  }

  private void openStatChooser() {
    statChooser = new JFrame("Choose Stat");
    statChooser.setContentPane(new StatChooser(TimeLost.this).rootPanel);
    statChooser.pack();
    statChooser.setResizable(false);
    statChooser.setLocationRelativeTo(rootPanel);
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

  private void saveNew() {
    // configure jfc
    JFileChooser jfc = new JFileChooser();
    jfc.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
    jfc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
    File selectedFile;
    if (currentFile != null) {
      selectedFile = currentFile;
    }
    else {
      selectedFile = new File(defaultName + ".json");
    }
    jfc.setSelectedFile(selectedFile);

    if (jfc.showSaveDialog(rootPanel) == JFileChooser.APPROVE_OPTION) {
      File file = jfc.getSelectedFile();

      if (!file.getName().endsWith(".json")) {
        file = new File(file + ".json");
      }
      save(file);
    }
  }

  private void save(File file) {
    try {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      TimeLostChar toSave = buildCharacterFromGUI();
      String str = gson.toJson(toSave);
      FileWriter writer = new FileWriter(file);
      writer.write(str);
      writer.close();

      // reset frame title and set current file to the one we just saved
      mainFrame.setTitle(file.getName().substring(0, file.getName().length() - 5));
      currentFile = file;

    } catch (Exception e) {
      e.printStackTrace();
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

        // reset title and set current file
        mainFrame.setTitle(file.getName().substring(0, file.getName().length() - 5));
        currentFile = file;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void newChar() {
    CharacterName.setText("");
    CharacterLVL.setValue(0);
    CharacterHP.setValue(0);
    CharacterMaxHP.setValue(0);
    CharacterFocus.setValue(0);
    CharacterMaxFocus.setValue(0);
    setStatRadioButton(
        BodyGroup,
        0,
        new JRadioButton[]{Body1, Body2, Body3, Body4 });
    setStatRadioButton(
        MindGroup,
        0,
        new JRadioButton[]{Mind1, Mind2, Mind3, Mind4 });
    setStatRadioButton(
        SoulGroup,
        0,
        new JRadioButton[]{Soul1, Soul2, Soul3, Soul4 });
    setStatRadioButton(
        EssenceGroup,
        0,
        new JRadioButton[]{Essence1, Essence2, Essence3, Essence4 });
    fillInventoryTable(new TimeLostItem[0]);

    mainFrame.setTitle(defaultName);
    currentFile = null;
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
    output.setInventory(convertInventoryToItemArray());

    return output;
  }

  private TimeLostItem[] convertInventoryToItemArray() {
    ArrayList<TimeLostItem> output = new ArrayList<>();

    //TODO: optimize this so that the table stores what rows have been edited, and only loops through those rows
    // Will have to ensure that when loading a character, that it stores which rows it filled in, too
    for (int row = 0; row < inventoryTableModel.getRowCount(); row++) {
      TimeLostItem currentItem = new TimeLostItem();
      for (int col = 0; col < inventoryTableModel.getColumnCount(); col++) {
        String property = (String)inventoryTableModel.getValueAt(row, col);
        switch (col) {
          case 0:
            currentItem.setName(property);
            break;
          case 1:
            currentItem.setDescription(property);
            break;
          case 2:
            currentItem.setStat(property);
            break;
          default:
            currentItem.setModifier(property);
            break;
        }
      }
      if (!(currentItem.getName().isEmpty()
          && currentItem.getDescription().isEmpty()
          && currentItem.getStat().isEmpty()
          && currentItem.getModifier().isEmpty())) {
        output.add(currentItem);
      }
    }

    return output.toArray(new TimeLostItem[0]);
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
    fillInventoryTable(character.getInventory());
  }

  private void fillInventoryTable(TimeLostItem[] input) {
    TimeLostItem[] inventory;
    if (input == null)
      inventory = new TimeLostItem[0];
    else
      inventory = input;

    inventoryTableModel.setRowCount(0); // clearing old data
    inventoryTableModel.setRowCount(Math.max(inventory.length, defaultTableSize));

    int rowIndex = 1;
    for (TimeLostItem item : inventory) {
      // column indices are non-changeable, so we can assume their positions here
      InventoryTable.setValueAt(item.getName(), rowIndex, 0);
      InventoryTable.setValueAt(item.getDescription(), rowIndex, 1);
      InventoryTable.setValueAt(item.getStat(), rowIndex, 2);
      InventoryTable.setValueAt(item.getModifier(), rowIndex, 3);
      rowIndex++;
    }
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
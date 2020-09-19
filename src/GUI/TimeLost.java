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
import java.util.Objects;
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

// Represents the GUI supporting character editing, saving, and loading, based on the rules of the card game TimeLost
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

  // Sets up all the listeners and the inventory pane
  public TimeLost() {
    addListeners();
    setupInventory();
  }

  /* Congirues a table with the proper column #s and names, disallows moving the columns, and sets
   * the default row size. Puts it in the GUI
   */
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

  /*
   * Creates the main frame, attaches this objects rootPanel to it, so that this class'
   * functionality is accessible. Configures window settings, and sets visible.
   */
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

  // Adds the functionality to all of the interactables in the GUI
  private void addListeners() {

    // these listeners literally just see if something in the GUI has changed, to add the * to the
    // frame title (indicating a change).
    // TODO: make this less abysmal, and include the inventory pane
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

    // More notable listeners
    Attack.addActionListener(e -> openStatChooser()); // Opens window where you choose attack stat
    Defend.addActionListener(e -> defend());          // Rolls defense calculation
    Counter.addActionListener(e -> counter());        // Rolls counter calculation
    Draw.addActionListener(e -> openDrawMenu());      // opens draw window
    New.addActionListener(e -> newChar());            // resets gui to default state -> new character state
    Save.addActionListener(e -> {                     // Saves the current character:
      if (currentFile != null) {
        save(currentFile);                            // If the character file already exists (loaded/saved before), auto save
      } else {
        saveNew();                                    // Open file chooser gui to save a new character
      }
    });
    SaveAs.addActionListener(e -> {
      saveNew();                                      // Open file chooser gui to save a new character
    });
    loadButton.addActionListener(e -> load());        // opens file chooser to load a character

    // updates inventory row count
    AddRow.addActionListener(e -> inventoryTableModel.setRowCount(inventoryTableModel.getRowCount() + 1));
  }

  // Literally just changes the name of the frame to indicate unsaved changes
  private String appendAsterisks(String s) {
    if (!s.endsWith("*")) {
      return s.concat("*");
    }
    return s;
  }

  // Opens a new Draw menu window so the user can choose a max value for the RNG
  private void openDrawMenu() {
    draw = new JFrame("Choose Max Value");
    draw.setContentPane(new Draw(TimeLost.this).rootPanel);
    draw.pack();
    draw.setResizable(false);
    draw.setLocationRelativeTo(rootPanel);
    draw.setLocation(draw.getX(), draw.getY() + Math.round(draw.getHeight() / 2) + Math.round(rootPanel.getHeight() / 2));
    draw.setVisible(true);
  }

  // Opens the stat chooser window so the user can specify a stat for their attack
  private void openStatChooser() {
    statChooser = new JFrame("Choose Stat");
    statChooser.setContentPane(new StatChooser(TimeLost.this).rootPanel);
    statChooser.pack();
    statChooser.setResizable(false);
    statChooser.setLocationRelativeTo(rootPanel);
    statChooser.setVisible(true);
  }

  // This has to be a method of this class because the stat chooser window is a subwindow
  // TODO: consider destroying the window and/or opening a pre-existing window in openStatChooser
  public void closeStatChooser() {
    statChooser.setVisible(false);
  }

  // Counter an enemy attack by dealing a small amount of knockback damage to attacker
  // Auto apply body , since that is the meta relevant stat
  private void counter() {
    returnOutcome(BodyGroup, Stat.BODY, TimeLostChar.SMALLDECK);
  }

  // Mitigate damage taken from an enemy attack
  // Apply mind, since that is meta relevant stat
  private void defend() {
    returnOutcome(MindGroup, Stat.MIND, TimeLostChar.SMALLDECK);
  }

  // Attack, based on what stat is selected by the user
  public void attack(String s) {
    ButtonGroup group;
    Stat stat;

    // Determine which stat we care about
    // (currently based on the selection made in StatChooser window)
    // am aware this is non-ideal hardcoding,
    // but it would unnecessarily complicate something that is fairly invariable to do otherwise
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

  // Tell the player the outcome of their role in the bottom text window
  private void returnOutcome(ButtonGroup group, Stat stat, int bigdeck) {
    BattleOutcome.setText("Stat: " + stat + "\n" + TimeLostChar
        .action((int) CharacterLVL.getValue(), getSelectedButton(group), bigdeck));
  }

  // Save the character as a new character by opening the file chooser
  private void saveNew() {
    // configure jfc (j-file chooser)
    JFileChooser jfc = new JFileChooser();
    jfc.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
    jfc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
    File selectedFile;

    // Sets file to be saved, with a null check
    selectedFile = Objects
        .requireNonNullElseGet(currentFile, () -> new File(defaultName + ".json"));
    jfc.setSelectedFile(selectedFile);

    // Save the file with the .json extension
    if (jfc.showSaveDialog(rootPanel) == JFileChooser.APPROVE_OPTION) {
      File file = jfc.getSelectedFile();

      if (!file.getName().endsWith(".json")) {
        file = new File(file + ".json");
      }
      save(file);
    }
  }

  // Save the given file by serializing to a json file using the GSON library
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

  // Load a character file from the file chooser
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

        // convert string into the class with GSON library
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

  // Reset default values of GUI
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

  // Build a character class from the values in the GUI
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

  // Convert inventory table to an array, so it can be held in character class
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

  // Parse the spinner values as ints, or return 0.
  private int parseSpinner(JSpinner spinner) {
    try {
      return (int)spinner.getValue();
    } catch (Exception e) {
      return 0;
    }
  }

  // fill out GUI based on character class
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

  // Fill out inventory table based on values in array. Properties of TimeLostItem class correspond to columns
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

  // Set the correct radio button in the given group based on the index
  private void setStatRadioButton(ButtonGroup group, int index, JRadioButton[] buttons) {
    group.setSelected(buttons[index].getModel(), true);
  }

  // Get the button currently selected in the given radio group
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
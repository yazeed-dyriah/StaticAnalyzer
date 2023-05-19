package edu.najah.cap;

import edu.najah.cap.ex.EditorSaveAsException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, DocumentListener {
	public static  void main(String[] args) {
		new Editor();
	}private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	private static final int APPROVE_OPTION = 0;
	private static final int CANCEL_OPTION = 1;
	private static final int MESSAGE_ERROR = 0;
	private static final int MESSAGE_WARNING = 2;
	private static final int OPTION_TYPE = 0;
	private static final int NO_OPTION = 1;
	private static final String USER_HOME = "user.home";
	private static final String SAVE_FILE_MESSAGE = "Save file";
	private static final String CANT_WRITE_MESSAGE = "Cannot write file!";
	private static final String FILE_CHANGE_MESSAGE = "The file has changed. You want to save it?";
	private JEditorPane textPanel;
	private JMenuBar menu;
	private boolean changed = false;
	private File file;
	protected JMenu jmfile;

	public JEditorPane getTextPanel() {
		return textPanel;
	}

	public void setTextPanel(JEditorPane newTextPanel) {
		this.textPanel = newTextPanel;
	}


	public Editor() {
		super("Editor");
		textPanel = new JEditorPane();
		add(new JScrollPane(textPanel), "Center");
		textPanel.getDocument().addDocumentListener(this);

		menu = new JMenuBar();
		setJMenuBar(menu);
		buildMenu();
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void buildMenu() {
		buildFileMenu();
		buildEditMenu();
	}
	private void buildFileMenu() {
		jmfile = new JMenu("File");
		jmfile.setMnemonic('F');
		menu.add(jmfile);
		JMenuItem n = new JMenuItem("New");
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		jmfile.add(n);
		JMenuItem open = new JMenuItem("Open");
		jmfile.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem("Save");
		jmfile.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveas = new JMenuItem("Save as");
		saveas
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		jmfile.add(saveas);
		saveas.addActionListener(this);
		JMenuItem quit = new JMenuItem("Quit");
		jmfile.add(quit);
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}
	private void buildEditMenu() {
		JMenu edit = new JMenu("Edit");
		menu.add(edit);
		edit.setMnemonic('E');

		JMenuItem cut = new JMenuItem("Cut");
		cut.addActionListener(this);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cut.setMnemonic('T');
		edit.add(cut);

		JMenuItem copy = new JMenuItem("Copy");
		copy.addActionListener(this);
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		edit.add(copy);


		JMenuItem paste = new JMenuItem("Paste");
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(paste);
		paste.addActionListener(this);


		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic('F');
		find.addActionListener(this);
		edit.add(find);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));


		JMenuItem sellAll = new JMenuItem("Select All");
		sellAll.setMnemonic('A');
		sellAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		sellAll.addActionListener(this);
		edit.add(sellAll);
	}

	private String readFile() {
		StringBuilder rs = new StringBuilder();
		try (FileReader fr = new FileReader(file);
			 BufferedReader reader = new BufferedReader(fr);) {
			String line;
			while ((line = reader.readLine()) != null) {
				rs.append(line + "\n");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", MESSAGE_ERROR);
		}
		return rs.toString();
	}

	private void saveAs(String dialogTitle) {
		dialogTitle = dialogTitle.toUpperCase();
		JFileChooser dialog = new JFileChooser(System.getProperty(USER_HOME));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != APPROVE_OPTION)
			return;
		file = dialog.getSelectedFile();
		writeToFile();
		changed = false;
		setTitle("Editor - " + file.getName());
	}

	private void saveFile() {
		int ans = NO_OPTION;
		if (changed) {
			ans = JOptionPane.showConfirmDialog(null, FILE_CHANGE_MESSAGE, SAVE_FILE_MESSAGE, MESSAGE_ERROR, MESSAGE_WARNING);
		}
		if (ans == NO_OPTION) {
			if (file == null) {
				saveAs("Save");
			} else {
				writeToFile();
			}
		}
	}
	private void writeToFile() {
		String text = textPanel.getText();
		try (PrintWriter writer = new PrintWriter(file);) {
			if (!file.canWrite()) {
				throw new EditorSaveAsException(CANT_WRITE_MESSAGE);
			}
			writer.write(text);
			changed = false;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Failed to write to file: " + file.getName(), "Error !", MESSAGE_ERROR);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		try {
			if (action.equals("Quit")) {
				System.exit(0);
			}
			else if (action.equals("Open")) {
				loadFile();
			}
			else if (action.equals("Save")) {
				saveFile();
			}
			else if (action.equals("New")) {
				createFile();
			}
			else if (action.equals("Save As")) {
				saveAs("Save As");
			}
			else if (action.equals("Select All")) {
				textPanel.selectAll();
			} else if (action.equals("Copy")) {
				textPanel.copy();
			} else if (action.equals("Cut")) {
				textPanel.cut();
			} else if (action.equals("Paste")) {
				textPanel.paste();
			} else if (action.equals("Find")) {
				FindDialog find = new FindDialog(this, true);
				find.showDialog();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, e, "Error: " + ex.toString(), MESSAGE_ERROR);
		}
	}
	private void loadFile() {
		JFileChooser dialog = new JFileChooser(System.getProperty(USER_HOME));
		dialog.setMultiSelectionEnabled(false);
		try {
			int result = dialog.showOpenDialog(this);
			if (result == CANCEL_OPTION)
				return;
			if (result == OPTION_TYPE) {
				saveFileAfterChanges(dialog);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", MESSAGE_ERROR);
		}
	}

	private void saveFileAfterChanges(JFileChooser dialog) {
		if (changed) {
			int ans = JOptionPane.showConfirmDialog(null, FILE_CHANGE_MESSAGE, SAVE_FILE_MESSAGE, MESSAGE_ERROR, MESSAGE_WARNING);
			if (ans == CANCEL_OPTION)
				return;
			if (file == null) {
				saveAs("Save");
				return;
			}
			writeToFile();
		}
		file = dialog.getSelectedFile();
		textPanel.setText(readFile());
		changed = false;
		setTitle("Editor - " + file.getName());
	}

	private void createFile() {
		if (changed) {
			int ans = JOptionPane.showConfirmDialog(null, FILE_CHANGE_MESSAGE, SAVE_FILE_MESSAGE, MESSAGE_ERROR, MESSAGE_WARNING);
			if (ans == 1)
				return;
			if (file == null) {
				saveAs("Save");
				return;
			}
			writeToFile();
		}
		file = null;
		textPanel.setText("");
		changed = false;
		setTitle("Editor");
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changed = true;
	}

}



# Static_Analyzer

## 1- list all problems in the application:
![image](https://user-images.githubusercontent.com/94912770/225958553-50e0525a-2b99-4be7-8687-16f63e737f05.png)

![image](https://user-images.githubusercontent.com/94912770/225958645-d04310db-a67d-47f9-9fd1-1438cd634aa4.png)

![image](https://user-images.githubusercontent.com/94912770/225958715-d68517b2-9aae-4ff3-9ef3-1c0c96b3f8c6.png)

# 2- Issues Categories:
## 1- Code Smell:


### unused import : Affects readability

```
import edu.najah.cap.ex.EditorException
```
```
import java.awt.BorderLayout
```  
```
import java.util.ArrayList
```
```
import java.util.List`
```

### L84, 120


```
// System.out.println(e.getKeyCode());
```

```
// closeDialog();

```
		/*
		move = new JMenuItem("Move");
		move.setMnemonic('M');
		move.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(move);
		move.addActionListener(this);
		*/
```
### make the code difficult to understand and maintenance.
```

## L26 
```
JButton find, close;
```

### Affects readability.

### L122


		
### L162
```
System.out.println(text);
```

### L192
```
System.out.println(text);
```

#### L284
```
System.out.println(text);
```

#### L240
```
System.out.println("No change");
```
### It can lead to problems in maintaining the system.


#### L252
```
					try {
						PrintWriter writer = new PrintWriter(file);
						if (!file.canWrite())
							throw new Exception("Cannot write file!");
						writer.write(text);
						changed = false;
					} catch (Exception e) {
						e.printStackTrace();
					}
```

#### L262
```
		try (	FileReader fr = new FileReader(file);		
			BufferedReader reader = new BufferedReader(fr);) {
			String line;
			while ((line = reader.readLine()) != null) {
				rs.append(line + "\n");
					}
			        } catch (IOException e) {
				e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", 0);//0 means show Error Dialog
				}
```				

#### Affects maintainability.


				


#### L307
```
	private void saveAsText(String dialogTitle) throws EditorSaveAsException {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != 0)//0 value if approve (yes, ok) is chosen.
			return;
		file = dialog.getSelectedFile();
		try (PrintWriter writer = new PrintWriter(file);){
			writer.write(TP.getText());
			changed = false;
			setTitle("Save as Text Editor - " + file.getName());
		} catch (FileNotFoundException e) {
			throw new EditorSaveAsException(e.getMessage());
		}
	}	
```
### Affects maintainability


## L115
```
	public void keyTyped(KeyEvent e) {
	}
```

```
	public void keyReleased(KeyEvent e) {
	}
```

### Existing empty functions in the code, it causes difficulty in understanding and maintaining the code, and affects the performance of the program.



```
if (changed) {
				// 0 means yes and no option, 2 Used for warning messages.
				ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
			}
```
### Magic numbers affects readability.


### L134
```
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(actions[4])) {
			System.exit(0);
		} else if (action.equals(actions[0])) {
			loadFile();
		} else if (action.equals(actions[1])) {
			//Save file
			int ans = 0;
			if (changed) {
				// 0 means yes and no option, 2 Used for warning messages.
				ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
			}
			//1 value from class method if NO is chosen.
			if (ans != 1) {
				if (file == null) {
					saveAs(actions[1]);
				} else {
					String text = TP.getText();
					System.out.println(text);
					try (PrintWriter writer = new PrintWriter(file);){
						if (!file.canWrite())
							throw new EditorSaveException("Cannot write file!");
						writer.write(text);
						changed = false;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} else if (action.equals(actions[2])) {
			//New file 
			if (changed) {
				//Save file 
				if (changed) {
					// 0 means yes and no option, 2 Used for warning messages.
					int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
							0, 2);
					//1 value from class method if NO is chosen.
					if (ans == 1)
						return;
				} else {
					return;
				}
				if (file == null) {
					saveAs(actions[1]);
					return;
				}
				String text = TP.getText();
				System.out.println(text);
				try (PrintWriter writer = new PrintWriter(file);){
					if (!file.canWrite())
						throw new Exception("Cannot write file!");
					writer.write(text);
					changed = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			file = null;
			TP.setText("");
			changed = false;
			setTitle("Editor");
		} else if (action.equals(actions[5])) {
			saveAs(actions[5]);
		} else if (action.equals("Select All")) {
			TP.selectAll();
		} else if (action.equals("Copy")) {
			TP.copy();
		} else if (action.equals("Cut")) {
			TP.cut();
		} else if (action.equals("Paste")) {
			TP.paste();
		} else if (action.equals("Find")) {
			FindDialog find = new FindDialog(this, true);
			find.showDialog();
		}
	}
```
###A long function affect the maintainability and readability.


## L88
```
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
```

### Using code that has become outdated can affect system performance and make it difficult to
fix bugs and update the system for new requirements. Outdated code may contain
unresolved technical or security issues, which may cause unexpected errors or operational
problems.


## L63

```
	private void BuildMenu() {
		buildFileMenu();
		buildEditMenu();
	}
```

###The effect of following the naming convention in the system is to improve the reading and understanding of the source code by other developers, especially if they are not the original developer of the source code.



## L39
```
public boolean changed = false;
```
### When a public scope is given to class variables, it allows these variables to be accessed and modified from any other part of the code, which exposes the code to error-handling issues and difficulty in future maintenance.


## L38
```
public JMenuItem copy, paste, cut, move;
```
### When variables are declared on the same line, the code may affect maintainability, readability, and modification in the future.


## L36
```
public JEditorPane TP;//Text Panel
```
### Poorly named variables can make the code difficult to read and understand.
 
 
## D- Blocker:

## L22
```
Editor parent;
```

```
Matcher matcher;
```

### The sub class field will "shadow" or override the parent class field in this situation, which may result in unexpected behavior and confusion.

## 2- Bugs:
### B- Major:


#### L234

```
				if (changed){
					//Save file
					if (changed) {
						int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
								0, 2);//0 means yes and no question and 2 mean warning dialog
						if (ans == 1)// no option 
							return;
					}
```
### this leads to increase in the complexity of the code, therefore, it will increase the time needed to compile the code.

#### L294

```
	private void saveAs(String dialogTitle) {
		dialogTitle = dialogTitle.toUpperCase();
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != 0)//0 value if approve (yes, ok) is chosen.
			return;
		file = dialog.getSelectedFile();
		PrintWriter writer = getWriter(file);
		writer.write(TP.getText());
		changed = false;
		setTitle("Editor - " + file.getName());
	}
```
##### Empty values cause problems with the application crashing and running as well.


## 4- False positive and negative :

### 4.1- false positive :

#### there is no false positive

### 4.2 - false negative:

####
#### There is a magic number in this code, sonar did not detect it because it considered it a fixed window size and it will not be changed later


```
JOptionPane.showMessageDialog(null, e, "Error", 0);
```

```
		int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
		        	0, 2);
```							
```
if (result == 1)//1 value if cancel is chosen.
				return;
			if (result == 0) {
```
```
protected JMenu jmfile;
```
### the code convention should be "jmFile" but the sonarLint didn't catch him because, may not violate any of the rules enabled in SonarLint. SonarLint checks the code for various code quality issues based on the rules and configurations set by the user or the organization. If there is no rule that checks for the use of specific access modifiers (such as protected), then SonarLint will not flag it as an issue.



### The program was should return null exception if the  if statement was true, but it did not do so because it expected that the exception was present in the code when it was used in previous times.
```
		if (file == null) {
		saveAs(actions[1]);
		return;
		}
```				

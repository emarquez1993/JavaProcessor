// Eric Marquez and Nick Keele
// COSC 3420.001
// Project #4
// Due date: 4/4/2018
/*
 * This class provides the graphical user interface
 * for the JavaProcessor class.
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class JavaProcessorGUI extends JPanel implements ActionListener {

    private JButton browseButton;
    private JTextArea input;
    private JFileChooser fc;

    /*
     * Default constructor that sets the layout, button, and
     * text field for the GUI.
     */
    public JavaProcessorGUI() {
        super(new BorderLayout());

        input = new JTextArea(5,35);
        input.setMargin(new Insets(5,5,5,5));
        input.setEditable(false);
        JScrollPane inputScrollPane = new JScrollPane(input);

        fc = new JFileChooser();

        browseButton = new JButton("Browse for file");
        browseButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(browseButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(inputScrollPane, BorderLayout.CENTER);
    }

    /*
     * Sets the file name to the users specified file and performs
     * the necessary steps to fix the syntax in the .java file.
     * 
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            int inputFile = fc.showOpenDialog(JavaProcessorGUI.this);

            if (inputFile == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                JavaProcessor syntaxFix = new JavaProcessor(file.getName()); // JavaProcessor object
                syntaxFix.missingCurlyBraces(); //method call beginning
                syntaxFix.commentCheck();
                syntaxFix.missingSemicolon();
        		syntaxFix.missingSemicolon();
        		syntaxFix.indentationFormat();
        		syntaxFix.writeToFile(); //method call end.
        		input.append("The syntax errors in " + file.getName() + " have been fixed.");
            }
            else {
                input.append("Open command cancelled by user.");
            }
            input.setCaretPosition(input.getDocument().getLength());
        }
    }

    /*
     * For closing the window of the GUI.
     */
    private static void Window() {
        JFrame frame = new JFrame("JavaProcessor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JavaProcessorGUI());

        frame.pack();
        frame.setVisible(true);
    }

    /*
     * Main method to initialize the GUI.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Window();
            }
        });

    }
}
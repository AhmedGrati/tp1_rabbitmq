
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClienUI implements DocumentListener {

    int userNumber;

    ClienUI(int userNumber){
        this.userNumber = userNumber;
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Client"+userNumber));
        jPanel.setBounds(10,30,470,600);
        JFrame f= new JFrame("Text Editor");

        JTextArea area=new JTextArea(20,20);
        Font fieldFont = new Font("Arial", Font.PLAIN, 20);
        area.setFont(fieldFont);
        area.setBorder(BorderFactory.createCompoundBorder(
                new CustomeBorder(),
                new EmptyBorder(new Insets(15, 25, 15, 25))));

        area.getDocument().addDocumentListener(this);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);


        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10, 11, 455, 249);

        jPanel.add(scroll);
        f.add(jPanel);
        f.setSize(500,800);
        f.setLayout(null);
        f.setVisible(true);

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(0,e.getDocument().getLength());
            Send sender = new Send(text,"user"+userNumber);
            sender.sendMessage();
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(0,e.getDocument().getLength());
            Send sender = new Send(text,"user"+userNumber);
            sender.sendMessage();
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println("CHANGED UPDATE");
    }
}

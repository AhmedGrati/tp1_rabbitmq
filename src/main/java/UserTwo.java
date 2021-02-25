
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTwo {

    UserTwo(){
        JFrame f= new JFrame();
        JTextArea area=new JTextArea("Enter your message:");
        area.setBounds(10,30, 200,100);
        JButton button = new JButton("Send");
        button.setBounds(220,30,100,30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Send sender = new Send(area.getText(),"user2");
                sender.sendMessage();
            }
        });
        f.add(area);
        f.add(button);
        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
    }
    public static void main(String[] args) {
        new UserTwo();
    }
}

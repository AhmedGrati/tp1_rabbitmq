
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class ClienUI implements DocumentListener {

    int userNumber;
    String queueName;
    String otherQueueName;
    ClienUI(int userNumber, String queueName, String otherQueueName) throws IOException, TimeoutException {
        this.userNumber = userNumber;
        this.queueName = queueName;
        this.otherQueueName = otherQueueName;

        System.out.println(queueName);
        System.out.println(otherQueueName);
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Client"+userNumber));
        jPanel.setBounds(10,30,1000,600);
        JFrame f= new JFrame("Text Editor");

        // USER A
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

        // USER B
        JTextArea area2=new JTextArea(20,20);
        area2.setFont(fieldFont);
        area2.setBorder(BorderFactory.createCompoundBorder(
                new CustomeBorder(),
                new EmptyBorder(new Insets(15, 25, 15, 25))));
        area2.setEditable(false);
        area2.setLineWrap(true);
        area2.setWrapStyleWord(true);


        JScrollPane scroll2 = new JScrollPane(area2);
        scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll2.setBounds(510, 11, 455, 249);


        // Listening to other queue
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(otherQueueName,false,false,false,null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody(),"UTF-8");
            area2.setText(otherQueueName+":"+receivedMessage);
            System.out.println(" [x] sent '"+receivedMessage+" '");

        };
        channel.basicConsume(otherQueueName,true,deliverCallback,consumerTag -> {});

        jPanel.add(scroll);
        jPanel.add(scroll2);
        f.add(jPanel);
        f.setSize(1000,800);
        f.setLayout(null);
        f.setVisible(true);



    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(0,e.getDocument().getLength());
            Send sender = new Send(text,queueName);
            sender.sendMessage();
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(0,e.getDocument().getLength());
            Send sender = new Send(text,queueName);
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

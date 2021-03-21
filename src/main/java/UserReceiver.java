import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class UserReceiver {
    public final static String QUEUE_NAME1="user1";
    public final static String QUEUE_NAME2="user2";
    public static void main(String[] args) throws IOException, TimeoutException {
        receive();
    }

    static void receive() throws IOException, TimeoutException {
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Receiver"));
        jPanel.setBounds(10,30,1000,600);
        JFrame f= new JFrame("Text Editor");

        JTextArea area1=new JTextArea(20,20);
        Font fieldFont = new Font("Arial", Font.PLAIN, 20);
        area1.setFont(fieldFont);
        area1.setBorder(BorderFactory.createCompoundBorder(
                new CustomeBorder(),
                new EmptyBorder(new Insets(15, 25, 15, 25))));

        area1.setEditable(false);
        area1.setLineWrap(true);
        area1.setWrapStyleWord(true);


        JScrollPane scroll1 = new JScrollPane(area1);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll1.setBounds(10, 265, 455, 249);

        jPanel.add(scroll1);



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
        scroll2.setBounds(10, 11, 455, 249);

        jPanel.add(scroll2);

        f.add(jPanel);
        f.setSize(1000,800);
        f.setLayout(null);
        f.setVisible(true);


        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME1,false,false,false,null);


        Connection connection2 = connectionFactory.newConnection();
        Channel channel2 = connection2.createChannel();
        channel2.queueDeclare(QUEUE_NAME2,false,false,false,null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody(),"UTF-8");
            area1.setText(receivedMessage);
            System.out.println(" [x] sent '"+receivedMessage+" '");

        };
        channel.basicConsume(QUEUE_NAME1,true,deliverCallback,consumerTag -> {});

        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody(),"UTF-8");
            area2.setText(receivedMessage);
            System.out.println(" [x] sent '"+receivedMessage+" '");

        };
        channel.basicConsume(QUEUE_NAME2,true,deliverCallback2,consumerTag -> {});
    }
}

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class UserReceiver {
    public final static String QUEUE_NAME1="user1";
    public final static String QUEUE_NAME2="user2";
    static JLabel userLabel1, userLabel2;
    public static void main(String[] args) throws IOException, TimeoutException {
        receive();
    }

    static void receive() throws IOException, TimeoutException {
        JFrame f= new JFrame("Label Example");

        userLabel1=new JLabel("User one say: ");
        userLabel1.setBounds(50,50, 300,30);
        f.add(userLabel1);

        userLabel2=new JLabel("User two say: ");
        userLabel2.setBounds(50,100, 300,30);
        f.add(userLabel2);

        f.setSize(500,500);
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
            userLabel1.setText("User one say: "+receivedMessage);
            System.out.println(" [x] sent '"+receivedMessage+" '");

        };
        channel.basicConsume(QUEUE_NAME1,true,deliverCallback,consumerTag -> {});

        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody(),"UTF-8");
            userLabel2.setText("User two say: "+receivedMessage);
            System.out.println(" [x] sent '"+receivedMessage+" '");

        };
        channel.basicConsume(QUEUE_NAME2,true,deliverCallback2,consumerTag -> {});
    }
}

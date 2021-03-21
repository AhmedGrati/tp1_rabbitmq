package final_quest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Client {
    public static void main(String[] args) throws IOException, TimeoutException {
        int numberOfUsers = 2;
        for (int i=0;i<numberOfUsers;i++) {
            new ClienUI(i+1, "draft");
        }
    }
}

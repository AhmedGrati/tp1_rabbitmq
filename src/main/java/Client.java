import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Client {
    public static void main(String[] args) throws IOException, TimeoutException {
        int numberOfUsers = 2;
        for (int i=0;i<numberOfUsers;i++) {
            if (i == 0) {
                new ClienUI(1, "user1","user2");
            }else {
                new ClienUI(2, "user2","user1");
            }

        }
    }
}

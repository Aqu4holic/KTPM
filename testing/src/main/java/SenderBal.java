import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SenderBal {

	private final static String QUEUE_NAME = "banking";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
		     Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			int prefetch_count = 1;
			channel.basicQos(prefetch_count);

			Scanner scanner = new Scanner(System.in);
			String message = "";

			for (; ;){
				message = scanner.nextLine();

				channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

				System.out.println(Balance.getCurrentInstance());

				System.out.println("[x] Sent '" + message + "'");
				System.out.println("[x] Current balance: " + Balance.getCurrentInstance().getCurrentBalance());
			}
		}
	}
}
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MessageProducer {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
		     Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

//			Scanner scanner = new Scanner(System.in);
//			String message;
//
//			for (; ;){
//				message = scanner.nextLine();
//
//				channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
//
//				System.out.println("[x] Sent '" + message + "'");
//			}

			String message = "hello, world!";

			channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

			System.out.println("[x] Sent '" + message + "'");
		}
	}
}
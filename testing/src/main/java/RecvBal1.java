import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class RecvBal1 {

	private final static String QUEUE_NAME = "banking";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages on consumer 1. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			System.out.println("[x] Received '" + message + "'");

			String[] request = message.split(" ", 0);

			if (request.length != 2) {
				return;
			}

			System.out.println(Balance.getCurrentInstance());

			if (request[0].equals("+")) {
				Balance.getCurrentInstance().increase(Integer.parseInt(request[1]));
			} else if (request[0].equals("-")){
				Balance.getCurrentInstance().decrease(Integer.parseInt(request[1]));
			}

			System.out.println("[x] Current balance: " + Balance.getCurrentInstance().getCurrentBalance());
		};
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
	}
}
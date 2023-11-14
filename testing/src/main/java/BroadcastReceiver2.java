import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class BroadcastReceiver2 {

	private final static String EXCHANGE_NAME = "broadcast";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		String QUEUE_NAME = channel.queueDeclare().getQueue();
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			System.out.println("[x] Received '" + message + "'");
		};
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
	}
}
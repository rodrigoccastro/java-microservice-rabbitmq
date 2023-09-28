package microservice.read.client.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String newItemText) {
        System.out.println("receive Message " + newItemText);
        saveMongoDb(newItemText);
    }

    private void saveMongoDb(String text) {
        // hear save the message in mongoDb
    }

}

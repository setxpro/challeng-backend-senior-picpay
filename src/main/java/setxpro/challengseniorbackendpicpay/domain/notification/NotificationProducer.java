package setxpro.challengseniorbackendpicpay.domain.notification;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import setxpro.challengseniorbackendpicpay.domain.transaction.Transaction;


/**
 * Put message in topic kafka
 * to get a message
 */
@Service
public class NotificationProducer {
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Ensures the message is scheduled for sending
     */
    public void sendNotification(Transaction transaction) {
        kafkaTemplate.send("transaction-notification", transaction);
    }
}

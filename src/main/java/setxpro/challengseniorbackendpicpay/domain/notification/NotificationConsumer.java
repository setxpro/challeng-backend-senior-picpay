package setxpro.challengseniorbackendpicpay.domain.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import setxpro.challengseniorbackendpicpay.domain.transaction.Transaction;
import setxpro.challengseniorbackendpicpay.infra.email.EmailService;

import java.math.BigDecimal;

@Service
public class NotificationConsumer {
    private RestClient restClient;

    private final EmailService emailService;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    public NotificationConsumer(RestClient.Builder builder, EmailService emailService) {
        this.restClient = builder.baseUrl("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6").build();
        this.emailService = emailService;
    }

    /**
     * Consumer notification
     */
    @KafkaListener(topics = "transaction-notification", groupId = "challenge-backend-picpay")
    public void receiveNotification(Transaction transaction) {

        LOGGER.info("notifying transaction {}...", transaction);

        // Find transactions

        String subject = "Transaction Notification";
        String body = buildEmailBody(transaction);
        String emailTo = "patrickpqdt87289@gmail.com";
        String emailFrom = "patrick.anjos@bagaggio.com.br";

        var response = restClient.get()
                .retrieve()
                .toEntity(Notification.class);

        if (response.getStatusCode().isError() || !response.getBody().message())
            throw new NotificationException("Error sending notification!");

        emailService.sentEmail(emailTo, emailFrom, body, subject);
        LOGGER.info("notification has been sent {}...", transaction);
    }

    private String buildEmailBody(Transaction transaction) {
        BigDecimal value = transaction.value();
        Long recipient = transaction.payer();
        return "<h1>PAGO POR " +recipient+ " no TOTAL de R$ "+value+"</h1>";
    }
}

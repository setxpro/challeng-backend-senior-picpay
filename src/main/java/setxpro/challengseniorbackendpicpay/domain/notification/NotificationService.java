package setxpro.challengseniorbackendpicpay.domain.notification;

import org.springframework.stereotype.Service;
import setxpro.challengseniorbackendpicpay.domain.transaction.Transaction;

@Service
public class NotificationService {

    private final NotificationProducer notificationProducer;

    public NotificationService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }


    /***
     *  Asynchronous notification
     */
    public void notify(Transaction transaction) {
        notificationProducer.sendNotification(transaction);
    }

}

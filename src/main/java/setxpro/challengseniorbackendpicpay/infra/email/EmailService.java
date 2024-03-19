package setxpro.challengseniorbackendpicpay.infra.email;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EmailService {

    @Value("${api.endpoint.email}")
    private String endpoint;

    private RestClient restClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public EmailService(RestClient.Builder builder, @Value("${api.endpoint.email}") String endpoint) {
        this.restClient = builder.baseUrl(endpoint + "/ms-email/sent").build();
    }

    public void sentEmail(
            String emailTo,
            String emailFrom,
            String bodyHtml,
            String subject
    ) {

        String base64Attachment = "";
        String base64AttachmentName = "";
        String message = "";

        SentEmailDto newEmail = new SentEmailDto(
                emailTo,
                emailFrom,
                bodyHtml,
                subject,
                base64Attachment,
                base64AttachmentName,
                message
        );

        try {
            restClient.post()
                    .body(newEmail)
                    .retrieve()
                    .toEntity(EmailDTO.class); // Check response type if needed

            LOGGER.info("Email sent successfully to {}", emailTo);
        } catch (Exception e) {
            LOGGER.error("Error sending email:", e);
            // Handle exceptions (e.g., retry, notify administrator)
        }

        restClient.post().body(newEmail)
                .retrieve()
                .toEntity(EmailDTO.class);
    }

}

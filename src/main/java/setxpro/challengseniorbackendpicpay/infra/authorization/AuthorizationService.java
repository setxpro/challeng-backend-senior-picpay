package setxpro.challengseniorbackendpicpay.infra.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import setxpro.challengseniorbackendpicpay.domain.transaction.Transaction;

@Service
public class AuthorizationService {
    private RestClient restClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);

    public AuthorizationService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
                .build();
    }

    public void authorize(Transaction transaction) {
        LOGGER.info("Authorizing transaction: {}", transaction);
        var response = restClient.get()
                .retrieve()
                .toEntity(Authorization.class);

        if (response.getStatusCode().isError() || !response.getBody().isAuthorized())
            throw new UnauthorizedTransactionException("Unauthorized Transaction!");

        LOGGER.info("Transaction Authorize: {}", transaction);
    }
}

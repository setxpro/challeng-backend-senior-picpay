package setxpro.challengseniorbackendpicpay.domain.transaction;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("TRANSACTIONS")
public record Transaction(
        @Id Long id,
        Long payer, // Who pays
        Long payee, // Who receives
        BigDecimal value,
        @CreatedDate LocalDateTime createdAt
) {
    public Transaction {
        value = value.setScale(2);
    }
}

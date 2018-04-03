package dao.impl.mysql.converter;

import entity.Account;
import entity.Payment;
import dao.util.time.TimeConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Zulu Warrior on 5/22/2017.
 */
public class PaymentDtoConverter implements DtoConverter<Payment> {
    private final static String ID_FIELD = "payment_id";

    private final static String AMOUNT_FIELD = "amount";

    private final static String ACCOUNT_FROM_FIELD =
            "account_from";

    private final static String ACCOUNT_TO_FIELD =
            "account_to";

    private final static String DATE_FIELD = "date";

    private final static String ACCOUNT_TO_INDEX =
            "acc2.";

    private final static String ACCOUNT_FROM_INDEX =
            "acc1.";


    private final DtoConverter<Account> accountConverter;

    public PaymentDtoConverter() {
        this(new AccountDtoConverter());
    }

    public PaymentDtoConverter(DtoConverter<Account> accountConverter) {
        this.accountConverter = accountConverter;
    }

    @Override
    public Payment convertToObject(ResultSet resultSet, String tablePrefix) throws SQLException {
        Account accountFrom = accountConverter.
                convertToObject(resultSet,
                        ACCOUNT_FROM_INDEX);

        Account accountTo = accountConverter.
                convertToObject(resultSet,
                        ACCOUNT_TO_INDEX);

        Payment payment = Payment.newBuilder().
                setId(resultSet.getInt(
                        tablePrefix + ID_FIELD)).
                setAmount(resultSet.getBigDecimal(
                        tablePrefix + AMOUNT_FIELD)).
                setAccountFrom(accountFrom).
                setAccountTo(accountTo).
                setDate(TimeConverter.toDate(
                        resultSet.getTimestamp(
                                tablePrefix + DATE_FIELD
                        )
                )).build();

        return payment;
    }
}

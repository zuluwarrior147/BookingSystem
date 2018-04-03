package service;

import dao.abstraction.AccountDao;
import dao.abstraction.PaymentDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.Account;
import entity.Payment;
import entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Uses dao layer.
 *
 * @author Andrii Markovych
 */
public class PaymentService {
    private final DaoFactory daoFactory= DaoFactory.getInstance();

    private PaymentService() {}

    private static class Singleton {
        private final static PaymentService INSTANCE = new PaymentService();
    }

    public static PaymentService getInstance() {
        return Singleton.INSTANCE;
    }

    public Optional<Payment> findById(Integer id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.getPaymentDao(connection);
            return paymentDao.findOne(id);
        }
    }

    public List<Payment> findAll() {
        try(DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.getPaymentDao(connection);
            return paymentDao.findAll();
        }
    }


    public List<Payment> findAllByUser(User user) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.getPaymentDao(connection);
            return paymentDao.findByUser(user);
        }
    }

    public List<Payment> findAllByAccount(Account account) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.getPaymentDao(connection);
            return paymentDao.findByAccount(account);
        }
    }

    public Payment createPayment(Payment payment) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            PaymentDao paymentDao = daoFactory.getPaymentDao(connection);
            AccountDao accountDao = daoFactory.getAccountDao(connection);

            connection.startSerializableTransaction();

            Account accountFrom = payment.getAccountFrom();
            Account accountTo = payment.getAccountTo();
            BigDecimal amount = payment.getAmount();

            accountDao.decreaseBalance(accountFrom, amount);
            accountDao.increaseBalance(accountTo, amount);

            Payment inserted = paymentDao.insert(payment);

            connection.commit();

            return inserted;
        }
    }
}

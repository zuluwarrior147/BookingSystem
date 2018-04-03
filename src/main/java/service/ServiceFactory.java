package service;

import dao.factory.DaoFactory;
import entity.Account;

import javax.jws.soap.SOAPBinding;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Uses dao layer.
 *
 * @author Andrii Markovych
 */
public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        if(instance == null) {
            instance = new ServiceFactory();
        }

        return instance;
    }


    public static UserService getUserService() {
        return UserService.getInstance();
    }

    public static AccountService getAccountService() {
        return AccountService.getInstance();
    }

    public static CardService getCardService() {
        return CardService.getInstance();
    }

    public static PaymentService getPaymentService() {
        return PaymentService.getInstance();
    }


}

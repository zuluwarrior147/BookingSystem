package dao.exception;

import dao.factory.DaoFactory;

/**
 * Created by Zulu Warrior on 5/21/2017.
 */
public class DaoException extends RuntimeException{
    public DaoException(String message){
        super(message);
    }

    public DaoException(Throwable source) {
        super(source);
    }

    public DaoException(String message, Throwable source) {
        super(message,source);
    }

}

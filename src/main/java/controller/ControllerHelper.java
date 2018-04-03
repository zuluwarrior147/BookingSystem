package controller;

import controller.command.BlockAccountCommand;
import controller.command.DefaultCommand;
import controller.command.HomeCommand;
import controller.command.ICommand;
import controller.command.admin.*;
import controller.command.authorization.*;
import controller.command.user.*;
import controller.util.constants.PagesPaths;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zulu Warrior on 5/28/2017.
 */
public class ControllerHelper {
    private final static String DELIMITER = ":";
    private final DefaultCommand DEFAULT_COMMAND = new DefaultCommand();
    private Map<String, ICommand> commands = new HashMap<>();

    private ControllerHelper() {
        init();
    }

    private void init() {
        commands.put(buildKey(PagesPaths.HOME_PATH, Method.GET),
                new HomeCommand());
        commands.put(buildKey(PagesPaths.LOGIN_PATH, Method.GET),
                new GetLoginCommand());
        commands.put(buildKey(PagesPaths.SIGNUP_PATH, Method.GET),
                new GetSignupCommand());
        commands.put(buildKey(PagesPaths.LOGIN_PATH, Method.POST),
                new PostLoginCommand());
        commands.put(buildKey(PagesPaths.SIGNUP_PATH, Method.POST),
                new PostSignupCommand());
        commands.put(buildKey(PagesPaths.LOGOUT_PATH, Method.GET),
                new LogoutCommand());
        commands.put(buildKey(PagesPaths.USER_ACCOUNTS_PATH, Method.GET),
                new GetAccountsCommand());
        commands.put(buildKey(PagesPaths.USER_CARDS_PATH, Method.GET),
                new GetCardsCommand());
        commands.put(buildKey(PagesPaths.USER_PAYMENTS_PATH, Method.GET),
                new ShowPaymentsCommand());
        commands.put(buildKey(PagesPaths.USER_BLOCK_ACCOUNT, Method.POST),
                new BlockAccountCommand());
        commands.put(buildKey(PagesPaths.USER_CREATE_PAYMENT, Method.GET),
                new GetNewPaymentCommand());
        commands.put(buildKey(PagesPaths.USER_CREATE_PAYMENT, Method.POST),
                new PostNewPaymentCommand());
        commands.put(buildKey(PagesPaths.USER_REPLENISH, Method.GET),
                new GetReplenishCommand());
        commands.put(buildKey(PagesPaths.USER_REPLENISH, Method.POST),
                new PostReplenishCommand());
        commands.put(buildKey(PagesPaths.ADMIN_ACCOUNTS_PATH, Method.GET),
                new GetAllAccountsCommand());
        commands.put(buildKey(PagesPaths.ADMIN_CARDS_PATH, Method.GET),
                new GetAllCardsCommand());
        commands.put(buildKey(PagesPaths.ADMIN_PAYMENTS_PATH, Method.GET),
                new GetAllPaymentsCommand());
        commands.put(buildKey(PagesPaths.ADMIN_UNBLOCK_PATH, Method.POST),
                new UnblockAccountCommand());
        commands.put(buildKey(PagesPaths.ADMIN_CONFIRM_PATH, Method.POST),
                new ConfirmAccountCommand());
        commands.put(buildKey(PagesPaths.ADMIN_BLOCK_ACCOUNT, Method.POST),
                new BlockAccountCommand());
    }

    public ICommand getCommand(String path, Method method) {
        return commands.getOrDefault(buildKey(path, method), DEFAULT_COMMAND);
    }

    private String buildKey(String path, Method method) {
        return method.name() + DELIMITER + path;
    }

    public enum Method {
        GET, POST
    }

    public static class Singleton {
        private final static ControllerHelper INSTANCE =
                new ControllerHelper();

        public static ControllerHelper getInstance() {
            return INSTANCE;
        }
    }


}

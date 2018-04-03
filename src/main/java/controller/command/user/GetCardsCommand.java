package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.Card;
import entity.User;
import service.CardService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Zulu Warrior on 6/5/2017.
 */
public class GetCardsCommand implements ICommand {
    private final CardService cardService = ServiceFactory.getCardService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getUserFromSession(request.getSession());

        List<Card> cards = cardService.findAllByUser(user);

        request.setAttribute(Attributes.CARDS, cards);

        return Views.CARDS_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(Attributes.USER);
    }
}

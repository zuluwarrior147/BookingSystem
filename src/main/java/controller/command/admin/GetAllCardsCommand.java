package controller.command.admin;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.Card;
import service.CardService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Zulu Warrior on 6/7/2017.
 */
public class GetAllCardsCommand implements ICommand {
    private final CardService cardService = ServiceFactory.getCardService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Card> cards = cardService.findAllCards();

        request.setAttribute(Attributes.CARDS, cards);

        return Views.CARDS_VIEW;
    }
}

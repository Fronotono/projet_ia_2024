package awele.bot.competitor.last;

import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Alexandre Blansche
 * Bot qui joue toujours le coup le plus à gauche
 */
public class LastBot extends CompetitorBot
{
    /**
     * @throws InvalidBotException
     */
    public LastBot () throws InvalidBotException
    {
        this.setBotName ("Last");
        this.addAuthor ("Alexandre Blansche");
    }

    /**
     * Rien à faire
     */
    @Override
    public void initialize ()
    {
    }

    /**
     * Retourne une valeur decroissante avec l'index du trou
     */
    @Override
    public double [] getDecision (Board board)
    {
        double [] decision = new double [Board.NB_HOLES];
        for (int i = 0; i < decision.length; i++)
            decision [i] = i;
        return decision;
    }

    /**
     * Pas d'apprentissage
     */
    @Override
    public void learn ()
    {
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}
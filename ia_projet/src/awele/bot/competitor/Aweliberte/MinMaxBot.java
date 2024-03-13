package awele.bot.competitor.Aweliberte;

import awele.bot.Bot;
import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Alexandre Blansche
 * Bot qui prend ses decisions selon le MinMax
 */
public class MinMaxBot extends Bot
{
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 8;
	
    /**
     * @throws InvalidBotException
     */
    public MinMaxBot () throws InvalidBotException
    {
        this.setBotName ("Aweliberte");
        this.addAuthor ("Other");
    }

    /**
     * Rien à faire
     */
    @Override
    public void initialize ()
    {
    }

    /**
     * Pas d'apprentissage
     */
    @Override
    public void learn ()
    {
    }

    /**
     * Selection du coup selon l'algorithme MinMax
     */
    @Override
    public double [] getDecision (Board board)
    {
        MinMaxNode.initialize (board, MinMaxBot.MAX_DEPTH);
        return new MaxNode (board).getDecision ();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}

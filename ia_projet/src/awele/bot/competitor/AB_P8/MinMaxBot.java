package awele.bot.competitor.AB_P8;

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
    private int realDepth;
	
    /**
     * @throws InvalidBotException
     */
    public MinMaxBot () throws InvalidBotException
    {
        this.setBotName ("AB_DS_P8");
        this.addAuthor ("Theo COLLET");
    }

    /**
     * Rien à faire
     */
    @Override
    public void initialize ()
    {
    	realDepth=0;
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
        realDepth++;
        return new MaxNode (board,realDepth).getDecision ();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}

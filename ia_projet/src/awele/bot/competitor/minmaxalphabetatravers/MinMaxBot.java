package awele.bot.competitor.minmaxalphabetatravers;

import java.util.ArrayList;
import java.util.Random;

import awele.bot.Bot;
import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Alexandre Blansche
 * Bot qui prend ses decisions selon le MinMax
 */
public class MinMaxBot extends CompetitorBot
{
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 8;
    private int[]poids;
	
    /**
     * @throws InvalidBotException
     */
    public MinMaxBot () throws InvalidBotException
    {
        this.setBotName ("AlphaBetaHMathis");
        this.addAuthor ("Theo COLLET");
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
    	poids = new int[6];
    	
    	int taillePop = 42, poidsMin = -20, poidsMax = 20, nbCombattants = 5, nbMatch = 2;
    	double tauxMutation = 0.2;
    	
    	MinMaxNode[][] generations = new MinMaxNode[2][taillePop];
    	MinMaxNode.initialize (new Board(), MinMaxBot.MAX_DEPTH);
   
    	for(int i = 0; i < 6; i++)poids[i]=(i%2==0)?20:-20;
    	generations[0][0] = new MaxNode(new Board(), poids);
    	
    	for(int i = 1; i < taillePop; i++) {
    		generations[0][i] = new MaxNode(new Board(),tabRandom(6,poidsMin,poidsMax));
    	}
    	
    	
    }

    /**
     * Selection du coup selon l'algorithme MinMax
     */
    @Override
    public double [] getDecision (Board board)
    {
        MinMaxNode.initialize (board, MinMaxBot.MAX_DEPTH);
        return new MaxNode (board,poids).getDecision ();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
    
    private int[] tabRandom(int n,int min,int max) {
    	int[] res = new int[n];
    	for(int i = 0; i < n; i++) {
    		res[i] = random(min,max);
    	}
    	return res;
    }
    
    private int random(int min, int max) {
    	return min+new Random().nextInt(max-min)+1;
    }
}

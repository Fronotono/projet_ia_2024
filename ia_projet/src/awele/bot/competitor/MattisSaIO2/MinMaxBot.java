package awele.bot.competitor.MattisSaIO2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import awele.bot.Bot;
import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Awele;
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
    private static final int TIME_LEARN_GEN = 50;
    private static final double TAUX_MUTATION = 0.2;
	private static final int POP_SIZE = 40;
	private static final int MIN_WEIGHT = -20;
	private static final int MAX_WEIGHT = 20;
	private static final int NB_FIGHTERS = 5;
	
    private int[]poids;
    private int realDepth;
	
	
    /**
     * @throws InvalidBotException
     */
    public MinMaxBot () throws InvalidBotException
    {
        this.setBotName ("MattisSaIO2");
        this.addAuthor ("Theo COLLET");
    }
    
    private MinMaxBot (int[] poids) throws InvalidBotException
    {
        this.poids = poids;
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
    	/*try {
	    	long start = System.currentTimeMillis();
	    	int nbGen = 0;
	    	
	    	Individu[] individus = new Individu[POP_SIZE];*/

	    	poids = new int[] {11,-11,-5,9,-15,-19};	
	    	/*individus[0] = new Individu(poids);
	    	
	    	for(int i = 1; i < POP_SIZE; i++) {
	    		individus[i] = new Individu(tabRandom(6,MIN_WEIGHT,MAX_WEIGHT));
	    	}
	    	
	    	//competition
	    	while(System.currentTimeMillis() - start < (1000 * 60 * TIME_LEARN_GEN))
	    	{
	    		System.out.println("Nouvelle Generation "+nbGen+" t="+(System.currentTimeMillis() - start)/1000f/60f+"min");
	    		for(int i = 0; i < NB_FIGHTERS; i++) {
	    			for(int j = 0; j < POP_SIZE; j++) {
	    				if(i!=j)//Un combat ne se fait pas sur le meme individu qui devrait finir sur une egalite
	    				{ 
							Awele awele = new Awele (new MinMaxBot(individus[i].poids),new MinMaxBot(individus[j].poids));
							awele.play();
							if(awele.getWinner() == -1) {
								individus[i].fitness += 100;
								individus[j].fitness += 100;
							} else if(awele.getWinner() == 0) {
								individus[i].fitness += 200;
							} else {
								individus[j].fitness += 200;
							}
	    				}
	    				if(System.currentTimeMillis() - start > (1000 * 60 * TIME_LEARN_GEN))break;
	    			}
	    			//TODO AJOUTER COMBAT DE BOSS
	    		}
	    		//TRI
	    		Arrays.sort(individus);
	    		//Verification du meilleur
	    		if(individus[0].fitness < individus[1].fitness)individus[0] = individus[1];
	    		//REPOPULATION
	    		Individu[] nouvellePop = new Individu[POP_SIZE];
	    		System.out.println(individus[0]);
	    		for(int i = 1; i < POP_SIZE; i++) {
	    			System.out.print(individus[i]);
	    			nouvellePop[i] = new Individu(individus[random(0,POP_SIZE/2)],individus[random(0,POP_SIZE/2)]);
	    		}
	    		nouvellePop[0] = individus[0];
	    		individus = nouvellePop;
	    		individus[0].fitness = 0;
	    		nbGen++;
	    	}
	    	System.out.println("Individu de reference :"+individus[0]+"\ntrouv� en "+nbGen+" generations");
	    	this.poids = individus[0].poids;
    	
		} catch (InvalidBotException e) {
			e.printStackTrace();
		}*/
    }

    /**
     * Selection du coup selon l'algorithme MinMax
     */
    @Override
    public double [] getDecision (Board board)
    {
        MinMaxNode.initialize(board, MAX_DEPTH);
        realDepth++;
        return new MaxNode (board,poids,realDepth).getDecision ();
        
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

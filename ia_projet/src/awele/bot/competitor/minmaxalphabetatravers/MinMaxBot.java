package awele.bot.competitor.minmaxalphabetatravers;

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
    private int[]poids;
	
    /**
     * @throws InvalidBotException
     */
    public MinMaxBot () throws InvalidBotException
    {
        this.setBotName ("AlphaBetaHMathis");
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
    }

    /**
     * Pas d'apprentissage
     */
    @Override
    public void learn ()
    {
    	System.out.println("Learn");
    	try {
	    	long start = System.currentTimeMillis();
	    	int nbGen = 0;
	    	
	    	int taillePop = 40, poidsMin = -20, poidsMax = 20, nbCombattants = 5, nbMatch = 2;
	    	double tauxMutation = 0.2;
	    	
	    	Individu[] individus = new Individu[taillePop];

	    	poids = new int[6];
	    	poids[0] = poids[2] = poids[4] = 20;
	    	poids[1] = poids[3] = poids[5] = -20;   	
	    	individus[0] = new Individu(poids);
	    	
	    	for(int i = 1; i < taillePop; i++) {
	    		individus[i] = new Individu(tabRandom(6,poidsMin,poidsMax));
	    	}
	    	
	    	//competition
	    	while(System.currentTimeMillis() - start < (1000 * 60 * 40)) //30minutes
	    	{
	    		System.out.println("Nouvelle Generation t="+(System.currentTimeMillis() - start)/1000/60+"min");
	    		for(int i = 0; i < nbCombattants; i++) {
	    			for(int j = 0; j < taillePop; j++) {
	    				if(i!=j) {
	    					System.out.print("Debut de jeu "+i+" "+j);
							Awele awele = new Awele (new MinMaxBot(individus[i].poids),new MinMaxBot(individus[j].poids));
							awele.play();
							System.out.println(" Fin de jeu");
							if(awele.getWinner() == -1) {
								individus[i].fitness += 100;
								individus[j].fitness += 100;
							} else if(awele.getWinner() == 0) {
								individus[i].fitness += 200;
							} else {
								individus[j].fitness += 200;
							}
	    				}
	    			}
	    			//TODO AJOUTER COMBAT DE BOSS
	    		}
	    		//TRI
	    		Arrays.sort(individus);
	    		//Verification du meilleur
	    		if(individus[0].fitness < individus[1].fitness)individus[0] = individus[1];
	    		//REPOPULATION
	    		Individu[] nouvellePop = new Individu[taillePop];
	    		for(int i = 1; i < taillePop; i++) {
	    			nouvellePop[i] = new Individu(individus[random(0,taillePop/2)],individus[random(0,taillePop/2)]);
	    		}
	    		nouvellePop[0] = individus[0];
	    		individus = nouvellePop;
	    		System.out.println(individus[0]);
	    		individus[0].fitness = 0;
	    		nbGen++;
	    	}
	    	System.out.println("Individu de reference :"+individus[0]+"\ntrouv� en "+nbGen+" generatins");
	    	this.poids = individus[0].poids;
    	
		} catch (InvalidBotException e) {
			e.printStackTrace();
		}    	
    }

    /**
     * Selection du coup selon l'algorithme MinMax
     */
    @Override
    public double [] getDecision (Board board)
    {
        MinMaxNode.initialize (board, 6);
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

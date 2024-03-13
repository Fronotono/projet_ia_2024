package awele.bot.competitor.AB_Mattis;

import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Alexandre Blansche
 * Noeud d'un arbre MinMax
 */
public abstract class MinMaxNode
{
    /** Numero de joueur de l'IA */
    private static int player;

    /** Profondeur maximale */
    private static int maxDepth;

    /** L'evaluation du noeud */
    private double evaluation;

    /** Évaluation des coups selon MinMax */
    private double [] decision;
    
    //* Poids des parametre de l'heuristique *//
	private int poids[];

    /**
     * Constructeur... 
     * @param board L'etat de la grille de jeu
     * @param depth La profondeur du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta Le seuil pour la coupe beta
     */
    public MinMaxNode (Board board, int depth, double alpha, double beta, int[] poids)
    {
        this.poids = poids;
        /* On cree un tableau des evaluations des coups a jouer pour chaque situation possible */
        this.decision = new double [Board.NB_HOLES];
        /* Initialisation de l'evaluation courante */
        this.evaluation = this.worst ();
        /* On parcourt toutes les coups possibles */
        for (int i = 0; i < Board.NB_HOLES; i++)
            /* Si le coup est jouable */
            if (board.getPlayerHoles () [i] != 0)
            {
                /* Selection du coup à jouer */
                double [] decision = new double [Board.NB_HOLES];
                decision [i] = 1;
                /* On copie la grille de jeu et on joue le coup sur la copie */
                Board copy = (Board) board.clone ();
                try
                {
                    int score = copy.playMoveSimulationScore (copy.getCurrentPlayer (), decision);
                    copy = copy.playMoveSimulationBoard (copy.getCurrentPlayer (), decision);
                    /* Si la nouvelle situation de jeu est un coup qui met fin a la partie,
                       on evalue la situation actuelle */   
                    if ((score < 0) ||
                            (copy.getScore (Board.otherPlayer (copy.getCurrentPlayer ())) >= 25) ||
                            (copy.getNbSeeds () <= 6))
                        this.decision [i] = this.heuristique (copy);
                    /* Sinon, on explore les coups suivants */
                    else
                    {
                        /* Si la profondeur maximale n'est pas atteinte */
                        if (depth < maxDepth)
                        {
                            /* On construit le noeud suivant */
                            MinMaxNode child = this.getNextNode (copy, depth + 1, alpha, beta, poids);
                            /* On recupère l'evaluation du noeud fils */
                            this.decision [i] = child.getEvaluation ();
                        }
                        /* Sinon (si la profondeur maximale est atteinte), on evalue la situation actuelle */
                        else
                            this.decision [i] = this.heuristique (copy);
                    }
                    /* L'evaluation courante du noeud est mise à jour, selon le type de noeud (MinNode ou MaxNode) */
                    this.evaluation = this.minmax (this.decision [i], this.evaluation);
                    //*THEO : coupe *//
                    if(alphabeta(this.evaluation,alpha,beta))break;
                    /* Coupe alpha-beta */ 
                    if (depth > 0)
                    {
                        alpha = this.alpha (this.evaluation, alpha);
                        beta = this.beta (this.evaluation, beta);
                    }      
                }
                catch (InvalidBotException e)
                {
                    this.decision [i] = 0;
                }
            }
    }

    /** Pire score pour un joueur */
    protected abstract double worst ();

    /**
     * Initialisation
     */
    protected static void initialize (Board board, int maxDepth)
    {
        MinMaxNode.maxDepth = maxDepth;
        MinMaxNode.player = board.getCurrentPlayer ();

    }

    private int diffScore (Board board)
    {
        return board.getScore (MinMaxNode.player) - board.getScore (Board.otherPlayer (MinMaxNode.player));
    }
    
    private int heuristique(Board board)
    {
    	int nbTrouVide = 0, nbTrouVideAdv = 0;
    	int nbTrou1ou2 = 0, nbTrou1ou2Adv = 0;
    	int nbKrou = 0, nbKrouAdv = 0;
    	for(int i = 0; i <= Board.NB_HOLES/2; i++)
    	{
    		if(board.getPlayerHoles()[i] == 0)nbTrouVide++;
    		else if(board.getPlayerHoles()[i] < 3)nbTrou1ou2++;
    		else if(board.getPlayerHoles()[i] > 11)nbKrou++;
    		if(board.getOpponentHoles()[i] == 0)nbTrouVideAdv++;
    		else if(board.getOpponentHoles()[i] < 3)nbTrouVideAdv++;
    		else if(board.getOpponentHoles()[i] > 11)nbTrouVideAdv++;
    	}
    	
    	int score = board.getScore(MinMaxNode.player), scoreAdv = board.getScore(Board.otherPlayer(MinMaxNode.player));
    	
    	return score*10+scoreAdv*-10+nbTrouVide*poids[0]+nbTrouVideAdv*poids[1]+nbTrou1ou2*poids[2]+nbTrou1ou2Adv*poids[3]+nbKrou*poids[4]+nbKrouAdv*poids[5];
    }

    /**
     * Mise à jour de alpha
     * @param evaluation L'evaluation courante du noeud
     * @param alpha L'ancienne valeur d'alpha
     * @return
     */
    protected abstract double alpha (double evaluation, double alpha);

    /**
     * Mise à jour de beta
     * @param evaluation L'evaluation courante du noeud
     * @param beta L'ancienne valeur de beta
     * @return
     */
    protected abstract double beta (double evaluation, double beta);

    /**
     * Retourne le min ou la max entre deux valeurs, selon le type de noeud (MinNode ou MaxNode)
     * @param eval1 Un double
     * @param eval2 Un autre double
     * @return Le min ou la max entre deux valeurs, selon le type de noeud
     */
    protected abstract double minmax (double eval1, double eval2);

    /**
     * Indique s'il faut faire une coupe alpha-beta, selon le type de noeud (MinNode ou MaxNode)
     * @param eval L'evaluation courante du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta Le seuil pour la coupe beta
     * @return Un booleen qui indique s'il faut faire une coupe alpha-beta
     */
    protected abstract boolean alphabeta (double eval, double alpha, double beta);

    /**
     * Retourne un noeud (MinNode ou MaxNode) du niveau suivant
     * @param board L'etat de la grille de jeu
     * @param depth La profondeur du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta Le seuil pour la coupe beta
     * @return Un noeud (MinNode ou MaxNode) du niveau suivant
     */
    protected abstract MinMaxNode getNextNode (Board board, int depth, double alpha, double beta, int[] poids);

    /**
     * L'evaluation du noeud
     * @return L'evaluation du noeud
     */
    double getEvaluation ()
    {
        return this.evaluation;
    }

    /**
     * L'evaluation de chaque coup possible pour le noeud
     * @return
     */
    double [] getDecision ()
    {
        return this.decision;
    }
}

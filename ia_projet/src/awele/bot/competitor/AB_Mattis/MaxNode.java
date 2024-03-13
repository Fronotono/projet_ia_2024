package awele.bot.competitor.AB_Mattis;

import awele.core.Board;

/**
 * @author Alexandre Blansche
 * Noeud Max : estimation du meilleur coup possible pour l'IA
 */
public class MaxNode extends MinMaxNode
{
    /**
     * Constructeur pour un noeud initial
     * @param board La situation de jeu pour laquelle il faut prendre une decision
     */
    MaxNode (Board board,int[] poids)
    {
        this (board, 0, -Double.MAX_VALUE, Double.MAX_VALUE, poids);
    }

    /**
     * Constructeur d'un noeud interne
     * @param board La situation de jeu pour le noeud
     * @param depth La profondeur du noeud
     * @param alphabeta Le seuil pour la coupe alpha-beta
     */
    MaxNode (Board board, int depth, double alpha, double beta, int[] poids)
    {
        super (board, depth, alpha, beta, poids);
    }

    /**
     * Retourne le max
     * @param eval1 Un double
     * @param eval2 Un autre double
     * @return Le max entre deux valeurs, selon le type de noeud
     */
    @Override
    protected double minmax (double eval1, double eval2)
    {
        return Math.max (eval1, eval2);
    }

    /**
     * Indique s'il faut faire une coupe alpha-beta
     * (si l'evaluation courante du noeud est superieure à l'evaluation courante du noeud parent)
     * @param eval L'evaluation courante du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta Le seuil pour la coupe beta
     * @return Un booleen qui indique s'il faut faire une coupe alpha-beta
     */
    @Override
    protected boolean alphabeta (double eval, double alpha, double beta)
    {
        return eval >= beta;
    }

    /**
     * Retourne un noeud MinNode du niveau suivant
     * @param board L'etat de la grille de jeu
     * @param depth La profondeur du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta Le seuil pour la coupe beta
     * @return Un noeud MinNode du niveau suivant
     */
    @Override
    protected MinMaxNode getNextNode (Board board, int depth, double alpha, double beta, int[] poids)
    {
        return new MinNode (board, depth, alpha, beta, poids);
    }

    /**
     * Mise à jour de alpha
     * @param evaluation L'evaluation courante du noeud
     * @param alpha L'ancienne valeur d'alpha
     * @return
     */
    @Override
    protected double alpha (double evaluation, double alpha)
    {
        return Math.max (evaluation, alpha);
    }

    /**
     * Mise à jour de beta
     * @param evaluation L'evaluation courante du noeud
     * @param beta L'ancienne valeur de beta
     * @return
     */
    @Override
    protected double beta (double evaluation, double beta)
    {
        return beta;
    }

    /** Pire score : une petite valeur */
    @Override
    protected double worst ()
    {
        return -Double.MAX_VALUE;
    }
}

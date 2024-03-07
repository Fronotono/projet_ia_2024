package awele.bot.demo.knn1;

import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Alexandre Blansche
 * Premier bot qui utilise l'algorithm k-NN pour faire des predictions
 */
public class Knn1Bot extends DemoBot
{
    private static final int k = 10;
    private Knn1Data data;
    
    /**
     * @throws InvalidBotException
     */
    public Knn1Bot () throws InvalidBotException
    {
        this.setBotName ("k-NN1");
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
     * Pour une situation donnee, on regarde les situations les plus proches dans la base de donnees
     * La priorite pour chaque coup est donnee par le nombre de voisins pour ce coup
     */
    @Override
    public double [] getDecision (Board board)
    {
        int [] x = new int [12];
        int [] holes = board.getPlayerHoles ();
        for (int i = 0; i < 6; i++)
            x [i] = holes [i];
        holes = board.getOpponentHoles ();
        for (int i = 0; i < 6; i++)
            x [i + 6] = holes [i];
        return this.data.countNeighbors (x, Knn1Bot.k);
    }

    /**
     * Creation des donnees
     */
    @Override
    public void learn ()
    {
        this.data = new Knn1Data ();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}

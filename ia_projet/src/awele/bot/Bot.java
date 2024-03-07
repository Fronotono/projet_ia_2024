package awele.bot;

import java.util.ArrayList;

import awele.core.Board;
import awele.core.InvalidBotException;
import awele.run.Main;

/**
 * @author Alexandre Blansche
 * Classe abstraite representant un joueur artificiel pour l'Awele
 * Ce n'est pas la classe à etendre pour le projet, c'est la classe CompetitorBot qu'il faut etendre !
 * Cette classe peut servir à creer des bots qui ne seront pas utilises lors du championnat
 */
public abstract class Bot
{
    private String name;
    private ArrayList <String> authors;

    protected Bot ()
    {
        this.name = "";
        this.authors = new ArrayList <String> ();
    }

    /**
     * Fonction pour donner un nom au bot (soyez imaginatifs !)
     * Doit être appele dans le constructeur des classes derivees
     * @param name Le nom du bot
     */
    protected void setBotName (String name)
    {
        this.name = name;
        Main.getInstance ().print ("Instanciation du bot \"" + name + "\"");
    }

    /**
     * Fonction pour definir tous les auteurs
     * @param names Prenoms et noms des etudiants
     * @throws InvalidBotException Il ne peut y avoir que deux auteurs au maximum !
     */
    protected void setAuthors (String... names) throws InvalidBotException
    {
        if (names.length > 2)
            throw new InvalidBotException ("Trop d'auteurs pour ce bot");
        else
        {
            this.authors = new ArrayList <String> ();
            for (String name: names)
                this.authors.add (name);
        }
    }

    /**
     * Fonction pour rajouter un auteur
     * @param name Prenom et nom de l'etudiant
     * @throws InvalidBotException Il ne peut y avoir que deux auteurs au maximum !
     */
    protected void addAuthor (String name) throws InvalidBotException
    {
        if (this.authors.size () < 2)
            this.authors.add (name);
        else
            throw new InvalidBotException ("Trop d'auteurs pour ce bot");
    }

    /**
     * @return Le nom du bot
     */
    public String getName ()
    {
        return this.name;
    }

    /**
     * @return Le nom des auteurs
     */
    public String getAuthors ()
    {
        String string = this.authors.get (0);
        if (this.authors.size () == 2)
            string += " et " + this.authors.get (1);
        return string;
    }

    @Override
    public String toString ()
    {
        return this.getName ();
    }

    /**
     * Fonction d'initalisation du bot
     * Cette fonction est appelee avant chaque affrontement
     */
    public abstract void initialize ();

    /**
     * Fonction de finalisation du bot
     * Cette fonction est appelee après chaque affrontement
     */
    public abstract void finish ();

    /**
     * Fonction de prise de decision du bot
     * @param board État du plateau de jeu
     * @return Un tableau de six reels indiquant l'efficacite supposee de chacun des six coups possibles
     */
    public abstract double [] getDecision (Board board);

    /**
     * Apprentissage du bot
     * Cette fonction est appelee une fois (au chargement du bot)
     */
    public abstract void learn ();
}

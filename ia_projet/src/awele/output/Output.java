package awele.output;

/**
 * @author Alexandre Blansche
 * Classe abstraite pour gerer les affichages lors des parties d'Awele
 */
public abstract class Output
{
	private boolean anonymous;
	
	protected Output (boolean anonymous)
	{
		this.anonymous = anonymous;
	}
	
    /**
     * Retour à la ligne
     */
    public void print ()
    {
        this.print ("");
    }
    
    /**
     * @param object Affichage d'un objet quelconque
     */
    public void print (Object object)
    {
        this.print (object.toString (), false);
    }
    
    /**
     * @param object Affichage d'un objet quelconque
     * @param anonymous Affichage qui preserve l'anonymat
     */
    public void print (Object object, boolean anonymous)
    {
        this.print (object.toString (), anonymous);
    }
    
    protected void print (String string, boolean anonymous)
    {
    	if (!(this.anonymous && anonymous))
    		this.print (string);
    }
    
    protected abstract void print (String string);

    abstract void initialiaze ();
}

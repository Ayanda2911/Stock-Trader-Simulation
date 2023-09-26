public abstract class  Deployment implements Comparable<Deployment> {
    long timestamp;
    int general, planet;
    int force;
    int quantity;
    int id;

    public Deployment(int id,long ts, int gen, int plan, int f, int q ){
        timestamp = ts;
        general = gen;
        planet = plan;
        force = f;
        quantity = q;
        this.id = id;
    }

    /**
     * Determines if its a sith
     * @return true or false
     */
    public abstract boolean isSith();

    /**
     * Determines if its a Jedi
     * @return true or false
     */
    public abstract boolean isJedi();

    @Override
    public abstract int compareTo(Deployment o) ;

}

public class JediDeployment extends Deployment {

    public JediDeployment( int id, long ts, int gen, int plan, int f, int q) {
        super(id,ts, gen, plan, f, q);
    }

    @Override
    public boolean isSith() {
        return false;
    }
    public boolean isJedi(){return true;}

    @Override
    public int compareTo(Deployment d) {
        // comparing this and d
        // return a negavtive number if this> d
        // in priority and positive if its lower priority

        //if the forces are the same
        if(this.force == d.force){
            // which came earlier
            return this.id - d.id;
            // sith deployment is the same
        }
        // if forces are different
        return this.force - d.force;
    }
}

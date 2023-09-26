public class SithDeployment extends Deployment {

    public SithDeployment( int id, long ts, int gen, int plan, int f, int q) {
        super(id,ts, gen, plan, f, q);
    }

    @Override
    public boolean isSith() {
        return true;
    }

    @Override
    public boolean isJedi(){return false;}

    @Override
    public int compareTo(Deployment d) {
        // comparing this and d
        // return a negative number if this> d
        // in priority and positive if its lower priority

        //if the forces are the same
        if(this.force == d.force){
            // which came earlier
            return this.id - d.id;
            // sit deployment is the same
        }
        // if forces are different

        return d.force - this.force;

    }
}

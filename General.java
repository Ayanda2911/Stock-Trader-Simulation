public class General {
     public int numJedi, numSith, numSurvive, totaldeployed, id;

    public General(int id){
        numJedi = 0;
        numSith = 0;
        numSurvive = 0;
        this.id = id;
    }

    public void addDeployment(Deployment d) {
        totaldeployed += d.quantity;
        if(d.isJedi()){
            numJedi += d.quantity;
        }
        else{
            numSith += d.quantity;
        }
    }

    public void addSurvivors( int s){
        numSurvive += s  ;
    }


    public void printStat() {
        System.out.print("General " + id + " deployed " + numJedi + " Jedi troops and " + numSith  +" Sith troops, and "
                +numSurvive +"/" + totaldeployed +  " troops survived.\n");
    }
}

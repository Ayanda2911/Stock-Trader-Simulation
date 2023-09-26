import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Planet {
    private PriorityQueue<Deployment> jediDeployments;
    private PriorityQueue<Deployment> sithDeployments;
    // 2 priority queues for the median
    private PriorityQueue<Integer> lowMed;
    private PriorityQueue<Integer> upperMed;
    private int median;
    private int troopsLost;
    private ArrayList<Integer> troops;
    private int id;
    int numBattles;

    public Planet(int id) {
        this.id = id;

        jediDeployments = new PriorityQueue<>();
        sithDeployments = new PriorityQueue<>();
        lowMed = new PriorityQueue<>(Collections.reverseOrder());
        upperMed = new PriorityQueue<>();
        numBattles = 0;
        troops = new ArrayList<>();

    }

    /**
     * Add deployment
     *
     * @param d deployment to be added to priorityQueue
     */
    public void addDeployment(Deployment d) {

        if (!(d.isSith() || d.isJedi())) {
            System.err.println(" Invalid deployment");
            System.exit(1);
        } else {
            if (d.isSith()) {
                sithDeployments.add(d);
            } else {
                jediDeployments.add(d);
            }
        }
    }

    /**
     * check if a battle can take place
     *
     * @return true or false
     */
    public boolean canBattle() {
        // battles can happen if
        //  they have opposing sides
        // jediforce is less than the sith force
        if (jediDeployments.isEmpty() || sithDeployments.isEmpty()) {
            return false;
        }

        return jediDeployments.peek().force <= sithDeployments.peek().force;
    }


    /**
     * Perform Battles
     */
    public void performBattles(Config c) {
        while (canBattle()) {
            // a battle can occur
            // subtract the same number of troops from both sith and jedi deployments
            troopsLost = Math.min(jediDeployments.peek().quantity, sithDeployments.peek().quantity);


            // subtract these from both deployments
            jediDeployments.peek().quantity -= troopsLost;
            sithDeployments.peek().quantity -= troopsLost;



            // print output before removing from pq
            if (c.verbose) {
                System.out.print("General " + sithDeployments.peek().general + "'s battalion attacked General " + jediDeployments.peek().general
                        + "'s battalion on planet " + id + ". " + troopsLost * 2 + " troops were lost.\n");
            }

            // one of these deployments will have lost all the troops
            // remove that deployment from the PQ
            if (jediDeployments.peek().quantity == 0) {
                jediDeployments.remove();
            }
            if (sithDeployments.peek().quantity == 0) {
                sithDeployments.remove();
            }

            numBattles++;
            if (c.median) {
                troops.add(troopsLost * 2);
                calculateMedian();
            }
        }

    }

    /**
     * Keeps track of the median
     */
    public int calculateMedian() {
        for( int t  : troops ) {
            if (upperMed.isEmpty() && lowMed.isEmpty()) {
                upperMed.add(t);
                median = t;
            } else {
                // decide top half or bottom based on size relative to median
                if (t < median) {
                    lowMed.add(t);
                } else {
                    upperMed.add(t);
                }
                // balance the sizes of our PQs
                if (lowMed.size() - upperMed.size() == 2) {
                    // shift one element from bottom to
                    upperMed.add(lowMed.remove());
                } else if (upperMed.size() - lowMed.size() == 2) {
                    lowMed.add(upperMed.remove());
                }

                // update median value
                if (lowMed.size() > upperMed.size()) {
                    median = lowMed.peek();
                } else if (upperMed.size() > lowMed.size()) {
                    median = upperMed.peek();
                } else {
                    median = (upperMed.peek() + lowMed.peek()) / 2;
                }
            }
        }
        lowMed.clear();
        upperMed.clear();
        return median;

    }


    public int getNumBattles() {
        return numBattles;
    }

    public int getId() {
        return id;
    }

    public void countSurvivors(ArrayList<General> generals) {
        int count;
        // go through the sith PQ
        // pop each deployment
        // count for the appropriate general
        for (General g : generals) {
        for (Deployment s : sithDeployments) {
            if(g.id ==s.general){
                count = s.quantity ;
                generals.get(g.id).addSurvivors(count);}
            }

        for (Deployment j : jediDeployments) {
            if(g.id ==j.general){
                count = j.quantity ;
                generals.get(g.id).addSurvivors(count);}
        }
        }
    }
}




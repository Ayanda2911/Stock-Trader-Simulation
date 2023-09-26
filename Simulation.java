import java.util.ArrayList;
import java.util.Scanner;

public class Simulation {
    // keeps track of list of generals and planets
    private ArrayList<General>generals;
    private ArrayList<Planet> planets;

    private Config config;
    private Scanner in;
    private int currId  = 0;
    private int numGenerals;
    private int numPlanets;

    public Simulation(Config c){
        config = c;
        in = new Scanner(System.in);

        // skip first line;  it's a comment
        in.nextLine();


        // skip the header
        in.next();
        // read the mode
        String mode  = in.next();

        // read the number of generals
        in.next();
        numGenerals = in.nextInt();

        in.next();
        numPlanets = in.nextInt();

        generals = new ArrayList<>(numGenerals);

        for(int i = 0; i < numGenerals; i++){
            generals.add(new General(i));
        }

        planets = new ArrayList<>();
        for(int i = 0; i < numPlanets; i++){
            planets.add(new Planet(i));
        }

        // if PR mode finalise our
        if(mode.equals("PR")){
            // random seed
            in.next();
            int seed = in.nextInt();

            // num deployments
            in.next();
            int numDeployments = in.nextInt();

            // arrival date
            in.next();
            int arrivalRate = in.nextInt();

            // create our random deployment generator
            in = P2Random.PRInit(seed, numGenerals, numPlanets, numDeployments, arrivalRate);
        }
    }

    /**
     * read the deployments and perform warfare
     */
    public void performWarfare(){
        // initialise time
        long currentTime = 0 ;
        // print out simulation starting message
        System.out.println("Deploying troops...");

        while(in.hasNextLong()){
            // get next deployment
            Deployment d = getNextDeployment();
            // ERROR CHECK : if timestamps are not in descending order
            if(d.timestamp - currentTime < 0 ){
                System.err.println(" The time stamps are descending");
                System.exit(1);
            }

            if(currentTime < d. timestamp){

            if(config.median)  {
                for(Planet p : planets){
                    printMedian(p, currentTime);
                }
            }
                // update Timestamp
                currentTime = d.timestamp;
            }

            // get the planet from planet
            Planet p = planets.get(d.planet);
            // add deployment to planet
            p.addDeployment(d);
            // track the deployment for general eval mode
            generals.get(d.general).addDeployment(d);
            //if verbose is set  perform battles in verbose output
            p.performBattles(config);


        }


        if(config.median)  {
            for(Planet p : planets){
                printMedian(p, currentTime);
            }
        }

        // no more battles
        // print out summary output
        System.out.print("---End of Day---\n");

        int totalBattles = 0;

        for(Planet p: planets){
            totalBattles += p.getNumBattles();
        }

        System.out.print("Battles: " + totalBattles + "\n");

        if(config.general){
            System.out.println("---General Evaluation---");
            for( Planet p: planets){
                // count up survivors and add them to the appropriate general
                p.countSurvivors(generals);
            }

            for(General g: generals){
                g.printStat();
            }
        }
    }

    private Deployment getNextDeployment(){
        // timestamp, sith/jedi, general, planet_num, force_sensitivity, num-troops
        long timestamp = in.nextLong();
        // ERROR CHECK
        if(timestamp < 0){
            System.err.println("Invalid timestamp: " + timestamp);
            System.exit(1);
        }
        String type = in.next();

        int generalId = Integer.parseInt(in.next().substring(1));

        // ERROR CHECK : valid number of generals
        if(generalId <0 || generalId >= numGenerals){
            System.err.println("Invalid general ID: " + generalId);
            System.exit(1);
        }

        int planetId = Integer.parseInt(in.next().substring(1));

        // ERROR CHECK
        if(planetId < 0 || planetId >= numPlanets){
            System.err.println(" Invalid planet ID:"+ planetId);
            System.exit(1);
        }

        int force = Integer.parseInt(in.next().substring(1));

        // ERROR CHECK
        if(force <= 0){
            System.err.println("Force is invalid");
            System.exit(1);
        }

        int numTroops = Integer.parseInt(in.next().substring(1));

        if(numTroops <= 0){
            System.err.println("The number of troops is invalid");
            System.exit(1);
        }


        if(type.equals("SITH")){
            return new SithDeployment(currId++, timestamp, generalId, planetId, force, numTroops);
        }else{
            return new JediDeployment(currId++, timestamp, generalId, planetId, force, numTroops);
        }
    }


    public void printMedian(Planet p, long timestamp) {
        if(p.getNumBattles() == 0) {
            return;
        } else{
            System.out.printf("Median troops lost on planet " + p.getId() + " at time " + timestamp +  " is " + p.calculateMedian() + ".\n" );
        }
    }
}


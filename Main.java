public class Main {

    public static void main(String[]args){
        Config c = new Config(args);
        Simulation s = new Simulation(c);
        s.performWarfare();
    }
}

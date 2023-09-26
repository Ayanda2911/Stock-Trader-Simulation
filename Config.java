import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

public class Config {
    // holds all the configuaration options
    boolean verbose, median, general;

    public Config(String[] args) {
        LongOpt[] longOptions = {
                new LongOpt("verbose", LongOpt.NO_ARGUMENT, null, 'v'),
                new LongOpt("median", LongOpt.NO_ARGUMENT, null, 'm'),
                new LongOpt("general-eval", LongOpt.NO_ARGUMENT, null, 'g'),
                new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'),


        };
        Getopt g = new Getopt("project2", args, "vmgw", longOptions);
        g.setOpterr(true);

        int option;

        ////////////// While loop/////////////////////////////////
        while ((option = g.getopt()) != -1) {
            // option stores a command line argument to process
            // figure out which option we are processing
            switch (option) {
                case 'v':
                    // check for the correct mode and print
                    verbose = true;
                    break;
                case 'm':
                    median = true;
                    break;
                case 'g':
                    general = true;
                    break;
                case 'h':
                    System.out.print("""
                             This program simulates galactic warfare\s
                             This program takes any of the following command line inputs:
                            --verbose (-v) : Generates output in verbose mode\s
                            --median (): Generates median output
                            --general-eval(-g): Generates a general evaluation
                            """);
                default:
                    System.err.println("Error: Invalid Option");
                    System.exit(1);
            }
        }
    }
}

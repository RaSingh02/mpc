package main;

public class Waiter implements Runnable {
    private final static int MAX_WAITER_MILLIS = 4000;
    private final static int N_COURSES = 3;

    private Table[] tables;
    private String waiterName;
    private String[] customerNames;
    private String[][] courses;

    /**
     * Default constructor, initializes the data.
     * @param tables Array of tables
     * @param waiterName Names of the waiters
     * @param customerNames Names of customers
     * @param courses The menu items customers ordered
     */
    public Waiter(Table[] tables, String waiterName, String[] customerNames, String[][] courses) {
        this.tables = tables;
        this.waiterName = waiterName;
        this.customerNames = customerNames;
        this.courses = courses;
    }

    /**
     * A random time gets generated for this waiter object to serve the course.
     * @return Time
     */
    private int serveTime() {
        return (int)(Math.random() * MAX_WAITER_MILLIS);
    }

    /**
     * For each customer, a thread on this Waiter object serves the three courses
     * in the correct order by calling the serve() method in the corresponding Table,
     * prints out what course is served to which Customer, and sleeps for a random
     * time between 0 and 4 seconds to mimic time taken in serving.
     */
    @Override
    public void run() {
        for (int i = 0; i < customerNames.length; i++) {
            for (int j = 0; j< N_COURSES; j++) {
                System.out.println(this.waiterName + " serves " + customerNames[i] + ": " + courses[i][j]);
                tables[i].serve(courses[i][j]);

                try {
                    Thread.sleep(serveTime());
                } catch (InterruptedException ie) {
                    System.err.println(ie.getMessage());
                }
            }
            tables[i].serve("DONE");
        }
    }
}

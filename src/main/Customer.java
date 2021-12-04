package main;

public class Customer implements Runnable{
    private final static int MAX_CUSTOMER_MILLIS = 4000;
    private Table table;
    private String customerName;
    private String courseName;

    /**
     * Initializes the Data
     * @param table Where the customer sits
     * @param customerName The name of the customer.
     */
    public Customer( Table table, String customerName ) {
        this.table = table;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    /**
     * A random time gets generated for this customer to eat the course.
     * @return Time
     */
    private int eatTime() {
        return (int)(Math.random() + MAX_CUSTOMER_MILLIS);
    }

    /**
     * For each customer, a thread on this Customer object eats the three courses
     * in the correct order by calling the eat() method in the corresponding Table,
     * prints out what course this Customer is eating, and sleeps for a random time
     * between 0 and 4 seconds to mimic time taken to eat.
     */
    public void run() {
        courseName = table.eat();

        while(!courseName.equals("DONE")) {
            System.out.println(customerName + " is eating " + courseName);

            try {
                Thread.sleep(eatTime());
            } catch (InterruptedException ie) {
                System.err.println("Unable to wait while customer is eating " + courseName + ": " + ie.getMessage());
            }
            courseName = table.eat();;
        }
    }
}

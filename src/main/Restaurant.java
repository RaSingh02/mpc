package main;

import java.util.*;
import java.io.*;

public class Restaurant{

    private String fileName;
    private int numWaiters = 0;

    /**
     * Default constructor.
     */
    public Restaurant() {};

    /**
     * Reads in the file name and sets it to the fileName variable.
     */
    private void getFileInput() {
        Scanner file_input = new Scanner(System.in);
        System.out.println("Enter a file name: ");
        fileName = file_input.nextLine().toString();
        file_input.close();
    }

    /**
     * Converts the Customer list to a CustomerNames list.
     * @param customers The Customer list.
     * @paramm CustomerNames[] An array that holds all the customers names for each waiter
     */
    private String[] customersToNames(List<Customer> customers) {
        List<String> customerNames = new ArrayList<String>();
        for (Customer customer : customers) {
            customerNames.add(customer.getCustomerName());
        }
        return customerNames.toArray(new String[0]);
    }

    /**
     * Parses the line from the file to create and run the Waiter and
     * Customer instances.
     * @param line The line(s) from the file
     */
    private void parseData(String line) {
        try {
            final String[] lineArray = line.split(" ");
            final String waiterName = lineArray[0];
            final int numCustomers = Integer.parseInt(lineArray[1]);
            final Table[] tablesArray;
            final Thread t;

            List<Customer> customers = new ArrayList<Customer>();
            List<Table> tables = new ArrayList<Table>();
            List<List<String>> courses = new ArrayList<List<String>>();

            // Importing customer names and their courses to their respective variables.
            for (int i = 0; i < numCustomers; i++) {
                final int j = (i * 4) + 2;
                List<String> coursesForCustomer = new ArrayList<String>();
                Table table = new Table();
                customers.add(new Customer(table, lineArray[j]));
                for (int h = 1; h <= 3; h++) {
                    coursesForCustomer.add(lineArray[j + h]);
                }
                tables.add(table);
                courses.add(coursesForCustomer);
            }

            // Converting the courses variable to courseArray, which is a matrix (String[][])
            String[][] coursesArray = new String[courses.size()][];
            int i = 0;
            for (List<String> course: courses) {
                coursesArray[i++] = course.toArray(new String[course.size()]);
            }

            tablesArray = tables.toArray(new Table[0]);
            Thread w = new Thread(new Waiter(tablesArray, waiterName, customersToNames(customers), coursesArray));

            w.start();
            for (Customer customer : customers) {
                final Thread thread = new Thread(customer);
                thread.start();
            }
        } catch (NullPointerException npe) {
            System.err.println("Unable to parse data from file" + fileName);
        }
    }

    /**
     * Reads the file contents.
     */
    private void readFileContents() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            numWaiters = Integer.parseInt(br.readLine());

            for (int i = 0; i < numWaiters; i++) {
                parseData(br.readLine());
            }

            br.close();
        } catch (IOException ioe) {
            System.err.println("IO Error while reading file " + fileName + ioe.getMessage());
        }
    }

    /**
     * Main method for program.
     * @param args Where the program runs.
     */
    public static void main(String[] args) {
        final Restaurant restaurant = new Restaurant();
        restaurant.getFileInput();
        restaurant.readFileContents();
    }
}

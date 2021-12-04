package main;

public class Table {
    private String course;
    private boolean isEmpty;

    // Default Constructor
    public Table() {
        course = null;
        isEmpty = true;
    }

    /**
     * Sets the isEmpty variable based on input.
     * @param isEmpty Variable to see if table is empty.
     */
    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    /**
     * Waiter serving the course to customer.
     * @param course The course to server to customer.
     */
    public synchronized void serve(String course) {
        while(!isEmpty) {
            try {
                wait();
            } catch (InterruptedException ie){
                System.err.println("Unable to serve " + course + ": " + ie.getMessage());
            }
        }
        this.course = course;
        isEmpty = false;
        notifyAll();
    }

    /**
     * Customer eating the course.
     * @return course
     */
    public synchronized  String eat() {
        while (isEmpty){
            try {
                wait();
            } catch(InterruptedException e){
                System.err.println("Unable to eat course " + course + ": " + e.getMessage());
            }
        }
        isEmpty = true;
        notifyAll();
        return course;
    }
}

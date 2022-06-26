package p2;
import java.util.*;

/**
 * Process-Resource Graph based implementation of the PRGraph interface.
 * This implementation provides optional graph operations, and not permits null values and the null key.
 * An instance of Graph is implemented by Arraylist of Nodes, that is created as a private static class.
 * @author Daria Melnyk
 */

public class PRGraphImpl implements PRGraph{

    private ArrayList<Node> list;

    /**
     * Constructor with no parameters - creating a new graph of nodes
     */
    public PRGraphImpl(){
        list = new ArrayList<Node>();
    }

    /**
     * Inserts a new node of type process to the graph calling the method add
     * @param name - the name of process that is wanted to add
     */
    @Override
    public void addProcess(String name) {
        Node n = new Node(name, "process");
        add(name, n);
    }

    /**
     * Inserts a new node of type resource to the graph calling the method add
     * @param name - the name of resource that is wanted to add
     */
    @Override
    public void addResource(String name) {
        Node n = new Node(name, "resource");
        add(name, n);
    }

    /**
     * Inserts new nodes to the graph if there isn't the same already.
     * @param name - name of process or resource for checking if it's already existing
     *             by calling method isInList
     * @param n - node that is wanted to add in list
     */
    private void add(String name, Node n) {
        if(list.size() == 0) {
            list.add(n);
        } else{
            if(!isInList(name)){
                list.add(n);
            }
        }
    }

    /**
     * Checks if the process or resource is already existing in list by looking through
     * all of them. The same names for resources and process also are NOT permitted.
     * @param name - the name of process or resource
     * @return true if node with the same name exists
     */
    private boolean isInList(String name){
        for(Node node : list){
            if(Objects.equals(node.getKey(), name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Assigns the resource to process by adding it to the value of node, if the process was called
     * for the first time. If not, sets that the process owns the resource. If the resource is already
     * used by other process, it will be added to the waiting list of that process. We also check if the
     * types of passed parameters as the names of process and resource are appropriate. In addition,
     * before adding the connection to the graph, we are checking if it will create a deadlock by calling
     * function lastInChain. If it will be created we are printing the message with line that will create
     * a deadlock and put the appropriate resource to a waiting list of the process.
     * @throws IllegalArgumentException if the process or resource wasn't previously added to the graph
     * @param process - the name of opening process
     * @param resource - the name of resource to be connected with process
     */
    @Override
    public void open(String process, String resource) {
        boolean flag = false;
        if (!isInList(process) || !isInList(resource) || Objects.equals(process, resource)) {
            throw new IllegalArgumentException("The process" + process + " or resource" + resource + " haven't been added earlier. Please, first add and then try to open!!!");
        }
        String type1 = null;
        String type2 = null;
        for(Node node : list){
            if(Objects.equals(node.getKey(), process)) {
                type1 = node.getType();
            }
            if(Objects.equals(node.getKey(), resource)) {
                type2 = node.getType();
            }
        }
        if (Objects.equals(type1, type2)) {
            throw new NullPointerException();
        }
        for(Node node : list){
            if(Objects.equals(node.getKey(), resource) && !node.isValue()) {
                if (Objects.equals(resource, lastInChain(process))) {
                    node.addWaiting(process);
                    throw new NullPointerException();
                }
                flag = true;
                node.setValue(process);
                break;
            }
        }
        if(!flag) {
            for (Node node : list) {
                if (Objects.equals(node.getKey(), process)) {
                    if (!node.isValue()) {
                        if (Objects.equals(process, lastInChain(resource))) {
                            node.addWaiting(resource);
                            //throw new NullPointerException();
                            System.out.println("DEADLOCK ALERT! PROCESS " + process + " TRIES TO OPEN RESOURCE " + resource
                                    + ". THAT WILL CREATE A DEADLOCK!\nResource " + resource + " will be added to process` " + process + " waiting list!\n");
                            break;
                        }
                        if (node.isWaiting()) {
                            node.addWaiting(resource);
                            break;
                        }
                        node.setValue(resource);
                        break;
                    } else {
                        node.addWaiting(resource);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Checks if opening the process with the resource will create a deadlock
     * by looking throw the nodes beginning with the parameter
     * @param head - the name of resource or process
     * @return the String that represents name element if it has the value
     */
    private String lastInChain(String head){
        String element = head;
        boolean flag;
        do {
            flag = false;
            for(Node node : list){
                if(Objects.equals(node.getKey(), element) && node.isValue()) {
                    element = node.getValue();
                    flag = true;
                    break;
                }
            }
        } while (flag);
        return element;
    }

    /**
     * Closes the process by deleting its connection with the resource
     * @throws IllegalStateException if process and resource aren't connected
     * @param process - name of process to be closed
     * @param resource - name of resource
     */
    @Override
    public void close(String process, String resource) {
        boolean flag = false;
        if (!isInList(process) || !isInList(resource) || Objects.equals(process, resource)) {
            throw new NullPointerException();
        }
        for(Node node : list){
            if(Objects.equals(node.getKey(), resource) && Objects.equals(node.getValue(), process)) {
                flag = true;
                node.setValue(null);
                break;
            }
        }
        if(!flag){
            for(Node node : list){
                if(Objects.equals(node.getKey(), process) && Objects.equals(node.getValue(), resource)) {
                    node.setValue(null);
                    flag = true;
                    break;
                }
            }
        }
        if(!flag) throw new IllegalStateException("This process "+ process + " is not relied to resource " + resource);
        for(Node node : list){
            if(node.isWaiting()) {
                if (!Objects.equals(node.getFirstWaiting(), lastInChain(node.getKey()))) {
                    node.moveWaiting();
                }
            }
        }
    }

    /**
     * Shows processes and resources. Indicating for each process which	resources
     * it owns and	if it’s	waiting	for	any. Also, indicating for each resource	if
     * it’s	assigned to	any	process.
     * @return the string which represents the graph
     */
    public String toString(){
        StringBuilder output = new StringBuilder();
        if(list.size() == 0){
            output.append("Graph is empty");
        }else{
            output.append("Graph\n");
            for(Node node : list){
                output.append(node.getType() + " " + node.getKey());
                if (Objects.equals(node.getType(), "process")){
                    if (node.isValue()) output.append(" owns " + node.getValue());
                    if (node.isWaiting()) output.append(" waiting for " + node.getaAllWaiting());
                }
                if (Objects.equals(node.getType(), "resource")){
                    if (node.isValue()) output.append(" assigned to " + node.getValue());
                }
                output.append("\n");
            }
        }
        return output.toString();
    }

    /**
     * Basic graph node, used for representing processes and resources.
     * @author Daria Melnyk
     */
    private static class Node {

        private final String type; //The type of node that equals process or resources
        private final String key; // the name of node
        private String value; // name of assigned process or resource
        private ArrayList<String> waiting = new ArrayList<String>(); // arraylist of waiting resources

        /**
         * Constructor for creating the node with specific type and name
         * @param key - the name of node
         * @param type - the type of node (process or resource)
         */
        public Node(String key, String type) {
            this.key = key;
            this.type = type;
        }

        /**
         * Gets the first element of waiting resources
         * @return the String name of first resource
         */
        public String getFirstWaiting() {
            return waiting.get(0);
        }

        /**
         * Returns the line of waiting elements.
         * @return the string representing waiting resources
         */
        public String getaAllWaiting() {
            StringBuilder sb = new StringBuilder(waiting.get(0));
            for (int i = 1; i < waiting.size(); i++) {
                sb.append(", ");
                sb.append(waiting.get(i));
            }
            return sb.toString();
        }

        /**
         * Adds the resource to the waiting list.
         * @param waiting - name of process to be added
         */
        public void addWaiting(String waiting) {
            this.waiting.add(waiting);
        }

        /**
         * Returns if there are any waitings
         * @return true if the size of waiting list isn't zero
         */
        public boolean isWaiting() {
            return waiting.size() > 0;
        }

        /**
         * Assigns the value of first element in list of waiting. And moves all
         * the rest elements to the left.
         */
        public void moveWaiting() {
            ArrayList<String> w = new ArrayList<String>();
            value = this.getFirstWaiting();
            for (int i = 1; i < waiting.size(); i++) {
                w.add(waiting.get(i));
            }
            waiting = w;
        }


        /**
         * Returns the type of node
         * @return the String representing the type
         */
        public String getType() {
            return type;
        }
        /**
         * Returns the name of node
         * @return the String representing the name
         */
        public String getKey() {
            return key;
        }
        /**
         * Returns the value of node
         * @return the String representing the value
         */
        public String getValue() {
            return value;
        }
        /**
         * Sets the value of node
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Checks if the node has a value
         * @return true if the value of node isn't null
         */
        public boolean isValue() {
            return value != null;
        }
    }
}

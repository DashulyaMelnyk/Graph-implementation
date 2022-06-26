package p2;

/**
 * Interface of graph representing connections between processes and resources
 */
// Resource allocation graph
public interface PRGraph {
    // Adds a process to the graph
    void addProcess(String name);
    // Adds a resource to the graph
    void addResource(String name);
    // Requests a resource
    void open(String process, String resource);
    // Frees a resource
    void close(String process, String resource);
}
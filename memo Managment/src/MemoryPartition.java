public class MemoryPartition {
    public String name;
    public int size;
    boolean taken;
    public Process process;
    MemoryPartition(String n , int s){
        name = n;
        size = s;
        taken = false;
        process = null;
    }

    @Override
    public String toString() {
        if(!taken){  // if partition is empty
            return name  +  " (" + size + " KB) => External fragment"  ;
        }
        else{
            return name  +  " (" + size + " KB) => "+ process.name  ;
        }

    }
}

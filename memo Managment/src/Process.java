public class Process {
    public String name;
    public int size;
    Process(String n ,int s ){
        name = n;
        size = s;
    }
    @Override
    public String toString() {
        return "name='" + name + '\'' +  ", size=" + size ;
    }
}

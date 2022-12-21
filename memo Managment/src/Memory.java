import java.util.Scanner;
import java.util.Vector;

public class Memory {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int partitionsNumber , processesNumber , size;
        String name;
        Process process;
        MemoryPartition partition;
        Vector<Process> processes = new Vector<Process>();
        Vector<MemoryPartition> memoryPartitions = new Vector<MemoryPartition>();
        //LinkedList<MemoryPartition> memoryPartitions = new LinkedList<MemoryPartition>();
        System.out.print("Enter number of partitions: ");
        partitionsNumber = input.nextInt();
        for (int i = 0; i < partitionsNumber; i++) {
            name = input.next();
            size = input.nextInt();
            partition= new MemoryPartition(name,size);
            memoryPartitions.add(partition);
        }
        System.out.print("Enter number of Processes: ");
        processesNumber = input.nextInt();
        for (int i = 0; i < processesNumber; i++) {
            name = input.next();
            size = input.nextInt();
            process = new Process(name,size);
            processes.add(process);
        }
        System.out.println("Select the policy you want to apply:\n" + "1. First fit\n" + "2. Worst fit\n" + "3. Best fit");
        int choice = input.nextInt();
        if(choice == 1){
            Vector<Process> notAddedProcesses = new Vector<Process>();
            for (int i = 0; i < processesNumber; i++) {
                Process currentProcess = processes.get(i);
                boolean state = true;
                for (int j = 0; j < partitionsNumber; j++) {
                    if(currentProcess.size <= memoryPartitions.get(j).size && !memoryPartitions.get(j).taken){
                        int newPartitionSize;
                        String newPartitionName;
                        memoryPartitions.get(j).process = currentProcess;
                        memoryPartitions.get(j).taken = true;
                        newPartitionSize = memoryPartitions.get(j).size - currentProcess.size;
                        newPartitionName = "Partition" + partitionsNumber++;
                        memoryPartitions.get(j).size= currentProcess.size;
                        MemoryPartition newPartition = new MemoryPartition( newPartitionName, newPartitionSize);
                        memoryPartitions.add(j+1 ,newPartition);   // to add the new partition right after the current partition

                        state = false;
                        break;
                    }
                }
                if (state){
                    notAddedProcesses.add(currentProcess);
                    //System.out.println(currentProcess.name +" cannot be added");
                }

            }
            for (MemoryPartition memoryPartition : memoryPartitions) {
                System.out.println(memoryPartition);
            }
            System.out.println();
            for (Process notAddedProcess : notAddedProcesses) {
                System.out.println(notAddedProcess.name + " cannot be added");
            }

            System.out.println("\nDo you want to compact? 1.yes 2.no");
            int compactChoice = input.nextInt();
            if (compactChoice == 1){
                compact(notAddedProcesses,memoryPartitions,partitionsNumber);
            }
        }
        else if (choice ==2){


            for (MemoryPartition memoryPartition : memoryPartitions) {
                System.out.println(memoryPartition);
            }
        }
        else if(choice == 3){


            for (MemoryPartition memoryPartition : memoryPartitions) {
                System.out.println(memoryPartition);
            }
        }
        else
            System.out.println("ERROR : please try again ");

    }
    public static void compact (Vector<Process> notAddedProcesses , Vector<MemoryPartition> memoryPartitions, int partitionsNumber){
        MemoryPartition newPartition = new MemoryPartition( "null", 0); // null values for initialization
        for (int i = 0; i < memoryPartitions.size(); i++) {
            if(!memoryPartitions.get(i).taken){
                //System.out.println(memoryPartitions.get(i).name + " to be removed");
                newPartition.size = newPartition.size + memoryPartitions.get(i).size; // to add all sizes to new partition
                memoryPartitions.remove(memoryPartitions.get(i));  // to remove unused external fragments
                i--;
            }
        }
        newPartition.name = "Partition" + partitionsNumber++;
        memoryPartitions.add(newPartition);     // to add the new compact partition
        for (int i = 0; i < notAddedProcesses.size(); i++) {
            Process currentProcess = notAddedProcesses.get(i);
            for (int j = 0; j < memoryPartitions.size(); j++) {
                if(currentProcess.size <= memoryPartitions.get(j).size && !memoryPartitions.get(j).taken){
                    int newPartitionSize;
                    String newPartitionName;
                    memoryPartitions.get(j).process = currentProcess;
                    memoryPartitions.get(j).taken = true;
                    newPartitionSize = memoryPartitions.get(j).size - currentProcess.size;
                    newPartitionName = "Partition" + partitionsNumber++;
                    memoryPartitions.get(j).size= currentProcess.size;
                    MemoryPartition extraPartition = new MemoryPartition( newPartitionName, newPartitionSize);
                    memoryPartitions.add(j+1 ,extraPartition);   // to add the new partition right after the current partition
                    notAddedProcesses.remove(currentProcess);  // to remove newly added process form not added vector

                    break;
                }
            }
        }
        for (MemoryPartition memoryPartition : memoryPartitions) {
            System.out.println(memoryPartition);
        }
        System.out.println();
        for (Process notAddedProcess : notAddedProcesses) {
            System.out.println(notAddedProcess.name + " cannot be added");
        }
    }
}

/*
Partition0 90
Partition1 20
Partition2 5
Partition3 30
Partition4 120
Partition5 80

Process1 15
Process2 90
Process3 30
Process4 100

*/

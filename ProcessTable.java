import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Erick on 4/8/2017.
 * Program implements a process table
 */
public class ProcessTable {
    public static void main(String[] args){
        ProcessTable pt = new ProcessTable();
        System.out.println(pt.toString());
        pt.Fork();
        System.out.println(pt.toString());

    }
    /**
     * Process Table
     * Structure contains the Process Table of the CPU
     */
    private ArrayList<Process> table;
    /**
     * Contains the contents of the CPU
     */
    private int[] CPU  = new int[6];
    /**
     * Contains the process that is running
     */
    private Process runningProcess;

    /**
     * Keeps the number of processes running
     */
    private int pid;

    public ProcessTable(){
        this.table = new ArrayList<>();
        int[] tempReg = {1,2,3,4,5,6};
        pid = 0;
        Process temp = new Process(pid,"init","root",0,tempReg);
        table.add(temp);
        this.CPU = temp.getRegisters();
        this.runningProcess = temp;

    }
    public void Fork(){
        Process temp  = new Process(this.runningProcess);
        temp.setStatus(1);
        this.pid++;
        temp.setPid(this.pid);
        this.table.add(temp);

    }

    public String toString(){
        String str = String.format("CPU:\n PC = %10s SP = %10s\n R0 = %10s R1 = %10s\n R2 = %10s R3 = %10s\n",
                Integer.toHexString(this.CPU[0]),Integer.toHexString(this.CPU[1]),Integer.toHexString(this.CPU[2]),
                Integer.toHexString(this.CPU[3]),Integer.toHexString(this.CPU[4]),Integer.toHexString(this.CPU[5])) ;
        str = str + "\nProcess Table:\n" + (new Process()).header;
        for(int i =0; i<this.table.size();i++){
            str = str + table.get(i).toString() + "\n";
        }
        return str;
    }

}

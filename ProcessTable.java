import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Erick on 4/8/2017.
 * Program implements a process table
 */
public class ProcessTable {
    public static void main(String[] args){
        ProcessTable pt = new ProcessTable();
//        pt.Fork();
//        int[] reg = {1,2,3,4,5,6};
//        pt.table.add(new Process(3,"init","user",0,reg));
        System.out.println(pt.toString());
        pt.Kill(3);
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
        this.runningProcess = new Process(temp);
    }

    /**
     * This method makes a copy of the currently running process with a new status 1 (ready) and a
     new unique pid. The program, user, and register contents are the same as the running process
     */
    public void Fork(){
        Process temp  = new Process(this.runningProcess);
        temp.setStatus(1);
        this.pid++;
        temp.setPid(this.pid);
        this.table.add(temp);
    }

    /**
     * This method kills the process with the specified process id only if the currently running process
     * - has user set as "root" or
     * - has the same user set as the process being killed.
     * @param pidToKill
     */
    public void Kill(int pidToKill){
        boolean allow = false;
        if(this.runningProcess.getUser() == "root"){
            allow = true;
        }
        int i =0;
        boolean found= false;
        while(!found && i < table.size()){
            if(pidToKill== table.get(i).getPid()) {
                found = true;
                i--;
            }
            i++;
        }
        if(i < table.size() && table.get(i).getUser() == this.runningProcess.getUser())
            allow = true;
        if(allow && found)
            table.remove(i);


    }

    /**
     * Finds the Process in the Process Table
     * @param temp the Process to find
     * @return the index of the process temp, or -1 if its not found inside the Process Table
     */
    public int  find(Process temp){

        boolean found = false;
        int i =0;
        while(!found && i < table.size()) {
            if(temp.equals(table.get(i)))
                found = true;
            else
                i ++;
        }
        if(found)
            return i;
        else
            return -1;
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

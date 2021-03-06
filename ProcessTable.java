import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Erick on 4/8/2017.
 * Program implements a process table
 */
public class ProcessTable {
    public static void main(String[] args){
        ProcessTable pt = new ProcessTable();
        Scanner input = new Scanner(System.in);
        while(true){
            String str = input.next();
            switch (str){
                case("fork"):
                    pt.Fork();
                    break;
                case("kill"):
                    int temp = input.nextInt();
                    pt.Kill(temp);
                    break;
                case("execve"):
                    String newProgram = input.next();
                    String newUser = input.next();
                    pt.Exec(newUser,newProgram);
                    break;
                case("block"):
                    pt.Block();
                    break;
                case("yield"):
                    pt.Yield();
                    break;
                case("exit"):
                    pt.Exit();
                    break;
                case("print"):
                    pt.Print();
                    break;
                case("unblock"):
                    int tempPid = input.nextInt();
                    pt.Unblock(tempPid);
                    break;
            }
        }

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
     * Contains the process index that is running
     */
    private Process runningProcess;

    /**
     * Keeps the number of processes running
     */
    private int pid;

    public ProcessTable(){
        this.table = new ArrayList<>();
        int[] tempReg = new int[6];
        System.arraycopy(this.getRandomArray(),0,tempReg,0,6);
        pid = 0;
        Process temp = new Process(pid,"init","root",0,tempReg);
        table.add(temp);
        System.arraycopy(temp.getRegisters(),0,this.CPU,0,6);
        this.runningProcess = temp;
    }

    /**
     * This method makes a copy of the currently running process with a new status 1 (ready) and a
     * new unique pid. The program, user, and register content s are the same as the running process
     */
    public void Fork(){
        Process temp  = new Process(this.runningProcess);
        temp.setStatus(1);
        this.pid++;
        temp.setPid(this.pid);
        this.table.add(temp);
    }

    public void Block(){
        this.runningProcess.setStatus(2);
        this.runningProcess.setRegisters(this.CPU);
        this.runningProcess = this.getRandom();
        System.arraycopy(this.runningProcess.getRegisters(),0,this.CPU,0,6);
        this.runningProcess.setStatus(0);


    }
    public void Yield(){
        this.runningProcess.setRegisters(this.CPU);
        Process temp  = this.getRandom();
        this.runningProcess.setStatus(1);
        this.runningProcess = temp;
        System.arraycopy(this.runningProcess.getRegisters(),0,this.CPU,0,6);
        this.runningProcess.setStatus(0);
    }
    public void Exit(){
        table.remove(this.runningProcess);
        this.runningProcess = this.getRandom();
        System.arraycopy(this.runningProcess.getRegisters(),0,this.CPU,0,6);
        this.runningProcess.setStatus(0);
    }
    public void Print(){
        System.out.println(this.toString());
    }
    public void Exec(String newUser,String newProgram){
        if(this.runningProcess.getUser().equals("root") || this.runningProcess.getUser().equals(newUser)){
            this.runningProcess.setUser(newUser);
            this.runningProcess.setRegisters(this.getRandomArray());
            this.runningProcess.setProgram(newProgram);
            System.arraycopy(this.runningProcess.getRegisters(),0,this.CPU,0,6);
        }

    }

    /**
     * Changes the PID to (1) - Ready - from (2) - Blocked
     * In order to change the PID, the PID has to be found and the process is Blocked
     * @param pidToUnblock - the PID to unblock
     */
    public void Unblock(int pidToUnblock){
        int index = find(pidToUnblock);
        boolean allow = index >=0 && this.table.get(index).getStatus() == 2 ;
        if(allow)
            this.table.get(index).setStatus(1);
    }

    public int find (int pid){
        int i =0;
        boolean found= false;
        while(!found && i < table.size()){
            if(pid== table.get(i).getPid()) {
                found = true;
                i--;
            }
            i++;
        }
        if(i == table.size())
            return -1;
        else
            return i;
    }

    /**
     * getRandom()
     * Gets a random process from the process table with the ready status
     * TODO: When there's no processes with a ready status, it will return the process at the 0th index.
     *
     * @return a random process with a ready status of 1
     */
    public Process getRandom(){
        ArrayList<Process> temp = new ArrayList<>();
        for(int index =0;index< this.table.size();index++){
            if(this.table.get(index).getStatus() == 1)
                temp.add(table.get(index));
        }
        Random r = new Random();
        int i = r.nextInt(temp.size());
        return temp.get(i);

    }
    public int[] getRandomArray(){
        Random ran = new Random();
        int maximum = 911111111;
        int min = 879854396;
        int range = maximum - min + 1;
        int[] ar = new int[6];
        ar[0] = ran.nextInt(range) + min;
        ar[1] = ran.nextInt(range) + min;
        ar[2] = ran.nextInt(range) + min;
        ar[3] = ran.nextInt(range) + min;
        ar[4] = ran.nextInt(range) + min;
        ar[5] = ran.nextInt(range) + min;
        return ar;
    }

    /**
     * This method kills the process with the specified process id only if the currently running process
     * - has user set as "root" or
     * - has the same user set as the process being killed.
     * @param pidToKill - PID to kill from the table
     */
    public void Kill(int pidToKill){
        boolean allow = false;
        if(this.runningProcess.getUser().equals("root")){
            allow = true;
        }
        int index = this.find(pidToKill);

        boolean found=  index >= 0;
        if(found && table.get(index).getUser().equals(this.runningProcess.getUser()))
            allow = true;
        if(allow && found)
            table.remove(index);
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

        String str = String.format("CPU:\n PC = 0x%8s SP = 0x%8s\n R0 = 0x%8s R1 = 0x%8s\n R2 = 0x%8s R3 = 0x%8s\n",
                Integer.toHexString(this.CPU[0]),Integer.toHexString(this.CPU[1]),Integer.toHexString(this.CPU[2]),
                Integer.toHexString(this.CPU[3]),Integer.toHexString(this.CPU[4]),Integer.toHexString(this.CPU[5])) ;
        str = str + "\nProcess Table:\n" + (new Process()).header;
        for(int i =0; i<this.table.size();i++){
            str = str + table.get(i).toString() + "\n";
        }
        return str;
    }

}

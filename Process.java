import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Erick on 4/8/2017.
 * Class handles a process
 * - PID
 * - Program
 * - User
 * - Status
 * -Registers
 */
public class Process {
    public Process(){
        this.pid = 0;
        this.program = "l";
        this.usr = "l";
        this.status = 1;
        registers = new int[6];
    }
    public Process(Process p){
        this.pid = p.pid;
        this.program = p.program;
        this.usr = p.usr;
        this.status = p.status;
        this.registers = new int[6];
        System.arraycopy(p.registers,0,this.registers,0,6);

    }
    public Process(int newPid, String program, String user, int status, int[] newRegisters){
        this.pid = newPid;
        this.program = program;
        this.usr = user;
        this.status = status;
        registers = newRegisters;

    }
    String header = String.format("%3s %11s %9s %7s %10s %10s %10s %10s %10s" +
            " %10s\n","PID","Program","User","Status","PC","SP","R0","R1","R2","R3" );
    //Refers to the process ID - "pid"
    private int pid;
    //The name of the program
    private String program;
    //The name of the user running this program
    private String usr;

    public int getPid() {
        return pid;
    }

    public String getProgram() {
        return program;
    }

    public String getUser() {
        return usr;
    }

    public int getStatus() {
        return status;
    }

    public int[] getRegisters() {
        return registers;
    }

    public void setPid(int newPid) {
        pid = newPid;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setUser(String user) {
        this.usr = user;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRegisters(int[] registers) {
        System.arraycopy(registers,0,this.registers,0,6);
    }

    /**
     * ***Status
     * Refers to the current status
     * 0-Program running
     * 1- Process is ready
     * 2- Process is blocked
     */
    private int status;
    /**
     * Array contains 6 varibles of type int referring to the
     * contents of 6 registers
     *0- pc (program counter)
     *1- sp (stack pointer)
     *2- r0 (register 0)
     *3- r1 (register 1)
     *4- r2 (register 2)
     *5- r3 (register 3)
     */
    private int[] registers;

    public String toString(){
        String str;
        str = String.format("%3d %11s %9s %7d 0x%8s 0x%8s 0x%8s 0x%8s 0x%8s 0x%8s",
                this.pid,this.program,this.usr,this.status,Integer.toHexString(registers[0]),
                Integer.toHexString(registers[1]),Integer.toHexString(registers[2]),Integer.toHexString(registers[3]),
                Integer.toHexString(registers[4]),Integer.toHexString(registers[5]));
        return str;
    }
    public boolean equals(Process p ){
        return (this.usr == p.usr) && (this.pid == p.pid) && (this.program == p.program)
                && (this.status == p.status) && (this.registers[0] == p.registers[0]) &&
                (this.registers[1] == p.registers[1]) && (this.registers[2] == p.registers[2]) &&
                (this.registers[3] == p.registers[3]) && (this.registers[4] == p.registers[4]) && (this.registers[5] == p.registers[5]);
    }


}

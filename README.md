# OS-ProcessTable
This project simulates a process table and some system calls. 
Each process control block has the following information:
<ol>
<li> An integer referring to the process ID (pid)</li>
<li> A String referring to the program being executed</li>
<li> A String referring to the user name running the program</li>
<li> An integer referring to the current status. A 0 indicates the program is running, a 1 indicates the process
is ready, and a 2 indicates the process is blocked</li>

<li>Six integers referring to register contents</li> 
 They will contain the following registers:</br>
(a) <b>pc</b> (program counter)</br>
(b) <b>sp</b> (stack pointer)</br>
(c) <b>r0</b> (register 0)</br>
(d) <b>r1</b> (register 1)</br>
(e) <b>r2</b> (register 2)</br>
(f) <b>r3</b> (register 3)
</ol>

# Commands to modify the process table 
<ul>
<li><b>fork:</b> makakes a copy of current runnig process </li>
<li><b>kill &#60pid></b>: kills the process with specified id </li>
<li><b>execve &#60;program&gt; &#60user>:</b> switches the program and username for the 
currentrly running program to the values specified </li>
<li><b>block:</b> blocks the current running process</li>
<li><b>yeild:</b> puts the current running process into ready state</li>
<li><b>exit:</b> causes the running process to exit</li>
<li><b>print:</b> prints the CPU table</li>
<li><b>unblock &#60pid>:</b> moves the blocked process with the specified pid to the ready 
state</li>

# Example 
<a>
<img src="https://user-images.githubusercontent.com/10952643/32428742-3364dbd2-c27c-11e7-843a-22b71e57b65d.png" alt="Example">
</a>

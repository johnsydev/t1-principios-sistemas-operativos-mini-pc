package minipcsimulator.model;

public class Dispatcher {
    public static void dispatch(Process process, CPU cpu) {
        PCB pcb = process.getPCB();
        pcb.setState(PCB.ProcessState.RUNNING);
        cpu.setPCB(pcb);
    }
}

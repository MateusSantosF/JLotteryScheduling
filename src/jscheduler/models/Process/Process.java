
package jscheduler.models.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jscheduler.models.Ticket;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public class Process {
    
    
    private UUID PID;
    private Priority priority;
    private ProcessType type;
    private List<Ticket> tickets;
    private int timesScheduled; // numero de vezes que foi escalonado
    private boolean hasRaffled;
    private int CPUTimeToFinish;
    private int CPUTimeConsumed;
    private int unusedCPUTime;
    private int schedulerCounter;

    
  
    public Process(ProcessType type, Priority priority, int timeToFinish){
        this.type = type;
        this.priority = priority;
        this.CPUTimeToFinish = timeToFinish;
        this.CPUTimeConsumed = 0;
        this.timesScheduled = 0;
        this.tickets = new ArrayList<>();
        this.hasRaffled = false;
        this.PID = UUID.randomUUID();
    }

    public int getCPUTimeConsumed() {
        return CPUTimeConsumed;
    }
    
    
    
    

    public boolean isHasRaffled() {
        return hasRaffled;
    }

    public int getSchedulerCounter() {
        return schedulerCounter;
    }

    public void setSchedulerCounter(int schedulerCounter) {
        this.schedulerCounter = schedulerCounter;
    }
    
    
    public void setHasRaffled() {
        this.hasRaffled = true;
    }
      
    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }
    
    public List<Ticket> getTickets(){
        return tickets;
    }

    public ProcessType getType() {
        return type;
    }
    
    public Priority getPriority(){
        return priority;
    }

    public void setType(ProcessType type) {
        this.type = type;
    }
    
    public int getCPUTimeToFinish(){
        return this.CPUTimeToFinish;
    }
    
    /**
     * Simula o processamento do Processo pelo tempo passado por parametro.
     * @param time 
     */
    public void giveCPUTime(int time){
        
        incrementTimesScheduled();
        int subtract = getCPUTimeToFinish() - time;
        
        if(subtract == 0){
            CPUTimeToFinish = 0;
            this.CPUTimeConsumed += time; //somando tmepo consumido
        }else if(subtract < 0){
            this.CPUTimeConsumed += getCPUTimeToFinish(); //somando o tempo consumido (se o tempo sorteado > tempo que resta, soma o tempo que resta como consumido)
            CPUTimeToFinish = 0;
            unusedCPUTime = -1*subtract;
            
        }else{
            this.CPUTimeToFinish = subtract;
            this.CPUTimeConsumed += time; //soma tempo consumido
        }
    }
    
    private void incrementTimesScheduled(){
        this.timesScheduled++;
    }
    
    public boolean hasUnusedTime(){
        return unusedCPUTime > 0;
    }
    
    
    public int getUnusedTime(){
        return unusedCPUTime;
    }
    
    public boolean hasFinish(){
        return CPUTimeToFinish == 0;
    }

    public UUID getPID() {
        return PID;
    }

    public void setPID(UUID PID) {
        this.PID = PID;
    }
    /**
     * Apresenta todas as informações referentes ao processo.
     */
    public void showProcessInfo(){
        
        System.out.printf("\n\nPID = %s\nTIME = %d\nPriority = %s \nTickets Amount = %d\nTicket Numbers = ",
                getPID().toString(),
                CPUTimeToFinish,
                priority,
                tickets.size()
        );
        showTicketsNumbers();
        System.out.println("");
    }
    
    /**
     * Apresenta o número dos tickets ganhos por este processo.
     */
    private void showTicketsNumbers(){
        
        tickets.forEach(t ->{
            System.out.print(t.getNumber() + " |");
        });
    }
    
    public void removeTicket(Ticket ticket){
        this.tickets.remove(ticket);
    }

    public int getTimesScheduled() {
        return timesScheduled;
    }
    
    
    /**
     * Monta as informações que irão para o CSV 
     */
    public String getInfo(){
        return  this.getPID() + ", " +
                this.getPriority() + 
                ", " + this.getTickets().size() +
                ", " + this.getCPUTimeToFinish() +
                ", " + this.getUnusedTime() +
                ", " + this.getSchedulerCounter() +
                ", " + this.getCPUTimeConsumed() +
                ", " + this.getTimesScheduled();
    }
    
   
}


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
    private List<Ticket> tickets;
    private int timesScheduled; // numero de vezes que foi escalonado
    private ProcessType type;
    private boolean hasRaffled;
    private Priority priority;

    
  
    public Process(ProcessType type, Priority priority){
        this.type = type;
        this.priority = priority;
        this.tickets = new ArrayList<>();
        this.hasRaffled = false;
        PID = UUID.randomUUID();
    }

    public boolean isHasRaffled() {
        return hasRaffled;
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

    public void setType(ProcessType type) {
        this.type = type;
    }

    public UUID getPID() {
        return PID;
    }

    public void setPID(UUID PID) {
        this.PID = PID;
    }

    
    
    /**
     * Transfere os Tickets do Processo chamador, para o processo passado
     * 
     * @param winner 
     */
    public void transferTickets(Process winner){
        
    }
    
    /**
     * Monta as informações que irão para o CSV 
     */
    public String getInfo(){
        return "";
    }
    
   
}

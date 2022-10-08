
package jscheduler.models;

import java.util.UUID;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public class Ticket {
    
      
    private int number;
    private UUID PID;
    
     public Ticket(int number, UUID PID){
        this.number = number;
        this.PID = PID;
    }

    public UUID getPID() {
        return PID;
    }

    public void setPID(UUID PID) {
        this.PID = PID;
    }

        
    public int getNumber() {
        return number;
    }

}

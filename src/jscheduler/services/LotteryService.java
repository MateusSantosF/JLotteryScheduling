
package jscheduler.services;


import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import jscheduler.models.Ticket;
import jscheduler.models.Process.Process;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public class LotteryService {
    
    /**
     * Retorna uma lista com todos os tickets sorteados
     * @param maxTicket
     */
    
    private final Random random = new Random();
    private HashMap<Integer, Boolean> sortedTickets = new HashMap<>();
    private final int maxTicketPerProcess;
    private final int maxTickets;
    private int maxSortedNumber;
       
    
    public LotteryService(int maxTicketPerProcess, int maxTickets){
        this.maxTicketPerProcess = maxTicketPerProcess;
        this.maxTickets = maxTickets;
    }
    
    /**
     * Sorteia a QUANTIDADE de tickets para cada processo
     * @param processes 
     */
    public void raffleTickets( List<Process> processes){
        
        int floor = 0;           
        for(int i = 0; i < maxTickets; i++){          
            int amount = random.nextInt(maxTickets) + 1;    
            
            insertTickets(processes.get(i), floor, amount);
            floor += amount;
        } 
        
        maxSortedNumber = floor;
    }
    
   
    /**
     * Sorteia o próximo ticket ganhador. Verificando se o numero sorteado já
     * foi previamente sorteado, em caso afirmativo, é sorteado um novo número.
     */
    public int raffleWinner(){
        
        int maxNumber = getMaxSortedNumber();
        if( sortedTickets.size() >= maxNumber ){
           throw new Error("Você não pode sortear mais ganhadores do que tickets.");
        }
        
        int sortedTicket = random.nextInt(maxNumber);
        
        while(sortedTickets.containsKey(sortedTicket)){
            sortedTicket = random.nextInt(maxNumber);
        }
        sortedTickets.put(sortedTicket, Boolean.TRUE);    

        return sortedTicket;
    }
    
    public int getMaxSortedNumber(){
        return maxSortedNumber;
    }
     
    private void insertTickets(Process process, int currentFloor, int amount){
        
        UUID PID = process.getPID();
        if(amount > maxTicketPerProcess){
            amount = maxTicketPerProcess;
        }
        
        for(int i = currentFloor; i < currentFloor + amount; i++){             
            Ticket ticket = new Ticket(i,PID);
            process.addTicket(ticket);
        }
    }
}

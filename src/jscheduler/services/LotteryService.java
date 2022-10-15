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
     *
     * @param maxTicket
     */
    private final Random random = new Random();
    private HashMap<Integer, Boolean> sortedTickets = new HashMap<>();
    private HashMap<Integer, Ticket> tickets = new HashMap<>();
    private final int maxTickets;
    private int maxSortedNumber;

    public LotteryService(int maxTickets) {
        this.maxTickets = maxTickets;
        this.maxSortedNumber = 0;
    }

    /**
     * Sorteia a QUANTIDADE de tickets para cada processo
     *
     * @param processes
     */
    public void raffleTickets(List<Process> processes) {

        int floor = 0;
        int priorityValue = 0;
        
        
        //reset variable for new raffle
        
        tickets = new HashMap<>();
        maxSortedNumber = 0;
        sortedTickets = new HashMap<>();
        
        for (int i = 0; i < maxTickets; i++) {

            Process process = processes.get(i);
            switch (process.getPriority()) {
                case HIGH:
                    priorityValue = 3;
                    break;
                case MEDIUM:
                    priorityValue = 2;
                    break;
                case LOW:
                    priorityValue = 1;
                    break;
            }

            int amount = random.nextInt(maxTickets) + 1;
            amount *= priorityValue;
            insertTickets(process, floor, amount);
            floor += amount;
        }

        maxSortedNumber = floor;
    }

    /**
     * Sorteia o próximo ticket ganhador. Verificando se o numero sorteado já
     * foi previamente sorteado, em caso afirmativo, é sorteado um novo número.
     */
    public int raffleWinner() {

        int maxNumber = getMaxSortedNumber();
        if (sortedTickets.size() >= maxNumber) {
            throw new Error("Você não pode sortear mais ganhadores do que tickets.");
        }

        int sortedTicket = random.nextInt(maxNumber);

        while (sortedTickets.containsKey(sortedTicket)) {
            sortedTicket = random.nextInt(maxNumber);
        }

        sortedTickets.put(sortedTicket, Boolean.TRUE);

        return sortedTicket;
    }

    public boolean hasRaffledAllTickets() {
        return tickets.isEmpty();
    }
    
    public void removeRaffledTicket(Ticket ticket){
        tickets.remove(ticket.getNumber());
    }

    public int getMaxSortedNumber() {
        return maxSortedNumber;
    }

    public boolean hasFinishedAllProcesses() {
        return sortedTickets.size() == maxSortedNumber;
    }

    public Process getProcessByTicked(Ticket ticket, HashMap<UUID, Process> processes) {
        return processes.get(tickets.get(ticket.getNumber()).getPID());
    }
    

    public Ticket getTicketByNumber(int number) {
        return tickets.get(number);
    }

    private void insertTickets(Process process, int currentFloor, int amount) {

        UUID PID = process.getPID();

        for (int i = currentFloor; i < (currentFloor + amount); i++) {
            Ticket ticket = new Ticket(i, PID);
            tickets.put(i, ticket);
            process.addTicket(ticket);
        }
    }
    
   
}

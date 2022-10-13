
package jscheduler;

import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;
import jscheduler.factory.ProcessFactory;
import jscheduler.models.Process.Process;
import jscheduler.models.Ticket;
import jscheduler.services.LotteryService;
import jscheduler.services.WriterService;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public class JScheduler {

    private static final int GLOBAL_MAX_TICKET = 2;
    private static final int AMOUNT_PROCESS = GLOBAL_MAX_TICKET;
    private static final int MAX_TIME_TO_FINISH_PROCESS = 100;
    private static final int CPU_TIME = 15;
    private static final double MULTIPLIER = 0.05;
    
    /**
     * A ideia, é utilizar o algoritmo de loteria para o sorteio de bilhetes entre
     * os processos, e ao mesmo tempo, beneficiar os bilhetes menos sorteados
     * para que, com o tempo, eles consigam ser sorteados e ganharem tempo de CPU
     *  
     * @param args
     */
    public static void main( String[] args ) {
       
        WriterService writer = WriterService.Configure()
                                            .Path("COLOCA UM DIRETÓRIO VÁLIDO AKI PRA RODAR")
                                            .Build();
        
        LotteryService loterry = new LotteryService(GLOBAL_MAX_TICKET);  
        HashMap<UUID,Process> processes = ProcessFactory.make(AMOUNT_PROCESS, MAX_TIME_TO_FINISH_PROCESS);
        
        // Sorteia tickets para o processo atual
        loterry.raffleTickets( processes.values().stream().collect(Collectors.toList()) );
        
        System.out.println("Nº TOTAL DE PROCESSOS: " + processes.size());
        
        processes.values().forEach(p ->{
            p.showProcessInfo();
        });
        
        System.out.println("\nINICIANDO ESCALONAMENTO\n");
        
        int SHARED_TIME = 0;
        
        while( !processes.isEmpty() ){
            
            if( loterry.hasRaffledAllTickets() ){
                System.out.println("Sorteando novos tickets para os processos restantes...");
                loterry.raffleTickets( processes.values().stream().collect(Collectors.toList()) );
            }
            
            int currentTicketWinnerNumber = loterry.raffleWinner();
            System.out.println("Nº Sorteado: " + currentTicketWinnerNumber);
            
            Ticket winnerTicket = loterry.getTicketByNumber(currentTicketWinnerNumber);
            System.out.println("\tPID Processo Sorteado: " + winnerTicket.getPID());
            
            Process winnerProcess = loterry.getProcessByTicked(winnerTicket, processes);
           
            loterry.removeRaffledTicket(winnerTicket); // remove o ticket da lista de tickets globais
            
            if(winnerProcess == null){ // se for nulo, o processo que ganhou o ticket ja terminou! 
                System.out.println("\tTEMPO RESTANTE: 0");
                return;
            }
            
            System.out.println("\tTEMPO RESTANTE: " + winnerProcess.getCPUTimeToFinish());
            
            int winnerTime = CPU_TIME + SHARED_TIME;
            winnerProcess.giveCPUTime(winnerTime);  
            System.out.println("\tTEMPO SORTEADO: " + winnerTime);
            
            SHARED_TIME = 0;
            
            if(winnerProcess.hasFinish()){               
                if(winnerProcess.hasUnusedTime()){
                    SHARED_TIME = winnerProcess.getUnusedTime(); 
                }
                //NESSE PONTO, PODERIAMOS DISTRIBUIR OS TICKETS RESTANTES DESTE PROCESSO Q TERMINOU, REALIMENTANDO A LISTA DE TICKETS
                writer.writeProcessInfo(winnerProcess);
                processes.remove(winnerProcess.getPID()); // remove processo da lista
                System.out.println("\nPROCESSOS RESTANTES: ");
                processes.values().forEach(p ->{
                   p.showProcessInfo();
                });
            }
 
        }
        
    }
    
}

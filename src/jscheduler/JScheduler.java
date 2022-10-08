
package jscheduler;

import java.util.List;
import jscheduler.factory.ProcessFactory;
import jscheduler.models.Process.Process;
import jscheduler.services.LotteryService;
import jscheduler.services.WriterService;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public class JScheduler {

    private static final int GLOBAL_MAX_TICKET = 3;
    private static final int AMOUNT_PROCESS = GLOBAL_MAX_TICKET;
    private static final int MAX_TICKET_PER_PROCESS = 10;
    private static final double MULTIPLIER = 0.05;
    
    /**
     * A ideia, é utilizar o algoritmo de loteria para o sorteio de bilhetes entre
     * os processos, e ao mesmo tempo, beneficiar os bilhetes menos sorteados
     * para que, com o tempo, eles consigam ser sorteados e ganharem tempo de CPU
     *  
     */
    public static void main(String[] args) {
       
        WriterService writer = WriterService.Configure()
                                            .Path("CAMINHO DO ARQUIVO.TXT")
                                            .Build();
        
        List<Process> processes = ProcessFactory.make(AMOUNT_PROCESS);
        LotteryService loterry = new LotteryService(MAX_TICKET_PER_PROCESS, GLOBAL_MAX_TICKET);
        
        loterry.raffleTickets(processes);
        
        
        
        
        System.out.println("Testando Processos Gerados");
        processes.forEach(p ->{
            System.out.println("\nProcess PID = " + p.getPID());
            System.out.printf("\tTickets Amount(%d) ->  ", p.getTickets().size());
            p.getTickets().forEach(t ->{
                System.out.print(t.getNumber() + " | ");
            });
        });
        
        System.out.println("\n");
        
        System.out.println("Testando sorteio de tickets");
        for (int i = 0; i < loterry.getMaxSortedNumber(); i++) {
            System.out.println(loterry.raffleWinner());
        }
       
    }
    
}

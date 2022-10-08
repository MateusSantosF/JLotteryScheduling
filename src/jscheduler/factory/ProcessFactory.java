
package jscheduler.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jscheduler.models.Process.Priority;
import jscheduler.models.Process.ProcessType;
import jscheduler.models.Process.Process;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public class ProcessFactory {
    
    
    /**
     * Cria todos os processos que rodarão na CPU, e define seus tipos.
     * @param processesAmount
     */
    public static List<Process> make(int processesAmount){
        
        List<Process> processes = new ArrayList<>();
      
        for(int i = 0; i < processesAmount; i++){  
            processes.add(generateProcess());
        }
        
        return processes;
    }
    
    private static Process generateProcess(){
        
        Random random = new Random();
        ProcessType processType = null;
        Priority priority = null;
        
        int randNumber = random.nextInt(3) + 1;
       
        switch (randNumber) {
            case 1:
                processType = ProcessType.OPEN_BROWSER;
                priority = Priority.LOW;
                break;
            case 2:
                processType = ProcessType.CLOSE_TAB;
                priority = Priority.MEDIUM;
                break;
            case 3:
                processType = ProcessType.SEND_MAIL;
                priority = Priority.HIGH;
                break;
        }      
        
        return new Process(processType, priority);
    }
}

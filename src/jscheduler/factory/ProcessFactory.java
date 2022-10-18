
package jscheduler.factory;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
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
    public static HashMap<UUID,Process> make(int processesAmount, int maxTimeToFinishProcess){
        
        HashMap<UUID, Process> processes = new HashMap<>();
      
        for(int i = 0; i < processesAmount; i++){           
            Process p = generateProcess(maxTimeToFinishProcess);
            processes.put(p.getPID(), p);
        }
        
        return processes;
    }
    
     public static HashMap<UUID,Process> make(int processesAmount, int maxTimeToFinishProcess, int lowAmount, int mediumAmount, int highAmount){
        
        HashMap<UUID, Process> processes = new HashMap<>();
      
        for(int i = 0; i < lowAmount; i++){         
            Process p = generateProcess(maxTimeToFinishProcess, Priority.LOW);
            processes.put(p.getPID(), p);
        }
        
        for(int i = 0; i < mediumAmount; i++){         
            Process p = generateProcess(maxTimeToFinishProcess, Priority.MEDIUM);
            processes.put(p.getPID(), p);
        }
        
        for(int i = 0; i < highAmount; i++){         
            Process p = generateProcess(maxTimeToFinishProcess, Priority.HIGH);
            processes.put(p.getPID(), p);
        }
        
        return processes;
    }
    
    private static Process generateProcess(int maxTimeToFinishProcess){
        
        Random random = new Random();
        ProcessType processType = null;
        Priority priority = null;
        
        int timeToFinish = random.nextInt(maxTimeToFinishProcess) + 1;
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
        
        return new Process(processType, priority, timeToFinish);
    }
    
    private static Process generateProcess(int maxTimeToFinishProcess, Priority priority){
        
        Random random = new Random();
        ProcessType processType = null;
        
        int timeToFinish = random.nextInt(maxTimeToFinishProcess) + 1;
        int randNumber = random.nextInt(3) + 1;
       
        switch (randNumber) {
            case 1:
                processType = ProcessType.OPEN_BROWSER;
                break;
            case 2:
                processType = ProcessType.CLOSE_TAB;
                break;
            case 3:
                processType = ProcessType.SEND_MAIL;
                break;
        }      
        
        return new Process(processType, priority, timeToFinish);
    }
}

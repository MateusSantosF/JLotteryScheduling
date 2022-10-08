package jscheduler.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import jscheduler.models.interfaces.IPath;
import jscheduler.models.Process.Process;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public class WriterService implements IPath{
    
    
    private String filePath;
    
    /**
     * Construtor privado, para evitar instâncias do objeto, sem as configurações
     * Necessárias.
     */
    private WriterService(){     
    }
    
    /**
     * Retorna uma instância de WriteService de forma estática
     * @return IPath 
     */
    public static IPath Configure(){
        return new WriterService();
    }
    
    /**
     * Configura o caminho do arquivo, que será alvo da escrita.
     * @param filePath
     * @return 
     */
    @Override
    public IPath Path(String filePath) {
        this.filePath = filePath;
        return this;
    }
    
    /**
     * Retorna a instância configurada do WriterService.
     * @return 
     */
    @Override
    public WriterService Build() {
        return this;
    }
    
    /**
     * Escreve as informações do processo no CSV
     * @param process 
     */
    public void writeProcessInfo(Process process){
        
        File file = new File(filePath);
        
        if(!file.exists()) throw new Error("Arquivo informado não existe.");
        
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            bufferedWriter.append(process.getInfo());
            bufferedWriter.newLine();
        } catch (IOException e) {
        }
        
    }
}

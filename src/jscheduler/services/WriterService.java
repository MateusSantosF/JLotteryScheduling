package jscheduler.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jscheduler.models.interfaces.IPath;
import jscheduler.models.Process.Process;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public class WriterService implements IPath{
    
    
    private String filePath;
    private File file;
    private FileWriter writer;
    private BufferedWriter bufferedWriter;
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
        file = new File(filePath);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException exc){
            
        }
        return this;
    }
    
    /**
     * Retorna a instância configurada do WriterService.
     * @return 
     */
    @Override
    public WriterService Build() {
        try {
            writer = new FileWriter(file);
        } catch (IOException ex) {
            Logger.getLogger(WriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        bufferedWriter = new BufferedWriter(writer);
        return this;
    }
    
    /**
     * Escreve as informações do processo no CSV
     * @param process 
     */
    public void writeProcessInfo(String info){
       Date time = new Date();
        
        if(!file.exists()) throw new Error("Arquivo informado não existe.");
        try {
            
            bufferedWriter.newLine();
            bufferedWriter.append(info);
            bufferedWriter.append(", " + time.getTime());
            
            bufferedWriter.flush();
            writer.flush();
            
            
            
            
        } catch (IOException e) {
        }
        
    }
    
    public void closeBuffer(){
        try {
            bufferedWriter.close();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(WriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

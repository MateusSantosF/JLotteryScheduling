package jscheduler.models.interfaces;

import jscheduler.services.WriterService;

/**
 *
 * @author Mateus Santos | Gabriela Lima | João Pedro
 */
public interface IPath {
    
    public IPath Path(String path);
    
    public WriterService Build();
}

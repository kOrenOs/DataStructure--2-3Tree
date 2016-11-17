/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T2t3Package;

/**
 *
 * @author korenciak.marek
 */
public class ParentReferenceWrongException extends RuntimeException{
    public ParentReferenceWrongException(){
        super();
    }
    public ParentReferenceWrongException(String msg){
        super(msg);
    }
    
}

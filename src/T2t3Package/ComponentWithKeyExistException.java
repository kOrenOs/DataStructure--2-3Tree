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
public class ComponentWithKeyExistException extends RuntimeException{
    public ComponentWithKeyExistException(){
        super();
    }
    public ComponentWithKeyExistException(String msg){
        super(msg);
    }    
}

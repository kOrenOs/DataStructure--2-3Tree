/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoreClasses;

/**
 * Vynimka vyuzivajuca sa na upozornenie strukturalnych hranic systemu
 *
 * @author korenciak.marek
 */
public class DataStructureException extends RuntimeException {

    public DataStructureException() {
        super();
    }

    public DataStructureException(String msg) {
        super(msg);
    }
}

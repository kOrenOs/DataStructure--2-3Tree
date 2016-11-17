/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.LinkedList;

/**
 *
 * @author korenciak.marek
 */
public class Person {
    
    private String name;
    private String surName;
    private String personID;
    private Property permAddress;
    private LinkedList<PropertyList> wealthList;

    public Person(String initName, String initSurName, String initPersonID, Property initPermAddress) {
        name = initName;
        surName = initSurName;
        personID = initPersonID;
        permAddress = initPermAddress;
        wealthList = new LinkedList<>();
    }  
    
    public String getName(){
        return name;
    }
    
    public String getSurName(){
        return surName;
    }
    
    public String getPersonID(){
        return personID;
    }
    
    public Property getPermAddress(){
        return permAddress;
    }
    
    public void setPermAddress(Property setPermAddress){
        permAddress = setPermAddress;
    }
    
    public void addToWealthList(LinkedList<PropertyList> initWlethList){
        wealthList = initWlethList;
    }
    
    public void addToWealthList(PropertyList initPropertyList){
        wealthList.add(initPropertyList);
    }
    
    public boolean deleteFromWealthList(PropertyList deleteWealthList){
        return wealthList.remove(deleteWealthList);
    }
    
    public LinkedList<PropertyList> getPropertyLists(){
        return wealthList;
    }
}

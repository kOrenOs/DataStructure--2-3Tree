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
public class Property {
    
    private int propertyIDnum;
    private String address;
    private PropertyList relatedPropertyList;
    private LinkedList<Person> occupierList;

    public Property(int initPropertyIDnum, String initAddress, PropertyList initRelatedPropertyList) {
        propertyIDnum = initPropertyIDnum;
        address = initAddress;
        relatedPropertyList = initRelatedPropertyList;
        occupierList = new LinkedList<>();
    }

    public Property(int initPropertyIDnum, String initAddress) {
        propertyIDnum = initPropertyIDnum;
        address = initAddress;
    }
    
    public int getPropertIDnum(){
        return propertyIDnum;
    } 
    
    public String getPropertyAddress(){
        return address;
    }
    
    public PropertyList getRelatedPropertyList(){
        return relatedPropertyList;
    }
    
    public void setPropID(int propID){
        propertyIDnum = propID;
    }
    
    public void setRelatedPropertyList(PropertyList setRelatedPropertyList){
        relatedPropertyList = setRelatedPropertyList;
    }   
    
    public void addOccupier(Person occupier){
        occupierList.add(occupier);
    }
    
    public boolean removeOccupier(Person occupier){
        return occupierList.remove(occupier);
    }
    
    public LinkedList<Person> getOccupiers(){
        return occupierList;
    }
}

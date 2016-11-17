/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import CoreClasses.DataStructureException;
import java.util.LinkedList;

/**
 *
 * @author korenciak.marek
 */
public class PropertyList {

    private Cadaster cadasterLocation;
    private int listIDnum;
    private LinkedList<Property> listedProperties;
    private LinkedList<Person> ownerList;
    private LinkedList<Double> ownerRate;

    public PropertyList(Cadaster initCadasterLocation, int initListIDnum, LinkedList<Property> initListedProperties, LinkedList<Person> initOwnerList, LinkedList<Double> initOwnerRate) {
        cadasterLocation = initCadasterLocation;
        listIDnum = initListIDnum;
        if (initListedProperties == null) {
            listedProperties = new LinkedList<>();
        } else {
            listedProperties = initListedProperties;
        }
        if (initOwnerList == null) {
            ownerList = new LinkedList<>();
        } else {
            ownerList = initOwnerList;
        }
        if (initOwnerRate == null) {
            ownerRate = new LinkedList<>();
        } else {
            ownerRate = initOwnerRate;
        }
        if (initListedProperties != null) {
            for (Property prop : initListedProperties) {
                prop.setRelatedPropertyList(this);
            }
        }
        if (initOwnerList != null) {
            for (Person prop : initOwnerList) {
                prop.addToWealthList(this);
            }
        }
    }

    public PropertyList(Cadaster initCadasterLocation, int initListIDnum, Property initListedProperties, Person initOwnerList) {
        cadasterLocation = initCadasterLocation;
        listIDnum = initListIDnum;
        listedProperties = new LinkedList<>();
        listedProperties.add(initListedProperties);
        ownerList = new LinkedList<>();
        ownerList.add(initOwnerList);
        ownerRate = new LinkedList<>();
        ownerRate.add(1.0);
    }

    public Cadaster getCadasterLocation() {
        return cadasterLocation;
    }

    public int getListIDnum() {
        return listIDnum;
    }

    public LinkedList<Property> getListedProperties() {
        return listedProperties;
    }

    public LinkedList<Person> getOwnerList() {
        return ownerList;
    }

    public LinkedList<Double> getOwnerRate() {
        return ownerRate;
    }

    public void setCadaster(Cadaster cad) {
        cadasterLocation = cad;
    }

    public void setListIDnum(int listID) {
        listIDnum = listID;
    }

    public void changeOwner(Person actualOwnerPersonID, Person newOwnerPersonID) {
        int index = ownerList.indexOf(actualOwnerPersonID);
        if (index == -1) {
            throw new DataStructureException("This person is not owner of property list.");
        }
        ownerList.set(ownerList.indexOf(actualOwnerPersonID), newOwnerPersonID);
        actualOwnerPersonID.deleteFromWealthList(this);
        newOwnerPersonID.addToWealthList(this);
    }

    public boolean addProperty(Property addedProp) {
        if (!listedProperties.contains(addedProp)) {
            listedProperties.add(addedProp);
            addedProp.setRelatedPropertyList(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteProperty(Property addedProp) {
        if (!listedProperties.remove(addedProp)) {
            return true;
        } else {
            return false;
        }
    }

    public Person addOwner(Person addedPerson, double ownersPart) {
        if (!ownerList.contains(addedPerson)) {
            ownerList.add(addedPerson);
            ownerRate.add(ownersPart);
            addedPerson.addToWealthList(this);
            double temp = 1.0 / ownerList.size();
            for (int i = 0; i < ownerRate.size(); i++) {
                ownerRate.set(i, temp);
            }
            return addedPerson;
        } else {
            return null;
        }
    }

    public Boolean deleteOwner(Person deletePerson) {
        if (ownerList.contains(deletePerson)) {
            if (ownerList.size() == 1) {
                return null;
            }
            if (ownerList.remove(deletePerson)) {
                deletePerson.deleteFromWealthList(this);
                ownerRate.remove(0);
                for (int i = 0; i < ownerList.size(); i++) {
                    ownerRate.set(i, 1.0 / ownerList.size());
                }
                return true;
            }
        }
        return false;
    }
}

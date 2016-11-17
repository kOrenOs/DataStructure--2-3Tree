/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import CoreClasses.DataStructureException;
import T2t3Package.T2t3;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Predstavuje kataster s atrubutmi
 *
 * @author korenciak.marek
 */
public class Cadaster {

    private int cadasterIDnumber;                                   //Cislo katastra
    private String cadasterIDname;                                  //Meno katastra
    private T2t3<Integer, Property> includedProperties;            //Stromova struktura obsahujuca nehnutelnosti katastra
    private T2t3<Integer, PropertyList> includedPropertyLists;     //Stromova struktura obsahujuca listy vlastnictva katastra
    private int maxPropertyIndex = 0;                               //maximalny momentalne pouzity index nehnutelnosti, pri vytvarani novej nehnutelnosti
    private int maxPropertyListIndex = 0;                           //maximalny momentalne pouzity index listu vlastnictva, pri vytvarani noveho listu vlastnictva

    /**
     * Vytvorenie noveho listu vlastnictva definovaneho jeho menom a cislom
     */
    public Cadaster(int initIDnumber, String initIDname) {
        cadasterIDnumber = initIDnumber;
        cadasterIDname = initIDname;
        includedProperties = new T2t3<>();
        includedPropertyLists = new T2t3<>();
    }

    /**
     * vratenie cisla katastra
     */
    public int getCadIDnumber() {
        return cadasterIDnumber;
    }

    /**
     * vratenie mena katastra
     */
    public String getCadIDname() {
        return cadasterIDname;
    }

    /**
     * Pridanie nehnutelnosti do katastra, definovanej listom vlastnictva a
     * adresou
     */
    public Property addProperty(String initAddress, PropertyList initPropertyList) {
        Property temp = new Property(maxPropertyIndex, initAddress, initPropertyList);
        if (includedProperties.add(maxPropertyIndex, temp)) {
            maxPropertyIndex++;
            return temp;
        }
        return null;
    }

    /**
     * vymazanie nehnutelnosti z katastra
     */
    public boolean deleteProperty(Integer deletePropertyIDnum) {
        return includedProperties.delete(deletePropertyIDnum);            //pridat odobranie z PL ked odoberieme nehnutelnost
    }

    /**
     * pridanie noveho listu vlastnictva do katastra definovaneho zoznamom
     * nehnutelnosti a zoznamom osob
     */
    public PropertyList addPropertyList(LinkedList<Property> initProperties, LinkedList<Person> initPersons) {
        LinkedList<Double> ownerRate = null;
        if (initPersons != null) {
            if (!initPersons.isEmpty()) {
                int size = initPersons.size();
                ownerRate = new LinkedList<>();
                for (int i = 0; i < size; i++) {
                    ownerRate.add(1.0 / size);
                }
            }
        }
        PropertyList temp = new PropertyList(this, maxPropertyListIndex, initProperties, initPersons, ownerRate);
        if (includedPropertyLists.add(maxPropertyListIndex, temp)) {
            maxPropertyListIndex++;
            return temp;
        }
        return null;
    }

    /**
     * pridanie listu vlastnictva s uz existujucou nehutelnosotu (doteraz
     * zapisanou v inom liste vlastnictva)
     */
    public PropertyList addExistingPropertyList(PropertyList propList) {
        Cadaster oldCadaster = propList.getCadasterLocation();
        oldCadaster.migratePropertyList(this, propList.getListIDnum());
        propList.setListIDnum(maxPropertyListIndex);
        includedPropertyLists.add(maxPropertyListIndex, propList);
        maxPropertyListIndex++;
        LinkedList<Property> listOfProp = propList.getListedProperties();

        for (Property prop : listOfProp) {
            addExistingProperty(oldCadaster, prop);
        }
        return propList;
    }

    /**
     * pridanie uz existujucej nehnutelnosti (doteraz v inom katastri)
     */
    private void addExistingProperty(Cadaster cad, Property prop) {
        cad.includedProperties.delete(prop.getPropertIDnum());
        prop.setPropID(maxPropertyIndex);
        includedProperties.add(maxPropertyIndex, prop);
        maxPropertyIndex++;
    }

    /**
     * vyamzanie listu vlastnictva
     */
    public boolean deletePropertyList(Integer deletePropertyList) {
        PropertyList propList = findPropertyList(deletePropertyList);
        LinkedList<Person> temp = propList.getOwnerList();
        for (Person person : temp) {
            person.deleteFromWealthList(propList);
        }
        return includedPropertyLists.delete(deletePropertyList);
    }

    /**
     * presunutie listov vlastnictva do ineho katastralnehio uzemia
     */
    private boolean migratePropertyList(Cadaster cad, Integer movedPropertyList) {
        PropertyList propList = findPropertyList(movedPropertyList);
        propList.setCadaster(cad);
        return includedPropertyLists.delete(movedPropertyList);
    }

    /**
     * vyhladanie nehnutelnosti v danom katastri pomocou cisla nehnutelnosti
     */
    public Property findProperty(int propIDnum) {
        Property temp = includedProperties.searchData(propIDnum).getValue();
        if (temp == null) {
            throw new DataStructureException("Property with this ID is not exist.");
        }
        return temp;
    }

    /**
     * vyhladanie listu vlastnictva pomocou cisla listu vlastnictva v danej
     * nehnutelnosti
     */
    public PropertyList findPropertyList(int listIDnum) {
        PropertyList temp = includedPropertyLists.searchData(listIDnum).getValue();
        if (temp == null) {
            throw new DataStructureException("Property list with this ID is not exist.");
        }
        return temp;
    }

    /**
     * vrati vsetky obsiahnute nehnutelnosti zoradene podla cisla nehnutelnosti
     */
    public ArrayList<Property> getIncludedProperties() {
        return includedProperties.inOrder();
    }

    /**
     * vrati vsetky obsiahnute listy vlastnictva zoradene podla cisla listu
     * vlastnictva
     */
    public ArrayList<PropertyList> getIncludedPropertyLists() {
        return includedPropertyLists.inOrder();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoreClasses;

import Entities.Cadaster;
import Entities.Person;
import Entities.Property;
import Entities.PropertyList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Trieda, ktora predstavuje rozhranie medzi GUI aplikaciuo a samotnou
 * strukturou systemu. Zabezpecuje preklad vystupnych udajov do formy, ktora je
 * zrozumitelna pre aplikaciu mimo stukturu (remote GUI).
 *
 * @author korenciak.marek
 */
public class ApplicationController {

    private MainStructure structure = null;

    public static void main(String[] args) {
        ApplicationController instance = new ApplicationController();
    }

    public ApplicationController() {
        structure = new MainStructure();
    }

    /**
     * Generator dat. Naplni strukturu random datami na testovanie.
     */
    public boolean dataGenerate(int cadasterNumber, int minPropertyListPerCadaster,
            int maxPropertyListPerCadaster, int minPropertiesPerPL, int maxPropertiesPerPL, int minPersonsPerPL, int maxPersonsPerPL) {

        DataGenerator generator = new DataGenerator(structure, cadasterNumber, minPropertyListPerCadaster,
                maxPropertyListPerCadaster, minPropertiesPerPL, maxPropertiesPerPL, minPersonsPerPL, maxPersonsPerPL);
        return true;
    }

    public boolean saveStructure(String filePath) {
        return structure.saveToFile(filePath);
    }

    public boolean loadStructure(String filePath) {
        return structure.loadFromFile(filePath);
    }

    public ArrayList<Object> addCadaster(String cadasterName) {
        return translateCadaster(structure.addCadasterInternal(cadasterName));
    }

    public ArrayList<ArrayList> addProperty(String initCadName, String initAddress, int initPropListID) {
        return translateProperty(structure.addPropertyInternal(initCadName, initAddress, initPropListID));
    }

    public ArrayList<ArrayList> addProperty(int initCadID, String initAddress, int initPropListID) {
        return translateProperty(structure.addPropertyInternal(initCadID, initAddress, initPropListID));
    }

    public ArrayList<ArrayList> addPropertyList(String initCadName, LinkedList<Integer> propertyIDList, LinkedList<String> personIDList) {
        return translatePropertyList(structure.addPropertyListInternal(initCadName, propertyIDList, personIDList));
    }

    public ArrayList<ArrayList> addPropertyList(int initCadID, LinkedList<Integer> propertyIDList, LinkedList<String> personIDList) {
        return translatePropertyList(structure.addPropertyListInternal(initCadID, propertyIDList, personIDList));
    }

    public ArrayList<Object> addPerson(String initFirstName, String initLastName) {
        return translatePerson(structure.addPersonInternal(initFirstName, initLastName));
    }

    public ArrayList<Object> addPerson(String initFirstName, String initLastName, String cadNameOfPermAdd, int IDPropOfPermAdd) {
        return translatePerson(structure.addPersonInternal(initFirstName, initLastName, cadNameOfPermAdd, IDPropOfPermAdd));
    }

    public ArrayList<ArrayList> addOwnerToPropertyList(String cadasterName, int propListID, String personID, double ownersPart) {
        return translatePropertyList(structure.addOwnerToPropertyListInternal(cadasterName, propListID, personID, ownersPart));
    }

    public ArrayList<ArrayList> addOwnerToPropertyList(int cadasterName, int propListID, String personID, double ownersPart) {
        return translatePropertyList(structure.addOwnerToPropertyListInternal(cadasterName, propListID, personID, ownersPart));
    }

    public ArrayList<ArrayList> addPropertyListExistProperty(int cadID, String personID, int propertyID) {
        return translatePropertyList(structure.addPropertyListExistPropertyInternal(cadID, personID, propertyID));
    }

    public ArrayList<ArrayList> addPropertyListExistProperty(String cadID, String personID, int propertyID) {
        return translatePropertyList(structure.addPropertyListExistPropertyInternal(cadID, personID, propertyID));
    }

    public ArrayList<ArrayList> addPropertyListNewProperty(int cadID, String personID, String address) {
        return translatePropertyList(structure.addPropertyListNewPropertyInternal(cadID, personID, address));
    }

    public ArrayList<ArrayList> addPropertyListNewProperty(String cadID, String personID, String address) {
        return translatePropertyList(structure.addPropertyListNewPropertyInternal(cadID, personID, address));
    }

    public ArrayList<Object> findCadaster(int cadsterIDnum) {
        return translateCadaster(structure.findCadasterInternal(cadsterIDnum));
    }

    public ArrayList<Object> findCadaster(String cadsterIDName) {
        return translateCadaster(structure.findCadasterInternal(cadsterIDName));
    }

    public ArrayList<ArrayList> findProperty(String cadsterIDName, int propIDnum) {
        return translateProperty(structure.findPropertyInternal(cadsterIDName, propIDnum));
    }

    public ArrayList<ArrayList> findProperty(int cadsterIDName, int propIDnum) {
        return translateProperty(structure.findPropertyInternal(cadsterIDName, propIDnum));
    }

    public ArrayList<ArrayList> findPropertyList(String cadsterIDName, int propListIDnum) {
        return translatePropertyList(structure.findPropertyListInternal(cadsterIDName, propListIDnum));
    }

    public ArrayList<ArrayList> findPropertyList(int cadsterIDName, int propListIDnum) {
        return translatePropertyList(structure.findPropertyListInternal(cadsterIDName, propListIDnum));
    }

    public ArrayList<ArrayList> findPropertyOfOwner(String ownerPersonalID, int cadasterID) {
        return translateProperty(structure.findPropertyOfOwnerInternal(ownerPersonalID, cadasterID));
    }

    public ArrayList<ArrayList> findPropertyOfOwner(String ownerPersonalID, String cadasterID) {
        return translateProperty(structure.findPropertyOfOwnerInternal(ownerPersonalID, cadasterID));
    }

    public ArrayList<ArrayList> findPropertyOfOwner(String ownerPersonalID) {
        return translateProperty(structure.findPropertyOfOwnerInternal(ownerPersonalID));
    }

    public ArrayList<ArrayList> findPermPAddress(String personID) {
        return translateProperty(structure.findPermPAddressInternal(personID));
    }

    public ArrayList<ArrayList> findOccupiersOfProperty(int cadasterID, int propertyID) {
        return translatePerson(structure.findOccupiersOfPropertyInternal(cadasterID, propertyID));
    }

    public ArrayList<ArrayList> findOccupiersOfProperty(String cadasterID, int propertyID) {
        return translatePerson(structure.findOccupiersOfPropertyInternal(cadasterID, propertyID));
    }

    public ArrayList<ArrayList> findAllPeople() {
        return translatePerson(structure.findAllPeopleInternal());
    }

    public boolean changePermAddress(String personID, String cadasterName, int propID) {
        return structure.changePermAddressInternal(personID, cadasterName, propID);
    }

    public boolean changePermAddress(String personID, int cadasterName, int propID) {
        return structure.changePermAddressInternal(personID, cadasterName, propID);
    }

    public ArrayList<ArrayList> changeOwnerOfPropertyList(int cadasterID, int propertyID, String actualOwnerPersonID, String newOwnerPersonID) {
        return translatePropertyList(structure.changeOwnerOfPropertyListInternal(cadasterID, propertyID, actualOwnerPersonID, newOwnerPersonID));
    }

    public ArrayList<ArrayList> changeOwnerOfPropertyList(String cadasterName, int propertyID, String actualOwnerPersonID, String newOwnerPersonID) {
        return translatePropertyList(structure.changeOwnerOfPropertyListInternal(cadasterName, propertyID, actualOwnerPersonID, newOwnerPersonID));
    }

    public ArrayList<ArrayList> migrateAllPropertyToAnotherPropertyList(String cadasterName, int homePropListID, int destinationPropListID) {
        return translatePropertyList(structure.migrateAllPropertyToAnotherPropertyListInternal(cadasterName, homePropListID, destinationPropListID));
    }

    public ArrayList<ArrayList> migrateAllPropertyToAnotherPropertyList(int cadasterName, int homePropListID, int destinationPropListID) {
        return translatePropertyList(structure.migrateAllPropertyToAnotherPropertyListInternal(cadasterName, homePropListID, destinationPropListID));
    }

    public ArrayList<ArrayList> deleteOwnerOfPropertyList(String cadasterName, int propertyID, String ownerPersonID, Integer backUpPropListID) {
        return translatePropertyList(structure.deleteOwnerOfPropertyListInternal(cadasterName, propertyID, ownerPersonID, backUpPropListID));
    }

    public ArrayList<ArrayList> deleteOwnerOfPropertyList(int cadasterName, int propertyID, String ownerPersonID, Integer backUpPropListID) {
        return translatePropertyList(structure.deleteOwnerOfPropertyListInternal(cadasterName, propertyID, ownerPersonID, backUpPropListID));
    }

    public boolean deleteProperty(String cadasterName, int propertyID, int propertyListID) {
        return structure.deletePropertyInternal(cadasterName, propertyID, propertyListID);
    }

    public boolean deleteProperty(int cadasterID, int propertyID, int propertyListID) {
        return structure.deletePropertyInternal(cadasterID, propertyID, propertyListID);
    }

    public ArrayList<Object> deleteCadaster(String homeNameID, String destinationNameID) {
        return translateCadaster(structure.deleteCadasterInternal(homeNameID, destinationNameID));
    }

    public ArrayList<Object> deleteCadaster(int homeNameID, int destinationNameID) {
        return translateCadaster(structure.deleteCadasterInternal(homeNameID, destinationNameID));
    }

    public ArrayList<Object> deleteCadaster(String homeNameID, int destinationNameID) {
        return translateCadaster(structure.deleteCadasterInternal(homeNameID, destinationNameID));
    }

    public ArrayList<Object> deleteCadaster(int homeNameID, String destinationNameID) {
        return translateCadaster(structure.deleteCadasterInternal(homeNameID, destinationNameID));
    }

    public boolean deletePersonInternal(String deletedPersonID, String backUpPersonID) {
        return structure.deletePersonInternal(deletedPersonID, backUpPersonID);
    }

    public ArrayList<ArrayList> getCadasterArrayByID() {
        return translateCadaster(structure.getCadastersByIDInternal());
    }

    public ArrayList<ArrayList> getCadasterArrayByName() {
        return translateCadaster(structure.getCadastersByNameInternal());
    }

    public ArrayList<ArrayList> getPropertyListsOfCadaster(int cadasterID) {
        return translatePropertyList(structure.getPropertyListsOfCadasterInternal(cadasterID));
    }

    public ArrayList<ArrayList> getPropertyListsOfCadaster(String cadasterID) {
        return translatePropertyList(structure.getPropertyListsOfCadasterInternal(cadasterID));
    }

    public ArrayList<ArrayList> getPropertyOfCadaster(String cadasterID) {
        return translateProperty(structure.getPropertyOfCadasterInternal(cadasterID));
    }

    public ArrayList<ArrayList> getPropertyOfCadaster(int cadasterID) {
        return translateProperty(structure.getPropertyOfCadasterInternal(cadasterID));
    }

    /**
     * Rozsirene informacie na vypis property listu, vyuzivaju sa na naplnenie
     * externeho okna popisujuceho samotny property list
     */
    private ArrayList<ArrayList> getPropertyListInfo(PropertyList propList) {

        List<Property> ownersProp = propList.getListedProperties();
        List<Person> owners = propList.getOwnerList();

        ArrayList<Object> propInfo = new ArrayList<>();
        propInfo.add(propList.getCadasterLocation().getCadIDname());
        propInfo.add(propList.getCadasterLocation().getCadIDnumber());
        propInfo.add(propList.getListIDnum());

        ArrayList<Integer> propID = new ArrayList<>();
        ArrayList<String> propAdd = new ArrayList<>();

        ArrayList<String> personID = new ArrayList<>();
        ArrayList<String> personName = new ArrayList<>();
        ArrayList<String> personSurname = new ArrayList<>();
        ArrayList<Integer> personCadasterIdPermAdd = new ArrayList<>();
        ArrayList<Integer> personPropPermAddID = new ArrayList<>();
        ArrayList<String> personPropPermAdd = new ArrayList<>();

        Property temp;

        for (Property prop : ownersProp) {
            propID.add(prop.getPropertIDnum());
            propAdd.add(prop.getPropertyAddress());
        }

        for (Person owner : owners) {
            temp = null;
            personID.add(owner.getPersonID());
            personName.add(owner.getName());
            personSurname.add(owner.getSurName());
            temp = owner.getPermAddress();
            if (temp != null) {
                personCadasterIdPermAdd.add(temp.getRelatedPropertyList().getCadasterLocation().getCadIDnumber());
                personPropPermAddID.add(temp.getPropertIDnum());
                personPropPermAdd.add(temp.getPropertyAddress());
            } else {
                personCadasterIdPermAdd.add(null);
                personPropPermAddID.add(null);
                personPropPermAdd.add(null);
            }
        }

        ArrayList<ArrayList> ret = new ArrayList<>();
        ret.add(propInfo);                              //obsahuje info o liste vlastnictva (cadaster, ID listu vlast.)
        ret.add(propID);                                //obsahuje ID nehnutelnosti, ktore sa nachadzaju v liste vlastnictva
        ret.add(propAdd);                               //obsahuje adresu nehnutelnosti na rovnakom indexe ako v propID
        ret.add(personID);                              //obsahuje osobne cisla vcetkych osob zapisanych na liste vlastnictva
        ret.add(personName);                            //obsahuje mena osob uvedenych na rovnakom indexe v personID
        ret.add(personSurname);                         //obsahuje priezviska osob uvedenych na rovnakom indexe v personID
        ret.add(personCadasterIdPermAdd);               //obsahuje oznacenie katastra, v ktorom ma dana osoba trvaly pobyt, ak nema trvaly pobyt, tak null
        ret.add(personPropPermAddID);                   //obsahuje ID nehnutelnosti, v ktorej ma dana osoba trvaly pobyt, ak nema trvaly pobyt, tak null
        ret.add(personPropPermAdd);                     //obsahuje adresu, na ktorej ma dana osoba trvaly pobyt, ak nema trvaly pobyt, tak null
        List<Double> rate = propList.getOwnerRate();
        ArrayList<Double> rate2 = new ArrayList<>();
        for (Double r : rate) {
            rate2.add(r);
        }
        ret.add(rate2);                                 //obsahuje podiely osob sporovane indexom

        return ret;
    }

    /**
     * Preklad informacii katastra na vonkajsie vyuzitelny vypis
     */
    private ArrayList<Object> translateCadaster(Cadaster cad) {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(cad.getCadIDnumber());
        temp.add(cad.getCadIDname());
        return temp;
    }

    /**
     * Preklad informacii skupiny katastrov na vonkajsie vyuzitelny vypis
     */
    private ArrayList<ArrayList> translateCadaster(List<Cadaster> cadList) {
        ArrayList<ArrayList> temp = new ArrayList<>();
        for (Cadaster cad : cadList) {
            temp.add(translateCadaster(cad));
        }
        return temp;
    }

    /**
     * Preklad informacii osoby na vonkajsie vyuzitelny vypis (bez referencii)
     */
    private ArrayList<Object> translatePerson(Person pers) {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(pers.getPersonID());
        temp.add(pers.getName());
        temp.add(pers.getSurName());
        if (pers.getPermAddress() != null) {
            temp.add(pers.getPermAddress().getRelatedPropertyList().getCadasterLocation().getCadIDname());
            temp.add(pers.getPermAddress().getPropertIDnum());
            temp.add(pers.getPermAddress().getPropertyAddress());
        } else {
            temp.add(null);
            temp.add(null);
            temp.add(null);
        }
        return temp;
    }

    /**
     * Preklad informacii skupiny osob na vonkajsie vyuzitelny vypis (bez
     * referencii)
     */
    private ArrayList<ArrayList> translatePerson(List<Person> pers) {
        ArrayList<ArrayList> temp = new ArrayList<>();
        for (Person per : pers) {
            temp.add(translatePerson(per));
        }
        return temp;
    }

    /**
     * Preklad informacii nehnutelnosti na vonkajsie vyuzitelny vypis (bez
     * referencii)
     */
    private ArrayList<ArrayList> translateProperty(Property prop) {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(prop.getRelatedPropertyList().getCadasterLocation().getCadIDname());
        temp.add(prop.getPropertIDnum());
        temp.add(prop.getPropertyAddress());
        temp.add(prop.getRelatedPropertyList().getListIDnum());
        ArrayList<ArrayList> LLofProp = getPropertyListInfo(prop.getRelatedPropertyList());
        LLofProp.add(temp);
        return LLofProp;
    }

    /**
     * Preklad informacii skupiny nehnutelnosti na vonkajsie vyuzitelny vypis
     * (bez referencii)
     */
    private ArrayList<ArrayList> translateProperty(List<Property> propList) {
        ArrayList<ArrayList> temp = new ArrayList<>();
        ArrayList<Object> LLofProp = null;
        for (Property prop : propList) {
            LLofProp = new ArrayList<>();
            LLofProp.add(prop.getRelatedPropertyList().getCadasterLocation().getCadIDname());
            LLofProp.add(prop.getPropertIDnum());
            LLofProp.add(prop.getPropertyAddress());
            LLofProp.add(prop.getRelatedPropertyList().getListIDnum());
            temp.add(LLofProp);
        }
        return temp;
    }

    /**
     * Preklad informacii listu vlastnictva na vonkajsie vyuzitelny vypis (bez
     * referencii)
     */
    private ArrayList<ArrayList> translatePropertyList(PropertyList propList) {
        return getPropertyListInfo(propList);
    }

    /**
     * Preklad informacii skupiny listov vlastnictva na vonkajsie vyuzitelny
     * vypis (bez referencii)
     */
    private ArrayList<ArrayList> translatePropertyList(List<PropertyList> propListList) {
        ArrayList<ArrayList> temp = new ArrayList<>();
        ArrayList<Object> LLofPropList = null;
        for (PropertyList propList : propListList) {
            LLofPropList = new ArrayList<>();
            LLofPropList.add(propList.getCadasterLocation().getCadIDnumber());
            LLofPropList.add(propList.getListIDnum());
            LLofPropList.add(propList.getListedProperties().size());
            LLofPropList.add(propList.getOwnerList().size());
            temp.add(LLofPropList);
        }
        return temp;
    }
}

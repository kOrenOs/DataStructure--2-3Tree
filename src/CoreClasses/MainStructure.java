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
import T2t3Package.T2t3;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Obsahuje vykonne jadro aplikacie, zacina sa tu struktura a poskytuje vsetky
 * metody na pracu s danou strukturou.
 *
 * @author korenciak.marek
 */
public class MainStructure {

    private T2t3<Integer, Cadaster> cadasterListByID;      //obsahuje katastre ulozene do stromovej struktury zoradene podla unikatneho cisla katastra
    private T2t3<String, Cadaster> cadasterListByName;     //obsahuje katastre ulozene do stromovej struktury zoradene podla unikatneho meno katastra
    private T2t3<String, Person> personByID;               //obsahuje osoby ulozene do stromovej struktury zoradene podla osobneho cisla
    private int maxCadasterIndex = 0;                       //maximalny index katastra, potrebne pri iterovani a vytvarani novych katastrov
    private int maxPersonIndex = 0;                         //maximalny index osob, potrebny pre vytvarani novych osob

    public MainStructure() {
        cadasterListByID = new T2t3<>();
        cadasterListByName = new T2t3<>();
        personByID = new T2t3<>();
    }

    /**
     * Zresetuje celu strukturu.
     */
    public void resetStructure() {
        cadasterListByID = new T2t3<>();
        cadasterListByName = new T2t3<>();
        personByID = new T2t3<>();
        maxCadasterIndex = 0;
        maxPersonIndex = 0;
    }

    /**
     * Najde kataster podla cisla katastra
     */
    public Cadaster findCadasterInternal(int cadsterIDnum) {
        try {
            Cadaster temp = cadasterListByID.searchData(cadsterIDnum).getValue();
            if (temp == null) {
                throw new DataStructureException("Action failed. Cadaster with this name is not exist.");
            }
            return temp;
        } catch (NullPointerException e) {
            throw new DataStructureException("Action failed. Cadaster with this name is not exist.");
        }
    }

    /**
     * Najde kataster podla mena katastra
     */
    public Cadaster findCadasterInternal(String cadsterIDName) {
        try {
            Cadaster temp = cadasterListByName.searchData(cadsterIDName).getValue();
            if (temp == null) {
                throw new DataStructureException("Action failed. Cadaster with this name is not exist.");
            }
            return temp;
        } catch (NullPointerException e) {
            throw new DataStructureException("Action failed. Cadaster with this name is not exist.");
        }
    }

    /**
     * Vyhlada nehnutelnost zadanu menom katastralneho uzemia a cislom
     * nehnutelnosti
     */
    public Property findPropertyInternal(String cadsterIDName, int propIDnum) {
        Cadaster temp = findCadasterInternal(cadsterIDName);
        return findPropertyInternal(temp, propIDnum);
    }

    /**
     * Vyhlada nehnutelnost zadanu cislom katastralneho uzemia a cislom
     * nehnutelnosti
     */
    public Property findPropertyInternal(int cadsterIDName, int propIDnum) {
        Cadaster temp = findCadasterInternal(cadsterIDName);
        return findPropertyInternal(temp, propIDnum);
    }

    /**
     * Vyhlada nehnutelnost zadanu samotnym katastralnym uzemim a cislom
     * nehnutelnosti
     */
    private Property findPropertyInternal(Cadaster cad, int propIDnum) {
        return cad.findProperty(propIDnum);
    }

    /**
     * Vyhlada list vlastnictva zadany menom katastralneho uzemia a cislom listu
     * vlastnictva
     */
    public PropertyList findPropertyListInternal(String cadsterIDName, int propListIDnum) {
        Cadaster temp = findCadasterInternal(cadsterIDName);
        return findPropertyListInternal(temp, propListIDnum);
    }

    /**
     * Vyhlada list vlastnictva zadany cislom katastralneho uzemia a cislom
     * listu vlastnictva
     */
    public PropertyList findPropertyListInternal(int cadsterIDName, int propListIDnum) {
        Cadaster temp = findCadasterInternal(cadsterIDName);
        return findPropertyListInternal(temp, propListIDnum);
    }

    /**
     * Vyhlada list vlastnictva zadany samotnym katastralnym uzemim a cislom
     * listu vlastnictva
     */
    private PropertyList findPropertyListInternal(Cadaster cadster, int propListIDnum) {
        return cadster.findPropertyList(propListIDnum);
    }

    /**
     * Vyhlada osobu s urcitym osobnym cislom
     */
    public Person findPersonInternal(String personID) {
        try {
            return personByID.searchData(personID).getValue();
        } catch (NullPointerException e) {
            throw new DataStructureException("Action failed. Person with this ID is not exist.");
        }
    }

    /**
     * Vyhlada nehnutelnost osoby zadanej osobnym cislom, v ktorej ma urceny
     * trvaly pobyt, ak trvaly pobyt nema urceny, tak vrati null
     */
    public Property findPermPAddressInternal(String personID) {
        Person temp = findPersonInternal(personID);
        if (temp.getPermAddress() == null) {
            throw new DataStructureException("This person doesn t have perm address.");
        }
        return findPersonInternal(personID).getPermAddress();
    }

    /**
     * Vykonavacia metoda, ktora vyhlada a vrati nehnutelnosti, ktore su vo
     * vlastnictne osoby zadanej osobnym cislom a v danom katastralnom uzemi
     */
    private LinkedList<Property> findPropertyOfOwnerWork(String ownerPersonalID, Cadaster cad) {
        Person owner = findPersonInternal(ownerPersonalID);
        LinkedList<PropertyList> lists = owner.getPropertyLists();

        LinkedList<Property> properties = new LinkedList<>();
        for (PropertyList list : lists) {
            if (list.getCadasterLocation() == cad) {
                properties.addAll(list.getListedProperties());
            }
        }
        return properties;
    }

    /**
     * Metoda, ktora predstavuje pristup ku vykonnej metode na najdenie
     * nehnutelnosti patriace osobe v danom katastralnom uzemi. Katastralne
     * uzemie je dane cislom.
     */
    public LinkedList<Property> findPropertyOfOwnerInternal(String ownerPersonalID, int cadasterID) {
        Cadaster cadaster = findCadasterInternal(cadasterID);
        return findPropertyOfOwnerWork(ownerPersonalID, cadaster);
    }

    /**
     * Metoda, ktora predstavuje pristup ku vykonavacia metode na najdenie
     * nehnutelnosti patriace osobe v danom katastralnom uzemi. Katastralne
     * uzemie je dane menom.
     */
    public LinkedList<Property> findPropertyOfOwnerInternal(String ownerPersonalID, String cadasterID) {
        Cadaster cadaster = findCadasterInternal(cadasterID);
        return findPropertyOfOwnerWork(ownerPersonalID, cadaster);
    }

    /**
     * Vykonavacia metoda, ktora vyhlada vsetky nehnutelnosti osoby danej
     * osobnym cislo.
     */
    public LinkedList<Property> findPropertyOfOwnerInternal(String ownerPersonalID) {
        Person owner = findPersonInternal(ownerPersonalID);
        LinkedList<PropertyList> lists = owner.getPropertyLists();

        LinkedList<Property> properties = new LinkedList<>();
        for (PropertyList list : lists) {
            properties.addAll(list.getListedProperties());
        }
        return properties;
    }

    /**
     * Vyhlada osobny, ktore maju trvaly pobyt v nehnutelnosti zadanej menom
     * katastralneho uzemia a cislom nehnutelnosti.
     */
    public LinkedList<Person> findOccupiersOfPropertyInternal(String cadasterName, int propertyID) {
        Property temp = findPropertyInternal(cadasterName, propertyID);
        return temp.getOccupiers();
    }

    /**
     * Vyhlada osobny, ktore maju trvaly pobyt v nehnutelnosti zadanej cislom
     * katastralneho uzemia a cislom nehnutelnosti.
     */
    public LinkedList<Person> findOccupiersOfPropertyInternal(int cadasterID, int propertyID) {
        Property temp = findPropertyInternal(cadasterID, propertyID);
        return temp.getOccupiers();
    }

    /**
     * Vrati zoznam vsetkych osob
     */
    public List<Person> findAllPeopleInternal() {
        return personByID.inOrder();
    }

    /**
     * Vrati zoznam vsetkych katastralnych uzemi zoradene podla cisla
     */
    public ArrayList<Cadaster> getCadastersByIDInternal() {
        return cadasterListByID.inOrder();
    }

    /**
     * Vrati zoznam vsetkych katastralnych uzemi zoradene podla mena
     */
    public ArrayList<Cadaster> getCadastersByNameInternal() {
        return cadasterListByName.inOrder();
    }

    /**
     * Vykonavajuca metoda, vrati zoznam vsetkych listov vlastnictva podla cisla
     * listu vlastnictva v danom katastralnom uzemi
     */
    private ArrayList<PropertyList> getPropertyListsOfCadasterWork(Cadaster cad) {
        try {
            return cad.getIncludedPropertyLists();
        } catch (NullPointerException e) {
            throw new DataStructureException("This cadaster doesn t have any property lists.");
        }
    }

    /**
     * Pristupova metoda na vratenie vsetkych listov vlastnictva v danom
     * katastralnom uzemi (list vlastnictva zadany cislom, katastralne uzemie
     * cislom)
     */
    public ArrayList<PropertyList> getPropertyListsOfCadasterInternal(int cadasterID) {
        Cadaster cadaster = findCadasterInternal(cadasterID);
        return getPropertyListsOfCadasterWork(cadaster);
    }

    /**
     * Pristupova metoda na vratenie vsetkych listov vlastnictva v danom
     * katastralnom uzemi (list vlastnictva zadany cislom, katastralne uzemie
     * menom)
     */
    public ArrayList<PropertyList> getPropertyListsOfCadasterInternal(String cadasterID) {
        Cadaster cadaster = findCadasterInternal(cadasterID);
        return getPropertyListsOfCadasterWork(cadaster);
    }

    /**
     * Vykonavajuca metoda, vrati zoznam vsetkych nehnutelnosti podla cisla
     * nehnutelnosti v danom katastralnom uzemi
     */
    private ArrayList<Property> getPropertyOfCadasterWork(Cadaster cad) {
        try {
            return cad.getIncludedProperties();
        } catch (NullPointerException e) {
            throw new DataStructureException("This cadaster doesn t have any properties.");
        }
    }

    /**
     * Pristupova metoda na vratenie zoznamu vsetkych nehnutelnosti katastra
     * zadaneho menom
     */
    public ArrayList<Property> getPropertyOfCadasterInternal(String cadasterID) {
        Cadaster cadaster = findCadasterInternal(cadasterID);
        return getPropertyOfCadasterWork(cadaster);
    }

    /**
     * Pristupova metoda na vratenie zoznamu vsetkych nehnutelnosti katastra
     * zadaneho cislom
     */
    public ArrayList<Property> getPropertyOfCadasterInternal(int cadasterID) {
        Cadaster cadaster = findCadasterInternal(cadasterID);
        return getPropertyOfCadasterWork(cadaster);
    }

    /**
     * Vykonavacia metoda, ktora prida novy list vlastnoctva do daneho katastra
     */
    private PropertyList addPropertyListWork(Cadaster cad, LinkedList<Integer> initListedProperties, LinkedList<String> initOwnerList) {
        LinkedList<Property> listedProperties = new LinkedList<>();
        LinkedList<Person> owners = new LinkedList<>();
        if (initListedProperties != null) {
            for (Integer property : initListedProperties) {
                listedProperties.add(findPropertyInternal(cad, property));
            }
        }
        if (initOwnerList != null) {
            for (String owner : initOwnerList) {
                owners.add(findPersonInternal(owner));
            }
        }

        return cad.addPropertyList(listedProperties, owners);
    }

    /**
     * Pristupova metoda na vytvorenie noveho listu vlatsnictva, zada sa cislo
     * katastra a mnozina nehnutelnosti a vlastnikov
     */
    public PropertyList addPropertyListInternal(int initCadasterID, LinkedList<Integer> initListedProperties, LinkedList<String> initOwnerList) {
        Cadaster temp = findCadasterInternal(initCadasterID);
        return addPropertyListWork(temp, initListedProperties, initOwnerList);
    }

    /**
     * Pristupova metoda na vytvorenie noveho listu vlatsnictva, zada sa meno
     * katastra a mnozina nehnutelnosti a vlastnikov
     */
    public PropertyList addPropertyListInternal(String initCadasterName, LinkedList<Integer> initListedProperties, LinkedList<String> initOwnerList) {
        Cadaster temp = findCadasterInternal(initCadasterName);
        return addPropertyListWork(temp, initListedProperties, initOwnerList);
    }

    /**
     * Vykonavacia metoda, prida nehnutelnost do daneho listu vlatsnictva v
     * danom katastralnom uzemi
     */
    private Property addPropertyWork(Cadaster cad, String initAddress, int initRelatedPropertyListID) {
        PropertyList tempPropertyListID = findPropertyListInternal(cad, initRelatedPropertyListID);

        Property newProperty = cad.addProperty(initAddress, tempPropertyListID);
        tempPropertyListID.addProperty(newProperty);
        return newProperty;
    }

    /**
     * Pristupova metoda na pridanie nehnutelnosti v zadanom katastralnom uzemi
     * (definovane cislom) a v nom do daneho listu vlatnictva
     */
    public Property addPropertyInternal(int initCadasterID, String initAddress, int initRelatedPropertyListID) {
        Cadaster temp = findCadasterInternal(initCadasterID);
        return addPropertyWork(temp, initAddress, initRelatedPropertyListID);
    }

    /**
     * Pristupova metoda na pridanie nehnutelnosti v zadanom katastralnom uzemi
     * (definovane menom) a v nom do daneho listu vlatnictva
     */
    public Property addPropertyInternal(String initCadasterName, String initAddress, int initRelatedPropertyListID) {
        Cadaster temp = findCadasterInternal(initCadasterName);
        return addPropertyWork(temp, initAddress, initRelatedPropertyListID);
    }

    /**
     * Pridanie noveho katastralneho uzemia, vstpom je meno katastralneho
     * uzemia, ktore tiez musi byt unikatne
     */
    public Cadaster addCadasterInternal(String cadasterName) {
        if (cadasterName.compareTo("") != 0) {
            cadasterName = cadasterName.substring(0, 1).toUpperCase() + cadasterName.substring(1);
            if (cadasterListByName.searchData(cadasterName) == null) {
                Cadaster temp = new Cadaster(maxCadasterIndex, cadasterName);
                if (cadasterListByID.add(maxCadasterIndex, temp)) {
                    if (cadasterListByName.add(cadasterName, temp)) {
                        maxCadasterIndex++;
                        return temp;
                    } else {
                        cadasterListByID.delete(temp.getCadIDnumber());
                        throw new DataStructureException("Action failed. Unexpected error.");
                    }
                }
                throw new DataStructureException("Action failed. Cadster ID iterator fail.");
            }
            throw new DataStructureException("Action failed. Cadaster with this name exist yet.");
        }
        throw new DataStructureException("Action failed. Set name please.");
    }

    /**
     * Pridanie novej osoby definovane menom a priezviskom
     */
    public Person addPersonInternal(String initName, String initSurName) {
        String initPersonID = String.format("%016d", maxPersonIndex);
        initName = initName.substring(0, 1).toUpperCase() + initName.substring(1);
        initSurName = initSurName.substring(0, 1).toUpperCase() + initSurName.substring(1);
        Person temp = new Person(initName, initSurName, initPersonID, null);
        if (personByID.add(initPersonID, temp)) {
            maxPersonIndex++;
            return temp;
        }
        throw new DataStructureException("Action failed. Person ID iterator fail.");
    }

    /**
     * Pridanie novej osoby definovane menom, priezviskom a nehnutelnostou
     * (trvalym pobytom)
     */
    public Person addPersonInternal(String initName, String initSurName, String cadasterIDNameOfProp, int IDnumOfProp) {
        String initPersonID = String.format("%016d", maxPersonIndex);
        initName = initName.substring(0, 1).toUpperCase() + initName.substring(1);
        initSurName = initSurName.substring(0, 1).toUpperCase() + initSurName.substring(1);
        Property permAddress = findPropertyInternal(cadasterIDNameOfProp, IDnumOfProp);
        Person temp = null;
        if (permAddress != null) {
            temp = new Person(initName, initSurName, initPersonID, permAddress);
            permAddress.addOccupier(temp);
        } else {
            throw new DataStructureException("Action failed. Property not found.");
        }
        if (personByID.add(initPersonID, temp)) {
            maxPersonIndex++;
            return temp;
        }
        throw new DataStructureException("Action failed. Person ID iterator fail.");
    }

    /**
     * Vykonna metoda, pridanie vlastnika do listu vlastnictva
     */
    private PropertyList addOwnerToPropertyListWork(Cadaster cad, int PropertyListID, String perosnID, double ownerRate) {
        PropertyList propList = findPropertyListInternal(cad, PropertyListID);
        Person pers = findPersonInternal(perosnID);
        if (propList.addOwner(pers, ownerRate) == null) {
            throw new DataStructureException("Person is owner of this property list yet.");
        }
        return propList;
    }

    /**
     * Pristupova metoda na pridanie vlatnika do listu vlastnictva, kde je
     * kataster definovany cez cislo
     */
    public PropertyList addOwnerToPropertyListInternal(int cadasterID, int PropertyListID, String perosnID, double ownerRate) {
        Cadaster cad = findCadasterInternal(cadasterID);
        return addOwnerToPropertyListWork(cad, PropertyListID, perosnID, ownerRate);
    }

    /**
     * Pristupova metoda na pridanie vlatnika do listu vlastnictva, kde je
     * kataster definovany cez meno
     */
    public PropertyList addOwnerToPropertyListInternal(String cadasterID, int PropertyListID, String perosnID, double ownerRate) {
        Cadaster cad = findCadasterInternal(cadasterID);
        return addOwnerToPropertyListWork(cad, PropertyListID, perosnID, ownerRate);
    }

    /**
     * Vykonna metoda na pridanie listu vlatnictva, pricom je pridana aj
     * nehnutelnost, ktora je tiez nanovo vytvorena
     */
    private PropertyList addPropertyListNewPropertyWork(Cadaster cad, String personID, String address) {
        LinkedList<Property> listedProperties = new LinkedList<>();
        LinkedList<Person> owners = new LinkedList<>();
        owners.add(findPersonInternal(personID));
        PropertyList propList = cad.addPropertyList(listedProperties, owners);
        propList.addProperty(cad.addProperty(address, propList));
        return propList;
    }

    /**
     * Pristupova metoda na pridanie listu vlastnictva s definovanou novou
     * nehnutelnosotu, katastralne uzemie je definovane cez meno
     */
    public PropertyList addPropertyListNewPropertyInternal(String cadName, String personID, String address) {
        Cadaster temp = findCadasterInternal(cadName);
        return addPropertyListNewPropertyWork(temp, personID, address);
    }

    /**
     * Pristupova metoda na pridanie listu vlastnictva s definovanou novou
     * nehnutelnosotu, katastralne uzemie je definovane cez cislo
     */
    public PropertyList addPropertyListNewPropertyInternal(int cadID, String personID, String address) {
        Cadaster temp = findCadasterInternal(cadID);
        return addPropertyListNewPropertyWork(temp, personID, address);
    }

    /**
     * Vykonna metoda na pridanie listu vlatsnictva, kde sa hned prida aj
     * existujuca nehnutelnost
     */
    private PropertyList addPropertyListExistPropertyWork(Cadaster cad, String personID, int propertyID) {
        Property prop = findPropertyInternal(cad, propertyID);
        PropertyList momentalPropList = prop.getRelatedPropertyList();
        momentalPropList.deleteProperty(prop);
        if (momentalPropList.getListedProperties().size() == 0) {
            cad.deletePropertyList(momentalPropList.getListIDnum());
        }
        LinkedList<Property> listedProperties = new LinkedList<>();
        LinkedList<Person> owners = new LinkedList<>();
        owners.add(findPersonInternal(personID));
        listedProperties.add(prop);
        return cad.addPropertyList(listedProperties, owners);
    }

    /**
     * Pristupova metoda na pridanie listu vlastnictva s definovanou existujucou
     * nehnutelnosotu, katastralne uzemie je definovane cez meno
     */
    public PropertyList addPropertyListExistPropertyInternal(String cadName, String personID, int propertyID) {
        Cadaster temp = findCadasterInternal(cadName);
        return addPropertyListExistPropertyWork(temp, personID, propertyID);
    }

    /**
     * Pristupova metoda na pridanie listu vlastnictva s definovanou existujucou
     * nehnutelnosotu, katastralne uzemie je definovane cez cislo
     */
    public PropertyList addPropertyListExistPropertyInternal(int cadID, String personID, int propertyID) {
        Cadaster temp = findCadasterInternal(cadID);
        return addPropertyListExistPropertyWork(temp, personID, propertyID);
    }

    /**
     * Vykonna metoda, zmena trvaleho pobytu osoby
     */
    private boolean changePermAddressWork(String personID, Cadaster cad, int propID) {
        Property prop = findPropertyInternal(cad, propID);
        Person person = findPersonInternal(personID);
        prop.addOccupier(person);
        if (person.getPermAddress() != null) {
            person.getPermAddress().removeOccupier(person);
        }
        person.setPermAddress(prop);

        return true;
    }

    /**
     * Pristupova metoda na zmenu trvaleho pobytu osoby definovanej
     * prostrednictvom osobneho cisla, kataster defin. cez meno a nehnutelnost
     * prostrednictvom cisla
     */
    public boolean changePermAddressInternal(String personID, String cadasterName, int propID) {
        Cadaster cad = findCadasterInternal(cadasterName);
        return changePermAddressWork(personID, cad, propID);
    }

    /**
     * Pristupova metoda na zmenu trvaleho pobytu osoby definovanej
     * prostrednictvom osobneho cisla, kataster defin. cez cislo a nehnutelnost
     * prostrednictvom cisla
     */
    public boolean changePermAddressInternal(String personID, int cadasterName, int propID) {
        Cadaster cad = findCadasterInternal(cadasterName);
        return changePermAddressWork(personID, cad, propID);
    }

    /**
     * Vykonna metoda na vymenu dvoch vlastnikov na liste vlastnictva
     */
    private PropertyList changeOwnerOfPropertyListWork(Cadaster cad, int propertyID, String actualOwnerPersonID, String newOwnerPersonID) {
        PropertyList propList = findPropertyListInternal(cad, propertyID);
        propList.changeOwner(findPersonInternal(actualOwnerPersonID), findPersonInternal(newOwnerPersonID));
        return propList;
    }

    /**
     * Pristupova metoda na vymenu dvoch osob na liste vlastnictva definovanom
     * menom katastra, cislom listu vlastnictva a osobnymi cislami osob
     */
    public PropertyList changeOwnerOfPropertyListInternal(String cadasterName, int propertyID, String actualOwnerPersonID, String newOwnerPersonID) {
        Cadaster cad = findCadasterInternal(cadasterName);
        return changeOwnerOfPropertyListWork(cad, propertyID, actualOwnerPersonID, newOwnerPersonID);
    }

    /**
     * Pristupova metoda na vymenu dvoch osob na liste vlastnictva definovanom
     * cislom katastra, cislom listu vlastnictva a osobnymi cislami osob
     */
    public PropertyList changeOwnerOfPropertyListInternal(int cadasterID, int propertyID, String actualOwnerPersonID, String newOwnerPersonID) {
        Cadaster cad = findCadasterInternal(cadasterID);
        return changeOwnerOfPropertyListWork(cad, propertyID, actualOwnerPersonID, newOwnerPersonID);
    }

    /**
     * Vykonna metoda na prenesenie vsetkych nehnutelnosti na iny list
     * vlastnictva
     */
    private PropertyList migrateAllPropertyToAnotherPropertyListWork(Cadaster cad, PropertyList homePropListID, PropertyList destinationPropListID) {
        LinkedList<Property> relatedProperties = homePropListID.getListedProperties();
        LinkedList<Person> relatedOwners = homePropListID.getOwnerList();

        for (Property prop : relatedProperties) {
            destinationPropListID.addProperty(prop);
            prop.setRelatedPropertyList(destinationPropListID);
        }

        for (Person pers : relatedOwners) {
            destinationPropListID.addOwner(pers, 0.0);
        }

        cad.deletePropertyList(homePropListID.getListIDnum());
        return destinationPropListID;
    }

    /**
     * Pristupova metoda na prenesenie vsetkych nehnutelnosti na iny list
     * vlastnictva, kataster definovany menom a listy vlastnictva cislom
     */
    public PropertyList migrateAllPropertyToAnotherPropertyListInternal(String cadasterName, int homePropListID, int destinationPropListID) {
        Cadaster cad = findCadasterInternal(cadasterName);
        PropertyList home = findPropertyListInternal(cad, homePropListID);
        PropertyList destination = findPropertyListInternal(cad, destinationPropListID);
        return migrateAllPropertyToAnotherPropertyListWork(cad, home, destination);
    }

    /**
     * Pristupova metoda na prenesenie vsetkych nehnutelnosti na iny list
     * vlastnictva, kataster definovany cislom a listy vlastnictva cislom
     */
    public PropertyList migrateAllPropertyToAnotherPropertyListInternal(int cadasterID, int homePropListID, int destinationPropListID) {
        Cadaster cad = findCadasterInternal(cadasterID);
        PropertyList home = findPropertyListInternal(cad, homePropListID);
        PropertyList destination = findPropertyListInternal(cad, destinationPropListID);
        return migrateAllPropertyToAnotherPropertyListWork(cad, home, destination);
    }

    /**
     * Vykonna metoda na vymazanie osoby z listu vlastnictva
     */
    private PropertyList deleteOwnerOfPropertyListWork(Cadaster cad, int propertyID, String ownerPersonID, Integer backUpPropListID) {
        PropertyList propList = null;
        propList = findPropertyListInternal(cad, propertyID);
        if (propList.deleteOwner(findPersonInternal(ownerPersonID)) == null) {
            if (backUpPropListID == null) {
                throw new DataStructureException("Unable to delete part of this owner (last owner). Set back-up prop list.");
            }
            PropertyList backUp = findPropertyListInternal(cad, backUpPropListID);
            if (backUp != null) {

            } else {
                throw new DataStructureException("Back up prop list not found.");
            }

            migrateAllPropertyToAnotherPropertyListWork(cad, propList, backUp);
        }else{
            throw new DataStructureException("This person is not owner of property list.");
        }

        return propList;
    }

    /**
     * Pristupova metoda na vymazanie vlastnika z listu vlastnictva, kde je
     * kataster definovany menom, vastnik osobnym cislom a listy vlastnictva
     * cisloms
     */
    public PropertyList deleteOwnerOfPropertyListInternal(String cadasterName, int propertyID, String ownerPersonID, Integer backUpPropListID) {
        Cadaster cad = findCadasterInternal(cadasterName);
        return deleteOwnerOfPropertyListWork(cad, propertyID, ownerPersonID, backUpPropListID);
    }

    /**
     * Pristupova metoda na vymazanie vlastnika z listu vlastnictva, kde je
     * kataster definovany cislom, vastnik osobnym cislom a listy vlastnictva
     * cisloms
     */
    public PropertyList deleteOwnerOfPropertyListInternal(int cadasterID, int propertyID, String ownerPersonID, Integer backUpPropListID) {
        Cadaster cad = findCadasterInternal(cadasterID);
        return deleteOwnerOfPropertyListWork(cad, propertyID, ownerPersonID, backUpPropListID);
    }

    /**
     * Vykonna metoda na vymazanie nehnutelnosti zo systemu
     */
    private boolean deletePropertyWork(Cadaster cad, int propertyID, int propertyListID) {
        Property prop = findPropertyInternal(cad, propertyID);
        if (prop.getRelatedPropertyList().getListIDnum() == propertyListID) {
            if (prop.getRelatedPropertyList().getListedProperties().size() == 1) {
                cad.deletePropertyList(prop.getRelatedPropertyList().getListIDnum());
            }
            prop.getRelatedPropertyList().deleteProperty(prop);
            cad.deleteProperty(propertyID);
            LinkedList<Person> occupiers = new LinkedList<>();
            occupiers = prop.getOccupiers();
            for (Person occupier : occupiers) {
                occupier.setPermAddress(null);
            }

        } else {
            return false;
        }
        return true;
    }

    /**
     * Pristupova metoda na vymazanie nehnutelnosti zo systemu, kataster
     * definovany cislom, nehnutelnost cislom a list valstnitctva tiez cislom
     */
    public boolean deletePropertyInternal(int cadasterID, int propertyID, int propertyListID) {
        Cadaster temp = findCadasterInternal(cadasterID);
        return deletePropertyWork(temp, propertyID, propertyListID);
    }

    /**
     * Pristupova metoda na vymazanie nehnutelnosti zo systemu, kataster
     * definovany menom, nehnutelnost cislom a list valstnitctva tiez cislom
     */
    public boolean deletePropertyInternal(String cadasterName, int propertyID, int propertyListID) {
        Cadaster temp = findCadasterInternal(cadasterName);
        return deletePropertyWork(temp, propertyID, propertyListID);
    }

    /**
     * Vykonna metoda na premigrovanie katastra do druheho a nasledne vyazanie
     */
    private Cadaster deleteCadasterWork(Cadaster home, Cadaster destination) {
        if (home == destination) {
            throw new DataStructureException("Can t set back-up cadaster same as deleted.");
        }
        List<PropertyList> propLists = getPropertyListsOfCadasterWork(home);
        for (PropertyList propList : propLists) {
            destination.addExistingPropertyList(propList);
        }
        cadasterListByID.delete(home.getCadIDnumber());
        cadasterListByName.delete(home.getCadIDname());
        return destination;
    }

    /**
     * Pristupova metoda na vymazanie katastralneho uzemia, katastre definovane
     * menami
     */
    public Cadaster deleteCadasterInternal(String homeName, String destinationName) {
        Cadaster home = findCadasterInternal(homeName);
        Cadaster destination = findCadasterInternal(destinationName);
        return deleteCadasterWork(home, destination);
    }

    /**
     * Pristupova metoda na vymazanie katastralneho uzemia, katastre definovane
     * cislami
     */
    public Cadaster deleteCadasterInternal(int homeID, int destinationID) {
        Cadaster home = findCadasterInternal(homeID);
        Cadaster destination = findCadasterInternal(destinationID);
        return deleteCadasterWork(home, destination);
    }

    /**
     * Pristupova metoda na vymazanie katastralneho uzemia, katastre definovane
     * menom a cislom
     */
    public Cadaster deleteCadasterInternal(String homeName, int destinationID) {
        Cadaster home = findCadasterInternal(homeName);
        Cadaster destination = findCadasterInternal(destinationID);
        return deleteCadasterWork(home, destination);
    }

    /**
     * Pristupova metoda na vymazanie katastralneho uzemia, katastre definovane
     * cislom a menom
     */
    public Cadaster deleteCadasterInternal(int homeID, String destinationName) {
        Cadaster home = findCadasterInternal(homeID);
        Cadaster destination = findCadasterInternal(destinationName);
        return deleteCadasterWork(home, destination);
    }

    /**
     * Vymazanie osoby zo systemu definovany osobnym cislom osoby a osobnym
     * cislom osoby, ktora ma v pripade potreby prebrat jeho miesto (napr. ak je
     * poslednym vlastnikom na lise vlastnictva)
     */
    public boolean deletePersonInternal(String deletedPersonID, String backUpPersonID) {
        Person deleted = findPersonInternal(deletedPersonID);
        Person backUp = null;
        if (backUpPersonID != null) {
            backUp = findPersonInternal(backUpPersonID);
        }
        LinkedList<PropertyList> propListsOfOwner = deleted.getPropertyLists();
        for (PropertyList propList : propListsOfOwner) {
            if (propList.getOwnerList().size() == 1) {
                if (backUp == null) {
                    throw new DataStructureException("For delete this person you have to set back-up person.");
                } else {
                    propList.changeOwner(deleted, backUp);
                }
            } else {
                propList.deleteOwner(deleted);
            }
        }

        if (deleted.getPermAddress() != null) {
            deleted.getPermAddress().removeOccupier(deleted);
        }
        personByID.delete(deletedPersonID);
        return true;
    }

    /**
     * Ulozenie momentalneho stavy systemu, vstupom je meno suboru, kde chceme
     * system ulozit (bez pripony)
     */
    public boolean saveToFile(String filePath) {
        PrintWriter writeInto = null;
        try {
            writeInto = new PrintWriter(new File(filePath));
        } catch (FileNotFoundException e) {
            return false;
        }

        ArrayList<Cadaster> cadasterView = cadasterListByID.inOrder();
        ArrayList<Person> personView = personByID.inOrder();
        ArrayList<Property> propertyView = null;
        ArrayList<PropertyList> propListView = null;

        writeInto.println(cadasterView.size());
        for (Cadaster cadaster : cadasterView) {
            writeInto.println(cadaster.getCadIDnumber() + ", " + cadaster.getCadIDname());
        }

        for (Cadaster cadaster : cadasterView) {
            propertyView = cadaster.getIncludedProperties();
            writeInto.println(cadaster.getCadIDnumber() + ", " + propertyView.size());
            for (Property prop : propertyView) {
                writeInto.println(prop.getPropertIDnum() + ", " + prop.getPropertyAddress());
            }
        }

        writeInto.println(personView.size());
        for (Person person : personView) {
            if (person.getPermAddress() == null) {
                writeInto.println(person.getPersonID() + ", " + person.getName() + ", " + person.getSurName() + ", null, null");
            } else {
                writeInto.println(person.getPersonID() + ", " + person.getName() + ", " + person.getSurName() + ", "
                        + person.getPermAddress().getRelatedPropertyList().getCadasterLocation().getCadIDname() + ", " + person.getPermAddress().getPropertIDnum());
            }
        }

        LinkedList<Person> personOfPropList = null;
        LinkedList<Property> propOfPropList = null;
        LinkedList<Double> ownerRank = null;

        for (Cadaster cadaster : cadasterView) {
            propListView = cadaster.getIncludedPropertyLists();
            writeInto.println(cadaster.getCadIDnumber() + ", " + propListView.size());
            for (PropertyList propList : propListView) {
                propOfPropList = propList.getListedProperties();
                personOfPropList = propList.getOwnerList();
                ownerRank = propList.getOwnerRate();
                writeInto.println(propOfPropList.size() + ", " + personOfPropList.size());
                for (int i = 0; i < personOfPropList.size(); i++) {
                    writeInto.println(personOfPropList.get(i).getPersonID() + ", " + ownerRank.get(i));
                }

                for (int i = 0; i < propOfPropList.size(); i++) {
                    writeInto.println(propOfPropList.get(i).getPropertIDnum());
                }
            }
        }

        writeInto.close();
        return true;
    }

    /**
     * Naloadovanie systemu zo suboru definovaneho cestou (bez pripony)
     */
    public boolean loadFromFile(String filePath) {
        maxCadasterIndex = 0;
        maxPersonIndex = 0;
        cadasterListByID = new T2t3<>();
        cadasterListByName = new T2t3<>();
        personByID = new T2t3<>();
        int cadasterCout = 0;
        HashMap<Integer, Integer> propertyMap = new HashMap<>();
        HashMap<String, String> personMap = new HashMap<>();
        Scanner read = null;
        Cadaster initCadaster = null;
        Person initPerson = null;
        PropertyList initPropList = null;
        String line = null;
        String[] parse = null;

        try {
            read = new Scanner(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            return false;
        }

        parse = new String[2];
        int tempNum = read.nextInt();
        int tempVar = 0;
        read.nextLine();
        for (int i = 0; i < tempNum; i++) {
            line = read.nextLine();
            parse = line.split(", ", 2);
            tempVar = Integer.parseInt(parse[0]);
            initCadaster = new Cadaster(tempVar, parse[1]);

            if (maxCadasterIndex < tempVar) {
                maxCadasterIndex = tempVar;
            }

            cadasterCout++;
            cadasterListByID.add(tempVar, initCadaster);
            cadasterListByName.add(parse[1], initCadaster);
        }

        for (int j = 0; j < cadasterCout; j++) {
            line = read.nextLine();
            parse = line.split(", ", 2);
            tempNum = Integer.parseInt(parse[0]);
            initCadaster = findCadasterInternal(tempNum);
            tempNum = Integer.parseInt(parse[1]);
            parse = new String[2];
            for (int i = 0; i < tempNum; i++) {
                line = read.nextLine();
                parse = line.split(", ", 2);
                propertyMap.put(initCadaster.addProperty(parse[1], null).getPropertIDnum(), Integer.parseInt(parse[0]));
            }
        }

        tempNum = read.nextInt();
        read.nextLine();
        parse = new String[5];
        for (int i = 0; i < tempNum; i++) {
            line = read.nextLine();
            parse = line.split(", ", 5);
            if (parse[4].compareTo("null") == 0) {
                initPerson = MainStructure.this.addPersonInternal(parse[1], parse[2]);
                if (Integer.parseInt(initPerson.getPersonID()) > maxPersonIndex) {
                    maxPersonIndex = Integer.parseInt(initPerson.getPersonID());
                }
                line = personMap.put(initPerson.getPersonID(), parse[0]);
            } else {
                personMap.put(addPersonInternal(parse[1], parse[2], parse[3], propertyMap.get(Integer.parseInt(parse[4]))).getPersonID(), parse[0]);
            }
        }

        parse = new String[2];
        int propCount = 0;
        int personCount = 0;
        while (read.hasNext()) {
            line = read.nextLine();
            if (line.compareTo("") == 0) {
                line = read.nextLine();
            }
            parse = line.split(", ", 2);
            tempNum = Integer.parseInt(parse[0]);
            initCadaster = findCadasterInternal(tempNum);
            tempNum = Integer.parseInt(parse[1]);

            for (int s = 0; s < tempNum; s++) {
                line = read.nextLine();
                if (line.compareTo("") == 0) {
                    line = read.nextLine();
                }
                parse = line.split(", ", 2);
                propCount = Integer.parseInt(parse[0]);
                personCount = Integer.parseInt(parse[1]);
                initPropList = addPropertyListInternal(initCadaster.getCadIDname(), null, null);
                for (int i = 0; i < personCount; i++) {
                    line = read.nextLine();
                    parse = line.split(", ", 2);
                    initPropList.addOwner(findPersonInternal(personMap.get(parse[0])), 0.0);
                }
                for (int i = 0; i < propCount; i++) {
                    tempVar = read.nextInt();
                    initPropList.addProperty(findPropertyInternal(initCadaster, propertyMap.get(tempVar)));
                }
            }
        }
        return true;
    }
}

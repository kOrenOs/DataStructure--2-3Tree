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
import java.util.Random;

/**
 * Genetovanie nahodnych dat, ktore dodrzuju strukturu systemu
 *
 * @author korenciak.marek
 */
public class DataGenerator {

    public DataGenerator(MainStructure target, int cadasterNumber, int minPropertyListPerCadaster,
            int maxPropertyListPerCadaster, int minPropertiesPerPL, int maxPropertiesPerPL, int minPersonsPerPL, int maxPersonsPerPL) {

        target.resetStructure();
        
        Random chance = new Random();
        int propertiesNum = 0;
        int propertyListsNum = 0;
        int personsNum = 0;
        Property house = null;
        Cadaster tempCadaster = null;
        PropertyList tempPropList = null;
        Person onePerson = null;
        ArrayList<Property> createdProperty = new ArrayList<>();
        ArrayList<Person> createdPersons = new ArrayList<>();

        for (int i = 0; i < cadasterNumber; i++) {
            System.out.println("cadaster " + i + " creating...");
            try {
                tempCadaster = target.addCadasterInternal("CadNo" + chance.nextInt(200) + i);
            } catch (DataStructureException e) {
                System.out.println("aaaaa");
                i--;
                continue;
            }
            if (tempCadaster == null) {
                throw new NullPointerException();
            }

            propertyListsNum = chance.nextInt(maxPropertyListPerCadaster - minPropertyListPerCadaster) + minPropertyListPerCadaster;
            for (int j = 0; j < propertyListsNum; j++) {
                tempPropList = tempCadaster.addPropertyList(null, null);

                propertiesNum = chance.nextInt(maxPropertiesPerPL - minPropertiesPerPL) + minPropertiesPerPL;
                for (int k = 0; k < propertiesNum; k++) {
                    house = tempCadaster.addProperty("Address" + k * j + chance.nextInt(20), tempPropList);
                    tempPropList.addProperty(house);
                    createdProperty.add(house);
                }

                personsNum = chance.nextInt(maxPersonsPerPL - minPersonsPerPL) + minPersonsPerPL;
                for (int k = 0; k < personsNum; k++) {
                    if (createdPersons.size() > 10 && chance.nextDouble() < 0.5) {
                        tempPropList.addOwner(createdPersons.get(chance.nextInt(createdPersons.size())), 0.0);
                    } else {
                        if (chance.nextDouble() < 0.1) {
                            onePerson = tempPropList.addOwner(target.addPersonInternal("Name" + k * j, "Surname" + k * j), 0.0);
                        } else {
                            house = createdProperty.get(chance.nextInt(createdProperty.size()));
                            onePerson = tempPropList.addOwner(target.addPersonInternal("Name" + k * j, "Surname" + k * j,
                                    house.getRelatedPropertyList().getCadasterLocation().getCadIDname(), house.getPropertIDnum()), 0.0);
                        }
                        createdPersons.add(onePerson);
                    }
                }
            }
        }
    }
}

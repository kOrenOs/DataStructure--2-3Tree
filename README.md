# DataStructure--2-3Tree

This project was one of the schoolworks for the course Data structure. The main point was to implement data structure- 2-3 Three, validate it and demonstrate implemented code. Fake data are:
•	Cadaster
•	Person
•	Property
•	Property list

In the Cadaste can be more property lists. On the property list can be written more persons and properties. Person can have more property lists. Every cadaster has to have unique name and ID. Property has to be in the some Cadaster and have unique ID in this Cadaster.

It is possible to use GUI (ApplicationGUI class) to use all functionalities. It is able to create new data and make selections with this data. It is possible to use generator in the GUI mode (Data generator tab) to create huge amount of data. In the generator is necessary to fill in max and min number value of every type of data. All created data is possible to save in CSV format and later again reload. It will be open relevant property list, after click on property list or property.

Verification of structure is in the VerificatorRandomAction class. This class creates one instance of data structure and tries to input some amount of fake data there. This data are random, so it is possible, that it will try to insert data, which are already inserted. Then the data are not inserted. Second round is checking of deleting all data from the structure. After every action is called structure check sequence.

In autumn 2015

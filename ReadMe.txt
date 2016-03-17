FLIGHT OPERATIONS ONTOLOGY USING RDF & JENA API

Dataset
Use the given dataset FlightOperations.csv, which presents a record of
the number of flights occurred at Los Angeles International Airport.

Using the Jena API, write a Java program that reads a given dataset in CSV, and
outputs this data in RDF, i.e. the input of your program is a .csv file and the output of
your program is a .rdf file, where both files should be stored/generated onto the Desktop

Tools/IDE/Language: Protégé, Eclipse, Jena API, JAVA
Design Details:
Firstly I created my design using Protégé tool and verified the validity of my design.
I have designed the LAX Flight Operations as the main root: 
Thing to have the following classes: dataExtractDate, reportPeriod, operationCount,
flightClassification(domestic/international), flightStatus(arrival/departure), 
flightType(charter, commuter, scheduled), montlyData, flightData. 
I defined the properties and viewed the same in Protégé in the OntoGraf tab. 
The validity of the design was confirmed by exploring the various views and tabs of Protégé tool.

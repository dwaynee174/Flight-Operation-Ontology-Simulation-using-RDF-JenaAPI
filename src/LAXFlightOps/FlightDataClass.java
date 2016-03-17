package LAXFlightOps;

/**
 * @author Dhwani
 * NOTE:
 * FlightOperations.csv will be read from your Desktop
 * RDFFlightOps.rdf will be generated on your Desktop
 *
 */
//Jena packages
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.ontology.Individual; 
import org.apache.jena.ontology.OntClass; 
import org.apache.jena.ontology.OntModel; 
import org.apache.jena.ontology.OntModelSpec; 
import org.apache.jena.rdf.model.Property;

//Java packages
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import javax.swing.filechooser.FileSystemView;

public class FlightDataClass {
public static void main(String[] args) throws IOException,FileNotFoundException {
    	   
        //define the Flight Namespace required for RDF
        String flightOpNs="http://www.LAX.org/FlightOperations#";
        
        //create Ontology Model for Flight
        OntModel flightOntModel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM); 
        flightOntModel.setNsPrefix("FL", flightOpNs);     
       
        //Getting Local desktop path
        FileSystemView view = FileSystemView.getFileSystemView();
        String DesktopPath = view.getHomeDirectory().toString();
       
        //Read input .csv file from the Desktop
        String readerFileName = DesktopPath+"/FlightOperations.csv";
		BufferedReader br = null;
		String line = "";
		int lineCount=0;
		
		//skippedOnce is integer variable using which first row of FlightOperations.csv containing titles is skipped.
		int isSkippedOnce=0;
		
		try {

			br = new BufferedReader(new FileReader(readerFileName));	
			
			while((line=br.readLine()) != null){
				lineCount++;
			   //Using comma as a delimiter
				String[] flightDetails = line.split(",");	
				if(isSkippedOnce==0){
					isSkippedOnce=isSkippedOnce+1; 				
				}
				else{			
				OntClass monthlyData = flightOntModel.createClass(flightOpNs+"monthlyData");
				OntClass dataExtractDate = flightOntModel.createClass(flightOpNs+"dataExtractDate");
				OntClass reportPeriod = flightOntModel.createClass(flightOpNs+"reportPeriod");
				OntClass opsCount = flightOntModel.createClass(flightOpNs+"opsCount");
				
				OntClass flightData = flightOntModel.createClass(flightOpNs +"flightData");
				OntClass flightType = flightOntModel.createClass(flightOpNs +"flightType");
				OntClass flightClassification = flightOntModel.createClass(flightOpNs +"flightClassification");
				OntClass flightStatus = flightOntModel.createClass(flightOpNs +"flightStatus");				
				
				Property contains = flightOntModel.createProperty( flightOpNs + "contains");
				Property ofType = flightOntModel.createProperty(flightOpNs + "ofType");
				Property extractedOn = flightOntModel.createProperty( flightOpNs + "extractedOn");
				Property reportedOn = flightOntModel.createProperty( flightOpNs + "reportedOn");
							
				monthlyData.addProperty(contains, opsCount);
				monthlyData.addProperty(extractedOn, dataExtractDate);
				monthlyData.addProperty(reportedOn, reportPeriod);
								
				flightData.addProperty(contains, monthlyData);
				flightData.addProperty(ofType, flightType);
				flightData.addProperty(ofType, flightClassification);
				flightData.addProperty(ofType, flightStatus);
			
				Individual Iflight = flightData.createIndividual(flightOpNs+"flight"+lineCount);
				Individual IMonthData = monthlyData.createIndividual(flightOpNs+"monthlyData"+lineCount);
				
				Iflight.addProperty(contains, IMonthData);
				IMonthData.addProperty(extractedOn, flightDetails[0]);
				IMonthData.addProperty(reportedOn, flightDetails[1]);
				
				Iflight.addProperty(ofType, flightDetails[2]);
				Iflight.addProperty(ofType, flightDetails[3]);
				Iflight.addProperty(ofType, flightDetails[4]);
				IMonthData.addProperty(contains, flightDetails[5]);		
			}
		}	    

		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		
        //Generate the file on the dektop
        String writerFileName = DesktopPath+"/FlightOperations.rdf";
       FileWriter file = new FileWriter(writerFileName );
        try {
        		flightOntModel.write( file, "RDF/XML-ABBREV" );
        }
        finally {
           try {
               file.close();
               System.out.println("Congratulations!!");
                System.out.println("Please open FlightOperations.rdf from your desktop.");
           }
           catch (IOException closeException) {
       
           }
        }
    }
}


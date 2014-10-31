import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class Application {
	
	String variableName = "user_near_sending" ;
	int maxValue = 200; 
	double time = 30 ; 
	String modelName = "model.sm" ;
	int numberOfRuns=1000;	
	double snapshot = 15;
	
	
	HashMap<Integer, Integer> bins;
	HashMap<Integer, Double> probBins;
	

	public static void main(String[] args) throws IOException {
		
		
		Application application = new Application(); 
		
		application.run();
	//	application.test();
	//	application.processPath();
		
	}
	
	public Application(){
		
		bins = new HashMap<Integer, Integer>();
		probBins = new HashMap<Integer, Double>();
		
		for(int i=0 ; i <= maxValue; i++){
			bins.put(i, 0);
		}
		
	}
	
	
	public void run() throws IOException{
	
		
		for (int i=1; i<=numberOfRuns; i++){
			
			// this will generate one path in the file called output_path.csv
			generateOnePath();
			
			// process the generated path and get the value out of the last line
			int value = processPath();
			updateBins(value);
			
			System.out.printf("trajectory %d of %d - value=%d\n\n", i , numberOfRuns,value);

			
		}
		
		System.out.println(bins.toString());
		
		createProbabilityDist();	
		
		System.out.println(probBins.toString());
		
		writeFile();
		
		System.out.println("Output distribution produced.");
		
	}
	
	public void writeFile(){
		String output;
		 try {
		        BufferedWriter out = new BufferedWriter(new FileWriter("/home/alireza/Dropbox/Academics/Thesis/TwoTierNetworkCaseStudy/Model/TwoFemtoCells/prob_dist_"+variableName+".csv"));
		        out.write("value,prob\n");
		        for (int i = 0; i <= maxValue; i++) {
		        	output = String.format("%d,%f\n",i,probBins.get(i));
		            out.write(output);
		        }
		        out.close();
		 } catch (IOException e) {
			 System.out.printf("Could not write the output file.\n");
			 e.printStackTrace();			 
		 }
	}
	
	public void createProbabilityDist(){
		
		double prob;
		// the frequency distribution is now in bins.
		for (int i=0;i<=maxValue; i++){
			
			int freq = bins.get(i); 
			prob = ( ((double)freq) / numberOfRuns ); 
			probBins.put(i, prob);
					
		}
				
	}

	public void updateBins(int value){
		
		// put this in the bin
		int previousFreq = bins.get(value); 
		int newFreq = previousFreq+1;
		bins.put(value, newFreq);
		
	}
	
	// find the value in the last line of the file and return it. 
	public int processPath(){
			
		Scanner in = null ;
		try {
			in = new Scanner(new FileReader("/home/alireza/Dropbox/Academics/Thesis/TwoTierNetworkCaseStudy/Model/TwoFemtoCells/output_path.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not find the path file. Terminating.");
			e.printStackTrace();
		}
		
		String line= "";
		while(in.hasNext()){
			line = in.nextLine(); 
		}
		
		String[] tokens = line.split(",");
		
		String currentStep = tokens[0];
		String currentTime = tokens[1];
		String currentValue = tokens[2];
		
		
				
		int numericalValue = Integer.parseInt(currentValue);		
		
		return numericalValue;
		
	}

	
	public String[] buildCommand(String modelName, double time, String variableName, double snapshot){
									
		String[] command = {"/home/alireza/PRISM/prism-4.2.beta1-linux64/bin/prism", modelName , "-simpath", "time="+time+",snapshot="+snapshot+",vars=("+variableName+"),sep=comma", "output_path.csv", "-simpathlen", "100000000"};						
		return command;
	}
	
	public void generateOnePath() throws IOException{
		
		
		String[] command = this.buildCommand(modelName, time, variableName, snapshot);
		//System.out.printf("command = %s \n\n" , Arrays.toString(command));
		
		ProcessBuilder probuilder = new ProcessBuilder( command );

        //You can set up your work directory
        probuilder.directory(new File("/home/alireza/Dropbox/Academics/Thesis/TwoTierNetworkCaseStudy/Model/TwoFemtoCells"));
        
        Process process = probuilder.start();
        
      //Read out dir output
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        //System.out.printf("Output of running %s is:\n", Arrays.toString(command));
       
        
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        
		
	}
	
		
	public void test() throws IOException{
			
	}
	
}

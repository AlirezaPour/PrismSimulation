import java.io.IOException;


public class Application {
	
	Integer value; 
	String variableName ;
	double time ; 
	
	String modelName;
	double snapshot;

	public static void main(String[] args) {
		
		Application application = new Application(); 
		
	//	application.run();
		application.test();
		
	}
	
	
	public void run(){
		
		
		value = processAPath(modelName,time,variableName,snapshot);
		
		
		
	}
	
	public int processAPath(String modelName, double time, String variableName, Double snapshot){

		String command = buildCommand(modelName,time,variableName, snapshot);
		
		return 0;
	}

	
	public String buildCommand(String modelName, double time, String variableName, double snapshot){
		
		String command = "/home/alireza/prism-4.2/bin/prism" + " " + modelName + " " + "-simpath" + " " + "'" + 
							"time=" + String.format("%f",time) +","+ "snapshot="+ String.format("%f",snapshot) + 
							"," + "vars=(" + variableName + ")" + "," + "sep=comma" + "'" + " " +"output_path.csv" + " " +"-simpathlen 100000000"   ; 
		 		
		return command;
	}
	
	public String buildProgram(){
		
		String program = "~/prism-4.2/bin/prism" ;
		return program;
		
	}
	
	public String buildArgument(String modelName, double time, String variableName, double snapshot){
		
		String argument = modelName + " " + "-simpath" + " " + "'" + 
				"time=" + String.format("%f",time) +","+ "snapshot="+ String.format("%f",snapshot) + 
				"," + "vars=(" + variableName + ")" + "," + "sep=comma" + "'" + " " +"output_path.csv" + " " +"-simpathlen 100000000"   ;
		
		return argument;
		
	}
	
	public void test(){
		
		this.variableName = "user_near_sending" ;
		this.time = 5.2 ; 
		this.modelName = "model.sm" ;
		this.snapshot = 0.1;
		
		String command = this.buildCommand(modelName, time, variableName, snapshot);
		System.out.println(command);
		
		/*
		 try {
			Process p = new ProcessBuilder(command).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
}

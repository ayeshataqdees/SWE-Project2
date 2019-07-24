import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Random;
import java.io.IOException;
import java.io.InputStreamReader;

public class Start {
	public final static Random RANDOM = new Random(1);
	public static void main(String [] args) throws IOException {
		String seed = new String();
		String jobs = new String();
		String algorithm = new String();
		

		while(algorithm.isEmpty()) {
			System.out.println("\nEnter the Algorithm you want to run:"); 
			
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
			algorithm = bufferReader.readLine();
		}
		
		while(jobs.isEmpty()) {
			System.out.println("\nEnter the number of jobs you want to run :");
	
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
			jobs = bufferReader.readLine();
		}
		
		while(seed.isEmpty()) {
			System.out.println("\nEnter the seed :");
	
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
			seed = bufferReader.readLine();
		}
		

		try {
			Workload[] arrWorkload = getRandomWorkload(Integer.parseInt(jobs), Integer.parseInt(seed));
			
			switch (algorithm.toLowerCase()) {
		      case "fcfs":
		    	  FCFS fcfs = new FCFS();
					fcfs.run(arrWorkload);
		        break;
		      case "sjn":
		    	  SJN sjn = new SJN();
					sjn.run(arrWorkload);
		        break;
		      case "rr":
		    	  RR rr = new RR();
					rr.run(arrWorkload);
		        break;
		      case "hpf":
		    	  HPF hpf = new HPF();
					hpf.run(arrWorkload);
		        break;
		      case "srt":
		    	  SRT srt = new SRT();
				 srt.run(arrWorkload);
		        break;
		      case "hpfp":
		    	  HPF_P hpfp = new HPF_P();
				  hpfp.run(arrWorkload);
		        break;
		    }
		}
		catch(Exception ex) {
			System.out.println("Invalid Entry");
			System.exit(0);
		}
	}

	private static Workload[] getRandomWorkload(int jobs, int seed) {
		Random rand1 = new Random();
		Random rand2 = new Random();
		Random rand3 = new Random();
		
	    rand1.setSeed(seed);
	    rand2.setSeed(seed);
	    rand3.setSeed(seed);
	   
		Workload[] arrWorkload = new Workload[jobs];
		
		for(int i = 0; i < jobs; i++)
		{
			Workload workload = new Workload();
			
			workload.arrivalTime = rand1.nextInt(150);
			workload.executionTime = rand2.nextInt(9) + 1;
			workload.priority = rand3.nextInt(4) + 1;
			workload.processId = i+1;
			
			
			workload.flag = 0;
			
			arrWorkload[i] = workload;
		}
		
		// sorting according to arrival times
		Arrays.sort(arrWorkload, (a,b) -> Integer.compare(a.arrivalTime, b.arrivalTime));
		
		return arrWorkload;
	}
}
	

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Random;
import java.io.IOException;
import java.io.InputStreamReader;

public class Start {
	public final static Random RANDOM = new Random(1);
	public static void main(String [] args) throws IOException {
		String jobs = new String();
		
		while(jobs.isEmpty()) {
			System.out.println("\nEnter the number of jobs you want to run :");
	
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
			jobs = bufferReader.readLine();
		}
		
		try {
			Workload[] arrWorkload = getRandomWorkload(Integer.parseInt(jobs));
			// algorithm.run(arrWorkload);
			
			FCFS fcfs = new FCFS();
			fcfs.run(arrWorkload);
			
			HPF hpf = new HPF();
			hpf.run(arrWorkload);
		
			SJN sjn = new SJN();
			sjn.run(arrWorkload);
			
			SRT srt = new SRT();
			srt.run(arrWorkload);

			HPF_P hpfp = new HPF_P();
			hpfp.run(arrWorkload);
		}
		catch(Exception ex) {
			System.out.println("Invalid Entry");
			System.exit(0);
		}
	}

	private static Workload[] getRandomWorkload(int n) {
		Random rand1 = new Random();
		Random rand2 = new Random();
		Random rand3 = new Random();
        
	    rand1.setSeed(150);
	    rand2.setSeed(10);
	    rand3.setSeed(5);
	   
	    System.out.println("Setting seed 150 to produce the previous sequence");
        
		Workload[] arrWorkload = new Workload[n];
		
		for(int i = 0; i < n; i++)
		{
			Workload workload = new Workload();
			
			workload.arrivalTime = rand1.nextInt(100);
			workload.executionTime = rand2.nextInt(10) + 1;
			workload.processId = i+1;
			workload.priority = rand3.nextInt(4) + 1;
			
			arrWorkload[i] = workload;
		}
		
		// sorting according to arrival times
		Arrays.sort(arrWorkload, (a,b) -> Integer.compare(a.arrivalTime, b.arrivalTime));
		
		return arrWorkload;
	}
}
	

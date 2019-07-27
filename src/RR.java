import java.util.*;
public class RR implements Algorithm {
	public void run(Workload[] arrWorkload) {
		float totalWaitingTime = 0,totalTurnAroundTime = 0, totalResponseTime = 0;
		float totalIdleTime = 0;
		float completedProcess = 0;
		int n = arrWorkload.length;
		int timeQuantam = 0;
		boolean isProcessExecuted = false;
		int lastFinishedProcessTime = 0; 
		int MAX_QUONTA = 150;
		int idleTime = 0;
		
		System.out.print("Round Robin Simulation");
		System.out.println("\npid  Priority IdleTime arrival waitTime  startTime Execution complete turnAroundTime ResponseTime");
		

		StatsPerPriority statsPerPriority[] = new StatsPerPriority[4];
		for(int i = 0; i < 4; i++)
		{
			StatsPerPriority workload = new StatsPerPriority();
			statsPerPriority[i] = workload;
		}
		
		
		//run until time quonta is less than max quonta for simulation or there is any ongoing process 
		//no new process will not start once quonta goes beyond max quoant
		while ((timeQuantam < MAX_QUONTA || isProcessExecuted == true) && completedProcess != n) {
			isProcessExecuted = false;
				
			for(Workload currentWorkload : arrWorkload) {
				// if process is in ready queue
				if(currentWorkload.arrivalTime <= timeQuantam &&
						currentWorkload.runTime < currentWorkload.executionTime) {
					
					// check we are not starting new process beyond quonta limit 
					if (currentWorkload.runTime == 0 && timeQuantam >= MAX_QUONTA) {
						continue;
					}
					
					// if this first time process getting executed 
					if (currentWorkload.runTime == 0) {
						currentWorkload.responseTime = timeQuantam - currentWorkload.arrivalTime;  
						currentWorkload.startTime = timeQuantam;
						currentWorkload.idleTime = idleTime;
						idleTime = 0;
					}
					
					// Given one time quonta
					currentWorkload.runTime++;
					timeQuantam++;
					
					//set that this iteration cpu got some process to execute
					isProcessExecuted = true;
					
					// checking if process complete
					if (currentWorkload.runTime == currentWorkload.executionTime) {
						// since not all process will complete, count the process which we 
						// are completing so that we can use this for avg calculation.
						completedProcess++;
						
						// Lets populate this process stats 
						lastFinishedProcessTime = timeQuantam; 
						currentWorkload.completionTime = timeQuantam;
						currentWorkload.turnAroundTime = currentWorkload.completionTime - currentWorkload.arrivalTime;          // turnaround time= completion time- arrival time
						currentWorkload.waitingTime = currentWorkload.turnAroundTime - currentWorkload.executionTime;          	// waiting time= turnaround time- burst time			
						
						// lets add it to cumulative stats 
						totalWaitingTime += currentWorkload.waitingTime;              											 // total waiting time
						totalTurnAroundTime += currentWorkload.turnAroundTime;
						totalResponseTime += currentWorkload.responseTime;
						totalIdleTime += currentWorkload.idleTime;
						StatsPerPriority currentPerPrStats = statsPerPriority[currentWorkload.priority - 1];
						
						currentPerPrStats.totalWaitingTime += currentWorkload.waitingTime ;
						currentPerPrStats.totalTurnAroundTime += currentWorkload.turnAroundTime;
						currentPerPrStats.totalResponseTime += currentWorkload.responseTime;				
						currentPerPrStats.totalProcess += 1;
						
						System.out.println(currentWorkload.processId + "  \t " + currentWorkload.priority + "\t" + currentWorkload.idleTime + "\t" + currentWorkload.arrivalTime + "\t" + currentWorkload.waitingTime + "  \t "  + currentWorkload.startTime + "  \t " + "\t" + currentWorkload.executionTime  + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime +"\t"+ "\t"+currentWorkload.responseTime) ;
						// System.out.println(currentWorkload.processId + "  \t " + currentWorkload.arrivalTime + "\t" + currentWorkload.executionTime + "\t" + currentWorkload.completionTime + "\t" + currentWorkload.turnAroundTime + "\t"  + currentWorkload.waitingTime + "\t" + currentWorkload.runTime);
					}
				}	
			}
			
			if (isProcessExecuted == false) {
				timeQuantam++;
				idleTime++;
			}	
		}
		
		System.out.println("\nTotal process executed to completion "+ completedProcess + " from " + n);
		System.out.println("\nAverage waiting time: "+ (totalWaitingTime/completedProcess));     // printing average waiting time.
		System.out.println("\nAverage turnaround time: "+(totalTurnAroundTime/completedProcess));// printing average turnaround time.
		System.out.println("\nNo of process completed: " + completedProcess);
		System.out.println("\nAverage response time: "+ (totalResponseTime/completedProcess));
		System.out.println("\nThroughput: " + ((float)completedProcess/(float)timeQuantam));
		System.out.println("\nTotal idle time: "+ totalIdleTime);
		
		System.out.println("\nPriority Avg_WT Avg_TAT Avg_RT Total_Process");
		int x = 1;
		for(StatsPerPriority currentStats : statsPerPriority) 
		{	if (currentStats.totalProcess != 0)
			{
			    System.out.println("   " + x + "  \t " + (currentStats.totalWaitingTime/currentStats.totalProcess) + "\t" + (currentStats.totalTurnAroundTime/currentStats.totalProcess) + "\t" + (currentStats.totalResponseTime/currentStats.totalProcess) + "\t" + (currentStats.totalProcess)) ;
		    }
		    x++;
		}

	}
}
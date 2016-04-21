package CompWaitTimePerAttr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainEntry_2 {
	public static void main(String[] args) throws IOException{
		MainEntry_2 self = new MainEntry_2();
		String readerpath = "D:\\EclipseProject\\attr_clocked_sequences_all.csv";
		BufferedReader reader = null;
		int attractionLength = 86;
		int timesliceLength = 96;
		int unitTime = 6;
		double[][] averageTime = new double[attractionLength][timesliceLength];
		String[][] outliersVid = new String[attractionLength][timesliceLength];
		double[][] stdTime = new double[attractionLength][timesliceLength];
		String[][] visitors = new String[attractionLength][timesliceLength];
		HashMap<String, String[]> visitorSeq = new HashMap<String, String[]>();
		HashMap<String, double[][]> WTPerVisitor = new HashMap<String, double[][]>();
		int totalTime = 0;
		try{
			String eachline;
			reader = new BufferedReader(new FileReader(readerpath));
			while((eachline = reader.readLine()) != null){
				String visitorID = eachline.split(";")[0].replace("\"", "");
				String sequence = eachline.split(";")[1].replace("\"", "");
				String[] attractions = sequence.split("-");
				visitorSeq.put(visitorID, attractions);
				totalTime = attractions.length;
				double[][] time = new double[attractionLength][timesliceLength];
				for(int i=0; i<attractions.length; i++){
					if(!attractions[i].equals("0")){
						int minute = i/6;
						int attId = Integer.valueOf(attractions[i]);
						time[attId][minute]++;
						averageTime[attId][minute]++;
						if(visitors[attId][minute] == null){
							visitors[attId][minute] = visitorID;
						}
						else if(!visitors[attId][minute].contains(visitorID)){
							visitors[attId][minute]+=(","+visitorID);
						}
					}
				}
				WTPerVisitor.put(visitorID, time);
			}
			reader.close();
		}catch(IOException e){
			System.out.println(e);
		}
		System.out.println("Finish 1\n");
		for(int t = 0; t<timesliceLength; t++){
			for(int i=0; i<attractionLength; i++){
				if(visitors[i][t]!=null){
					averageTime[i][t]/=visitors[i][t].split(",").length;
					Iterator visitorIt = WTPerVisitor.entrySet().iterator();
					while(visitorIt.hasNext()){
						Map.Entry pair = (Map.Entry)visitorIt.next();
						String visitorId = (String) pair.getKey();
						if(visitors[i][t]!=null && visitors[i][t].contains(visitorId)){
							double time = WTPerVisitor.get(visitorId)[i][t];
							stdTime[i][t] += Math.pow(averageTime[i][t] - time, 2);
						}
					}
					stdTime[i][t] = Math.sqrt(stdTime[i][t]/visitors[i][t].split(",").length);
				}
				else{
					stdTime[i][t] = 0;
				}
			}
		}
		System.out.println("Finish 2\n");
		String averagePath = "D:\\EclipseProject\\averageresult_perAttraction.csv";
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(averagePath));
		String head = "attractionId,timeslice,visitorId,AverageTime,StdTime,VisitorTime";
		writer.write(head);
		writer.newLine();
		for(int t = 0; t<timesliceLength; t++){
			for(int i=0; i<attractionLength; i++){
				Iterator visitorIt = WTPerVisitor.entrySet().iterator();
				while(visitorIt.hasNext()){
					Map.Entry pair = (Map.Entry)visitorIt.next();
					String visitorId = (String) pair.getKey();
					if(visitors[i][t]!=null && visitors[i][t].contains(visitorId)){
						double time = WTPerVisitor.get(visitorId)[i][t];
						if(time<averageTime[i][t] - 2*stdTime[i][t] || time> averageTime[i][t] + 2*stdTime[i][t]){
							if(outliersVid[i][t] == null){
								outliersVid[i][t] = visitorId;
							}
							else{
								outliersVid[i][t]+=(","+visitorId);
							}
						}
						String content = String.valueOf(i) + "," + String.valueOf(t) + "," + visitorId + "," + averageTime[i][t] + "," + stdTime[i][t] + "," + time;
						writer.write(content);
						writer.newLine();
					}
				}
			}
		}
		writer.flush();
		writer.close();
		System.out.println("Finish 3\n");
		String writerpath = "D:\\EclipseProject\\timeresult_perAttraction.csv";
		try{
			head = "attractionId,timeslice, AverageTime,StdTime,AnomolousVisitorId";
			writer = new BufferedWriter(new FileWriter(writerpath));
			writer.write(head);
			writer.newLine();
			for(int t = 0; t<timesliceLength; t++){
				for(int i=0; i<attractionLength; i++){
					if(outliersVid[i][t]!=null)
						outliersVid[i][t] = outliersVid[i][t].replace(",", "|");
					String content = String.valueOf(i) + "," + String.valueOf(t) + "," + averageTime[i][t] + "," + stdTime[i][t] + "," + outliersVid[i][t];
					writer.write(content);
					writer.newLine();
				}
			}
			writer.flush();
			writer.close();
		} catch(IOException e){
			System.out.println(e);
		}
		System.out.println("Finish 4\n");
//		for(int t = 0; t<totalTime; t++){
//			Iterator visitorIt = visitorSeq.entrySet().iterator();
//			while(visitorIt.hasNext()){
//				Map.Entry pair = (Map.Entry)visitorIt.next();
//				String[] seq = (String[]) pair.getValue();
//				String visitorId = (String) pair.getKey();
//				int minute = t/unitTime;
//				if(!seq[t].equals("0")){
//					int attId = Integer.valueOf(seq[t]);
//					averageTime[attId][minute]++;
//					if(!visitors[attId][minute].contains(visitorId)){
//						if(visitors[attId][minute] == null){
//							visitors[attId][minute] = visitorId;
//						}
//						else{
//							visitors[attId][minute]+=(","+visitorId);
//						}
//					}
//					
//					
//				}
//				
//			}
//		}
	}
}

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
	public static void main(String[] args){
		MainEntry_2 self = new MainEntry_2();
		String readerpath = "E:\\EclipseProjects\\attr_clocked_sequences_actual.csv";
		BufferedReader reader = null;
		int attractionLength = 86;
		int timesliceLength = 96;
		int unitTime = 6;
		double[][] averageTime = new double[attractionLength][timesliceLength];
		double[][] outliersVid = new double[attractionLength][timesliceLength];
		double[][] stdTime = new double[attractionLength][attractionLength];
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
					}
				}
				WTPerVisitor.put(visitorID, time);
			}
			reader.close();
		}catch(IOException e){
			System.out.println(e);
		}
		Iterator visitorIt = WTPerVisitor.entrySet().iterator();
		while(visitorIt.hasNext()){
			Map.Entry pair = (Map.Entry)visitorIt.next();
		}
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

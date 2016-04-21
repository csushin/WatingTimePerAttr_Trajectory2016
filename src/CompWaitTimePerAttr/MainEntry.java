//package CompWaitTimePerAttr;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.util.HashMap;
//import java.util.HashSet;
//
//public class MainEntry {
//	public static void main(String[] args){
//		MainEntry self = new MainEntry();
//		String readerpath = "E:\\EclipseProjects\\attr_clocked_sequences_actual.csv";
//		String writerpath = "E:\\EclipseProjects\\timeresult_actual.csv";
//		BufferedReader reader = null;
//		BufferedWriter writer = null;
//		double unitTimeAvg=0, timeAvg=0, attractionAvg = 0, lineCount=0, repeatedAttrAvg = 0;
//		try{
//			String eachline;
//			reader = new BufferedReader(new FileReader(readerpath));
//			writer = new BufferedWriter(new FileWriter(writerpath));
//			String head = "visitorID,UnitTime,TotalTime,TotalAttractions,RepeatedTimes";
//			writer.write(head);
//			writer.newLine();
//			NumberFormat formatter = new DecimalFormat("#0.00");
//			while((eachline = reader.readLine()) != null){
//				lineCount++;
//				String visitorID = eachline.split(";")[0].replace("\"", "");
//				if(visitorID=="1983765"){
//					System.out.println(eachline);
//				}
//				String sequence = eachline.split(";")[1].replace("\"", "");
//				String[] attractions = sequence.split("-");
//				int totalTimeCount = self.queryTime(attractions);
//				timeAvg+=totalTimeCount;
//				int totalAttractionCount = self.queryAttractions(attractions);
//				attractionAvg+=totalAttractionCount;
//				double MinPerAttr = (double) totalTimeCount / (double)totalAttractionCount;
//				unitTimeAvg+=MinPerAttr;
//				int repeatedAttractionCount = self.queryRepeatedAttractions(attractions);
//				repeatedAttrAvg+=repeatedAttractionCount;
//				String toWrite = visitorID + "," + formatter.format(MinPerAttr) + "," + totalTimeCount + "," + totalAttractionCount+","+repeatedAttractionCount/(double)totalAttractionCount;
//				writer.write(toWrite);
//				writer.newLine();
//			}
//			timeAvg/=lineCount;
//			attractionAvg/=lineCount;
//			unitTimeAvg/=lineCount;
//			repeatedAttrAvg/=lineCount;
//			String toWrite = "StatAvg,"+unitTimeAvg+","+timeAvg+","+attractionAvg+","+repeatedAttrAvg;
//			writer.write(toWrite);
//			writer.newLine();
//			
//			writer.flush();
//			writer.close();
//			reader.close();
//		}catch(IOException e){
//			System.out.println(e);
//		}
//		BufferedReader computeStat = null;
//		try{
//			String eachline;
//			computeStat = new BufferedReader(new FileReader(writerpath));
//			double unitTimeStd=0, timeStd = 0, attrStd = 0, repeatedStd = 0;
//			while((eachline = computeStat.readLine()) != null){
//				if(!eachline.contains("visitor") && !eachline.contains("Stat")){
//					double singleUnitTime = Double.valueOf(eachline.split(",")[1]);
//					unitTimeStd+=(Math.pow(singleUnitTime - unitTimeAvg, 2.0));
//					double singleTime = Double.valueOf(eachline.split(",")[2]);
//					timeStd+=(Math.pow(singleTime-timeAvg, 2.0));
//					double singleAttr = Double.valueOf(eachline.split(",")[3]);
//					attrStd+=(Math.pow(singleAttr-attractionAvg, 2.0));
//					double singleRepeated = Double.valueOf(eachline.split(",")[4]);
//					repeatedStd+=(Math.pow(singleRepeated-repeatedAttrAvg, 2.0));
//				}
//			}
//			writer = new BufferedWriter(new FileWriter(writerpath, true));
//			unitTimeStd = Math.sqrt(unitTimeStd/lineCount);
//			timeStd = Math.sqrt(timeStd/lineCount);
//			attrStd = Math.sqrt(attrStd/lineCount);
//			repeatedStd = Math.sqrt(repeatedStd/lineCount);
//			String toWrite = "StatStd,"+unitTimeStd+","+timeStd+","+attrStd+","+repeatedStd;
//			writer.write(toWrite);
//			writer.newLine();
//			writer.flush();
//			writer.close();
//		}catch(IOException e){
//			
//		}
//		System.out.println("Computation Finished!");
//	}
//	
//	public int queryTime(String[] data){
//		int result = 0;
//		for(int i=0; i<data.length; i++){
//			if(!data[i].equals("0")) {
//				result++;
//			}
//		}
//		return result;
//	}
//	
//	public int queryAttractions(String[] data){
//		HashSet<Integer> uniqueAttr = new HashSet<Integer>();
//		for(int i=0; i<data.length; i++){
//			if(!uniqueAttr.contains(Integer.valueOf(data[i])) && !data[i].equals("0")){
//				uniqueAttr.add(Integer.valueOf(data[i]));
//			}
//		}
//		return uniqueAttr.size();
//	}
//	
//	public int queryRepeatedAttractions(String[] data){
//		HashSet<String> uniqueAttr = new HashSet<String>();
//		int total = 0;
//		for(int i=0; i<data.length; i++){
//			if(!uniqueAttr.contains(data[i]) && !data[i].equals("0")){
//				uniqueAttr.add(data[i]);
//				continue;
//			}
//			else if(uniqueAttr.contains(data[i]) && !data[i].equals("0")){
//				total+=1;
//			}
//		}
//		
//		return total;
//	}
//}

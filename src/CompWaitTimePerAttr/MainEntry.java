package CompWaitTimePerAttr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;

public class MainEntry {
	public static void main(String[] args){
		MainEntry self = new MainEntry();
		String readerpath = "D:\\EclipseProject\\attr_clocked_sequences_all.csv";
		String writerpath = "D:\\EclipseProject\\timeresult.csv";
		BufferedReader reader = null;
		BufferedWriter writer = null;
		double unitTimeAvg=0, timeAvg=0, attractionAvg = 0, lineCount=0;
		try{
			String eachline;
			reader = new BufferedReader(new FileReader(readerpath));
			writer = new BufferedWriter(new FileWriter(writerpath));
			String head = "visitorID,UnitTime,TotalTime,TotalAttractions";
			writer.write(head);
			writer.newLine();
			NumberFormat formatter = new DecimalFormat("#0.00");
			while((eachline = reader.readLine()) != null){
				lineCount++;
				String visitorID = eachline.split(";")[0].replace("\"", "");
				if(visitorID=="1983765"){
					System.out.println(eachline);
				}
				String sequence = eachline.split(";")[1].replace("\"", "");
				String[] attractions = sequence.split("-");
				int totalTimeCount = self.queryTime(attractions);
				timeAvg+=totalTimeCount;
				int totalAttractionCount = self.queryAttractions(attractions);
				attractionAvg+=totalAttractionCount;
				double MinPerAttr = (double) totalTimeCount / (double)totalAttractionCount;
				unitTimeAvg+=MinPerAttr;
				String toWrite = visitorID + "," + formatter.format(MinPerAttr) + "," + totalTimeCount + "," + totalAttractionCount;
				writer.write(toWrite);
				writer.newLine();
			}
			timeAvg/=lineCount;
			attractionAvg/=lineCount;
			unitTimeAvg/=lineCount;
			String toWrite = "StatAvg,"+unitTimeAvg+","+timeAvg+","+attractionAvg;
			writer.write(toWrite);
			writer.newLine();
			System.out.println("Computation Finished!");
			writer.flush();
			writer.close();
			reader.close();
		}catch(IOException e){
			System.out.println(e);
		}
		BufferedReader computeStat = null;
		try{
			String eachline;
			computeStat = new BufferedReader(new FileReader(writerpath));
			double unitTimeStd=0, timeStd = 0, attrStd = 0;
			while((eachline = computeStat.readLine()) != null){
				if(!eachline.contains("visitor") && !eachline.contains("Stat")){
					double singleUnitTime = Double.valueOf(eachline.split(",")[1]);
					unitTimeStd+=(Math.pow(singleUnitTime - unitTimeAvg, 2.0));
					double singleTime = Double.valueOf(eachline.split(",")[2]);
					timeStd+=(Math.pow(singleTime-timeAvg, 2.0));
					double singleAttr = Double.valueOf(eachline.split(",")[3]);
					attrStd+=(Math.pow(singleAttr-attractionAvg, 2.0));
				}
			}
			writer = new BufferedWriter(new FileWriter(writerpath, true));
			unitTimeStd = Math.sqrt(unitTimeStd)/lineCount;
			timeStd = Math.sqrt(timeStd/lineCount);
			attrStd = Math.sqrt(attrStd/lineCount);
			
			String toWrite = "StatStd,"+unitTimeStd+","+timeStd+","+attrStd;
			writer.write(toWrite);
			writer.newLine();
			writer.flush();
			writer.close();
		}catch(IOException e){
			
		}
		
	}
	
	public int queryTime(String[] data){
		int result = 0;
		for(int i=0; i<data.length; i++){
			if(!data[i].contains("0")) {
				result++;
			}
		}
		return result;
	}
	
	public int queryAttractions(String[] data){
		HashSet<Integer> uniqueAttr = new HashSet<Integer>();
		for(int i=0; i<data.length; i++){
			if(!uniqueAttr.contains(data[i])){
				uniqueAttr.add(Integer.valueOf(data[i]));
			}
		}
		return uniqueAttr.size();
	}
}

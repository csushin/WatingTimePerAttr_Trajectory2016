package CompWaitTimePerAttr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainEntry_2 {
	public static void main(String[] args){
		MainEntry self = new MainEntry();
		String readerpath = "E:\\EclipseProjects\\attr_clocked_sequences_actual.csv";
		String writerpath = "E:\\EclipseProjects\\timeresult_actual.csv";
		BufferedReader reader = null;
		BufferedWriter writer = null;
		double unitTimeAvg=0, timeAvg=0, attractionAvg = 0, lineCount=0, repeatedAttrAvg = 0;
		try{
			String eachline;
			reader = new BufferedReader(new FileReader(readerpath));
			writer = new BufferedWriter(new FileWriter(writerpath));
			String head = "visitorID,UnitTime,TotalTime,TotalAttractions,RepeatedTimes";
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
				int repeatedAttractionCount = self.queryRepeatedAttractions(attractions);
				repeatedAttrAvg+=repeatedAttractionCount;
				String toWrite = visitorID + "," + formatter.format(MinPerAttr) + "," + totalTimeCount + "," + totalAttractionCount+","+repeatedAttractionCount/(double)totalAttractionCount;
				writer.write(toWrite);
				writer.newLine();
			}
			timeAvg/=lineCount;
			attractionAvg/=lineCount;
			unitTimeAvg/=lineCount;
			repeatedAttrAvg/=lineCount;
			String toWrite = "StatAvg,"+unitTimeAvg+","+timeAvg+","+attractionAvg+","+repeatedAttrAvg;
			writer.write(toWrite);
			writer.newLine();
			
			writer.flush();
			writer.close();
			reader.close();
		}catch(IOException e){
			System.out.println(e);
		}
	}
}

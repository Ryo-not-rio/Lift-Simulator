package lift.main;

import java.io.FileWriter;
import java.io.IOException;

public class ExcelWriter {
    public static void write(Test test) {
        FileWriter csvWriter;
        try {
            csvWriter = new FileWriter("stats_w_rates.csv", true);
            
            csvWriter.append(Integer.toString(test.getSteps()));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(test.getFloorNum()));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(test.getPeopleRate()));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(test.getPeopleNum()));
            csvWriter.append(",");
            csvWriter.append(test.getBrain().getClass().getName());
            csvWriter.append(",");
            csvWriter.append(Integer.toString(test.getLiftNum()));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(test.getSteps()/test.getPeopleNum()));
            csvWriter.append("\n");
            
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
}

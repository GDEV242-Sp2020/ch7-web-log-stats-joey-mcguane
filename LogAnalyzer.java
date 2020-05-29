/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     * 7.12 is done by adding the "String fileName" in LogAnalyzer and
     * LogFileReader.
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }
    
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * For 7.14, allows for all the accesses for each hour 
     * to be added to a total, but only if the value of data is higher than
     * zero.
     */
    
    public int numberOfAccesses()
    {
        int total = 0;
        
        for (int aC = 0; aC < hourCounts.length; aC++){
            total += hourCounts[aC];
        }
        return total;
    }
    
    public int busiestHour()
    {
        int numberAccesses = 0;
        int busyHour = 0;
        int i = 0;
        
         while (i < hourCounts.length) {
             if (numberAccesses < hourCounts[i]){
             busyHour = i;
             numberAccesses = hourCounts[i];
             i ++;
            }
            else {
                i++;
            }
        }
        return busyHour;
    }
    
    public int quiestestHour()
    {
        int numAccessQuiet = numberOfAccesses();
        int quietHour = 0;
        int i = 0;
        
        while (i < hourCounts.length - 1) {
            if (numAccessQuiet > hourCounts[i]){
                quietHour = i;
                numAccessQuiet = hourCounts[i];
                i++;
            }
            else{
                i++;
            }
        }
        return quietHour;
    }
    
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}

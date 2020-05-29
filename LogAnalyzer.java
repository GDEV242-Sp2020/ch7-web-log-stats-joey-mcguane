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
    private int[] dayCounts;
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
        dayCounts = new int [28];
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
     * Used to analyze day counts.
     */
    
    public void analyzeDailyData()
    {
        while(reader.hasNext()){
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
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
    
    /**
     * Busiest hour code, registers the most counts on an hour.
     */
    
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
    
    /**
     * Busiest 2 hour code, registers which two-hour period has the most
     * checks. Then when it is done, it counts the hour after that period.
     */
        
    public int busiest2Hour()
    {
        int numAccess2Busy = 0;
        int busy2Hour = 0;
        int i = 0;
        
        while (i < hourCounts.length - 1){
            if (numAccess2Busy< hourCounts[i] + hourCounts[i+1]){
                busy2Hour = i;
                numAccess2Busy = hourCounts[i] + hourCounts[i +1];
                i++;
            }
            else {
                i++;
            }
        }
        return busy2Hour;
    }
    
    /**
     * Quietest hour code, analyzes all hours present, checks which one is the
     * lowest count, then puts it up.
     */
    
    public int quietestHour()
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
     * Busiest day code, analyzes the number of times a day comes up, then
     * picks out the day with the highest about of counts.
     */
    
    public int busiestDay()
    {
        analyzeDailyData();
        
        int busyDay = 0;
        int numAccessBusyDay = 0;
        
        for(int i = 0; i < dayCounts.length; i++) {
            if(dayCounts[i] > numAccessBusyDay){
                numAccessBusyDay = dayCounts[i];
                busyDay = i;
            }
        }
        return busyDay;
    }
    
    /**
     * Unable to complete quietest day, couldn't figure out the code.
     */
/*    
    public int quietestDay()
    {
        analyzeDailyData();
        
        int quietDay = 1;
        int numAccessQuietDay = numberOfAccesses();
        int i = 0;
        
        while ( i < dayCounts.length - 1) {
            if(dayCounts[i] < numAccessQuietDay){
                quietDay = i;
                dayCounts[i] = numAccessQuietDay;
                i++;
            }
            else {
                i++;
            }
        }
        return quietDay;
    }
    */
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
     * Prints the daily counts from analyzeDailyData
     */
    
    public void printDailyCounts()
    {
        System.out.println("Day: Count");
        for(int day = 1; day < dayCounts.length; day++) {
            System.out.println(day + ": " + dayCounts[day]);
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

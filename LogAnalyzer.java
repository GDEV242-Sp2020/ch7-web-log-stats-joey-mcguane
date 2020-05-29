/**
 * Read web server data and analyse hourly, daily, and monthly access patterns.
 * Also able to use data to determine the busiest and quietest hours, days,
 * or months.
 * 
 * @author Joey McGuane
 * @version    2020.05.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private int[] dayCounts;
    private int[] monthCounts;
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
        dayCounts = new int [29];
        monthCounts = new int [13];
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
     * Used to analyze month counts.
     */
    
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()){
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month]++;
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
     * Quietest day code, analyzes the least number of times a day comes up,
     * it picks out that day. That is, if a day comes up, it has to come
     * up once officially.
     */
    
    public int quietestDay()
    {
        analyzeDailyData();
        
        int quietDay = 0;
        int numAccessQuietDay = numberOfAccesses();
        int i = 0;
        
        while ( i < dayCounts.length ) {
            if(dayCounts[i] > numAccessQuietDay){
                dayCounts[i] = numAccessQuietDay;
                quietDay = i;
                i++;
            }
            else {
                i++;
            }
        }
        return quietDay;
    }
   
    /**
     * Busiest month code, analyzes the number of times a month comes up, then
     * picks out the month with the highest about of counts.
     */
    
    public int busiestMonth()
    {
        analyzeMonthlyData();
        
        int busyMonth = 0;
        int numAccessBusyMonth = 0;
        
        for (int i = 0; i < monthCounts.length; i++){
            if (monthCounts[i] > numAccessBusyMonth){
                numAccessBusyMonth = monthCounts[i];
                busyMonth = i;
            }
        }
        return busyMonth;
    }
    
    /**
     * Quietest month code, analyzes the least number of times a month comes up,
     * it picks out that month. That is, if a month comes up, it has to come
     * up once officially.
     */
    
    public int quietestMonth()
    {
        analyzeMonthlyData();
        
        int quietMonth = 0;
        int numAccessQuietMonth = numberOfAccesses();
        int i = 0;
        
        while ( i < monthCounts.length ) {
            if(monthCounts[i] > numAccessQuietMonth){
                dayCounts[i] = numAccessQuietMonth;
                quietMonth = i;
                i++;
            }
            else {
                i++;
            }
        }
        return quietMonth;
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
     * Prints monthly counts from analyzeMonthlyData
     */
    
    public void printMontlyCounts()
    {
        System.out.println("Month: Count");
        for(int month = 1; month < monthCounts.length; month++){
            System.out.println(month + ": " + monthCounts[month]);
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

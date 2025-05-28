package com.currency.bb.components.bussiness.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.currency.bb.components.bussiness.dto.FrankfurterApiDTO;

@Service
public class FrankFurterDerivedService {
    
    private final FrankFurterService service;
    public FrankFurterDerivedService(
        FrankFurterService service
    ){
        this.service = service;
    }


    public FrankfurterApiDTO getHistoricalRatesWithBaseWithLimitedTarget(String base, String date, String target)  throws Exception{
        try {
            return this.service.getHistoricalRatesWithBaseWithLimitedTarget(base, date, target);
        } catch (Exception e) {
            // Only unexpected exceptions reach here
            throw new IllegalArgumentException("The base currency, date or target is not defined!", e);
        }
    }

    public ArrayList<FrankfurterApiDTO> timeLineOfSpecificCurrency( String base, String onsetDate, String endDate, String target ) throws Exception{
        try{
            if( !this.isEndDateValid(endDate) || !this.isEndDateValid(onsetDate) )
                throw new IllegalArgumentException("The end date-onsetDate are not before or equals to current date!");

            Calendar onsetDateCalendar = Calendar.getInstance();
            Calendar endDateCalendar = Calendar.getInstance();
            onsetDateCalendar = this.castringStringToCalendar(onsetDate);
            endDateCalendar = this.castringStringToCalendar(endDate);
            ArrayList<String> calendarList = this.calendarList( onsetDateCalendar, endDateCalendar );

            ArrayList<FrankfurterApiDTO> rs = new ArrayList<>();
            /*
            because with payload of {"currency": "USD", "date": "1999-01-10", "target": "EUR"} 
            the return of the  public FrankfurterApiDTO getHistoricalRatesWithBaseWithLimitedTarget(String base, String date, String target)
            can be as below
            {
                "amount": "1.0",
                "base": "USD",
                "date": "1999-01-08",   #!!!
                "rates": {
                "EUR": "0.85771"
            }
            */
            HashSet<String> dateRepetitionControlSet = new HashSet<>();    

            for (String e : calendarList) {
                try {
                    FrankfurterApiDTO item = this.getHistoricalRatesWithBaseWithLimitedTarget(base, e, target);
                    // Skip if no rates returned
                    if (item != null && item.getRates() != null && !item.getRates().isEmpty()) {
                        if( !dateRepetitionControlSet.contains( item.getDate() ) ){
                            dateRepetitionControlSet.add( item.getDate() );
                            rs.add(item);
                        }
                    } else {
                        System.out.println("No rates available for " + e + ", skipping.");
                    }
                } catch (Exception ex) {
                    System.out.println("Skipping date " + e + " due to error: " + ex.getMessage());
                }
            }
            return rs;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public boolean isEndDateValid( String endDate ) throws Exception{
        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar = this.castringStringToCalendar(endDate);
        Calendar currentDateCalendar = Calendar.getInstance();
        currentDateCalendar = this.getCurrentDate();

        int status = endDateCalendar.compareTo(currentDateCalendar);
        return (status<=0);
    }

    public ArrayList<String> calendarList(Calendar onsetDate, Calendar endDate) {
        ArrayList<String> rs = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (onsetDate.compareTo(endDate) <= 0) {
            rs.add(sdf.format(onsetDate.getTime())); // Get "yyyy-MM-dd"
            onsetDate.add(Calendar.DATE, 1);
        }
        return rs;
    }

    // date is in format of "yyyy-MM-dd"
    public Calendar castringStringToCalendar( String endDate ) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // casting can create errors, so using try-catch block
        try{
            Date date = sdf.parse(endDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    // Returns current date with time (hour, min, sec, millis) set to zero
    public Calendar getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        // Zero out time fields
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

}


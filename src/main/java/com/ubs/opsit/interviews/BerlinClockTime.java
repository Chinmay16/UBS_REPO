package com.ubs.opsit.interviews;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;






import static com.ubs.opsit.interviews.TimeUtil.*;

public class BerlinClockTime implements TimeConverter{

	private static final String YELLOW_LAMP_ON 	= "Y";
	private static final String RED_LAMP_ON 	= "R";
	private static final String LAMP_OFF		= "O";
	
	private static final int YELLOW_LAMP_TIME 				= 2;
	private static final int TOP_RED_LAMP_HR_INTERVAL 		= 5;
	private static final int TOP_RED_LAMP_MIN_INTERVAL 		= 5;
	private static final int N_RED_LAMP_TOP_HR_ROW 			= 4;
	private static final int N_RED_LAMP_BOTTOM_HR_ROW 		= 4;
	private static final int N_LAMP_TOP_MIN_ROW 			=11;
	private static final int N_LAMP_BOTTOM_MIN_ROW 			=4;
	
	/**
	 * validates the provided time
	 * @param time
	 */
	private LocalTime  validateTime(final String time)
	{
		 if(time == null || time.isEmpty()) 
			 throw new IllegalArgumentException(NO_TIME_ERROR);
		 if (time.split(TIME_SEPERATOR).length != 3)
			 throw new IllegalArgumentException(INVALID_TIME_FORMAT_EXCEPTION);
		 try{
			 return LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME.withResolverStyle( ResolverStyle.SMART ));
		 }
		 catch(Exception ex)
		 {
			 throw new IllegalArgumentException(INVALID_TIME_FORMAT_EXCEPTION);
		 }
	}
	
	/**
	 * 
	 */
	@Override
	public String convertTime(final String aTime) {
		final LocalTime time = validateTime(aTime);
		
    	return String.join(TimeUtil.NEW_LINE, 
    				Arrays.asList(
    					getYellowLampStatus(time.getSecond()),
    					getTopRedLampStatus(time.getHour()),
    					getBottomRedLampStatus(time.getHour()),
    					geTopLampStatusMinutes(time.getMinute()),
    					getBottomLampStatusMinutes(time.getMinute())));
	}
	
	/**
	 * 
	 * @param sec
	 * @return
	 */
	protected String getYellowLampStatus(final int sec) {
        if (sec % YELLOW_LAMP_TIME == 0) 
        	return YELLOW_LAMP_ON;
        else return LAMP_OFF;
    }

	/**
	 * 
	 * @param hr
	 * @return
	 */
	protected String getTopRedLampStatus(final int hr) {
        return getLampStatus(N_RED_LAMP_TOP_HR_ROW, getTopLampStatus(hr, TOP_RED_LAMP_HR_INTERVAL));
    }
    
    
	/**
	 * 
	 * @param hr
	 * @return
	 */
	protected String getBottomRedLampStatus(final int hr) {
        return getLampStatus(N_RED_LAMP_BOTTOM_HR_ROW, hr % TOP_RED_LAMP_HR_INTERVAL);
    }

    /**
     * 
     * @param min
     * @return
     */
	protected String geTopLampStatusMinutes(final int min) {
        return getLampStatus(N_LAMP_TOP_MIN_ROW, getTopLampStatus(min, TOP_RED_LAMP_MIN_INTERVAL), "Y").replaceAll("YYY", "YYR");
    }

    /**
     * 
     * @param min
     * @return
     */
	protected String getBottomLampStatusMinutes(final int min) {
        return getLampStatus(N_LAMP_BOTTOM_MIN_ROW, min % TOP_RED_LAMP_MIN_INTERVAL, YELLOW_LAMP_ON);
    }

    /**
     * 
     * @param nLamp
     * @param onSigns
     * @return
     */
    private String getLampStatus(final int nLamp, int onSigns) {
        return getLampStatus(nLamp, onSigns, RED_LAMP_ON);
    }
    
    /**
     * 
     * @param nLamp
     * @param nOnLamp
     * @param onLampColor
     * @return
     */
    private String getLampStatus(final int nLamp, final int nOnLamp, final String onLampColor) {
        String out = "";
        for (int i = 0; i < nOnLamp; i++) {
            out += onLampColor;
        }
        for (int i = 0; i < (nLamp - nOnLamp); i++) {
            out += LAMP_OFF;
        }
        return out;
    }

    /**
     * 
     * @param time
     * @param interval
     * @return
     */
    private int getTopLampStatus(final int time, final int interval) {
        return (time - (time % interval)) / interval;
    }

	
	

}

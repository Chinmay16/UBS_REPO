package com.ubs.opsit.interviews;

import static org.junit.Assert.*;

import org.junit.Test;

import com.berlin.BerlinClock;

public class BerlinClockTimeTest {

	BerlinClockTime berlinClockTime = new BerlinClockTime();
	 
   
	//Test exception for no input 
	@Test(expected = IllegalArgumentException.class )
	public void testNullInputString()
	{
		berlinClockTime.convertTime(null);
    }
	
	//Test exception for invalid input
	@Test(expected = IllegalArgumentException.class )
	public void testInvalidInputString()
	{
		berlinClockTime.convertTime("2:5:a");
    }
	
	// Yellow lamp should blink on/off every two seconds
    @Test
    public void testYellowLampBlinkEveryTwoSeconds() {
        assertEquals("Y", berlinClockTime.getYellowLampStatus(0));
        assertEquals("O", berlinClockTime.getYellowLampStatus(1));
        assertEquals("O", berlinClockTime.getYellowLampStatus(53));
    }
 
    // Top Red(hr) should have 4 lamps
    @Test
    public void testTopHoursLampsCnt() {
        assertEquals(4, berlinClockTime.getTopRedLampStatus(7).length());
    }
 
    // Top Red(hr)  should light a red lamp for every 5 hours
    @Test
    public void testTopRedLampStatusForEvery5Hours() {
        assertEquals("OOOO", berlinClockTime.getTopRedLampStatus(0));
        assertEquals("RROO", berlinClockTime.getTopRedLampStatus(14));
        assertEquals("RRRR", berlinClockTime.getTopRedLampStatus(22));
        assertEquals("RRRR", berlinClockTime.getTopRedLampStatus(24));
    }
 
    // Bottom red(hr) should have 4 lamps
    @Test
    public void testBottomRedLampCnt() {
        assertEquals(4, berlinClockTime.getBottomRedLampStatus(5).length());
    }
 
    // Bottom red(hr) should light a red lamp for every hour left from top hours
    @Test
    public void testBottomRedStatusForEveryHour() {
        assertEquals("OOOO", berlinClockTime.getBottomRedLampStatus(0));
        assertEquals("RRRO", berlinClockTime.getBottomRedLampStatus(13));
        assertEquals("RRRO", berlinClockTime.getBottomRedLampStatus(23));
        assertEquals("RRRR", berlinClockTime.getBottomRedLampStatus(24));
    }
 
    // Top minutes should have 11 lamps
    @Test
    public void testTopMinutesLampCnt() {
        assertEquals(11, berlinClockTime.geTopLampStatusMinutes(34).length());
    }
 
    // Top minutes should have 3rd, 6th and 9th lamps in red to indicate first quarter, half and last quarter
    @Test
    public void testTopMinutes369LampsStatus() {
        String minutes32 = berlinClockTime.geTopLampStatusMinutes(32);
        assertEquals("R", minutes32.substring(2, 3));
        assertEquals("R", minutes32.substring(5, 6));
        assertEquals("O", minutes32.substring(8, 9));
    }
 
    // Top minutes should light a yellow lamp for every 5 minutes unless it's first quarter, half or last quarter
    @Test
    public void testTopMinutesShouldLightYellowLampForEvery5MinutesUnlessItIsFirstQuarterHalfOrLastQuarter() {
        assertEquals("OOOOOOOOOOO", berlinClockTime.geTopLampStatusMinutes(0));
        assertEquals("YYROOOOOOOO", berlinClockTime.geTopLampStatusMinutes(17));
        assertEquals("YYRYYRYYRYY", berlinClockTime.geTopLampStatusMinutes(59));
    }
 
    // Bottom minutes should have 4 lamps
    @Test
    public void testBottomMinutesLampCnt() {
        assertEquals(4, berlinClockTime.getBottomLampStatusMinutes(0).length());
    }
 
    // Bottom minutes should light a yellow lamp for every minute left from top minutes
    @Test
    public void testBottomMinutesShouldLightYellowLampForEveryMinuteLeftFromTopMinutes() {
        assertEquals("OOOO", berlinClockTime.getBottomLampStatusMinutes(0));
        assertEquals("YYOO", berlinClockTime.getBottomLampStatusMinutes(17));
        assertEquals("YYYY", berlinClockTime.getBottomLampStatusMinutes(59));
    }
 
 
    // Berlin Clock it should "result in correct seconds, hours and minutes" in {
    @Test
    public void testBerlinClockShouldResultInCorrectSecondsHoursAndMinutes() {
        String berlinTime = berlinClockTime.convertTime("16:37:16");
        
        final String NEW_LINE = System.getProperty("line.separator");
        String expected =String.join(NEW_LINE, "Y", "RRRO", "ROOO", "YYRYYRYOOOO", "YYOO");
        assertEquals(expected, berlinTime);
        
        
        berlinTime = berlinClockTime.convertTime("13:17:01");
        expected =String.join(NEW_LINE, "O", "RROO", "RRRO", "YYROOOOOOOO", "YYOO");
        assertEquals(expected, berlinTime);
        
        berlinTime = berlinClockTime.convertTime("00:30:00");
        expected =String.join(NEW_LINE, "Y", "OOOO", "OOOO", "YYRYYROOOOO", "OOOO");
        assertEquals(expected, berlinTime);
        
    }
}

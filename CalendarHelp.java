
import java.text.SimpleDateFormat;
import java.time.Duration;
//import java.util.Arrays;
import java.util.Calendar; //0 is January and 1 - February and so forth
import javax.swing.*;

public class CalendarHelp
{
    public String[] datesArraySP = new String[5];
    public String[] datesArrayNoSP = new String[5];
    public int[] calArraySP = new int[5];
    public int[] calArrayNoSP = new int[5];
    private static Calendar prevCal = Calendar.getInstance();
    private static Calendar currCal = Calendar.getInstance();

    public int num10Days;

    public CalendarHelp()
    {
        prevCal = Calendar.getInstance();
        prevCal.set(Calendar.DATE,1);
        prevCal.set(Calendar.HOUR, 0);
        prevCal.set(Calendar.MINUTE, 0);
        prevCal.set(Calendar.SECOND, 0);
        prevCal.set(Calendar.MILLISECOND, 0);
        prevCal.set(Calendar.MONTH, 0);
        prevCal.set(Calendar.YEAR, 2023);  
        //System.out.println(prevCal.toInstant());

        currCal = Calendar.getInstance();
        //currCal.add(Calendar.DATE,44); //-- for testing
        currCal.set(Calendar.HOUR, 0);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        //System.out.println(currCal.toInstant());

        selectDatesInterpreter();
    }

    public void selectDatesInterpreter()
    {
        int days = (int) Duration.between(prevCal.toInstant(), currCal.toInstant()).toDays();
        //System.out.println("Days: " + days);
        if(days % 10 == 0)
        {
            //System.out.println("0");
            selectDates(datesArraySP, 0, currCal);
            selectDates(datesArrayNoSP, 2, currCal);
            currCal.add(Calendar.DATE, -9);
        }
        else
        {
            int num10DaysPassed = (days)/10;
            this.num10Days = num10DaysPassed;
            prevCal.add(Calendar.DATE, (10 * num10DaysPassed));
            //System.out.println(prevCal.toInstant());

            if( ((days+1)%10) == 0 ) 
            { 
                //System.out.println("tt"); 
                currCal.add(Calendar.DATE, -9);
                selectDates(datesArraySP, 0, currCal);
                selectDates(datesArrayNoSP, 2, currCal);
                //currCal.add(Calendar.DATE, -9);
            }
            else
            {
                selectDates(datesArraySP, 0, prevCal);
                selectDates(datesArrayNoSP, 1, prevCal);
            }

        }
    }
    public void selectDates(String[] sp, int var, Calendar calInsert)
    {       
        Calendar cal = calInsert; //will take the date of execution
        if(var == 0)
        {
            //cal.add(Calendar.DATE, -1); // Removes 1 Day
            String s = new SimpleDateFormat("MMM").format(cal.getTime());
            sp[0] = s + ". " + cal.get(Calendar.DAY_OF_MONTH);
            calArraySP[0] = cal.get(Calendar.DAY_OF_MONTH);
            for(int i = 1; i <= 4; i++)
            {
                cal.add(Calendar.DATE, 1); // Adds 1 day
                s = new SimpleDateFormat("MMM").format(cal.getTime());
                sp[i] = s + ". " + cal.get(Calendar.DAY_OF_MONTH);
                calArraySP[i] = cal.get(Calendar.DAY_OF_MONTH);
            }
        }
        else
        {
            cal.add(Calendar.DATE, 1); 
            String s = new SimpleDateFormat("MMM").format(cal.getTime());
            sp[0] = s + ". " + cal.get(Calendar.DAY_OF_MONTH);
            calArrayNoSP[0] = cal.get(Calendar.DAY_OF_MONTH);
            for(int i = 1; i <= 4; i++)
            {
                cal.add(Calendar.DATE, 1); // Adds 1 day
                s = new SimpleDateFormat("MMM").format(cal.getTime());
                sp[i] = s + ". " + cal.get(Calendar.DAY_OF_MONTH);
                calArrayNoSP[i] = cal.get(Calendar.DAY_OF_MONTH);
            }
        }
        //System.out.println(Arrays.toString(calArraySP));
        //System.out.println(Arrays.toString(calArrayNoSP));
    }

    public void unEditablePrevDates(JCheckBox cb1, JCheckBox cb2, JCheckBox cb3, JCheckBox cb4, JCheckBox cb5, int var, JRadioButton b1, JRadioButton b2)
    {
        //Idea: use the Calendar arrays AND the current date of program execution to make sure that the past JCheckBox dates are unclickable
        //TBC
        int todayDate = currCal.get(Calendar.DAY_OF_MONTH);
        //System.out.println("TD: " + todayDate);
        int counter = 0;
        if(var == 0)
        {
            for(int i = 0; i < calArraySP.length; i++)
            {
                if(todayDate == calArraySP[i])
                {
                    break;
                }
                else
                {
                    counter++;
                }
                
            }
        }
        if(var == 1)
        {
            //System.out.println("DSFSFSFSFSF");
            for(int i = 0; i < calArrayNoSP.length; i++)
            {
                if(todayDate <= calArrayNoSP[i])
                {
                    break;
                }
                else
                {
                    if(todayDate == currCal.getActualMaximum(Calendar.DATE) && (todayDate > calArrayNoSP[i]))
                    {
                        break;
                    }
                    else
                    {
                        //System.out.print(todayDate);
                        //System.out.println(calArrayNoSP[i]);
                        counter++;
                    }
                }
                    
            }
        }
        if(counter > 0)
        {
            cb1.setEnabled(false);
        }
        if(counter > 1)
        {
            cb2.setEnabled(false);
        }
        if(counter > 2)
        {
            cb3.setEnabled(false);
        }
        if(counter > 3)
        {
            cb4.setEnabled(false);
        }
        if(counter > 4)
        {
            cb5.setEnabled(false);
            b1.setEnabled(false);
            b2.setEnabled(false);
        }
        //System.out.println(counter);
    }

    public int getNum10DaysPassed()
    {
        return num10Days;
    }

    public boolean checkQuarterly()
    {
        Calendar quarterDate = Calendar.getInstance();
        /*
         * Dates are going to be 
         * November 1st --- Month: 10; Date: 1
         * January 1st --- Month: 0; Date: 1
         * March 1st --- Month: 2; Date: 1
         * June 1st --- Month: 5; Date: 1
         */

         int month = quarterDate.get(Calendar.MONTH);
         int day = quarterDate.get(Calendar.DATE);

         if(day == 1)
         {
            if(month == 10)
            {
                return true;
            }
            if(month == 0)
            {
                return true;
            }
            if(month == 2)
            {
                return true;
            }
            if(month == 5)
            {
                return true;
            }
         }
         return false;
    }

    public boolean checkQuarterlyAfter()
    {
        Calendar quarterDate = Calendar.getInstance();
        /*
         * Dates are going to be 
         * November 2nd --- Month: 10; Date: 2
         * January 2nd --- Month: 0; Date: 2
         * March 2nd --- Month: 2; Date: 2
         * June 2nd --- Month: 5; Date: 2
         */

         int month = quarterDate.get(Calendar.MONTH);
         int day = quarterDate.get(Calendar.DATE);

         if(day == 2)
         {
            if(month == 10)
            {
                return true;
            }
            if(month == 0)
            {
                return true;
            }
            if(month == 2)
            {
                return true;
            }
            if(month == 5)
            {
                return true;
            }
         }
         return false;
    }

    public boolean checkYearly()
    {
        //7
        Calendar yearDate = Calendar.getInstance();
        /*
         * Dates are going to be 
         * November 15th --- Month: 10; Date: 15
         * January 15th --- Month: 0; Date: 15
         * March 15th --- Month: 2; Date: 15
         * June 15 --- Month: 5; Date: 15
         */

         int month = yearDate.get(Calendar.MONTH);
         int day = yearDate.get(Calendar.DATE);

         if(month == 7 && day == 28)
         {
            return true;
         }
         return false;
    }
}
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;    

import java.util.regex.Pattern;

public class Swing extends JFrame implements ActionListener
{

    public JFrame frame;

    public static JTextField firstName, lastName;

    public static JSlider slider = new JSlider(14,18);

    public static JPanel gradePanel;
    public static JComboBox<String> comboBox; 
    public static String[] grades = {"Select Grade","Freshman", "Sophomore", "Junior", "Senior"};

    public CalendarHelp ch;
    
    public static JCheckBox date1, date2, date3, date4, date5; 
    public static JRadioButton attendanceSP, participationSP;
    public static JPanel spPanel;

    public static JPanel nospPanel;
    public static JCheckBox noDate1, noDate2, noDate3, noDate4, noDate5;
    public static JRadioButton attendanceNoSP, participationNoSP;

    public static JButton submitButton;

    private static int points;

    public JButton makeDBAdmins;

    public int num10Days; 

    public MySQLDB testfileobject = new MySQLDB();

    public JButton b1, b2;

    public String topPointFN = "";
    public String topPointLN = "";
    public int topPoints = 0;

    public JButton g9,g10,g11,g12;

    public JTable table;
    public JScrollPane pane;

    public JButton top3;
    
    public Swing()
    {
        frame = new JFrame();

        frame.setSize(700,500);
        frame.setTitle("Who are You?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);        

        JPanel panel = new JPanel();
        panel.setBounds(0,0,580,85);
        //panel.setLayout(null);
        //panel.setBackground(Color.red);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(new JLabel("Enter First Name: "));
        firstName = new JTextField(8);
        JLabel lastNameLabel = new JLabel("Enter Last Name: ");
        lastName = new JTextField(8);
        
        panel.add(firstName);
        panel.add(lastNameLabel);
        panel.add(lastName);

        panel.add(new JLabel("      Select Your Age:                       "));

        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panel.add(slider);

        gradePanel = new JPanel();
        gradePanel.setBounds(0,85,580,40);
        gradePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel gradeLabel = new JLabel("Select Your Grade:");
        comboBox = new JComboBox<String>(grades);
        //comboBox.setSelectedIndex(0);
        gradePanel.add(gradeLabel);
        gradePanel.add(comboBox);
        /* ----------------------------------------------- DATES CODING FOR BOTH TYPES OF EVENTS ----------------------------------------------- */
        ch = new CalendarHelp();
        /* ----------------------------------------------- SPORTING EVENTS SELECTION PANEL ----------------------------------------------- */
        spPanel = new JPanel();
        spPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        //p2.setBackground(Color.blue);
        spPanel.setBounds(0,125,580,65);

        JButton dateButtonSP = new JButton("Select Sporting Event Days: ");
        dateButtonSP.addActionListener(e -> labelClicked(dateButtonSP, 0, ch));
        //dateLabel.setForeground(Color.white);
        date1 = new JCheckBox(ch.datesArraySP[0]);
        date2 = new JCheckBox(ch.datesArraySP[1]);
        date3 = new JCheckBox(ch.datesArraySP[2]);
        date4 = new JCheckBox(ch.datesArraySP[3]);
        date5 = new JCheckBox(ch.datesArraySP[4]);
        
        spPanel.add(dateButtonSP);


        attendanceSP = new JRadioButton("Attending");
        participationSP = new JRadioButton("Participating");
        ButtonGroup spButtonGroup = new ButtonGroup();
        spButtonGroup.add(attendanceSP); spButtonGroup.add(participationSP);

        ch.unEditablePrevDates(date1, date2, date3, date4, date5, 0, attendanceSP, participationSP);
        spPanel.add(date1); spPanel.add(date2); spPanel.add(date3); spPanel.add(date4); spPanel.add(date5); //adding all sporting event dates
        spPanel.add(new JLabel("Select Participation Type: "));
        spPanel.add(attendanceSP); spPanel.add(participationSP); 

        /* ----------------------------------------------- NON-SPORTING EVENTS SELECTION PANEL ----------------------------------------------- */
        nospPanel = new JPanel();
        nospPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        nospPanel.setBounds(0,190,580,65);

        JButton noDateButton = new JButton("Select Non-Sporting Event Days:");
        noDateButton.addActionListener(e -> labelClicked(noDateButton, 1, ch));
        noDate1 = new JCheckBox(ch.datesArrayNoSP[0]);
        noDate2 = new JCheckBox(ch.datesArrayNoSP[1]);
        noDate3 = new JCheckBox(ch.datesArrayNoSP[2]);
        noDate4 = new JCheckBox(ch.datesArrayNoSP[3]);
        noDate5 = new JCheckBox(ch.datesArrayNoSP[4]);
        
        nospPanel.add(noDateButton);

        attendanceNoSP = new JRadioButton("Attending");
        participationNoSP = new JRadioButton("Participating");
        ButtonGroup nospButtonGroup = new ButtonGroup();
        nospButtonGroup.add(attendanceNoSP); nospButtonGroup.add(participationNoSP);

        ch.unEditablePrevDates(noDate1, noDate2, noDate3, noDate4, noDate5, 1, attendanceNoSP, participationNoSP);

        nospPanel.add(noDate1); nospPanel.add(noDate2); nospPanel.add(noDate3); nospPanel.add(noDate4); nospPanel.add(noDate5); //adding all sporting event dates
        nospPanel.add(new JLabel("Select Participation Type: "));
        nospPanel.add(attendanceNoSP); nospPanel.add(participationNoSP); 

        submitButton = new JButton("Submit");
        submitButton.setBounds(0, 275, 580, 40);
        submitButton.setBorder(BorderFactory.createLineBorder(Color.black));
        submitButton.addActionListener(e -> submitClick());

        b1 = new JButton("Display Winners");
        b1.setBounds(200, 350, 200, 30);
        b1.addActionListener(e -> b1click());
        b1.setVisible(false);
        frame.add(b1);

        b2 = new JButton("Display Top Student Info");
        b2.setBounds(400, 350, 200, 30);
        b2.addActionListener(e -> b2click(topPointFN,topPointLN,topPoints));
        b2.setVisible(false);
        frame.add(b2);

        g9 = new JButton("Grade 9 Info");
        g9.setBounds(0, 432, 145, 30);
        g9.addActionListener(e -> g9());
        g9.setVisible(false);

        g10 = new JButton("Grade 10 Info");
        g10.setBounds(180, 432, 145, 30);
        g10.addActionListener(e -> g10());
        g10.setVisible(false);

        g11 = new JButton("Grade 11 Info");
        g11.setBounds(360, 432, 145, 30);
        g11.addActionListener(e -> g11());
        g11.setVisible(false);

        g12 = new JButton("Grade 12 Info");
        g12.setBounds(540, 432, 145, 30);
        g12.addActionListener(e -> g12());
        g12.setVisible(false);

        frame.add(g9);
        frame.add(g10);
        frame.add(g11);
        frame.add(g12);

        top3 = new JButton("Display Top Prize Winners");
        top3.setBounds(300, 387, 200, 30);
        top3.addActionListener(e -> top3());
        top3.setVisible(false);
        frame.add(top3);

        makeDBAdmins = new JButton("Admins Only");
        makeDBAdmins.setBounds(0,350,120,30);
        makeDBAdmins.addActionListener( e -> makeDBAdminsClick() );
        makeDBAdmins.setVisible(true);
        frame.add(makeDBAdmins);

        frame.add(panel);
        frame.add(gradePanel);
        frame.add(spPanel);
        frame.add(nospPanel);
        frame.add(submitButton);
        frame.setVisible(true);
    }

    private void top3() 
    {
        MySQLDB.selectTop3();
    }

    public void g9()
    {
        MySQLDB.GradeInfo(9);
    }

    public void g10()
    {
        MySQLDB.GradeInfo(10);
    }

    public void g11()
    {
        MySQLDB.GradeInfo(11);
    }

    public void g12()
    {
        MySQLDB.GradeInfo(12);
    }

    private void labelClicked(JButton b, int i, CalendarHelp ch) {
        if(i == 0)
        {
            JOptionPane.showMessageDialog(b, 
            "" + ch.datesArraySP[0] + ": High Jump" +
            "\n" + ch.datesArraySP[1] + ": Pole Vault" +
            "\n" + ch.datesArraySP[2] + ": Relay Races" +
            "\n" + ch.datesArraySP[3] + ": Javelin Throwing" +
            "\n" + ch.datesArraySP[4] + ": Badminton",
        "Sporting Events", JOptionPane.PLAIN_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(b, 
            "" + ch.datesArrayNoSP[0] + ": Orchestra Concert" +
            "\n" + ch.datesArrayNoSP[1] + ":  Chess Tournament" +
            "\n" + ch.datesArrayNoSP[2] + ":  Band Concert" +
            "\n" + ch.datesArrayNoSP[3] + ":  Choir Concert" +
            "\n" + ch.datesArrayNoSP[4] + ":  Dance",
        "Non-Sporting Events", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public int checkGradeSelected()
    {
        String value = (String) comboBox.getSelectedItem();
        if(value.equals("Freshman"))
        {
            return 9;
        }
        else if(value.equals("Sophomore"))
        {
            return 10;
        }
        else if(value.equals("Junior"))
        {
            return 11;
        }
        else if(value.equals("Senior"))
        {
            return 12;
        }
        else
        {
            JOptionPane.showMessageDialog(gradePanel, "Please select which grade you are in", "Choose Grade", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    public int checkEvents(JCheckBox d1, JCheckBox d2, JCheckBox d3, JCheckBox d4, JCheckBox d5, JRadioButton rb1, JRadioButton rb2, JPanel p)
    {
        int points = 0;
        boolean val = false;
        if(d1.isSelected())
        {
            points +=1;
            val = (val || true);
        }
        if(d2.isSelected())
        {
            points +=1;
            val = (val || true);
        } 
        if(d3.isSelected())
        {
            points +=1;
            val = (val || true);
        }           
        if(d4.isSelected())
        {
            points +=1;
            val = (val || true);
        }
        if(d5.isSelected())
        {
            points +=1;
            val = (val || true);
        }       
        if(val)
        {
            if(rb1.isSelected() || rb2.isSelected())
            { 
                return points;
            }
            else
            {
                if(d1.isEnabled() == false && d2.isEnabled() == false && d3.isEnabled() == false && d4.isEnabled() == false && d5.isEnabled() == false && rb1.isEnabled() == false && rb2.isEnabled() == false)
                {
                    return 0;
                }
                else
                {
                    JOptionPane.showMessageDialog(p, "Please Select if you're Attending or Participating in this events ", "Select Role", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
            }
        }
        else
        {
            if(d1.isEnabled() == false && d2.isEnabled() == false && d3.isEnabled() == false && d4.isEnabled() == false && d5.isEnabled() == false && rb1.isEnabled() == false && rb2.isEnabled() == false)
            {
                    return 0;
            }
            else
            {
                JOptionPane.showMessageDialog(p, "Please Select Dates", "Select Dates", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        }
    }

    public boolean isNameValid(JTextField f, String msg)
    {
        String s = f.getText();
        String alphaCAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        boolean value = false;
        if (Pattern.matches("[a-zA-Z]+",s))
        {
            //value = true;
            for(int i = 0; i < s.length(); i++)
            {
                String letter = s.substring(i,i+1);
                for(int j = 0; j < alphaCAPS.length(); j++)
                {
                    if(i == 0)
                    {
                        if(s.substring(0,1).equals(alphaCAPS.substring(j,j+1)) == true)
                        { 
                            value = true;
                        } 
                    }
                    else if(value == true)
                    {
                        if(letter.equals(alphaCAPS.substring(j,j+1)) == true)
                        {
                            value = false;
                        }
                    }
                }
            }
            if(value == false)
            {
                JOptionPane.showMessageDialog(f, msg, "Entry Error", JOptionPane.ERROR_MESSAGE);
            }
            return value;
        }
        else
        {
            JOptionPane.showMessageDialog(f, msg, "Entry Error", JOptionPane.ERROR_MESSAGE);
            return false;    
        }
    }

    public void switchEnabled(JCheckBox d1, JCheckBox d2, JCheckBox d3, JCheckBox d4, JCheckBox d5, JRadioButton rb1, JRadioButton rb2)
    {
        d1.setEnabled(false);
        d2.setEnabled(false);
        d3.setEnabled(false);
        d4.setEnabled(false);
        d5.setEnabled(false);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
    }

    public void submitClick()
    {
        boolean firstStringValidReturned = isNameValid(firstName, "Type your FIRST name with the FIRST letter of your FIRST name being Capitalized. \nUse only letters(No other characters)\nExamples: John, Ava, Robert");
        boolean lastStringValidReturned = isNameValid(lastName, "Type your LAST name with the FIRST letter of your LAST name being Capitalized. \nUse only letters(No other characters)\nExamples: John, Ava, Robert");
        int gradeReturned = checkGradeSelected();
        int sportingEventsPoints = 0;
        int noSportingEventsPoints = 0;

        if(firstStringValidReturned != false && lastStringValidReturned != false)
        {
            if(gradeReturned != -1)
            {
    
                sportingEventsPoints = checkEvents(date1, date2, date3, date4, date5, attendanceSP, participationSP, spPanel);
                if(sportingEventsPoints != -1)
                {
                    noSportingEventsPoints = checkEvents(noDate1, noDate2, noDate3, noDate4, noDate5, attendanceNoSP, participationNoSP, nospPanel);
                    if(noSportingEventsPoints != -1)
                    {
                        firstName.setEditable(false);
                        lastName.setEditable(false);
                        comboBox.setEnabled(false);
                        submitButton.setEnabled(false);
                        switchEnabled(date1, date2, date3, date4, date5, attendanceSP, participationSP);
                        switchEnabled(noDate1, noDate2, noDate3, noDate4, noDate5, attendanceNoSP, participationNoSP);
                        slider.setEnabled(false);
                        points = sportingEventsPoints+noSportingEventsPoints;
                        
                        makeDBAdmins.setVisible(true);
                        
                        //System.out.println(testfileobject.getNum10DaysPassed("Jayan", "Sirikonda"));
                        if(ch.checkYearly() == true)
                        {
                            testfileobject.deleteTestTable();
                        }
                        testfileobject.checkRecordExists(firstName, lastName, gradeReturned, points, ch.getNum10DaysPassed());

                        if(ch.checkQuarterly() == true)
                        {
                            //System.out.println("0");
                            testfileobject.makeRandomTableAndAddRandomStudent();
                            b1.setVisible(true);
                            b2.setVisible(true);
                            top3.setVisible(true);

                            g9.setVisible(true);                         
                            g10.setVisible(true);                           
                            g11.setVisible(true);
                            g12.setVisible(true);
                        }
                        testfileobject.chooseTopPointStudent();
                        topPointFN = testfileobject.getTopPointFN();
                        topPointLN = testfileobject.getTopPointLN();
                        topPoints = testfileobject.getValue();
                        if(ch.checkQuarterlyAfter() == true)
                        {
                            testfileobject.deleteTestTable();
                            testfileobject.deleteTestTableRandom();
                        }
                        
                    }
                } 
            }
        }
    }

    public void b1click()
    {
        table = new JTable(testfileobject.displayTable());
        table.setPreferredScrollableViewportSize(new Dimension(150, 50));
        pane = new JScrollPane(table);

        JOptionPane.showMessageDialog(b1, pane, "Title", JOptionPane.INFORMATION_MESSAGE);
        pane.setVisible(true);
    }

    public void b2click(String inputFN, String inputLN, int inputPoints)
    {
        String message = 
            "First Name: " + inputFN + "\n"
            + "Last Name: " + inputLN  + "\n"
            + "Points: " + inputPoints;
        JOptionPane.showMessageDialog(b2, message , "Based on Order of Entries", JOptionPane.INFORMATION_MESSAGE);
    }

    public void makeDBAdminsClick()
    {
        JTextField codeField = new JTextField("0",10);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Administrator Password: "));
        myPanel.add(codeField);
        
        int result = JOptionPane.showConfirmDialog(null,myPanel, 
                 "Please Enter the Password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            if(codeField.getText().equals("AI6g!V48k1Zo")) //data validation(without this specific code, no random person can make the database)
            {
                JPanel myPanel2 = new JPanel();
                myPanel2.add(new JLabel("Create MySQL Database? "));
                int result2 = JOptionPane.showConfirmDialog(null,myPanel2, 
                "Choose Your Option", JOptionPane.OK_CANCEL_OPTION);
                if(result2 == JOptionPane.OK_OPTION)
                {
                    testfileobject.makeNewTable();
                }
            }
        }

        
    }

    public static void main(String[] args) 
    {
        new Swing();    
    }

    @Override
    public void actionPerformed(ActionEvent e) {    }
}
# Passion-Project

This is a Java Program that is supposed to boost kids' involvement in 10 recurring School Wide Events. 
Many Java Swing Components(Checkboxes, Text Fields, Buttons, CheckBoxes, ScrollPane, Panel, etc.) were used to form the GUI of the application.

# The GUI
The GUI is layed out in a rectangular frame, where the user types in their first name, last name, uses the slider to select their age, selects their grade using a drop down menu, and selects the dates they want to either participate in/attend in. Finally, they hit submit to process their entry. Data Validation is their for every single field in the GUI.

The Dates shown in the GUI are from a complex algorithm in CalendarHelp.java that uses the old java.util.Calendar library to form two arrays containing 5 days each, which are then displayed in the GUI in the from of checkboxes. CalendarHelp.java took the longest to code and is also the most invaluable file in this project.

# Database Connectivity
All Data from the GUI is processed through JDBC(Java Database Connectivity) and entered into the MySQL Workbench Community Version Database.
MySQLDB.java contains all the methods to create tables, update tables, choosing 4 random winners on a select 4 days, and choosing the student with the most amount of points. 

In MySQLDB.java, the use of the JFreeChart library helped make the graphs that shows the number of points each student has per grade. If the graph gets small, just expand it using the Maximize button

# Limitations

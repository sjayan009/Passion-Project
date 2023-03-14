
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Color;


public class MySQLDB extends JFrame
{
	public static Connection connection;
	public JTextField showOptionPaneFieldEntry;

	public String fN9 = ""; 
	public String lN9 = "";
	public String fN10 = ""; 
	public String lN10 = "";
	public String fN11 = ""; 
	public String lN11 = "";
	public String fN12 = ""; 
	public String lN12 = "";

	public String topPointFN = "";
	public String topPointLN = "";
	public int topPointNumber = 0;

    //public static void main(String[] args){
            
		//try {
			
			//Creating a connection
			//Class.forName("com.mysql.cj.jdbc.Driver");
			//Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","MySQL4jayan+");
			
			//Create statement Query
			//Statement stmt = connection.createStatement();
			
			/*String sql = "CREATE TABLE REGISTRATION " +
	                   "(id INTEGER not NULL, " +
	                   " first VARCHAR(255), " + 
	                   " last VARCHAR(255), " + 
	                   " age INTEGER, " + 
	                   " PRIMARY KEY ( id ))"; */
			
			//stmt.executeUpdate(sql);
			
			//stmt.close();
		//}
//		catch(Exception e) {
//			System.out.println(e);
//		}
	//}

	public MySQLDB()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","MySQL4jayan+");

		}
		catch(Exception e)
		{

		}
	}

	public void makeNewTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			
			String sql = "CREATE TABLE IF NOT EXISTS test_table ("+
							"id int NOT NULL AUTO_INCREMENT,"+
							"first_name VARCHAR(30) NOT NULL,"+
							"last_name VARCHAR(30) NOT NULL,"+
							"grade int NOT NULL,"+
							"points int NOT NULL,"+
							"numTenDaysPassed int NOT NULL,"+
				 			"PRIMARY KEY(id));";

			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e)
		{
			
		}
	}

	public int getNum10DaysPassed(String firstName, String lastName)
	{
		String firstN = firstName;
		String lastN = lastName;
		int num10Days = 0;
		try
		{
			Statement statement = connection.createStatement();
			String sql = "SELECT numTenDaysPassed FROM test_table WHERE first_name = " + firstN + " AND last_name = " + lastN + "";
			//System.out.println(sql);
			ResultSet result = statement.executeQuery(sql);

			while(result.next())
			{
				num10Days = result.getInt("numTenDaysPassed");
				//System.out.println(num10Days);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return num10Days;
	}

	public int getPoints(String firstName, String lastName)
	{
		String firstN = firstName;
		String lastN = lastName;
		int points = 0;
		try
		{
			Statement statement = connection.createStatement();
			String sql = "SELECT points FROM test_table WHERE first_name = " + firstN + " AND last_name = " + lastN + "";
			ResultSet result = statement.executeQuery(sql);

			while(result.next())
			{
				points = result.getInt("points");
				//System.out.println(points);
			}
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
		return points;
	}

	public void insertRecord(String firstName, String lastName, int grade, int points, int num10DaysPassed)
	{
		try
		{
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO test_table(first_name, last_name, grade, points, numTenDaysPassed) VALUES" + "(" + firstName + ", " + lastName + ", " + grade + ", " + points + ", " + num10DaysPassed + ")";
			statement.executeUpdate(sql);
		}
		catch(Exception e) { }
	}

	public void updateRecord(String firstName, String lastName, int grade, int points, int num10DaysPassed)
	{
		try
		{
			if(getNum10DaysPassed(firstName, lastName) == num10DaysPassed)
			{
				//System.out.println("NO BETA");
				//System.out.println(getPoints(firstName, lastName));
				JOptionPane.showMessageDialog(showOptionPaneFieldEntry, "You already selected your days for the week! Come back next week.", "Multiple Entry Detected", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				int prevPoints = getPoints(firstName, lastName);
				//System.out.println("Prev Points: " + prevPoints);
				int currPoints = prevPoints + points;
				//System.out.println("urrent Points: " + currPoints);
				Statement statement = connection.createStatement();
				String sql = "UPDATE test_table SET grade = " + grade + ", " + "points = " + currPoints + ", numTenDaysPassed = " + num10DaysPassed + " WHERE first_name = " + firstName + " AND last_name = " + lastName;
				//System.out.println(sql);
				statement.executeUpdate(sql);
			}

		}
		catch(Exception e) { }
	}

	public void checkRecordExists(JTextField firstName, JTextField lastName, int gradeFromOtherClass, int pointsFromOtherClass, int num10DaysPassed)
	{
		this.showOptionPaneFieldEntry = firstName;
		String firstN = "\"" + firstName.getText() + "\"";
		String lastN = "\"" + lastName.getText() + "\"";

		try
		{
			Statement statement = connection.createStatement();

			String sql = "SELECT * FROM test_table WHERE first_name = " + firstN + " AND last_name = " + lastN + "";

			ResultSet rs = statement.executeQuery(sql);

			if(rs.next())
			{
				//System.out.println("VALUE EXISTS");
				updateRecord(firstN, lastN, gradeFromOtherClass, pointsFromOtherClass, num10DaysPassed);
			}
			else
			{
				//System.out.println("VALUE DOES NOT EXIST");
				insertRecord(firstN, lastN, gradeFromOtherClass, pointsFromOtherClass, num10DaysPassed);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(firstName, "The database has not been created. Please contact your administrator and tell them to create it. ", "Table Does Not Exist", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*
	 * Method used to choose a random winner each quarter
	 */
	public void chooseRandomWinner()
	{
		try
		{
			Statement stmt = connection.createStatement();

			String sql9 = "SELECT first_name, last_name FROM test_table WHERE grade = 9 ORDER BY RAND() LIMIT 1;";
			ResultSet result9 = stmt.executeQuery(sql9);
			while(result9.next())
			{
				fN9 = result9.getString("first_name");
				lN9 = result9.getString("last_name");
			}

			String sql10 = "SELECT first_name, last_name FROM test_table WHERE grade = 10 ORDER BY RAND() LIMIT 1;";
			ResultSet result10 = stmt.executeQuery(sql10);
			while(result10.next())
			{
				fN10 = result10.getString("first_name");
				lN10 = result10.getString("last_name");
			}

			String sql11 = "SELECT first_name, last_name FROM test_table WHERE grade = 11 ORDER BY RAND() LIMIT 1;";
			ResultSet result11 = stmt.executeQuery(sql11);
			while(result11.next())
			{
				fN11 = result11.getString("first_name");
				lN11 = result11.getString("last_name");
			}

			String sql12 = "SELECT first_name, last_name FROM test_table WHERE grade = 12 ORDER BY RAND() LIMIT 1;";
			ResultSet result12 = stmt.executeQuery(sql12);
			while(result12.next())
			{
				fN12 = result12.getString("first_name");
				lN12 = result12.getString("last_name");
			}			
		}

		catch(Exception e)
		{

		}
	}

	public void makeRandomTableAndAddRandomStudent()
	{
		chooseRandomWinner();
		try 
		{
			Statement stmt = connection.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS test_table_random ("+
							"id int NOT NULL AUTO_INCREMENT,"+
							"first_name VARCHAR(30) NOT NULL,"+
							"last_name VARCHAR(30) NOT NULL,"+
							"grade int NOT NULL,"+
				 			"PRIMARY KEY(id));";

			stmt.executeUpdate(sql);

			String sql9 =  "INSERT INTO test_table_random(first_name, last_name, grade) VALUES"  + "(\"" + fN9 +  "\", \"" + lN9  + "\", " + 9  + ")";
			String sql10 = "INSERT INTO test_table_random(first_name, last_name, grade) VALUES"  + "(\"" + fN10 + "\", \"" + lN10 + "\", " + 10 + ")";
			String sql11 = "INSERT INTO test_table_random(first_name, last_name, grade) VALUES"  + "(\"" + fN11 + "\", \"" + lN11 + "\", " + 11 + ")";
			String sql12 = "INSERT INTO test_table_random(first_name, last_name, grade) VALUES"  + "(\"" + fN12 + "\", \"" + lN12 + "\", " + 12 + ")";
			
			//System.out.println(sql9);
			//System.out.println(sql10);
			//System.out.println(sql11);
			//System.out.println(sql12);


			stmt.executeUpdate(sql9);
			stmt.executeUpdate(sql10);
			stmt.executeUpdate(sql11);
			stmt.executeUpdate(sql12);
		} 
		catch (Exception e) 
		{
			//System.out.println(e);
		}

		
	}

	public void chooseTopPointStudent()
	{
		try
		{
			Statement stmt = connection.createStatement();

			String sql = "SELECT * FROM test_table ORDER BY points DESC LIMIT 0,1";
			ResultSet topPointResult = stmt.executeQuery(sql);
			while(topPointResult.next())
			{
				topPointFN = topPointResult.getString("first_name");
				topPointLN = topPointResult.getString("last_name");
				topPointNumber = topPointResult.getInt("points");
			}

			//System.out.println(firstName + "" + lastName + "" + topPoints);
			
		} 
		catch (Exception e) 
		{
			//System.out.println(e);
		}

	}

	public String getTopPointFN()
	{
		return topPointFN;
	}
	public String getTopPointLN()
	{
		return topPointLN;
	}
	public int getValue()
	{
		return topPointNumber;
	}

	public DefaultTableModel displayTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			String sql="SELECT * FROM test_table_random";

			DefaultTableModel model = new DefaultTableModel(new String[]{"First Name", "Last Name", "Grade"}, 0);

			ResultSet rs = stmt.executeQuery(sql);

			String fN, lN, grade;
			fN = "";
			lN = "";
			grade = "";

			int counter = 0;
			while(rs.next() && counter < 4)
			{
    			fN = rs.getString("first_name");
    			lN = rs.getString("last_name");
    			grade = String.valueOf(rs.getInt("grade"));
				model.addRow(new Object[]{fN, lN, grade});
				counter++;
			}
			return model;
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
		DefaultTableModel d1 = null;
		return  d1;
	}


	public static void selectTop3()
	{
		int topPoint1, topPoint2, topPoint3;
		topPoint1 = 0;
		topPoint2 = 0;
		topPoint3 = 0;
		try
		{
			Statement stmt = connection.createStatement();

			String sqlOne   = "SELECT DISTINCT points FROM test_table WHERE points IS NOT NULL ORDER BY points DESC LIMIT 1 OFFSET 0";
			String sqlTwo   = "SELECT DISTINCT points FROM test_table WHERE points IS NOT NULL ORDER BY points DESC LIMIT 1 OFFSET 1";
			String sqlThree = "SELECT DISTINCT points FROM test_table WHERE points IS NOT NULL ORDER BY points DESC LIMIT 1 OFFSET 2";
			
			ResultSet rs = stmt.executeQuery(sqlOne);

			while (rs.next()) 
			{
    			topPoint1 = rs.getInt("points");
				
			}

			rs = stmt.executeQuery(sqlTwo);

			while (rs.next()) 
			{
    			topPoint2 = rs.getInt("points");
			}

			rs = stmt.executeQuery(sqlThree);

			while (rs.next()) 
			{
    			topPoint3 = rs.getInt("points");
			}

			//System.out.println(topPoint1 + " " + topPoint2 + " " + topPoint3);

			final int t1 = topPoint1;
			final int t2 = topPoint2;

			String sqlF = "SELECT first_name, last_name, points FROM test_table WHERE points = " + topPoint1 + " UNION ALL SELECT first_name, last_name, points FROM test_table WHERE points = " + topPoint2 + " UNION ALL SELECT first_name, last_name, points FROM test_table WHERE points = " + topPoint3 + "";
			ResultSet finalRS = stmt.executeQuery(sqlF);

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			while (finalRS.next()) 
			{
    			String firstName = finalRS.getString("first_name");
				String lastName = finalRS.getString("last_name");
				String name = firstName + " " + lastName;
    			int points = finalRS.getInt("points");
    			dataset.addValue(points, "Points", name);
			}

			JFreeChart chart = ChartFactory.createBarChart(
    			"Top Scorers Based on Color", // chart title
    			"Student Name", // domain axis label
    			"Points", // range axis label
    			dataset, // chart dataset
    			PlotOrientation.HORIZONTAL, // chart orientation
    			false, // include legend
    			true, // include tooltips
    			false // include URLs
			);

			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			CategoryAxis xAxis = plot.getDomainAxis();
			Font labelFont = new Font("Arial", Font.BOLD, 12);
			xAxis.setTickLabelFont(labelFont);

	        // Create the renderer and set the color for each column
			BarRenderer renderer = new BarRenderer() {
				@Override
				public Paint getItemPaint(int row, int column) {
					// Get the value of the current column
					Number value = dataset.getValue(row, column);
					// Set the color based on the value
					if (value.intValue() == t1) {
						return new Color(135, 206, 235);
					} else if (value.intValue() == t2) {
						return new Color(212,175,55);
					} else {
						return new Color(176,141,87);
					}
				}
			};
			plot.setRenderer(renderer);
	
			NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
			yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			yAxis.setTickUnit(new NumberTickUnit(1));
			
			ChartPanel chartPanel = new ChartPanel(chart);
			JFrame frame = new JFrame("Top Scorers");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setContentPane(chartPanel);
			frame.pack();

			String str = "Diamond Color - 1st Place Winner(s)\n"
					   + "Gold Color - 2nd Place Winner(s)\n"
					   + "Bronze Color - 3rd Place Winner(s)\n------------------------------------------------\n";

			String str2 = "First Place Winner(s) Prize --> Free School Swag For This Quarter!\n"
						+ "Second Place Winner(s) Prize --> Free Lunch For This Quarter!\n"
						+ "Thrid Place Winner(s) Prize --> A Backpack!\n";
			

			JOptionPane pane = new JOptionPane(str+str2);
    		JDialog d = pane.createDialog(frame, "Legend and Prizes");
    		d.setLocation(680,0);	

			frame.setVisible(true);
			d.setVisible(true);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	public void deleteTestTableRandom()
	{
		try
		{
			Statement stmt = connection.createStatement();

			String sql = "DROP TABLE test_table_random";

			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{

		}
	}

	public void deleteTestTable()
	{
		try
		{
			Statement stmt = connection.createStatement();

			String sql = "UPDATE test_table SET points = 0";

			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{

		}
	}

	public static void GradeInfo(int num)
	{
		Connection connection;
		try 
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","MySQL4jayan+");
		 
			String query = "SELECT first_name, last_name, points FROM test_table WHERE grade = " + num;
		
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			while (rs.next()) 
			{
    			String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String name = firstName + " " + lastName;
    			int points = rs.getInt("points");
    			dataset.addValue(points, "Points", name);
			}

			JFreeChart chart = ChartFactory.createBarChart(
    			"Points per Student", // chart title
    			"Student Name", // domain axis label
    			"Points", // range axis label
    			dataset, // chart dataset
    			PlotOrientation.HORIZONTAL, // chart orientation
    			true, // include legend
    			true, // include tooltips
    			false // include URLs
			);

			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			CategoryAxis xAxis = plot.getDomainAxis();
			Font labelFont = new Font("Arial", Font.BOLD, 12);
			xAxis.setTickLabelFont(labelFont);

			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			Paint color = new Color(135, 206, 235);
			renderer.setSeriesPaint(0, color);

			NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
			yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			yAxis.setTickUnit(new NumberTickUnit(1));


			ChartPanel chartPanel = new ChartPanel(chart);
			JFrame frame = new JFrame("Points per Student in Grade " + num);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setContentPane(chartPanel);
			frame.pack();
			frame.setVisible(true);
		} 
		catch (Exception e) 
		{
		}
		
	}
}
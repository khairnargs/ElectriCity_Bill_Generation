
package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;

public class Calculatebill extends JFrame implements ActionListener{
    JTextField tfname , tfaddress, tfunits, tfcity , tfemail, tfphone;
    JButton next, cancel;
    JLabel lblname,labeladress;
    Choice meternumber, cmonth;
    Calculatebill()
        {
            setSize(700,500);
            setLocation(400,150);

            JPanel p = new JPanel();
            p.setLayout(null);
            p.setBackground(Color.LIGHT_GRAY);
            add(p);

            JLabel heading = new JLabel("Calculate Electricity Bill"); 
            heading.setBounds(100,20,400,20);
            p.add(heading);
            heading.setFont(new Font("Tahuma",Font.PLAIN, 24));
            
        
            JLabel lblmeternumber = new JLabel("Meter Number"); 
            lblmeternumber.setBounds(100,80,100,20);
            p.add(lblmeternumber);
            
            meternumber = new Choice();
            
            try{
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from customer");
                while(rs.next()){
                    meternumber.add(rs.getString("meter_no"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            
            meternumber.setBounds(250,80,200,20);
            p.add(meternumber);
            
                        
            lblname = new JLabel("Name"); 
            lblname.setBounds(100,120,100,20);
            p.add(lblname);
            
            lblname = new JLabel(""); 
            lblname.setBounds(250,120,100,20);
            p.add(lblname);
            
            JLabel lbladdress = new JLabel("Address"); 
            lbladdress.setBounds(100,160,200,20);
            p.add(lbladdress);
            
            labeladress = new JLabel();
            labeladress.setBounds(250,160,200,20);
            p.add(labeladress);
            
            try{
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meternumber.getSelectedItem()+"'");
                while(rs.next()){
                    lblname.setText(rs.getString("name"));
                    labeladress.setText(rs.getString("address"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            
            meternumber.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent ie){
                    try{
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meternumber.getSelectedItem()+"'");
                while(rs.next()){
                    lblname.setText(rs.getString("name"));
                    labeladress.setText(rs.getString("address"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
                }
            });
            
            JLabel lblstate = new JLabel("Unit Consumed"); 
            lblstate.setBounds(100,200,200,20);
            p.add(lblstate);
            
            tfunits = new JTextField();
            tfunits.setBounds(250,200,200,20);
            p.add(tfunits);
            
            JLabel lblcity = new JLabel("Month"); 
            lblcity.setBounds(100,240,100,20);
            p.add(lblcity);
            
            cmonth = new Choice();
            cmonth.setBounds(250,240,200,20);
            cmonth.add("January");
            cmonth.add("February");
            cmonth.add("March");
            cmonth.add("April");
            cmonth.add("May");
            cmonth.add("June");
            cmonth.add("July");
            cmonth.add("August");
            cmonth.add("September");
            cmonth.add("October");
            cmonth.add("November");
            cmonth.add("December");
            p.add(cmonth);
            
            

            next = new JButton("Submit");
            next.setBounds(120,350,100,20);
            next.addActionListener(this);
            p.add(next);
            
            cancel = new JButton("Cancel");
            cancel.setBounds(300,350,100,20);
            cancel.addActionListener(this);
            p.add(cancel);
            
            setLayout(new BorderLayout());
            
            add(p, "Center");
            
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
            Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel image = new JLabel(i3);
            add(image,"East");
            
            getContentPane().setBackground(Color.WHITE);
            
            setVisible(true);
        }
    
        public void actionPerformed(ActionEvent ae){
            if (ae.getSource()== next){
                String meter = meternumber.getSelectedItem();
                String units = tfunits.getText();
                String month = cmonth.getSelectedItem();
            
                int totalbill = 0;
                int unit_consumed = Integer.parseInt(units);
            
                String query = "select * from tax";
            
                try
                {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query);
            
            while(rs.next()){
                totalbill += unit_consumed * Integer.parseInt(rs.getString("Cost_per_Unit"));
                totalbill += Integer.parseInt(rs.getString("meter_rent"));
                totalbill += Integer.parseInt(rs.getString("service_charge"));
                totalbill += Integer.parseInt(rs.getString("service_tax"));
                totalbill += Integer.parseInt(rs.getString("swacch_bharat_cess"));
                totalbill += Integer.parseInt(rs.getString("fixed_tax"));
             
            }
        }catch (Exception e)
            {
            e.printStackTrace();
            }
            
            String query3 = "insert into bill  values('"+meter+"', '"+month+"' , '"+units+"', '"+totalbill+"')";
            //String querString3 = "insert into bill  values('"+meter+"', '"+month+"' , '"+units+"', '"+totalbill+"')";
            
            try{
                Conn c = new Conn();
                c.s.executeUpdate("query3");
                
                JOptionPane.showMessageDialog(null, "Customer Bill Is Calculated Successfully!");
                setVisible(false);
            }catch(Exception e){
                e.printStackTrace();
            }
            
            }else{
                setVisible(false);
            }
        }
    
    public static void main(String args[]){
        new Calculatebill();
    }
    
}

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Phone extends JFrame implements ActionListener {

    JPanel[] row = new JPanel[5];
    JButton[] button = new JButton[19];
    String[] buttonString = {"7", "8", "9", "",
            "4", "5", "6", "",
            "1", "2", "3", "",
            "", "", "Clear", "End Call",
            "", "Call", "0"};

    int[] dimW = {300,45,100,90};
    int[] dimH = {35, 40};
    Dimension displayDimension = new Dimension(dimW[0], dimH[0]);
    Dimension regularDimension = new Dimension(dimW[1], dimH[1]);
    Dimension rColumnDimension = new Dimension(dimW[2], dimH[1]);
    Dimension zeroButDimension = new Dimension(dimW[3], dimH[1]);

    JTextArea display = new JTextArea(1,20);
    Font font = new Font("Times new Roman", Font.BOLD, 14);

    Phone() {

        super("Phone");

        setDesign();

        setSize(380, 250);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(5,5);
        setLayout(grid);

        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
        for(int i = 0; i < 5; i++)
            row[i] = new JPanel();
        row[0].setLayout(f1);
        for(int i = 1; i < 5; i++)
            row[i].setLayout(f2);
        for(int i = 0; i < 19; i++) {
            
            button[i] = new JButton();
            if (i==12)
                continue;
            button[i].setText(buttonString[i]);
            button[i].setFont(font);
            button[i].addActionListener(this);
        }
        button[15].setBackground(Color.red);
        button[17].setBackground(Color.green);
        button[3].setBackground(Color.cyan);
        button[3].setText("A");
        button[7].setBackground(Color.red);
        button[7].setText("d");
        button[11].setBackground(Color.green);
        button[11].setText("i");
        button[12].setBackground(Color.gray);
        button[12].setText("SLS");
        button[13].setBackground(Color.pink);
        button[16].setBackground(Color.yellow);
        button[16].setText("& Biswa");
        display.setFont(font);
        display.setEditable(false);
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        display.setPreferredSize(displayDimension);
        for(int i = 0; i < 14; i++)
            {if(i==12)
                continue;
            button[i].setPreferredSize(regularDimension);
        }
        for(int i = 14; i < 18; i++)
            button[i].setPreferredSize(rColumnDimension);
        button[18].setPreferredSize(zeroButDimension);
        row[0].add(display);
        add(row[0]);

        for(int i = 0; i < 4; i++)
            row[1].add(button[i]);
        row[1].add(button[14]);
        add(row[1]);

        for(int i = 4; i < 8; i++)
            row[2].add(button[i]);
        row[2].add(button[15]);
        add(row[2]);

        for(int i = 8; i < 12; i++)

            row[3].add(button[i]);
        row[3].add(button[16]);
        add(row[3]);

        row[4].add(button[18]);
        for(int i = 12; i < 14; i++)
        {if (i==13)
            {continue;}
            row[4].add(button[i]);}
        row[4].add(button[17]);
        add(row[4]);
        setVisible(true);
        display.setEditable(true);
    }    

    public void clear() {
        //  System.out.println("\f");
        try {
            display.setText("");

        } catch(NullPointerException e) { 
        }
        display.setEditable(true);
    }

    public void getResult() throws Exception{
        display.setEditable(false);
        String num = (display.getText());

        if (!(new numCheck().check(num)))
        {
            display.setText("Enter a valid number");
        }
        else
        {
            try{
                if(new DeviceChecker().work())
                {
                    String cmd="adb shell am start -a android.intent.action.CALL tel:"+num;
                    //  System.out.println("\f");
                    // System.out.println("Calling "+num+"...");
                    Process p=Runtime.getRuntime().exec(cmd);
                    display.setText("Dialling");
                    p.waitFor();
                }
                else
                {
                    display.setText("Device not connected");

                }
            }catch(Exception ex){}

        }
    }

    public void endCall() throws Exception
    {
        Process ec=Runtime.getRuntime().exec("adb shell input keyevent 6");
        ec.waitFor();
        ec=Runtime.getRuntime().exec("adb shell input keyevent 3");
        ec.waitFor();
        display.setText("Call Ended");
        display.setEditable(false);
        // System.out.println("\f");
        // System.out.println("Call Ended");
    }

    public final static void setDesign() {
        try {
            UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {  
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == button[0])
            display.append("7");
        if(ae.getSource() == button[1])
            display.append("8");
        if(ae.getSource() == button[2])
            display.append("9");
        if(ae.getSource() == button[4])
            display.append("4");
        if(ae.getSource() == button[5])
            display.append("5");
        if(ae.getSource() == button[6])
            display.append("6");
        if(ae.getSource() == button[8])
            display.append("1");
        if(ae.getSource() == button[9])
            display.append("2");
        if(ae.getSource() == button[10])
            display.append("3");
        if(ae.getSource() == button[15])
        { try
            {endCall();}
            catch (Exception e)
            {}}    

        if(ae.getSource() == button[17])
        { try
            {getResult();}
            catch (Exception e)
            {}}
        if(ae.getSource() == button[18])
            display.append("0");
        if(ae.getSource() == button[14])
            clear();

    }

    void none()
    {
        //System.out.println();
    }

}

package files;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class Mcamera implements ActionListener {

	JFrame frame;
	JToolBar tool_bar;
	JLabel output_fol;
	JButton capt,ex,browse;
	//JComboBox<String> device;
	JTextField out;
	Webcam webcam;
	WebcamPanel panel;
	JPanel fp;
	//List<Webcam> lv;
	//int length;
	//ListIterator<String> list;
	String format;
	
	public Mcamera()
	{
		frame = new JFrame("Masani Cam");
		tool_bar = new JToolBar();
		output_fol = new JLabel("Output file");
		capt = new JButton("Capture");
		ex = new JButton("Exit");
		browse = new JButton("Browse");
		//device = new JComboBox<String>();
		out = new JTextField(20);
		/*lv = Webcam.getWebcams();
		
	    length = lv.size();
	    for(int i=0;i<length;i++)
	    {
	    	webcam = Webcam.getWebcams().get(i);
	    	
			try{
			webcam.setViewSize(new Dimension(640,480));
			}catch(Exception e2)
			{
				webcam.close();
			}
	    }*/
	    webcam = Webcam.getDefault();
		try{
		webcam.setViewSize(new Dimension(640,480));
		}catch(Exception e2)
		{
			webcam.close();
		}
		panel = new WebcamPanel(webcam);
		fp = new JPanel();
		
		//add methods to constructor
		
		Add_to();
		set_to();
	}
	
	public void Add_to()
	{
		tool_bar.add(capt);
		tool_bar.add(output_fol);
		tool_bar.add(out);
		tool_bar.add(browse);
		//tool_bar.add(device);
		tool_bar.add(ex);
		
		frame.add(tool_bar,BorderLayout.NORTH);
		
		if(webcam != null)
		{
			
			webcam.open();
		}
		
		fp.add(panel);
		
		frame.add(fp,BorderLayout.CENTER);
		
		frame.validate();
		
		
		
		
	}
	
	public void set_to()
	{
		capt.addActionListener(this);
		ex.addActionListener(this);
		browse.addActionListener(this);
		
		//device.addItemListener(this);
		
		frame.setSize(600,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//frame.setLayout(new FlowLayout());
		//frame.setLayout(new GridLayout(1,1));
		
		panel.setFPSDisplayed(true);
		panel.setPreferredSize(new Dimension(800,800));
		//fp.setSize(500,500);
		
		frame.setResizable(false);
		
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		
		if(s.equals("Exit"))
		{
			try
			{
				webcam.close();
				System.exit(0);
			
			}catch(Exception e1)
			{}
		}
		else if(s.equalsIgnoreCase("capture"))
		{
			//int count = 0;
			BufferedImage image = webcam.getImage();
			// save image to PNG file
						try {
							String file = out.getText();
							if(file.endsWith("png"))
							{
								format = "PNG";
							}
							else if(file.endsWith("jpg"))
							{
								format = "JPG";
							}
							else if(file.endsWith("bmp"))
							{
								format = "bmp";
							}
							
							else{
								
							}
							
							
							ImageIO.write(image,format, new File(file));
							
							File f1 = new File(file);
							if(f1.exists())
							{
								MyDia().setVisible(true);
							}
							
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		}
		else if(s.equals("Browse"))
		{
			JFrame f = new JFrame("Select Location");
			JFileChooser fc = new JFileChooser();
			f.add(fc);
			f.setVisible(true);
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int returnval = fc.showDialog(f,"select");
			if(returnval == JFileChooser.APPROVE_OPTION) {
			    fc.approveSelection();
			   // f.pack();
			    //f.setLocation(0,0);
			    out.setText(fc.getCurrentDirectory().getPath()+File.separator+fc.getSelectedFile().getName());
				
			    f.setVisible(false);
			    f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				
			}
			else if(returnval == JFileChooser.CANCEL_OPTION){
				fc.cancelSelection();
				out.setText("");
				f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				
			}
			else{
				
			}
		}
		
	}
	
	public JDialog MyDia()
	{
		final JDialog d = new JDialog();
		d.add(new JLabel("Captured"));
		JButton b = new JButton("OK");
		d.add(b);
		b.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d.setVisible(false);
				
			}
			
		});
		d.setUndecorated(true);
		d.setSize(100,100);
		d.setLocation(200,300);
		d.setLayout(new FlowLayout());
		
		
		return d;
	}

	
	
	public static void main(String[] args)
	{
		new Mcamera();
	}
	
	
	
}

import java.io.IOException;

import javax.swing.JFrame;
public class Main 
{	public static void main(String[] args) throws IOException
	{   //create frame object
		JFrame frame = new JFrame();
		
		
		//objects of XML parsers, pass them to Search panel
		ParseInputXML parseInput = new ParseInputXML();
		ParseTaxonomyXML parseTaxonomy = new ParseTaxonomyXML();
		ParseDefaultParametersXML parseParams = new ParseDefaultParametersXML();
		
		//adding panel to frame
		frame.add(new SearchPanel(parseInput, parseTaxonomy, parseParams));
		
		//frame specifications
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
	}
}

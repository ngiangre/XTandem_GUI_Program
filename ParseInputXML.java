import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class ParseInputXML 
{
	//Object declarations
	private Object taxonomyFileNamePath, outputFileNamePath, paramsFileNamePath;
	private Object inputFileNamePath, taxonFileName, spectraFileNamePath;
	//parser method
	public void parseInputXML()
	{
		try
        {
        //parse file 
		//needed to build representation of XML file, so we can "virtually" parse and modify,
		//then rebuild, escape "virtual mode", and do actual parsing and modifying
        String filepath = getInputFileNamePath().toString();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filepath);
        //node structure seen in XML files. Or see XML tutorials to understand terms
        //get parent node
        Node bioml = doc.getElementsByTagName("bioml").item(0);
        //get child nodes
        NodeList notes = bioml.getChildNodes();
        //go through child nodes
        for(int i=3; i<notes.getLength(); i=2+i)
        {
        	//grab child node
        	Node note = notes.item(i);
        	//grab all child node attributes
        	NamedNodeMap attr = note.getAttributes();
        	//if no attributes at node continue
        	if(attr == null)
        	{
        		continue;
        	}
        	//if there are...
        	else
        	{
        		//get "label" attribute, each child node has the "label" attribute
        		Node nodeAttr = attr.getNamedItem("label");
        		//select label attribute and see if equal 
        		if("list path, default parameters".equals(nodeAttr.getNodeValue()))
        		{
        			note.setTextContent(getParamsFileNamePath().toString());
        		}
        		if("list path, taxonomy information".equals(nodeAttr.getNodeValue()))
        		{
        			note.setTextContent(getTaxonomyFileNamePath().toString());
        		}
            	if("protein, taxon".equals(nodeAttr.getNodeValue()))
            	{
            		note.setTextContent(getTaxonFileName().toString());
            	}
            	if("spectrum, path".equals(nodeAttr.getNodeValue()))
            	{
            		note.setTextContent(getSpectraFileNamePath().toString());
            	}
            	if("output, path".equals(nodeAttr.getNodeValue()))
            	{
            		note.setTextContent(getOutputFileNamePath().toString());
            	}
           	}//end of else
        }//end of for
        //update file, escape "virtual mode"
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);

   }//end of try
	catch(NullPointerException e){
		restart();
	} catch (ParserConfigurationException pce) {
   pce.printStackTrace();
   } catch (TransformerException tfe) {
        tfe.printStackTrace();
   } catch (IOException ioe) {
        ioe.printStackTrace();
   } catch (SAXException sae) {
        sae.printStackTrace();
   }
	}
	//catcher method
	public void restart()
	{
		JOptionPane.showMessageDialog(null,  "Sorry, choose the correct input file to parse. Try again");
	}
	//mutators or "setters"
	public void setInputFileNamePath(String string)
	{
		if("".equals(string))
			JOptionPane.showMessageDialog(null,  "Sorry, you need to choose an input file to copy to your new file. Try again.");
		else
			inputFileNamePath = string;
	}
	public void setTaxonomyFileNamePath(String string)
	{
		if("".equals(string))
			JOptionPane.showMessageDialog(null,  "Sorry, you need to choose a taxonomy file to copy to your new file. Try again.");
		else
			taxonomyFileNamePath = string;
		
	}
	public void setOutputFileNamePath(String string)
	{
		if("".equals(string))
			JOptionPane.showMessageDialog(null,  "Sorry, you need to enter an output file name. Try again.");
		else
			outputFileNamePath = string;
	}
	public void setParamsFileNamePath(String string) 
	{
		if("".equals(string))
			JOptionPane.showMessageDialog(null,  "Sorry, you need to choose a parameters file to copy to your new file. Try again");
		else
			paramsFileNamePath = string;
	}
	public void setSpectraFileName(File file)
	{
		if(file == null)
			JOptionPane.showMessageDialog(null,  "Sorry, you need to choose a spectra file. Try again.");
		else
		{
			//ignore block comment, but may need it later if desired to change file extensions
/*			if("txt".equals(isExt(file)))
			{
				
				 * piece file name as name and extension
				 * put txt extension and file name together-concatenate strings
				 * 
				 
				String mgf = ".mgf";
				String[] pieces;
				String fileName;
				fileName=file.getName();
				pieces = fileName.split("\\.(?=[^\\.]+$)");
				String spectraFileName = pieces[0];
				String f = getPath(file);
				String fileNameWithMGF = f + "/" + spectraFileName + mgf;
				Process cp;
				try {
					cp = Runtime.getRuntime().exec("cp " + fileNameWithMGF + " " + file.toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				file = new File(fileNameWithMGF);
			}*/
			spectraFileNamePath = file;
		}
	}
	public void setTaxonName(File file)
	{
		if(file == null)
			JOptionPane.showMessageDialog(null,  "Sorry, you need to choose a taxon file. Try again.");
		else
		{
			//need to piece file name to get part wanted which is just the taxon name
			String[] pieces;
			String fileName;
			fileName=file.getName();
			pieces = fileName.split("\\.(?=[^\\.]+$)");
			taxonFileName = pieces[0].toLowerCase();
		}
	}
	//accessors
	public Object getInputFileNamePath()
	{
		return inputFileNamePath;
	}
	public Object getTaxonomyFileNamePath()
	{
		return taxonomyFileNamePath;
	}
	public Object getOutputFileNamePath()
	{
		return outputFileNamePath;
	}
	public Object getParamsFileNamePath()
	{
		return paramsFileNamePath;
	}
	public Object getTaxonFileName()
	{
		return taxonFileName;
	}
	public Object getSpectraFileNamePath()
	{
		return spectraFileNamePath;
	}
	//methods
	//returning the actual extension
	private String isExt(File file)
	{
		String fileName = file.getName();
		String [] pieces;
		pieces = fileName.split("\\.(?=[^\\.]+$)");
		return pieces[1];
	}
	//gets file path
	private String getPath(File file)
	{
		//code from http://www.mkyong.com/java/how-to-get-the-filepath-of-a-file-in-java/
		String absolutePath = file.getAbsolutePath();
		String filePath = absolutePath.
		    substring(0,absolutePath.lastIndexOf(File.separator));
		return filePath;
	}
}
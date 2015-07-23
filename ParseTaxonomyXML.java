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
public class ParseTaxonomyXML 
{
	//declare objects
	private File taxonFileNamePath;
	private Object taxonomyFileNamePath;
	private String taxonName;
	//parse method
	public void parseTaxonomyXML()
	{
		try
        {
        String filepath = getTaxonomyFileNamePath().toString();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filepath);
        //changing taxon attribute value, only one child node in file
        Node bioml = doc.getElementsByTagName("bioml").item(0);
        NodeList taxons = bioml.getChildNodes();
        Node taxon = taxons.item(1);
        NamedNodeMap taxonAttrs = taxon.getAttributes();
        Node taxonAttr = taxonAttrs.getNamedItem("label");
        //change attribute content to taxon name
        taxonAttr.setNodeValue(getTaxonName());
        //changing file branch attribute value
        NodeList files = taxon.getChildNodes();
        Node file = files.item(1);
        NamedNodeMap fileAttrs = file.getAttributes();
        Node fileAttr = fileAttrs.getNamedItem("URL");
        //change attr value for fasta file path name
        fileAttr.setNodeValue(getTaxonFileNamePath().toString());;
        //update file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
   }
	catch(NullPointerException e) {
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
		JOptionPane.showMessageDialog(null,  "Sorry, choose the correct taxonomy file to parse. Try again.");
	}
	//mutators
	public void setTaxonomyFileNamePath(String string)
	{
		taxonomyFileNamePath = string;
	}
	public void setTaxonFileNamePath(File file) 
	{
		taxonFileNamePath = file;
	}
	public void setTaxonName(File file) 
	{
		String[] pieces;
		String fileName;
		fileName=file.getName();
		pieces = fileName.split("\\.(?=[^\\.]+$)");
		taxonName = pieces[0].toLowerCase();
	}
	//accessors
	public Object getTaxonomyFileNamePath()
	{
		return taxonomyFileNamePath;
	}
	public Object getTaxonFileNamePath()
	{
		return taxonFileNamePath;
	}
	public String getTaxonName()
	{
		return taxonName;
	}
}

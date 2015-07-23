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
public class ParseDefaultParametersXML 
{
	//declare objects
	private Object revSeqAnswer, paramsFileNamePath, taxonomyFileNamePath, fragMassError, fragMassErrorUnits, outputFileNamePath;
	private Object modMass, potModMass, ptmsAnswer, rsPotModMass;
	private Object rsPotModMotif, rsPtms, sAP, ssCleavage, vExp;
	private Object cleaveSite, pcsSSCleavage, removeRed, specSynth, angle;
	//parse method
	public void parseDefaultParametersXML()
	{
		try
        {
        String filepath = getParamsFileNamePath().toString();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filepath);
        //get parent branch
        Node bioml = doc.getElementsByTagName("bioml").item(0);
        //get child nodes, all needed nodes are on the same "branch level"
        NodeList notes = bioml.getChildNodes();
        //go through all nodes
        for(int i=1; i<notes.getLength(); i=2+i)
        {
        	//grab the node
        	Node note = notes.item(i);
        	//get all attributes of the node
        	NamedNodeMap attr = note.getAttributes();
        	//if no attributes in node, go to next node
        	if(attr == null)
        	{
        		continue;
        	}
        	//if there are...
        	else
        	{
        		Node nodeAttr = attr.getNamedItem("label");
        		//if there is no "label" attribute
        		if(nodeAttr == null)
        		{
        			continue;
        		}
        		else
        		//if there is...
        		{
        			//if attribute value is this, enter specified value
        			if("list path, default parameters".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getParamsFileNamePath().toString());
        			}
        			if("list path, taxonomy information".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getTaxonomyFileNamePath().toString());
        			}
        			if("spectrum, fragment monoisotopic mass error".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getFragMassError().toString());
        			}
        			if("spectrum, fragment monoisotopic mass error units".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getFragMassErrorUnits().toString());
        			}
        			if("spectrum, use contrast angle".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getRemoveRed().toString());
        			}
        			if("spectrum, contrast angle".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getAngle().toString());
        			}
        			if("residue, modification mass".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getModMass().toString());
        			}
        			if("residue, potential modification mass".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getPotModMass().toString());
        			}
        			if("protein, cleavage site".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getCleaveSite().toString());
        			}
        			if("protein, cleavage semi".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getPscSSCleavage().toString());
        			}
        			if("protein, use annotations".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getPtmsAnswer().toString());
        			}
        			if("refine, potential modification motif".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getRSPotModMotif().toString());
        			}
        			if("refine, potential modification mass".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getRSPotModMass().toString());
        			}
        			if("refine, use annotations".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getRSPtms().toString());
        			}
        			if("refine, point mutations".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getSAP().toString());
        			}
        			if("refine, cleavage semi".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getSSCleavage().toString());
        			}
        			if("refine, maximum valid expectation value".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getVExp().toString());
        			}
        			if("refine, spectrum synthesis".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getSpecSynth().toString());
        			}
        			if("scoring, include reverse".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getRevSeqAnswer().toString());
        			}
        			if("ouput, path".equals(nodeAttr.getNodeValue()))
        			{
        				note.setTextContent(getOutputFileNamePath().toString());
        			}
        			
        		}         	
        	}
        }
        //update document, make it "real" after just doing a virtual representation 
        //of parsing and modifying
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
   }
	catch(NullPointerException e) {
		e.printStackTrace();
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
		JOptionPane.showMessageDialog(null,  "Sorry, choose the correct parameters file to parse. Try again");
	}
	//mutators-setting values
	public void setRevSeqAnswer(Object answer)
	{
		revSeqAnswer = answer;
	}
	public void setParamsFileNamePath(String string)
	{
		paramsFileNamePath = string;
	}
	public void setTaxonomyFileNamePath(String string)
	{
		taxonomyFileNamePath = string;
	}
	public void setFragMassError(Object mError)
	{
		fragMassError = mError;
	}
	public void setFragMassErrorUnits(Object units)
	{
		fragMassErrorUnits = units;
	}
	public void setModMass(Object mass)
	{
		try
		{
			//getting value only by splitting line by the single space present
			if(mass.equals(""))
				modMass = mass;
			else if(((String) mass).contains(",")) 
				modMass = mass;
			else if(((String) mass).contains(" "))
			{
				String[] pieces;
				pieces = ((String) mass).split(" ");
				modMass = pieces[1];
			}
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Your enter modification mass(es) is in the wrong format. Refer to the README file. Try again");
			e.printStackTrace(); 
		}
	}
	public void setPotModMass(Object mass)
	{
		try
		{
			//getting value only by splitting line by the single space present
			if(mass.equals(""))
				potModMass = mass;
			else if(((String) mass).contains(",")) 
				potModMass = mass;
			else if(((String) mass).contains(" "))
			{
				String[] pieces;
				pieces = ((String) mass).split(" ");
				potModMass = pieces[1];
			}
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Your enter modification mass(es) is in the wrong format. Refer to the README file. Try again");
			e.printStackTrace(); 
		}
	}
	public void setPtmsAnswer(Object ptm)
	{
		ptmsAnswer = ptm;
	}
	public void setRSPotModMass(Object mass)
	{
		try
		{
			//getting value only by splitting line by the single space present
			if(mass.equals(""))
				rsPotModMass = mass;
			else if(((String) mass).contains(",")) 
				rsPotModMass = mass;
			else if(((String) mass).contains(" "))
			{
				String[] pieces;
				pieces = ((String) mass).split(" ");
				rsPotModMass = pieces[1];
			}
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Your enter modification mass(es) is in the wrong format. Refer to the README file. Try again");
			e.printStackTrace(); 
		}
	}
	public void setRSPotModMotif(Object motif)
	{
		try
		{
			//getting value only by splitting line by the single space present
			if(motif.equals(""))
				rsPotModMotif = motif;
			else if(((String) motif).contains(",")) 
				rsPotModMotif = motif;
			else if(((String) motif).contains(" "))
			{
				String[] pieces;
				pieces = ((String) motif).split(" ");
				rsPotModMotif = pieces[1];
			}
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Your enter modification mass(es) is in the wrong format. Refer to the README file. Try again");
			e.printStackTrace(); 
		}
	}
	public void setRSPtms(Object ptm)
	{
		rsPtms = ptm;
	}
	public void setSAP(Object sap)
	{
		sAP = sap;
	}
	public void setSSCleavage(Object cleav)
	{
		ssCleavage = cleav;
	}
	public void setVExp(Object e)
	{
		vExp = e;
	}
	public void setCleaveSite(Object cleav)
	{
		cleaveSite = cleav;
	}
	public void setPcsSSCleavage(Object cleav)
	{
		pcsSSCleavage = cleav;
	}
	public void setRemoveRed(Object red, Object ang)
	{
		//if no no need to enter an angle, but if yes enter an angle
		if("no".equals(red))
		{
			angle = "";
			removeRed = red;
		}
		else
		{
			angle=ang;
			removeRed=red;
		}
	}
	public void setSpecSynth(Object ss)
	{
		specSynth = ss;
	}
	public void setOutputFileNamePath(String output)
	{
		outputFileNamePath = output;
	}
	//accessors-return value
	public Object getRevSeqAnswer()
	{
		return revSeqAnswer;
	}
	public Object getParamsFileNamePath()
	{
		return paramsFileNamePath;
	}
	public Object getTaxonomyFileNamePath()
	{
		return taxonomyFileNamePath;
	}
	public Object getFragMassError()
	{
		return fragMassError;
	}
	public Object getFragMassErrorUnits()
	{
		return fragMassErrorUnits;
	}
	public Object getModMass()
	{
		return modMass;
	}
	public Object getPotModMass()
	{
		return potModMass;
	}
	public Object getPtmsAnswer()
	{
		return ptmsAnswer;
	}
	public Object getRSPotModMass()
	{
		return rsPotModMass;
	}
	public Object getRSPotModMotif()
	{
		return rsPotModMotif;
	}
	public Object getRSPtms()
	{
		return rsPtms;
	}
	public Object getSAP()
	{
		return sAP;
	}
	public Object getSSCleavage()
	{
		return ssCleavage;
	}
	public Object getVExp()
	{
		return vExp;
	}
	public Object getCleaveSite()
	{
		return cleaveSite;
	}
	public Object getPscSSCleavage()
	{
		return pcsSSCleavage;
	}
	public Object getRemoveRed()
	{
		return removeRed;
	}
	public Object getSpecSynth()
	{
		return specSynth;
	}
	public Object getAngle()
	{
		return angle;
	}
	public Object getOutputFileNamePath()
	{
		return outputFileNamePath;
	}
}
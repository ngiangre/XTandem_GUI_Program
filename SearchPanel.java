import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

//if you're looking at the code and you see yellow underneath some of the keywords,
//they are fine, just either need serialization or another way to declare the variables so that
//their "more secure". It's not necessary to correct this, just ignore it 
public class SearchPanel extends JPanel
{   
	//declarations and/or instantiations
	private final int WIDTH=600, HEIGHT=600;
	JButton inputButton, spectraButton, taxonomyButton, taxonButton, paramsButton, done;
	JComboBox revSeqAnswer, fragMassError, fragMassErrorUnits, modMass, potModMass, ptmsAnswer, rsPotModMass;
	JComboBox rsPotModMotif, rsPtms, sAPs, ssCleavage, vExp, cleavSite, pcsSsCleavage, removeRed, specSynth;
	JTextArea description, enterInputFileName, enterTaxonomyFileName, enterParametersFileName, enterOutputFileName, angle;
	private String[] revSeqOptions = { "no", "yes", "only" };
	private String[] fragMassErrorUnitOptions = { "Da", "ppm" };
	private String[] ptmsOptions = { "no", "yes" };
	private String[] rsPtmsOptions = { "no", "yes" };
	private String[] sAPsOptions = { "no", "yes" }, ssCleavageOptions = { "no", "yes" };
	private String[] pcsSsCleavageOptions = { "no", "yes" };
	private String[] removeRedOptions = { "no", "yes" }, specSynthOptions = { "yes", "no" };
	
	//panel's custom color
	Color seaBlue = new Color(0,155,180);
	
	//constructor
	public SearchPanel(ParseInputXML a, ParseTaxonomyXML b, ParseDefaultParametersXML c) throws IOException
	{   //SearchPanel specifications
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(seaBlue);
		//SearchPanel layout
		setLayout(new BorderLayout());
		//Component for North panel: description of the program
		description = new JTextArea("Welcome! Choose input files, parameters and click done to modify files for X!Tandem search. Refer to README.txt for more information and instruction");
		//description settings
		description.setEnabled(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setBackground(seaBlue);
		description.setDisabledTextColor(Color.BLACK);	
		//south panel component: Done button to pass on parameters for XML parsing
		done = new JButton("Done");
		//add components to SearchPanel
		add(description, BorderLayout.NORTH);
		add(done, BorderLayout.SOUTH);
		add(new EastParameters(), BorderLayout.EAST);
		//pass parser objects to West section of panel for proper parameter pass
		//on for XML parser
		add(new WestParameters(a, b, c), BorderLayout.WEST);
	}//end constructor
	private class WestParameters extends JPanel
	{	
		//declaring parsing objects
		ParseInputXML parseInput;
		ParseTaxonomyXML parseTaxonomy;
		ParseDefaultParametersXML parseParams;
		//constructor
		public WestParameters(ParseInputXML a, ParseTaxonomyXML b, ParseDefaultParametersXML d)
		{   
			//instantiating parsing objects
			parseInput = a;
			parseTaxonomy = b;
			parseParams = d;
			//west panel's layout, GridBagLayout with needed constraints for 
			//proper positioning of components onto panel
			setLayout(new GridBagLayout());
			setPreferredSize(new Dimension(300,260));
			GridBagConstraints c = new GridBagConstraints();
			setBackground(seaBlue);
			//Components for Input file
			JLabel inputLabel = new JLabel("1. Input File", SwingConstants.CENTER);
			
			JTextArea chooseInput = new JTextArea("a. Choose Input File and enter new file name");
			chooseInput.setEnabled(false);
			chooseInput.setLineWrap(true);
			chooseInput.setWrapStyleWord(true);
			chooseInput.setDisabledTextColor(Color.BLACK);
			chooseInput.setBackground(seaBlue);
			
			inputButton = new JButton("input");
			
			enterInputFileName = new JTextArea("input_new.xml");
			enterInputFileName.setEnabled(true);
			enterInputFileName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			//Components for Spectra file
			JLabel spectraLabel = new JLabel("2. Spectra File", SwingConstants.CENTER);
			JLabel chooseSpectra = new JLabel("a. Choose Spectra File");
			spectraButton = new JButton("spectra");
			
			//Components for Taxon file
			JLabel taxonLabel = new JLabel("3. Taxon", SwingConstants.CENTER);
			
			JTextArea chooseTaxonomy = new JTextArea("a. Choose taxonomy file and enter new file name");
			chooseTaxonomy.setEnabled(false);
			chooseTaxonomy.setLineWrap(true);
			chooseTaxonomy.setWrapStyleWord(true);
			chooseTaxonomy.setDisabledTextColor(Color.BLACK);
			chooseTaxonomy.setBackground(seaBlue);
			
			taxonomyButton = new JButton("taxonomy");
			
			enterTaxonomyFileName = new JTextArea("taxonomy_new.xml");
			enterTaxonomyFileName.setEnabled(true);
			enterTaxonomyFileName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			JTextArea chooseTaxon = new JTextArea("b. Choose Taxon sequence file");
			chooseTaxon.setEnabled(false);
			chooseTaxon.setLineWrap(true);
			chooseTaxon.setWrapStyleWord(true);
			chooseTaxon.setDisabledTextColor(Color.BLACK);
			chooseTaxon.setBackground(seaBlue);
			
			taxonButton = new JButton("Taxon File");
			
			JLabel revSeqLabel = new JLabel("c. include reversed sequences?");
			revSeqAnswer = new JComboBox( revSeqOptions );
			
			//Components for Parameter file
			JLabel paramaterFileLabel = new JLabel("4. Parameters File", SwingConstants.CENTER);
			
			JTextArea chooseParamsLabel = new JTextArea("a. Choose Parameter File and enter new file name");
			chooseParamsLabel.setEnabled(false);
			chooseParamsLabel.setLineWrap(true);
			chooseParamsLabel.setWrapStyleWord(true);
			chooseParamsLabel.setDisabledTextColor(Color.BLACK);
			chooseParamsLabel.setBackground(seaBlue);
			
			paramsButton = new JButton("Parameters");
			
			enterParametersFileName = new JTextArea("parameters_new.xml");
			enterParametersFileName.setEnabled(true);
			enterParametersFileName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			//Components for Output File
			JLabel outputFileLabel = new JLabel("5. Output File", SwingConstants.CENTER);
			
			JTextArea chooseOutputFileName = new JTextArea("a. Set output file name/path");
			chooseOutputFileName.setEnabled(false);
			chooseOutputFileName.setLineWrap(true);
			chooseOutputFileName.setWrapStyleWord(true);
			chooseOutputFileName.setDisabledTextColor(Color.BLACK);
			chooseOutputFileName.setBackground(seaBlue);
			
			enterOutputFileName = new JTextArea("output_new.xml");
			enterOutputFileName.setEditable(true);
			enterOutputFileName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			//adding Input components to panel
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 0;
			add(inputLabel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 1;
			add(chooseInput, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 2;
			add(inputButton, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 3;
			add(enterInputFileName, c);

			//adding Spectra components to panel
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 4;
			add(spectraLabel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 5;
			add(chooseSpectra, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 6;
			add(spectraButton, c);
			
			//adding Taxonomy and Taxon Components to panel
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 7;
			add(taxonLabel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 8;
			add(chooseTaxonomy, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 9;
			add(taxonomyButton, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 10;
			add(enterTaxonomyFileName, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 11;
			add(chooseTaxon, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 12;
			add(taxonButton, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 13;
			
			add(revSeqLabel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 14;
			add(revSeqAnswer, c);
			
			//adding Params Component to Panel
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 15;
			add(paramaterFileLabel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 16;
			add(chooseParamsLabel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 17;
			add(paramsButton, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 18;
			add(enterParametersFileName, c);
			
			//adding output components to panel
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 19;
			add(outputFileLabel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 20;
			add(chooseOutputFileName, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 21;
			add(enterOutputFileName, c);
			
            //indicating ActionListeners for when buttons are pressed
			ButtonHandler handler = new ButtonHandler();
			inputButton.addActionListener(handler);
			spectraButton.addActionListener(handler);
			taxonomyButton.addActionListener(handler);
			taxonButton.addActionListener(handler);
			paramsButton.addActionListener(handler);
			done.addActionListener(handler);
		}//end Parameters()
	//class for dealing with buttons when pressed
	private class ButtonHandler implements ActionListener
	{   
		//file declarations
		File inputFile, spectraFile, taxonomyFile, taxonFile, paramsFile;
		//mandatory method for listening to actions performed
		public void actionPerformed(ActionEvent e)
		{   //if this button is pressed
			if(e.getSource() == inputButton)
			{
				//get file selected
				inputFile = getFileorDirectory();
				//while there are lines in the file
				while(inputFile != null)
				{
					//make sure it has xml extension and if not warn them and
					//prompt them again. If it's right, tell them what they have
					if("xml".equals(isExt(inputFile)))
					{
						JOptionPane.showMessageDialog(null,  "You chose " + inputFile.getName());
						break;
					}
					else
					{
						JOptionPane.showMessageDialog(null,  "You need to choose a .xml file");
						inputFile = getFileorDirectory();
					}
				}
			}
			if(e.getSource() == spectraButton)
			{
				spectraFile = getFileorDirectory();
				while(spectraFile != null)
				{
					if("mgf".equals(isExt(spectraFile))||"gaml".equals(isExt(spectraFile))||"dta".equals(isExt(spectraFile))||"pkl".equals(isExt(spectraFile))||"mzdata".equals(isExt(spectraFile))||"mzxml".equals(isExt(spectraFile)))
					{
						JOptionPane.showMessageDialog(null,  "You chose " + spectraFile.getName());
						break;
					}
					else
					{
						JOptionPane.showMessageDialog(null,  "X!Tandem only takes spectra files with cmn, dta, pkl, mgf, mzData (v.1.05) oe mzXML (v.2.0) extensions.\nYou can manually change the name of your spectra file, from that directory, by entering,\ne.g. mv filename.txt filename.mgf\nThis won't change the file contents, just the name. Use with caution");
						spectraFile = getFileorDirectory();
					}
				}
			}
			else if(e.getSource() == taxonomyButton)
			{
				taxonomyFile = getFileorDirectory();
				while(taxonomyFile != null)
				{
					if("xml".equals(isExt(taxonomyFile)))
					{
						JOptionPane.showMessageDialog(null, "You chose " + taxonomyFile.getName());
						break;
					}
					else
					{
						JOptionPane.showMessageDialog(null,  "You need to choose a .xml file");
						taxonomyFile = getFileorDirectory();
					}
				}
			}
			else if (e.getSource() == taxonButton)
			{
				taxonFile = getFileorDirectory();
				while(taxonFile != null)
				{
					if("fasta".equals(isExt(taxonFile)))
					{
						JOptionPane.showMessageDialog(null, "You chose " + taxonFile.getName());
						break;
					}
					else
					{
						JOptionPane.showMessageDialog(null,  "You need to choose a .fasta file");
						taxonFile = getFileorDirectory();
					}
				}
			}
			else if(e.getSource() == paramsButton)
			{
				paramsFile = getFileorDirectory();
				while(paramsFile != null)
				{
					if("xml".equals(isExt(paramsFile)))
					{
						JOptionPane.showMessageDialog(null, "You chose " + paramsFile.getName());
						break;
					}
					else
					{
						JOptionPane.showMessageDialog(null,  "You need to choose a .xml file");
						paramsFile = getFileorDirectory();
					}
				}
			}
			else if(e.getSource() == done)
			{
				//make sure output name has right file extension.
				//If not, warn them and prompt them again
				//if it does...
				if("xml".equals(isExt(enterOutputFileName))&&"xml".equals(isExt(enterInputFileName))&&"xml".equals(isExt(enterTaxonomyFileName))&&"xml".equals(isExt(enterParametersFileName)))
				{
					
					//run command line arguments to copy files chosen to new files specified
					Process comLineInput, comLineTaxonomy, comLineParameters;
					
					try 
					{
						comLineInput = Runtime.getRuntime().exec("cp " + inputFile.toString() + " " + getPath(inputFile) + "/" + enterInputFileName.getText());
						comLineTaxonomy = Runtime.getRuntime().exec("cp " + taxonomyFile.toString() + " " +  getPath(taxonomyFile) + "/" + enterTaxonomyFileName.getText());
						comLineParameters = Runtime.getRuntime().exec("cp " + paramsFile.toString() + " " +  getPath(paramsFile) + "/" + enterParametersFileName.getText());
					} 
					catch (Exception e1) 
					{
						JOptionPane.showMessageDialog(null,  "You didn't specify an input file. Try again.");
						e1.printStackTrace();
					}
					
					//pass parameters and new file names (now copied from templates chosen and can now parse them) selected to XML parser objects 
					//and methods from their classes
					parseInput.setInputFileNamePath(getPath(inputFile) + "/" + enterInputFileName.getText());
					parseInput.setParamsFileNamePath(getPath(paramsFile) + "/" + enterParametersFileName.getText());
					parseInput.setTaxonomyFileNamePath(getPath(taxonomyFile) + "/" + enterTaxonomyFileName.getText());
					parseInput.setTaxonName(taxonFile);
					parseInput.setSpectraFileName(spectraFile);
					parseInput.setOutputFileNamePath(getPath(inputFile) + "/" + enterOutputFileName.getText());
					
					parseTaxonomy.setTaxonomyFileNamePath(getPath(taxonomyFile) + "/" + enterTaxonomyFileName.getText());
					parseTaxonomy.setTaxonFileNamePath(taxonFile);
					parseTaxonomy.setTaxonName(taxonFile);
					
					parseParams.setRevSeqAnswer(revSeqAnswer.getSelectedItem());
					parseParams.setParamsFileNamePath(getPath(paramsFile) + "/" + enterParametersFileName.getText());
					parseParams.setTaxonomyFileNamePath(getPath(taxonomyFile) + "/" + enterTaxonomyFileName.getText());
					parseParams.setFragMassError(fragMassError.getSelectedItem());
					parseParams.setFragMassErrorUnits(fragMassErrorUnits.getSelectedItem());
					parseParams.setModMass(modMass.getSelectedItem());
					parseParams.setPotModMass(potModMass.getSelectedItem());
					parseParams.setPtmsAnswer(ptmsAnswer.getSelectedItem());
					parseParams.setRSPotModMass(rsPotModMass.getSelectedItem());
					parseParams.setRSPotModMotif(rsPotModMotif.getSelectedItem());
					parseParams.setRSPtms(rsPtms.getSelectedItem());
					parseParams.setSAP(sAPs.getSelectedItem());
					parseParams.setSSCleavage(ssCleavage.getSelectedItem());
					parseParams.setVExp(vExp.getSelectedItem());
					parseParams.setCleaveSite(cleavSite.getSelectedItem());
					parseParams.setPcsSSCleavage(pcsSsCleavage.getSelectedItem());
					parseParams.setRemoveRed(removeRed.getSelectedItem(), angle.getText());
					parseParams.setSpecSynth(specSynth.getSelectedItem());
					parseParams.setOutputFileNamePath(getPath(inputFile) + "/" + enterOutputFileName.getText());
					
					//finally, after just setting the values in those class files,
					//actually do the parsing and modifying process
					parseInput.parseInputXML();
					parseTaxonomy.parseTaxonomyXML();
					parseParams.parseDefaultParametersXML();
					
					//ask if you want to run tandem
					//final JOptionPane tandemOption = new JOptionPane("Would you like to run tandem at this time?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
					//run tandem-it's not working, have to fix
					Process tandem;
					try {
						tandem = Runtime.getRuntime().exec("echo ./tandem " + parseInput.getInputFileNamePath());
						JOptionPane.showMessageDialog(null, "All input files have been created and modified.\nIf you were prompted with a try again statement, enter correct files and parameters again to generate correct input files.\nIf you were not already prompted, you are now ready to run tandem!\nIf you are on a linux machine, you can run it.\nRefer to the correct command in the README.txt.\nIf not, from your linux or Linux virtual machine,\ntype in the command line, \"./tandem " + enterInputFileName.getText() + "\"");
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Can't run tandem because the file doesn't exist in the current directory");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,  "You need to specify each file as .xml");
				}
			}	
		}//end actionPerformed
		//method used for retrieving file selected from filechoosers
		private File getFileorDirectory()
		{
			//declaring and instantiating JFileChooser
			JFileChooser fileChooser= new JFileChooser();
			//JFileChooser working
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//stored as an int? ok, it works
			int result = fileChooser.showOpenDialog(null);
			//if cancel, just return to panel
			if(result==JFileChooser.CANCEL_OPTION) 
				fileChooser.cancelSelection();
			//grabs selected file
			File fileName = fileChooser.getSelectedFile();
			//returns selected file
			return fileName;
		}//end getFileorDirectory()
		//returning the actual extension
		private String isExt(File file)
		{
			String fileName = file.getName();
			String [] pieces;
			pieces = fileName.split("\\.(?=[^\\.]+$)");
			return pieces[1];
		}
		//overloaded from above
		private String isExt(JTextArea area)
		{
			String fileName = area.getText();
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
	}//end ButtonHandler class
	}//end WestParameters class
	private class EastParameters extends JPanel
	{
		public EastParameters() throws IOException
		{	//East panel's layout
			setLayout(new GridBagLayout());
			setPreferredSize(new Dimension(300,260));
			GridBagConstraints d = new GridBagConstraints();
			setBackground(seaBlue);
			
			//Components for East Parameters
			//Components for frag Mass Error
			JLabel fragMassErrorLabel = new JLabel("A. Fragment Mass Error:");		
			fragMassError = new JComboBox(getFragMasses());
			fragMassErrorUnits = new JComboBox(fragMassErrorUnitOptions);
			
			//Components for Residue Modifications
			JLabel resMassLabel = new JLabel("B. Residue Modifications");
			JLabel modMassLabel = new JLabel("I. Mass Modifications:");
			modMass = new JComboBox(getResidueMassModifications());
			modMass.setEditable(true);
			JLabel potModMassLabel = new JLabel("II. Potential Mass Mods.:");
			potModMass = new JComboBox(getResiduePotentialMassModifications());
			potModMass.setEditable(true);
			JLabel ptmsLabel = new JLabel("III. Check for known PTMs:");
			ptmsAnswer = new JComboBox(ptmsOptions);
			
			//Components for Refinement Specifications
			JLabel refSpecLabel = new JLabel("C. Refinement Specifications");
			JLabel rsPotModMassLabel = new JLabel("I. Potential Mod. Mass:");
			rsPotModMass = new JComboBox(getRefinePotentialMassModifications());
			rsPotModMass.setEditable(true);
			JLabel rsPotModMotifLabel = new JLabel("II. Potential Mod. Motif:");
			rsPotModMotif = new JComboBox(getRefineMotifs());
			rsPotModMotif.setEditable(true);
			JLabel rsPtmsLabel = new JLabel("III. check for known PTMs: ");
			rsPtms = new JComboBox(rsPtmsOptions);
			JLabel sAPsLabel = new JLabel("IV. sAPs:");
			sAPs = new JComboBox(sAPsOptions);
			JLabel ssCleavageLabel = new JLabel("V. Semi-Style Cleavage:");
			ssCleavage = new JComboBox(ssCleavageOptions);
			JLabel vExpLabel = new JLabel("VI. Valid Expectation Value:");
			vExp = new JComboBox(getVExpValues());
			
			//Components for Prot. Cleav. Specif'n/Spec. Cond.
			JLabel protCleavageSpef = new JLabel("D. Prot. Cleav. Specif'n./Spec. Cond.");
			JLabel cleavSiteLabel = new JLabel("I. Cleave Site: ");
			cleavSite = new JComboBox(getCleavageSite());
			JLabel pcsSsCleavageLabel = new JLabel("II. Semi-style Cleavage:");
			pcsSsCleavage = new JComboBox(pcsSsCleavageOptions);
			
			JLabel removeRedLabel = new JLabel("III. remove redundant: ");
			removeRed = new JComboBox(removeRedOptions);
			JLabel angleLabel = new JLabel("a. If no, choose angle: ");
			angle = new JTextArea("90");
			angle.setEnabled(true);
			angle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			JLabel specSynthLabel = new JLabel("IV. Spectrum Synthesis: ");
			specSynth = new JComboBox(specSynthOptions);
			
			//adding Search Parameters Components
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 0;
			add(fragMassErrorLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 0;
			add(fragMassError, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 1;
			add(fragMassErrorUnits, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 2;
			add(resMassLabel, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 3;
			add(modMassLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 4;
			add(modMass, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 5;
			add(potModMassLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 6;
			add(potModMass, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 7;
			add(ptmsLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 7;
			add( ptmsAnswer, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.gridx = 0;
			d.gridy = 8;
			add(refSpecLabel, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 9;
			add(rsPotModMassLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 10;
			add(rsPotModMass, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 11;
			add(rsPotModMotifLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 12;
			add(rsPotModMotif, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 13;
			add(rsPtmsLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 13;
			add(rsPtms, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 14;
			add(sAPsLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 14;
			add(sAPs, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 15;
			add(ssCleavageLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 15;
			add(ssCleavage, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 16;
			add(vExpLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 16;
			add(vExp, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 17;
			add(protCleavageSpef, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 18;
			add(cleavSiteLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 18;
			add(cleavSite, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 19;
			add(pcsSsCleavageLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 19;
			add(pcsSsCleavage, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 20;
			add(removeRedLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 20;
			add(removeRed, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 21;
			add(angleLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 21;
			add(angle, d);
			d.fill = GridBagConstraints.HORIZONTAL;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 22;
			add(specSynthLabel, d);
			d.fill = GridBagConstraints.NONE;
			d.anchor = GridBagConstraints.LINE_END;
			d.weightx = 2;
			d.weighty = 1;
			d.gridx = 0;
			d.gridy = 22;
			add(specSynth, d);
		}//end EastParameters constructor
		//retrieve lines from Modifications file containing values, so that exact lines are shown in drop down box
		String[] getResidueMassModifications() throws IOException
		{
			String[] lines;
			//inspired from http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
			
			LineNumberReader  lnr = new LineNumberReader(new FileReader("residuemassmodifications.txt"));
			lnr.skip(Long.MAX_VALUE);
			lines = new String[lnr.getLineNumber()];
			
			
			BufferedReader b = new BufferedReader
					(new FileReader("residuemassmodifications.txt"));
			String line = null;
			int i = 0;
			while((line = b.readLine()) != null)
			{
				lines[i] = line;
				i++;
			}
			b.close();
			lnr.close();
			return lines;
		}
		//retrieve lines from Modifications file containing values, so that exact lines are shown in drop down box
		//different methods because different txt files are used!
		String[] getResiduePotentialMassModifications() throws IOException
		{
			String[] lines;
			//inspired from http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
			
			LineNumberReader  lnr = new LineNumberReader(new FileReader("residuepotentialmassmodifications.txt"));
			lnr.skip(Long.MAX_VALUE);
			lines = new String[lnr.getLineNumber()];
			
			
			BufferedReader b = new BufferedReader
					(new FileReader("residuepotentialmassmodifications.txt"));
			String line = null;
			int i = 0;
			while((line = b.readLine()) != null)
			{
				lines[i] = line;
				i++;
			}
			b.close();
			lnr.close();
			return lines;
		}
		//retrieve lines from Modifications file containing values, so that exact lines are shown in drop down box
		String[] getRefinePotentialMassModifications() throws IOException
		{
			String[] lines;
			//inspired from http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
			
			LineNumberReader  lnr = new LineNumberReader(new FileReader("refinepotentialmassmodifications.txt"));
			lnr.skip(Long.MAX_VALUE);
			lines = new String[lnr.getLineNumber()];
			
			
			BufferedReader b = new BufferedReader
					(new FileReader("refinepotentialmassmodifications.txt"));
			String line = null;
			int i = 0;
			while((line = b.readLine()) != null)
			{
				lines[i] = line;
				i++;
			}
			b.close();
			lnr.close();
			return lines;
		}
		//retrieve lines from Motifs file containing values
		String[] getRefineMotifs() throws IOException
		{
			String[] lines;
			//inspired from http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
			
			LineNumberReader  lnr = new LineNumberReader(new FileReader("refinemotifs.txt"));
			lnr.skip(Long.MAX_VALUE);
			lines = new String[lnr.getLineNumber()];
			
			
			BufferedReader b = new BufferedReader
					(new FileReader("refinemotifs.txt"));
			String line = null;
			int i = 0;
			while((line = b.readLine()) != null)
			{
				lines[i] = line;
				i++;
			}
			b.close();
			lnr.close();
			return lines;
		}
		//retrieve lines from Cleavage Site file containing values
		String[] getCleavageSite() throws IOException
		{
			String[] lines;
			//inspired from http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
			
			LineNumberReader  lnr = new LineNumberReader(new FileReader("CleavageSite.txt"));
			lnr.skip(Long.MAX_VALUE);
			lines = new String[lnr.getLineNumber()];
			
			
			BufferedReader b = new BufferedReader
					(new FileReader("CleavageSite.txt"));
			String line = null;
			int i = 0;
			while((line = b.readLine()) != null)
			{
				lines[i] = line;
				i++;
			}
			b.close();
			lnr.close();
			return lines;
		}
		//retrieve lines from ValidExpectations file containing values
		String[] getVExpValues() throws IOException
		{
			String[] lines;
			//inspired from http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
			
			LineNumberReader  lnr = new LineNumberReader(new FileReader("ValidExpectationValue.txt"));
			lnr.skip(Long.MAX_VALUE);
			lines = new String[lnr.getLineNumber()];
			
			
			BufferedReader b = new BufferedReader
					(new FileReader("ValidExpectationValue.txt"));
			String line = null;
			int i = 0;
			while((line = b.readLine()) != null)
			{
				lines[i] = line;
				i++;
			}
			b.close();
			lnr.close();
			return lines;
		}
		//retrieve lines from FragMasses file containing values
		String[] getFragMasses() throws IOException
		{
			String[] lines;
			//inspired from http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
			
			LineNumberReader  lnr = new LineNumberReader(new FileReader("FragMasses.txt"));
			lnr.skip(Long.MAX_VALUE);
			lines = new String[lnr.getLineNumber()];
			
			
			BufferedReader b = new BufferedReader
					(new FileReader("FragMasses.txt"));
			String line = null;
			int i = 0;
			while((line = b.readLine()) != null)
			{
				lines[i] = line;
				i++;
			}
			b.close();
			lnr.close();
			return lines;
		}
	}//end EastParameters class
}//end SearchPanel class
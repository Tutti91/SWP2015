package webot.logic;

import java.io.File;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import webot.WeBot;
import webot.xmi_to_java.BotLanguageLoader;
import webot.xtext_to_xmi.Converter;

/**
 * Class to mangage application and logic interactions
 * 
 */
public class LogicManager implements InterfaceLogicManager 
{

	private EList<EObject> instructionList;
	private LogicExecutor logicExecutor;

	
	/**
	 * Constructor with assigned WeBot
	 * @param weBot
	 */
	public LogicManager(WeBot weBot) 
	{
		this.reset();
		this.logicExecutor = new LogicExecutor(weBot);
	}

	/**
	 * debugg method of the developer
	 * removeable after complition
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		File f = new File("C:/Users/Michael/runtime-EclipseApplication/BlaTester/src/angriff.bla");
		
		InterfaceLogicManager llm = new LogicManager(null);
		llm.newLogicChosen(f);
		try {
			llm.playGameLogic();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see webot.InterfaceLogicManager#newLogicChosen(java.io.File)
	 */
	public boolean newLogicChosen(File origin)
	{
		
		
		try
		{
		String filePath = createSuiteablePath(origin) ;
		String xmiFilePath = "l_xmi.xmi";
		
		
		Converter con = new Converter();
		con.convert_to_xmi(filePath, xmiFilePath);
		
		
		instructionList = new BotLanguageLoader().loadBotLanguage(xmiFilePath);
		
		}
		catch(Exception e)
		{
			System.out.println("error");
			e.printStackTrace(System.out);
			
			return false;
		}
		
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see webot.InterfaceLogicManager#playGameLogic()
	 */
	public void playGameLogic() 
	{
		Thread execut = new Thread(new Runnable() 
		{
			
			public void run() 
			{
				logicExecutor.executeLogic(instructionList);
			}
		});
		
		execut.start();
	}

	
	/**
	 * Converts the File (origin) data into a String and prepares the absolut path
	 * to be valid for the xtext_to_xmi.Converter Class
	 * 
	 * @param origin The File holding the user input
	 * @return the absolut path valid for the xtext_to_xmi.Converter Class
	 */
	private String createSuiteablePath(File origin) 
	{
		String path = origin.getAbsolutePath();
		
		while(!path.startsWith("\\"))
		{
			path = path.substring(1);
		}
		
		return path;
	}

	
	/**
	 * Reset the instructions
	 */
	public void reset() 
	{		
		instructionList = ECollections.newBasicEList();
	}

	/**
	 * Stopping the Bot
	 */
	public void stopPlaying() 
	{
		logicExecutor.abort();
	}

}

package networking;

import java.io.IOException;

import game.GameResult;

/**
 * Collect data from Internet
 * @author mutant
 */
public interface INetCollector {

	/**
	 * Read data from given URL  
	 * @param url
	 * @throws IOException
	 */
	abstract public void readURL(String url) throws IOException;

	/**
	 * Parse next game data from URL 
	 * @return GameResult, null if no data (more) available
	 */
	abstract public GameResult getResult();
	
	/**
	 * Jump to begin of read document 
	 */
	abstract public void rewind();

}

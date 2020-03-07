/**
 * 
 */
package com.issue.output;

import java.io.FileWriter;
import java.io.IOException;

import com.issue.contract.Dao2Output;

/**
 * The Class OutputCreator.
 *
 * @author benito
 */
public class OutputCreator {

	/** The dao 2 output. */
	private Dao2Output dao2Output;

	/**
	 * Instantiates a new output creator.
	 *
	 * @param dao2Output the dao 2 output
	 */
	public OutputCreator(Dao2Output dao2Output) {
		this.dao2Output = dao2Output;
	}

	/**
	 * Creates the output file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createOutputFile() throws IOException {
		if (dao2Output.provideOutputFileName() != null)
			try (FileWriter fileWriter = new FileWriter(dao2Output.provideOutputFileName())) {
				fileWriter.write(dao2Output.provideContent());
			}
		else
			throw new IllegalArgumentException();
	}
}

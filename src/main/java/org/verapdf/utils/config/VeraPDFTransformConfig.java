package org.verapdf.utils.config;

/**
 * @author Evgeniy Muravitskiy
 */
public class VeraPDFTransformConfig {

	private final String inputPath;
	private final boolean url;
	private final String outputPath;

	public VeraPDFTransformConfig(String inputPath, boolean url, String outputPath) {
		this.inputPath = inputPath;
		this.url = url;
		this.outputPath = outputPath;
	}

	public String getInputPath() {
		return inputPath;
	}

	public boolean isUrl() {
		return url;
	}

	public String getOutputPath() {
		return outputPath;
	}

}

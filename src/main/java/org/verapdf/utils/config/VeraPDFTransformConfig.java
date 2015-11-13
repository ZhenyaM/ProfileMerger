package org.verapdf.utils.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
public class VeraPDFTransformConfig {

	private final List<String> inputPath;
	private final List<String> outputPath;
	private final String version;

	public VeraPDFTransformConfig(List<String> inputPath, List<String> outputPath, String version) {
		this.inputPath = inputPath;
		this.outputPath = this.getOutputPath(inputPath, outputPath);
		this.version = version;
	}

	private List<String> getOutputPath(List<String> inputPath, List<String> outputPath) {
		if (inputPath.size() == outputPath.size()) {
			return outputPath;
		} else if (outputPath.size() == 0) {
			List<String> list = new ArrayList<>(inputPath.size());
			for (String path : inputPath) {
				String outPath = path.substring(0, path.length() - 4) + ".html";
				list.add(outPath);
			}
			return list;
		} else {
			throw new IllegalArgumentException(
					"Output paths must be empty or number of output paths must equals to input paths count.");
		}
	}

	public List<String> getInputPath() {
		return inputPath;
	}

	public List<String> getOutputPath() {
		return outputPath;
	}

	public String getVersion() {
		return version;
	}
}

package org.verapdf.utils.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
public class VeraPDFMergerConfig {

	private Input inputPath;

	private String outputPath;

	private List<String> excluded;

	private String model;

	private String namespace;

	private String nameTagValue;

	private String descriptionTagValue;

	private String creatorTagValue;

	private VeraPDFMergerConfig(Input inputPath, String outputPath, List<String> excluded,
							   String model, String namespace, String nameTagValue,
							   String descriptionTagValue, String creatorTagValue) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		this.excluded = excluded;
		this.model = model;
		this.namespace = namespace;
		this.nameTagValue = nameTagValue;
		this.descriptionTagValue = descriptionTagValue;
		this.creatorTagValue = creatorTagValue;
	}

	public Input getInputPath() {
		return inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public List<String> getExcluded() {
		return excluded;
	}

	public String getModel() {
		return model;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getNameTagValue() {
		return nameTagValue;
	}

	public String getDescriptionTagValue() {
		return descriptionTagValue;
	}

	public String getCreatorTagValue() {
		return creatorTagValue;
	}

	public static class Builder {

		private Input inputPath;
		private String outputPath;
		private List<String> excluded;
		private String model;
		private String namespace;
		private String nameTagValue;
		private String descriptionTagValue;
		private String creatorTagValue;

		public Builder() {

		}

		public Builder inputPath(String inputPath, boolean isURL) {
			this.inputPath = new Input(inputPath, isURL);
			return this;
		}

		public Builder outputPath(String outputPath) {
			this.outputPath = outputPath;
			return this;
		}

		public Builder excluded(List<String> excluded) {
			this.excluded = excluded;
			return this;
		}

		public Builder model(String model) {
			this.model = model;
			return this;
		}

		public Builder namespace(String namespace) {
			this.namespace = namespace;
			return this;
		}

		public Builder nameTagValue(String nameTagValue) {
			this.nameTagValue = nameTagValue;
			return this;
		}

		public Builder descriptionTagValue(String descriptionTagValue) {
			this.descriptionTagValue = descriptionTagValue;
			return this;
		}

		public Builder creatorTagValue(String creatorTagValue) {
			this.creatorTagValue = creatorTagValue;
			return this;
		}

		public VeraPDFMergerConfig build() {
			return new VeraPDFMergerConfig(inputPath, outputPath, excluded, model,
					namespace, nameTagValue, descriptionTagValue, creatorTagValue);
		}
	}
}

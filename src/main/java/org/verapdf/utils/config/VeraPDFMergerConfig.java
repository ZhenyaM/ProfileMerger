package org.verapdf.utils.config;

import java.util.List;
import java.util.Locale;

/**
 * @author Evgeniy Muravitskiy
 */
public class VeraPDFMergerConfig {

	private final List<String> inputPath;
	private final String outputPath;
	private final List<String> excluded;

	private final String nameTagValue;
	private final String descriptionTagValue;
	private final String creatorTagValue;
	private final String flavour;

	private final String version;
	private final boolean saveDuplicated;
	private final boolean pretty;

	private VeraPDFMergerConfig(List<String> inputPath, String outputPath, List<String> excluded,
								String nameTagValue, String descriptionTagValue, String creatorTagValue,
								String flavour, String version, boolean saveDuplicated, boolean pretty) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		this.excluded = excluded;
		this.nameTagValue = nameTagValue;
		this.descriptionTagValue = descriptionTagValue;
		this.creatorTagValue = creatorTagValue;
		this.flavour = flavour;
		this.version = version.toUpperCase(Locale.US);
		this.saveDuplicated = saveDuplicated;
		this.pretty = pretty;
	}

	public List<String> getInputPath() {
		return inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public List<String> getExcluded() {
		return excluded;
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

	public String getFlavour() {
		return this.flavour;
	}

	public boolean isPretty() {
		return this.pretty;
	}

	public boolean isSaveDuplicated() {
		return this.saveDuplicated;
	}

	public String getVersion() {
		return this.version;
	}

	public static class Builder {

		private List<String> inputPath;
		private String outputPath;
		private List<String> excluded;

		private String nameTagValue;
		private String descriptionTagValue;
		private String creatorTagValue;
		private String flavour;

		private String version;
		private boolean saveDuplicated;
		private boolean pretty;

		public Builder inputPath(List<String> inputPath) {
			this.inputPath = inputPath;
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

		public Builder flavour(String flavour) {
			this.flavour = flavour;
			return this;
		}

		public Builder version(String version) {
			this.version = version;
			return this;
		}

		public Builder saveDuplicated(boolean saveDuplicated) {
			this.saveDuplicated = saveDuplicated;
			return this;
		}

		public Builder pretty(boolean pretty) {
			this.pretty = pretty;
			return this;
		}

		public VeraPDFMergerConfig build() {
			return new VeraPDFMergerConfig(inputPath, outputPath, excluded, nameTagValue,
					descriptionTagValue, creatorTagValue, flavour, version, saveDuplicated, pretty);
		}
	}
}

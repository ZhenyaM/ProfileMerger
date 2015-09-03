package org.verapdf.utils.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
@Parameters(commandNames = "verapdf")
public class CommandProfileMerger extends Command{

	public static final String DEFAULT_OUTPUT_PATH = "result.xml";
	public static final String DEFAULT_MODEL = "org.verapdf.model.PDFA1a";
	public static final String DEFAULT_NAMESPACE = "http://www.verapdf.org/ValidationProfile";
	public static final String DEFAULT_NAME_TAG_VALUE = "PDF/A-1B validation profile";
	public static final String DEFAULT_DESCRIPTION_TAG_VALUE =
			"Validation rules against ISO 19005-1:2005, Cor.1:2007 and Cor.2:2011";
	public static final String DEFAULT_CREATOR_TAG_VALUE = "veraPDF Consortium";

	@Parameter(names = "--merge")
	private boolean merge;

	@Parameter(names = "--transform-to-html")
	private boolean transformToHTML;

	@Parameter(names = "--input", required = true,
			description = "path to xml profiles, which will be merged. just 1 argument - path to file or directory")
	private String inputPath;

	// TODO : add description
	@Parameter(names = "--url")
	private boolean url;

	@Parameter(names = "--output", description = "path to result xml profile. just 1 argument - path to file")
	private String outputPath = DEFAULT_OUTPUT_PATH;

	@Parameter(names = "--exclude", variableArity = true)
	private List<String> exclude = new ArrayList<>();

	@Parameter(names = "--model", description = "model type for validation. just 1 argument - string")
	private String model = DEFAULT_MODEL;

	// TODO : add description
	@Parameter(names = "--namespace")
	private String namespace = DEFAULT_NAMESPACE;

	@Parameter(names = "--name", description = "value of name tag for result profile. just 1 argument - string")
	private String nameTagValue = DEFAULT_NAME_TAG_VALUE;

	@Parameter(names = "--description",
			description = "value of description tag for result profile. just 1 argument - string")
	private String descriptionTagValue = DEFAULT_DESCRIPTION_TAG_VALUE;

	@Parameter(names = "--creator", description = "value of creator tag for result profile. just 1 argument - string")
	private String creatorTagValue = DEFAULT_CREATOR_TAG_VALUE;

	@Parameter(names = "--help", description = "print list of all supported commands", help = true)
	private boolean help;

	public boolean isMerge() {
		return merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

	public boolean isTransformToHTML() {
		return transformToHTML;
	}

	public void setTransformToHTML(boolean transformToHTML) {
		this.transformToHTML = transformToHTML;
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public boolean isUrl() {
		return url;
	}

	public void setUrl(boolean url) {
		this.url = url;
	}

	public String getOutputPath() {
		return !DEFAULT_OUTPUT_PATH.equals(outputPath) ? outputPath : isMerge() ?
				DEFAULT_OUTPUT_PATH : DEFAULT_OUTPUT_PATH.replace(".xml", ".html");
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public List<String> getExclude() {
		return exclude;
	}

	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getNameTagValue() {
		return nameTagValue;
	}

	public void setNameTagValue(String nameTagValue) {
		this.nameTagValue = nameTagValue;
	}

	public String getDescriptionTagValue() {
		return descriptionTagValue;
	}

	public void setDescriptionTagValue(String descriptionTagValue) {
		this.descriptionTagValue = descriptionTagValue;
	}

	public String getCreatorTagValue() {
		return creatorTagValue;
	}

	public void setCreatorTagValue(String creatorTagValue) {
		this.creatorTagValue = creatorTagValue;
	}

	public boolean isHelp() {
		return help;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}
}

package org.verapdf.utils.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.verapdf.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
@Parameters(commandNames = "verapdf")
public class CommandProfileMerger extends Command{

	public static final String DEFAULT_NAME_TAG_VALUE = "PDF/A-1B validation profile";
	public static final String DEFAULT_DESCRIPTION_TAG_VALUE =
			"Validation rules against ISO 19005-1:2005, Cor.1:2007 and Cor.2:2011";
	public static final String DEFAULT_CREATOR_TAG_VALUE = "veraPDF Consortium";

	@Parameter(names = "--merge")
	private boolean merge;

	@Parameter(names = "--transform-to-html")
	private boolean transformToHTML;

	@Parameter(names = "--input", variableArity = true,
			description = "path to xml profiles, which will be merged. just 1 argument - path to file or directory")
	private List<String> inputPath = new ArrayList<>();

	@Parameter(names = "--output", variableArity = true,
			description = "path to result xml profile. just 1 argument - path to file")
	private List<String> outputPath = new ArrayList<>();

	@Parameter(names = "--exclude", variableArity = true)
	private List<String> exclude = new ArrayList<>();

	@Parameter(names = "--name", description = "value of name tag for result profile. just 1 argument - string")
	private String nameTagValue = DEFAULT_NAME_TAG_VALUE;

	@Parameter(names = "--description",
			description = "value of description tag for result profile. just 1 argument - string")
	private String descriptionTagValue = DEFAULT_DESCRIPTION_TAG_VALUE;

	@Parameter(names = "--creator", description = "value of creator tag for result profile. just 1 argument - string")
	private String creatorTagValue = DEFAULT_CREATOR_TAG_VALUE;

	@Parameter(names = "--flavour", description = "set validation flavour of result profile")
	private String flavour = "1b";

	@Parameter(names = "--version", description = "for profile merger is representation of result profile, " +
			"for html transformer - representation of passed profile.")
	private String version = "V2";

	@Parameter(names = "--list-versions", description = "list version names and corresponding structure of profile")
	private boolean listVersions = false;

	@Parameter(names = "--save-duplicated", description = "is need to save rules with the same id")
	private boolean saveDuplicated = false;

	@Parameter(names = "--save-pretty", description = "")
	private boolean savePretty = true;

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

	public List<String> getInputPath() {
		return inputPath;
	}

	public void setInputPath(List<String> inputPath) {
		this.inputPath = inputPath;
	}

	public List<String> getOutputPath() {
		return this.outputPath;
	}

	public void setOutputPath(List<String> outputPath) {
		this.outputPath = outputPath;
	}

	public List<String> getExclude() {
		return exclude;
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

	public String getFlavour() {
		return this.flavour;
	}

	public void setFlavour(String flavour) {
		this.flavour = flavour;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isListVersions() {
		return listVersions;
	}

	public void setListVersions(boolean listVersions) {
		this.listVersions = listVersions;
	}

	public boolean isSaveDuplicated() {
		return saveDuplicated;
	}

	public void setSaveDuplicated(boolean saveDuplicated) {
		this.saveDuplicated = saveDuplicated;
	}

	public boolean isSavePretty() {
		return savePretty;
	}

	public void setSavePretty(boolean savePretty) {
		this.savePretty = savePretty;
	}
}

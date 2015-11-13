package org.verapdf;

import com.beust.jcommander.JCommander;
import org.verapdf.entity.ValidationProfile;
import org.verapdf.impl.ProfileImplementations;
import org.verapdf.merger.ProfileMerger;
import org.verapdf.transformer.HtmlTransformer;
import org.verapdf.utils.commands.CommandProfileMerger;
import org.verapdf.utils.config.VeraPDFMergerConfig;
import org.verapdf.utils.config.VeraPDFTransformConfig;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Module for creating validation profile from several
 * profiles and transform validation profile to html
 * table. Validation profile considered to be valid
 * for merge if root tag is {@code <profile>} and this
 * tag contains {@code <rules>} (which contain {@code
 * <rule>} tag) and(or) {@code variables} (which contain
 * {@code <variable>} tag).
 *
 * @author Evgeniy Muravitskiy
 */
public class Main {

	private static final CommandProfileMerger commandVeraPDF;

	static {
		commandVeraPDF = new CommandProfileMerger();
	}

	private Main() {
		// disable default constructor
	}

	/**
	 * Entry point to logic of this module. Current module can
	 * merge few validation profiles into 1 and transform validation
	 * profile to html table. For merging profiles need to input
	 * 'verapdf --merge'. For transforming validation profile to
	 * html table need to input 'verapdf --transform-to-html'.
	 *
	 * <p>
	 * In order to find more options must enter next args:
	 * 'verapdf --help'.
	 *
	 * @param args arguments for defining arguments of module
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static void main(String[] args) throws Exception {
		JCommander jCommander = new JCommander();
		jCommander.addCommand(commandVeraPDF);
		jCommander.parse(args);

		if (commandVeraPDF.isListVersions()) {
			listVersions();
		} else if (commandVeraPDF.isMerge()) {
			VeraPDFMergerConfig config = createConfigFromCliOptions();
			ProfileMerger.mergeProfiles(config);
		} else if (commandVeraPDF.isTransformToHTML()) {
			VeraPDFTransformConfig config = getVeraPDFTransformConfig();
			HtmlTransformer.transformToHtml(config);
		} else {
			jCommander.usage();
		}
	}

	private static void listVersions() {
		for (ProfileImplementations value : ProfileImplementations.values()) {
			// TODO : implement print of profile view
			System.out.println(value);
		}
	}

	private static VeraPDFTransformConfig getVeraPDFTransformConfig() {
		return new VeraPDFTransformConfig(commandVeraPDF.getInputPath(),
				commandVeraPDF.getOutputPath(), commandVeraPDF.getVersion());
	}

	private static VeraPDFMergerConfig createConfigFromCliOptions() {
		VeraPDFMergerConfig.Builder builder = new VeraPDFMergerConfig.Builder();

		builder
				.inputPath(commandVeraPDF.getInputPath())
				.outputPath(commandVeraPDF.getOutputPath().get(0))
				.excluded(commandVeraPDF.getExclude())
				.nameTagValue(commandVeraPDF.getNameTagValue())
				.descriptionTagValue(commandVeraPDF.getDescriptionTagValue())
				.creatorTagValue(commandVeraPDF.getCreatorTagValue())
				.flavour(commandVeraPDF.getFlavour())
				.version(commandVeraPDF.getVersion())
				.saveDuplicated(commandVeraPDF.isSaveDuplicated())
				.pretty(commandVeraPDF.isSavePretty());

		return builder.build();
	}

}

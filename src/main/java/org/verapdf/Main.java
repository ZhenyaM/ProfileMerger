package org.verapdf;

import com.beust.jcommander.JCommander;
import org.verapdf.merger.ProfileMerger;
import org.verapdf.transformer.HtmlTransformer;
import org.xml.sax.SAXException;
import org.verapdf.utils.commands.CommandProfileMerger;
import org.verapdf.utils.config.VeraPDFTransformConfig;
import org.verapdf.utils.config.VeraPDFMergerConfig;
import org.verapdf.utils.config.Input;
import org.verapdf.utils.filter.XMLFileFilter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
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

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
		JCommander jCommander = new JCommander();
		jCommander.addCommand(commandVeraPDF);
		jCommander.parse(args);

		if (commandVeraPDF.isMerge()) {
			VeraPDFMergerConfig config = createConfigFromCliOptions();
			ProfileMerger.mergeProfiles(config);
		} else if (commandVeraPDF.isTransformToHTML()) {
			VeraPDFTransformConfig config = getVeraPDFTransformConfig();
			HtmlTransformer.transformToHtml(config);
		} else {
			jCommander.usage();
		}
	}

	private static VeraPDFTransformConfig getVeraPDFTransformConfig() {
		return new VeraPDFTransformConfig(commandVeraPDF.getInputPath(),
				commandVeraPDF.isUrl(), commandVeraPDF.getOutputPath());
	}

	private static VeraPDFMergerConfig createConfigFromCliOptions() {
		VeraPDFMergerConfig.Builder builder = new VeraPDFMergerConfig.Builder();

		builder
				.inputPath(commandVeraPDF.getInputPath(), commandVeraPDF.isUrl())
				.outputPath(commandVeraPDF.getOutputPath())
				.model(commandVeraPDF.getModel())
				.namespace(commandVeraPDF.getNamespace())
				.nameTagValue(commandVeraPDF.getNameTagValue())
				.descriptionTagValue(commandVeraPDF.getDescriptionTagValue())
				.creatorTagValue(commandVeraPDF.getCreatorTagValue());

		return builder.build();
	}

}

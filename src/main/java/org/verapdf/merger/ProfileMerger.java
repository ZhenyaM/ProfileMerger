package org.verapdf.merger;

import org.apache.log4j.Logger;
import org.verapdf.entity.ProfileDetails;
import org.verapdf.entity.Rule;
import org.verapdf.entity.ValidationProfile;
import org.verapdf.entity.Variable;
import org.verapdf.impl.AbstractValidationProfile;
import org.verapdf.impl.ProfileImplementations;
import org.verapdf.impl.v2.ProfileDetailsImpl;
import org.verapdf.impl.v2.ValidationProfileImpl;
import org.verapdf.utils.config.VeraPDFMergerConfig;
import org.verapdf.utils.filter.XMLFileFilter;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Evgeniy Muravitskiy
 */
public class ProfileMerger {

	private static final Logger LOGGER = Logger.getLogger(ProfileMerger.class);

	public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH-mm-ss:SSSZ";

	public static ValidationProfile mergeProfiles(VeraPDFMergerConfig config) throws JAXBException, IOException {
		List<Rule> rules = new ArrayList<>();
		List<Variable> variables = new ArrayList<>();

		getRulesAndVariables(config, rules, variables);
		return save(rules, variables, config);
	}

	private static void getRulesAndVariables(VeraPDFMergerConfig config,
											 List<Rule> rules,
											 List<Variable> variables) {
		Deque<File> fileStack = getFiles(config);
		while (!fileStack.isEmpty()) {
			File file = fileStack.pop();
			if (file.isDirectory()) {
				addFilesToStack(fileStack, file, config);
			} else if (file.isFile()) {
				getRulesAndVariables(file, rules, variables, config);
			}
		}
	}

	private static Deque<File> getFiles(VeraPDFMergerConfig config) {
		Deque<File> files = new ArrayDeque<>();
		FileFilter filter = XMLFileFilter.getInstance();
		for (String path : config.getInputPath()) {
			File file = new File(path);
			if (file.exists() && filter.accept(file) && !isExcluded(file, config)) {
				files.push(file);
			}
		}
		return files;
	}

	private static boolean isExcluded(File file, VeraPDFMergerConfig config) {
		List<String> excluded = config.getExcluded();
		return excluded.contains(file.getName()) || excluded.contains(file.getPath()) ||
				excluded.contains(file.getAbsolutePath());
	}

	private static void addFilesToStack(Deque<File> fileStack, File file, VeraPDFMergerConfig config) {
		File[] folderFiles = file.listFiles(XMLFileFilter.getInstance());
		if (folderFiles != null) {
			for (File temp : folderFiles) {
				if (!isExcluded(temp, config)) {
					fileStack.push(temp);
				} else {
					LOGGER.warn("'" + temp.getAbsolutePath() + "' is excluded");
				}
			}
		}
	}

	private static void getRulesAndVariables(File file,
											 List<Rule> rules,
											 List<Variable> variables,
											 VeraPDFMergerConfig config) {
		try {
			Object result = AbstractValidationProfile
					.fromXml(file, ProfileImplementations.getClasses());
			if (result instanceof Rule) {
				if (!rules.contains(result)) {
					rules.add((Rule) result);
				} else if (config.isSaveDuplicated()) {
					rules.add((Rule) result);
					LOGGER.warn("Rule '" + ((Rule) result).getID() + "' is repeated, but saved.");
				} else {
					LOGGER.warn("Rule '" + ((Rule) result).getID() + "' is repeated");
				}
			} else if (result instanceof Variable) {
				if (!variables.contains(result)) {
					variables.add((Variable) result);
				} else if (config.isSaveDuplicated()) {
					variables.add((Variable) result);
					LOGGER.warn("Variable '" + ((Variable) result).getName() + "' is repeated, but saved");
				} else {
					LOGGER.warn("Variable '" + ((Variable) result).getName() + "' is repeated");
				}
			} else if (result instanceof ValidationProfile) {
				ValidationProfile profile = (ValidationProfile) result;
				addValuesIfNotPresent(rules, profile.getRules(), config);
				addValuesIfNotPresent(variables, profile.getVariables(), config);
			} else {
				String type = result != null ? result.getClass().getName() : null;
				LOGGER.warn("Unexpected type of object:\r\nType: " + type
						+ "\r\nFile: " + file.getAbsolutePath());
			}
		} catch (JAXBException | IOException e) {
			LOGGER.warn("Problems with file '" + file.getAbsolutePath() + "'.");
			LOGGER.error("Exception while xml parse. Process continued.", e);
		}
	}

	private static <T> void addValuesIfNotPresent(List<T> obtainValues,
												  List<? extends T> profileValues,
												  VeraPDFMergerConfig config) {
		for (T value : profileValues) {
			if (!obtainValues.contains(value)) {
				obtainValues.add(value);
			} else if (config.isSaveDuplicated()) {
				obtainValues.add(value);
				LOGGER.warn(value.getClass().getSimpleName() + " from profile is repeated, but saved");
			}
		}
	}

	private static ValidationProfile save(List<Rule> rules, List<Variable> variables, VeraPDFMergerConfig config)
			throws JAXBException, IOException {
		ProfileImplementations impl = ProfileImplementations.valueOf(config.getVersion());
		AbstractValidationProfile profile = impl.getProfileFromValues(
				config.getFlavour(), getDetails(config), "", rules, variables);
		AbstractValidationProfile.toXml(profile, config.getOutputPath(), config.isPretty());
		return profile;
	}

	private static ProfileDetails getDetails(VeraPDFMergerConfig config) {
		return new ProfileDetailsImpl(config.getCreatorTagValue(), getTime(),
				config.getNameTagValue(), config.getDescriptionTagValue());
	}

	private static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		Calendar utc = Calendar.getInstance();
		return formatter.format(utc.getTime());
	}

}

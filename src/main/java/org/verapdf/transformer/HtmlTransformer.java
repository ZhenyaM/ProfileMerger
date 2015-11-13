package org.verapdf.transformer;

import org.apache.log4j.Logger;
import org.verapdf.entity.ValidationProfile;
import org.verapdf.impl.AbstractValidationProfile;
import org.verapdf.impl.ProfileImplementations;
import org.verapdf.utils.config.VeraPDFTransformConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * @author Evgeniy Muravitskiy
 */
public class HtmlTransformer {

	private static final Logger LOGGER = Logger.getLogger(HtmlTransformer.class);

	private HtmlTransformer() {
		// disable default constructor
	}

	public static void transformToHtml(VeraPDFTransformConfig config) {
		for (int i = 0; i < config.getInputPath().size(); i++) {
			File inputFile = new File(config.getInputPath().get(i));
			if (inputFile.isFile() && inputFile.getName().endsWith(".xml")) {
				String outputFilepath = config.getOutputPath().get(i);
				try (OutputStream htmlFile = new FileOutputStream(outputFilepath)) {
					TransformerFactory tFactory = TransformerFactory.newInstance();
					ProfileImplementations profileVersion = ProfileImplementations
							.valueOf(config.getVersion());

					Class<HtmlTransformer> resources = HtmlTransformer.class;
					Source xslDoc = new StreamSource(resources
							.getResourceAsStream(profileVersion.getHtmlPattern()));

					Transformer transformer = tFactory.newTransformer(xslDoc);

					transformer.setOutputProperty(OutputKeys.METHOD, "html");
					transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

					Class<? extends AbstractValidationProfile> aClass = profileVersion
							.getCurrentImplementationClass();
					Object fromXml = AbstractValidationProfile
							.fromXml(inputFile, ProfileImplementations.getClasses());

					JAXBContext context = JAXBContext.newInstance(aClass);
					JAXBSource jaxbSource = new JAXBSource(context,
							profileVersion.getProfileFromProfile((ValidationProfile) fromXml));
					transformer.transform(jaxbSource, new StreamResult(htmlFile));
				} catch (Exception e) {
					LOGGER.error("Problems with transforming to html.", e);
				}
			} else {
				LOGGER.error("File not found or this is not xml.");
			}
		}
	}

}

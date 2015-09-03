package org.verapdf.transformer;

import org.apache.log4j.Logger;
import org.verapdf.utils.config.VeraPDFTransformConfig;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Evgeniy Muravitskiy
 */
public class HtmlTransformer {

	private static final Logger LOGGER = Logger.getLogger(HtmlTransformer.class);

	private static final String XSLT_SCHEMA = getSystemIndependentPath("/htmlPattern.xsl");

	public static final int FILE_SYSTEM_CONST = 1;

	public static void transformToHtml(VeraPDFTransformConfig config) {
		File file = new File(config.getInputPath());
		if (file.isFile() && file.getName().endsWith(".xml")) {
			try {
				System.out.println(XSLT_SCHEMA);
				TransformerFactory tFactory = TransformerFactory.newInstance();

				File schema = new File(XSLT_SCHEMA);
				Source xslDoc = new StreamSource(schema);
				Source xmlDoc = new StreamSource(file);

				OutputStream htmlFile = new FileOutputStream(config.getOutputPath());

				Transformer transformer = tFactory.newTransformer(xslDoc);

				transformer.setOutputProperty(OutputKeys.METHOD, "html");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

				transformer.transform(xmlDoc, new StreamResult(htmlFile));
			} catch (Exception e) {
				LOGGER.error("Problems with transforming to html.", e);
			}
		} else {
			LOGGER.error("File not found or this is not xml.");
		}
	}

	private static String getSystemIndependentPath(String path) {
		try {
			URL resourceUrl = ClassLoader.class.getResource(path);
			/*URL url = ClassLoader.class.getResource("");
			Map<String, String> env = new HashMap<>(FILE_SYSTEM_CONST);
			env.put("create", "true");
			FileSystem zipfs = FileSystems.newFileSystem(resourceUrl.toURI(), env);*/
			Path resourcePath = Paths.get(resourceUrl.toURI());
			return resourcePath.toString();
		} catch (URISyntaxException e) {
			LOGGER.error("Can`t get path to resources.", e);
		}

		return null;
	}
}

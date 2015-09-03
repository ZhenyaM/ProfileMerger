package org.verapdf.transformer;

import org.apache.log4j.Logger;
import org.verapdf.utils.config.VeraPDFTransformConfig;

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

	private static final String XSLT_SCHEMA_NAME = "/htmlPattern.xsl";
	private static final File XSLT_SCHEMA = getSystemIndependentFile();

	private HtmlTransformer() {
		// disable default constructor
	}

	public static void transformToHtml(VeraPDFTransformConfig config) {
		File file = new File(config.getInputPath());
		if (file.isFile() && file.getName().endsWith(".xml")) {
			try (OutputStream htmlFile = new FileOutputStream(config.getOutputPath())) {
				TransformerFactory tFactory = TransformerFactory.newInstance();

				Source xslDoc = new StreamSource(XSLT_SCHEMA);
				Source xmlDoc = new StreamSource(file);

				Transformer transformer = tFactory.newTransformer(xslDoc);

				transformer.setOutputProperty(OutputKeys.METHOD, "html");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

				transformer.transform(xmlDoc, new StreamResult(htmlFile));
			} catch (Exception e) {
				LOGGER.error("Problems with transforming to html.", e);
			} finally {
				if (XSLT_SCHEMA != null) {
					XSLT_SCHEMA.delete();
					XSLT_SCHEMA.deleteOnExit();
				}
			}
		} else {
			LOGGER.error("File not found or this is not xml.");
		}
	}

	private static File getSystemIndependentFile() {
		try (InputStream resourceUrl = ClassLoader.class.getResourceAsStream(XSLT_SCHEMA_NAME)) {
			File file = new File(XSLT_SCHEMA_NAME);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
			byte[] bytes = new byte[resourceUrl.available()];
			resourceUrl.read(bytes);
			writer.write(bytes);
			writer.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}

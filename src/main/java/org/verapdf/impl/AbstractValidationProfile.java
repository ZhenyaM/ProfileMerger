package org.verapdf.impl;

import org.verapdf.entity.ValidationProfile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.*;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlJavaTypeAdapter(AbstractValidationProfile.Adapter.class)
public abstract class AbstractValidationProfile implements ValidationProfile {

	static class Adapter extends XmlAdapter<AbstractValidationProfile, AbstractValidationProfile> {
		@Override
		public AbstractValidationProfile unmarshal(AbstractValidationProfile v) throws Exception {
			return v;
		}

		@Override
		public AbstractValidationProfile marshal(AbstractValidationProfile v) throws Exception {
			return v;
		}
	}

	public static Object fromXml(String filepath, Class<?>... aClass) throws JAXBException, IOException{
		return fromXml(new File(filepath), aClass);
	}

	public static Object fromXml(File file, Class<?>... aClass) throws JAXBException, IOException {
		try (InputStream stream = new BufferedInputStream(new FileInputStream(file))) {
			return fromXml(stream, aClass);
		}
	}

	public static Object fromXml(InputStream stream, Class<?>... aClass) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(aClass);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller.unmarshal(stream);
	}

	public static void toXml(AbstractValidationProfile profile, String filepath,
							 boolean isPretty) throws JAXBException, IOException {
		toXml(profile, new File(filepath), isPretty);
	}

	public static void toXml(AbstractValidationProfile profile, File outputFile,
							 boolean isPretty) throws JAXBException, IOException {
		try (OutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile))) {
			toXml(profile, output, isPretty);
		}
	}

	public static void toXml(AbstractValidationProfile profile, OutputStream output,
							 boolean isPretty) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(profile.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isPretty);
		marshaller.marshal(profile, output);
	}

}

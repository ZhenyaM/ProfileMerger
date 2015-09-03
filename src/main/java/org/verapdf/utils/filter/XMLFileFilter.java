package org.verapdf.utils.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Evgeniy Muravitskiy
 */
public class XMLFileFilter implements FileFilter {

	public static final String FILTER = ".xml";

	private static XMLFileFilter INSTANCE;

	private XMLFileFilter() {

	}

	public static FileFilter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new XMLFileFilter();
		}
		return INSTANCE;
	}

	@Override
	public boolean accept(File file) {
		return file.getName().endsWith(FILTER) || file.isDirectory();
	}
}

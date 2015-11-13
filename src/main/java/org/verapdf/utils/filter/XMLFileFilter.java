package org.verapdf.utils.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Evgeniy Muravitskiy
 */
public class XMLFileFilter implements FileFilter {

	public static final String EXTENSION = ".xml";

	private static XMLFileFilter INSTANCE;

	private XMLFileFilter() {

	}

	public synchronized static FileFilter getInstance() {
		return INSTANCE == null ? INSTANCE = new XMLFileFilter() : INSTANCE;
	}

	@Override
	public boolean accept(File file) {
		return file.getName().endsWith(EXTENSION) || file.isDirectory();
	}
}

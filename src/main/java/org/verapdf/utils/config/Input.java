package org.verapdf.utils.config;

/**
 * @author Evgeniy Muravitskiy
 */
public class Input {

	private final String path;
	private final boolean url;

	public Input(String path, boolean url) {
		this.path = path;
		this.url = url;
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return url
	 */
	public boolean isUrl() {
		return url;
	}
}

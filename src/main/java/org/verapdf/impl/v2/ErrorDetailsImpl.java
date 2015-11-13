package org.verapdf.impl.v2;

import org.verapdf.entity.ErrorDetails;
import org.verapdf.impl.AbstractErrorDetails;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlRootElement(name = "error")
public class ErrorDetailsImpl extends AbstractErrorDetails {

	public static final ErrorDetailsImpl DEFAULT = new ErrorDetailsImpl();

	@XmlElement(name = "message")
	private final String message;
	@XmlElementWrapper
	@XmlElement(name = "argument")
	private final List<String> arguments;

	private ErrorDetailsImpl() {
		this("Indirect has no contains id", new ArrayList<String>());
	}

	public ErrorDetailsImpl(String message, List<String> arguments) {
		this.message = message;
		this.arguments = arguments;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public List<String> getArguments() {
		return this.arguments;
	}

	public static ErrorDetailsImpl fromErrorDetails(ErrorDetails details) {
		return new ErrorDetailsImpl(details.getMessage(), details.getArguments());
	}
}

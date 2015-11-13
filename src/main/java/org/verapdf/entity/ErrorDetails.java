package org.verapdf.entity;

import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
public interface ErrorDetails {

	String getMessage();

	List<String> getArguments();


}

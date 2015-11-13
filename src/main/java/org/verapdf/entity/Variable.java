package org.verapdf.entity;

/**
 * @author Evgeniy Muravitskiy
 */
public interface Variable {

	String getObjectType();

	String getName();

	String getDefaultValue();

	String getValue();

}

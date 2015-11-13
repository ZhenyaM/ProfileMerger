package org.verapdf.entity;

import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
public interface ValidationProfile {

	String getFlavour();

	<T extends ProfileDetails> T getDetails();

	String getHash();

	List<? extends Rule> getRules();

	List<? extends Variable> getVariables();

}

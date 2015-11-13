package org.verapdf.entity;

import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
public interface Rule {

	String getObjectType();

	<T extends RuleID> T getID();

	String getDescription();

	String getTest();

	<T extends ErrorDetails> T getErrorDetails();

	List<? extends Reference> getReferences();

}

package org.verapdf.entity;

import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
public interface Reference {

	String getSpecification();

	String getClause();

	List<? extends Reference> getReferences();

}

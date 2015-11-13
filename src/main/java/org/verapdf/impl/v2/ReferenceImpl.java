package org.verapdf.impl.v2;

import org.verapdf.entity.Reference;
import org.verapdf.impl.AbstractReference;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlRootElement(name = "reference")
public class ReferenceImpl extends AbstractReference {

	public static final ReferenceImpl DEFAULT = new ReferenceImpl();

	@XmlElement(name = "specification")
	private final String specification;
	@XmlElement(name = "clause")
	private final String clause;
	//@XmlElementWrapper
	//@XmlElement(name = "reference")
	private final List<ReferenceImpl> references;

	private ReferenceImpl() {
		this("No ISO", "No Clause", new ArrayList<ReferenceImpl>());
	}

	public ReferenceImpl(String specification, String clause, List<ReferenceImpl> references) {
		this.specification = specification;
		this.clause = clause;
		this.references = references;
	}

	@Override
	public String getSpecification() {
		return this.specification;
	}

	@Override
	public String getClause() {
		return this.clause;
	}

	@Override
	public List<ReferenceImpl> getReferences() {
		return this.references;
	}

	public static ReferenceImpl fromReference(Reference ref) {
		List<ReferenceImpl> list = new ArrayList<>(ref.getReferences().size());
		for (Reference temp : ref.getReferences()) {
			list.add(ReferenceImpl.fromReference(temp));
		}
		return new ReferenceImpl(ref.getSpecification(), ref.getClause(), list);
	}

}

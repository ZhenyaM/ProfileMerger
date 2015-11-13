package org.verapdf.impl.v2;

import org.verapdf.entity.Reference;
import org.verapdf.entity.Rule;
import org.verapdf.impl.AbstractRule;
import org.verapdf.utils.Utils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlRootElement(name = "rule")
public class RuleImpl extends AbstractRule {

	public static final RuleImpl DEFAULT = new RuleImpl();

	@XmlAttribute(name = "object")
	public final String objectType;
	@XmlElement(name = "id")
	public final RuleIDImpl id;
	@XmlElement(name = "description")
	public final String description;
	@XmlElement(name = "test")
	public final String test;
	@XmlElement(name = "error")
	public final ErrorDetailsImpl errorDetails;
	@XmlElementWrapper
	@XmlElement(name = "reference")
	public final List<ReferenceImpl> references;

	public RuleImpl() {
		this("CosIndirect", RuleIDImpl.DEFAULT, "Every CosIndirect must have id", "id != null",
				ErrorDetailsImpl.DEFAULT, Utils.getList(ReferenceImpl.DEFAULT));
	}

	public RuleImpl(String objectType, RuleIDImpl id, String description,
					String test, ErrorDetailsImpl errorDetails, List<ReferenceImpl> references) {
		this.objectType = objectType;
		this.id = id;
		this.description = description;
		this.test = test;
		this.errorDetails = errorDetails;
		this.references = references;
	}

	@Override
	public String getObjectType() {
		return this.objectType;
	}

	@Override
	public RuleIDImpl getID() {
		return this.id;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getTest() {
		return this.test;
	}

	@Override
	public ErrorDetailsImpl getErrorDetails() {
		return this.errorDetails;
	}

	@Override
	public List<ReferenceImpl> getReferences() {
		return this.references;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Rule)) {
			return false;
		}
		Rule buffer = (Rule) obj;
		return this.id == null ? buffer.getID() == null : this.id.equals(buffer.getID());
	}

	public static RuleImpl fromRule(Rule rule) {
		List<ReferenceImpl> ref = new ArrayList<>(rule.getReferences().size());
		for (Reference temp : rule.getReferences()) {
			ref.add(ReferenceImpl.fromReference(temp));
		}
		RuleIDImpl id = RuleIDImpl.fromRuleID(rule.getID());
		ErrorDetailsImpl details = ErrorDetailsImpl.fromErrorDetails(rule.getErrorDetails());
		return new RuleImpl(rule.getObjectType(), id, rule.getDescription(),
				rule.getTest(), details, ref);
	}

}

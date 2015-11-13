package org.verapdf.impl.v2;

import org.verapdf.entity.RuleID;
import org.verapdf.impl.AbstractRuleID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlRootElement(name = "id")
public class RuleIDImpl extends AbstractRuleID {

	public static final RuleIDImpl DEFAULT = new RuleIDImpl();

	@XmlAttribute(name = "specification")
	private final String specification;
	@XmlAttribute(name = "clause")
	private final String clause;
	@XmlAttribute(name = "testNumber")
	private final int testNumber;

	public RuleIDImpl() {
		this("No ISO", "No Close", -1);
	}

	public RuleIDImpl(String specification, String clause, int testNumber) {
		this.specification = specification;
		this.clause = clause;
		this.testNumber = testNumber;
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
	public int getTestNumber() {
		return this.testNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof RuleID)) {
			return false;
		}
		RuleID buffer = (RuleID) obj;
		if (this.testNumber != buffer.getTestNumber()) {
			return false;
		}
		if (this.specification == null ? buffer.getSpecification() != null :
				!this.specification.equals(buffer.getSpecification())) {
			return false;
		}
		return !(this.clause == null ? buffer.getClause() != null :
				!this.clause.equals(buffer.getClause()));
	}

	@Override
	public String toString(){
		return this.specification + '-' + this.clause.replace('.','-') + "-t" + this.testNumber;
	}

	public static RuleIDImpl fromRuleID(RuleID id) {
		return new RuleIDImpl(id.getSpecification(), id.getClause(), id.getTestNumber());
	}

}

package org.verapdf.impl.v2;

import org.verapdf.entity.Variable;
import org.verapdf.impl.AbstractVariable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlRootElement(name = "variable")
public class VariableImpl extends AbstractVariable {

	public static final VariableImpl DEFAULT = new VariableImpl();

	@XmlAttribute(name = "object")
	private final String objectType;
	@XmlAttribute(name = "name")
	private final String name;
	@XmlElement(name = "defaultValue")
	private final String defaultValue;
	@XmlElement(name = "value")
	private final String value;

	public VariableImpl() {
		this("CosIndirect", "var", "null", "id");
	}

	public VariableImpl(String objectType, String name,
						String defaultValue, String value) {
		this.objectType = objectType;
		this.name = name;
		this.defaultValue = defaultValue;
		this.value = value;
	}

	@Override
	public String getObjectType() {
		return this.objectType;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Variable)) {
			return false;
		}

		Variable variable = (Variable) obj;

		if (this.objectType == null ? variable.getObjectType() != null :
				!this.objectType.equals(variable.getObjectType())) {
			return false;
		}
		return !(this.name == null ? variable.getName() != null :
				!this.name.equals(variable.getName()));

	}

	public static VariableImpl fromVariable(Variable var) {
		return new VariableImpl(var.getObjectType(), var.getName(),
				var.getDefaultValue(), var.getValue());
	}

}

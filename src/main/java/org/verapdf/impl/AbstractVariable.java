package org.verapdf.impl;

import org.verapdf.entity.Variable;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlJavaTypeAdapter(AbstractVariable.Adapter.class)
public abstract class AbstractVariable implements Variable {

	class Adapter extends XmlAdapter<AbstractVariable, AbstractVariable> {
		@Override
		public AbstractVariable unmarshal(AbstractVariable v) throws Exception {
			return v;
		}

		@Override
		public AbstractVariable marshal(AbstractVariable v) throws Exception {
			return v;
		}
	}
}

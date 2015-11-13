package org.verapdf.impl;

import org.verapdf.entity.Rule;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlJavaTypeAdapter(AbstractRule.Adapter.class)
public abstract class AbstractRule implements Rule {

	static class Adapter extends XmlAdapter<AbstractRule, AbstractRule> {
		@Override
		public AbstractRule unmarshal(AbstractRule v) throws Exception {
			return v;
		}

		@Override
		public AbstractRule marshal(AbstractRule v) throws Exception {
			return v;
		}
	}
}

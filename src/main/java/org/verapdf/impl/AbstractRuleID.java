package org.verapdf.impl;

import org.verapdf.entity.RuleID;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlJavaTypeAdapter(AbstractRuleID.Adapter.class)
public abstract class AbstractRuleID implements RuleID {

	static class Adapter extends XmlAdapter<AbstractRuleID, AbstractRuleID> {

		@Override
		public AbstractRuleID unmarshal(AbstractRuleID v) throws Exception {
			return v;
		}

		@Override
		public AbstractRuleID marshal(AbstractRuleID v) throws Exception {
			return v;
		}
	}
}

package org.verapdf.impl;

import org.verapdf.entity.ProfileDetails;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlJavaTypeAdapter(AbstractProfileDetails.Adapter.class)
public abstract class AbstractProfileDetails implements ProfileDetails {

	static class Adapter extends XmlAdapter<AbstractProfileDetails, AbstractProfileDetails> {
		@Override
		public AbstractProfileDetails unmarshal(AbstractProfileDetails v) throws Exception {
			return v;
		}

		@Override
		public AbstractProfileDetails marshal(AbstractProfileDetails v) throws Exception {
			return v;
		}
	}
}

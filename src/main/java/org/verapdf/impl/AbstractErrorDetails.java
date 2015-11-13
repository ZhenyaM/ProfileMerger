package org.verapdf.impl;

import org.verapdf.entity.ErrorDetails;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlJavaTypeAdapter(AbstractErrorDetails.Adapter.class)
public abstract class AbstractErrorDetails implements ErrorDetails {

	static class Adapter extends XmlAdapter<AbstractErrorDetails, AbstractErrorDetails> {
		@Override
		public AbstractErrorDetails unmarshal(AbstractErrorDetails v) throws Exception {
			return v;
		}

		@Override
		public AbstractErrorDetails marshal(AbstractErrorDetails v) throws Exception {
			return v;
		}
	}
}

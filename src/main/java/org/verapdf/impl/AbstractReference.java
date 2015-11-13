package org.verapdf.impl;

import org.verapdf.entity.Reference;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlJavaTypeAdapter(AbstractReference.Adapter.class)
public abstract class AbstractReference implements Reference {

	static class Adapter extends XmlAdapter<AbstractReference, AbstractReference> {
		@Override
		public AbstractReference unmarshal(AbstractReference v) throws Exception {
			return null;
		}

		@Override
		public AbstractReference marshal(AbstractReference v) throws Exception {
			return null;
		}
	}
}

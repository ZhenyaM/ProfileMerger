package org.verapdf.impl.v2;

import org.verapdf.entity.ProfileDetails;
import org.verapdf.impl.AbstractProfileDetails;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlRootElement(name = "details")
public class ProfileDetailsImpl extends AbstractProfileDetails {

	public static final ProfileDetailsImpl DEFAULT = new ProfileDetailsImpl();

	@XmlAttribute(name = "creator")
	private final String creator;
	@XmlAttribute(name = "created")
	private final String createdDate;
	@XmlElement(name = "name")
	private final String name;
	@XmlElement(name = "description")
	private final String description;

	private ProfileDetailsImpl() {
		this("VeraPDF", "", "Custom profile", "Default custom profile");
	}

	public ProfileDetailsImpl(String creator, String createdDate,
							  String name, String description) {
		this.creator = creator;
		this.createdDate = createdDate;
		this.name = name;
		this.description = description;
	}

	@Override
	public String getCreator() {
		return this.creator;
	}

	@Override
	public String getCreatedDate() {
		return this.createdDate;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public static ProfileDetailsImpl fromProfileDetails(ProfileDetails details) {
		return new ProfileDetailsImpl(details.getCreator(), details.getCreatedDate(),
				details.getName(), details.getDescription());
	}

}

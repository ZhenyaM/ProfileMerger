package org.verapdf.impl.v2;

import org.verapdf.entity.ProfileDetails;
import org.verapdf.entity.Rule;
import org.verapdf.entity.ValidationProfile;
import org.verapdf.entity.Variable;
import org.verapdf.impl.AbstractValidationProfile;
import org.verapdf.utils.Utils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
@XmlRootElement(name = "profile", namespace = "http://www.verapdf.org/ValidationProfile")
public class ValidationProfileImpl extends AbstractValidationProfile {

	public static final ValidationProfileImpl DEFAULT = new ValidationProfileImpl();

	@XmlAttribute(name = "flavour")
	private final String flavour;
	@XmlElement(name = "details")
	private final ProfileDetailsImpl details;
	@XmlElement(name = "hash")
	private final String hash;
	@XmlElementWrapper
	@XmlElement(name = "rule")
	private final List<RuleImpl> rules;
	@XmlElementWrapper
	@XmlElement(name = "variable")
	private final List<VariableImpl> variables;

	private ValidationProfileImpl() {
		this("1b", ProfileDetailsImpl.DEFAULT, "", Utils.getList(RuleImpl.DEFAULT),
				Utils.getList(VariableImpl.DEFAULT));
	}

	private ValidationProfileImpl(String flavour, ProfileDetailsImpl details, String hash,
								 List<RuleImpl> rules, List<VariableImpl> variables) {
		this.flavour = flavour;
		this.details = details;
		this.hash = hash;
		this.rules = rules;
		this.variables = variables;
	}

	@Override
	public String getFlavour() {
		return this.flavour;
	}

	@Override
	public ProfileDetailsImpl getDetails() {
		return this.details;
	}

	@Override
	public String getHash() {
		return this.hash;
	}

	@Override
	public List<RuleImpl> getRules() {
		return this.rules;
	}

	@Override
	public List<VariableImpl> getVariables() {
		return this.variables;
	}

	public static ValidationProfileImpl fromProfile(ValidationProfile profile) {
		return fromValues(profile.getFlavour(), profile.getDetails(), profile.getHash(),
				profile.getRules(), profile.getVariables());
	}

	public static ValidationProfileImpl fromValues(String flavour, ProfileDetails details, String hash,
												   List<? extends Rule> rules, List<? extends Variable> variables) {
		List<RuleImpl> resultRules = new ArrayList<>(rules.size());
		List<VariableImpl> resultVariables = new ArrayList<>(variables.size());
		for (Rule rule : rules) {
			resultRules.add(RuleImpl.fromRule(rule));
		}
		for (Variable var : variables) {
			resultVariables.add(VariableImpl.fromVariable(var));
		}
		ProfileDetailsImpl resDetails = ProfileDetailsImpl.fromProfileDetails(details);
		return new ValidationProfileImpl(flavour, resDetails, hash, resultRules, resultVariables);
	}

}

package org.verapdf.impl;

import org.verapdf.entity.ProfileDetails;
import org.verapdf.entity.Rule;
import org.verapdf.entity.ValidationProfile;
import org.verapdf.entity.Variable;
import org.verapdf.impl.v2.ValidationProfileImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author Evgeniy Muravitskiy
 */
public enum ProfileImplementations {

	V2(ValidationProfileImpl.class, "/v1HtmlPattern.xsl");

	private final Class<? extends AbstractValidationProfile> profileClass;
	private static final Class[] PARAMETERS = new Class[]{String.class,
			ProfileDetails.class, String.class, List.class, List.class};
	private final String xsltSchemaPath;

	ProfileImplementations(Class<? extends AbstractValidationProfile> profileClass, String xsltSchemaPath) {
		this.profileClass = profileClass;
		this.xsltSchemaPath = xsltSchemaPath;
	}

	public AbstractValidationProfile getProfileFromProfile(ValidationProfile profile) {
		return getProfileFromValues(profile.getFlavour(), profile.getDetails(), profile.getHash(),
				profile.getRules(), profile.getVariables());
	}

	public AbstractValidationProfile getProfileFromValues(String flavour,
														  ProfileDetails details,
														  String hash,
														  List<? extends Rule> rules,
														  List<? extends Variable> variables) {
		try {
			Method fromValues = this.profileClass.getDeclaredMethod("fromValues", PARAMETERS);
			if (!Modifier.isStatic(fromValues.getModifiers())) {
				throw new NoSuchMethodException();
			}
			fromValues.setAccessible(true);
			Object invoke = fromValues.invoke(null, flavour, details, hash, rules, variables);
			if (invoke instanceof AbstractValidationProfile) {
				return (AbstractValidationProfile) invoke;
			} else {
				throw new NoSuchMethodException();
			}
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(this.profileClass.getSimpleName()
					+ " does not implement method: static <? extends AbstractValidationProfile> fromValues(String, ProfileDetails, String, " +
					"List<? extends Rule>, List<? extends Variable>", e);
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	public Class<? extends AbstractValidationProfile> getCurrentImplementationClass() {
		return this.profileClass;
	}

	public static Class<? extends AbstractValidationProfile>[] getClasses() {
		ProfileImplementations[] values = values();
		Class<? extends AbstractValidationProfile>[] classes = new Class[values.length];
		for (int i = 0; i < values.length; i++) {
			classes[i] = values[i].profileClass;
		}
		return classes;
	}

	public String getHtmlPattern() {
		return this.xsltSchemaPath;
	}
}

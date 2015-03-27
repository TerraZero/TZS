package TZ.System.Annotations.Interfaces;

import java.util.Map;

import TZ.System.LoadState;
import TZ.System.File.CFid;

/**
 * Annotation interface
 * 
 * @author terrazero
 * @created Mar 27, 2015
 * 
 * @file InstallProfile.java
 * @project TZS
 * @identifier TZ.System.Annotations.Interfaces
 *
 */
public interface InstallProfile {

	/**
	 * The UNIQUE name of the install profile.
	 */
	public String name();
	
	/**
	 * A List of install profiles which will load before this install profile.
	 */
	public default String[] extend() {
		return null;
	}
	
	/**
	 * Install files for install profile
	 * @param state - the load state
	 * @param base - the base file for create
	 */
	public default void profileBase(LoadState state, CFid base) {
		
	}
	
	/**
	 * Add install informations for info.
	 * @param state
	 * @param profile
	 * @param info
	 */
	public default void profilling(LoadState state, String profile, Map<String, String> info) {
		
	}
	
	/**
	 * Add install informations for constructions.
	 * @param info - the info map for construction
	 */
	public default void profileConstruction(Map<String, String> info) {
		
	}

	/**
	 * Add install informations for module.
	 * @param info - the info map for module
	 */
	public default void profileModule(Map<String, String> info) {
		
	}

	/**
	 * Add install informations for mechnic.
	 * @param info - the info map for mechnic
	 */
	public default void profileMechnic(Map<String, String> info) {
		
	}
	
}

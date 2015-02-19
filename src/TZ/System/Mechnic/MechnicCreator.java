package TZ.System.Mechnic;

/**
 * 
 * @author terrazero
 * @created Feb 13, 2015
 * 
 * @file MechnicObject.java
 * @project G7C
 * @identifier TZ.Mechnic
 *
 */
public interface MechnicCreator<type> {

	public String mechnicName();
	
	public type mechnic(Object... args);
	
}

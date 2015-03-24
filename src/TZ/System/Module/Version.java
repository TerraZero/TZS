package TZ.System.Module;

/**
 * 
 * @author terrazero
 * @created Mar 24, 2015
 * 
 * @file Version.java
 * @project TZS
 * @identifier TZ.System.Module
 *
 */
public class Version {
	
	public static int[] toVersion(String version) {
		int[] versions = new int[4];
		
		try {
			String[] ver = version.split("\\.");
			for (int i = 0; i < versions.length; i++) {
				if (i < ver.length) {
					if (ver[i].equals("x")) {
						versions[i] = -1;
					} else {
						versions[i] = Integer.parseInt(ver[i]);
					}
				} else {
					versions[i] = -1;
				}
			}
			if (ver.length != versions.length && !ver[ver.length - 1].equals("x")) {
				return null;
			}
		} catch (NullPointerException | NumberFormatException e) {
			return null;
		}
		return versions;
	}

	protected String version;
	protected int[] versions;
	
	public Version(String version) {
		this.version = version;
		this.versions = Version.toVersion(version);
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (!this.isAccept()) {
			return "x.x.x.x (!.!.!.!)"; 
		}
		String string = this.version + " (";
		for (int i = 0; i < this.versions.length; i++) {
			if (this.versions[i] == -1) {
				string += "x";
			} else {
				string += this.versions[i];
			}
			if (i != this.versions.length - 1) {
				string += ".";
			}
		}
		return string + ")";
	}
	
	public boolean isAccept() {
		return this.versions != null;
	}
	
	public boolean isCompatible(Version version) {
		if (this.isAccept() && version.isAccept()) {
			for (int i = 0; i < this.versions.length; i++) {
				if (this.versions[i] != -1 && version.versions[i] != -1 && this.versions[i] != version.versions[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}

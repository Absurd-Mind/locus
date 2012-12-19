package org.inrain.pmap.provider.preferences;

public interface PreferencesProvider {
    public String getUsername();
    public String getServerURL();

    public boolean isActivated();
    public void setActivated(boolean activated);
}

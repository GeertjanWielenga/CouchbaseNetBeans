package com.mycompany.snhfc.level1;

import com.mycompany.snhfc.RootNode;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.JOptionPane;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

public class Level1ChildFactory extends ChildFactory.Detachable<String> implements PreferenceChangeListener {

    private List<String> servers;
    private String serverName;

    public Level1ChildFactory() {
        this.servers = new ArrayList<String>();
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        if (evt.getKey().equals("serverName")) {
            serverName = evt.getNewValue();
            String serverAddress = NbPreferences.forModule(RootNode.class).get("serverAddress", "localhost");
            String login = NbPreferences.forModule(RootNode.class).get("serverLogin", "error!");
            String password = NbPreferences.forModule(RootNode.class).get("serverPassword", "error!");
            if (login.equals("foo") && password.equals("bar")) {
                String server = serverName + " (" + serverAddress + ")";
                servers.add(server);
                refresh(true);
                StatusDisplayer.getDefault().setStatusText("New server.");
            } else {
                String msg = "Invalid login credentials.";
                JOptionPane.showMessageDialog(null, msg);
                StatusDisplayer.getDefault().setStatusText(msg);
            }
        }
    }

    @Override
    protected void addNotify() {
        NbPreferences.forModule(RootNode.class).addPreferenceChangeListener(this);
    }

    @Override
    protected void removeNotify() {
        NbPreferences.forModule(RootNode.class).removePreferenceChangeListener(this);
    }

    @Override
    protected boolean createKeys(final List<String> list) {
        list.addAll(servers);
        return true;
    }

    @Override
    protected Node createNodeForKey(String key) {
        Level1Node node = null;
        try {
            node = new Level1Node(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

}

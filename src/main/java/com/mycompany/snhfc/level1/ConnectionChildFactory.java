package com.mycompany.snhfc.level1;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.mycompany.snhfc.RootNode;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

public class ConnectionChildFactory extends ChildFactory.Detachable<Cluster> implements PreferenceChangeListener {

    private List<Cluster> clusters;
    private CouchbaseEnvironment env;

    public ConnectionChildFactory() {
        this.clusters = new ArrayList<Cluster>();
        this.env = DefaultCouchbaseEnvironment.builder().queryEnabled(true).build();
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        if (evt.getKey().equals("serverName")) {
            String serverAddress = NbPreferences.forModule(RootNode.class).get("serverAddress", "localhost");
            String login = NbPreferences.forModule(RootNode.class).get("serverLogin", "error!");
            String password = NbPreferences.forModule(RootNode.class).get("serverPassword", "error!");
            try {
                Cluster cluster = CouchbaseCluster.create(serverAddress);
                cluster.clusterManager(login, password);
                clusters.add(cluster);
                refresh(true);
                StatusDisplayer.getDefault().setStatusText("New server.");
            } catch (com.couchbase.client.java.error.InvalidPasswordException e) {
                String msg = "Invalid authentication credentials...";
                StatusDisplayer.getDefault().setStatusText(msg);
            } catch (com.couchbase.client.core.config.ConfigurationException f) {
                String msg = "Invalid address...";
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
    protected boolean createKeys(List<Cluster> list) {
        list.addAll(clusters);
        return true;
    }

    @Override
    protected Node createNodeForKey(Cluster cmgr) {
        ConnectionNode node = null;
        try {
            node = new ConnectionNode(cmgr);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

}

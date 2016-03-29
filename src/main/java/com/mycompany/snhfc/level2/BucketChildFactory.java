package com.mycompany.snhfc.level2;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterManager;
import com.mycompany.snhfc.RootNode;
import java.beans.IntrospectionException;
import java.util.List;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

public class BucketChildFactory extends ChildFactory<Bucket> {

    private final Cluster cluster;

    public BucketChildFactory(Cluster cluster) {
        this.cluster = cluster;
    }

    @Override
    protected boolean createKeys(List<Bucket> list) {
        String msg = "Processing Level 2...";
        ProgressHandle handle = ProgressHandleFactory.createSystemHandle(msg);
        handle.start();
        String login = NbPreferences.forModule(RootNode.class).get("serverLogin", "error!");
        String password = NbPreferences.forModule(RootNode.class).get("serverPassword", "error!");
        ClusterManager cmg = cluster.clusterManager(login, password);
        for (BucketSettings bs : cmg.getBuckets()) {
            String name = bs.name();
            Bucket b = cluster.openBucket(name);
            list.add(b);
        }
        handle.finish();
        return true;
    }

    @Override
    protected Node createNodeForKey(Bucket bucket) {
        BucketNode node = null;
        try {
            node = new BucketNode(bucket);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

}

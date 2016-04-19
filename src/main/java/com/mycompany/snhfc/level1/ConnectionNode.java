package com.mycompany.snhfc.level1;

import com.couchbase.client.java.Cluster;
import com.mycompany.snhfc.RootNode;
import com.mycompany.snhfc.level2.BucketChildFactory;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.NbPreferences;
import org.openide.util.Utilities;

public class ConnectionNode extends BeanNode<Cluster> {

    @StaticResource
    private static final String ICON = "com/mycompany/snhfc/level1/icon.png";

    public ConnectionNode(Cluster cluster) throws IntrospectionException {
        super(cluster, Children.create(new BucketChildFactory(cluster), true));
        String serverName = NbPreferences.forModule(RootNode.class).get("serverName", "default");
        setDisplayName(serverName);
        setIconBaseWithExtension(ICON);
    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> bucketActions = Utilities.actionsForPath("Actions/Connection");
        return bucketActions.toArray(new Action[bucketActions.size()]);
    }

    @Override
    public Action getPreferredAction() {
        return null;
    }

}

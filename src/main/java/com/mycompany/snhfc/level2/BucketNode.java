package com.mycompany.snhfc.level2;

import com.couchbase.client.java.Bucket;
import java.beans.IntrospectionException;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;

public class BucketNode extends BeanNode {

    @StaticResource
    private static final String ICON = "com/mycompany/snhfc/level2/icon.png";

    public BucketNode(Bucket bucket) throws IntrospectionException {
        super(bucket, Children.LEAF);
        setDisplayName(bucket.name());
        setIconBaseWithExtension(ICON);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{};
    }

    @Override
    public Action getPreferredAction() {
        return null;
    }

}

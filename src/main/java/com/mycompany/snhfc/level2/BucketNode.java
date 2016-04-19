package com.mycompany.snhfc.level2;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.mycompany.snhfc.level3.DocumentChildFactory;
import java.awt.Image;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class BucketNode extends BeanNode {

    private static final String BASE_ICON_PATH = "com/mycompany/snhfc/level2/";
    @StaticResource
    private static final String ICON = BASE_ICON_PATH + "icon.png";
    @StaticResource
    private static final String INDEXED = BASE_ICON_PATH + "index_icon.png";
    @StaticResource
    private static final String UNINDEXED = BASE_ICON_PATH + "unindexed_icon.png";

    public BucketNode(Bucket bean) throws IntrospectionException {
        this(bean, new InstanceContent());
    }

    public BucketNode(Bucket bucket, InstanceContent ic) throws IntrospectionException {
        super(bucket,
              Children.create(new DocumentChildFactory(bucket), true),
              new AbstractLookup(ic)
        );
        ic.add(bucket);
        ic.add(this);
        setDisplayName(bucket.name());
        setIconBaseWithExtension(ICON);
    }

    public void refresh() {
        fireIconChange();
        fireOpenedIconChange();
    }

     @Override
    public Image getIcon(int type) {
        return getCurrentImage();
    }

    private Image getCurrentImage() {
        Bucket bucket = getLookup().lookup(Bucket.class);
        Image original = ImageUtilities.loadImage(ICON);
        N1qlQueryResult query = bucket.query(N1qlQuery.simple(String.format("select * from system:indexes where keyspace_id = '" + bucket.name() + "'", bucket.name())));
        int size = query.allRows().size();
        if (size > 0) {
            return ImageUtilities.mergeImages(original, ImageUtilities.loadImage(INDEXED), 7, 7);
        } else {
            return ImageUtilities.mergeImages(original, ImageUtilities.loadImage(UNINDEXED), 7, 7);
        }
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getCurrentImage();
    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> bucketActions = Utilities.actionsForPath("Actions/Bucket");
        return bucketActions.toArray(new Action[bucketActions.size()]);
    }

    @Override
    public Action getPreferredAction() {
        return null;
    }

}

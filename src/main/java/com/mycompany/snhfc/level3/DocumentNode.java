package com.mycompany.snhfc.level3;

import com.couchbase.client.java.query.N1qlQueryRow;
import java.beans.IntrospectionException;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;

public class DocumentNode extends BeanNode {

    @StaticResource
    private static final String ICON = "com/mycompany/snhfc/level3/icon.png";

    public DocumentNode(int id, N1qlQueryRow row) throws IntrospectionException {
        super(row, Children.LEAF);
        setDisplayName(String.valueOf(id));
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

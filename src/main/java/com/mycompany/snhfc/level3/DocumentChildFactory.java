package com.mycompany.snhfc.level3;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

public class DocumentChildFactory extends ChildFactory<String> {

    @Override
    protected boolean createKeys(List<String> list) {
        try {
            Thread.sleep(500);
            list.add("three");
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(String key) {
        DocumentNode node = null;
        try {
            node = new DocumentNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
    
}

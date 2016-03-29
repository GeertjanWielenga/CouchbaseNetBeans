package com.mycompany.snhfc.level4;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

public class Level4ChildFactory extends ChildFactory<String> {

    @Override
    protected boolean createKeys(List<String> list) {
        list.add("four");
        return true;
    }

    @Override
    protected Node createNodeForKey(String key) {
        Level4Node node = null;
        try {
            node = new Level4Node(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
    
}

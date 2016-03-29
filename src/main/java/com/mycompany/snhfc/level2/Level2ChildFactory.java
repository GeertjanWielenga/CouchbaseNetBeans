package com.mycompany.snhfc.level2;

import java.beans.IntrospectionException;
import java.util.List;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

public class Level2ChildFactory extends ChildFactory<String> {

    @Override
    protected boolean createKeys(List<String> list) {
        String msg = "Processing Level 2...";
        ProgressHandle handle = ProgressHandleFactory.createSystemHandle(msg);
        handle.start();
        try {
            Thread.sleep(500);
            list.add("two");
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
        handle.finish();
        return true;
    }

    @Override
    protected Node createNodeForKey(String key) {
        Level2Node node = null;
        try {
            node = new Level2Node(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

}

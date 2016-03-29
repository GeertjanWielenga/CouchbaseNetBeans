package com.mycompany.snhfc;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.db.explorer.node.NodeProvider;
import org.netbeans.api.db.explorer.node.NodeProviderFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

public class RootNodeProvider extends NodeProvider {
    
    // lazy initialization holder class idiom for static fields is used
    // for retrieving the factory
    public static NodeProviderFactory getFactory() {
        return FactoryHolder.FACTORY;
    }

    private static class FactoryHolder {
        static final NodeProviderFactory FACTORY = new NodeProviderFactory() {
            @Override
            public RootNodeProvider createInstance(Lookup lookup) {
                RootNodeProvider provider = new RootNodeProvider(lookup);
                return provider;
            }
        };
    }

    private RootNodeProvider(Lookup lookup) {
        super(lookup);
    }

    @Override
    protected synchronized void initialize() {
        List<Node> newList = new ArrayList<Node>();
        newList.add(RootNode.getDefault());
        setNodes(newList);
    }
    
}

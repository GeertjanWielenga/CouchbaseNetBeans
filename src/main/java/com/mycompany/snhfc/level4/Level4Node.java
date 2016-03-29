package com.mycompany.snhfc.level4;

import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;

public class Level4Node extends BeanNode {
    
    public Level4Node(String bean) throws IntrospectionException {
        super(bean);
        setDisplayName(bean);
    }
    
}

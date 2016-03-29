package com.mycompany.snhfc.level1;

import com.mycompany.snhfc.level2.Level2ChildFactory;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;

public class Level1Node extends BeanNode {
    
    public Level1Node(String bean) throws IntrospectionException {
        super(bean, Children.create(new Level2ChildFactory(), true));
        setDisplayName(bean);
    }
    
}

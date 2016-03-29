package com.mycompany.snhfc.level2;

import com.mycompany.snhfc.level3.Level3ChildFactory;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;

public class Level2Node extends BeanNode {
    
    public Level2Node(String bean) throws IntrospectionException {
        super(bean, Children.create(new Level3ChildFactory(), true));
        setDisplayName(bean);
    }
    
}

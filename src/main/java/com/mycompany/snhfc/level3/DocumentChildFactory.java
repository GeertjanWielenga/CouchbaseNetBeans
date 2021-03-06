package com.mycompany.snhfc.level3;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import static com.couchbase.client.java.query.Select.select;
import com.couchbase.client.java.query.SimpleN1qlQuery;
import static com.couchbase.client.java.query.dsl.Expression.i;
import com.mycompany.snhfc.level1.ConnectionNode;
import java.beans.IntrospectionException;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

public class DocumentChildFactory extends ChildFactory.Detachable<DocumentChildFactory.RowAndIdObject> implements PreferenceChangeListener {

    private final Bucket bucket;
    private int newTotal = 0;

    public DocumentChildFactory(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    protected boolean createKeys(List<RowAndIdObject> list) {
        String name = bucket.name();
        if (newTotal==0){
            newTotal = 3;
        }
        SimpleN1qlQuery simple = N1qlQuery.simple(select("*").from(i(name)).limit(newTotal));
        N1qlQueryResult query = bucket.query(simple);
        List<N1qlQueryRow> allRows = query.allRows();
        for (int i = 0; i < allRows.size(); i++) {
            N1qlQueryRow row = allRows.get(i);
            list.add(new RowAndIdObject(i, row));
        }
        return true;
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        if (evt.getKey().contains("defaultNumber")) {
            newTotal = Integer.parseInt(evt.getNewValue());
            refresh(true);
            StatusDisplayer.getDefault().setStatusText("Refresh...");
        }
    }

    @Override
    protected void addNotify() {
        NbPreferences.forModule(ConnectionNode.class).addPreferenceChangeListener(this);
    }

    @Override
    protected void removeNotify() {
        NbPreferences.forModule(ConnectionNode.class).removePreferenceChangeListener(this);
    }

    public class RowAndIdObject {
        int id;
        N1qlQueryRow row;
        public RowAndIdObject(int id, N1qlQueryRow row) {
            this.id = id;
            this.row = row;
        }
        public N1qlQueryRow getRow() {
            return row;
        }
        public int getId() {
            return id;
        }
    }

    @Override
    protected Node createNodeForKey(RowAndIdObject key) {
        DocumentNode node = null;
        try {
            node = new DocumentNode(key.getId(), key.getRow());
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

}

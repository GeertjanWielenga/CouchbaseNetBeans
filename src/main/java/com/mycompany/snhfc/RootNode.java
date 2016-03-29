package com.mycompany.snhfc;

import com.mycompany.snhfc.level1.ConnectionChildFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.JButton;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.*;
import org.openide.actions.NewAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.*;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

@NbBundle.Messages("CTL_DisplayName=Couchbase")
public class RootNode extends AbstractNode {

    @StaticResource
    private static final String ICON = "com/mycompany/snhfc/icon.png";

    private static final RootNode DEFAULT = new RootNode();

    private String serverName;
    private String address;
    private String login;
    private String password;

    public static RootNode getDefault() {
        return DEFAULT;
    }

    public RootNode() {
        super(Children.create(new ConnectionChildFactory(), true));
        setDisplayName(Bundle.CTL_DisplayName());
        setIconBaseWithExtension(ICON);
    }

    @Override
    public Action getPreferredAction() {
        return null;
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{
            SystemAction.get(NewAction.class)
        };
    }

    @NbBundle.Messages({
        "LBL_Title=Database Server..."})
    @Override
    public NewType[] getNewTypes() {
        return new NewType[]{
            new NewType() {
                @Override
                public String getName() {
                    return Bundle.LBL_Title();
                }
                @Override
                public void create() throws IOException {
                    final LoginForm form = new LoginForm();
                    NotifyDescriptor.Confirmation nd = new NotifyDescriptor.Confirmation(
                            form,
                            Bundle.LBL_Title());
                    JButton ok = new JButton();
                    ok.setText("OK");
                    JButton cancel = new JButton();
                    cancel.setText("Cancel");
                    nd.setOptions(new Object[]{ok, cancel});
                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            serverName = form.getServerName();
                            address = form.getAddress();
                            login = form.getLogin();
                            password = form.getPassword();
                            NbPreferences.forModule(RootNode.class).put("serverName", serverName);
                            NbPreferences.forModule(RootNode.class).put("serverAddress", address);
                            NbPreferences.forModule(RootNode.class).put("serverLogin", login);
                            NbPreferences.forModule(RootNode.class).put("serverPassword", password);
                        }
                    });
                    DialogDisplayer.getDefault().notify(nd);
                }
            }
        };

    }

}

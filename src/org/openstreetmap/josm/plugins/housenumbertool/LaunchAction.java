package org.openstreetmap.josm.plugins.housenumbertool;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.SelectionChangedListener;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.tools.Shortcut;

public class LaunchAction extends JosmAction implements SelectionChangedListener
{

   /**
    * 
    */
   private static final long serialVersionUID = -3508864293222033185L;
   private Collection<? extends OsmPrimitive> selection = null;

   private String pluginDir;
   
   public LaunchAction(String pluginDir)
   {
      super("HouseNumberTaggingTool", 
            "home-icon32", 
            "Launches the HouseNumberTaggingTool dialog", 
            Shortcut.registerShortcut("edit:housenumbertaggingtool", "HouseNumberTaggingTool", KeyEvent.VK_K, Shortcut.CTRL),
            true);

      this.pluginDir = pluginDir;
      DataSet.addSelectionListener(this);
      setEnabled(false);
      
   }

    public void actionPerformed(ActionEvent e)
   {
       if (!isEnabled())
       {
          return;
       }

       TagDialog dialog = new TagDialog(pluginDir, selection);
       dialog.showDialog();
   }

   public void selectionChanged(Collection<? extends OsmPrimitive> newSelection)
   {
       setEnabled(newSelection != null);
       selection  =  newSelection;
   }
}

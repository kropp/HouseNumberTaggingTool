package org.openstreetmap.josm.plugins.housenumbertool;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MainMenu;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;

public class HouseNumberTaggingToolPlugin extends Plugin
{
   LaunchAction action;
   NextHouseAutomaticTaggingAction action2;

   /**
    * constructor
    */
   public HouseNumberTaggingToolPlugin(PluginInformation info)
   {
      super(info);

      action = new LaunchAction(getPluginDir());
      action2 = new NextHouseAutomaticTaggingAction(getPluginDir());

      MainMenu.add(Main.main.menu.editMenu, action);
      MainMenu.add(Main.main.menu.editMenu, action2);
   }
}

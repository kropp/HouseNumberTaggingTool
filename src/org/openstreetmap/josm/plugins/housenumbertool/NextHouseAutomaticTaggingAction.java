package org.openstreetmap.josm.plugins.housenumbertool;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.SelectionChangedListener;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.tools.Shortcut;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;

/**
 * @author Victor Kropp 12.03.2012 <http://victor.kropp.name>
 */
public class NextHouseAutomaticTaggingAction extends JosmAction implements SelectionChangedListener {
    private OsmPrimitive selection = null;

    private DtoReaderWriter dtoReaderWriter;

    public NextHouseAutomaticTaggingAction(String pluginDir) {
        super("HouseNumberAutomaticTagging",
                "home-icon-auto32",
                "Tags next house on the same street automatic",
                Shortcut.registerShortcut("edit:housenumberautomatictagging", "HouseNumberAutomaticTagging", KeyEvent.VK_K, Shortcut.DIRECT),
                true);

        dtoReaderWriter = new DtoReaderWriter(pluginDir);

        DataSet.addSelectionListener(this);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Dto dto = dtoReaderWriter.loadDto(selection);

        int housenumber = 0;
        try {
            housenumber = Integer.valueOf(dto.getHousenumber());
        } catch (NumberFormatException e) {
        }

        if (housenumber > 0) {
            housenumber += 2; // add an option later
            dto.setHousenumber(String.valueOf(housenumber));
        }

        OsmPrimitiveUpdater.updateJOSMSelection(selection, dto);

        dtoReaderWriter.saveDto(dto);
    }

    @Override
    public void selectionChanged(Collection<? extends OsmPrimitive> newSelection) {
        if ((newSelection != null && newSelection.size() == 1)) {
            setEnabled(true);
            selection = newSelection.iterator().next();
        } else {
            setEnabled(false);
            selection = null;
        }
    }
}
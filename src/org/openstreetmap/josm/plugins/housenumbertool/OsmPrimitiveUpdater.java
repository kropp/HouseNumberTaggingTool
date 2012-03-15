package org.openstreetmap.josm.plugins.housenumbertool;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangePropertyCommand;
import org.openstreetmap.josm.command.Command;
import org.openstreetmap.josm.command.SequenceCommand;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.tools.I18n;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Oliver Raupach 09.01.2012 <http://www.oliver-raupach.de>
 * @author Victor Kropp 12.03.2012 <http://victor.kropp.name>
 */
public class OsmPrimitiveUpdater {
    protected static void updateJOSMSelection(OsmPrimitive selection, Dto dto) {
        ArrayList<Command> commands = new ArrayList<Command>();

        if (dto.isSaveBuilding()) {
            String value = selection.get(TagDialog.TAG_BUILDING);
            if (value == null || (value != null && !value.equals("yes"))) {
                ChangePropertyCommand command = new ChangePropertyCommand(selection, TagDialog.TAG_BUILDING, "yes");
                commands.add(command);
            }
        }

        if (dto.isSaveCity()) {
            String value = selection.get(TagDialog.TAG_ADDR_CITY);
            if (value == null || (value != null && !value.equals(dto.getCity()))) {
                ChangePropertyCommand command = new ChangePropertyCommand(selection, TagDialog.TAG_ADDR_CITY, dto.getCity());
                commands.add(command);
            }
        }

        if (dto.isSaveCountry()) {
            String value = selection.get(TagDialog.TAG_ADDR_COUNTRY);
            if (value == null || (value != null && !value.equals(dto.getCountry()))) {
                ChangePropertyCommand command = new ChangePropertyCommand(selection, TagDialog.TAG_ADDR_COUNTRY, dto.getCountry());
                commands.add(command);
            }
        }

        if (dto.isSaveHousenumber()) {
            String value = selection.get(TagDialog.TAG_ADDR_HOUSENUMBER);
            if (value == null || (value != null && !value.equals(dto.getHousenumber()))) {
                ChangePropertyCommand command = new ChangePropertyCommand(selection, TagDialog.TAG_ADDR_HOUSENUMBER, dto.getHousenumber());
                commands.add(command);
            }
        }

        if (dto.isSavePostcode()) {
            String value = selection.get(TagDialog.TAG_ADDR_POSTCODE);
            if (value == null || (value != null && !value.equals(dto.getPostcode()))) {
                ChangePropertyCommand command = new ChangePropertyCommand(selection, TagDialog.TAG_ADDR_POSTCODE, dto.getPostcode());
                commands.add(command);
            }
        }

        if (dto.isSaveStreet()) {
            String value = selection.get(TagDialog.TAG_ADDR_STREET);
            if (value == null || (value != null && !value.equals(dto.getStreet()))) {
                ChangePropertyCommand command = new ChangePropertyCommand(selection, TagDialog.TAG_ADDR_STREET, dto.getStreet());
                commands.add(command);
            }
        }

        if (dto.isSaveState()) {
            String value = selection.get(TagDialog.TAG_ADDR_STATE);
            if (value == null || (value != null && !value.equals(dto.getState()))) {
                ChangePropertyCommand command = new ChangePropertyCommand(selection, TagDialog.TAG_ADDR_STATE, dto.getState());
                commands.add(command);
            }
        }

        if (commands.size() > 0) {
            SequenceCommand sequenceCommand = new SequenceCommand(I18n.trn("Updating properties of up to {0} object", "Updating properties of up to {0} objects", commands.size(), commands.size()), commands);

            // executes the commands and adds them to the undo/redo chains
            Main.main.undoRedo.add(sequenceCommand);
        }
    }
}
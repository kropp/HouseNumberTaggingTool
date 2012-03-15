package org.openstreetmap.josm.plugins.housenumbertool;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.TreeSet;

import javax.swing.*;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.gui.ExtendedDialog;
import org.openstreetmap.josm.gui.tagging.ac.AutoCompletingComboBox;
import org.openstreetmap.josm.gui.tagging.ac.AutoCompletionListItem;
import org.openstreetmap.josm.gui.tagging.ac.AutoCompletionManager;

/**
 * @author Oliver Raupach 09.01.2012 <http://www.oliver-raupach.de>
 * @author Victor Kropp 10.03.2012 <http://victor.kropp.name>
 */
public class TagDialog extends ExtendedDialog {
    public static final String TAG_BUILDING = "building";
    public static final String TAG_ADDR_COUNTRY = "addr:country";
    public static final String TAG_ADDR_STATE = "addr:state";
    public static final String TAG_ADDR_CITY = "addr:city";
    public static final String TAG_ADDR_POSTCODE = "addr:postcode";
    public static final String TAG_ADDR_HOUSENUMBER = "addr:housenumber";
    public static final String TAG_ADDR_STREET = "addr:street";
    /**
     *
     */
    private static final long serialVersionUID = 6414385452106276923L;

    private AutoCompletionManager acm;
    private OsmPrimitive selection;

    public static final String TEMPLATE_DATA = "/template.data";

    private AutoCompletingComboBox country;
    private AutoCompletingComboBox state;
    private AutoCompletingComboBox city;
    private AutoCompletingComboBox postcode;
    private AutoCompletingComboBox street;
    private JTextField housnumber;
    private final DtoReaderWriter dtoReaderWriter;

    public TagDialog(String pluginDir, OsmPrimitive p_selection) {
        super(Main.parent,
                tr("House Number Editor"),
                new String[]{tr("OK"), tr("Cancel")},
                true
        );
        dtoReaderWriter = new DtoReaderWriter(pluginDir);
        this.selection = p_selection;

        JPanel editPanel = createContentPane();


        setContent(editPanel);
        setButtonIcons(new String[]{"ok.png", "cancel.png"});
        setDefaultButton(1);
        setupDialog();
        getRootPane().setDefaultButton(defaultButton);

        // middle of the screen
        setLocationRelativeTo(null);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                housnumber.requestFocus();
                housnumber.selectAll();
            }
        });
    }

    private JPanel createContentPane() {
        acm = selection.getDataSet().getAutoCompletionManager();

        Dto dto = dtoReaderWriter.loadDto(selection);

        JPanel editPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel buildingLabel = new JLabel(TAG_BUILDING);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        editPanel.add(buildingLabel, c);

        JTextField building = new JTextField();
        building.setPreferredSize(new Dimension(200, 24));
        building.setText("yes");
        building.setEditable(false);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        editPanel.add(building, c);

        // country
        JLabel countryLabel = new JLabel(TAG_ADDR_COUNTRY);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        editPanel.add(countryLabel, c);

        country = new AutoCompletingComboBox();
        country.setPossibleACItems(acm.getValues(TAG_ADDR_COUNTRY));
        country.setPreferredSize(new Dimension(200, 24));
        country.setEditable(true);
        country.setSelectedItem(dto.getCountry());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        editPanel.add(country, c);

        // state
        JLabel stateLabel = new JLabel(TAG_ADDR_STATE);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        editPanel.add(stateLabel, c);

        state = new AutoCompletingComboBox();
        state.setPossibleACItems(acm.getValues(TAG_ADDR_STATE));
        state.setPreferredSize(new Dimension(200, 24));
        state.setEditable(true);
        state.setSelectedItem(dto.getState());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        editPanel.add(state, c);

        // city
        JLabel cityLabel = new JLabel(TAG_ADDR_CITY);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        editPanel.add(cityLabel, c);

        city = new AutoCompletingComboBox();
        city.setPossibleACItems(acm.getValues(TAG_ADDR_CITY));
        city.setPreferredSize(new Dimension(200, 24));
        city.setEditable(true);
        city.setSelectedItem(dto.getCity());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 1;
        editPanel.add(city, c);

        // postcode
        JLabel zipEnabled = new JLabel(TAG_ADDR_POSTCODE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0;
        editPanel.add(zipEnabled, c);

        postcode = new AutoCompletingComboBox();
        postcode.setPossibleACItems(acm.getValues(TAG_ADDR_POSTCODE));
        postcode.setPreferredSize(new Dimension(200, 24));
        postcode.setEditable(true);
        postcode.setSelectedItem(dto.getPostcode());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 1;
        editPanel.add(postcode, c);

        // street
        JLabel streetLabel = new JLabel(TAG_ADDR_STREET);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0;
        editPanel.add(streetLabel, c);

        street = new AutoCompletingComboBox();
        street.setPossibleItems(getPossibleStreets());
        street.setPreferredSize(new Dimension(200, 24));
        street.setEditable(true);
        street.setSelectedItem(dto.getStreet());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        c.weightx = 1;
        editPanel.add(street, c);

        // housenumber
        JLabel houseNumberLabel = new JLabel(TAG_ADDR_HOUSENUMBER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 0;
        editPanel.add(houseNumberLabel, c);

        housnumber = new JTextField();
        housnumber.setPreferredSize(new Dimension(200, 24));
        int number = 0;
        try {
            number = Integer.valueOf(dto.getHousenumber()) + 2;
        } catch (NumberFormatException e) {
        }
        if (number > 0)
            housnumber.setText(String.valueOf(number));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 6;
        c.weightx = 1;
        editPanel.add(housnumber, c);
        return editPanel;
    }

    @Override
    protected void buttonAction(int buttonIndex, ActionEvent evt) {
        if (buttonIndex == 0) {
            Dto dto = new Dto();
            dto.setCity(getAutoCompletingComboBoxValue(city));
            dto.setCountry(getAutoCompletingComboBoxValue(country));
            dto.setHousenumber(housnumber.getText());
            dto.setPostcode(getAutoCompletingComboBoxValue(postcode));
            dto.setStreet(getAutoCompletingComboBoxValue(street));
            dto.setState(getAutoCompletingComboBoxValue(state));

            OsmPrimitiveUpdater.updateJOSMSelection(selection, dto);
            dtoReaderWriter.saveDto(dto);
        }
        setVisible(false);
    }

    private String getAutoCompletingComboBoxValue(AutoCompletingComboBox box) {
        Object item = box.getSelectedItem();
        if (item != null) {
            if (item instanceof String) {
                return (String) item;
            }
            if (item instanceof AutoCompletionListItem) {
                return ((AutoCompletionListItem) item).getValue();
            }
            return item.toString();
        } else {
            return "";
        }
    }

    private Collection<String> getPossibleStreets() {
        /**
         * Generates a list of all visible names of highways in order to do
         * autocompletion on the road name.
         */
        final TreeSet<String> names = new TreeSet<String>();
        for (OsmPrimitive osm : Main.main.getCurrentDataSet()
                .allNonDeletedPrimitives()) {
            if (osm.getKeys() != null && osm.keySet().contains("highway")
                    && osm.keySet().contains("name")) {
                names.add(osm.get("name"));
            }
        }
        return names;
    }
}

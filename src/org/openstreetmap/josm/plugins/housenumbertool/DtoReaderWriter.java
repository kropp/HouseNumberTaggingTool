package org.openstreetmap.josm.plugins.housenumbertool;

import org.openstreetmap.josm.data.osm.OsmPrimitive;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Oliver Raupach 09.01.2012 <http://www.oliver-raupach.de>
 * @author Victor Kropp 12.03.2012 <http://victor.kropp.name>
 */
public class DtoReaderWriter {
    private final String pluginDir;
    static private final Logger logger = Logger.getLogger(DtoReaderWriter.class.getName());


    public DtoReaderWriter(String pluginDir) {
        this.pluginDir = pluginDir;
    }

    protected void saveDto(Dto dto) {
        File path = new File(pluginDir);
        File fileName = new File(pluginDir + TagDialog.TEMPLATE_DATA);

        try {

            path.mkdirs();


            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream o = new ObjectOutputStream(file);
            o.writeObject(dto);
            o.close();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());

            if (fileName != null) {
                fileName.delete();
            }
        }
    }

    Dto loadDto(OsmPrimitive selection) {
        Dto dto = new Dto();
        File fileName = new File(pluginDir + TagDialog.TEMPLATE_DATA);

        try {

            if (fileName.exists()) {
                FileInputStream file = new FileInputStream(fileName);
                ObjectInputStream o = new ObjectInputStream(file);

                dto = (Dto) o.readObject();
                o.close();
            }

            // get data from selection
            String city = selection.get(TagDialog.TAG_ADDR_CITY);
            if (city != null)
                dto.setCity(city);
            String suburb = selection.get(TagDialog.TAG_ADDR_SUBURB);
            if (suburb != null)
                dto.setSuburb(suburb);
            String country = selection.get(TagDialog.TAG_ADDR_COUNTRY);
            if (country != null)
                dto.setCountry(country);
            String housenumber = selection.get(TagDialog.TAG_ADDR_HOUSENUMBER);
            if (housenumber != null)
                dto.setHousenumber(housenumber);
            String postcode = selection.get(TagDialog.TAG_ADDR_POSTCODE);
            if (postcode != null)
                dto.setPostcode(postcode);
            String street = selection.get(TagDialog.TAG_ADDR_STREET);
            if (street != null)
                dto.setStreet(street);
            String state = selection.get(TagDialog.TAG_ADDR_STATE);
            if (state != null)
                dto.setState(state);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            if (fileName != null) {
                fileName.delete();
            }
        }

        return dto;

    }
}
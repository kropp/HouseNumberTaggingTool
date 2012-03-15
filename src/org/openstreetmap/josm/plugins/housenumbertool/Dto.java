package org.openstreetmap.josm.plugins.housenumbertool;

import java.io.Serializable;

/**
 * @author Oliver Raupach 18.01.2012 <http://www.oliver-raupach.de>
 */
public class Dto implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -4025800761473341695L;

   private String country;
   private String state;
   private String city;
   private String postcode;
   private String street;
   private String housenumber;

   public String getCountry()
   {
      return country;
   }

   public void setCountry(String country)
   {
      this.country = country;
   }

   public String getCity()
   {
      return city;
   }

   public void setCity(String city)
   {
      this.city = city;
   }

   public String getPostcode()
   {
      return postcode;
   }

   public void setPostcode(String postcode)
   {
      this.postcode = postcode;
   }

   public String getStreet()
   {
      return street;
   }

   public void setStreet(String street)
   {
      this.street = street;
   }

   public String getHousenumber()
   {
      return housenumber;
   }

   public void setHousenumber(String housenumber)
   {
      this.housenumber = housenumber;
   }

   public String getState()
   {
      return state;
   }

   public void setState(String state)
   {
      this.state = state;
   }
}
//=========================================================================
//
//  Part of units package -- a Java version of GNU Units program.
//
//  Units is a program for unit conversion originally written in C
//  by Adrian Mariano (adrian@cam.cornell.edu.).
//  Copyright (C) 1996, 1997, 1999, 2000, 2001, 2002, 2003, 2004,
//  2005, 2006, 2007, 2009, 2011 by Free Software Foundation, Inc.
//
//  Java version Copyright (C) 2003, 2004, 2005, 2006, 2007, 2008,
//  2009, 2012 by Roman R Redziejowski (www.romanredz.se).
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program. If not, see <http://www.gnu.org/licenses/>.
//
//-------------------------------------------------------------------------
//
//  Change log
//
//  Version 1.84.J05.
//    050203 Check number of files in 'build'.
//           Initialize tables in 'build'.
//           Added method 'clean'.
//           Added title parameter to calls for Browser.
//
//  Version 1.84.J07.
//    050315 Changed package name to 'units'.
//
//  Version 1.86.J01.
//    061231 Removed printing of statistics from 'build'.
//           Added method 'stat' to obtain statistics.
//
//  Version 1.87.J01.
//    091024 Used generics for 'Unit.table', 'Prefix.table',
//           BuiltInFunction.table', 'DefinedFunction.table', and
//           'list' in 'showConformable'.
//    091031 Replaced 'addtolist' by 'isCompatibleWith'
//           and 'insertAlph' by 'Collections.sort'.
//    091101 Implemented 'search'  ('showMatching').
//
//  Version 1.89.J01.
//    120121 Renamed 'File' to 'UnitsFile'.
//           Used generics for the three Enumerations in 'check'.
//    120123 Prepared for include files being added to 'Env.filenames';
//           see under 'Read unit definitions' in 'build'.
//    120201 Instantiate Alias table.
//    120208 Renamed one-argument method 'isCompatibleWith'
//           to 'conformsTo' to avoid confusion with two-argument one
//           defined in Product and Value.
//    120225 Added Alias table in 'showConformable',
//           'showMatching', 'check', 'stat', and 'clean'.
//    120228 Replaced use of 'Env.err' by 'Env.out'.
//    120303 Added method 'showdef' to show a complete
//           definition of a unit name, function name, or alias.
//    120309 Added Alias table in 'showSource'.
//    120316 Added Prefix table in 'showMatching' and 'showConformable'.
//    120318 Removed check for multiple invocations.
//           It was superfluous: each invocation has own static variables.
//
//=========================================================================

package units;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  Class Tables
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
/**
 *  Contains static methods for maintenance of tables and extracting
 *  information from tables. Is never instantiated.
 */

class Tables
{
  //=====================================================================
  //  build
  //=====================================================================
  /**
   *  Build tables from given definition files.
   *
   *  @return true if success, false otherwise.
   */
  static boolean build()
    {
      //---------------------------------------------------------------
      //  Check number of files.
      //---------------------------------------------------------------
      if (Env.filenames.size()>=Env.MAXFILES)
      {
        Env.out.println
          ("At most " + Env.MAXFILES + " unit definition files are allowed.");
        return false;
      }

      //---------------------------------------------------------------
      //  Initialize all tables.
      //---------------------------------------------------------------
      Unit.table            = new Hashtable<String,Unit>();
      Prefix.table          = new Hashtable<String,Prefix>();
      BuiltInFunction.table = new Hashtable<String,BuiltInFunction>();
      DefinedFunction.table = new Hashtable<String,DefinedFunction>();
      Alias.table           = new Hashtable<String,Alias>();

      //---------------------------------------------------------------
      //  Read unit definitions.
      //  Note that 'Env.filenames' may be extended with include files
      //  by 'file.readunits'; we do not want to process them.
      //---------------------------------------------------------------
      int nfiles = Env.filenames.size();
      for (int i=0; i<nfiles; i++)
      {
        String filename = Env.filenames.elementAt(i);
        if (filename.length()==0) filename = Env.UNITSFILE;
        UnitsFile file = new UnitsFile(filename);
        boolean ok = file.readunits(0);
        if (!ok) return false;
      }

      //---------------------------------------------------------------
      //  Fill table of built-in functions.
      //---------------------------------------------------------------
      BuiltInFunction.makeTable();

      return true;
    }

  //=====================================================================
  //  clean
  //=====================================================================
  /**
   *  Removes all tables.
   */
  static void clean()
    {
      Unit.table = null;
      Prefix.table = null;
      BuiltInFunction.table = null;
      DefinedFunction.table = null;
      Alias.table = null;
    }

  //=====================================================================
  //  stat
  //=====================================================================
  /**
   *  Returns string showing numbers of different entities.
   */
   static String stat()
   {
     return Unit.table.size() + " units, " + Prefix.table.size() + " prefixes, "
            + DefinedFunction.table.size() + " nonlinear units, "
            + Alias.table.size() + " unit lists." ;
   }

  //=====================================================================
  //  showSource
  //=====================================================================
  /**
   *  Shows portion of definition file containing definition
   *  of named entity.
   *
   *  @param  name name of the entity
   */
  static void showSource(final String name)
    {
      Location loc;

      Alias a = Alias.table.get(name);
      Function f = DefinedFunction.table.get(name);
      Unit u = Unit.table.get(name);
      Prefix p = Prefix.table.get(name);
      if (a!=null)      loc = a.location;
      else if (f!=null) loc = f.location;
      else if (u!=null) loc = u.location;
      else if (p!=null) loc = p.location;
      else
      {
        Env.out.println
            ("'" + name +
             "' is not the name of a unit, function, or unit list.");
        return;
      }

      Browser.show(loc.file.name,
                   loc.file.contents,
                   loc.beginChar,
                   loc.endChar,
                   true);
    }

  //=====================================================================
  //  showConformable
  //=====================================================================
  /**
   *  Shows units, functions, and aliases conformable to given Value.
   *  (Originally part of 'tryallunits'.)
   *
   *  @param  have the Value
   *  @param  havestr expression for the Value (used only in title)
   */
  static void showConformable(final Value have, final String havestr)
    {
      Vector<Entity> list = new Vector<Entity>();

      for (Enumeration<Alias> enu=Alias.table.elements();enu.hasMoreElements();)
      {
        Entity e = enu.nextElement();
        if (e.conformsTo(have)) list.add(e);
      }

      for(Enumeration<DefinedFunction> enu=DefinedFunction.table.elements();enu.hasMoreElements();)
      {
        Entity e = enu.nextElement();
        if (e.conformsTo(have)) list.add(e);
      }

      for (Enumeration<Unit> enu=Unit.table.elements();enu.hasMoreElements();)
      {
        Entity e = enu.nextElement();
        if (e.conformsTo(have)) list.add(e);
      }

      for (Enumeration<Prefix> enu=Prefix.table.elements();enu.hasMoreElements();)
      {
        Entity e = enu.nextElement();
        if (e.conformsTo(have)) list.add(e);
      }

      showListed(list,"Units conformable to " + havestr);
    }


  //=====================================================================
  //  showMatching
  //=====================================================================
  /**
   *  Shows units and functions with names containing a given substring.
   *  (Originally part of 'tryallunits'.)
   *
   *  @param  havestr substring to match
   */
  static void showMatching(final String havestr)
    {
      Vector<Entity> list = new Vector<Entity>();

      for (Enumeration<Alias> enu=Alias.table.elements();enu.hasMoreElements();)
      {
        Entity e = enu.nextElement();
        if (e.name.contains(havestr)) list.add(e);
      }

      for(Enumeration<DefinedFunction> enu=DefinedFunction.table.elements();enu.hasMoreElements();)
      {
        Entity e = enu.nextElement();
        if (e.name.contains(havestr)) list.add(e);
      }

      for (Enumeration<Unit> enu=Unit.table.elements();enu.hasMoreElements();)
      {
        Entity e = enu.nextElement();
        if (e.name.contains(havestr)) list.add(e);
      }

      for (Enumeration<Prefix> enu=Prefix.table.elements();enu.hasMoreElements();)
      {
        Entity e = enu.nextElement();
        if (e.name.contains(havestr)) list.add(e);
      }

      showListed(list,"Search result for *" + havestr + "*");
    }


  //=====================================================================
  //  showListed
  //=====================================================================
  /**
   *  Shows given list of entities.
   *
   *  @param  list  of entities to show
   *  @param  title to show in the window
   */
  private static void showListed(Vector<Entity> list, final String title)
    {
      Collections.sort(list);

      StringBuffer sb = new StringBuffer();

      for (int i=0; i<list.size(); i++)
      {
        Entity d = list.elementAt(i);
        sb.append(d.name).append(" ").append(d.desc()).append("\n");
      }

      Browser.show(title,sb.toString(),0,0,false);
    }


  //=====================================================================
  //  check
  //=====================================================================
  /**
   *  Checks all tables.
   *  (Originally 'checkunits'.)
   *  <br>
   *  Cycles through all functions, units, prefixes, and aliases
   *  applying their 'check' methods to check for correctnes.
   *  Prints messages about any error found.
   */
  static void check()
    {
      //---------------------------------------------------------------
      //  Check aliases.
      //---------------------------------------------------------------
      for (Enumeration<Alias> e=Alias.table.elements();e.hasMoreElements();)
        (e.nextElement()).check();

      //---------------------------------------------------------------
      //  Check functions.
      //---------------------------------------------------------------
      for(Enumeration<DefinedFunction> e=DefinedFunction.table.elements();e.hasMoreElements();)
        (e.nextElement()).check();

      //---------------------------------------------------------------
      //  Check prefixes.
      //---------------------------------------------------------------
      for (Enumeration<Prefix> e=Prefix.table.elements();e.hasMoreElements();)
        (e.nextElement()).check();

      //---------------------------------------------------------------
      //  Check units.
      //---------------------------------------------------------------
      for (Enumeration<Unit> e=Unit.table.elements();e.hasMoreElements();)
        (e.nextElement()).check();
    }


  //=====================================================================
  //  showdef
  //=====================================================================
  /**
   *  If the argument is the name of a function or unit list,
   *  returns its definition. Otherwise returns null.
   *  If 'showUnit' is true, returns definition also if the argument
   *  is the name of a unit or prefix.
   *
   *  @param  name the name.
   *  @param  showUnit if true, show unit or prefix definition.
   *  @return definition or null.
   */
  static String showdef(final String name, boolean showUnit)
    {
      //---------------------------------------------------------------
      //  Check first if 'name' is valid as an Entity name.
      //---------------------------------------------------------------
      if (Entity.checkName(name)!=null) return null;

      //---------------------------------------------------------------
      //  If 'name' is an alias, return its definition.
      //---------------------------------------------------------------
      String def = Alias.showdef(name);
      if (def!=null) return def;

      //---------------------------------------------------------------
      //  If 'name' is the name of a function, return its definition.
      //---------------------------------------------------------------
      def = DefinedFunction.showdef(name);
      if (def!=null) return def;

      //---------------------------------------------------------------
      //  Return if no check for unit or prefix.
      //---------------------------------------------------------------
      if (!showUnit) return null;

      //---------------------------------------------------------------
      //  If 'name' is the name of a unit, prefix, or combination
      //  of prefix and unit, return its definition.
      //---------------------------------------------------------------
      def = Factor.showdef(name);
      if (def!=null) return def;

      return null;
    }

 }

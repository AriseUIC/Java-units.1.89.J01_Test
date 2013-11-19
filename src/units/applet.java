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
//  2009, 2011, 2012 by Roman R Redziejowski (www.romanredz.se).
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
//    050203 Total rewrite using common GUI object
//           and common data in Env object.
//
//  Version 1.84.J07.
//    050315 Changed package name to 'units'.
//
//  Version 1.86.J01.
//    061229 New setting of options.
//    070103 Use 'Env.showAbout' to show initial message.
//
//  Version 1.87.J01.
//    091024 Added serialVersionUID.
//           Used generics for 'Env.filenames'.
//
//  Version 1.88.J02.
//    110219 Do not show initial message.
//           Show one-line result (no inverse value).
//
//  Version 1.89.J01.
//    120121 Renamed 'File' to 'UnitsFile'.
//    120318 Removed call to 'Browser.init'.
//    120326 Rewritten option setting. Added GUIFONT parameter.
//           'FileAcc' moved from Env to UnitsFile.
//
//=========================================================================

package units;

import java.util.Vector;
import javax.swing.*;



//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  class Applet
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
/**
 *  The applet version of Units.
 */
public class applet extends JApplet
{
  //-------------------------------------------------------------------
  /** Serial version UID.
   *  Unused - defined to eliminate compiler warning. */
  //-------------------------------------------------------------------
  static final long serialVersionUID = 4711L;


  //=====================================================================
  //  init
  //=====================================================================
  /**
   *  Applet milestone.
   */
  @Override
public void init()
    {
      //---------------------------------------------------------------
      //  Set up options.
      //---------------------------------------------------------------
      Env.filenames = new Vector<String>(); // Standard units file
      Env.filenames.add(Env.UNITSFILE);

      Env.locale = Env.LOCALE;              // Locale
      Env.encoding = null;                  // IO encoding name (unused)
      Env.font = Env.GUIFONT;               // GUI font name
      Env.verbose = 2;                      // Verbose output
      Env.quiet = false;                    // Don't suppress statistics
      Env.oneline = true;                   // Show one output line
      Env.strict = false;                   // Allow reciprocal conversion
      Env.unitcheck = false;                // No unit checking
      Env.round = false;                    // No unit list rounding

      String parm = getParameter("LOCALE"); // Set locale from parameter
      if (parm!=null) Env.locale = parm;    // .. if specified

      parm = getParameter("GUIFONT");       // GUI font from parameter
      if (parm!=null) Env.font = parm;      // .. if specified

      //---------------------------------------------------------------
      //  Set up file access.
      //---------------------------------------------------------------
      UnitsFile.fileAcc = new UnitsFile.AppletAcc();

      //---------------------------------------------------------------
      //  Start GUI.
      //---------------------------------------------------------------
      SwingUtilities.invokeLater(new Runnable()
        {  @Override
		public void run() { createGUI(); } });

    }


  //=====================================================================
  //  destroy
  //=====================================================================
  /**
   *  Applet milestone.
   */
  @Override
public void destroy()
    {
      Tables.clean();
      Browser.close();
      System.exit(0);
    }


  //=====================================================================
  //  Create GUI
  //=====================================================================
  /**
   *  Initializes GUI and builds tables.
   */
  private void createGUI()
    {
      //---------------------------------------------------------------
      //  Initialize GUI.
      //---------------------------------------------------------------
      setSize(getSize());
      setContentPane(new GUI(getRootPane()));
      setVisible(true);
      validate();       // Make sure GUI is drawn before next step.

      //---------------------------------------------------------------
      //  Build tables.
      //  It must be done after initialization of GUI because it writes
      //  output to GUI's text area (via GUI's writer 'myOut').
      //---------------------------------------------------------------
      Tables.build();
    }
}



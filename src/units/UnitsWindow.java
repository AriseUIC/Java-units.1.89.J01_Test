////=========================================================================
////
////  Part of units package -- a Java version of GNU Units program.
////
////  Units is a program for unit conversion originally written in C
////  by Adrian Mariano (adrian@cam.cornell.edu.).
////  Copyright (C) 1996, 1997, 1999, 2000, 2001, 2002, 2003, 2004,
////  2005, 2006, 2007, 2009, 2011 by Free Software Foundation, Inc.
////
////  Java version Copyright (C) 2003, 2004, 2005, 2006, 2007, 2008,
////  2009, 2011, 2012 by Roman R Redziejowski (www.romanredz.se).
////
////  This program is free software: you can redistribute it and/or modify
////  it under the terms of the GNU General Public License as published by
////  the Free Software Foundation, either version 3 of the License, or
////  (at your option) any later version.
////
////  This program is distributed in the hope that it will be useful,
////  but WITHOUT ANY WARRANTY; without even the implied warranty of
////  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
////  GNU General Public License for more details.
////
////  You should have received a copy of the GNU General Public License
////  along with this program. If not, see <http://www.gnu.org/licenses/>.
////
////-------------------------------------------------------------------------
////
////  Change log
////
////  Version 1.84.J05.
////    050203 Created.
////
////  Version 1.84.J07.
////    050315 Changed package name to 'units'.
////
////  Version 1.86.J01.
////    061229 New setting of options.
////    070103 Use 'Env.showAbout' to show initial message.
////
////  Version 1.87.J01.
////    091024 Added serialVersionUID.
////           Used generics for 'Env.filenames'.
////    091103 Implemented personal units file.
////
////  Version 1.88.J02.
////    110219 Do not show initial message.
////           Show one-line result (no inverse value).
////
////  Version 1.89.J01.
////    120121 Renamed 'File' to 'UnitsFile'.
////    120317 Append personal units file AFTER units.dat,
////           as in the original 'units'.
////    120318 Removed call to 'Browser.init'.
////    120326 Rewritten setting of options.
////           'FileAcc' moved from Env to UntisFile.
////
////=========================================================================
//
//package units;
//
//import java.util.Vector;
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//
//
////HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
////
////  class UnitsWindow
////
////HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
///**
// *  Window for GUI invoked from a JAR.
// */
//
//class UnitsWindow extends JFrame
//implements WindowListener
//{
//  //=====================================================================
//  //  Constructor.
//  //=====================================================================
//  UnitsWindow()
//    {
//      Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
//      int width = size.width/2;
//      int height = size.height/2;
//      setSize(width,height);
//      setLocation(width/2,height/2);
//      setContentPane(new GUI(getRootPane()));
//      setTitle("Units " + Env.VERSION);
//      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//      addWindowListener(this);
//      setResizable(true);
//      setVisible(true);
//      validate();       // Make sure GUI is drawn before next step.
//
//      //---------------------------------------------------------------
//      //  Build tables.
//      //  It must be done after initialization of GUI because it writes
//      //  output to GUI's text area (via GUI's writer 'myOut').
//      //---------------------------------------------------------------
//      Tables.build();
//    }
//
//
//  //=====================================================================
//  //  Initialize window
//  //=====================================================================
//  static void init()
//    {
//      //---------------------------------------------------------------
//      //  Set up options.
//      //---------------------------------------------------------------
//      Env.filenames = new Vector<String>(); // Unit definition files
//      Env.filenames.add(Env.UNITSFILE);
//      Env.getPersonalUnits();
//
//      Env.locale = Env.LOCALE;  // Locale
//      Env.encoding = null;      // IO encoding name (unused)
//      Env.font = Env.GUIFONT;   // GUI font
//      Env.verbose = 2;          // Verbose
//      Env.quiet = false;        // Don't suppress statistics
//      Env.oneline = true;       // Show one output line
//      Env.strict = false;       // Allow reciprocal conversion
//      Env.unitcheck = false;    // No unit checking
//      Env.round = false;        // Round last item of unit list
//
//      //---------------------------------------------------------------
//      //  Get option values from properties file (if any).
//      //  The UNITSFILE property, if specified, replaces all of
//      //  'Env.filenames' set in 'init'.
//      //  The LOCALE property, if specified, replaces 'Env.locale'.
//      //  The ENCODING property, if specified, replaces 'Env.encoding'.
//      //  The GUIFONT property, if specified, replaces 'Env.font'.
//      //---------------------------------------------------------------
//      Env.getProperties();
//
//      //---------------------------------------------------------------
//      //  Set up file access.
//      //---------------------------------------------------------------
//      UnitsFile.fileAcc = new UnitsFile.StandAcc();
//
//      //---------------------------------------------------------------
//      //  Start GUI.
//      //---------------------------------------------------------------
//      SwingUtilities.invokeLater(new Runnable()
//        {  @Override
//		public void run() { new UnitsWindow(); } });
//    }
//
//
//  //=====================================================================
//  //
//  //  windowListener interface
//  //  Needed because Java 1.2.2 does not have option EXIT_ON_CLOSE.
//  //
//  //=====================================================================
//  /**
//   *  Captures request to close the window and terminates Units.
//   */
//  @Override
//public void windowOpened(WindowEvent e){}
//  @Override
//public void windowClosed(WindowEvent e){}
//  @Override
//public void windowActivated(WindowEvent e){}
//  @Override
//public void windowDeactivated(WindowEvent e){}
//  @Override
//public void windowDeiconified(WindowEvent e){}
//  @Override
//public void windowIconified(WindowEvent e){}
//  @Override
//public void windowClosing(WindowEvent e) { System.exit(0); }
//
//
//  //=====================================================================
//  //  Serial version UID. Unused - defined to eliminate compiler warning
//  //=====================================================================
//  static final long serialVersionUID = 4711L;
//}
//
//

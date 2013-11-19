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
//    050203 Text area and scroll pane made into instance variables.
//           Added methods 'init' and 'close'.
//           Added window title to 'show'.
//
//  Version 1.84.J07.
//    050315 Changed package name to 'units'.
//
//  Version 1.87.J01.
//    091024 Added serialVersionUID.
//    091029 Defined margins and window position.
//
//  Version 1.89.J01.
//    120228 Replaced use of 'Env.err' by 'Env.out'.
//    120310 Set scroll mode to BLIT_SCROLL_MODE (said to be fastest).
//    120318 Removed method 'init'. The check for multiple invocations
//           was superfluous: each invocation has own static variables.
//    120326 Instantiate and use 'Env.guiFont'.
//
//=========================================================================

package units;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  class Browser
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
/**
 *  A window to display long text.
 *  <br>
 *  It is a singleton class: an existing window is always reused
 *  to display new text.
 */

class Browser extends JFrame
implements WindowListener
{
  /** Reference to the existing window. */
  private static Browser theWindow = null;

  /** Scroll pane and text area. */
  private JScrollPane pane;
  private JTextArea area;


  //=====================================================================
  //  Constructor
  //=====================================================================
  /**
   *  Creates an empty window.
   */
  Browser()
    {
      super("Units");

      if (Env.guiFont==null)
        Env.guiFont = new Font(Env.font,Font.PLAIN,12);

      Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
      int width = size.width;
      int height = size.height;
      setSize(width*5/9,height*24/25);
      setLocation(width/10,height/50);

      area = new JTextArea(10,10);
      area.setMargin(new Insets(10,10,10,10));
      area.setFont(Env.guiFont);
      area.setEditable(false);

      pane = new JScrollPane(area,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      getContentPane().add(pane);
      addWindowListener(this);
      setResizable(true);
      setVisible(true);
      validate();
    }


  //=====================================================================
  //  show
  //=====================================================================
  /**
   *  Displays given text, selects given portion,
   *  and optionally scrolls to show selected text.
   *
   *  @param  title for the window.
   *  @param  text the text to be displayed.
   *  @param  beginSel position of the first character to select.
   *  @param  endSel position of the last character to select.
   *  @param  scroll true = scroll to show selection in the middle of screen.
   *                 false = scroll to top.
   */
  static void show(final String title, final String text, int beginSel, int endSel, boolean scroll)
    {
      if (theWindow==null) theWindow = new Browser();

      JScrollPane sp = theWindow.pane;
      JTextArea ta = theWindow.area;

      theWindow.setTitle(title);
      ta.setText(text);

      int lineHeight = ta.getFontMetrics(ta.getFont()).getHeight();
      int midSel = (beginSel + endSel)/2;
      int scrollPos = 0;
      JViewport vp = sp.getViewport();
      vp.setScrollMode(JViewport.BLIT_SCROLL_MODE);
      if (scroll)
      {
        int line = 0;
        try {line = ta.getLineOfOffset(midSel);}
        catch (Exception e) {Env.out.println(e.getMessage());}
        int height = (int)(vp.getExtentSize().getHeight());
        scrollPos = line*lineHeight-height/2;
        if (scrollPos<0) scrollPos = 0;
      }

      theWindow.toFront();
      vp.setViewPosition(new Point(0,scrollPos));
      ta.select(beginSel,endSel);
    }

  //=====================================================================
  //  close
  //=====================================================================
  /**
   *  Releases (hopefully) the window resources.
   */
  static void close()
    {
      theWindow.dispose();
      theWindow = null;
    }



  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  //
  //  windowListener interface
  //
  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  /**
   *  Captures request to close the window and implements it.
   */
  @Override
public void windowOpened(WindowEvent e){}
  @Override
public void windowClosed(WindowEvent e){}
  @Override
public void windowActivated(WindowEvent e){}
  @Override
public void windowDeactivated(WindowEvent e){}
  @Override
public void windowDeiconified(WindowEvent e){}
  @Override
public void windowIconified(WindowEvent e){}
  @Override
public void windowClosing(WindowEvent e) { close(); }


  //=====================================================================
  //  Serial version UID. Unused - defined to eliminate compiler warning
  //=====================================================================
  static final long serialVersionUID = 4711L;
}
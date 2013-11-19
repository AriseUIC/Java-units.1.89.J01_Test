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
//    050203 Created.
//
//  Version 1.84.J06.
//    050226 Help text examples expanded and moved to 'Env'.
//
//  Version 1.84.J07.
//    050315  Changed package name to 'units'.
//
//  Version 1.86.J01.
//    061230 Changed 'Reduce' button to 'Definition' and rearranged buttons.
//           Show reduced value when 'Convert' pressed with empty 'You want'.
//    061231 Pressing 'Definition' shows the definition only if 'You have'
//           is a function name or a possibly prefixed unit name or a prefix.
//           Modified the HELP text accordingly.
//           Moved table statistics to the end of 'About' text.
//    070102 Suppress printing "\tDefinition :" and tabs for compact output.
//    070103 Use 'Env.showAbout' to show info about units.
//           If 'Source' pressed for prefix+unit, show source for unit.
//
//  Version 1.87.J01.
//     091024 Added serialVersionUID.
//     091028 Changed font in windows to Verdana.
//     091029 Specified margins and text wrapping for 'outArea'
//     091101 Added 'Search' button.
//
//  Version 1.88.J02.
//    110219 Renamed 'Convert' button to 'Compute'.
//           Modified HELP text to emphasize the use as calculator.
//    110403 Modified to accept String from 'Factor.showdef()'
//           and to start definition with the name of defined entity.
//           Removed setting of 'verbose' around printing of function def.
//
//  Version 1.88.J03.
//    110623 Updated HELP to show correctly rounded results.
//
//  Version 1.89.J01.
//    120208 Removed acccidental extra copy of 'Conformable units pressed'.
//    120213 Handle unit lists.
//    120228 Removed setting up of 'Env.err'.
//    120311 A major rewrite to handle unit lists and use 'Env.convert'.
//    120326 Added example of unit list conversion to HELP.
//           Removed setting of 'Env.gui' - never used.
//           Instantiate and use 'Env.guiFont'.
//
//=========================================================================

package units;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  Class GUI.
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
/**
 *  Common graphic interface for applet and Units window.
 */
public class GUI extends Container implements ActionListener
{
  //=====================================================================
  //  Help text.
  //=====================================================================
  static final String HELP =
    " Units is a program for computations on values expressed in terms\n"
     + " of different measurement units. It is an advanced calculator\n"
     + " that takes care of the units. An example shows how to use it:\n"
     + " Suppose you want to compute the mass, in pounds, of water\n"
     + " that fills to the depth of 7 inches a rectangular area measuring\n"
     + " 5 yards by 4 feet 3 inches. You recall that 1 liter of water\n"
     + " has the mass of 1 kilogram. To compute the weight, you multiply\n"
     + " the water's volume by its specific mass, like this:\n\n"
     + "   5 yds * (4 feet + 3 in) * 7 in * 1 kg/liter\n\n"
     + " Just type this computation in 'You have' field, enter 'pounds'\n"
     + " at 'You want', and press 'Compute'. The result is shown like this:\n\n"
     + "   5 yds * (4 feet + 3 in) * 7 in * 1 kg/liter = 2321.5398 pounds\n\n"
     + " You do not bother about conversions between yards, inches, feet,\n"
     + " liters, kilograms, and pounds. The caculator does it all for you.\n\n"
     + " If you leave 'You want:' field empty, you obtain the result\n"
     + " reduced to standard primitive units; in this case, 1053.0327 kg.\n"
     + " Press 'Conformable units' for a list of other units in which\n"
     + " you can express your result.\n\n"
     + " Enter a unit name in 'You have:' field and press 'Definition'\n"
     + " to see the definition of that unit. Press 'Source' to explore\n"
     + " units database around the definition of that unit. Press 'Search'\n"
     + " to find units whose names contain the 'You have' string.\n\n"
     + " You can also use Units for simple conversions:\n\n"
     + " Feet and inches to meters:\n"
     + "   You have: 6 ft + 7 in\n"
     + "   You want: m\n"
     + "     6 ft + 7 in = 2.0066 m\n\n"
     + " Meters to feet and inches:\n"
     + "   You have: 2 m\n"
     + "   You want: ft;in;1|8in\n"
     + "     2 m = 6 ft + 6 in + 5.9212598 * 1|8in\n\n"
     + " Temperature from Fahrenheit to Celsius:\n"
     + "   You have: tempF(75)\n"
     + "   You want: tempC\n"
     + "     tempF(75) = tempC(23.888889)\n\n"
     + " Reciprocal conversion (volume / distance to distance / volume.):\n"
     + "   You have: 8 liters / 100 km\n"
     + "   You want: miles / gallon\n"
     + "     reciprocal conversion\n"
     + "     1 / (8 liters / 100 km) = 0.034011497 miles / gallon\n\n"
     + " Compound result units:\n"
     + "   You have: 5 mA\n"
     + "   You want: e/sec\n"
     + "           5mA = 3.1207548e16 e/sec\n"
     + " (units data base defines 'e' as the charge of an electron.)\n\n"
     + " See http://units-in-java.sourceforge.net for user's manual.\n";


  //=====================================================================
  //  Elements of GUI.
  //=====================================================================
  JTextField haveField = new JTextField();
  JTextField wantField = new JTextField();

  JTextArea outArea  = new JTextArea(10,10);
  JScrollPane scroll = new JScrollPane(outArea,
                           JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                           JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

  JButton aboutKey   = new JButton("About Units");
  JButton clearKey   = new JButton("Clear");
  JButton conformKey = new JButton("Conformable units");
  JButton convertKey = new JButton("Compute");
  JButton helpKey    = new JButton("Help");
  JButton defKey     = new JButton("Definition");
  JButton searchKey  = new JButton("Search");
  JButton sourceKey  = new JButton("Source");


  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  //
  //  Output writer
  //
  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  /**
   *  Contains output methods for use in GUI.
   */
  private class myOut extends Env.Writer
  {
    @Override
	void print(final String s)   {outArea.append(s);}
    @Override
	void println(final String s) {outArea.append(s + "\n");}
	

  }


  //=====================================================================
  //  Constructor
  //=====================================================================
  GUI(JRootPane rootPane)
    {
      if (Env.guiFont==null)
        Env.guiFont = new Font(Env.font,Font.PLAIN,12);

          setLayout(new BorderLayout());

      JPanel dialog = new JPanel(new GridLayout(2,0));
      add(dialog,BorderLayout.NORTH);

        JPanel havePart = new JPanel(new BorderLayout());
        dialog.add(havePart);
          JPanel haveLine = new JPanel(new BorderLayout());
          havePart.add(haveLine,BorderLayout.NORTH);
            haveLine.add(new JLabel("  "),BorderLayout.NORTH);
            haveLine.add(new JLabel(" "),BorderLayout.EAST);
            haveLine.add(new JLabel("  You have:  "),BorderLayout.WEST);
            haveLine.add(haveField,BorderLayout.CENTER);
          JPanel haveKeys = new JPanel(new FlowLayout(FlowLayout.RIGHT));
          havePart.add(haveKeys,BorderLayout.SOUTH);
            haveKeys.add(searchKey);
            haveKeys.add(conformKey);
            haveKeys.add(defKey);
            haveKeys.add(sourceKey);

        JPanel wantPart = new JPanel(new BorderLayout());
        dialog.add(wantPart);
          JPanel wantLine = new JPanel(new BorderLayout());
          wantPart.add(wantLine,BorderLayout.NORTH);
            wantLine.add(new JLabel(" "),BorderLayout.NORTH);
            wantLine.add(new JLabel(""),BorderLayout.EAST);
            wantLine.add(new JLabel("  You want:  "),BorderLayout.WEST);
            wantLine.add(wantField,BorderLayout.CENTER);
            wantLine.add(new JLabel(" "),BorderLayout.EAST);
          JPanel wantKeys = new JPanel(new FlowLayout(FlowLayout.RIGHT));
          wantPart.add(wantKeys,BorderLayout.SOUTH);
            wantKeys.add(convertKey);

      add(scroll,BorderLayout.CENTER);

      add(new JLabel(" "),BorderLayout.EAST);
      add(new JLabel(" "),BorderLayout.WEST);

      JPanel control = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      add(control,BorderLayout.SOUTH);
        control.add(aboutKey);
        control.add(clearKey);
        control.add(helpKey);

      aboutKey.addActionListener(this);
      clearKey.addActionListener(this);
      conformKey.addActionListener(this);
      convertKey.addActionListener(this);
      defKey.addActionListener(this);
      helpKey.addActionListener(this);
      searchKey.addActionListener(this);
      sourceKey.addActionListener(this);

      rootPane.setDefaultButton(convertKey);

      haveField.setFont(Env.guiFont);
      wantField.setFont(Env.guiFont);
      outArea.setFont(Env.guiFont);
      outArea.setEditable(false);
      outArea.setMargin(new Insets(0,8,0,8));
      outArea.setLineWrap(true);
      outArea.setTabSize(3);

      //---------------------------------------------------------------
      //  Direct writers to window.
      //---------------------------------------------------------------
      Env.out = new myOut();
    }


  //=====================================================================
  //  Get default button
  //=====================================================================
  JButton defaultButton()
    { return convertKey; }

  //=====================================================================
  //
  //   ActionListener interface
  //
  //=====================================================================

  @Override
public void actionPerformed(ActionEvent e)
    {
      String arg = e.getActionCommand();

      //===============================================================
      //  "Search" pressed
      //===============================================================
      if (arg.equals("Search"))
      {
        outArea.setText(null);

        String havestr = haveField.getText().trim();
        if (havestr.length()==0)
        {
          Env.out.println("Enter search string in 'You have' and try again.");
          return;
        }

        Tables.showMatching(havestr);
      }

      //===============================================================
      //  "Conformable units" pressed
      //===============================================================
      if (arg.equals("Conformable units"))
      {
        outArea.setText(null);

        String havestr = haveField.getText().trim();
        if (havestr.length()==0)
        {
          Env.out.println("Enter 'You have' expression and try again.");
          return;
        }

        Value have = Value.fromString(havestr);
        if (have==null)
        {
          Env.out.println("Enter a valid unit expression and try again.");
          return;
        }

        Tables.showConformable(have,havestr);
      }

      //===============================================================
      //  "Definition" pressed
      //===============================================================
      else if (arg.equals("Definition"))
      {
        // Clear output area.
        outArea.setText(null);

        // Get 'have' string or prompt for it if none.
        String havestr = haveField.getText().trim();
        if (havestr.length()==0)
        {
          Env.out.println("Enter 'You have' expression and try again.");
          return;
        }

        // Get the definition
        String haveDef = Tables.showdef(havestr,true);

        // If 'havestr' is the name of an alias, function, prefix,
        // or unit, 'haveDef' contains its definition. Show it.
        if (haveDef!=null)
        {
          Env.out.println(haveDef);
          return;
        }

        // Otherwise print a message.
        Env.out.println
            ("'" + havestr +
             "' is not the name of a unit, function, or unit list." +
             "\nPress 'Compute' to see the reduced form, " +
             "\nor correct 'You have' expression and try again.");
        return;
      }

      //===============================================================
      //  "Source" pressed
      //===============================================================
      else if (arg.equals("Source"))
      {
        // Clear output area.
        outArea.setText(null);

        // Get 'have' string or prompt for it if none.
        String havestr = haveField.getText().trim();
        if (havestr.length()==0)
        {
          Env.out.println("Enter 'You have' expression and try again.");
          return;
        }

        Tables.showSource(havestr);
      }

      //===============================================================
      //  "Compute" pressed
      //===============================================================
      else if (arg.equals("Compute"))
      {
        outArea.setText(null);

        String havestr = haveField.getText().trim();
        if (havestr.length()==0)
        {
          Env.out.println("Enter 'You have' expression and try again.");
          return;
        }

        Value have = Value.fromString(havestr);
        if (have==null)
        {
          Env.out.println("Correct 'You have' expression and try again.");
          return;
        }

        String wantstr = wantField.getText().trim();
        if (wantstr.length()==0)
        {
          have.show();
          return;
        }

      //---------------------------------------------------------------
      //  Perform the conversion.
      //---------------------------------------------------------------
      Env.convert(havestr,have,wantstr);
      return;
      }

      //===============================================================
      //  "Clear" pressed
      //===============================================================
      else if (arg.equals("Clear"))
      {
        haveField.setText(null);
        wantField.setText(null);
        outArea.setText(null);
      }

      //===============================================================
      //  "About Units" pressed
      //===============================================================
      else if (arg.equals("About Units"))
      {
        outArea.setText("");
        Env.showAbout();
        scroll.getViewport().setViewPosition(new Point(0,0));
        outArea.select(0,0);
      }

      //===============================================================
      //  "Help" pressed
      //===============================================================
      else if (arg.equals("Help"))
        Browser.show("Units help", HELP,0,0,false);
    }


  //=====================================================================
  //  Serial version UID. Unused - defined to eliminate compiler warning
  //=====================================================================
  static final long serialVersionUID = 4711L;
}



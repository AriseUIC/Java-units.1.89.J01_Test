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
//  2009, 2010, 2011 by Roman R Redziejowski (www.romanredz.se).
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
//    050203 Total rewrite using GUI for interactive invocation
//           and Env for common constants and switches.
//           Properties used to specify locale and unit files.
//
//  Version 1.84.J06.
//    050226 Help text examples expanded and moved to 'Env'.
//           Added info about help to 'USAGE'.
//
//  Version 1.84.J07.
//    050315 Changed package name to "units".
//
//  Version 1.86.J01.
//    061229 New setting of options.
//           Included new options in USAGE.
//    061231 Used Tables.stat to print table statistics.
//    070102 Suppress printing "\tDefinition :" for compact output.
//
//  Version 1.87.J01.
//    091024 Adjusted to new interface of CommandArgs.
//    091101 Implemented 'search text'.
//    091103 Corrected description of option -f "" (was -f '').
//           Implemented personal units file.
//    091105 No table statistics on non-interactive use.
//
//  Version 1.87.J01.
//    101031 Use 'Env.showAbout" to print version.
//           (Moved after table building to show table statistics.)
//
//  Version 1.88.J02.
//    110403 Modified to accept String from 'Factor.showdef()'
//           and to start definition with the name of defined entity.
//           Added optional "\tDefinition: " before function definition.
//
//  Version 1.88.J03.
//    110623 Appended EXAMPLES text from Env, with correctly rounded results,
//           after HELP.
//
//  Version 1.89.J01.
//    120121 Renamed 'File' to 'UnitsFile'.
//    120126 Added option '-r'.
//    120213 Handle unit list in 'noninteractive' and 'interactive'.
//    120228 Removed error writer 'myErr' and setting of 'Env.err'.
//           Replaced use of 'Env.err' by 'Env.out'.
//    120311 A major rewrite to handle unit lists and use 'Env.convert'.
//    120317 Append personal units file AFTER units.dat,
//           as in the original 'units'.
//    120326 Rewritten option setting. Added options '-e' and '-g'.
//           Added example of unit list conversion to HELP.
//           Use encoding from 'Env.encoding' for System.in and out.
//           'FileAcc' moved from Env to UntisFile.
//
//=========================================================================

package units;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Vector;



//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  class convert
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
/**
 *  Holds the main method and its subroutines.
 */

public class convert
{
  //=====================================================================
  //  Constants.
  //=====================================================================
  static final String USAGE =
    "Usage:\n\n"
     + "  java -jar <jarfile> [options] [from-unit [to-unit]]\n\n"
     + "Call without 'options', 'from-unit', and 'to-unit' opens graphical interface.\n"
     + "Options are:\n\n"
     + "  -c  suppress printing of tab, '*', and '/' character\n"
     + "  -e  specify encoding for the dialog\n"
     + "  -f  specify a units data file (-f \"\" loads default file)\n"
     + "  -g  specify font for browser window\n"
     + "  -h  print this help and exit\n"
     + "  -i  use interactively from command prompt\n"
     + "  -l  specify locale\n"
     + "  -q  suppress prompting\n"
     + "  -r  round last element of unit list output to an integer\n"
     + "  -s  suppress reciprocal unit conversion (e.g. Hz<->s)\n"
     + "  -t  terse output (-c -q -s -1)\n"
     + "  -v  print slightly more verbose output\n"
     + "  -1  suppress the second line of output\n"
     + "  -C  check that all units reduce to primitive units\n"
     + "  -V  describe this version of Units and exit\n\n"
     + "After starting the program, type 'help' at a prompt\n"
     + "or click on 'Help' button for more help.";

  static final String HELP =
    " Units converts between different measuring systems.\n"
     + " Type the measure you want to convert at the 'You have:' prompt.\n"
     + " Type the desired units at the 'You want:' prompt.\n\n"
     + " Press return at the 'You want:' prompt to see the measure you entered above\n"
     + " reduced to primitive units.\n"
     + " Type '?' at 'You want:' prompt to get a list of conformable units.\n\n"
     + " At either prompt you can type:\n"
     + "  'help' to see this message, or\n"
     + "  'help unit' to explore units database around the definition of 'unit', or\n"
     + "  'search text' to see the units whose name contains 'text'.\n\n"
     + " Type 'quit' at either prompt to quit.\n\n"
     + " Examples of conversions:\n\n"
     + " EXAMPLE 1. What is 6 feet 7 inches in meters?\n\n"
     + "   You have: 6 ft + 7 in\n"
     + "   You want: m\n"
     + "           6 ft + 7 in = 2.0066 m\n\n"
     + " Answer: About 2 m.\n\n"
     + " EXAMPLE 2. What is 2 meters in feet and inches?\n\n"
     + "   You have: 2 m\n"
     + "   You want: ft;in;1|8in;;\n"
     + "     2 m = 6 ft + 6 in + 6|8in (rounded up to nearest 1|8in)\n\n"
     + " EXAMPLE 3. Thermometer shows 75 degrees Fahrenheit.\n"
     + " What is the temperature in degrees Celsius?\n\n"
     + "   You have: tempF(75)\n"
     + "   You want: tempC\n"
     + "           tempF(75) = tempC(23.888889)\n\n"
     + " Answer: About 24 C.\n\n"
     + " EXAMPLE 4. A European car maker states fuel consumption of the newest model\n"
     + " as 8 liters per 100 km. What it means in miles per gallon?\n\n"
     + "   You have: 8 liters / 100 km\n"
     + "   You want: miles per gallon\n"
     + "          reciprocal conversion\n"
     + "           1 / (8 liters / 100 km) = 29.401823 miles per gallon\n\n"
     + " Answer: About 29.4 mpg. Notice the indication that 'miles per gallon'\n"
     + " and 'liters per 100 km' are reciprocal dimensions.\n\n"
     + " EXAMPLE 5. A flow of electrons in a vacuum tube has ben measured as 5 mA.\n"
     + " How many electrons flow through the tube every second?\n"
     + " (Hint: units data file defines the electron charge as 'e'.)\n\n"
     + "   You have: 5 mA\n"
     + "   You want: e/sec\n"
     + "           5mA = 3.1207548e16 e/sec\n\n"
     + " Answer: About 31 200 000 000 000 000.\n\n"
     + " EXAMPLE 6. What is the energy, in electronvolts, of a photon of yellow sodium light\n"
     + " with wavelength of 5896 angstroms? (The energy is equal to Planck's constant times\n"
     + " speed of light divided by the wavelength. The units data file defines the Planck's\n"
     + " constant as 'h' and the speed of light as 'c'.)\n\n"
     + "   You have: h * (c/5896 angstroms)\n"
     + "   You want: e V\n"
     + "           h * (c/5896 angstroms) = 2.1028526 e V\n\n"
     + " Answer: About 2.103 eV.\n\n";


  //-------------------------------------------------------------------
  //  Encoding for System.in / out
  //-------------------------------------------------------------------
  private static Charset charset;


  //=====================================================================
  //  main
  //=====================================================================
  /**
   *  Main entry to 'convert'
   *
   *  @param argv command arguments
   */
  public static void main(final String[] argv)
    {
      //---------------------------------------------------------------
      //  If invoked without options or arguments: open GUI.
      //---------------------------------------------------------------
      if (argv.length==0)
      {
        UnitsWindow.init();
        return;
      }

      //---------------------------------------------------------------
      //  We arrive here only for use from command prompt.
      //  Set up default values for options.
      //  (May be replaced in the next steps by values from properties
      //  file and from command line.)
      //---------------------------------------------------------------
      Env.filenames = new Vector<String>(); // Unit definition files
      Env.filenames.add(Env.UNITSFILE);
      Env.getPersonalUnits();

      Env.locale = Env.LOCALE;      // Locale
      Env.encoding = null;          // IO encoding (null = JVM default)
      Env.font = Env.GUIFONT;       // GUI font name
      Env.verbose = 1;              // Neither verbose nor compact
      Env.quiet = false;            // Don't suppress prompting / statistics
      Env.oneline = false;          // Print both lines of result
      Env.strict = false;           // Allow reciprocal conversion
      Env.unitcheck = false;        // No unit checking
      Env.round = false;            // Round last item of unit list

      //---------------------------------------------------------------
      //  Get option values from properties file (if any).
      //  The UNITSFILE property, if specified, replaces all of
      //  'Env.filenames' set in 'init'.
      //  The LOCALE property, if specified, replaces 'Env.locale'.
      //  The ENCODING property, if specified, replaces 'Env.encoding'.
      //  The GUIFONT property, if specified, replaces 'Env.font'.
      //---------------------------------------------------------------
      Env.getProperties();

      //---------------------------------------------------------------
      //  Parse command argument vector 'argv'.
      //  Write usage message and return if error detected.
      //---------------------------------------------------------------
      CommandArgs cmd = new CommandArgs(argv,"chiqrstv1CV","efgl",0,2);
      if (cmd.nErrors()>0)
      {
        Env.out.println(USAGE);
        System.exit(1);//<-
      }

      //---------------------------------------------------------------
      //  Object 'cmd' represents now parsed 'argv' vector.
      //  Return if -h and/or -V was specified.
      //  Otherwise save information specified by options.
      //  This may replace all option values in Env.
      //---------------------------------------------------------------
     boolean done = processopts(cmd);
     if (done) System.exit(0);

      //---------------------------------------------------------------
      //  Set encoding for System io
      //---------------------------------------------------------------
      if (Env.encoding==null)
        charset = Charset.defaultCharset();
      else
        try { charset = Charset.forName(Env.encoding); }
        catch(Exception e)
        {
          System.out.println(e);
          System.exit(1);
        }

      //---------------------------------------------------------------
      //  Set up file access and output writer.
      //---------------------------------------------------------------
      UnitsFile.fileAcc = new UnitsFile.StandAcc(); // File access
      Env.out = new myOut();                        // Output writer

      //---------------------------------------------------------------
      //  Build tables.
      //---------------------------------------------------------------
      boolean ok = Tables.build();
      if (!ok) System.exit(1);


      //---------------------------------------------------------------
      //  If requested, show version and return.
      //---------------------------------------------------------------
      if (cmd.opt('V'))
      {
        Env.showAbout();
        System.exit(0);
      }

      //---------------------------------------------------------------
      //  If requested, do unit check and return.
      //---------------------------------------------------------------
      if (Env.unitcheck)
      {
        Tables.check();
        System.exit(0);
      }

      //---------------------------------------------------------------
      //  Proceed to do conversions.
      //---------------------------------------------------------------
      if (cmd.nArgs()>0) noninteractive(cmd);
      else interactive();

     // System.exit(0);
    }


  //=====================================================================
  //  noninteractive
  //=====================================================================
  /**
   *  Does non-interactive conversion.
   */
  private static void noninteractive(CommandArgs cmd)
    {
      Env.quiet = true;

      //---------------------------------------------------------------
      //  Get 'from-unit' argument to 'havestr'
      //  and 'to-unit' argument, if present, to 'wantstr'.
      //  Set 'wantstr' to null if 'to-unit' was not present.
      //---------------------------------------------------------------
      String havestr = cmd.arg(0).trim();
      String wantstr = null;
      if (cmd.nArgs()>1)
        wantstr = cmd.arg(1).trim();

      //---------------------------------------------------------------
      //  If 'havestr' is the name of a function or unit list,
      //  write its definition and exit.
      //---------------------------------------------------------------
      String haveDef = Tables.showdef(havestr,false);
      if (haveDef!=null)
      {
        Env.out.print(Env.verbose>0? "\tDefinition: " : "\t");
        Env.out.println(haveDef);
     //   System.exit(0);
      }

      //---------------------------------------------------------------
      //  Evaluate 'havestr' to Value 'have'.
      //  A failed evaluation prints error message and returns null.
      //---------------------------------------------------------------
      Value have = Value.fromString(havestr);
      if (have==null)
        System.exit(1);

      //---------------------------------------------------------------
      //  If 'to-unit' was not specified:
      //---------------------------------------------------------------
      if (wantstr==null)
      {
        //-------------------------------------------------------------
        //  If 'havestr' is the name of a unit, function,
        //  or unit list, write its definition and exit.
        //-------------------------------------------------------------
        haveDef = Tables.showdef(havestr,true);
        if (haveDef!=null)
        {
          Env.out.print(Env.verbose>0? "\tDefinition: " : "\t");
          Env.out.println(haveDef);
          System.exit(0);
        }

        //-------------------------------------------------------------
        //  Otherwise, write 'from-unit' reduced to primitive units.
        //-------------------------------------------------------------
        have.completereduce();
        Env.out.println
              ("\t" + havestr + " = " + have.asString());
        //System.exit(0);
      }

      //---------------------------------------------------------------
      //  If 'to-unit' was specified: perform the conversion.
      //---------------------------------------------------------------
      boolean ok = Env.convert(havestr,have,wantstr);
     // if (ok) System.exit(0);
     // System.exit(1);
    }


  //=====================================================================
  //  interactive
  //=====================================================================
  /**
   *  Does interactive conversions.
   */
  private static void interactive()
    {
      String havestr;
      String wantstr;
      Value have;
      Value want;

      //---------------------------------------------------------------
      //  Create reader for user's input
      //---------------------------------------------------------------
      InputStreamReader isr = new InputStreamReader(System.in,charset);
      BufferedReader in = new BufferedReader(isr);

      //---------------------------------------------------------------
      //  Print table statistics if not suppressed.
      //---------------------------------------------------------------
      if (!Env.quiet) Env.out.println(Tables.stat());

      //---------------------------------------------------------------
      //  Keep doing conversions.
      //  This 'while' statement is terminated by 'break'.
      //---------------------------------------------------------------
      mainloop:
      while(true)
      {
        //-------------------------------------------------------------
        //  Keep prompting the user with "You have:".
        //-------------------------------------------------------------
        haveloop:
        while(true)
        {
          havestr = getuser("You have: ",in);

          //-----------------------------------------------------------
          //  Exit if the user entered empty line or "quit".
          //-----------------------------------------------------------
          if (havestr==null || havestr.equals("quit"))
            break mainloop;

          //-----------------------------------------------------------
          //  If the user entered "help" or "search",
          //  provide the requested information and keep prompting.
          //-----------------------------------------------------------
          if (ishelpquery(havestr))
            continue haveloop;

          //---------------------------------------------------------------
          //  If 'havestr' is the name of a function or unit list,
          //  write its definition and exit.
          //---------------------------------------------------------------
          String haveDef = Tables.showdef(havestr,false);
          if (haveDef!=null)
          {
            Env.out.print(Env.verbose>0? "\tDefinition: " : "\t");
            Env.out.println(haveDef);
            continue haveloop;
          }

          //---------------------------------------------------------------
          //  Evaluate 'havestr' to Value 'have'.
          //  A failed evaluation prints error message and returns null.
          //---------------------------------------------------------------
          have = Value.fromString(havestr);
          if (have==null)
            continue haveloop;

          break haveloop;

        } // end haveloop

        //-------------------------------------------------------------
        //  Keep prompting the user with "You want:".
        //-------------------------------------------------------------
        wantloop:
        while(true)
        {
          wantstr = getuser("You want: ",in);

          //-----------------------------------------------------------
          //  If user entered empty line, show definition of 'have',
          //  or only reduced value if 'have' is not a unit name.
          //-----------------------------------------------------------
          if (wantstr==null)
          {
            //---------------------------------------------------------
            //  If 'from-unit' is the name of a unit, function,
            //  or unit list, write its definition.
            //---------------------------------------------------------
            String haveDef = Tables.showdef(havestr,true);
            if (haveDef!=null)
            {
              if (Env.verbose>0) Env.out.print("\tDefinition: ");
              Env.out.println(haveDef);
              break wantloop;
            }

            //-------------------------------------------------------------
            //  Otherwise, write 'from-unit' reduced to primitive units.
            //-------------------------------------------------------------
            have.completereduce();
            Env.out.println
              ("\t" + havestr + " = " + have.asString());
            break wantloop;
          }

          //-----------------------------------------------------------
          //  We arrive here if the user entered a nonempty line.
          //  Exit if the user entered "quit".
          //-----------------------------------------------------------
          if (wantstr.equals("quit"))
            break mainloop;

          //-----------------------------------------------------------
          //  If the user entered "?",
          //  show conformable units and keep prompting.
          //-----------------------------------------------------------
          if (wantstr.equals("?"))
          {
            Tables.showConformable(have,havestr);
            continue wantloop;
          }

          //-----------------------------------------------------------
          //  If the user entered "help" or "search",
          //  provide the requested information and keep prompting.
          //-----------------------------------------------------------
          if (ishelpquery(wantstr))
            continue wantloop;

          //---------------------------------------------------------------
          //  Otherwise perform the conversion.
          //---------------------------------------------------------------
          Env.convert(havestr,have,wantstr);
          break wantloop;

        } // end of wantloop
      }
    }


  //=====================================================================
  //  processopts
  //=====================================================================
  /**
   *  Obtains options specified by the command.
   *  Prints help or version if -h and/or -V option is present.
   *
   *  @param  cmd CommandArgs object containing parsed command arguments.
   *  @return true if help or version was printed,
   *          false otherwise.
   */
  private static boolean processopts(CommandArgs cmd)
    {
      if (cmd.opt('h'))
      {
        Env.out.println(USAGE);
        return true;
      }

      if (cmd.opt('e')) Env.encoding = cmd.optArg('e');
      if (cmd.opt('f')) Env.filenames = cmd.optArgs('f');
      if (cmd.opt('g')) Env.font = cmd.optArg('g');
      if (cmd.opt('l')) Env.locale = cmd.optArg('l');

      if (cmd.opt('v')) Env.verbose = 2;
      if (cmd.opt('c')) Env.verbose = 0;
      if (cmd.opt('q')) Env.quiet = true;
      if (cmd.opt('1')) Env.oneline = true;
      if (cmd.opt('r')) Env.round = true;
      if (cmd.opt('s')) Env.strict = true;
      if (cmd.opt('C')) Env.unitcheck = true;
      if (cmd.opt('t'))
      {
        Env.verbose = 0;
        Env.quiet=true;
        Env.strict=true;
        Env.oneline = true;
      }

      return false;
    }


  //=====================================================================
  //  ishelpquery
  //=====================================================================
  /**
   *  Provides help in interactive mode.
   *  <br>
   *  If 's' is 'help', prints general help and returns true.
   *  If 's' is 'help' followed by a unit name, shows part of
   *  definition file for that unit and returns true.
   *  If 's' is 'search' followed by some text, shows a list
   *  of unit names containing that text as substring.
   *  Otherwise returns false.
   *
   *  @param  s string to be checked for being request for help.
   *  @return true if help was requested.
   */
  static private boolean ishelpquery
    ( final String s)
    {
      String str = s.trim();

      //---------------------------------------------------------------
      //  Request for help?
      //---------------------------------------------------------------
      if (str.equals("help"))
      {
        Browser.show("Units help", HELP,0,0,false);
        return true;
      }

      //---------------------------------------------------------------
      //  Request for source? ('help unit')
      //---------------------------------------------------------------
      if (str.startsWith("help ") ||
          str.startsWith("help\t"))
      {
        str = str.substring("help ".length(),str.length()).trim();
        Tables.showSource(str);
        return true;
      }

      //---------------------------------------------------------------
      //  Incomplete request for search?
      //---------------------------------------------------------------
      if (str.equals("search"))
      {
        Env.out.println
            ("Type 'search text' to see a list of all unit names" +
             "\ncontaining 'text' as a substring.");
        return true;
      }

      //---------------------------------------------------------------
      //  Request for search? ('search text')
      //---------------------------------------------------------------
      if (str.startsWith("search ") ||
          str.startsWith("search\t"))
      {
        str = str.substring("search ".length(),str.length()).trim();
        Tables.showMatching(str);
        return true;
      }

      return false;
    }


  //=====================================================================
  //  getuser
  //=====================================================================
  /**
   *  Prompts the user and reads reply in the interactive mode.
   *
   *  @param  prompt the prompt string.
   *  @param  in reader to obtain reply from.
   *  @return user's reply with leading and trailing blanks removed,
   *          or null if the reply was empty line.
   */
  private static String getuser(final String prompt,BufferedReader in)
    {
      if (!Env.quiet)
        Env.out.print(prompt);

      String reply;
      try
      { reply = in.readLine().trim(); }
      catch (IOException e)
      {
        Env.out.println(e.toString());
        return null;
      }

      return reply.length()==0? null : reply;
    }



  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  //
  //  Output writer
  //
  //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  /**
   *  Contains output methods for command-line use.
   */
  private static class myOut extends Env.Writer
  {
    OutputStreamWriter osw = new OutputStreamWriter(System.out,charset);
    BufferedWriter writer = new BufferedWriter(osw);
    
    @Override
	void print(final String s)
      {
        //writer.write(s);
          System.out.print(s);
        	//writer.flush();
          System.out.flush();
      }

    @Override
	void println(final String s)
      { print(s + "\n"); }


  }
}



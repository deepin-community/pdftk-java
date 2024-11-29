/*
 *   This file is part of the pdftk port to java
 *
 *   Copyright (c) Marc Vinyals 2017-2018
 *
 *   The program is a java port of PDFtk, the PDF Toolkit
 *   Copyright (c) 2003-2013 Steward and Lee, LLC
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   The program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gitlab.pdftk_java;

import com.gitlab.pdftk_java.com.lowagie.text.exceptions.InvalidPdfException;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

class InputPdf {
  String m_filename = "";
  String m_password = "";
  boolean m_authorized_b = true;
  byte[] m_buffer = null; // Copy input to memory if reading from stdin.

  String m_role = "input";
  String m_role_determined = "an input";

  // keep track of which pages get output under which readers,
  // because one reader mayn't output the same page twice;
  static class PagesReader {
    HashSet<Integer> first = new HashSet<Integer>();
    PdfReader second;

    PagesReader(PdfReader second) {
      this.second = second;
    }
  }

  ArrayList<PagesReader> m_readers = new ArrayList<PagesReader>();

  int m_num_pages = 0;

  InputPdf.PagesReader add_reader(boolean keep_artifacts_b, boolean ask_about_warnings_b) {
    boolean open_success_b = true;
    InputPdf.PagesReader pr = null;

    // No point on asking for a password after we read all of stdin.
    if (m_filename.equals("-")) ask_about_warnings_b = false;

    try {
      PdfReader reader = null;
      if (m_filename.equals("PROMPT")) {
        m_filename =
            pdftk.prompt_for_filename("Please enter a filename for " + m_role_determined + " PDF:");
      }
      if (m_password.isEmpty()) {
        if (m_filename.equals("-")) {
          if (m_buffer == null) m_buffer = pdftk.readAllBytes(System.in);
          reader = new PdfReader(m_buffer);
        } else {
          reader = new PdfReader(m_filename);
        }
      } else {
        if (m_password.equals("PROMPT")) {
          m_password =
              pdftk.prompt_for_password("open", "the " + m_role + " PDF:\n   " + m_filename);
        }

        byte[] password =
            passwords.utf8_password_to_pdfdoc(
                m_password, false); // allow user to enter greatest selection of chars

        if (password != null) {
          if (m_filename.equals("-")) {
            if (m_buffer == null) m_buffer = pdftk.readAllBytes(System.in);
            reader = new PdfReader(m_buffer, password);
          } else {
            reader = new PdfReader(m_filename, password);
          }
        } else { // bad password
          System.err.println("Error: Password used to decrypt " + m_role + " PDF:");
          System.err.println("   " + m_filename);
          System.err.println("   includes invalid characters.");
          return null; // <--- return
        }
      }

      if (reader == null) {
        System.err.println("Error: Unexpected null from open_reader()");
        return null; // <--- return
      }

      if (!keep_artifacts_b) {
        // generally useful operations
        reader.consolidateNamedDestinations();
        reader.removeUnusedObjects();
        // reader->shuffleSubsetNames(); // changes the PDF subset names, but not the PostScript
        // font names
      }

      m_num_pages = reader.getNumberOfPages();

      // keep tally of which pages have been laid claim to in this reader;
      // when creating the final PDF, this tally will be decremented
      pr = new InputPdf.PagesReader(reader);
      m_readers.add(pr);

      m_authorized_b = true; // instead of:  ( !reader->encrypted || reader->ownerPasswordUsed );

      if (open_success_b && reader.encrypted && !reader.ownerPasswordUsed) {
        System.err.println("WARNING: The creator of the " + m_role + " PDF:");
        System.err.println("   " + m_filename);
        System.err.println(
            "   has set an owner password (which is not required to handle this PDF).");
        System.err.println("   You did not supply this password. Please respect any copyright.");
      }

      if (!m_authorized_b) {
        open_success_b = false;
      }
    } catch (IOException ioe_p) { // file open error
      String message = ioe_p.getMessage();
      if (message == null) message = "";
      if (message.equals("Bad password")) {
        m_authorized_b = false;
      } else if (message.indexOf("not found as file or resource") != -1) {
        System.err.println("Error: Unable to find file.");
      } else if (ioe_p instanceof InvalidPdfException) {
        System.err.println("Error: Invalid PDF: " + message);
      } else { // unexpected error
        System.err.println("Error: Unexpected Exception in open_reader()");
        ioe_p.printStackTrace(); // debug
      }
      open_success_b = false;
    } catch (Throwable t_p) { // unexpected error
      open_success_b = false;
      System.err.println("Error: Unexpected Exception in open_reader()");
      throw (t_p);
    }

    if (!m_authorized_b && ask_about_warnings_b) {
      // prompt for a new password
      if (m_password.isEmpty()) {
        System.err.println("The password for the " + m_role + " PDF:");
        System.err.println("   " + m_filename);
        System.err.println("   is missing.  This PDF is encrypted, and you must supply the");
        System.err.println("   owner or the user password to open it. To quit, enter a blank");
        System.err.println("   password at the next prompt.");
      } else {
        System.err.println("The password you supplied for the " + m_role + " PDF:");
        System.err.println("   " + m_filename);
        System.err.println("   did not work.  This PDF is encrypted, and you must supply the");
        System.err.println("   owner or the user password to open it. To quit, enter a blank");
        System.err.println("   password at the next prompt.");
      }

      m_password = pdftk.prompt_for_password("open", "the " + m_role + " PDF:\n   " + m_filename);
      if (!m_password.isEmpty()) { // reset flags try again
        m_authorized_b = true;
        return (add_reader(keep_artifacts_b, ask_about_warnings_b)); // <--- recurse, return
      }
    }

    // report
    if (!open_success_b) { // file open error
      System.err.println("Error: Failed to open " + m_role + " PDF file: ");
      System.err.println("   " + m_filename);
      if (!m_authorized_b) {
        if (m_password.isEmpty()) {
          System.err.println("   OWNER OR USER PASSWORD REQUIRED, but not given");
        } else {
          System.err.println("   OWNER OR USER PASSWORD REQUIRED, but incorrect");
        }
      }
    }

    return open_success_b ? pr : null;
  }
}

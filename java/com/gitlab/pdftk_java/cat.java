/*
 *   This file is part of the pdftk port to java
 *
 *   Copyright (c) Marc Vinyals 2017-2020
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

import com.gitlab.pdftk_java.com.lowagie.text.Document;
import com.gitlab.pdftk_java.com.lowagie.text.DocumentException;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfCopy;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfDictionary;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfImportedPage;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfIndirectReference;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfName;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfNumber;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfObject;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfReader;
import com.gitlab.pdftk_java.com.lowagie.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class cat {

  TK_Session session;
  String m_output_filename;
  ArrayList<InputPdf> m_input_pdf;
  ArrayList<ArrayList<PageRef>> m_page_seq;
  boolean m_output_keep_first_id_b;
  boolean m_output_keep_final_id_b;

  cat(TK_Session session) {
    this.session = session;
    m_output_filename = session.m_output_filename;
    m_input_pdf = session.m_input_pdf;
    m_page_seq = session.m_page_seq;
    m_output_keep_first_id_b = session.m_output_keep_first_id_b;
    m_output_keep_final_id_b = session.m_output_keep_final_id_b;
  }

  static char GetPdfVersionChar(PdfObject version_p) {
    char version_cc = PdfWriter.VERSION_1_4; // default

    if (version_p == null) return version_cc;

    if (!version_p.isName()) {
      try {
        version_p = new PdfName(version_p.toString());
      } catch (Exception e) {
        return version_cc;
      }
    }

    if (version_p.equals(PdfName.VERSION_1_4)) version_cc = PdfWriter.VERSION_1_4;
    else if (version_p.equals(PdfName.VERSION_1_5)) version_cc = PdfWriter.VERSION_1_5;
    else if (version_p.equals(PdfName.VERSION_1_6)) version_cc = PdfWriter.VERSION_1_6;
    else if (version_p.equals(PdfName.VERSION_1_7)) version_cc = PdfWriter.VERSION_1_7;
    else if (version_p.equals(PdfName.VERSION_1_3)) version_cc = PdfWriter.VERSION_1_3;
    else if (version_p.equals(PdfName.VERSION_1_2)) version_cc = PdfWriter.VERSION_1_2;
    else if (version_p.equals(PdfName.VERSION_1_1)) version_cc = PdfWriter.VERSION_1_1;
    else if (version_p.equals(PdfName.VERSION_1_0)) version_cc = PdfWriter.VERSION_1_0;

    return version_cc;
  }

  ErrorCode create_output_page(PdfCopy writer_p, PageRef page_ref, int output_page_count) {
    ErrorCode ret_val = ErrorCode.NO_ERROR;

    // get the reader associated with this page ref.
    if (page_ref.m_input_pdf_index < m_input_pdf.size()) {
      InputPdf page_pdf = m_input_pdf.get(page_ref.m_input_pdf_index);

      if (session.m_verbose_reporting_b) {
        System.out.print(
            "   Adding page "
                + page_ref.m_page_num
                + " X"
                + page_ref.m_page_rot
                + "X "); // DF rotate
        System.out.println(" from " + page_pdf.m_filename);
      }

      // take the first, associated reader and then disassociate
      PdfReader input_reader_p = null;
      for (InputPdf.PagesReader mt : page_pdf.m_readers) {
        if (mt.first.contains(page_ref.m_page_num)) { // assoc. found
          input_reader_p = mt.second;
          mt.first.remove(page_ref.m_page_num); // remove this assoc.
          break;
        }
      }

      if (input_reader_p != null) {

        //
        if (session.m_output_uncompress_b) {
          filter.add_mark_to_page(input_reader_p, page_ref.m_page_num, output_page_count + 1);
        } else if (session.m_output_compress_b) {
          filter.remove_mark_from_page(input_reader_p, page_ref.m_page_num);
        }

        // DF rotate
        session.apply_rotation_to_page(
            input_reader_p, page_ref.m_page_num, page_ref.m_page_rot.value, page_ref.m_page_abs);

        //
        try {
          PdfImportedPage page_p = writer_p.getImportedPage(input_reader_p, page_ref.m_page_num);
          try {
            writer_p.addPage(page_p);
          } catch (DocumentException e) {
            System.err.print("Internal Error: addPage() failed for: ");
            System.err.println(page_ref.m_page_num + " in file: " + page_pdf.m_filename);
            ret_val = ErrorCode.BUG;
          }
        } catch (IOException e) { // error
          System.err.print("Internal Error: getImportedPage() failed for: ");
          System.err.println(page_ref.m_page_num + " in file: " + page_pdf.m_filename);
          ret_val = ErrorCode.BUG;
        }
      } else { // error
        System.err.print("Internal Error: no reader found for page: ");
        System.err.println(page_ref.m_page_num + " in file: " + page_pdf.m_filename);
        ret_val = ErrorCode.BUG;
      }
    } else { // error
      System.err.println("Internal Error: Unable to find handle in m_input_pdf.");
      ret_val = ErrorCode.BUG;
    }

    return ret_val;
  }

  // catenate pages or shuffle pages
  ErrorCode create_output_cat() throws DocumentException, IOException {
    Document output_doc_p = new Document();

    OutputStream ofs_p = pdftk.get_output_stream(m_output_filename, session.m_ask_about_warnings_b);

    if (ofs_p == null) { // file open error
      return ErrorCode.ERROR;
    }
    PdfCopy writer_p = new PdfCopy(output_doc_p, ofs_p);

    output_doc_p.addCreator(TK_Session.creator);

    // update to suit any features that we add, e.g. encryption;
    char max_version_cc = session.prepare_writer(writer_p);

    // copy file ID?
    if (m_output_keep_first_id_b || m_output_keep_final_id_b) {
      PdfReader input_reader_p =
          m_output_keep_first_id_b
              ? m_input_pdf.get(0).m_readers.get(0).second
              : m_input_pdf.get(m_input_pdf.size() - 1).m_readers.get(0).second;

      PdfDictionary trailer_p = input_reader_p.getTrailer();

      PdfObject file_id_p = input_reader_p.getPdfObject(trailer_p.get(PdfName.ID));
      if (file_id_p != null && file_id_p.isArray()) {
        writer_p.setFileID(file_id_p);
      }
    }

    // set output PDF version to the max PDF ver of all the input PDFs;
    // also find the maximum extension levels, if present -- this can
    // only be added /after/ opening the document;
    //
    // collected extensions information; uses PdfName::hashCode() for key
    HashMap<PdfName, PdfName> ext_base_versions = new HashMap<PdfName, PdfName>();
    HashMap<PdfName, Integer> ext_levels = new HashMap<PdfName, Integer>();
    for (InputPdf it : m_input_pdf) {
      PdfReader reader_p = it.m_readers.get(0).second;

      ////
      // PDF version number

      // version in header
      if (max_version_cc < reader_p.getPdfVersion()) max_version_cc = reader_p.getPdfVersion();

      // version override in catalog; used only if greater than header version, per PDF
      // spec;
      PdfDictionary catalog_p = reader_p.getCatalog();
      if (catalog_p.contains(PdfName.VERSION)) {

        PdfObject version_p = reader_p.getPdfObject(catalog_p.get(PdfName.VERSION));
        char version_cc = GetPdfVersionChar(version_p);

        if (max_version_cc < version_cc) max_version_cc = version_cc;
      }

      ////
      // PDF extensions

      if (catalog_p.contains(PdfName.EXTENSIONS)) {
        PdfObject extensions_po = reader_p.getPdfObject(catalog_p.get(PdfName.EXTENSIONS));
        if (extensions_po != null && extensions_po.isDictionary()) {
          PdfDictionary extensions_p = (PdfDictionary) extensions_po;

          // iterate over developers
          Set<PdfObject> keys_p = extensions_p.getKeys();
          for (PdfObject kit : keys_p) {
            // We need to be defensive about object types
            // cf https://gitlab.com/pdftk-java/pdftk/-/issues/127
            PdfObject developer_po = reader_p.getPdfObject(kit);
            PdfName developer_p = null;
            if (developer_po.isName()) {
              developer_p = (PdfName) developer_po;
            } else {
              try {
                developer_p = new PdfName(developer_po.toString());
              } catch (Exception e) {
                continue;
              }
            }

            PdfObject dev_exts_po = reader_p.getPdfObject(extensions_p.get(developer_p));
            if (dev_exts_po != null && dev_exts_po.isDictionary()) {
              PdfDictionary dev_exts_p = (PdfDictionary) dev_exts_po;

              if (dev_exts_p.contains(PdfName.BASEVERSION)
                  && dev_exts_p.contains(PdfName.EXTENSIONLEVEL)) {
                // use the greater base version or the greater extension level

                PdfObject base_version_po =
                    reader_p.getPdfObject(dev_exts_p.get(PdfName.BASEVERSION));
                PdfName base_version_p = null;
                if (base_version_po.isName()) {
                  base_version_p = (PdfName) base_version_p;
                } else {
                  try {
                    base_version_p = new PdfName(base_version_po.toString());
                  } catch (Exception e) {
                    continue;
                  }
                }

                PdfObject ext_level_po =
                    reader_p.getPdfObject(dev_exts_p.get(PdfName.EXTENSIONLEVEL));
                Integer ext_level_i = null;
                if (ext_level_po.isNumber()) {
                  ext_level_i = ((PdfNumber) ext_level_po).intValue();
                } else {
                  try {
                    ext_level_i = Integer.valueOf(ext_level_i.toString());
                  } catch (Exception e) {
                    continue;
                  }
                }

                if (!ext_base_versions.containsKey(developer_p)
                    || GetPdfVersionChar(ext_base_versions.get(developer_p))
                        < GetPdfVersionChar(base_version_po)) {
                  // new developer or greater base version
                  ext_base_versions.put(developer_p, base_version_p);
                  ext_levels.put(developer_p, ext_level_i);
                } else if (GetPdfVersionChar(ext_base_versions.get(developer_p))
                        == GetPdfVersionChar(base_version_po)
                    && ext_levels.get(developer_p) < ext_level_i) {
                  // greater extension level for current base version
                  ext_levels.put(developer_p, ext_level_i);
                }
              }
            }
          }
        }
      }
    }
    // set the pdf version
    writer_p.setPdfVersion(max_version_cc);

    // open the doc
    output_doc_p.open();

    // set any pdf version extensions we might have found
    if (!ext_base_versions.isEmpty()) {
      PdfDictionary extensions_dict_p = new PdfDictionary();
      PdfIndirectReference extensions_ref_p = writer_p.getPdfIndirectReference();
      for (Map.Entry<PdfName, PdfName> it : ext_base_versions.entrySet()) {
        PdfDictionary ext_dict_p = new PdfDictionary();
        ext_dict_p.put(PdfName.BASEVERSION, it.getValue());
        ext_dict_p.put(PdfName.EXTENSIONLEVEL, new PdfNumber(ext_levels.get(it.getKey())));

        extensions_dict_p.put(it.getKey(), ext_dict_p);
      }

      writer_p.addToBody(extensions_dict_p, extensions_ref_p);
      writer_p.setExtensions(extensions_ref_p);
    }

    if (session.m_operation == keyword.shuffle_k) {
      int max_seq_length = 0;
      for (ArrayList<PageRef> jt : m_page_seq) {
        max_seq_length = (max_seq_length < jt.size()) ? jt.size() : max_seq_length;
      }

      int output_page_count = 0;
      // iterate over ranges
      for (int ii = 0; ii < max_seq_length; ++ii) {
        // iterate over ranges
        for (ArrayList<PageRef> jt : m_page_seq) {
          if (ii < jt.size()) {
            ErrorCode ret_val = create_output_page(writer_p, jt.get(ii), output_page_count);
            if (ret_val != ErrorCode.NO_ERROR) return ret_val;
            ++output_page_count;
          }
        }
      }
    } else { // cat_k

      int output_page_count = 0;
      // iterate over page ranges
      for (ArrayList<PageRef> jt : m_page_seq) {
        // iterate over pages in page range
        for (PageRef it : jt) {
          ErrorCode ret_val = create_output_page(writer_p, it, output_page_count);
          if (ret_val != ErrorCode.NO_ERROR) return ret_val;
          ++output_page_count;
        }
      }

      // first impl added a bookmark for each input PDF and then
      // added any of that PDFs bookmarks under that; now it
      // appends input PDF bookmarks, which is more attractive;
      // OTOH, some folks might want pdftk to add bookmarks for
      // input PDFs, esp if they don't have bookmarks -- TODO
      // but then, it would be nice to allow the user to specify
      // a label -- using the PDF filename is unattractive;
      if (session.m_cat_full_pdfs_b) { // add bookmark info
        // cerr << "cat full pdfs!" << endl; // debug

        PdfDictionary output_outlines_p = new PdfDictionary(PdfName.OUTLINES);
        PdfIndirectReference output_outlines_ref_p = writer_p.getPdfIndirectReference();

        PdfDictionary after_child_p = null;
        PdfIndirectReference after_child_ref_p = null;

        int page_count = 1;
        int num_bookmarks_total = 0;
        /* used for adding doc bookmarks
           itext::PdfDictionary* prev_p= 0;
           itext::PdfIndirectReference* prev_ref_p= 0;
        */
        // iterate over page ranges; each full PDF has one page seq in m_page_seq;
        // using m_page_seq instead of m_input_pdf, so the doc order is right
        for (ArrayList<PageRef> jt : m_page_seq) {
          PdfReader reader_p = m_input_pdf.get(jt.get(0).m_input_pdf_index).m_readers.get(0).second;
          long reader_page_count = m_input_pdf.get(jt.get(0).m_input_pdf_index).m_num_pages;

          /* used for adding doc bookmarks
             itext::PdfDictionary* item_p= new itext::PdfDictionary();
             itext::PdfIndirectReference* item_ref_p= writer_p->getPdfIndirectReference();

             item_p->put( itext::PdfName::PARENT, outlines_ref_p );
             item_p->put( itext::PdfName::TITLE,
             new itext::PdfString( JvNewStringUTF( (*it).m_filename.c_str() ) ) );

             // wire into linked list
             if( prev_p ) {
               prev_p->put( itext::PdfName::NEXT, item_ref_p );
               item_p->put( itext::PdfName::PREV, prev_ref_p );
             }
             else { // first item; wire into outlines dict
               output_outlines_p->put( itext::PdfName::FIRST, item_ref_p );
             }

             // the destination
             itext::PdfDestination* dest_p= new itext::PdfDestination(itext::PdfDestination::FIT);
             itext::PdfIndirectReference* page_ref_p= writer_p->getPageReference( page_count );
             if( page_ref_p ) {
               dest_p->addPage( page_ref_p );
             }
             item_p->put( itext::PdfName::DEST, dest_p );
          */

          // pdf bookmarks -> children
          {
            PdfDictionary catalog_p = reader_p.getCatalog();
            PdfObject outlines_p = reader_p.getPdfObject(catalog_p.get(PdfName.OUTLINES));
            if (outlines_p != null && outlines_p.isDictionary()) {

              PdfObject top_outline_p =
                  reader_p.getPdfObject(((PdfDictionary) outlines_p).get(PdfName.FIRST));
              if (top_outline_p != null && top_outline_p.isDictionary()) {

                ArrayList<PdfBookmark> bookmark_data = new ArrayList<PdfBookmark>();
                int rr =
                    bookmarks.ReadOutlines(
                        bookmark_data, (PdfDictionary) top_outline_p, 0, reader_p, true);
                if (rr == 0 && !bookmark_data.isEmpty()) {

                  // passed in by reference, so must use variable:
                  bookmarks.BuildBookmarksState state = new bookmarks.BuildBookmarksState();
                  state.final_child_p = after_child_p;
                  state.final_child_ref_p = after_child_ref_p;
                  state.num_bookmarks_total = num_bookmarks_total;
                  bookmarks.BuildBookmarks(
                      writer_p,
                      bookmark_data.listIterator(),
                      // item_p, item_ref_p, // used for adding doc bookmarks
                      output_outlines_p,
                      output_outlines_ref_p,
                      after_child_p,
                      after_child_ref_p,
                      0,
                      page_count - 1, // page offset is 0-based
                      0,
                      true,
                      state);
                  after_child_p = state.final_child_p;
                  after_child_ref_p = state.final_child_ref_p;
                  num_bookmarks_total = state.num_bookmarks_total;
                }
                /*
                  else if( rr!= 0 )
                  cerr << "ReadOutlines error" << endl; // debug
                  else
                  cerr << "empty bookmark data" << endl; // debug
                */
              }
            }
            /*
              else
              cerr << "no outlines" << endl; // debug
            */
          }

          /* used for adding doc bookmarks
          // finished with prev; add to body
          if( prev_p )
            writer_p->addToBody( prev_p, prev_ref_p );

          prev_p= item_p;
          prev_ref_p= item_ref_p;
          */

          page_count += reader_page_count;
        }
        /* used for adding doc bookmarks
        if( prev_p ) { // wire into outlines dict
          // finished with prev; add to body
          writer_p->addToBody( prev_p, prev_ref_p );

          output_outlines_p->put( itext::PdfName::LAST, prev_ref_p );
          output_outlines_p->put( itext::PdfName::COUNT, new itext::PdfNumber( (jint)m_input_pdf.size() ) );
        }
        */

        if (num_bookmarks_total != 0) { // we encountered bookmarks

          // necessary for serial appending to outlines
          if (after_child_p != null && after_child_ref_p != null)
            writer_p.addToBody(after_child_p, after_child_ref_p);

          writer_p.addToBody(output_outlines_p, output_outlines_ref_p);
          writer_p.setOutlines(output_outlines_ref_p);
        }
      }
    }

    output_doc_p.close();
    writer_p.close();
    return ErrorCode.NO_ERROR;
  }
}

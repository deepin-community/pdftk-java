## Updated to itext-4.2.0

Changes are listed in individual file headers.

* com/lowagie/text/pdf/AcroFields.java
* com/lowagie/text/pdf/BaseField.java
* com/lowagie/text/pdf/FdfReader.java
* com/lowagie/text/pdf/FontSelector.java
* com/lowagie/text/pdf/GrayColor.java
* com/lowagie/text/pdf/TextField.java
* com/lowagie/text/pdf/XfdfReader.java
* com/lowagie/text/Utilities.java

## Based on itext-2.1.7

* com/lowagie/text/exceptions/BadPasswordException.java
* com/lowagie/text/exceptions/IllegalPdfSyntaxException.java
* com/lowagie/text/exceptions/InvalidPdfException.java
* com/lowagie/text/exceptions/UnsupportedPdfException.java

* com/lowagie/text/pdf/IntHashtable.java
  * Exceptions renamed
* com/lowagie/text/pdf/OutputStreamEncryption.java
* com/lowagie/text/pdf/PRStream.java
  * **Added code**
* com/lowagie/text/pdf/PRTokeniser.java
  * **Multiple changes**
* com/lowagie/text/pdf/PdfArray.java
  * Changes in `toPdf`.
  * Other changes are just comments.
* com/lowagie/text/pdf/PdfDeveloperExtension.java
  * Added singleton method.
* com/lowagie/text/pdf/PdfDictionary.java
  * Changes in `toPdf`.
  * Unmarked changes come from itext-paulo-155.
* com/lowagie/text/pdf/PdfEncryption.java
  * Commented out code related to public keys.
    * Marked as `// 4.2.0`, but mot related to iText 4.2.0 AFAICT.
    * Unmkarked.
  * Changes in `setupAllKeys`.
  * Legacy code left at the end of the file.
* com/lowagie/text/pdf/PdfLayer.java
* com/lowagie/text/pdf/PdfName.java
  * Added constants
  * Cosmetic rename
* com/lowagie/text/pdf/PdfReader.java
  * **Multiple changes**
    * Marked as `ssteward`, `4.2.0`, and unmarked.
* com/lowagie/text/pdf/PdfStream.java
  * Few changes
* com/lowagie/text/pdf/PdfString.java
  * Multiple changes
* com/lowagie/text/pdf/StandardDecryption.java

* com/lowagie/text/pdf/crypto/AESCipher.java
* com/lowagie/text/pdf/crypto/ARCFOUREncryption.java
* com/lowagie/text/pdf/crypto/IVGenerator.java

* com/lowagie/text/pdf/interfaces/PdfAnnotations.java
* com/lowagie/text/pdf/interfaces/PdfDocumentActions.java
* com/lowagie/text/pdf/interfaces/PdfEncryptionSettings.java
* com/lowagie/text/pdf/interfaces/PdfPageActions.java
* com/lowagie/text/pdf/interfaces/PdfRunDirection.java
* com/lowagie/text/pdf/interfaces/PdfVersion.java
* com/lowagie/text/pdf/interfaces/PdfViewerPreferences.java
* com/lowagie/text/pdf/interfaces/PdfXConformance.java

* com/lowagie/text/pdf/internal/PdfVersionImp.java
* com/lowagie/text/pdf/internal/PdfViewerPreferencesImp.java


## Based on itext-paulo-155

Changes are marked `ssteward`. Cosmetic changes are unmarked. Added `serialVersionUID` are ignored.

Dropped code has to do with images, watermarks, headers, footers, tables.

* com/lowagie/text/Anchor.java
* com/lowagie/text/Annotation.java
  * Cosmetic type casts
* com/lowagie/text/BadElementException.java
* com/lowagie/text/Chunk.java
  * Cosmetic type casts
  * Commented out methods
* com/lowagie/text/DocListener.java
  * Commented out methods
* com/lowagie/text/DocWriter.java
  * Commented out methods
  * Commented out unused(?) member 'document'
  * Cosmetic rename
* com/lowagie/text/Document.java
  * Commented out methods
* com/lowagie/text/DocumentException.java
* com/lowagie/text/Element.java
* com/lowagie/text/ElementListener.java
* com/lowagie/text/ElementTags.java
* com/lowagie/text/Entities.java
* com/lowagie/text/ExceptionConverter.java
* com/lowagie/text/Font.java
* com/lowagie/text/FontFactory.java
  * Cosmetic type casts
* com/lowagie/text/Header.java
* com/lowagie/text/List.java
* com/lowagie/text/ListItem.java
* com/lowagie/text/MarkupAttributes.java
* com/lowagie/text/Meta.java
* com/lowagie/text/PageSize.java
* com/lowagie/text/Paragraph.java
  * Commented out code
* com/lowagie/text/Phrase.java
  * Commented out code
  * Cosmetic type casts
* com/lowagie/text/Rectangle.java
* com/lowagie/text/SpecialSymbol.java
* com/lowagie/text/SplitCharacter.java
* com/lowagie/text/StringCompare.java
* com/lowagie/text/TextElementArray.java

* com/lowagie/text/markup/MarkupParser.java
  * Commented out code
* com/lowagie/text/markup/MarkupTags.java

  * **Multiple changes**
* com/lowagie/text/pdf/ArabicLigaturizer.java
* com/lowagie/text/pdf/AsianFontMapper.java
* com/lowagie/text/pdf/BadPdfFormatException.java
* com/lowagie/text/pdf/BaseFont.java
  * Cosmetic type casts
* com/lowagie/text/pdf/BidiLine.java
  * Commented out code
* com/lowagie/text/pdf/BidiOrder.java
  * Cosmetic unused variable removed
* com/lowagie/text/pdf/ByteBuffer.java
* com/lowagie/text/pdf/CFFFont.java
  * Commented out code
* com/lowagie/text/pdf/CFFFontSubset.java
  * Commented out code
* com/lowagie/text/pdf/CJKFont.java
* com/lowagie/text/pdf/CMYKColor.java
* com/lowagie/text/pdf/ColorDetails.java
* com/lowagie/text/pdf/ColumnText.java
  * Commented out code
* com/lowagie/text/pdf/DefaultFontMapper.java
* com/lowagie/text/pdf/DocumentFont.java
* com/lowagie/text/pdf/EnumerateTTC.java
* com/lowagie/text/pdf/ExtendedColor.java
* com/lowagie/text/pdf/ExtraEncoding.java
* com/lowagie/text/pdf/FdfWriter.java
  * **Multiple changes**
* com/lowagie/text/pdf/FontDetails.java
* com/lowagie/text/pdf/FontMapper.java
* com/lowagie/text/pdf/GlyphList.java
* com/lowagie/text/pdf/HyphenationEvent.java
* com/lowagie/text/pdf/OutputStreamCounter.java
* com/lowagie/text/pdf/PRAcroForm.java
* com/lowagie/text/pdf/PRIndirectReference.java
* com/lowagie/text/pdf/PageResources.java
* com/lowagie/text/pdf/PatternColor.java
* com/lowagie/text/pdf/PdfAcroForm.java
  * Added code
* com/lowagie/text/pdf/PdfAction.java
* com/lowagie/text/pdf/PdfAnnotation.java
  * Modified code
* com/lowagie/text/pdf/PdfAppearance.java
* com/lowagie/text/pdf/PdfBoolean.java
* com/lowagie/text/pdf/PdfBorderArray.java
* com/lowagie/text/pdf/PdfBorderDictionary.java
* com/lowagie/text/pdf/PdfChunk.java
  * Commented out methods
* com/lowagie/text/pdf/PdfColor.java
* com/lowagie/text/pdf/PdfContentByte.java
  * Cosmetic type casts
  * Commented out methods
* com/lowagie/text/pdf/PdfContents.java
* com/lowagie/text/pdf/PdfCopy.java
  * **Multiple changes**
* com/lowagie/text/pdf/PdfCopyFieldsImp.java
  * Commented out methods
* com/lowagie/text/pdf/PdfDashPattern.java
* com/lowagie/text/pdf/PdfDate.java
* com/lowagie/text/pdf/PdfDestination.java
  * Added class instance for Windows nonsense.
* com/lowagie/text/pdf/PdfDocument.java
  * **Multiple changes**
  * Unmarked changes in `getAcroForm`.
* com/lowagie/text/pdf/PdfEncodings.java
  * Added class instance for Windows nonsense.
  * Changes in `convertToString`.
* com/lowagie/text/pdf/PdfEncryptionStream.java
* com/lowagie/text/pdf/PdfException.java
* com/lowagie/text/pdf/PdfFileSpecification.java
  * Changes in `PdfFileSpecification` and `fileEmbedded`.
  * Commented out methods
* com/lowagie/text/pdf/PdfFont.java
  * Commented out code
* com/lowagie/text/pdf/PdfFormField.java
* com/lowagie/text/pdf/PdfFormXObject.java
* com/lowagie/text/pdf/PdfFunction.java
  * Cosmetic rename
* com/lowagie/text/pdf/PdfGState.java
* com/lowagie/text/pdf/PdfImportedPage.java
  * Commented out methods
* com/lowagie/text/pdf/PdfIndirectObject.java
  * Multiple changes
* com/lowagie/text/pdf/PdfIndirectReference.java
* com/lowagie/text/pdf/PdfLayerMembership.java
* com/lowagie/text/pdf/PdfLine.java
  * Commented out code
* com/lowagie/text/pdf/PdfLister.java
* com/lowagie/text/pdf/PdfLiteral.java
* com/lowagie/text/pdf/PdfMediaClipData.java
* com/lowagie/text/pdf/PdfNameTree.java
  * Multiple changes
* com/lowagie/text/pdf/PdfNull.java
  * Cosmetic rename
* com/lowagie/text/pdf/PdfNumber.java
* com/lowagie/text/pdf/PdfNumberTree.java
* com/lowagie/text/pdf/PdfOCG.java
* com/lowagie/text/pdf/PdfOCProperties.java
* com/lowagie/text/pdf/PdfObject.java
  * Cosmetic rename
  * Subtle unicode encoding change
* com/lowagie/text/pdf/PdfOutline.java
* com/lowagie/text/pdf/PdfPKCS7.java
  * Unmarked changes `DER` -> `ASN1` and `X509` -> `X500`
    * Probably to adapt to BouncyCastle 1.47 and can be ignored
    * See itext commit bcab23ec2a97622aedabb44411f90f804c179965
* com/lowagie/text/pdf/PdfPSXObject.java
* com/lowagie/text/pdf/PdfPage.java
* com/lowagie/text/pdf/PdfPageElement.java
* com/lowagie/text/pdf/PdfPageEvent.java
* com/lowagie/text/pdf/PdfPageEventHelper.java
* com/lowagie/text/pdf/PdfPageLabels.java
* com/lowagie/text/pdf/PdfPages.java
* com/lowagie/text/pdf/PdfPattern.java
* com/lowagie/text/pdf/PdfPatternPainter.java
  * Commented out code
* com/lowagie/text/pdf/PdfReaderInstance.java
* com/lowagie/text/pdf/PdfRectangle.java
* com/lowagie/text/pdf/PdfRendition.java
* com/lowagie/text/pdf/PdfResources.java
* com/lowagie/text/pdf/PdfShading.java
  * Cosmetic rename
* com/lowagie/text/pdf/PdfShadingPattern.java
* com/lowagie/text/pdf/PdfSigGenericPKCS.java
* com/lowagie/text/pdf/PdfSignature.java
  * Few changes
* com/lowagie/text/pdf/PdfSignatureAppearance.java
  * Commented out code
* com/lowagie/text/pdf/PdfSpotColor.java
  * Gcj nonsense, ignore
* com/lowagie/text/pdf/PdfStamper.java
  * Commented out methods
* com/lowagie/text/pdf/PdfStamperImp.java
  * Multiple changes
* com/lowagie/text/pdf/PdfStructureElement.java
* com/lowagie/text/pdf/PdfStructureTreeRoot.java
* com/lowagie/text/pdf/PdfTemplate.java
* com/lowagie/text/pdf/PdfTextArray.java
* com/lowagie/text/pdf/PdfTransition.java
* com/lowagie/text/pdf/PdfTransparencyGroup.java
* com/lowagie/text/pdf/PdfWriter.java
  * **Multiple changes**
    * Marked as `ssteward` and unmarked.
  * Added constants, unmarked
    * Some constants look mismatched
* com/lowagie/text/pdf/PdfXConformanceException.java
* com/lowagie/text/pdf/Pfm2afm.java
  * Unmarked changes `private` -> `protected` 
  * Commented out code
* com/lowagie/text/pdf/RandomAccessFileOrArray.java
  * **Multiple changes**
    * Marked as `//`?
* com/lowagie/text/pdf/SequenceList.java
* com/lowagie/text/pdf/ShadingColor.java
* com/lowagie/text/pdf/SimpleBookmark.java
* com/lowagie/text/pdf/SimpleNamedDestination.java
* com/lowagie/text/pdf/SimpleXMLDocHandler.java
* com/lowagie/text/pdf/SimpleXMLDocHandlerComment.java
* com/lowagie/text/pdf/SimpleXMLParser.java
* com/lowagie/text/pdf/SpotColor.java
* com/lowagie/text/pdf/StampContent.java
* com/lowagie/text/pdf/TrueTypeFont.java
  * Commented out code
* com/lowagie/text/pdf/TrueTypeFontSubSet.java
* com/lowagie/text/pdf/TrueTypeFontUnicode.java
* com/lowagie/text/pdf/Type1Font.java
  * Cosmetic type casts
* com/lowagie/text/pdf/VerticalText.java

* com/lowagie/text/pdf/codec/postscript/paparser.jj

* com/lowagie/text/pdf/fonts/Courier-Bold.afm
* com/lowagie/text/pdf/fonts/Courier-BoldOblique.afm
* com/lowagie/text/pdf/fonts/Courier-Oblique.afm
* com/lowagie/text/pdf/fonts/Courier.afm
* com/lowagie/text/pdf/fonts/FontsResourceAnchor.java
* com/lowagie/text/pdf/fonts/Helvetica-Bold.afm
* com/lowagie/text/pdf/fonts/Helvetica-BoldOblique.afm
* com/lowagie/text/pdf/fonts/Helvetica-Oblique.afm
* com/lowagie/text/pdf/fonts/Helvetica.afm
* com/lowagie/text/pdf/fonts/License-Adobe.txt
* com/lowagie/text/pdf/fonts/Symbol.afm
* com/lowagie/text/pdf/fonts/Times-Bold.afm
* com/lowagie/text/pdf/fonts/Times-BoldItalic.afm
* com/lowagie/text/pdf/fonts/Times-Italic.afm
* com/lowagie/text/pdf/fonts/Times-Roman.afm
* com/lowagie/text/pdf/fonts/ZapfDingbats.afm


## Based on Apache Batik?!

* com/lowagie/text/pdf/TIFFLZWDecoder.java


## Deprecated

* com/lowagie/text/pdf/PangoArabicShapping.java


## Appeared out of the blue

* com/lowagie/text/pdf/ICC_Profile.java

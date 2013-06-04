package com.puppycrawl.tools.checkstyle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.junit.ComparisonFailure;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import static org.junit.Assert.*;


/**
 * This class is copied from the test suite of Checkstyle itself but extended
 * with further behavior.
 */
public abstract class BaseCheckTestSupport {

  /**
   * Contains all necessary information for an error used by the test suite.
   * Create an Err with the {@link arrAt} method.
   */
  protected static class Err {

    public final int row;
    public final int column;

    /** Create an Err with the {@link arrAt} method. */
    private Err(final int row, final int column) {
      this.row = row;
      this.column = column;
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(this).addValue(row).addValue(column).toString();
    }
  }

  protected Err errAt(final int row, final int column) {
    return new Err(row, column);
  }

  /** a brief logger that only display info about errors */
  protected static class BriefLogger extends DefaultLogger {

    public BriefLogger(final OutputStream out) {
      super(out, true);
    }

    @Override
    public void auditStarted(final AuditEvent evt) {}

    @Override
    public void fileFinished(final AuditEvent evt) {}

    @Override
    public void fileStarted(final AuditEvent evt) {}

    @Override
    public void auditFinished(final AuditEvent evt) {
      closeStreams();
    }
  }

  protected final ByteArrayOutputStream mBAOS = new ByteArrayOutputStream();
  protected final PrintStream mStream = new PrintStream(mBAOS);
  protected final Properties mProps = new Properties();

  public static DefaultConfiguration createCheckConfig(final Class<?> aClazz) {
    final DefaultConfiguration checkConfig = new DefaultConfiguration(
        aClazz.getName());
    return checkConfig;
  }

  protected Checker createChecker(final Configuration aCheckConfig)
      throws Exception {
    final DefaultConfiguration dc = createCheckerConfig(aCheckConfig);
    final Checker c = new Checker();
    // make sure the tests always run with english error messages
    // so the tests don't fail in supported locales like german
    final Locale locale = Locale.ENGLISH;
    c.setLocaleCountry(locale.getCountry());
    c.setLocaleLanguage(locale.getLanguage());
    c.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
    c.configure(dc);
    c.addListener(new BriefLogger(mStream));
    return c;
  }

  protected DefaultConfiguration createCheckerConfig(final Configuration aConfig) {
    final DefaultConfiguration dc = new DefaultConfiguration("configuration");
    final DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
    // make sure that the tests always run with this charset
    dc.addAttribute("charset", "iso-8859-1");
    dc.addChild(twConf);
    twConf.addChild(aConfig);
    return dc;
  }

  protected File fileWithSuffix(final String suffix) {
    // TODO move out constant
    final String testDir = "src/test/resources/";
    final String fileName = "/" + suffix + ".java";
    final String[] dirs = getClass().getName().split("\\.");
    dirs[dirs.length - 1] = dirs[dirs.length - 1].toLowerCase();

    return new File(System.getProperty("testinputs.dir"), testDir
        + StringUtils.join(dirs, '/') + fileName);
  }

  protected String fileNameWithSuffix(final String suffix) {
    return fileWithSuffix(suffix).getAbsolutePath();
  }

  protected void checkTest(final DefaultConfiguration config, final File path, final List<Err> errors) {
    try {
      final Checker c = createChecker(config);
      c.process(Arrays.asList(path));

      // process each of the lines
      final ByteArrayInputStream bais = new ByteArrayInputStream(
          mBAOS.toByteArray());
      final LineNumberReader lnr = new LineNumberReader(new InputStreamReader(
          bais));

      for (int i = 0, size = errors.size(); i < size; ++i) {
        final String msg = lnr.readLine();
        final Err err = errors.get(i);
        final String regex = ".*:" + err.row + ":" + err.column + ":.*";

        if (!msg.matches(regex)) {
          throw new ComparisonFailure("Error message didn't match the expected regex,", regex, msg);
        }
      }

      c.destroy();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  protected String getPath(final String aFilename) throws IOException {
    final File f = new File(System.getProperty("testinputs.dir"), aFilename);
    return f.getCanonicalPath();
  }

  protected String getSrcPath(final String aFilename) throws IOException {
    final File f = new File(System.getProperty("testsrcs.dir"), aFilename);
    return f.getCanonicalPath();
  }

  protected void verify(final Configuration aConfig, final String aFileName,
      final String[] aExpected) throws Exception {
    verify(createChecker(aConfig), aFileName, aFileName, aExpected);
  }

  protected void verify(final Checker aC, final String aFileName,
      final String[] aExpected) throws Exception {
    verify(aC, aFileName, aFileName, aExpected);
  }

  protected void verify(final Checker aC, final String aProcessedFilename,
      final String aMessageFileName, final String[] aExpected) throws Exception {
    verify(aC, new File[] {
      new File(aProcessedFilename)
    }, aMessageFileName, aExpected);
  }

  protected void verify(final Checker aC, final File[] aProcessedFiles,
      final String aMessageFileName, final String[] aExpected) throws Exception {
    mStream.flush();
    final List<File> theFiles = Lists.newArrayList();
    Collections.addAll(theFiles, aProcessedFiles);
    final int errs = aC.process(theFiles);

    // process each of the lines
    final ByteArrayInputStream bais = new ByteArrayInputStream(
        mBAOS.toByteArray());
    final LineNumberReader lnr = new LineNumberReader(new InputStreamReader(
        bais));

    for (int i = 0; i < aExpected.length; i++) {
      final String expected = aMessageFileName + ":" + aExpected[i];
      final String actual = lnr.readLine();
      assertEquals("error message " + i, expected, actual);
    }

    assertEquals("unexpected output: " + lnr.readLine(), aExpected.length, errs);
    aC.destroy();
  }
}

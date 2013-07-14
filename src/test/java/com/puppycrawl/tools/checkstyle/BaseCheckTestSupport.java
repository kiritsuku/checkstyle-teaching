package com.puppycrawl.tools.checkstyle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.junit.ComparisonFailure;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Configuration;


/**
 * This class is copied from the test suite of Checkstyle itself but extended
 * with further behavior.
 */
public abstract class BaseCheckTestSupport {

  /**
   * Contains all necessary information for an Checkstyle report used by the
   * test suite. An instance of this class needs to be created with the
   * {@link errAt} or {@link infoAt} methods.
   */
  protected static class Report {

    public final int row;
    public final int column;
    public final ReportType type;
    public final String message;

    /** Create an Err with the {@link arrAt} method. */
    private Report(final int row, final int column, final ReportType type, final String message) {
      this.row = row;
      this.column = column;
      this.type = type;
      this.message = message;
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(this).addValue(row).addValue(column).addValue(type).toString();
    }
  }

  protected enum ReportType {
    Err, Info
  }

  protected static final List<Report> NO_REPORT = Lists.newArrayList();

  protected Report errAt(final int row, final int column) {
    return new Report(row, column, ReportType.Err, "");
  }

  protected Report metricAt(final int row, final int column, final String message) {
    return new Report(row, column, ReportType.Info, "metric:"+message);
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

  private final ByteArrayOutputStream mBAOS = new ByteArrayOutputStream();
  private final PrintStream mStream = new PrintStream(mBAOS);

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

    return new File(System.getProperty("testinputs.dir"),
        testDir + StringUtils.join(dirs, '/') + fileName);
  }

  protected String fileNameWithSuffix(final String suffix) {
    return fileWithSuffix(suffix).getAbsolutePath();
  }

  protected void test(final DefaultConfiguration config, final List<Report> reports) {
    final String testClassName = new Exception().getStackTrace()[1].getMethodName();
    testFile(config, fileWithSuffix(testClassName), reports);
  }

  protected void test(final DefaultConfiguration config, final String fileNameSuffix, final List<Report> reports) {
    testFile(config, fileWithSuffix(fileNameSuffix), reports);
  }

  protected void testFile(final DefaultConfiguration config, final File path, final List<Report> reports) {
    try {
      final Checker c = createChecker(config);
      c.process(Arrays.asList(path));

      try (
          LineNumberReader lnr = new LineNumberReader(
              new InputStreamReader(
                  new ByteArrayInputStream(
                      mBAOS.toByteArray())))) {

        for (int i = 0, size = reports.size(); i < size; ++i) {
          final String msg = lnr.readLine();
          final Report r = reports.get(i);
          final String regex = ".*:" + r.row + ":" + r.column + ": " + (!r.message.isEmpty() ? r.message : ".*");

          if (msg == null || !msg.matches(regex)) {
            throw new ComparisonFailure("Reported message didn't match the expected regex,", regex, msg);
          }
        }

        final String msg = lnr.readLine();
        if (msg != null) {
          throw new AssertionError("Message retrieved but nothing expected. Message: " + msg);
        }

      }

      c.destroy();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
}

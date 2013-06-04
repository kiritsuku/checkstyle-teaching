package edu.kit.checkstyle.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;


public class VerboseListener extends AutomaticBean implements AuditListener {

  private PrintWriter mWriter = new PrintWriter(System.out);
  private boolean mCloseOut = false;
  private int mTotalErrors;
  private int mErrors;

  public void auditStarted(final AuditEvent aEvt) {
    mTotalErrors = 0;
    mWriter.println("Audit started.\n");
  }

  public void fileStarted(final AuditEvent aEvt) {
    mErrors = 0;
    mWriter.println("Started checking file '" + aEvt.getFileName() + "'.");
  }

  public void auditFinished(final AuditEvent aEvt) {
    mWriter.println("Audit finished. Total errors: " + mTotalErrors);
    mWriter.flush();
    if (mCloseOut) {
      mWriter.close();
    }
  }

  public void fileFinished(final AuditEvent aEvt) {
    mWriter.println("Finished checking file '" + aEvt.getFileName()
        + "'. Errors: " + mErrors);
    mWriter.println();
  }

  public void addError(final AuditEvent aEvt) {
    printEvent(aEvt);
    if (SeverityLevel.ERROR.equals(aEvt.getSeverityLevel())) {
      mErrors++;
      mTotalErrors++;
    }
  }

  public void setFile(final String aFileName) throws FileNotFoundException {
    final OutputStream out = new FileOutputStream(aFileName);
    mWriter = new PrintWriter(out);
    mCloseOut = true;
  }

  public void addException(final AuditEvent aEvt, final Throwable aThrowable) {
    printEvent(aEvt);
    aThrowable.printStackTrace(System.out);
    mErrors++;
    mTotalErrors++;
  }

  private void printEvent(final AuditEvent aEvt)
  {
    String msg = errorMsg(aEvt);
    mWriter.println(msg);
  }

  private String errorMsg(AuditEvent e) {
    try {
      String[] fileParts = e.getFileName().split("/");
      int line = e.getLine();
      String source = FileUtils.readLines(new File(e.getFileName())).get(line - 1);
      String indent = StringUtils.leftPad("", e.getColumn() - 1);
      List<String> parts = Arrays.asList(fileParts[fileParts.length - 1], ":",
          Integer.toString(line), ": ", e.getSeverityLevel().toString(),
          ": ", e.getMessage(), ";\n\t", source, "\n\t", indent, "^");
      return StringUtils.join(parts.iterator(), "");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return "";
  }
}

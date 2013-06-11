package edu.kit.checkstyle.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

  private PrintWriter writer = new PrintWriter(System.out);
  private boolean closeOut = false;
  private int totalErrors;
  private int fileErrors;

  public void auditStarted(final AuditEvent e) {
    totalErrors = 0;
    writer.println("Audit started.\n");
  }

  public void fileStarted(final AuditEvent e) {
    fileErrors = 0;
    writer.println("Started checking file '" + e.getFileName() + "'.");
  }

  public void auditFinished(final AuditEvent e) {
    writer.println("Audit finished. Total errors: " + totalErrors);
    writer.flush();
    if (closeOut) {
      writer.close();
    }
  }

  public void fileFinished(final AuditEvent e) {
    writer.println("Finished checking file '" + e.getFileName() + "'. Errors: " + fileErrors);
    writer.println();
  }

  public void addError(final AuditEvent e) {
    printEvent(e);
    if (SeverityLevel.ERROR.equals(e.getSeverityLevel())) {
      fileErrors++;
      totalErrors++;
    }
  }

  public void setFile(final String fileName) throws FileNotFoundException {
    writer = new PrintWriter(new FileOutputStream(fileName));
    closeOut = true;
  }

  public void addException(final AuditEvent e, final Throwable aThrowable) {
    printEvent(e);
    aThrowable.printStackTrace(System.out);
    fileErrors++;
    totalErrors++;
  }

  private void printEvent(final AuditEvent e)
  {
    final String msg = errorMsg(e);
    writer.println(msg);
  }

  private String errorMsg(final AuditEvent e) {
    try {
      final String[] fileParts = e.getFileName().split("/");
      final int line = e.getLine();
      final String source = FileUtils.readLines(new File(e.getFileName())).get(line - 1);
      final String indent = StringUtils.leftPad("", e.getColumn() - 1);
      final List<String> parts = Arrays.asList(
          fileParts[fileParts.length - 1], ":",
          Integer.toString(line), ": ", e.getSeverityLevel().toString(),
          ": ", e.getMessage(), ";\n\t", source, "\n\t", indent, "^");
      return StringUtils.join(parts.iterator(), "");
    } catch (final IOException ex) {
      ex.printStackTrace();
    }
    return "";
  }
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;

public class start {

	/**
	 * @param args
	 */
	private static DicomInputStream dis;
	private static String sourceDir;

	private static String toProcess;

	private static final String USAGE = "dcmfileget [-Vh] <dcmfile>";
	private static final String DESCRIPTION = "Dump some DICOM attributes in this form: \n"+
											  "filepath|PatientName|Date|patientid|studyid|studyinstanceuid\n\n"+
											  "  \nOptions:";
	private static final String EXAMPLE = null;

	public static void listFilesForFolder(final File folder) {

		for (final File fileEntry : folder.listFiles()) {

			if (fileEntry.isDirectory()) {

				listFilesForFolder(fileEntry);

			} else {
				
				toProcess = folder + File.separator + fileEntry.getName();
				//System.out.println("");
				//System.out.println("Processing " + toProcess);

				DicomInputStream dis = null;
				try {

					dis = new DicomInputStream(fileEntry);

					DicomObject obj = null;

					try {
						obj = dis.readDicomObject();

						String patientname = obj.getString(Tag.PatientName);
						String studydate = obj.getString(Tag.StudyDate);
						String studyid = obj.getString(Tag.StudyID);
						String patientid = obj.getString(Tag.PatientID);
						String siuid = obj.getString(Tag.StudyInstanceUID);
						System.out.println(fileEntry.toString()+"|"+patientname + "|" + studydate + "|"
								+ patientid + "|" + studyid + "|" + siuid);

						DateFormat df = new SimpleDateFormat("yyyyMMdd");

						Date date;

						try {
							date = df.parse(studydate);

							String startDateString1 = df.format(date);
							// System.out.println("Date in format yyyyMMdd: " +
							// startDateString1);

							DateFormat year = new SimpleDateFormat("yyyy");
							DateFormat month = new SimpleDateFormat("MM");
							DateFormat day = new SimpleDateFormat("dd");

						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println("3 " + e.getMessage() + " fuck");
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						System.out.println("2 " + e.getMessage() + " ignoring");

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("1 " + e.getMessage() + " ignoring");
				}

			}
		}
	}

	private static CommandLine parse(String[] args)
			throws org.apache.commons.cli.ParseException {

		Options opts = new Options();

		OptionBuilder.withArgName("v");
		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("verbose");
		opts.addOption(OptionBuilder.create("v"));

		opts.addOption("h", "help", false, "print this message");

		CommandLine cl = null;

		cl = new PosixParser().parse(opts, args);
		if (cl.hasOption('V')) {
			Package p = start.class.getPackage();
			System.out.println("dcmfileget v" + p.getImplementationVersion());
			System.exit(0);
		}
		if (cl.hasOption('h') || cl.getArgList().isEmpty()) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(USAGE, DESCRIPTION, opts, EXAMPLE);
			System.exit(0);
		}
		return cl;

	}

	private static void exit(String msg) {
		System.err.println(msg);
		System.err.println("Try 'dcmfileget -h' for more information.");
		System.exit(1);
	}

	public static void main(String[] args) {

		CommandLine cl;
		try {
			cl = parse(args);

			sourceDir = (String) cl.getArgList().get(0);

			final File folder = new File(sourceDir);

			//System.out.println("- " + sourceDir);

			listFilesForFolder(folder);

		} catch (org.apache.commons.cli.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

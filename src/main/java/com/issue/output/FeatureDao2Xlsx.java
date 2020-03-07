/**
 * 
 */
package com.issue.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.issue.configuration.GlobalParams;
import com.issue.contract.Dao2Output;
import com.issue.contract.Feature;
import com.issue.contract.IFeatureDao;
import com.issue.utils.Features;

/**
 * The Class FeatureDao2Xlsx.
 */
public class FeatureDao2Xlsx implements Dao2Output {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(FeatureDao2Xlsx.class);

	/** The dao. */
	private IFeatureDao<String, Feature> dao;

	/** The global params. */
	private GlobalParams globalParams;

	/**
	 * Instantiates a new feature dao 2 csv.
	 *
	 * @param dao          the dao
	 * @param globalParams the global params
	 */
	public FeatureDao2Xlsx(IFeatureDao<String, Feature> dao, GlobalParams globalParams) {
		this.dao = dao;
		this.globalParams = globalParams;
	}

	/**
	 * Default font.
	 *
	 * @param workbook the workbook
	 * @return the XSSF font
	 */
	private XSSFFont defaultFont(Workbook workbook) {
		XSSFFont contentFont = ((XSSFWorkbook) workbook).createFont();
		contentFont.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		contentFont.setFontHeightInPoints((short) 11);

		return contentFont;
	}

	/**
	 * Link font.
	 *
	 * @param workbook the workbook
	 * @return the XSSF font
	 */
	private XSSFFont linkFont(Workbook workbook) {
		XSSFFont contentFont = ((XSSFWorkbook) workbook).createFont();
		contentFont.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		contentFont.setFontHeightInPoints((short) 11);
		contentFont.setUnderline(FontUnderline.SINGLE);
		contentFont.setColor(IndexedColors.BLUE.getIndex());

		return contentFont;
	}

	/**
	 * Column style.
	 *
	 * @param workbook  the workbook
	 * @param alignment the alignment
	 * @param isRowLine the is row line
	 * @return the cell style
	 */
	private CellStyle columnStyle(Workbook workbook, final HorizontalAlignment alignment, final boolean isRowLine) {
		// Set style
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFont(defaultFont(workbook));
		contentStyle.setAlignment(alignment);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		if (isRowLine)
			contentStyle.setBorderTop(BorderStyle.THIN);
		return contentStyle;
	}

	/**
	 * Column percentage style.
	 *
	 * @param workbook  the workbook
	 * @param isRowLine the is row line
	 * @return the cell style
	 */
	private CellStyle columnPercentageStyle(Workbook workbook, final boolean isRowLine) {
		// Set style
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFont(defaultFont(workbook));
		contentStyle.setDataFormat(workbook.createDataFormat().getFormat("0%"));
		contentStyle.setAlignment(HorizontalAlignment.CENTER);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		if (isRowLine)
			contentStyle.setBorderTop(BorderStyle.THIN);
		return contentStyle;
	}

	/**
	 * Column style 4 hyperlink.
	 *
	 * @param workbook  the workbook
	 * @param alignment the alignment
	 * @param isRowLine the is row line
	 * @return the cell style
	 */
	private CellStyle columnStyle4Hyperlink(Workbook workbook, final HorizontalAlignment alignment,
			final boolean isRowLine) {
		// Set style
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFont(linkFont(workbook));
		contentStyle.setAlignment(alignment);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		if (isRowLine)
			contentStyle.setBorderTop(BorderStyle.THIN);

		return contentStyle;
	}

	/**
	 * Last row style.
	 *
	 * @param workbook the workbook
	 * @return the cell style
	 */
	private CellStyle lastRowStyle(Workbook workbook) {
		// Set style
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFont(defaultFont(workbook));
		contentStyle.setBorderTop(BorderStyle.THIN);
		return contentStyle;
	}

	/**
	 * Content 2 cell.
	 *
	 * @param workbook  the workbook
	 * @param cell      the cell
	 * @param value     the value
	 * @param isRowLine the is row line
	 */
	private void content2Cell(Workbook workbook, Cell cell, final String value, final boolean isRowLine) {
		cell.setCellValue(value);
		cell.setCellStyle(columnStyle(workbook, HorizontalAlignment.LEFT, isRowLine));
	}

	/**
	 * Content 2 cell.
	 *
	 * @param workbook  the workbook
	 * @param cell      the cell
	 * @param value     the value
	 * @param isRowLine the is row line
	 */
	private void content2Cell(Workbook workbook, Cell cell, final boolean value, final boolean isRowLine) {
		cell.setCellValue(value);
		cell.setCellStyle(columnStyle(workbook, HorizontalAlignment.CENTER, isRowLine));
	}

	/**
	 * Content 2 cell.
	 *
	 * @param workbook  the workbook
	 * @param cell      the cell
	 * @param value     the value
	 * @param link      the link
	 * @param isRowLine the is row line
	 */
	private void content2Cell(Workbook workbook, Cell cell, final String value, final String link,
			final boolean isRowLine) {
		cell.setCellValue(value);
		final Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
		href.setAddress(link);
		cell.setHyperlink(href);
		cell.setCellStyle(columnStyle4Hyperlink(workbook, HorizontalAlignment.LEFT, isRowLine));
	}

	/**
	 * Content 2 cell.
	 *
	 * @param workbook  the workbook
	 * @param cell      the cell
	 * @param value     the value
	 * @param isRowLine the is row line
	 */
	private void content2Cell(Workbook workbook, Cell cell, final Integer value, final boolean isRowLine) {
		if (value != null)
			cell.setCellValue(value);
		else
			cell.setCellValue("n/a");
		cell.setCellStyle(columnStyle(workbook, HorizontalAlignment.CENTER, isRowLine));
	}

	/**
	 * Content 2 cell.
	 *
	 * @param workbook  the workbook
	 * @param cell      the cell
	 * @param value     the value
	 * @param isRowLine the is row line
	 */
	private void content2Cell(Workbook workbook, Cell cell, final Double value, final boolean isRowLine) {
		if (value != null) {
			cell.setCellValue(value);
			cell.setCellStyle(columnPercentageStyle(workbook, isRowLine));
		} else {
			cell.setCellValue("n/a");
			cell.setCellStyle(columnStyle(workbook, HorizontalAlignment.CENTER, isRowLine));
		}
	}

	/**
	 * Creates the last line.
	 *
	 * @param workbook the workbook
	 * @param rowIdx   the row idx
	 */
	private void createLastLine(Workbook workbook, int rowIdx) {
		Row content = workbook.getSheetAt(0).createRow(rowIdx);
		IntStream.range(0, 10).forEach(i -> {
			Cell cell = content.createCell(i);
			cell.setCellStyle(lastRowStyle(workbook));
		});
	}

	/**
	 * Generate features 4 xlsx.
	 *
	 * @param workbook the workbook
	 */
	private void generateFeatures4Xlsx(Workbook workbook) {
		// prepare list of features
		List<Feature> values = Features.prepareFeaturesList(dao);

		// Add features
		String teamName = "";
		int rowIdx = 1;
		for (Feature feature : values) {
			// Check whether row should contain top line
			boolean isRowLine = true;
			if (teamName.equalsIgnoreCase(feature.getTeam()))
				isRowLine = false;
			teamName = feature.getTeam();

			// Initialize column counter
			int colIdx = 0;

			// Create new row
			Row content = workbook.getSheetAt(0).createRow(rowIdx++);

			// Column 1 - Team
			content2Cell(workbook, content.createCell(colIdx), feature.getTeam(), isRowLine);

			// Column 2 - Issue key
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx), feature.getKey(),
					globalParams.getIssueUri() + "/" + feature.getKey(), isRowLine);

			// Column 3 - Feature summary
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx),
					Optional.ofNullable(feature.getFeatureSummary()).orElse("n/a"), isRowLine);

			// Column 4 - Estimated
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx), feature.getEstimated(), isRowLine);

			// Column 5 - Opened
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx), feature.getOpened(), isRowLine);

			// Column 6 - In progress
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx), feature.getInProgress(), isRowLine);

			// Column 7 - Closed
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx), feature.getClosed(), isRowLine);

			// Column 8 - Done
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx), feature.getRawDone(), isRowLine);

			// Column 9 - Stories defined
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx), feature.isStoriesDefined(), isRowLine);

			// Column 10 - Link to epic report
			colIdx++;
			content2Cell(workbook, content.createCell(colIdx), feature.getFeatureSummary() + " report",
					globalParams.getEpicReportUri() + feature.getKey(), isRowLine);
		}

		// Create last empty row with top line
		createLastLine(workbook, rowIdx);
	}

	/**
	 * Header style.
	 *
	 * @param workbook the workbook
	 * @return the cell style
	 */
	private CellStyle headerStyle(Workbook workbook) {
		// Define font
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		font.setFontHeightInPoints((short) 8);
		font.setBold(true);

		// Create style
		CellStyle headerStyle = workbook.createCellStyle();

		// Style settings
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFont(font);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		return headerStyle;
	}

	/**
	 * Header row.
	 *
	 * @param columnNames the column names
	 * @param headerStyle the header style
	 * @param header      the header
	 */
	private void headerRow(List<String> columnNames, CellStyle headerStyle, Row header) {
		IntStream.range(0, columnNames.size()).forEach(i -> {
			Cell headerCell = header.createCell(i);
			headerCell.setCellValue(columnNames.get(i));
			headerCell.setCellStyle(headerStyle);
		});
	}

	/**
	 * Provide xlsx content.
	 *
	 * @param workbook the workbook
	 */
	private void xlsxContent(Workbook workbook) {
		// Throw exception when path to file is null
		String fileLocation = Optional.ofNullable(globalParams.getOutputFileName4Xlsx())
				.orElseThrow(IllegalArgumentException::new);

		// Write the content to output xlsx file
		try (FileOutputStream outputStream = new FileOutputStream(fileLocation);) {
			workbook.write(outputStream);
		} catch (IOException e) {
			logger.error("Content for XLSX file wasn't provided!");
		}
	}

	/**
	 * Provide content.
	 *
	 * @return the string
	 */
	@Override
	public String provideContent() {
		// add page header
		try (Workbook workbook = new XSSFWorkbook();) {

			// Define column titles
			List<String> columnNames = List.of("Team", "Epic JIRA link", "Feature / Function", "Estimated [SPs]",
					"Opened [SPs]", "In Progress [SPs]", "Closed [SPs]", "Done[%]", "Stories defined", "Epic report");

			// Define header row style
			CellStyle headerStyle = headerStyle(workbook);

			// Create sheet
			Sheet sheet = workbook.createSheet("Features");

			// Create header row
			Row header = sheet.createRow(0);

			// Fill header row
			headerRow(columnNames, headerStyle, header);

			// Put content into table
			generateFeatures4Xlsx(workbook);

			// Autosize columns
			IntStream.range(0, columnNames.size()).forEach(sheet::autoSizeColumn);

			xlsxContent(workbook);

		} catch (IOException e1) {
			logger.error("File with XLSX content wasn't created!");
		}

		return null;
	}

	/**
	 * Provide output file name.
	 *
	 * @return the string
	 */
	@Override
	public String provideOutputFileName() {
		return globalParams.getOutputFileName4Xlsx();
	}
}

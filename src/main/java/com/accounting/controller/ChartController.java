package com.accounting.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.accounting.model.EntityManagerProvider;
import com.accounting.model.firm.Expense;
import com.accounting.model.firm.Firm;
import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plots;

public class ChartController {
	private static ChartController controller;
	private static EntityManagerProvider provider;

	private ChartController() {
		provider = EntityManagerProvider.getInstance();
	}

	public static ChartController getInstance() {
		if (controller == null) {

			controller = new ChartController();
		}
		return controller;
	}

	public String createChartURLProjects(Date from, Date to) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(from);
		String url;
		int yearFrom = calendar.get(Calendar.YEAR);
		calendar.setTime(to);
		int yearTo = calendar.get(Calendar.YEAR);
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		if (yearFrom == yearTo) {

			List<Object[]> months = mng.createNamedQuery(Firm.QUERY_FIND_FIRM_FOR_MONTH)
					.setParameter("year", yearFrom).getResultList();
			long january = 0;
			long february = 0;
			long march = 0;
			long april = 0;
			long may = 0;
			long june = 0;
			long july = 0;
			long august = 0;
			long september = 0;
			long october = 0;
			long november = 0;
			long december = 0;
			for (Object[] obj : months) {
				switch (((Integer) obj[0]).intValue()) {
				case 1:
					january = ((Long) obj[1]).longValue();
					break;
				case 2:
					february = ((Long) obj[1]).longValue();
					break;
				case 3:
					march = ((Long) obj[1]).longValue();
					break;
				case 4:
					april = ((Long) obj[1]).longValue();
					break;
				case 5:
					may = ((Long) obj[1]).longValue();
					break;
				case 6:
					june = ((Long) obj[1]).longValue();
					break;
				case 7:
					july = ((Long) obj[1]).longValue();
					break;
				case 8:
					august = ((Long) obj[1]).longValue();
					break;
				case 9:
					september = ((Long) obj[1]).longValue();
					break;
				case 10:
					october = ((Long) obj[1]).longValue();
					break;
				case 11:
					november = ((Long) obj[1]).longValue();
					break;
				case 12:
					december = ((Long) obj[1]).longValue();
					break;
				}
			}
			List<Long> values = new ArrayList<>();
			values.add(january);
			values.add(february);
			values.add(march);
			values.add(april);
			values.add(may);
			values.add(june);
			values.add(july);
			values.add(august);
			values.add(september);
			values.add(october);
			values.add(november);
			values.add(december);

			long max = values.stream().mapToLong(Long::longValue).max().getAsLong();
			List<Long> valuesAranged = new ArrayList<>();
			for (Iterator<Long> iterator = values.iterator(); iterator.hasNext();) {
				Long long1 = iterator.next();
				if (long1.longValue() != 0) {
					double maxD = (double) max;
					double long1D = (double) long1.longValue();
					valuesAranged.add((long) ((long1D / maxD) * 100));
				} else {
					valuesAranged.add(long1.longValue());
				}
			}
			String[] labels = new String[12];
			labels[0] = "Януари";
			labels[1] = "Февруари";
			labels[2] = "Март";
			labels[3] = "Април";
			labels[4] = "Май";
			labels[5] = "Юни";
			labels[6] = "Юли";
			labels[7] = "Август";
			labels[8] = "Септември";
			labels[9] = "Октомври";
			labels[10] = "Ноември";
			labels[11] = "Декември";

			url = drawChart(max, valuesAranged, labels, "Месец", "Брой фирми", "Фирми");
		} else {
			List<Object[]> yearsCount = mng.createNamedQuery(Firm.QUERY_FIND_FIRM_FOR_YEARS)
					.setParameter("startDate", from).setParameter("endDate", to).getResultList();
			String[] labels = new String[yearsCount.size()];
			List<Long> values = new ArrayList<>();
			int i = 0;
			for (Object[] obj : yearsCount) {
				labels[i] = String.valueOf(((Integer) obj[0]).intValue());
				values.add((Long) obj[1]);
				i++;
			}
			long max = values.stream().mapToLong(Long::longValue).max().getAsLong();
			List<Long> valuesAranged = new ArrayList<>();
			for (Iterator<Long> iterator = values.iterator(); iterator.hasNext();) {
				Long long1 = iterator.next();
				double maxD = (double) max;
				double long1D = (double) long1.longValue();
				valuesAranged.add((long) ((long1D / maxD) * 100));
			}

			url = drawChart(max, valuesAranged, labels, "Година", "Брой фирми", "Фирми");
		}
		mng.getTransaction().commit();
		return url;
	}
	
	public String createChartURLExpenses(String type, String firm, Date from, Date to) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(from);
		String url;
		int yearFrom = calendar.get(Calendar.YEAR);
		calendar.setTime(to);
		int yearTo = calendar.get(Calendar.YEAR);
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		if (yearFrom == yearTo) {

			List<Object[]> months = mng.createNamedQuery(Expense.QUERY_FIND_EXPENSE_FOR_MONTH)
					.setParameter("year", yearFrom).setParameter("name", firm).setParameter("type", type).getResultList();
			long january = 0;
			long february = 0;
			long march = 0;
			long april = 0;
			long may = 0;
			long june = 0;
			long july = 0;
			long august = 0;
			long september = 0;
			long october = 0;
			long november = 0;
			long december = 0;
			for (Object[] obj : months) {
				switch (((Integer) obj[0]).intValue()) {
				case 1:
					january = ((Long) obj[1]).longValue();
					break;
				case 2:
					february = ((Long) obj[1]).longValue();
					break;
				case 3:
					march = ((Long) obj[1]).longValue();
					break;
				case 4:
					april = ((Long) obj[1]).longValue();
					break;
				case 5:
					may = ((Long) obj[1]).longValue();
					break;
				case 6:
					june = ((Long) obj[1]).longValue();
					break;
				case 7:
					july = ((Long) obj[1]).longValue();
					break;
				case 8:
					august = ((Long) obj[1]).longValue();
					break;
				case 9:
					september = ((Long) obj[1]).longValue();
					break;
				case 10:
					october = ((Long) obj[1]).longValue();
					break;
				case 11:
					november = ((Long) obj[1]).longValue();
					break;
				case 12:
					december = ((Long) obj[1]).longValue();
					break;
				}
			}
			List<Long> values = new ArrayList<>();
			values.add(january);
			values.add(february);
			values.add(march);
			values.add(april);
			values.add(may);
			values.add(june);
			values.add(july);
			values.add(august);
			values.add(september);
			values.add(october);
			values.add(november);
			values.add(december);

			long max = values.stream().mapToLong(Long::longValue).max().getAsLong();
			List<Long> valuesAranged = new ArrayList<>();
			for (Iterator<Long> iterator = values.iterator(); iterator.hasNext();) {
				Long long1 = iterator.next();
				if (long1.longValue() != 0) {
					double maxD = (double) max;
					double long1D = (double) long1.longValue();
					valuesAranged.add((long) ((long1D / maxD) * 100));
				} else {
					valuesAranged.add(long1.longValue());
				}
			}
			String[] labels = new String[12];
			labels[0] = "Януари";
			labels[1] = "Февруари";
			labels[2] = "Март";
			labels[3] = "Април";
			labels[4] = "Май";
			labels[5] = "Юни";
			labels[6] = "Юли";
			labels[7] = "Август";
			labels[8] = "Септември";
			labels[9] = "Октомври";
			labels[10] = "Ноември";
			labels[11] = "Декември";

			url = drawChart(max, valuesAranged, labels, "Месец", "Пари", type);
		} else {
			List<Object[]> yearsCount = mng.createNamedQuery(Expense.QUERY_FIND_EXPENSE_FOR_YEARS)
					.setParameter("startDate", from).setParameter("endDate", to).setParameter("name", firm).setParameter("type", type).getResultList();
			String[] labels = new String[yearsCount.size()];
			List<Long> values = new ArrayList<>();
			int i = 0;
			for (Object[] obj : yearsCount) {
				labels[i] = String.valueOf(((Integer) obj[0]).intValue());
				values.add((Long) obj[1]);
				i++;
			}
			long max = values.stream().mapToLong(Long::longValue).max().getAsLong();
			List<Long> valuesAranged = new ArrayList<>();
			for (Iterator<Long> iterator = values.iterator(); iterator.hasNext();) {
				Long long1 = iterator.next();
				double maxD = (double) max;
				double long1D = (double) long1.longValue();
				valuesAranged.add((long) ((long1D / maxD) * 100));
			}

			url = drawChart(max, valuesAranged, labels, "Година", "Пари", type);
		}
		mng.getTransaction().commit();
		return url;
	}
	
	public String createChartURLExpensesEnd(String firm, Date from, Date to) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(from);
		String url;
		int yearFrom = calendar.get(Calendar.YEAR);
		calendar.setTime(to);
		int yearTo = calendar.get(Calendar.YEAR);
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
			List<Object[]> yearsCountPrihod = mng.createNamedQuery(Expense.QUERY_FIND_EXPENSE_FOR_YEARS)
					.setParameter("startDate", from).setParameter("endDate", to).setParameter("name", firm).setParameter("type", "Приход").getResultList();
			List<Object[]> yearsCountRazhod = mng.createNamedQuery(Expense.QUERY_FIND_EXPENSE_FOR_YEARS)
					.setParameter("startDate", from).setParameter("endDate", to).setParameter("name", firm).setParameter("type", "Разход").getResultList();
			String[] labels = new String[yearsCountPrihod.size()];
			List<Long> values = new ArrayList<>();
			int i = 0;
			for (Object[] obj : yearsCountPrihod) {
				long value = (long) obj[1];
				for(Object[] objRz : yearsCountRazhod) {
					if(((Integer) obj[0]).intValue() == ((Integer) objRz[0]).intValue()) {
						value = value - (long) objRz[1];
					}
				}
				labels[i] = String.valueOf(((Integer) obj[0]).intValue());
				values.add(value);
				i++;
			}
			long max = values.stream().mapToLong(Long::longValue).max().getAsLong();
			List<Long> valuesAranged = new ArrayList<>();
			for (Iterator<Long> iterator = values.iterator(); iterator.hasNext();) {
				Long long1 = iterator.next();
				double maxD = (double) max;
				double long1D = (double) long1.longValue();
				valuesAranged.add((long) ((long1D / maxD) * 100));
			}

			url = drawChart(max, valuesAranged, labels, "Година", "Пари", "Печалби");
		mng.getTransaction().commit();
		return url;
	}

	private String drawChart(long max, List<Long> valuesAranged, String[] labels, String typeWidht, String typeHeight,
			String title) {
		String url;
		BarChartPlot projectsBar = Plots.newBarChartPlot(Data.newData(valuesAranged), Color.BLUEVIOLET, title);

		BarChart chart = GCharts.newBarChart(projectsBar);

		AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.BLACK, 13, AxisTextAlignment.CENTER);
		AxisLabels number = AxisLabelsFactory.newAxisLabels(typeHeight, 50.0);
		number.setAxisStyle(axisStyle);
		AxisLabels month = AxisLabelsFactory.newAxisLabels(typeWidht, 50.0);

		month.setAxisStyle(axisStyle);

		// Adding axis info to chart.
		chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(labels));
		chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, max));
		chart.addYAxisLabels(number);
		chart.addXAxisLabels(month);

		chart.setSize(1000, 300);
		chart.setBarWidth(40);
		chart.setSpaceWithinGroupsOfBars(20);
		chart.setDataStacked(true);
		chart.setTitle(title, Color.BLACK, 16);
		chart.setGrid(100, 10, 3, 2);
		chart.setBackgroundFill(Fills.newSolidFill(Color.ALICEBLUE));
		LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.LAVENDER, 100);
		fill.addColorAndOffset(Color.WHITE, 0);
		chart.setAreaFill(fill);

		url = chart.toURLString();
		return url;
	}
}

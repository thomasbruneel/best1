package scheduler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import org.w3c.dom.Document;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		procesReader pr=new procesReader();
		String nl=System.getProperty("line.separator");
		
		System.out.println("druk 1 voor de uitvoer van 10000 processen, druk 2 voor de uitvoer van 20000 processen");
		int input=sc.nextInt();
		Queue<Proces> processen = new LinkedList<Proces>();
		int deler = 0;
		if(input==1){
			processen = pr.readIn("processen10000.xml");
			deler=100;
		}
		else if(input==2){
			processen = pr.readIn("processen20000.xml");
			deler=200;
		}
		else{
			System.out.println("geen geldige invoer!");
			
		}
		

		double totNormTATfcfs=0;
		double totNormTATrrq2=0;
		double totNormTATrrq8=0;
		double totNormTATspn=0;
		double totNormTAThrrn=0;
		double totNormTATsrt=0;
		double totNormTATfb=0;
		
		double totWaitfcfs=0;
		double totWaitrrq2=0;
		double totWaitrrq8=0;
		double totWaitspn=0;
		double totWaithrrn=0;
		double totWaitsrt=0;
		double totWaitfb=0;
		

		//fcfs
		System.out.println("fcfs");
		ArrayList<Proces>fcfsres=new ArrayList<Proces>();
		FirstComeFirstServed fcfs=new FirstComeFirstServed();
		fcfsres=fcfs.start(processen);
		fcfsres.sort((Proces p1,Proces p2)->p1.getServicetime()-p2.getServicetime());
		XYSeries seriesfcfs_genTAT = new XYSeries("fcfs");
		XYSeries seriesfcfs_wait = new XYSeries("fcfs");
		System.out.println("fcfs uitgevoerd"+nl);

		//rr met q=1
		System.out.println("rr met q=2");
		ArrayList<Proces>rrq2=new ArrayList<Proces>();
		RoundRobin rr2=new RoundRobin(2);
		rrq2=rr2.start(processen);
		rrq2.sort((Proces p1,Proces p2)->p1.getServicetime()-p2.getServicetime());
		XYSeries seriesrrq2_genTAT = new XYSeries("rr met q=2");
		XYSeries seriesrrq2_wait = new XYSeries("rr met q=2");
		System.out.println("rr met q=2 uitgevoerd"+nl);
	
		//rr met q=4
		System.out.println("rr met q=8");
		ArrayList<Proces>rrq8=new ArrayList<Proces>();
		RoundRobin rr8=new RoundRobin(8);
		rrq8=rr8.start(processen);
		rrq8.sort((Proces p1,Proces p2)->p1.getServicetime()-p2.getServicetime());
		XYSeries seriesrrq8_genTAT = new XYSeries("rr met q=8");
		XYSeries seriesrrq8_wait = new XYSeries("rr met q=8");
		System.out.println("rr met q=8 uitgevoerd"+nl);
	
		//SPN
		System.out.println("spn");
		ArrayList<Proces>spnres=new ArrayList<Proces>();
		ShortestProcessNext spn=new ShortestProcessNext();
		spnres=spn.start(processen);
		spnres.sort((Proces p1,Proces p2)->p1.getServicetime()-p2.getServicetime());
		XYSeries seriesspn_genTAT = new XYSeries("spn");
		XYSeries seriesspn_wait = new XYSeries("spn");
		System.out.println("spn uitgevoerd"+nl);
	
		//HRRN
		System.out.println("hrrn");
		ArrayList<Proces>hrrnres=new ArrayList<Proces>();
		HighestResponseRatioNext hrrn=new HighestResponseRatioNext();
		hrrnres=hrrn.start(	processen);
		hrrnres.sort((Proces p1,Proces p2)->p1.getServicetime()-p2.getServicetime());
		XYSeries serieshrrn_genTAT = new XYSeries("hrrn");
		XYSeries serieshrrn_wait = new XYSeries("hrrn");
		System.out.println("hrrn uitgevoerd"+nl);
	
	
		//SRT
		System.out.println("srt");
		ArrayList<Proces>srtres=new ArrayList<Proces>();
		ShortestRemainingTime srt=new ShortestRemainingTime();
		srtres=srt.start(processen);
		srtres.sort((Proces p1,Proces p2)->p1.getServicetime()-p2.getServicetime());
		XYSeries seriessrt_genTAT = new XYSeries("srt");
		XYSeries seriessrt_wait = new XYSeries("srt");
		System.out.println("srt uitgevoerd"+nl);
		
		// feedback
		System.out.println("fb");
		ArrayList<Proces>feedbackres=new ArrayList<Proces>();
		Feedback fb=new Feedback();
		feedbackres=fb.start(processen);
		feedbackres.sort((Proces p1,Proces p2)->p1.getServicetime()-p2.getServicetime());
		XYSeries seriesfb_genTAT = new XYSeries("fb");
		XYSeries seriesfb_wait= new XYSeries("fb");
		System.out.println("fb uitgevoerd"+nl);
		
		

		// grafiek opstellen met bijhorende percentielen
		 for(int i=1;i<=srtres.size();i++){
			totNormTATfcfs=totNormTATfcfs+fcfsres.get(i-1).getNormtat();
			totNormTATrrq2=totNormTATrrq2+rrq2.get(i-1).getNormtat();
			totNormTATrrq8=totNormTATrrq8+rrq8.get(i-1).getNormtat();
			totNormTATspn=totNormTATspn+spnres.get(i-1).getNormtat();
			totNormTAThrrn=totNormTAThrrn+hrrnres.get(i-1).getNormtat();
			totNormTATsrt=totNormTATsrt+srtres.get(i-1).getNormtat();
			totNormTATfb=totNormTATfb+feedbackres.get(i-1).getNormtat();
			 
			 
			totWaitfcfs=totWaitfcfs+fcfsres.get(i-1).getWaittime();
			totWaitrrq2=totWaitrrq2+rrq2.get(i-1).getWaittime();
			totWaitrrq8=totWaitrrq8+rrq8.get(i-1).getWaittime();
			totWaitspn=totWaitspn+spnres.get(i-1).getWaittime();
			totWaithrrn=totWaithrrn+hrrnres.get(i-1).getWaittime();
			totWaitsrt=totWaitsrt+srtres.get(i-1).getWaittime();
			totWaitfb=totWaitfb+feedbackres.get(i-1).getWaittime();
			 
			 
			
			 if(i%deler==0){
				seriesfcfs_genTAT.add((i/deler),(double)(totNormTATfcfs/deler));
				seriesrrq2_genTAT.add((i/deler),(double)(totNormTATrrq2/deler));
				seriesrrq8_genTAT.add((i/deler),(double)(totNormTATrrq8/deler));
				seriesspn_genTAT.add((i/deler),(double)(totNormTATspn/deler));
				serieshrrn_genTAT.add((i/deler),(double)(totNormTAThrrn/deler));
				seriessrt_genTAT.add((i/deler),(double)(totNormTATsrt/deler));
				seriesfb_genTAT.add((i/deler),(double)(totNormTATfb/deler));
				 
				seriesfcfs_wait.add((i/deler),(double)(totWaitfcfs/deler));
				seriesrrq2_wait.add((i/deler),(double)(totWaitrrq2/deler));
				seriesrrq8_wait.add((i/deler),(double)(totWaitrrq8/deler));
				seriesspn_wait.add((i/deler),(double)(totWaitspn/deler));
				serieshrrn_wait.add((i/deler),(double)(totWaithrrn/deler));
				seriessrt_wait.add((i/deler),(double)(totWaitsrt/deler));
				seriesfb_wait.add((i/deler),(double)(totWaitfb/deler));
				 
				 
				 
				 totNormTATfcfs=0;
				 totNormTATrrq2=0;
				 totNormTATrrq8=0;
				 totNormTATspn=0;
				 totNormTAThrrn=0;
				 totNormTATsrt=0;
				 totNormTATfb=0;
				 
				 totWaitfcfs=0;
				 totWaitrrq2=0;
				 totWaitrrq8=0;
				 totWaitspn=0;
				 totWaithrrn=0;
				 totWaitsrt=0;
				 totWaitfb=0;
				 
				 
			 }
		 }
		 //genormaliseerde omlooptijd toevoegen aan dataset1
		XYSeriesCollection dataset1 = new XYSeriesCollection();
		dataset1.addSeries(seriesfcfs_genTAT);
		dataset1.addSeries(seriesrrq2_genTAT);
		dataset1.addSeries(seriesrrq8_genTAT);
		dataset1.addSeries(seriesspn_genTAT);
		dataset1.addSeries(serieshrrn_genTAT);
		dataset1.addSeries(seriessrt_genTAT);
		dataset1.addSeries(seriesfb_genTAT);
		 
		 
		//wachttijd toevoegen aan dataset2
		 XYSeriesCollection dataset2 = new XYSeriesCollection();
		 dataset2.addSeries(seriesfcfs_wait);
		 dataset2.addSeries(seriesrrq2_wait);
		 dataset2.addSeries(seriesrrq8_wait);
		 dataset2.addSeries(seriesspn_wait);
		 dataset2.addSeries(serieshrrn_wait);
		 dataset2.addSeries(seriessrt_wait);
		 dataset2.addSeries(seriesfb_wait);
		 
		 if(input==1){
			 //grafieken opstellen voor 10000 processen
			 // grafiek de genormaliseerde omplooptijd in functie van de bedieningstijd voor 10000 processen"
			 JFreeChart chartgenTAT_10000processen = ChartFactory.createXYLineChart(
			 "de genormaliseerde omplooptijd in functie van de bedieningstijd voor 10000 processen", // Title
			 "service time [JIFFY's]", // x-axis Label
			 "genorm TAT [JIFFY's]", // y-axis Label
			 dataset1, // Dataset1
			 PlotOrientation.VERTICAL, // Plot Orientation
			 true, // Show Legend
			 true, // Use tooltips
			 false // Configure chart to generate URLs?
			 );
			 chartgenTAT_10000processen.getXYPlot().getRangeAxis().setRange(0, 100);
			 try {
			 ChartUtilities.saveChartAsJPEG(new File("genormaliseerdeomlooptijd_10000processen.jpg"), chartgenTAT_10000processen, 1600, 800);
			 } catch (IOException e) {
			 System.err.println("Problem occurred creating chart.");
			 }
			 
			 
			 // grafiek de wachttijd in functie van de bedieningstijd voor 10000 processen"
			 JFreeChart chartwait_10000processen = ChartFactory.createXYLineChart(
			 "de wachttijd in functie van de bedieningstijd voor 10000 processen", // Title
			 "service time [JIFFY's]", // x-axis Label
			 "wait time [JIFFY's]", // y-axis Label
			 dataset2, // Dataset2
			 PlotOrientation.VERTICAL, // Plot Orientation
			 true, // Show Legend
			 true, // Use tooltips
			 false // Configure chart to generate URLs?
			 );
			 chartwait_10000processen.getXYPlot().getRangeAxis().setRange(0, 2000);
			 try {
			 ChartUtilities.saveChartAsJPEG(new File("wachtijd_10000processen.jpg"), chartwait_10000processen, 1600, 800);
			 } catch (IOException e) {
			 System.err.println("Problem occurred creating chart.");
			 }
			 
		 }
		 else if(input==2){
			 //grafieken opstellen voor 20000 processen
			 // grafiek de genormaliseerde omplooptijd in functie van de bedieningstijd voor 20000 processen"
			 JFreeChart chartgenTAT_20000processen = ChartFactory.createXYLineChart(
			 "de genormaliseerde omplooptijd in functie van de bedieningstijd voor 20000 processen", // Title
			 "service time [JIFFY's]", // x-axis Label
			 "genorm TAT [JIFFY's]", // y-axis Label
			 dataset1, // Dataset1
			 PlotOrientation.VERTICAL, // Plot Orientation
			 true, // Show Legend
			 true, // Use tooltips
			 false // Configure chart to generate URLs?
			 );
			 chartgenTAT_20000processen.getXYPlot().getRangeAxis().setRange(0, 100);
			 try {
			 ChartUtilities.saveChartAsJPEG(new File("genormaliseerdeomlooptijd_20000processen.jpg"), chartgenTAT_20000processen, 1600, 800);
			 } catch (IOException e) {
			 System.err.println("Problem occurred creating chart.");
			 }
			 
			 
			 // grafiek de wachttijd in functie van de bedieningstijd voor 10000 processen"
			 JFreeChart chartwait_20000processen = ChartFactory.createXYLineChart(
			 "de wachttijd in functie van de bedieningstijd voor 20000 processen", // Title
			 "service time [JIFFY's]", // x-axis Label
			 "wait time [JIFFY's]", // y-axis Label
			 dataset2, // Dataset2
			 PlotOrientation.VERTICAL, // Plot Orientation
			 true, // Show Legend
			 true, // Use tooltips
			 false // Configure chart to generate URLs?
			 );
			 chartwait_20000processen.getXYPlot().getRangeAxis().setRange(0, 2000);
			 try {
			 ChartUtilities.saveChartAsJPEG(new File("wachtijd_20000processen.jpg"), chartwait_20000processen, 1600, 800);
			 } catch (IOException e) {
			 System.err.println("Problem occurred creating chart.");
			 }
			 
		 }
		 
		 

		  }


}

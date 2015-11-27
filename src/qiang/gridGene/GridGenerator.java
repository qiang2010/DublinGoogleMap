package qiang.gridGene;

import qiang.bean.OneCell;
import qiang.util.FileUtil;
import qiang.util.TwoGPSDis;


/**
 * 
 * 给定北京市左上角的GPS点，向右40个格子，向下28个格子。
 * @author jq
 *
 */
public class GridGenerator {

	 
	public static void main(String[] args) {
		 OneCell[][] grids =  getGridCells();
		 System.out.println(grids.length);
		// FileUtil tempF = new FileUtil(ansPath+"cellCenters.txt");
		 for(int i = 0 ; i < latCount;i++){
			 for(int j = 0 ; j < lngCount;j++ ){
				// tempF.writeLine(grids[i][j].getCenterLngLatTab());
				// System.out.println(grids[i][j].getCenterLngLatTab());
			 }
		 }
	}
	public static double latDiff = 0.009;
	public static double lngDiff = 0.015;
	
	
	public static double  leftBottomLng  = -6.565033-lngDiff/2;
	public static double  leftBottomLat = 53.221897-latDiff/2;
	public static int latCount = 30;
	public static int lngCount = 40;
	

	public static OneCell[][] grids  = null ;// 28*40
	
	public static OneCell[][] getGridCells(){
		if(grids != null) return grids;
		grids = getAllCells();
		return grids;
	}
	public static OneCell[][] getAllCells(){
		grids = new OneCell[latCount][lngCount];
		double curLat =GridGenerator.leftBottomLat ,curLng= GridGenerator.leftBottomLng;
		for(int j = 0 ; j < latCount;j++){
			curLat = GridGenerator.leftBottomLat+latDiff*j;
			for(int i = 0 ; i < lngCount;i++){
				curLng = GridGenerator.leftBottomLng+lngDiff*i;
				grids[j][i] = new OneCell(j, i, curLat, curLng);
				//System.out.println(":"+curLng+"\t"+curLat);
				//System.out.println(grids[j][i].getCenterLngLatTab());
			}
		}
		return grids;
	}
	
	

	
	static String ansPath = "E:\\北京分区聚类\\区域划分结果\\";
	public static void gridGPSGenetator(double leftBottomLat,double leftBottomLng,String fileName){
		
		FileUtil gpsPoint = new FileUtil(ansPath+leftBottomLat+"_"+leftBottomLng+"_"+fileName+".txt");
	
		double curLat =GridGenerator.leftBottomLat ,curLng= GridGenerator.leftBottomLng;
		for(int j = 0 ; j < latCount+1;j++){
			curLat = GridGenerator.leftBottomLat+latDiff*j;
			for(int i = 0 ; i < lngCount+1;i++){
				curLng = GridGenerator.leftBottomLng+lngDiff*i;
				gpsPoint.writeLine(curLng+"\t"+curLat);
			}
		}
	}
	public static void gridDisGenetator(double leftBottomLat,double leftBottomLng,String fileName){
		//FileUtil gpsDis = new FileUtil(ansPath+leftBottomLat+"_"+leftBottomLng+"_"+fileName+"Dis.txt");
		
		double curLat =GridGenerator.leftBottomLat ,curLng= GridGenerator.leftBottomLng;
		double tempLat,tempLng,tempDis;
		double maxLngDis = Double.MIN_NORMAL,maxLatDis  = Double.MIN_NORMAL;
		double minLngDis = Double.MAX_VALUE,minLatDis = Double.MAX_VALUE;
		for(int j = 0 ; j < latCount;j++){
			curLat = GridGenerator.leftBottomLat+latDiff*j;
//			String line="";
			for(int i = 0 ; i < lngCount;i++){
				curLng = GridGenerator.leftBottomLng+lngDiff*i;
				if(j > 0){
					tempLat = curLat+latDiff;
					tempDis = TwoGPSDis.haversineInM(curLat, curLng, tempLat, curLng);
					maxLatDis = maxLatDis > tempDis? maxLatDis:tempDis;
					minLatDis = minLatDis > tempDis? tempDis:minLatDis;
				}
				if(i >0 ){
					tempLng = curLng - lngDiff;
					tempDis = TwoGPSDis.haversineInM(curLat, tempLng, curLat, curLng);
					maxLngDis = maxLngDis>tempDis ? maxLngDis:tempDis;
					minLngDis = minLngDis > tempDis? tempDis:minLngDis;
				}
			}
		}
		System.out.println("lat:" + (maxLatDis-minLatDis));
		System.out.println("lat:" + (maxLngDis-minLngDis));
	}
	
}

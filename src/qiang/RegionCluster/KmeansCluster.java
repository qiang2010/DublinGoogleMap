package qiang.RegionCluster;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import qiang.bean.KMeansClusterBean;
import qiang.bean.OneCell;
import qiang.getGridRoadDis.GetRegionDis;
import qiang.gridGene.GridGenerator;
import qiang.util.FileUtil;
import qiang.util.TwoGPSDis;

public class KmeansCluster {

	public static void main(String[] args) {
		
		
//		Set<KMeansClusterBean> pivo = new KmeansCluster().initCluster();
		KmeansCluster.initData();
		Set<KMeansClusterBean> pivo = new KmeansCluster().kmeans();
		File f = new File(ansPath);
		f.deleteOnExit();
		f.mkdirs();
		int count = 1;
		FileUtil curF = new FileUtil(ansPath+"centers.txt");
		for(KMeansClusterBean cur:pivo){
			
			cur.calCenter();
			curF.writeLine(cur.getCenterLng()+"\t"+cur.getCenterLat());
//			for(OneCell cell:cur.getCluserCells()){
//				curF.writeLine(cell.getCenterLngLatTab());
//			}
			count++;
		}
//		System.out.println("pivo size:"+pivo.size());
//		for(KMeansClusterBean cur:pivo){
//			System.out.println(cur.getCluserCells().size()+"\t"+cur.getCol()+"\t"+cur.getRow());
//		}
	}
	
	static String ansPath = "E:\\都柏林分区聚类\\聚类结果\\";
	 
	static double disThreshold = 1.0; // 质心变化范围。
	static OneCell[][] grids ;
	static double[][][][] allRegionDis;
	static void initData(){
		 System.out.println("init allRegin");
		 allRegionDis = GetRegionDis.getAllRegionDis();
		 grids = GridGenerator.getGridCells();
		 System.out.println("init allRegin");
		 
			for(int i =0;i<30; i++){
				for(int j =0;j <40 ;j++){
					for(int x = 0 ; x < 30 ; x++){
						for(int y = 0 ; y<40;y++){
						//	if( i == x && j == y)continue;
							if(allRegionDis[i][j][x][y] < 0.001){
								System.err.println("wrong i j x y\t"+ i +"\t"+j+"\t"+x+"\t"+y);
							}
						}
					}
					
				}
			}
		 
	}
	 int row;
	 int col;
	 
	public Set<KMeansClusterBean> kmeans(){
		
		// 获取初始质心
		Set<KMeansClusterBean> pivo = initCluster();
		Set<KMeansClusterBean> newPivo;
		int deep =0;
		System.out.println(pivo.size());
		for(KMeansClusterBean cur:pivo){
			System.out.println(cur.getCluserCells().size());
		}
		KMeansClusterBean minCluser = null;
		double minDis = Double.MAX_VALUE;
		double curDis;
		for(;;){
			System.out.println(deep++);
			for(int i = 0;i< this.row;i++){
				for(int j =0; j < this.col;j++){
					//if(!grids[i][j].isCellValid())continue;
					minDis = unavaibleDis;
					for(KMeansClusterBean curC:pivo){
//						int x = curC.getRow();
//						int y = curC.getCol();
						curDis = disBetweenReginAndCluster(i, j,curC);
					//	System.out.println(curDis);
						if(curDis < minDis){
							minDis = curDis;
							minCluser = curC;
						}
					}
					if(Math.abs(unavaibleDis - minDis)<0.1){
						//System.out.println("continue");
						grids[i][j].setCellInvalid();
						continue;
					}
					// 添加新的
					minCluser.addOneCellToNewCluster(grids[i][j]);
				}
			}
			
//			for(KMeansClusterBean cur:pivo){
//				System.out.println("new:\t"+cur.getNewCluserCells().size());
//			}
			
			// 
			int cou = 0 ;
			newPivo = new HashSet<KMeansClusterBean>();
			for(KMeansClusterBean curC:pivo){
				KMeansClusterBean tempNewKM = new KMeansClusterBean();
				//System.out.println("curC.getCluserCells()"+curC.getCluserCells().size());
				if(curC.getNewCluserCells().size()==0){
					System.out.println("sizi is 0");
					continue;
				}
				tempNewKM.setCluserCells(curC.getNewCluserCells());
				tempNewKM.calCenter();
				newPivo.add(tempNewKM);
				if(disBetweenTwoCluser(curC,tempNewKM)< disThreshold){
					cou++;
				}
			}
			if(cou ==  newPivo.size()){
				return newPivo;
			}
			// 否则继续迭代，需要清空簇中的list
			pivo = newPivo;
		}
	}
	
	/**
	 *  返回给定cell，和类的距离。
	 *  给定cell和类中所有可达的cell的距离平均值
	 * @param i
	 * @param j
	 * @param km
	 * @return
	 */
	public double disBetweenReginAndCluster(int i, int j ,KMeansClusterBean km){
		int count=0;
		double curDis;
		double disSum = 0;
		int x,y;
		for(OneCell cell:km.getCluserCells()){
			x = cell.getRow();
			y = cell.getCol();
			curDis = disBetweentTwoCell(i, j, x, y);
			if(Math.abs(unavaibleDis - curDis)< 0.1){
				continue;
			}
			disSum+=curDis;
			//System.out.println("curDis"+curDis+"\t"+count+"\t"+i+"\t"+j+"\t"+x+"\t"+y+"\t");
			count++;
		}
		if(count==0){
			//System.out.println("un");
			return unavaibleDis;
		}
		return disSum/count;
	}
	
	
	/**
	 * 两个类之间的距离
	 * @param km1
	 * @param km2
	 * @return
	 */
	public double disBetweenTwoCluser(KMeansClusterBean km1, KMeansClusterBean km2){
		//return disBetweentTwoCell(km1.getRow(), km1.getCol(), km2.getRow(), km2.getCol());
		double ans = TwoGPSDis.haversineInKM(km1.getCenterLat(), km1.getCenterLng(), km2.getCenterLat(), km2.getCenterLng());
		
		return ans;
		
	}

	public double disBetweentTwoCell(int i ,int j ,int x,int y){
		//if(i == x && j == y) return 0;
		if(isRowColValid(i, j) && isRowColValid(x, y)){
			
			return allRegionDis[i][j][x][y];
		}
		//System.err.println("ddd wrong i j x y\t"+ i +"\t"+j+"\t"+x+"\t"+y);
		return unavaibleDis;
		
	}
	boolean isRowColValid(int i ,int j ){
		if(i < 0 || j < 0) return false;
		if(i > GridGenerator.latCount-1 || j > GridGenerator.lngCount-1)return false;
		return true;
	}
	
	public static double unavaibleDis = 100000.0; // 两个区域不可达的阈值
	

	
	Set<KMeansClusterBean> pivo;
	// 初始化质心
	public  Set<KMeansClusterBean> initCluster(){
		this.row = GridGenerator.latCount;
		this.col = GridGenerator.lngCount;
		pivo = new HashSet<KMeansClusterBean>();
		
		double []lng_lat = {
//				-6.406785,	53.408577,
//				-6.300300,	53.407082,
//				-6.213683,	53.409950,
//				-6.492805,	53.365669,
//				-6.415781,	53.358871,
//				-6.354373,	53.349720,
//				-6.272937,	53.345551,
//				-6.427933,	53.292732,
//				-6.323512,	53.288967,
//				-6.217859,	53.288841
				
				-6.406785,	53.408577,
				-6.300300,	53.407082,
				-6.213683,	53.409950,
				-6.492805,	53.365669,
				-6.415781,	53.358871,
				-6.354373,	53.349720,
				-6.272937,	53.345551,
				-6.235650,	53.332817,
				-6.323512,	53.288967,
				-6.217859,	53.288841,
				
				};
		KMeansClusterBean tempKM;
		
		for(int i = 0 ; i < lng_lat.length;i+=2){
			tempKM = new KMeansClusterBean();
			tempKM.setCenterLat(lng_lat[i+1]);
			tempKM.setCenterLng(lng_lat[i]);
			tempKM.setCluserCenter(tempKM.getCenterLat(), tempKM.getCenterLng());
			pivo.add(tempKM);
		}
		
	 
		// 第一次将所有点分配一次。
		KMeansClusterBean minCluser = null;
		double minDis = unavaibleDis;
		double curDis=unavaibleDis;
		for(int i = 0;i< this.row;i++){
			for(int j =0; j < this.col;j++){
				
				if(grids[i][j].isCellValid()==false)continue;
				minDis = unavaibleDis;
				for(KMeansClusterBean curC:pivo){
					int x = curC.getRow();
					int y = curC.getCol();
					curDis = disBetweentTwoCell(i, j, x, y);
					if(curDis < minDis){
						minDis = curDis;
						minCluser = curC;
					}
				}
				if(Math.abs(unavaibleDis - minDis)<0.1){
					//System.out.println("continue");
					grids[i][j].setCellInvalid();
					continue;
				}
				minCluser.addOneCell(grids[i][j]);
			}
		}
		for(KMeansClusterBean curC:pivo){
			curC.calCenter();
		}
		return pivo;
	}
}

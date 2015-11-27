package qiang.getGridRoadDis;

import qiang.RegionCluster.KmeansCluster;
import qiang.gridGene.GridGenerator;
import qiang.util.FileUtil;



/**
 * 
 * 获取任意两个格子之间的距离。
 * 
 * @author jq
 *
 */
public class GetRegionDis {

	public static void main(String[] args) {
		
		System.out.println("ccc");
		getAllRegionDis();
		check();
		
	}
	
	static void check(){
		
		for(int i =0;i<row; i++){
			for(int j =0;j <col ;j++){
				for(int x = 0 ; x < row ; x++){
					for(int y = 0 ; y<col;y++){
					//	if( i == x && j == y)continue;
						System.out.println("chek");
						if(allRegionDis[i][j][x][y] < 0.001){
							System.err.println("wrong i j x y\t"+ i +"\t"+j+"\t"+x+"\t"+y);
						}
					}
				}
				
			}
		}
		
		
	}
	
	static int row = GridGenerator.latCount;
	static int col = GridGenerator.lngCount;
	static double[][][][] allRegionDis ; 
	static String path = "E:\\都柏林分区聚类\\gridDist.txt";
	public static double[][][][] getAllRegionDis(){
		
		allRegionDis = new double[row][col][row][col];
		for(int i =0;i<row; i++){
			for(int j =0;j <col ;j++){
				for(int x = 0 ; x < row ; x++){
					for(int y = 0 ; y<col;y++){
						 allRegionDis[i][j][x][y]  = KmeansCluster.unavaibleDis;
					}
				}
			}
		}
		FileUtil fileUtil = new FileUtil(path);
		String line ;
		String []splits;
		int i,j,x,y;
		while((line = fileUtil.readLine())!=null){
			splits = line.split("\\s+");
			i = Integer.parseInt(splits[0].trim());
			j = Integer.parseInt(splits[1].trim());
			x = Integer.parseInt(splits[4].trim());
			y = Integer.parseInt(splits[5].trim());
			double diss = Double.parseDouble(splits[8]);
			if(diss < 0.001) {
				//System.out.println("diss");
				diss = KmeansCluster.unavaibleDis;
			}
			allRegionDis[i][j][x][y] = allRegionDis[x][y][i][j] = diss;
		}
		return allRegionDis;
	}
	
}

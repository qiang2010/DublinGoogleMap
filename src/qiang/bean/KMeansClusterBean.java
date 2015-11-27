package qiang.bean;

import java.util.ArrayList;

import qiang.gridGene.GridGenerator;

public class KMeansClusterBean {

	
	double centerLat;
	double centerLng;
	int row;
	int col;

	ArrayList<OneCell> cluserCells ;
	ArrayList<OneCell> newCluserCells ;
	
	public void calCenter(){
		
		int size = cluserCells.size();
		double centerLa=0,centerLn=0;
		OneCell tempOne;
		for(int i =0;i < size; i++){
			tempOne = cluserCells.get(i);
			centerLa += tempOne.getCenterLat()/size;
			centerLn += tempOne.getCenterLng()/size;
		}
		
		setCluserCenter(centerLa, centerLn);
		System.out.println("centerLa:"+centerLa+"\t"+centerLn);
	}
	
	public void setCluserCenter(double centerLat,double centerLng){
		this.centerLat = centerLat;
		this.centerLng = centerLng;
		this.row = (int)((centerLat - GridGenerator.leftBottomLat)/GridGenerator.latDiff);
		this.col = (int)((centerLng - GridGenerator.leftBottomLng)/GridGenerator.lngDiff);
		if(this.row > GridGenerator.latCount||this.row < 0 || this.col > GridGenerator.lngCount || this.col < 0){
			System.out.println("wrong center.sssss");
			System.out.println(this.row +"\t"+this.col+"\t"+centerLat+"\t"+centerLng);
		}
	}
	
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}

	
	
	

	public ArrayList<OneCell> getCluserCells() {
		return cluserCells;
	}
	public void setCluserCells(ArrayList<OneCell> cluserCells) {
		this.cluserCells = cluserCells;
	}
	public double getCenterLat() {
		return centerLat;
	}
	public void setCenterLat(double centerLat) {
		this.centerLat = centerLat;
	}
	public double getCenterLng() {
		return centerLng;
	}
	public void setCenterLng(double centerLng) {
		this.centerLng = centerLng;
	}
	public KMeansClusterBean(){
		this.centerLat = 0;
		this.centerLng =0;
		this.cluserCells = new ArrayList<>();
		this.newCluserCells = new ArrayList<>();
	}
	

	public boolean addOneCellToNewCluster(OneCell oneCell){
		if(this.newCluserCells == null){
			this.newCluserCells = new ArrayList<>();
		}
		this.newCluserCells.add(oneCell);
		return true;
	}
	
	
	
	public ArrayList<OneCell> getNewCluserCells() {
		return newCluserCells;
	}

	public void setNewCluserCells(ArrayList<OneCell> newCluserCells) {
		this.newCluserCells = newCluserCells;
	}

	public boolean addOneCell(OneCell oneCell){
		if(this.cluserCells == null){
			this.cluserCells = new ArrayList<>();
		}
		this.cluserCells.add(oneCell);
		return true;
	}
	
}

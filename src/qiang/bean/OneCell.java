package qiang.bean;

import qiang.gridGene.GridGenerator;

public class OneCell {

	
	boolean isValid ;
	double cellLeftBottomLat;
	double cellLeftBottomLng;
	
	double centerLat;
	double centerLng;
	
	int id;
	int row;
	int col;
	public OneCell(int row,int col,double cellLeftBottomLat,double cellLeftBottomLng){
		this.row = row;
		this.col = col;
		this.isValid = true;
		this.cellLeftBottomLat = cellLeftBottomLat;
		this.cellLeftBottomLng = cellLeftBottomLng;
		this.centerLat =  cellLeftBottomLat + GridGenerator.latDiff/2; 
		this.centerLng =  cellLeftBottomLng + GridGenerator.lngDiff/2;
	}
	public void setCellInvalid(){
		this.isValid = false;
	}
	public boolean isCellValid(){
		return this.isValid;
	}
	public String getCenterLngLatTab(){
		return this.centerLng+"\t"+this.centerLat;
	}
	public double getcellLeftBottomLat() {
		return cellLeftBottomLat;
	}
	public void setcellLeftBottomLat(double cellLeftBottomLat) {
		this.cellLeftBottomLat = cellLeftBottomLat;
	}
	public double getcellLeftBottomLng() {
		return cellLeftBottomLng;
	}
	public void setcellLeftBottomLng(double cellLeftBottomLng) {
		this.cellLeftBottomLng = cellLeftBottomLng;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
}

package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Menu {
	String kodeString;
	String namaString;
	Integer hargaInteger;
	Integer stokInteger;
	
	public Menu(String kodeString, String namaString, Integer hargaInteger, Integer stokInteger) {
		super();
		this.kodeString = kodeString;
		this.namaString = namaString;
		this.hargaInteger = hargaInteger;
		this.stokInteger = stokInteger;
	}

	public String getKodeString() {
		return kodeString;
	}

	public void setKodeString(String kodeString) {
		this.kodeString = kodeString;
	}

	public String getNamaString() {
		return namaString;
	}

	public void setNamaString(String namaString) {
		this.namaString = namaString;
	}

	public Integer getHargaInteger() {
		return hargaInteger;
	}

	public void setHargaInteger(Integer hargaInteger) {
		this.hargaInteger = hargaInteger;
	}

	public Integer getStokInteger() {
		return stokInteger;
	}

	public void setStokInteger(Integer stokInteger) {
		this.stokInteger = stokInteger;
	}
	
	public StringProperty kodeProperty() {
		return new SimpleStringProperty(kodeString);
	}
	
	public StringProperty namaProperty() {
		return new SimpleStringProperty(namaString);
	}
	
	public StringProperty hargaProperty() {
		return new SimpleStringProperty(Integer.toString(hargaInteger));
	}
	
	public StringProperty stokProperty() {
		return new SimpleStringProperty(Integer.toString(stokInteger));
	}
}

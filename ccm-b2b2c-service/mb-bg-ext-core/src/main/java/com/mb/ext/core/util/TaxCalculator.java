package com.mb.ext.core.util;

import java.math.BigDecimal;

public class TaxCalculator {
	
	public static  synchronized BigDecimal getTax(BigDecimal taxableAmount){
		
		BigDecimal tax = new BigDecimal(0);
		BigDecimal increment = taxableAmount.subtract(new BigDecimal(3500));
		if(!(increment.compareTo(new BigDecimal(0))==1)){
			
		}
		else if(!(increment.compareTo(new BigDecimal(1500))==1)){
			tax = increment.multiply(new BigDecimal(0.03));
		}
		else if(!(increment.compareTo(new BigDecimal(4500))==1)){
			tax = increment.multiply(new BigDecimal(0.1)).subtract(new BigDecimal(105));
		}
		else if(!(increment.compareTo(new BigDecimal(9000))==1)){
			tax = increment.multiply(new BigDecimal(0.2)).subtract(new BigDecimal(555));
		}
		else if(!(increment.compareTo(new BigDecimal(35000))==1)){
			tax = increment.multiply(new BigDecimal(0.25)).subtract(new BigDecimal(1005));
		}
		else if(!(increment.compareTo(new BigDecimal(55000))==1)){
			tax = increment.multiply(new BigDecimal(0.3)).subtract(new BigDecimal(2755));
		}
		else if(!(increment.compareTo(new BigDecimal(80000))==1)){
			tax = increment.multiply(new BigDecimal(0.35)).subtract(new BigDecimal(5505));
		}else
			tax = increment.multiply(new BigDecimal(0.45)).subtract(new BigDecimal(13505));
		
		return tax;
	}
	
}

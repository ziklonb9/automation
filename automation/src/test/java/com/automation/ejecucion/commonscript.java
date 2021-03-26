package com.automation.ejecucion;

import org.testng.annotations.Test;
import com.automation.wrapperclass;

public class commonscript {
  @Test
  public void f() 
  {
	wrapperclass auto=new wrapperclass(1);
	auto.ejecutar();
  }
}

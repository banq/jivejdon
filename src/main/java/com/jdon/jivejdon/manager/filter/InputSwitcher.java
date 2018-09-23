/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.manager.filter;

import java.io.BufferedReader;
import java.io.StringReader;

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.repository.dao.SetupDao;
import com.jdon.util.UtilValidate;

public class InputSwitcher implements InputSwitcherIF  {
	
    private final static Logger log = LogManager.getLogger(InputSwitcher.class);
	
	private  String PERSISTENCE_NAME = "INPUT_PERMIT";
	
	private boolean inputPermit = false;
	private String des;
	
    private SetupDao setupDao;
    
    public InputSwitcher(SetupDao setupDao) {
		this.setupDao = setupDao;
		this.loadSwitchValue();
	}
      
    
    /* (non-Javadoc)
	 * @see com.jdon.jivejdon.manager.filter.InputSwitcherIF#isInputPermit()
	 */
    public boolean isInputPermit() {
		return inputPermit;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.manager.filter.InputSwitcherIF#setInputPermit(boolean)
	 */
	public void setInputPermit(boolean inputPermit, String des) {
		this.inputPermit = inputPermit;
		this.des = des;
		saveSwitchValue();
	}
	
	public String getDes() {
		return des;
	}


	private void saveSwitchValue(){
    	try {    		
    		if (!inputPermit) des = "";
    		if (inputPermit && UtilValidate.isEmpty(des))
               des = (Boolean.valueOf(inputPermit)).toString();
		    setupDao.saveSetupValue(PERSISTENCE_NAME, des);   
		} finally {		
		}
    }
    
    /**
     * Load the list of banned ips from a file.  This clears the old list and
     * loads exactly what is in the file.
     */
    private synchronized void loadSwitchValue() {
		try {
			String ipListText = setupDao.getSetupValue(PERSISTENCE_NAME);
			BufferedReader in = new BufferedReader(new StringReader(ipListText));

			String desP = null;
			if ((desP = in.readLine()) != null) {
				if (!UtilValidate.isEmpty(desP) ){
					this.inputPermit = true;
					this.des = desP;
				}
				log.debug("READED " + desP);
			}

			in.close();
		} catch (Exception ex) {
			log.error("Error loading loadSwitchValues error", ex);
		}

	}
   


}

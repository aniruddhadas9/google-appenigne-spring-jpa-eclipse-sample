package com.package.rest.listeners;

import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public final class EMF {
	private static final Logger logger = Logger.getLogger(EMF.class.getName());
    private EntityManagerFactory emfInstance;
    private static EMF emf;
    
    private EMF() {
    }

    public EntityManagerFactory get() {
        if(emfInstance == null) {
            emfInstance = Persistence.createEntityManagerFactory("transactions-optional");
        }
        return emfInstance;
    }

    public static EMF getInstance() {
    	logger.info("EMF|getInstance|reached");
        if(emf == null) {
            emf = new EMF();
        }
        return emf;
    }
}

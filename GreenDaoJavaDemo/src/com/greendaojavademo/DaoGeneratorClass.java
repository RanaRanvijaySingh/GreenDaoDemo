package com.greendaojavademo;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class DaoGeneratorClass {
	
	private final static String NAME 			= "name";
	private final static String EMAIL		 	= "email";
	private final static String PHONE_NUMBER 	= "phone_number";

	public static void main(String [] args)throws Exception {
		Schema schema = new Schema(3, "com.greendaodemo");
		createDb(schema);
		new DaoGenerator().generateAll(schema, "/home/webonise/practice-workspace/GreenDao/GreenDaoDemo/src");
	}
	
	private static void createDb(Schema schema){
		Entity user = schema.addEntity("User");
		user.addIdProperty();
		user.addStringProperty(NAME).notNull();
		user.addStringProperty(EMAIL).notNull();
		user.addStringProperty(PHONE_NUMBER).notNull();
	}
	
}

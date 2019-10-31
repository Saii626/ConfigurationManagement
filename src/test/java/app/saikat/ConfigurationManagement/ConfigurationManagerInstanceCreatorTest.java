// package app.saikat.ConfigurationManagement;

// import org.junit.Test;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import app.saikat.ConfigurationManagement.interfaces.ConfigurationManager;
// import app.saikat.ConfigurationManagement.interfaces.OnConfigurationChange;

// import java.io.File;
// import java.io.IOException;

// import static org.junit.Assert.*;

// public class ConfigurationManagerInstanceCreatorTest {

//	 private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

//	 @Test
//	 public void createConfigurationManager() throws IOException {

//		 File testconfigFile = new File(System.getProperty("user.home")+"/test/config_instance_test.conf");

//		 if (testconfigFile.exists()) {
//			 testconfigFile.delete();
//		 }

//		 ConfigurationManager configurationManager = ConfigurationManagerInstanceHandler.createInstance(testconfigFile);

//		 configurationManager.put("name", "saikat");
//		 configurationManager.put("age", 10);

//		 configurationManager.addOnConfigurationChangeListener("name", (OnConfigurationChange<String>) (oldV, newV) -> {
//			 logger.debug("Config {} changing from {} to {}", "name", oldV, newV);
//		 });

//		 configurationManager.put("name", "Saikat");
//		 configurationManager.syncConfigurations();

//		 ConfigurationManager configManager = ConfigurationManagerInstanceHandler.createInstance(testconfigFile);
//		 assertEquals("Wrong name", configManager.<String>getRaw("name"), "Saikat");
//		 assertEquals("Wrong age", (int) configManager.<Integer>getRaw("age"), 10);
//	 }

//	 @Test
//	 public void gsonTest() throws IOException {

//		 File testconfigFile = new File(System.getProperty("user.home")+"/test/config_gson_test.conf");

//		 if (testconfigFile.exists()) {
//			 testconfigFile.delete();
//		 }

//		 ConfigurationManager configurationManager = ConfigurationManagerInstanceHandler.createInstance(testconfigFile);

//		 assert configurationManager.getRaw("gson_test") == null;

//		 // GsonTestFile testFile = GsonTestFile.createDeafult();
//		 // configurationManager.put("gson_test", testFile);
//		 // configurationManager.syncConfigurations();
//		 // assert configurationManager.getRaw("gson_test") != null;
//		 // assert GsonTestFile.postProcessRan == false;
//		 // assert testFile.getExcludedString().equals("excluded");

//		 // ConfigurationManager checkConfigurationManager = ConfigurationManagerInstanceHandler.createInstance(testconfigFile);
//		 // GsonTestFile createdGsonFile = checkConfigurationManager.getRaw("gson_test");
//		 // assert createdGsonFile != null;
//		 // assert createdGsonFile.getExcludedString() == null;
//		 // assert GsonTestFile.postProcessRan == true;
//		 // assert createdGsonFile.getGsonObject().equals(testFile.getGsonObject());

//		 // assert createdGsonFile.getJsonClass().equals(GsonTestObject.class);
//		 // assert createdGsonFile.getGsonInteger() == 5;
//		 // assert createdGsonFile.getGsonChar() == 'c';
//		 // assert createdGsonFile.getGsonDouble() == 12.55454;
//		 // assert createdGsonFile.getGsonLong() == 5413154644544L;
//	 }
// }
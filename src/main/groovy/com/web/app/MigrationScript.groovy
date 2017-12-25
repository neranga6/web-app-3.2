package com.web.app

import groovy.io.FileType
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.sql.SQLException

/**
Setup

It is necessary to set up Windows properly so that you can
access Access databases from Java.  To do so,

Go to Control Panel -> Administrative Tools -> Data Source (ODBC) ->
System DSN -> Add -> Microsoft Access Driver (.mdb) -> Finish ->
(add name such as SOA-Access-DB, optional description) ->
in the Database area, click the Select button, navigate to where your
local copy of the DB is using the Windows 3-style file dialog ->
I set the username and password under Advanced, although I'm not sure
this is actually necessary.

Windows 7 Users!!
Currently Microsoft does not have any 64 bit ODBC drivers available for the Office products.
However, as long as the application that you want to connect to is 32 bit, you can use the 32 bit ODBC drivers to create the DSN. To get to the 32 bit ODBC drivers, the 32 bit Data Source administrator must be used. The 32 bit Data Source administrator must be launched directly if you are using a 64 bit OS. The file should be located at the following path:
c:\windows\sysWOW64\odbcad32.exe


Then, modify the "def sql = Sql.newInstance line to contain
jdbc:odbc:{what you named the database in the Control Panel}
as the first parameter, the username/password as 2nd/3rd params,
and the driver as the fourth.

Also, delete any existing target database ("del devDB*" at the
command line) before running the script, to avoid duplicates in
the database.  The script will not work properly with a pre-existing
target database.

Requirement: jtds-1.2.2.jar (or compatible version) is in %GROOVY_HOME%\lib
	This is available from the enterprise repo
Requirement: use STS or the Grails Console.  This will ensure that the classpath
is already populated with the project's classes.  It would be possible to set up
the environment to allow simply running "groovy ReleaseScript", but the script will
not do that for you.

Run script with this command inside the Grails console:
<code>MigrationScript.main()</code>

Note: When accessing Access directly, columns DO appear to be case-sensitive
SQL Server seems to be indifferent to capitalization

Specific steps for handling custom ids. (The only tables we need to preserve ids for is Module and LetterTemplate.)
	1. In DataSource.groovy set data source to 'create-drop' (this drops the table, alternatively you could delete local files if you are using H2)
	2. Comment the static mapping for id generator in Module.groovy and LetterTemplate.groovy (this ensures that the id columns get set to auto-increment)
	3. Run grails console (this starts grails and drops the tables....for some reason it does not re-create them)
	4. Kill the grails console
	5. Now set DataSource.groovy to 'create'
	6. Run grails console (this starts grails and creates the tables)
	7. Kill the grails console
	8. Uncomment the static mapping for id generator in Module.groovy and LetterTemplate (this allows us to manually assign the ids)
	9. Set DataSource.groovy to '' (We're doing this because we don't want to remove the auto-increment on the ids, but it still lets us assign them manually)
	10. Run grails console
	11. Run the migration script
	12. Upon successful completion of the migration script make sure you remove the id generator in Module and LetterTemplate
*/
class MigrationScript {
	
	private static Log log = LogFactory.getLog(this)
	//The flags append and addingPlaceHolders are mutually exclusive when initially adding dummy hold records 
	//followed by a load of data at some point in the future ....I know, I know, I could have done this with only
	//one flag.  However, in the interest of minimizing rework on something that will only be run a couple of times; I left it this way.
	//You'll probably set addingPlaceHolders to true on the first run and then at some future date when the business provides the append 
	//data, you'll flip the flags and run again.  If they decide to load the entire database at one time (no future appends) then run the script
	//with append false and addingPlaceHolders false. 
	
	//*NOTE* In regards to loading future claims records; the assumption is that business will provide a fully populated Access DB with two exceptions.  The letter table will only contain
	//the new letters they want added.  The module table will only contain the new modules they want added.  
	
	
	//If appending modules and letters set append to true and set the id start count
	public static final boolean append = false;
	// If adding dummy placeholders for Modules and Letter Templates set to true.
	public static final boolean addingPlaceHolders = false;
	
	public static final maxIdNumber = 7500
	
	
	//Use the following two closures to customize which numbers they want reserved.  If the number is available
	//it will create a dummy record.  If it's not available it will do nothing.  These two closures were
	//setup with the initial values Becky supplied.
	
	public static final Closure addModuleSeriesReserveNumbersToList = { moduleNumberList ->
	
		//add just 4219
		moduleNumberList.add(4219)
		
		//add numbers 1 - 4086
		def seriesOne = 4086

		while (seriesOne > 0){
			moduleNumberList.add(seriesOne)
			seriesOne--
		}
		
		//add numbers 6000 - 7500
		def seriesTwoStart = 5999
		def seriesTwoEnd = 7500
	
		while(seriesTwoEnd > seriesTwoStart){
			moduleNumberList.add(seriesTwoEnd)
			seriesTwoEnd--
		}
		
	}
	
	public static final Closure addReserveLetterTemplateNumbersToList = { letterNumberList ->
		
		//add numbers 1 - 1999
		def max = 1999

		while (max > 0){
			letterNumberList.add(max)
			max--
		}
		
	}
	
	
	public static final int startCount = 0;

	private static final Map sourceDatabaseSettings
	private static final Map oracleDatabaseSettings
	

	static {
		
		oracleDatabaseSettings = [url:"jdbc:oracle:thin:@shplordb0006.nwie.net:1522:edmt", driver: "oracle.jdbc.driver.OracleDriver", username: "WritXmen", password: "R3dSun1"]

		String odbcName = "OYS-Access-DB"
		sourceDatabaseSettings = [url:"jdbc:odbc:${odbcName}", driver: "sun.jdbc.odbc.JdbcOdbcDriver", username: null, password: null]
		
	}
	
	public static void main(String[] args) {
		Sql sourceSql = null
		Sql oracleSql = null
		try {
			sourceSql = Sql.newInstance(sourceDatabaseSettings.url, sourceDatabaseSettings.username, sourceDatabaseSettings.password, sourceDatabaseSettings.driver)
			oracleSql = Sql.newInstance(oracleDatabaseSettings.url, oracleDatabaseSettings.username, oracleDatabaseSettings.password, oracleDatabaseSettings.driver)
			migrateOYSData(sourceSql,oracleSql);
		} catch(Exception ex) {
			println "Exception while migrating OYS data"
			println ex
			log.error("Exception while migrating OYS data", ex)
		} finally {
			sourceSql?.close()
			println ("Closed SQL connection")
			log.debug("Closed SQL connection")
		}
		println "Migration Completed."
		log.debug("Migration Completed.")
	}
	
	public static void migrateOYSData(Sql sql, Sql oracleSql) {
				
		println "Entering migrateOYSData"
		log.debug("Entering migrateOYSData")
		
		if(!append){
			
			loadFixedTableData();
			
			def userRole = new Role(authority:"ROLE_USER").save(flush:true)
			def adminRole = new Role(authority:"ROLE_ADMIN").save(flush:true)
			def writerRole = new Role(authority:"ROLE_WRITER").save(flush:true)
			def reviewerRole = new Role(authority:"ROLE_REVIEWER").save(flush:true)
			
			println 'Roles were created'
			log.debug('Roles were created')
			
			migrateTableData(sql, "tblUsers") { userRow ->
				createUser(userRow, adminRole, writerRole, reviewerRole)
			}
			
		}
		
												
		User defaultUser = User.findByUsername("WARNERA3")
		println ("Default user for lastModBy field is ${defaultUser}")
		log.debug("Default user for lastModBy field is ${defaultUser}")
		
		if(!append){
			
			migrateTableData(sql, "tblCategories") { categoryRow ->
				createCategory(categoryRow, defaultUser)
			}
			
			migrateTableData(sql, "tblGroups") { groupRow ->
				createGroup(groupRow, defaultUser)
			}
			
			migrateTableData(sql, "tblSection") { sectionRow ->
				createSection(sectionRow, defaultUser)
			}
			
			migrateTableData(sql, "tblTemplates") { templateRow ->
				createModel(templateRow)
			}
			
		}
		

		migrateTableData(sql, "tblModules") { moduleRow ->
			createModule(moduleRow, sql, defaultUser)
		}
		
		migrateTableData(sql, "tblLetters") { letterRow ->
			createLetterTemplate(letterRow, sql, defaultUser)
		}
		
		resetOracleSequenceNumber(oracleSql)
		

	}
	
	/**
	* Execute "SELECT" SQL query against a particular database table, passing each row into a closure.
	*/
   private static void migrateTableData(Sql sql, String sourceTable, Closure migrationClosure) {
	 
	   int successes = 0
	   int failures = 0
	   String sqlQuery
	   if(sourceTable == "tblModules"){
		   sqlQuery = "select * from ${sourceTable} where pkModuleID >= ${startCount}"
	   }else if (sourceTable == "tblLetters"){
	   	   sqlQuery = "select * from ${sourceTable} where pkRefID >= ${startCount}"
	   }else{
	   		sqlQuery = "select * from ${sourceTable}"
	   }
	   
	   List<GroovyRowResult> list = sql.rows(sqlQuery)
	   
	   println "Migrating ${list.size()} rows from ${sourceTable}"
	   log.debug("Migrating ${list.size()} rows from ${sourceTable}")
	   
	   list.each { row ->
		   migrationClosure.call(row) ? successes++ : failures++
	   }
	   
	   println("Finished migrating ${sourceTable} data")
	   log.debug("Finished migrating ${sourceTable} data")
	   println("${sourceTable} data Successes: ${successes} and Failures: ${failures}")
	   log.debug("${sourceTable} data Successes: ${successes} and Failures: ${failures}")
		
	   if(!append && addingPlaceHolders){
		   //reserve IDs for Claims data migration
		   if(sourceTable == "tblModules"){
			   createReserveModuleRecords()
			   
		   }
		   
		   //reserve IDs for Claims data migration
		   if(sourceTable == "tblLetters"){
			   createReserveLetterTemplateRecords()
			   
		   }
	   }
		
		   
	   //These last steps validate that the ids were copied correctly for modules and letters
	   
	   if(sourceTable == "tblModules"){
		   println("Starting verification of WRITERS_MODULE table migration....")
		   log.debug("Starting verification of WRITERS_MODULE table migration....")
		   println("Verifying migration of ${list.size()} modules from Access....")
		   log.debug("Verifying migration of ${list.size()} modules from Access....")
		   def notFoundCount = 0
		   def foundCount = 0
			 list.each {
				def foundModule = Module.get(it.pkModuleID)
				 if(!foundModule){
					 notFoundCount++
					 println "No module found in Oracle for module id ${it.pkModuleID}"
					 log.debug("No module found in Oracle for module id ${it.pkModuleID}")
				 }else{
					 foundCount++
				 }
			 }
			println("Verification of WRITERS_MODULE table migration complete....")
			log.debug("Verification of WRITERS_MODULE table migration complete....")
			println("${notFoundCount} module records were not found in Oracle")
			log.debug("${notFoundCount} module records were not found in Oracle")
			println("${foundCount} module records were found in Oracle")
			log.debug("${foundCount} module records were found in Oracle")
	   }
	   
	   if(sourceTable == "tblLetters"){
		   println("Starting verification of LETTER_TEMPLATE table migration....")
		   log.debug("Starting verification of LETTER_TEMPLATE table migration....")
		   println("Verifying migration of ${list.size()} modules from Access....")
		   log.debug("Verifying migration of ${list.size()} modules from Access....")
		   def notFoundCount = 0
		   def foundCount = 0
			 list.each {
				def foundTemplate = LetterTemplate.get(it.pkRefID)
				 if(!foundTemplate){
					 notFoundCount++
					 println "No template found in Oracle for template id ${it.pkRefID}"
					 log.debug("No template found in Oracle for template id ${it.pkRefID}")
				 }else{
					 foundCount++
				 }
			 }
			println("Verification of LETTER_TEMPLATE table migration complete....")
			log.debug("Verification of LETTER_TEMPLATE table migration complete....")
			println("${notFoundCount} template records were not found in Oracle")
			log.debug("${notFoundCount} template records were not found in Oracle")
			println("${foundCount} template records were found in Oracle")
			log.debug("${foundCount} template records were found in Oracle")
	   }
	      
	  
	   
   }
   
    private static createReserveModuleRecords(){
		
		List reserveModuleList = new ArrayList()
		addModuleSeriesReserveNumbersToList(reserveModuleList)
		
		User defaultUser = User.findByUsername("WARNERA3")
		Section defaultSection = Section.findBySectionName("Reserve claims")
		
		if(!defaultUser){
			println "Default User not found"
			log.debug( "Default User not found")
		}
		
		if(!defaultSection){
			println "Default Section not found"
			log.debug("Default Section not found")
		}
		
		println("Attempting to create ${reserveModuleList.size()} reserve modules for Claims")
		log.debug("Attempting to create ${reserveModuleList.size()} reserve modules for Claims")
		def numberOfReserveModulesCreated = 0
		
		reserveModuleList.each { idNumber ->
			
			def foundModule = Module.get(idNumber)
			if(!foundModule ){
				try{
					def newModule = new Module(content: 'Claims default Module content', status : 'Draft', createdBy : defaultUser, lastModBy : defaultUser, section : defaultSection)
					if(newModule){
						def doubleIdNumber = idNumber.toDouble()
						newModule.id = doubleIdNumber
					}		
					
					if(newModule.save(flush:true)){
						numberOfReserveModulesCreated++
					}else{
						println "Error attempting to create reserve module ${idNumber}"
						log.debug("Error attempting to create reserve module ${idNumber}")
						println newModule.errors
						newModule.errors.each{
							log.debug(it)
						}
						
					}
				} catch (SQLException e) {
					println("SQL exception while creating new Module")
					println e
					log.error("SQL exception while creating new Module", e)
				}
			}else {
				println "Unable to create reserve module ${idNumber} as Module with that Id already exists."
				log.debug("Unable to create reserve module ${idNumber} as Module with that Id already exists.")
			}
			
		}
		
		println "Exiting createReserverModuleRecords...."
		log.debug("Exiting createReserverModuleRecords....")
		println "${numberOfReserveModulesCreated} Claims reserve Module records created."
		log.debug("${numberOfReserveModulesCreated} Claims reserve Module records created.")
	}
	
	private static createReserveLetterTemplateRecords(){
		
		List reserveLetterList = new ArrayList()
		addReserveLetterTemplateNumbersToList(reserveLetterList)
		
		User defaultUser = User.findByUsername("WARNERA3")
		Category defaultCategory = Category.findByName("Reserve claims")
		Model defaultModel = Model.findByTemplateStyle("Left Bar")
		
		if(!defaultUser){
			println "Default User not found"
			log.debug("Default User not found")
		}
		
		if(!defaultCategory){
			println "Default Category not found"
			log.debug("Default Category not found")
		}
		
		println("Attempting to create ${reserveLetterList.size()} reserve Templates for Claims")
		log.debug("Attempting to create ${reserveLetterList.size()} reserve Templates for Claims")
		def numberOfReserveTemplatesCreated = 0
		
		reserveLetterList.each { idNumber ->
			
			def foundTemplate = LetterTemplate.get(idNumber)
			if(!foundTemplate ){
				try{
					def newTemplate = new LetterTemplate(name: 'Claims default Letter Name', category : defaultCategory, status : "Draft", model : defaultModel,  createdBy : defaultUser, lastModBy : defaultUser)
					if(newTemplate){
						def doubleIdNumber = idNumber.toDouble()
						newTemplate.id = doubleIdNumber
					}
					
					if(newTemplate.save(flush:true)){
						numberOfReserveTemplatesCreated++
					}else{
						println "Error attempting to create reserve Template ${idNumber}"
						log.debug("Error attempting to create reserve Template ${idNumber}")
						println newTemplate.errors
						newTemplate.errors.each {
							log.debug(it)
						}
					}
				} catch (SQLException e) {
					println("SQL exception while creating new Template")
					println e
					log.error("SQL exception while creating new Template", ex)
				}
			}else {
				println "Unable to create reserve Template ${idNumber} as a Template with that Id already exists."
				log.debug("Unable to create reserve Template ${idNumber} as a Template with that Id already exists.")
			}
			
		}
		
		println "Exiting createReserveLetterTemplateRecords...."
		log.debug("Exiting createReserveLetterTemplateRecords....")
		println "${numberOfReserveTemplatesCreated} Claims reserve Template records created."
		log.debug("${numberOfReserveTemplatesCreated} Claims reserve Template records created.")
		
	}
      
	private static loadFixedTableData(){

		println 'Loading images from C:\\Projects\\WritersTool\\Images....'
		log.debug('Loading images from C:\\Projects\\WritersTool\\Images....')
		def imageFileList = []
		
		def dir = new File("C:\\Projects\\WritersTool\\Images")
		dir.eachFileRecurse(FileType.FILES){file ->
			imageFileList << file
		}
		
	
		imageFileList.each {
		
			def tokenizeList = it.path.tokenize('\\')
			def fullFileName
			def fileExtension
			tokenizeList.each {
				if(it.contains(".")){
					fullFileName = it
				}	
			}
			def image = new Image(originalFilename : fullFileName, thumbnailFilename : fullFileName, newFilename : fullFileName, imageBytes : it.getBytes(), fileSize : it.size())
		
			if(image){
				if(image.validate()){
					image.save(flush:true)
				} else {
					println image.errors
					image.errors.each {
						log.debug(it)
					}
					println("Error in validation for image: ${fullFileName}")
					log.debug("Error in validation for image: ${fullFileName}")
				}
			}
		
		}
		
		println 'Done loading images....'
		log.debug('Done loading images....')
	}
		 
	private static boolean createUser(GroovyRowResult row, Role adminRole, Role writerRole, Role reviewerRole){
		boolean success = false
		def username
		if(row.UserName){
			username = row.UserName.toUpperCase()
		}
		
		try {
			User user = new User(
				username: username,
				firstName: row.FirstName,
				lastName: row.LastName,
				password: row.Password,
				enabled: true
			)
			if(row.Status == 'D'){
			
				user.enabled = false
			}
			
					
			if (user.validate()) {
				success = user.save(flush:true)
				
				if(row.Status == 'A'){

						UserRole.create(user,adminRole)
					}
				if(row.Status == 'W'){

						UserRole.create(user,writerRole)
					}
				if(row.Status == 'R'){

						UserRole.create(user,reviewerRole)
					}
			} else {
				println user.errors
				user.errors.each {  
					log.debug(it)
				}
				println("Error in validation for user: ${row.UserName}")
				log.debug("Error in validation for user: ${row.UserName}")
			}
		} catch (SQLException e) {
			println("SQL exception while creating user")
			println e
			log.error("SQL exception while creating user", e)
		}
		return success
	}
	
	private static boolean createCategory(GroovyRowResult categoryRow, User defaultUser){
		
		boolean success = false
	
		try{
			Category category = new Category(name : categoryRow.Category, lastModBy:defaultUser)
			
			if (category.validate()) {
				success = category.save(flush:true)
			} else {
				println category.errors
				category.errors.each {  
					log.debug(it)
				}
				println("Error in validation for category: ${categoryRow.Category}")
				log.debug("Error in validation for category: ${categoryRow.Category}")
			}
			
		} catch (SQLException e) {
			println("SQL exception while creating new Category")
			println e
			log.error("SQL exception while creating new Category", e)
		}
		return success
		
		
	}
	
	private static boolean createGroup(GroovyRowResult groupRow, User defaultUser){
		
		boolean success = false
			
		try{
			SectionGroup group = new SectionGroup(groupName : groupRow.GroupName, sequence : groupRow.Sequence, lastModBy : defaultUser )
			group.id = groupRow.ID
			if (group.validate()) {
				success = group.save(flush:true)
			} else {
				println group.errors
				group.errors.each {  
					log.debug(it)
				}
				println("Error in validation for SectionGroup: ${groupRow.GroupName}")
				log.debug("Error in validation for SectionGroup: ${groupRow.GroupName}")
			}
			
		} catch (SQLException e) {
			println("SQL exception while creating new Group")
			println e
			log.error("SQL exception while creating new Group", e)
		}
		return success
	}
	
	private static boolean createSection(GroovyRowResult sectionRow, User defaultUser){

		boolean success = false
		SectionGroup group
		
		if(sectionRow.GroupID){
			group = SectionGroup.get(sectionRow.GroupID)
		}
		
		if(group){
			try{
				Section section = new Section(sectionName : sectionRow.Section, group : group, lastModBy : defaultUser )
					
				if (section.validate()) {
					success = section.save(flush:true)
				} else {
					println section.errors
					section.errors.each {
						log.debug(it)
					}
					println("Error in validation for Section: ${sectionRow.Section}")
					log.debug("Error in validation for Section: ${sectionRow.Section}")
				}
				
				
			} catch (SQLException e) {
				println("SQL exception while creating new Group")
				println e
				log.error("SQL exception while creating new Group", e)
			}
		}else{
			println("No group found for group sequence ${sectionRow.GroupID}")
			log.debug("No group found for group sequence ${sectionRow.GroupID}")
		}
		
		return success
		
	}
	
	private static boolean createModel(GroovyRowResult templateRow){
		boolean success =  false
		try{
			Model model = new Model(templateStyle: templateRow.templatename)
			
			if(model.validate()){
				success = model.save(flush:true)
			}else {
				println model.errors
				model.errors.each {
					log.debug(it)
				}
				println("Error in validation of model: ${templateRow.templatename}")
				log.debug("Error in validation of model: ${templateRow.templatename}")
			}
			
		} catch (SQLException e) {
			println("SQL exception while creating new Model")
			println e
			log.error("SQL exception while creating new Model", e)
		}
		return success
	}
		
	private static boolean createModule(GroovyRowResult moduleRow, Sql sql, User defaultUser){
		
		boolean success = false
		String sectionQuery = "select Section from tblSection where ID = ${moduleRow.SectionId}"
		def row = sql.firstRow(sectionQuery)
		
		def section
		if(row){
			section = Section.findBySectionName(row.Section)
		}
		
		def status = "Draft"
		
		if(moduleRow.StatusId == 1){
			status = "Draft"
		}
		if(moduleRow.StatusId == 2){
			status == "Review"
		}
		if(moduleRow.StatusId == 3){
			status == "Final"
		}
		
		def foundCreatedBy
		def foundLastModBy
		
		if(moduleRow.CreatedBy){
			foundCreatedBy = User.findByUsername(moduleRow.CreatedBy)
		}
		if(moduleRow.LastModBy){
			foundLastModBy = User.findByUsername(moduleRow.LastModBy)
		}
		
		def createdBy = foundCreatedBy ?: defaultUser
		def lastModBy = foundLastModBy ?: defaultUser
				
		def module
		def contentWithoutH5
		if(section && moduleRow.Content){
			
			def imgTagList = moduleRow.Content.findAll(/<img[^>]+src="([^"]+)"[^>]+>/)
			def newContent
			if(imgTagList){
				def imgTagListSize = imgTagList.size()
				println "Modifying ${imgTagListSize} img tags for module : ${moduleRow.pkModuleID}"
				log.debug("Modifying ${imgTagListSize} img tags for module : ${moduleRow.pkModuleID}")
				newContent = changeImageSrcAttributes(moduleRow.Content, imgTagList)			
			}else{
				newContent = moduleRow.Content
			}			
			contentWithoutH5 = replaceH5WithRealPageBreak(newContent)
		
		}
		
		if(append){
			module = Module.get(moduleRow.pkModuleID)
			if(module){
				module.content = contentWithoutH5
				module.status = status
				module.section = section 
				module.createdBy = createdBy
				module.lastModBy = lastModBy
			}else{
				println "Unable to append new module with ID ${moduleRow.pkModuleID} because matching placeholder does note exist in Oracle."
				log.debug("Unable to append new module with ID ${moduleRow.pkModuleID} because matching placeholder does note exist in Oracle.")
			}
		}else{
			module = new Module(content : contentWithoutH5, status : status, section : section, createdBy : createdBy, lastModBy : lastModBy)
		}
		
		
// need to ask if we care about Date, Grails is handling this on the save() which do not be helping us
		try{
			if(module.validate()){
				if(!append){
					module.id = moduleRow.pkModuleID
				}
				
				String commentQuery = "select * from tblModuleComments where ID = ${moduleRow.pkModuleID}"
				String businessRuleQuery = "select * from tblModuleBusinessRules where ID = ${moduleRow.pkModuleID}"
				
				List<GroovyRowResult> commentList = sql.rows(commentQuery)
				List<GroovyRowResult> businessRuleList = sql.rows(businessRuleQuery)
					
				def moduleCommentListSize = commentList.size()
				def moduleCommentCount = 0
											
				commentList.each { commentRow ->
					
					def foundCommentLastModBy
					if(commentRow.LastModBy){
						foundCommentLastModBy = User.findByUsername(commentRow.LastModBy)
					}
					def commentLastModBy = foundCommentLastModBy ?: defaultUser
				
					if(commentRow.CommentText){
						module.addToComments(new Comment(comment : commentRow.CommentText, lastModBy : commentLastModBy, module : module))
						moduleCommentCount++
					}
					
				}
				
				if(moduleCommentCount != moduleCommentListSize){
					println "Unable to migrate all module comments attached to module ${moduleRow.pkModuleID}"
					log.debug("Unable to migrate all module comments attached to module ${moduleRow.pkModuleID}")
				}
				
				def businessRuleListSize = businessRuleList.size()
				def businessRuleCount = 0
				
				businessRuleList.each { businessRuleRow ->
					
					def foundBusinessRuleLastModBy
					if(businessRuleRow.LastModBy){
						foundBusinessRuleLastModBy = User.findByUsername(businessRuleRow.LastModBy)
					}
					def businessRuleLastModBy = foundBusinessRuleLastModBy ?: defaultUser
					
					if(businessRuleRow.BusinessRuleText){
						module.addToBusinessRules(new BusinessRule(rule : businessRuleRow.BusinessRuleText, lastModBy : businessRuleLastModBy, module : module))
						businessRuleCount++
					}
					
				}
				
				if(businessRuleCount != businessRuleListSize){
					println "Unable to migrate all module business rules attached to module ${moduleRow.pkModuleID}"
					log.debug("Unable to migrate all module business rules attached to module ${moduleRow.pkModuleID}")
				}
				
							
				if(module.validate()){
//					if(moduleRow.LastModDt){
//						module.lastUpdated = moduleRow.LastModDt
//					}
					success = module.save(flush:true)
//					if(success && moduleRow.CreatedDt){
//						module.dateCreated = moduleRow.CreatedDt
//						success = module.save()
//					}
					
				}else {
					println("Error in validation of module: ${moduleRow.pkModuleID}")
					log.debug("Error in validation of module: ${moduleRow.pkModuleID}")
					module.errors.each {
						log.debug(it)
					}
					println module.errors
				}
			}else{
				println "Unable to create a module object from module: ${moduleRow.pkModuleID}"
				log.debug("Unable to create a module object from module: ${moduleRow.pkModuleID}")
				println module.errors
				module.errors.each {
					log.debug(it)
				}
			}
			
		} catch (SQLException e) {
			println("SQL exception while creating new Module")
			println e
			log.error("SQL exception while creating new Module", e)
		}
		return success
		
	}
		
	
	private static boolean createLetterTemplate(GroovyRowResult letterRow, Sql sql, User defaultUser){
				
		boolean success = false
		def categoryQuery = "select Category from tblCategories where CategoryID = ${letterRow.CategoryId}"
		def modelQuery = "select tblTemplateID from tblLetterToTemplate where tblLetterID = ${letterRow.pkRefID}"
	
			
		def categoryRow = sql.firstRow(categoryQuery)
		def modelRow = sql.firstRow(modelQuery)
		
		def category
		if(categoryRow){
			category = Category.findByName(categoryRow.Category)
		}
		
		def modelName = ""
		if(modelRow){
			if(modelRow.tblTemplateID == 1){
				modelName = "Left Bar"
			}
			if(modelRow.tblTemplateID == 2){
				modelName = "Centered"
			}
			if(modelRow.tblTemplateID == 3){
				modelName = "Coupon/Tearoff"
			}
		}
		
		
		def model
		if(modelName){
			model = Model.findByTemplateStyle(modelName)
		}
		
		def status = "Draft"
		
		if(letterRow.StatusId == 1){
			status = "Draft"
		}
		if(letterRow.StatusId == 2){
			status == "Review"
		}
		if(letterRow.StatusId == 3){
			status == "Final"
		}
		
		def foundCreatedBy
		def foundLastModBy
		
		if(letterRow.CreatedBy){
			foundCreatedBy = User.findByUsername(letterRow.CreatedBy)
		}
		if(letterRow.LastModBy){
			foundLastModBy = User.findByUsername(letterRow.LastModBy)
		}
		
		def createdBy = foundCreatedBy ?: defaultUser
		def lastModBy = foundLastModBy ?: defaultUser
				
		def description = ""
		if(letterRow['Overall Comments'] && letterRow['Overall Comments'] != '.'){
			description = letterRow['Overall Comments']
		}
			
		try{
			def letter
			if(append){
				letter = LetterTemplate.get(letterRow.pkRefID)
				if(letter){
					letter.name = letterRow.Title
					letter.description = description
					letter.lastModBy = lastModBy
					letter.category = category
					letter.status = status
					letter.model = model 
					letter.createdBy = createdBy
				}else{
					println "Unable to append new template with ID ${letterRow.pkRefID} because matching placeholder does note exist in Oracle."
					log.debug("Unable to append new template with ID ${letterRow.pkRefID} because matching placeholder does note exist in Oracle.")
				}
			}else{
				letter = new LetterTemplate(name : letterRow.Title, description : description, lastModBy : lastModBy, category : category, status : status, model : model, createdBy : createdBy)
			}
			
			if(letter.validate()){
				if(!append){
					letter.id = letterRow.pkRefID
				}
				def letterCommentQuery = "select * from tblLetterComments where LetterID = ${letterRow.pkRefID}"
				List<GroovyRowResult> letterCommentList = sql.rows(letterCommentQuery)
				
				def letterCommentListSize = letterCommentList.size()
				def letterCommentCount = 0
				letterCommentList.each { commentRow ->
					def foundCommentLastModBy
					if(commentRow.LastModBy){
						foundCommentLastModBy = User.findByUsername(commentRow.LastModBy)
					}
					def commentLastModBy = foundCommentLastModBy ?: defaultUser
					
					if(commentRow.CommentText && commentRow.CommentText != '.'){
						letter.addToComments(new LetterTemplateComment(comment : commentRow.CommentText, lastModBy : commentLastModBy, letterTemplate : letter))
						letterCommentCount++
					}
					
				}
				
				if(letterCommentCount != letterCommentListSize){
					println "Unable to migrate all letter comments attached to letter ${letterRow.pkRefID}"
					log.debug("Unable to migrate all letter comments attached to letter ${letterRow.pkRefID}")
				}
				
				def recipeQuery = "select * from tblLetterRecipeModule where LetterID = ${letterRow.pkRefID}"
				List<GroovyRowResult> recipeList = sql.rows(recipeQuery)
			
				def recipeListSize = recipeList.size()
				def ingredientCount = 0
				recipeList.each { recipeRow ->
					
					def module = Module.get(recipeRow.ModuleID)
					if(module){
						def ingredient = new Ingredient(module : module, letter : letter, sequence : recipeRow.Sortorder, lastModBy : defaultUser)
						if(ingredient){
							module.addToIngredients(ingredient)
							ingredientCount++
						}else{
							println "Unable to add indgredient for LetterID ${letter.id} and ModuleID ${module.id}"
							log.debug("Unable to add indgredient for LetterID ${letter.id} and ModuleID ${module.id}")
						}
					}
									
				}
						
				
				if(letter.validate()){
					success = letter.save(flush:true)
				}else {
					println("Error in validation of LetterTemplate: ${letterRow.pkRefID}")
					log.debug("Error in validation of LetterTemplate: ${letterRow.pkRefID}")
					println letter.errors
					letter.errors.each {
						log.debug(it)
					}
				}
			}else{
				println "Unable to create a Template object from template: ${letterRow.pkRefID}"
				log.debug("Unable to create a Template object from template: ${letterRow.pkRefID}")
				println letter.errors
				letter.errors.each {
					log.debug(it)
				}
			}
			
			
		} catch (SQLException e) {
			println("SQL exception while creating new LetterTemplate")
			println e
			log.error("SQL exception while creating new LetterTemplate", e)
		}
		return success
				
			
		}
		
		private static String changeImageSrcAttributes(String origContent, List imgTagList){
			
			def newContent = origContent
			
			imgTagList.each {
				
				def srcAttribute = it.find(/src=["|\']([^"|\']+)/)
				println "Found source attribute = ${srcAttribute}"
				log.debug("Found source attribute = ${srcAttribute}")
				def parsedSrcAttributeList = srcAttribute.tokenize('/')
				
				parsedSrcAttributeList.each {
					
					if(it =~ /.bmp/ || it =~ /.png/ || it =~ /.gif/ || it =~ /.jpg/){
						println "Found image file name = ${it}"
						log.debug("Found image file name = ${it}")
						def foundImage = Image.findByOriginalFilename(it)
						if(foundImage){
							def newSrcAttribute = 'src="../../image/picture/' + foundImage.id + '"'
							def replacedSrcAttribute = srcAttribute + '"'
							println "Replacing ${replacedSrcAttribute} with ${newSrcAttribute}"
							log.debug("Replacing ${replacedSrcAttribute} with ${newSrcAttribute}")
							newContent = origContent.replace(replacedSrcAttribute, newSrcAttribute)
						}else{
							println "No image found for attribute ${srcAttribute}"
							log.debug("No image found for attribute ${srcAttribute}")
						}
						
					}
					
				}
				
			}
			
			if(!newContent){
				newContent = origContent
			}
			return newContent
		}
		
		private static String replaceH5WithRealPageBreak(String content){
			
			def newContent
			
			newContent = content.replaceAll("<h5>&nbsp;</h5>", '<div style="page-break-before: always;"></div>')
			
			return newContent
		}
		
		private static resetOracleSequenceNumber(oracleSql) {
			
			println "Resetting Oracle Sequence Number ...."
			log.debug("Resetting Oracle Sequence Number ....")
			try{
				
				//Step 1 - Find the next value
				def findTheNextValToAssign = "SELECT hibernate_sequence.nextval FROM dual"
				def searchResult = oracleSql.firstRow(findTheNextValToAssign)
				def nextValToAssign = searchResult.NEXTVAL
				println "Next ID value that Oracle thinks it should assign is ${nextValToAssign}"
				log.debug("Next ID value that Oracle thinks it should assign is ${nextValToAssign}")
//				def bumpAmount = maxIdNumber - nextValToAssign
//				println "Difference between max ID we're adding and Oracle next val = ${bumpAmount}"
//				log.debug("Difference between max ID we're adding and Oracle next val = ${bumpAmount}")
//				
//				println "Bumping the sequence by ${bumpAmount}"
//				log.debug("Bumping the sequence by ${bumpAmount}")
//				
//				//Step 2 - Bump the sequence used by Hibernate by some large value (something to avoid collisions with existing IDs - might need to do some research here)
//				def largeBump = "ALTER sequence hibernate_sequence INCREMENT BY " + bumpAmount
//				oracleSql.execute(largeBump)
//							
//				//Step 3 - Force the sequence to generate the next number (will increase by the increment value)
//				def forceToGenerateNextNumber = "SELECT hibernate_sequence.nextval FROM dual"
//				def nextIDNumber = oracleSql.firstRow(forceToGenerateNextNumber)
//				println "Resetting Oracle Sequence Number...next ID number is ${nextIDNumber}"
//				log.debug("Resetting Oracle Sequence Number...next ID number is ${nextIDNumber}")
//			
//				//Step 4 - Change the sequence back to increment by 1
//				def incrementByOne = "ALTER sequence hibernate_sequence INCREMENT BY 1"
//				oracleSql.execute(incrementByOne)
			
			} catch (SQLException e) {
				println("SQL exception while executing resetOracleSequenceNumber")
				println e
				log.error("SQL exception while executing resetOracleSequenceNumber", e)
			}
		
//			println "Oracle Sequence Number has been reset."
//			log.debug( "Oracle Sequence Number has been reset.")
		}
	
}

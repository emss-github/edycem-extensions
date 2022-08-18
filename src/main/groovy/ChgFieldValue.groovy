/**
 * README
 * This extension is an API transaction from EXT600MI
 * 
 * Name: ChgFieldValue
 * Description: Changes a record of the table EXTCGE
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction ChgFieldValue
 */
public class ChgFieldValue extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility
  
  public ChgFieldValue(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
    this.mi = mi
    this.database = database
    this.program = program
    this.utility = utility
  }
  
  int cono
  String chid
  int chno
  
  public void main() {
    cono = (Integer) program.getLDAZD().CONO
    chid = program.getUser()
  
    // Select fields to handle from table EXTCGE
    DBAction query = database.table("EXTCGE")
      .index("00")
    .selection("EXCONO", "EXFILE", "EXPK01", "EXPK02", "EXPK03", "EXPK04", "EXPK05", "EXPK06", "EXPK07", "EXPK08", 
    "EXA030", "EXA130", "EXA230", "EXA330", "EXA430", "EXA530", "EXA630", "EXA730", "EXA830", "EXA930",  
    "EXN096", "EXN196", "EXN296", "EXN396", "EXN496", "EXN596", "EXN696", "EXN796", "EXN896", "EXN996", 
    "EXA256", "EXA121", "EXA122", "EXLMTS", "EXRGDT", "EXRGTM", "EXLMDT", "EXCHNO", "EXCHID", 
    "EXCHB1", "EXCHB2", "EXCHB3", "EXCHB4", "EXDAT1", "EXDAT2", "EXDAT3", "EXDAT4", "EXDAT5", "EXDAT6", "EXDAT7")
      .build()

    DBContainer container = query.getContainer()
    // Set the key fields
    container.set("EXCONO", cono)
    container.set("EXFILE", mi.inData.get("FILE").trim())
    container.set("EXPK01", mi.inData.get("PK01").trim())
    container.set("EXPK02", mi.inData.get("PK02").trim())
    container.set("EXPK03", mi.inData.get("PK03").trim())
    container.set("EXPK04", mi.inData.get("PK04").trim())
    container.set("EXPK05", mi.inData.get("PK05").trim())
    container.set("EXPK06", mi.inData.get("PK06").trim())
    container.set("EXPK07", mi.inData.get("PK07").trim())
    container.set("EXPK08", mi.inData.get("PK08").trim())
    
    Closure<?> updateCallBack = { LockedResult lockedResult ->
      checkEmptyField("EXA030", mi.inData.get("A030"), lockedResult)
      checkEmptyField("EXA130", mi.inData.get("A130"), lockedResult)
      checkEmptyField("EXA230", mi.inData.get("A230"), lockedResult)
      checkEmptyField("EXA330", mi.inData.get("A330"), lockedResult)
      checkEmptyField("EXA430", mi.inData.get("A430"), lockedResult)
      checkEmptyField("EXA530", mi.inData.get("A530"), lockedResult)
      checkEmptyField("EXA630", mi.inData.get("A630"), lockedResult)
      checkEmptyField("EXA730", mi.inData.get("A730"), lockedResult)
      checkEmptyField("EXA830", mi.inData.get("A830"), lockedResult)
      checkEmptyField("EXA930", mi.inData.get("A930"), lockedResult)
      checkEmptyField("EXA256", mi.inData.get("A256"), lockedResult)
      checkEmptyField("EXA121", mi.inData.get("A121"), lockedResult)
      checkEmptyField("EXA122", mi.inData.get("A122"), lockedResult)
  
      checkEmptyField("EXN096", mi.inData.get("N096"), lockedResult)
      checkEmptyField("EXN196", mi.inData.get("N196"), lockedResult)
      checkEmptyField("EXN296", mi.inData.get("N296"), lockedResult)
      checkEmptyField("EXN396", mi.inData.get("N396"), lockedResult)
      checkEmptyField("EXN496", mi.inData.get("N496"), lockedResult)
      checkEmptyField("EXN596", mi.inData.get("N596"), lockedResult)
      checkEmptyField("EXN696", mi.inData.get("N696"), lockedResult)
      checkEmptyField("EXN796", mi.inData.get("N796"), lockedResult)
      checkEmptyField("EXN896", mi.inData.get("N896"), lockedResult)
      checkEmptyField("EXN996", mi.inData.get("N996"), lockedResult)
      
      checkEmptyField("EXCHB1", mi.inData.get("CHB1"), lockedResult)
      checkEmptyField("EXCHB2", mi.inData.get("CHB2"), lockedResult)
      checkEmptyField("EXCHB3", mi.inData.get("CHB3"), lockedResult)
      checkEmptyField("EXCHB4", mi.inData.get("CHB4"), lockedResult)
      
      checkEmptyField("EXDAT1", mi.inData.get("DAT1"), lockedResult)
      checkEmptyField("EXDAT2", mi.inData.get("DAT2"), lockedResult)
      checkEmptyField("EXDAT3", mi.inData.get("DAT3"), lockedResult)
      checkEmptyField("EXDAT4", mi.inData.get("DAT4"), lockedResult)
      checkEmptyField("EXDAT5", mi.inData.get("DAT5"), lockedResult)
      checkEmptyField("EXDAT6", mi.inData.get("DAT6"), lockedResult)
      checkEmptyField("EXDAT7", mi.inData.get("DAT7"), lockedResult)

      // The change number is retrieved from the existing record and is being added 1
      chno = lockedResult.get("EXCHNO")
      chno++;
      lockedResult.set("EXLMDT", utility.call("DateUtil","currentDateY8AsInt"))
      lockedResult.set("EXCHNO", chno)
      lockedResult.set("EXCHID", chid)
      lockedResult.update()
    }
    
    // If there is an existing record of this key, the fields are set and the record is updated in the table
    if (query.read(container)) {
      query.readAllLock(container, 10, updateCallBack)
    } else {
      // If the record doesn't already exist in the table, an error is thrown
      mi.error("L'enregistrement n'existe pas.")
    }
  }
  
  /**
   * Check if the input field is empty. If it is not empty, the value is set to the record
   * @param fieldToSet: the name of the field to set in the table 
   * @param fieldToCheck: the input field of the API to control
   * @param recordToModify: the record that will be changed in the table
   */
  String checkEmptyField(String fieldToSet, String fieldToCheck, LockedResult recordToModify) {
    String wField = fieldToCheck.trim()
    if (!wField.isEmpty()) {
      recordToModify.set(fieldToSet, wField)
    }
  }
}
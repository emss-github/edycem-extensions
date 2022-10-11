/**
 * README
 * This extension is an API transaction from EXT600MI
 * 
 * Name: DelFieldValue
 * Description: Deletes a record in the table EXTCGE
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction DelFieldValue
 */
public class DelFieldValue extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility
  
  public DelFieldValue(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
    this.mi = mi
    this.database = database
    this.program = program
    this.utility = utility
  }
  
  int cono
  
  public void main() {
    cono = (Integer) program.getLDAZD().CONO
    
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
    // Set the key fields of the record to delete
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
    
    Closure<?> deleterCallback = { LockedResult lockedResult ->
      lockedResult.delete()
    }
    // If there is an existing record of this key, it is deleted
    if (query.read(container)) {
      query.readLock(container, deleterCallback)
    } else {
    // If there is no existing record of this key, an error is thrown
      mi.error("L'enregistrement n'existe pas.")
    }
  }
}
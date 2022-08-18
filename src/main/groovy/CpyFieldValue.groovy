/**
 * README
 * This extension is an API transaction from EXT600MI
 * 
 * Name: CpyFieldValue
 * Description: Copies a record in the table EXTCGE
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction CpyFieldValue
 */
public class CpyFieldValue extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility
  
  public CpyFieldValue(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
    this.mi = mi
    this.database = database
    this.program = program
    this.utility = utility
  }
 
  int cono
  String chid
  
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
    // Set the key fields of the record to copy
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
    
    // If there is no existing record of this key, an error is thrown
    if (!query.read(container)) {
      mi.error("L'enregistrement à copier n'existe pas.")
    } else {
      // The record to copy is kept in a variable
      DBContainer recordToCopy = container
      
      // Set the key fields of the record to create
      container.set("EXCONO", cono)
      container.set("EXFILE", mi.inData.get("TFIL").trim())
      container.set("EXPK01", mi.inData.get("PKT1").trim())
      container.set("EXPK02", mi.inData.get("PKT2").trim())
      container.set("EXPK03", mi.inData.get("PKT3").trim())
      container.set("EXPK04", mi.inData.get("PKT4").trim())
      container.set("EXPK05", mi.inData.get("PKT5").trim())
      container.set("EXPK06", mi.inData.get("PKT6").trim())
      container.set("EXPK07", mi.inData.get("PKT7").trim())
      container.set("EXPK08", mi.inData.get("PKT8").trim())
        
      // If there is an existing record of this key, an error is thrown
      if (query.read(container)) {
        mi.error("L'enregistrement à créer existe déjà.")
      } else {
      // If the record doesn't already exist in the table, the fields are set and the record is created in the table
      container.set("EXA030", recordToCopy.get("EXA030"))
      container.set("EXA130", recordToCopy.get("EXA130"))
      container.set("EXA230", recordToCopy.get("EXA230"))
      container.set("EXA330", recordToCopy.get("EXA330"))
      container.set("EXA430", recordToCopy.get("EXA430"))
      container.set("EXA530", recordToCopy.get("EXA530"))
      container.set("EXA630", recordToCopy.get("EXA630"))
      container.set("EXA730", recordToCopy.get("EXA730"))
      container.set("EXA830", recordToCopy.get("EXA830"))
      container.set("EXA930", recordToCopy.get("EXA930"))
    
      container.set("EXN096", recordToCopy.get("EXN096"))
      container.set("EXN196", recordToCopy.get("EXN196"))
      container.set("EXN296", recordToCopy.get("EXN296"))
      container.set("EXN396", recordToCopy.get("EXN396"))
      container.set("EXN496", recordToCopy.get("EXN496"))
      container.set("EXN596", recordToCopy.get("EXN596"))
      container.set("EXN696", recordToCopy.get("EXN696"))
      container.set("EXN796", recordToCopy.get("EXN796"))
      container.set("EXN896", recordToCopy.get("EXN896"))
      container.set("EXN996", recordToCopy.get("EXN996"))
    
      container.set("EXA256", recordToCopy.get("EXA256"))
      container.set("EXA121", recordToCopy.get("EXA121"))
      container.set("EXA122", recordToCopy.get("EXA122"))
    
      container.set("EXCHB1", recordToCopy.get("EXCHB1"))
      container.set("EXCHB2", recordToCopy.get("EXCHB2"))
      container.set("EXCHB3", recordToCopy.get("EXCHB3"))
      container.set("EXCHB4", recordToCopy.get("EXCHB4"))
    
      container.set("EXDAT1", recordToCopy.get("EXDAT1"))
      container.set("EXDAT2", recordToCopy.get("EXDAT2"))
      container.set("EXDAT3", recordToCopy.get("EXDAT3"))
      container.set("EXDAT4", recordToCopy.get("EXDAT4"))
      container.set("EXDAT5", recordToCopy.get("EXDAT5"))
      container.set("EXDAT6", recordToCopy.get("EXDAT6"))
      container.set("EXDAT7", recordToCopy.get("EXDAT7"))
    
      container.set("EXLMTS", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EXRGDT", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EXRGTM", utility.call("DateUtil","currentTimeAsInt"))
      container.set("EXLMDT", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EXCHNO", 1)
      container.set("EXCHID", chid)
    
      query.insert(container)
      }
    }
  }
}
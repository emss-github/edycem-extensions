/**
 * README
 * This extension is an API transaction from EXT600MI
 * 
 * Name: GetFieldValue
 * Description: Gets a record from the table EXTCGE
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction GetFieldValue
 */
public class GetFieldValue extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility
  
  public GetFieldValue(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
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
    // Set the key fields of the record to get
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
    
    // If there is an existing record of this key, the outData is set
    if (query.read(container)) {
      mi.outData.put("FILE", String.valueOf(container.get("EXFILE")))
      mi.outData.put("PK01", String.valueOf(container.get("EXPK01")))
      mi.outData.put("PK02", String.valueOf(container.get("EXPK02")))
      mi.outData.put("PK03", String.valueOf(container.get("EXPK03")))
      mi.outData.put("PK04", String.valueOf(container.get("EXPK04")))
      mi.outData.put("PK05", String.valueOf(container.get("EXPK05")))
      mi.outData.put("PK06", String.valueOf(container.get("EXPK06")))
      mi.outData.put("PK07", String.valueOf(container.get("EXPK07")))
      mi.outData.put("PK08", String.valueOf(container.get("EXPK08")))
      
      mi.outData.put("A030", String.valueOf(container.get("EXA030")))
      mi.outData.put("A130", String.valueOf(container.get("EXA130")))
      mi.outData.put("A230", String.valueOf(container.get("EXA230")))
      mi.outData.put("A330", String.valueOf(container.get("EXA330")))
      mi.outData.put("A430", String.valueOf(container.get("EXA430")))
      mi.outData.put("A530", String.valueOf(container.get("EXA530")))
      mi.outData.put("A630", String.valueOf(container.get("EXA630")))
      mi.outData.put("A730", String.valueOf(container.get("EXA730")))
      mi.outData.put("A830", String.valueOf(container.get("EXA830")))
      mi.outData.put("A930", String.valueOf(container.get("EXA930")))
      
      mi.outData.put("N096", String.valueOf(container.get("EXN096")))
      mi.outData.put("N196", String.valueOf(container.get("EXN196")))
      mi.outData.put("N296", String.valueOf(container.get("EXN296")))
      mi.outData.put("N396", String.valueOf(container.get("EXN396")))
      mi.outData.put("N496", String.valueOf(container.get("EXN496")))
      mi.outData.put("N596", String.valueOf(container.get("EXN596")))
      mi.outData.put("N696", String.valueOf(container.get("EXN696")))
      mi.outData.put("N796", String.valueOf(container.get("EXN796")))
      mi.outData.put("N896", String.valueOf(container.get("EXN896")))
      mi.outData.put("N996", String.valueOf(container.get("EXN996")))
      
      mi.outData.put("A256", String.valueOf(container.get("EXA256")))
      mi.outData.put("A121", String.valueOf(container.get("EXA121")))
      mi.outData.put("A122", String.valueOf(container.get("EXA122")))
    
      mi.outData.put("CHB1", String.valueOf(container.get("EXCHB1")))
      mi.outData.put("CHB2", String.valueOf(container.get("EXCHB2")))
      mi.outData.put("CHB3", String.valueOf(container.get("EXCHB3")))
      mi.outData.put("CHB4", String.valueOf(container.get("EXCHB4")))
    
      mi.outData.put("DAT1", String.valueOf(container.get("EXDAT1")))
      mi.outData.put("DAT2", String.valueOf(container.get("EXDAT2")))
      mi.outData.put("DAT3", String.valueOf(container.get("EXDAT3")))
      mi.outData.put("DAT4", String.valueOf(container.get("EXDAT4")))
      mi.outData.put("DAT5", String.valueOf(container.get("EXDAT5")))
      mi.outData.put("DAT6", String.valueOf(container.get("EXDAT6")))
      mi.outData.put("DAT7", String.valueOf(container.get("EXDAT7")))
    
      mi.outData.put("LMTS", String.valueOf(container.get("EXLMTS")))
      mi.outData.put("RGDT", String.valueOf(container.get("EXRGDT")))
      mi.outData.put("RGTM", String.valueOf(container.get("EXRGTM")))
      mi.outData.put("LMDT", String.valueOf(container.get("EXLMDT")))
      mi.outData.put("CHNO", String.valueOf(container.get("EXCHNO")))
      mi.outData.put("CHID", String.valueOf(container.get("EXCHID")))
      mi.write()
    } else {
      // If there is no existing record of this key, an error is thrown
      mi.error("Aucun enregistrement existant.")
    }
  }
}
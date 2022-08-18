/**
 * README
 * This extension is an API transaction from EXT600MI
 * 
 * Name: LstFieldValue
 * Description: Lists records from the table EXTCGE
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction LstFieldValue
 */
public class LstFieldValue extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility
  
  public LstFieldValue(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
    this.mi = mi
    this.database = database
    this.program = program
    this.utility = utility
  }
  
  int cono
  String chid
  int nbKeys
  
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

    // The number of fields in the keys that are not empty is defined
    if ((mi.inData.get("PK08").trim().isEmpty()) && (!mi.inData.get("PK07").trim().isEmpty())) {
      nbKeys = 9
    } else if ((mi.inData.get("PK07").trim().isEmpty()) && (!mi.inData.get("PK06").trim().isEmpty())) {
      nbKeys = 8
    } else if ((mi.inData.get("PK06").trim().isEmpty()) && (!mi.inData.get("PK05").trim().isEmpty())) {
      nbKeys = 7
    } else if ((mi.inData.get("PK05").trim().isEmpty()) && (!mi.inData.get("PK04").trim().isEmpty())) {
      nbKeys = 6
    } else if ((mi.inData.get("PK04").trim().isEmpty()) && (!mi.inData.get("PK03").trim().isEmpty())) {
      nbKeys = 5
    } else if ((mi.inData.get("PK03").trim().isEmpty()) && (!mi.inData.get("PK02").trim().isEmpty())) {
      nbKeys = 4
    } else if ((mi.inData.get("PK02").trim().isEmpty()) && (!mi.inData.get("PK01").trim().isEmpty())) {
      nbKeys = 3
    } else if ((mi.inData.get("PK01").trim().isEmpty()) && (!mi.inData.get("FILE").trim().isEmpty())) {
      nbKeys = 2
    } else if ((mi.inData.get("FILE").trim().isEmpty())) {
      // If the field FILE is empty, an error is thrown
      mi.error("Le champ 'FILE' est obligatoire.")
    } else {
      nbKeys = 10
    }

    Closure<?> releasedItemProcessor = { DBContainer data ->
      mi.outData.put("FILE", String.valueOf(data.get("EXFILE")))
      mi.outData.put("PK01", String.valueOf(data.get("EXPK01")))
      mi.outData.put("PK02", String.valueOf(data.get("EXPK02")))
      mi.outData.put("PK03", String.valueOf(data.get("EXPK03")))
      mi.outData.put("PK04", String.valueOf(data.get("EXPK04")))
      mi.outData.put("PK05", String.valueOf(data.get("EXPK05")))
      mi.outData.put("PK06", String.valueOf(data.get("EXPK06")))
      mi.outData.put("PK07", String.valueOf(data.get("EXPK07")))
      mi.outData.put("PK08", String.valueOf(data.get("EXPK08")))
      
      mi.outData.put("A030", String.valueOf(data.get("EXA030")))
      mi.outData.put("A130", String.valueOf(data.get("EXA130")))
      mi.outData.put("A230", String.valueOf(data.get("EXA230")))
      mi.outData.put("A330", String.valueOf(data.get("EXA330")))
      mi.outData.put("A430", String.valueOf(data.get("EXA430")))
      mi.outData.put("A530", String.valueOf(data.get("EXA530")))
      mi.outData.put("A630", String.valueOf(data.get("EXA630")))
      mi.outData.put("A730", String.valueOf(data.get("EXA730")))
      mi.outData.put("A830", String.valueOf(data.get("EXA830")))
      mi.outData.put("A930", String.valueOf(data.get("EXA930")))
      
      mi.outData.put("N096", String.valueOf(data.get("EXN096")))
      mi.outData.put("N196", String.valueOf(data.get("EXN196")))
      mi.outData.put("N296", String.valueOf(data.get("EXN296")))
      mi.outData.put("N396", String.valueOf(data.get("EXN396")))
      mi.outData.put("N496", String.valueOf(data.get("EXN496")))
      mi.outData.put("N596", String.valueOf(data.get("EXN596")))
      mi.outData.put("N696", String.valueOf(data.get("EXN696")))
      mi.outData.put("N796", String.valueOf(data.get("EXN796")))
      mi.outData.put("N896", String.valueOf(data.get("EXN896")))
      mi.outData.put("N996", String.valueOf(data.get("EXN996")))
    
      mi.outData.put("A256", String.valueOf(data.get("EXA256")))
      mi.outData.put("A121", String.valueOf(data.get("EXA121")))
      mi.outData.put("A122", String.valueOf(data.get("EXA122")))
    
      mi.outData.put("CHB1", String.valueOf(data.get("EXCHB1")))
      mi.outData.put("CHB2", String.valueOf(data.get("EXCHB2")))
      mi.outData.put("CHB3", String.valueOf(data.get("EXCHB3")))
      mi.outData.put("CHB4", String.valueOf(data.get("EXCHB4")))
    
      mi.outData.put("DAT1", String.valueOf(data.get("EXDAT1")))
      mi.outData.put("DAT2", String.valueOf(data.get("EXDAT2")))
      mi.outData.put("DAT3", String.valueOf(data.get("EXDAT3")))
      mi.outData.put("DAT4", String.valueOf(data.get("EXDAT4")))
      mi.outData.put("DAT5", String.valueOf(data.get("EXDAT5")))
      mi.outData.put("DAT6", String.valueOf(data.get("EXDAT6")))
      mi.outData.put("DAT7", String.valueOf(data.get("EXDAT7")))
    
      mi.outData.put("LMTS", String.valueOf(data.get("EXLMTS")))
      mi.outData.put("RGDT", String.valueOf(data.get("EXRGDT")))
      mi.outData.put("RGTM", String.valueOf(data.get("EXRGTM")))
      mi.outData.put("LMDT", String.valueOf(data.get("EXLMDT")))
      mi.outData.put("CHNO", String.valueOf(data.get("EXCHNO")))
      mi.outData.put("CHID", String.valueOf(data.get("EXCHID")))
    
      mi.write()
    }
    
    // All the data matching the input key is retrieved
    query.readAll(container, nbKeys, releasedItemProcessor)
  }
}
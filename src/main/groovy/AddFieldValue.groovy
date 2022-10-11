/**
 * README
 * This extension is an API transaction from EXT600MI
 * 
 * Name: AddFieldValue
 * Description: Adds a record to the table EXTCGE
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction AddFieldValue
 */
public class AddFieldValue extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility
  
  public AddFieldValue(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
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
    
    // If there is no existing record of this key, the other fields are set and the record is inserted in the table
    if (!query.read(container)) {
      container.set("EXA030", mi.inData.get("A030").trim())
      container.set("EXA130", mi.inData.get("A130").trim())
      container.set("EXA230", mi.inData.get("A230").trim())
      container.set("EXA330", mi.inData.get("A330").trim())
      container.set("EXA430", mi.inData.get("A430").trim())
      container.set("EXA530", mi.inData.get("A530").trim())
      container.set("EXA630", mi.inData.get("A630").trim())
      container.set("EXA730", mi.inData.get("A730").trim())
      container.set("EXA830", mi.inData.get("A830").trim())
      container.set("EXA930", mi.inData.get("A930").trim())
    
      container.set("EXN096", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N096")))
      container.set("EXN196", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N196")))
      container.set("EXN296", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N296")))
      container.set("EXN396", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N396")))
      container.set("EXN496", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N496")))
      container.set("EXN596", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N596")))
      container.set("EXN696", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N696")))
      container.set("EXN796", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N796")))
      container.set("EXN896", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N896")))
      container.set("EXN996", utility.call("NumberUtil","parseStringToDouble", mi.inData.get("N996")))
    
      container.set("EXA256", mi.inData.get("A256").trim())
      container.set("EXA121", mi.inData.get("A121").trim())
      container.set("EXA122", mi.inData.get("A122").trim())
    
      container.set("EXCHB1", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("CHB1")))
      container.set("EXCHB2", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("CHB2")))
      container.set("EXCHB3", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("CHB3")))
      container.set("EXCHB4", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("CHB4")))
    
      container.set("EXDAT1", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("DAT1")))
      container.set("EXDAT2", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("DAT2")))
      container.set("EXDAT3", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("DAT3")))
      container.set("EXDAT4", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("DAT4")))
      container.set("EXDAT5", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("DAT5")))
      container.set("EXDAT6", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("DAT6")))
      container.set("EXDAT7", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("DAT7")))
    
      container.set("EXLMTS", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EXRGDT", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EXRGTM", utility.call("DateUtil","currentTimeAsInt"))
      container.set("EXLMDT", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EXCHNO", 1)
      container.set("EXCHID", chid)
    
      query.insert(container)
    } else {
      // If the record already exists in the table, an error is thrown
      mi.error("Enregistrement déjà existant dans la table.")
    }
  }
}
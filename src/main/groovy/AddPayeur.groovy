/**
 * README
 * This extension is an API transaction
 *
 * Name: AddPayeur
 * Description: Adds a record to the table FEINVS
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction AddPayeur
 */
public class AddPayeur extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility

  public AddPayeur(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
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

    // Select fields to handle from table FEINVS
    DBAction query = database.table("FEINVS")
      .index("00")
      .selection("EICONO", "EIINRC", "EIPRIO", "EIOBV1", "EIOBV2", "EIOBV3", "EIFDAT", "EIEPID", "EIENDT", "EISCHI",
        "EIEPIF", "EIEPIV", "EISENM", "EIEIVF", "EIBKID", "EIUPD1", "EIUPD2", "EIUPD3", "EIUPD4", "EIUPD5",
        "EIDFN1", "EIDFN2", "EIDFN3", "EIDFN4", "EIDFN5", "EIDFV1", "EIDFV2", "EIDFV3", "EIDFV4", "EIDFV5",
        "EIHPRT", "EILMTS", "EIRGDT", "EIRGTM", "EILMDT", "EICHNO", "EICHID")
      .build()

    DBContainer container = query.getContainer()
    // Set the key fields
    container.set("EICONO", cono)
    container.set("EIINRC", mi.inData.get("INRC").trim())
    container.set("EIPRIO", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("PRIO")))
    container.set("EIOBV1", mi.inData.get("OBV1").trim())
    container.set("EIOBV2", mi.inData.get("OBV2").trim())
    container.set("EIOBV3", mi.inData.get("OBV3").trim())
    container.set("EIFDAT", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("FDAT")))

    // If there is no existing record of this key, the other fields are set and the record is inserted in the table
    if (!query.read(container)) {
      container.set("EIEPID", mi.inData.get("EPID").trim())
      container.set("EIENDT", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("ENDT")))
      container.set("EISCHI", mi.inData.get("SCHI").trim())
      container.set("EIEPIF", mi.inData.get("EPIF").trim())
      container.set("EIEPIV", mi.inData.get("EPIV").trim())
      container.set("EISENM", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("SENM")))
      container.set("EIEIVF", mi.inData.get("EIVF").trim())
      container.set("EIBKID", mi.inData.get("BKID").trim())

      container.set("EIUPD1", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("UPD1")))
      container.set("EIUPD2", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("UPD2")))
      container.set("EIUPD3", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("UPD3")))
      container.set("EIUPD4", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("UPD4")))
      container.set("EIUPD5", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("UPD5")))
      container.set("EIDFN1", mi.inData.get("DFN1").trim())
      container.set("EIDFN2", mi.inData.get("DFN2").trim())
      container.set("EIDFN3", mi.inData.get("DFN3").trim())
      container.set("EIDFN4", mi.inData.get("DFN4").trim())
      container.set("EIDFN5", mi.inData.get("DFN5").trim())
      container.set("EIDFV1", mi.inData.get("DFV1").trim())
      container.set("EIDFV2", mi.inData.get("DFV2").trim())
      container.set("EIDFV3", mi.inData.get("DFV3").trim())
      container.set("EIDFV4", mi.inData.get("DFV4").trim())
      container.set("EIDFV5", mi.inData.get("DFV5").trim())

      container.set("EIHPRT", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("HPRT")))

      container.set("EILMTS", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EIRGDT", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EIRGTM", utility.call("DateUtil","currentTimeAsInt"))
      container.set("EILMDT", utility.call("DateUtil","currentDateY8AsInt"))
      container.set("EICHNO", 1)
      container.set("EICHID", chid)

      query.insert(container)
    } else {
      // If the record already exists in the table, an error is thrown
      mi.error("Enregistrement déjà existant dans la table.")
    }
  }
}

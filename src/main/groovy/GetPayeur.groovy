/**
 * README
 * This extension is an API transaction
 *
 * Name: GetPayeur
 * Description: Gets a record from the table FEINVS
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction GetPayeur
 */
public class GetPayeur extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility

  public GetPayeur(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
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
    // Set the key fields of the record to get
    container.set("EICONO", cono)
    container.set("EIINRC", mi.inData.get("INRC").trim())
    container.set("EIPRIO", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("PRIO")))
    container.set("EIOBV1", mi.inData.get("OBV1").trim())
    container.set("EIOBV2", mi.inData.get("OBV2").trim())
    container.set("EIOBV3", mi.inData.get("OBV3").trim())
    container.set("EIFDAT", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("FDAT")))

    // If there is an existing record of this key, the outData is set
    if (query.read(container)) {
      mi.outData.put("CONO", String.valueOf(container.get("EICONO")))
      mi.outData.put("INRC", String.valueOf(container.get("EIINRC")))
      mi.outData.put("PRIO", String.valueOf(container.get("EIPRIO")))
      mi.outData.put("OBV1", String.valueOf(container.get("EIOBV1")))
      mi.outData.put("OBV2", String.valueOf(container.get("EIOBV2")))
      mi.outData.put("OBV3", String.valueOf(container.get("EIOBV3")))
      mi.outData.put("FDAT", String.valueOf(container.get("EIFDAT")))

      mi.outData.put("EPID", String.valueOf(container.get("EIEPID")))
      mi.outData.put("ENDT", String.valueOf(container.get("EIENDT")))
      mi.outData.put("SCHI", String.valueOf(container.get("EISCHI")))
      mi.outData.put("EPIF", String.valueOf(container.get("EIEPIF")))
      mi.outData.put("EPIV", String.valueOf(container.get("EIEPIV")))
      mi.outData.put("SENM", String.valueOf(container.get("EISENM")))
      mi.outData.put("EIVF", String.valueOf(container.get("EIEIVF")))
      mi.outData.put("BKID", String.valueOf(container.get("EIBKID")))

      mi.outData.put("UPD1", String.valueOf(container.get("EIUPD1")))
      mi.outData.put("UPD2", String.valueOf(container.get("EIUPD2")))
      mi.outData.put("UPD3", String.valueOf(container.get("EIUPD3")))
      mi.outData.put("UPD4", String.valueOf(container.get("EIUPD4")))
      mi.outData.put("UPD5", String.valueOf(container.get("EIUPD5")))

      mi.outData.put("DFN1", String.valueOf(container.get("EIDFN1")))
      mi.outData.put("DFN2", String.valueOf(container.get("EIDFN2")))
      mi.outData.put("DFN3", String.valueOf(container.get("EIDFN3")))
      mi.outData.put("DFN4", String.valueOf(container.get("EIDFN4")))
      mi.outData.put("DFN5", String.valueOf(container.get("EIDFN5")))

      mi.outData.put("DFV1", String.valueOf(container.get("EIDFV1")))
      mi.outData.put("DFV2", String.valueOf(container.get("EIDFV2")))
      mi.outData.put("DFV3", String.valueOf(container.get("EIDFV3")))
      mi.outData.put("DFV4", String.valueOf(container.get("EIDFV4")))
      mi.outData.put("DFV5", String.valueOf(container.get("EIDFV5")))

      mi.outData.put("HPRT", String.valueOf(container.get("EIHPRT")))

      mi.outData.put("LMTS", String.valueOf(container.get("EILMTS")))
      mi.outData.put("RGDT", String.valueOf(container.get("EIRGDT")))
      mi.outData.put("RGTM", String.valueOf(container.get("EIRGTM")))
      mi.outData.put("LMDT", String.valueOf(container.get("EILMDT")))
      mi.outData.put("CHNO", String.valueOf(container.get("EICHNO")))
      mi.outData.put("CHID", String.valueOf(container.get("EICHID")))
      mi.write()
    } else {
      // If there is no existing record of this key, an error is thrown
      mi.error("Aucun enregistrement existant.")
    }
  }
}

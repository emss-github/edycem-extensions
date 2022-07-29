/**
 * README
 * This extension is an API transaction
 *
 * Name: LstPayeur
 * Description: Lists records from the table FEINVS
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction LstPayeur
 */
public class LstPayeur extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility

  public LstPayeur(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
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

    // The number of fields in the keys that are not empty is defined
    if ((mi.inData.get("FDAT").trim().isEmpty()) && (!mi.inData.get("OBV3").trim().isEmpty())) {
      nbKeys = 6
    } else if ((mi.inData.get("OBV3").trim().isEmpty()) && (!mi.inData.get("OBV2").trim().isEmpty())) {
      nbKeys = 5
    } else if ((mi.inData.get("OBV2").trim().isEmpty()) && (!mi.inData.get("OBV1").trim().isEmpty())) {
      nbKeys = 4
    } else if ((mi.inData.get("OBV1").trim().isEmpty()) && (!mi.inData.get("PRIO").trim().isEmpty())) {
      nbKeys = 3
    } else if ((mi.inData.get("PRIO").trim().isEmpty()) && (!mi.inData.get("INRC").trim().isEmpty())) {
      nbKeys = 2
    } else if ((mi.inData.get("INRC").trim().isEmpty())) {
      // If the field INRC is empty, an error is thrown
      mi.error("Le champ 'INRC' est obligatoire.")
    } else {
      nbKeys = 7
    }

    Closure<?> releasedItemProcessor = { DBContainer data ->
      mi.outData.put("CONO", String.valueOf(data.get("EICONO")))
      mi.outData.put("INRC", String.valueOf(data.get("EIINRC")))
      mi.outData.put("PRIO", String.valueOf(data.get("EIPRIO")))
      mi.outData.put("OBV1", String.valueOf(data.get("EIOBV1")))
      mi.outData.put("OBV2", String.valueOf(data.get("EIOBV2")))
      mi.outData.put("OBV3", String.valueOf(data.get("EIOBV3")))
      mi.outData.put("FDAT", String.valueOf(data.get("EIFDAT")))

      mi.outData.put("EPID", String.valueOf(data.get("EIEPID")))
      mi.outData.put("ENDT", String.valueOf(data.get("EIENDT")))
      mi.outData.put("SCHI", String.valueOf(data.get("EISCHI")))
      mi.outData.put("EPIF", String.valueOf(data.get("EIEPIF")))
      mi.outData.put("EPIV", String.valueOf(data.get("EIEPIV")))
      mi.outData.put("SENM", String.valueOf(data.get("EISENM")))
      mi.outData.put("EIVF", String.valueOf(data.get("EIEIVF")))
      mi.outData.put("BKID", String.valueOf(data.get("EIBKID")))

      mi.outData.put("UPD1", String.valueOf(data.get("EIUPD1")))
      mi.outData.put("UPD2", String.valueOf(data.get("EIUPD2")))
      mi.outData.put("UPD3", String.valueOf(data.get("EIUPD3")))
      mi.outData.put("UPD4", String.valueOf(data.get("EIUPD4")))
      mi.outData.put("UPD5", String.valueOf(data.get("EIUPD5")))

      mi.outData.put("DFN1", String.valueOf(data.get("EIDFN1")))
      mi.outData.put("DFN2", String.valueOf(data.get("EIDFN2")))
      mi.outData.put("DFN3", String.valueOf(data.get("EIDFN3")))
      mi.outData.put("DFN4", String.valueOf(data.get("EIDFN4")))
      mi.outData.put("DFN5", String.valueOf(data.get("EIDFN5")))

      mi.outData.put("DFV1", String.valueOf(data.get("EIDFV1")))
      mi.outData.put("DFV2", String.valueOf(data.get("EIDFV2")))
      mi.outData.put("DFV3", String.valueOf(data.get("EIDFV3")))
      mi.outData.put("DFV4", String.valueOf(data.get("EIDFV4")))
      mi.outData.put("DFV5", String.valueOf(data.get("EIDFV5")))

      mi.outData.put("HPRT", String.valueOf(data.get("EIHPRT")))

      mi.outData.put("LMTS", String.valueOf(data.get("EILMTS")))
      mi.outData.put("RGDT", String.valueOf(data.get("EIRGDT")))
      mi.outData.put("RGTM", String.valueOf(data.get("EIRGTM")))
      mi.outData.put("LMDT", String.valueOf(data.get("EILMDT")))
      mi.outData.put("CHNO", String.valueOf(data.get("EICHNO")))
      mi.outData.put("CHID", String.valueOf(data.get("EICHID")))

      mi.write()
    }

    // All the data matching the input key is retrieved
    query.readAll(container, nbKeys, releasedItemProcessor)
  }
}

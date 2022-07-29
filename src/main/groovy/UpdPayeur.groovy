/**
 * README
 * This extension is an API transaction
 *
 * Name: UpdPayeur
 * Description: Updates a record of the table FEINVS
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction UpdPayeur
 */
public class UpdPayeur extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility

  public UpdPayeur(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
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

    Closure<?> updateCallBack = { LockedResult lockedResult ->
      checkEmptyField("EIEPID", mi.inData.get("EPID"), lockedResult)
      checkEmptyField("EIENDT", mi.inData.get("ENDT"), lockedResult)
      checkEmptyField("EISCHI", mi.inData.get("SCHI"), lockedResult)
      checkEmptyField("EIEPIF", mi.inData.get("EPIF"), lockedResult)
      checkEmptyField("EIEPIV", mi.inData.get("EPIV"), lockedResult)
      checkEmptyField("EISENM", mi.inData.get("SENM"), lockedResult)
      checkEmptyField("EIEIVF", mi.inData.get("EIVF"), lockedResult)
      checkEmptyField("EIBKID", mi.inData.get("BKID"), lockedResult)

      checkEmptyField("EIUPD1", mi.inData.get("UPD1"), lockedResult)
      checkEmptyField("EIUPD2", mi.inData.get("UPD2"), lockedResult)
      checkEmptyField("EIUPD3", mi.inData.get("UPD3"), lockedResult)
      checkEmptyField("EIUPD4", mi.inData.get("UPD4"), lockedResult)
      checkEmptyField("EIUPD5", mi.inData.get("UPD5"), lockedResult)

      checkEmptyField("EIDFN1", mi.inData.get("DFN1"), lockedResult)
      checkEmptyField("EIDFN2", mi.inData.get("DFN2"), lockedResult)
      checkEmptyField("EIDFN3", mi.inData.get("DFN3"), lockedResult)
      checkEmptyField("EIDFN4", mi.inData.get("DFN4"), lockedResult)
      checkEmptyField("EIDFN5", mi.inData.get("DFN5"), lockedResult)
      checkEmptyField("EIDFV1", mi.inData.get("DFV1"), lockedResult)
      checkEmptyField("EIDFV2", mi.inData.get("DFV2"), lockedResult)
      checkEmptyField("EIDFV3", mi.inData.get("DFV3"), lockedResult)
      checkEmptyField("EIDFV4", mi.inData.get("DFV4"), lockedResult)
      checkEmptyField("EIDFV5", mi.inData.get("DFV5"), lockedResult)

      checkEmptyField("EIHPRT", mi.inData.get("HPRT"), lockedResult)

      // The change number is retrieved from the existing record and is being added 1
      chno = lockedResult.get("EICHNO")
      chno++;
      lockedResult.set("EILMDT", utility.call("DateUtil","currentDateY8AsInt"))
      lockedResult.set("EICHNO", chno)
      lockedResult.set("EICHID", chid)
      lockedResult.update()
    }

    // If there is an existing record of this key, the fields are set and the record is updated in the table
    if (query.read(container)) {
      query.readAllLock(container, 7, updateCallBack)
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

/**
 * README
 * This extension is an API transaction
 *
 * Name: DelPayeur
 * Description: Deletes a record in the table FEINVS
 * Date       Changed By            Description
 * 20220616   Claire Loyen          Creation of transaction DelPayeur
 */
public class DelPayeur extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final UtilityAPI utility

  public DelPayeur(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
    this.mi = mi
    this.database = database
    this.program = program
    this.utility = utility
  }

  int cono

  public void main() {
    cono = (Integer) program.getLDAZD().CONO

    // Select fields to handle from table FEINVS
    DBAction query = database.table("FEINVS")
      .index("00")
      .selection("EICONO", "EIINRC", "EIPRIO", "EIOBV1", "EIOBV2", "EIOBV3", "EIFDAT", "EIEPID", "EIENDT", "EISCHI",
        "EIEPIF", "EIEPIV", "EISENM", "EIEIVF", "EIBKID", "EIUPD1", "EIUPD2", "EIUPD3", "EIUPD4", "EIUPD5",
        "EIDFN1", "EIDFN2", "EIDFN3", "EIDFN4", "EIDFN5", "EIDFV1", "EIDFV2", "EIDFV3", "EIDFV4", "EIDFV5",
        "EIHPRT", "EILMTS", "EIRGDT", "EIRGTM", "EILMDT", "EICHNO", "EICHID")
      .build()

    DBContainer container = query.getContainer()
    // Set the key fields of the record to delete
    container.set("EICONO", cono)
    container.set("EIINRC", mi.inData.get("INRC").trim())
    container.set("EIPRIO", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("PRIO")))
    container.set("EIOBV1", mi.inData.get("OBV1").trim())
    container.set("EIOBV2", mi.inData.get("OBV2").trim())
    container.set("EIOBV3", mi.inData.get("OBV3").trim())
    container.set("EIFDAT", utility.call("NumberUtil","parseStringToInteger", mi.inData.get("FDAT")))

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

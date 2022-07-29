public class CUS_xt_OIS101_B_Chk_Pre_CheckWHLOLine extends ExtendM3Trigger {
  private final ProgramAPI program
  private final InteractiveAPI interactive
  private final DatabaseAPI database
  private final LoggerAPI logger
  private final MICallerAPI miCaller

  String company
  int CONO
  String societe
  String WHLO
  String mode

  public CUS_xt_OIS101_B_Chk_Pre_CheckWHLOLine(ProgramAPI program, InteractiveAPI interactive,
                     DatabaseAPI database, LoggerAPI logger, MICallerAPI miCaller) {
    this.program = program
    this.interactive = interactive
    this.database = database
    this.logger = logger
    this.miCaller = miCaller
  }

  public void main() {

    company = program.getLDAZD().CONO
    CONO = (Integer) program.getLDAZD().CONO
    societe = program.getLDAZD().DIVI

    String DIVI = ""

    mode = interactive.getMode()

    WHLO = interactive.display.fields.OBWHLO
    DIVI = this.callMMS005MI_GetWarehouse(WHLO)
    if (DIVI != this.societe) {
      interactive.showCustomError("OBWHLO", "Vous n'avez pas le droit d'utiliser le dépôt " + WHLO + ", Il n'appartient pas à la société " + this.societe +  " sur laquelle vous êtes connecté !")
    }
  }

  /**
   * Initiation du MMS005MI_GetWarehouse .
   */
  private String callMMS005MI_GetWarehouse(String pWHLO) {
    String oDIVI = ""

    def params = ["WHLO" : pWHLO]

    def callback = {
      Map<String, String> response ->
        if (response.DIVI != null) {
          oDIVI = response.DIVI.trim()
        }
    }
    miCaller.call("MMS005MI", "GetWarehouse", params, callback)
    return oDIVI
  }
}

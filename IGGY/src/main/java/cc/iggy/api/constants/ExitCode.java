package cc.iggy.api.constants;

/**
 * Created by VanagaS on 06-05-2017.
 * 
 */
public class ExitCode {
  /* POSIX Error Codes */

  public static final byte EX_OK = 0; /* successful termination */
  public static final byte EX__BASE = 64; /* base value for error messages */
  public static final byte EX_USAGE = 64; /* command line usage error */
  public static final byte EX_DATAERR = 65; /* data format error */
  public static final byte EX_NOINPUT = 66; /* cannot open input */
  public static final byte EX_NOUSER = 67; /* addressee unknown */
  public static final byte EX_NOHOST = 68; /* host name unknown */
  public static final byte EX_UNAVAILABLE = 69; /* service unavailable */
  public static final byte EX_SOFTWARE = 70; /* internal software error */
  public static final byte EX_OSERR = 71; /* system error (e.g., can't fork) */
  public static final byte EX_OSFILE = 72; /* critical OS file missing */
  public static final byte EX_CANTCREAT = 73; /* can't create (user) output file */
  public static final byte EX_IOERR = 74; /* input/output error */
  public static final byte EX_TEMPFAIL = 75; /* temp failure; user is invited to retry */
  public static final byte EX_PROTOCOL = 76; /* remote error in protocol */
  public static final byte EX_NOPERM = 77; /* permission denied */
  public static final byte EX_CONFIG = 78; /* configuration error */
  public static final byte EX_MAX = 78; /* maximum listed value */

  /* IGGY Error Codes */

  /**
   * If error code is below the value of {@code IG_NON_CRITICAL_BASE} is non critical for user and
   * need not terminate the program if user turned on continue_on_errors
   */
  public static final byte IG_NON_CRITICAL_BASE = 48;
  public static final byte IG_FILE_NOT_FOUND = 8;



}

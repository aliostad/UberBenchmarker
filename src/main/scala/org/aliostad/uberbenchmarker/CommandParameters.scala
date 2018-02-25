package org.aliostad.uberbenchmarker

case class CommandParameters(
                              url: String = "",
                              logFile: String = "",
                              concurrency: Int = 1,
                              numberOfRequests: Int = 100,
                              numberOfSeconds: Option[Int] = None,
                              delayInMilli: Int = 0,
                              method: String = "GET",
                              templateFile: String = "",
                              plugin: String = "",
                              dataFile: String = "",
                              isTsv: Boolean = false,
                              isDryRun: Boolean = false,
                              timeField: String = "",
                              tlsVersion: Int = 3,
                              isVerbose: Boolean = false,
                              parameteriseBody: Boolean = false,
                              showCookies: Boolean = false,
                              useProxy: Boolean = false,
                              showOnlyRequest: Boolean = false,
                              showHeaders: Boolean = false,
                              saveResponse: Boolean = false,
                              responseFolder: String = "",
                              dontCap: Boolean = false,
                              responseRegex: String = "",
                              jsonCountPath: String = "",
                              warmupSeconds: Int = 0,
                              reportSliceSeconds: Int = 3,
                              reportFolder: String = "",
                              dontBrowseReports: Boolean = false,
                              shuffleData: Boolean = false
                            )


object CommandParameters {

  def parse(args: List[String]): Option[CommandParameters] = {

    val parser = new scopt.OptionParser[CommandParameters]("UberBenchmarker") {

      head(BuildInfo.name, BuildInfo.version)

      opt[String]('u', "url").required().valueName("<url>").
        action( (x, c) => c.copy(url = x)).text("Required. Target URL to call. " +
        "Can include placeholders as triple curly brackets.")

      opt[Int]('c', "concurrency").
        action( (x, c) => c.copy(concurrency = x)).text("(Default: 1) Number of concurrent requests.")

      opt[Int]('n', "numberOfRequests").
        action( (x, c) => c.copy(numberOfRequests = x)).text("(Default: 100) Total number of requests")

      opt[Int]('N', "numberOfSeconds").
        action( (x, c) => c.copy(numberOfSeconds = Some(x))).text("Number of seconds to run the test. " +
        "If specified, -n will be ignored.")

      opt[Int]('y', "delayInMillis").
        action( (x, c) => c.copy(delayInMilli = x)).text("(Default: 0) Delay in millisecond after firing a request. " +
        "Normally used to control the throughput")

      opt[String]('m', "method").
        action( (x, c) => c.copy(method = x)).text("(Default: GET) HTTP Method to use")

      opt[String]('t', "templateFile").
        action( (x, c) => c.copy(templateFile = x)).text("Path to the request template to use")

      opt[String]('p', "plugin").
        action( (x, c) => c.copy(plugin = x)).text("Name of the plugin to replace placeholders.")

      opt[String]('l', "logFile").
        action( (x, c) => c.copy(logFile = x)).text("Path to the log file storing run stats.")

      opt[String]('f', "file").
        action((x, c) => c.copy(dataFile = x)).text("Path to CSV/TSV file providing replacement values for the test")

      opt[Unit]('a', "TSV").
        action((x, c) => c.copy(isTsv = true)).text("Whether you have provided a tab-separated-file (TSV) with -f option instead of CSV")

      opt[Unit]('d', "dryRun").
        action((x, c) => c.copy(isDryRun = true)).text("Runs a single dry run request to make sure all is good")

      opt[String]('e', "timeField").
        action( (x, c) => c.copy(timeField = x)).text("esignates a datetime field in data. " +
        "If set, requests will be sent according to order and timing of records.")

      opt[Int]('g', "tlsVersion").
        action((x, c) => c.copy(tlsVersion = x)).text("Version of TLS used. " +
        "Accepted values are 0, 1, 2 and 3 for TLS 1.0, TLS 1.1 and TLS 1.2 and SSL3, respectively")

      opt[Unit]('b', "tokeniseBody").
        action((x, c) => c.copy(parameteriseBody = true)).text("Parameterise body.")

      opt[Unit]('k', "cookies").
        action((x, c) => c.copy(showCookies = true)).text("Outputs cookies.")

      opt[Unit]('x', "useProxy").
        action((x, c) => c.copy(useProxy = true)).text("Whether to use default browser proxy. Useful for seeing request/response in Fiddler.")

      opt[Unit]('q', "onlyRequest").
        action((x, c) => c.copy(showOnlyRequest = true)).text("In a dry-run (debug) mode shows only the request.")

      opt[Unit]('h', "headers").
        action((x, c) => c.copy(showHeaders = true)).text("Displays headers for request and response.")

      opt[Unit]('z', "saveResponses").
        action((x, c) => c.copy(saveResponse = true)).text("Saves responses in -w parameter or if not provided in\\response")

      opt[String]('w', "responseFolder").
        action((x, c) => c.copy(responseFolder = x)).text("Folder to save responses in if and only if -z parameter is set")

      opt[String]('R', "responseRegex").
        action((x, c) => c.copy(responseRegex = x)).text("Regex to extract from response. If it has groups, it retrieves the last group.")

      opt[Unit]('C', "dontCap").
        action((x, c) => c.copy(dontCap = true)).text("Don't Cap to 50 characters when Logging parameters.")

      opt[String]('j', "jsonCount").
        action((x, c) => c.copy(jsonCountPath = x)).text("Captures number of elements under the path e.g. " +
        "root/leaf1/leaf2 finds count of leaf2 children - stores in the log as another parameter")

      opt[Int]('W', "warmupPeriod").
        action( (x, c) => c.copy(warmupSeconds = x)).text("(Default: 0) Number of seconds to gradually increase number of concurrent users. " +
        "Warm-up calls do not affect stats.")

      opt[Int]('P', "reportSliceSeconds").
        action( (x, c) => c.copy(reportSliceSeconds = x)).text("(Default: 3) Number of seconds as interval for reporting slices. " +
        "E.g. if chosen as 5, report charts have 5 second intervals.")

      opt[String]('F', "reportFolder").
        action((x, c) => c.copy(reportFolder = x)).text("Name of the folder where report files get stored. " +
        "By default it is in yyyy-MM-dd_HH-mm-ss.ffffff of the start time.")

      opt[Unit]('B', "dontBrowseToReports").
        action((x, c) => c.copy(dontBrowseReports = true)).text("By default it, sb opens the browser with the report of the running test. " +
        "If specified, it wil not browse.")

      opt[Unit]('U', "shuffleData").
        action((x, c) => c.copy(shuffleData = true)).text("If specified, shuffles the dataset provided by -f option.")
    }

    parser.parse(args, CommandParameters())

  }

}

/*

  -c, --concurrency            (Default: 1) Number of concurrent requests

  -n, --numberOfRequests       (Default: 100) Total number of requests

  -N, --numberOfSeconds        Number of seconds to run the test. If specified, -n will be ignored.

  -y, --delayInMillisecond     (Default: 0) Delay in millisecond

  -u, --url                    Required. Target URL to call. Can include placeholders.

  -m, --method                 (Default: GET) HTTP Method to use

  -t, --template               Path to request template to use

  -p, --plugin                 Name of the plugin (DLL) to replace placeholders. Should contain one class which
                               implements IValueProvider. Must reside in the same folder.

  -l, --logfile                Path to the log file storing run stats

  -f, --file                   Path to CSV file providing replacement values for the test

  -a, --TSV                    If you provide a tab-separated-file (TSV) with -f option instead of CSV

  -d, --dryRun                 Runs a single dry run request to make sure all is good

  -e, --timedField             Designates a datetime field in data. If set, requests will be sent according to order
                               and timing of records.

  -g, --tlsVersion             Version of TLS used. Accepted values are 0, 1, 2 and 3 for TLS 1.0, TLS 1.1 and TLS 1.2
                               and SSL3, respectively

  -v, --verbose                Provides verbose tracing information

  -b, --tokeniseBody           Tokenise the body

  -k, --cookies                Outputs cookies

  -x, --useProxy               Whether to use default browser proxy. Useful for seeing request/response in Fiddler.

  -q, --onlyRequest            In a dry-run (debug) mode shows only the request.

  -h, --headers                Displays headers for request and response.

  -z, --saveResponses          saves responses in -w parameter or if not provided in\response_<timestamp>

  -w, --responsesFolder        folder to save responses in if and only if -z parameter is set

  -?, --help                   Displays this help.

  -C, --dontcap                Don't Cap to 50 characters when Logging parameters

  -R, --responseRegex          Regex to extract from response. If it has groups, it retrieves the last group.

  -j, --jsonCount              Captures number of elements under the path e.g. root/leaf1/leaf2 finds count of leaf2
                               children - stores in the log as another parameter

  -W, --warmUpPeriod           (Default: 0) Number of seconds to gradually increase number of concurrent users. Warm-up
                               calls do not affect stats.

  -P, --reportSliceSeconds     (Default: 3) Number of seconds as interval for reporting slices. E.g. if chosen as 5,
                               report charts have 5 second intervals.

  -F, --reportFolder           Name of the folder where report files get stored. By default it is in
                               yyyy-MM-dd_HH-mm-ss.ffffff of the start time.

  -B, --dontBrowseToReports    By default it, sb opens the browser with the report of the running test. If specified,
                               it wil not browse.

  -U, --shuffleData            If specified, shuffles the dataset provided by -f option.

  --help                       Display this help screen.

 */
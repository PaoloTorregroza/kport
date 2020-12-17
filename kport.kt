/*
 * PORTS TCP SYN ------------------------------------------------------------------- 
 * Send SYN packets to all ports
 * If response is a SYN/ACK packet it is open
 * If the response is a RST packet is close
 * If no packet returned or ICMP Port unreachable the port is protected by firewall
 *
 * UDP PORTS -----------------------------------------------------------------------
 * If port is closed server sends ICMP Port unreachable, else return nothing (can be
 * open or protected)
 */
import java.io.IOException
import java.net.Socket
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    val host: String? = args[0]
    val mode: String? = args[1]

    if (host == null) {
        throw IllegalArgumentException("Host is required")
    } else {
        for (port in PortsInfo.ports) {
            val res = checkPort(host, port)
            if (res) {
                if (mode != "closed")
                    println("$port is open")
            } else {
                if (mode != "open")
                    println("$port is closed or protected")
            }
        }
    }

}

// it throws an error if the connection can be made
fun checkPort(host: String, port: Int): Boolean {
    return try {
        val s = Socket(host, port)
        s.close()
        true
    } catch (e: IOException) {false}
}

object PortsInfo {
    // Well known ports list from wikipedia https://es.wikipedia.org/wiki/Anexo:Puertos_de_red
    val ports = arrayOf(
        1, 5, 7, 9, 11, 13, 17, 18, 19, 20, 21, 22, 23, 25, 37,
        39, 42, 43, 49, 50, 53, 63, 66, 67, 68, 69, 70, 79, 80,
        88, 95, 101, 107, 109, 110, 111, 113, 115, 117, 119, 123,
        135, 137, 138, 139, 143, 161, 162, 174, 177, 178, 179,
        194, 199, 201, 202, 204, 206, 209, 210, 213, 220, 245,
        347, 363, 369, 370, 372, 389, 427, 434, 435, 443, 444,
        445, 465, 500, 512, 513, 514, 515, 520, 521, 587, 591,
        631, 666, 690, 993, 995
    )
}

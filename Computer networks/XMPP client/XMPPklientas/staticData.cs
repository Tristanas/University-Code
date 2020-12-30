using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace XMPPklientas
{
    static class staticData
    {
        public static List<MsgWindow> ChatHeads = new List<MsgWindow>();

        public static string Username;
        public static string Resource;
        public static string Server;
        public static TcpClient TC;
        public static NetworkStream stream;
        public static XMPPclient Xmpp;
        public static EventWaitHandle waitHandle = new ManualResetEvent(initialState: true);
        public static Thread RecvThread;
    }
}

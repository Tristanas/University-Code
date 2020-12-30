using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace XMPPklientas
{
    class XMPPclient
    {

        NetworkStream stream;
        TcpClient TC;
        public string server = "ktpleb";
        public string Resource = "core";

        public XMPPclient(NetworkStream stream, TcpClient TC)
        {
            this.TC = TC;
            this.stream = stream;
        }

        public async Task addFriend(string name, string friendName)
        {
            string Msg = "<iq from='" + name + "@"+ server + "/" + Resource + "' id='ph1xaz53' type='set'><query xmlns='jabber:iq:roster'><item jid='" + friendName + "@" + server + "/" + Resource + "'></item></query></iq>";
            Console.WriteLine("Client: " + Msg);
            byte[] data = Encoding.ASCII.GetBytes(Msg);
            stream.Write(data, 0, data.Length);
        }

        public async Task ChangePassAsync(string name, string newpass)
        {
            string Msg = "<iq type='set' to='" + server + "' id='change1'><query xmlns='jabber:iq:register'><username>" + name + "</username><password>" + newpass + "</password></query></iq>";
            Console.WriteLine("Client: " + Msg);
            byte[] data = Encoding.ASCII.GetBytes(Msg);
            stream.Write(data, 0, data.Length);
        }

        public async Task unregAsync(string name)
        {
            string Msg = "<iq type='set' from='" + name + "@" + server + "/" + Resource + "' id='unreg1'><query xmlns='jabber:iq:register'><remove/></query></iq>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);
        }

        public void sendMsg(string Msg)
        {
            byte[] data = Encoding.ASCII.GetBytes(Msg);
            stream.Write(data, 0, data.Length);
        }

        public async Task sendChatMsgAsync(string Msg, string name, string receiver)
        {
            string chat = "<message from='" + name + "@" + server + "/" + Resource + "' to='" + receiver + "@" + server + "/" + Resource + "' type='chat'><body>" + Msg + "</body><active xmlns='http://jabber.org/protocol/chatstates'/></message>";
            Console.WriteLine("Client: " + chat);
            sendMsg(chat);
        }

        async Task bindResourceAsync(string name)
        {
            startStream();

            Console.WriteLine("Receive: " + await receiveMsgAsync());

            string Msg = "<iq from='" + name + "@" + server + "/" + Resource + "' type='set' id='bind-1'><bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'><resource>" + Resource + "</resource></bind></iq>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);
        }

        public async Task RemoveFriend(string name, string friendName)
        {
            string Msg = "<iq from='" + name + "@" + server + "/" + Resource + "' id='hm4hs97y' type='set'><query xmlns='jabber:iq:roster'><item jid='" + friendName + "@" + server + "' subscription='remove'/></query></iq>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);
        }

        public async Task establishSesAsync()
        {
            string Msg = "<iq to='" + server + "' type='set' id='sess_1'><session xmlns='urn:ietf:params:xml:ns:xmpp-session'/></iq>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);
        }

        public async Task<int> registerAsync(string name, string password)
        {
            startStream();

            Console.WriteLine("Receive: " + await receiveMsgAsync());

            string Msg = "<iq type='get' id='reg1' to='" + server + "'><query xmlns ='jabber:iq:register'/></iq> ";
            
            sendMsg(Msg);
            Console.WriteLine("Client: " + Msg);
            Console.WriteLine("Receive: " + await receiveMsgAsync());

            Msg = "<iq type='set' id='reg2'><query xmlns='jabber:iq:register'><username>" + name + "</username><password>" + password + "</password></query></iq>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);

            var str ="<nesting>"+await receiveMsgAsync()+"</nesting>";
            Console.WriteLine("Receive: " + str);
            if (str != "<nesting><iq xml:lang='en' from='" + server + "' type='result' id='reg2'/></nesting>")
            {
                XmlSerializer ser = new XmlSerializer(typeof(nesting));
                TextReader reader = new StringReader(str);
                nesting chall = (nesting)ser.Deserialize(reader);
                if (chall.iq.ToArray()[0].error == null)
                {
                    str = "<nesting>" + await receiveMsgAsync() + "</nesting>";
                    Console.WriteLine("Receive: " + str);
                    ser = new XmlSerializer(typeof(nesting));
                    reader = new StringReader(str);
                    chall = (nesting)ser.Deserialize(reader);
                    if (chall.iq.ToArray()[0].error == null) return 0;
                }
                Console.WriteLine("error: " + chall.iq.ToArray()[0].error.code);
                return chall.iq.ToArray()[0].error.code;
            }
            return 0;
        }

        public string receiveMsg()
        {
            Byte[] data = new Byte[TC.ReceiveBufferSize];
            string responseData = String.Empty;
            int bytes = 1;
            bytes = stream.Read(data, 0, data.Length);
            //if (bytes <= 0) return null;
            responseData = Encoding.ASCII.GetString(data, 0, bytes);
            return responseData;
        }

        public async Task<string> receiveMsgAsync()
        {
            Byte[] data = new Byte[TC.ReceiveBufferSize];
            string responseData = String.Empty;
            int bytes = 1;
            bytes = await stream.ReadAsync(data, 0, data.Length);
            if (bytes <= 0) return null;
            responseData = Encoding.ASCII.GetString(data, 0, bytes);
            return responseData;
        }

        void startStream()
        {
            string Msg = "<?xml version='1.0' encoding='utf-8' ?> <stream:stream xmlns='jabber:client' to='" + server + "' xmlns:stream='http://etherx.jabber.org/streams' version = '1.0'>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);
        }

        public async Task<int> auth2Async(string name, string password)
        {
            startStream();

            Console.WriteLine("Received: {0}", await receiveMsgAsync());

            var plainTextBytes = Encoding.UTF8.GetBytes("\0" + name + "\0" + password);
            string Msg = "<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='PLAIN'>" + Convert.ToBase64String(plainTextBytes) + "</auth>"; //"<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='DIGEST-MD5'/>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);

            var recv = await receiveMsgAsync();
            Console.WriteLine("Receive: " + recv);

            try
            {
                XmlSerializer ser = new XmlSerializer(typeof(failure));
                TextReader reader = new StringReader(recv);
                failure chall = (failure)ser.Deserialize(reader);
                if (chall.text.Value == "Invalid username or password")
                    return 1;
            }
            catch
            {
                Console.WriteLine("No Failure");
            }
            await bindResourceAsync(name);
            return 0;
        }

        public void gettingRosterAsync(string name)
        {
            string Msg = "<iq from='" + name + "@" + server + "/" + Resource + "' type='get' id='roster-1'><query xmlns='jabber:iq:roster'/></iq>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);
        }

        public async Task pressenceAsync(string name)
        {
            string Msg = "<presence from='" + name + "@" + server + "/" + Resource + "'/>";
            Console.WriteLine("Client: " + Msg);
            sendMsg(Msg);
        }
    }
}

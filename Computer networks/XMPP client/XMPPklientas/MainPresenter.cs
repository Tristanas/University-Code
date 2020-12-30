using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Serialization;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.TaskbarClock;

namespace XMPPklientas
{
    class MainPresenter
    {
        List<string> friends = new List<string>();
        MainWindow MW;
        bool ThreadStarted = false;

        public MainPresenter(MainWindow MW)
        {
            this.MW = MW;

            MW.UsernameField = staticData.Username;
            MW.ServerField = staticData.Server;
            MW.ResourceField = staticData.Resource;
            announcePresence();
        }

        public async void announcePresence()
        {
            
            new Thread(async () =>
            {
                Thread.CurrentThread.IsBackground = true;
                while (true)
                {
                    string str = await staticData.Xmpp.receiveMsgAsync();
                    
                    new Task(new Action(() => { Console.WriteLine("Thread Receive: " + str); deserializeXml(str); })).Start();
                    if (!staticData.TC.Connected) break;
                }
            }).Start();
            await getFriends();
            await staticData.Xmpp.pressenceAsync(staticData.Username);
            await staticData.Xmpp.establishSesAsync();
            
            ThreadStarted = true;
        }

        public async void ChangePass()
        {
            if (MW.ChangPass1 == string.Empty || MW.ChangPass2 == string.Empty)
            {
                MessageBox.Show("All the fields must be filled", "Empty field", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (MW.ChangPass1 != MW.ChangPass2)
            {
                MessageBox.Show("Passwords do not match", "No match", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            await staticData.Xmpp.ChangePassAsync(staticData.Username, MW.ChangPass1);
        }

        public async void DeleteAccount()
        {
            DialogResult dialogResult = MessageBox.Show("After you delete your account - you will not be able to log back in, are you sure?", "Confirmation", MessageBoxButtons.YesNo);
            if (dialogResult == DialogResult.Yes)
            {
                await staticData.Xmpp.unregAsync(staticData.Username);
                Application.Exit();
            }
        }

        public async void removeF()
        {
            if(MW.FriendTxt == string.Empty)
            {
                MessageBox.Show("Field is empty", "Empty field", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if(!friends.Contains(MW.FriendTxt))
            {
                MessageBox.Show("You dont have such friend", "No friend", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            await staticData.Xmpp.RemoveFriend(staticData.Username, MW.FriendTxt);
            await getFriends();
        }

        public async Task getFriends()
        {
            staticData.Xmpp.gettingRosterAsync(staticData.Username);
            var str = await staticData.Xmpp.receiveMsgAsync();
            Console.WriteLine("Receive: " + str);
            deserializeXml(str);
        }

        public void deserializeXml(string str)
        {
            str = "<nesting>" + str + "</nesting>";
            XmlSerializer ser = new XmlSerializer(typeof(nesting));
            TextReader reader = new StringReader(str);
            var Reads = XmlReader.Create(reader);
            try
            {
                nesting chal = (nesting)ser.Deserialize(Reads);
                if (chal.message != null)
                {
                    foreach (var chall in chal.message)
                    {
                        MW.Invoke(new MethodInvoker(() =>
                        {
                            if (chall.type == "error")
                            {
                                staticData.ChatHeads.Find(x => x.receiver == chall.from.Substring(0, chall.from.LastIndexOf('@'))).Close();
                                MessageBox.Show("The user does not excist", "No user", MessageBoxButtons.OK, MessageBoxIcon.Error);
                                return;
                            }
                            if (chall.delay != null) chall.body = "(Delayed " + (Int32)((DateTime.Now -chall.delay.stamp).TotalMinutes-180) +" minutes ago)   " + chall.body;
                            var msgW = staticData.ChatHeads.Find(x => x.receiver == chall.from.Substring(0, chall.from.LastIndexOf('@')));
                            if (msgW == null)
                            {
                                msgW = new MsgWindow();
                                msgW.Text = "Chat - " + chall.from.Substring(0, chall.from.LastIndexOf('@'));
                                msgW.receiver = chall.from.Substring(0, chall.from.LastIndexOf('@'));
                                msgW.sender = staticData.Username;
                                staticData.ChatHeads.Add(msgW);
                                msgW.Show();
                            }
                            msgW.MsgB = msgW.receiver + ":    " + chall.body;
                            msgW.MsgB = Environment.NewLine;
                            if (msgW.Visible == false) msgW.Show();
                        }));
                    }
                }
                if (chal.iq != null)
                {
                    
                    foreach (var chall in chal.iq)
                    {
                        MW.Invoke(new MethodInvoker(() =>
                        {
                            if (chall.error != null) return;
                            if(chall.query != null && chall.id == "roster-1")
                            {
                                friends.Clear();
                                MW.list.Items.Clear();
                                if (chall.query.item != null)
                                {
                                    foreach (var item in chall.query.item)
                                    {
                                        var name = item.jid.Substring(0, item.jid.LastIndexOf('@'));
                                        friends.Add(name);
                                        MW.list.Items.Add(name);
                                        MW.list.Refresh();
                                    }
                                }
                            }
                        }));
                    }
                }
            }
            catch
            {
                Console.WriteLine("Unrecognized XML stanza");
            }
        }

        public void updateList(string[] friends)
        {
            MW.Invoke(new MethodInvoker(() =>
            {
                MW.list.Items.Clear();
            }));
            if (friends == null) return;
            foreach (var item in friends)
            {
                MW.Invoke(new MethodInvoker(() =>
                {
                    MW.list.Items.Add(item);
                }));
            }            
        }

        public async void SendChat()
        {
            await staticData.Xmpp.sendChatMsgAsync(MW.MessageTx, staticData.Username, MW.ReceiverTx);
            var msgW = staticData.ChatHeads.Find(x => x.receiver == MW.ReceiverTx);
            if (msgW == null)
            {
                msgW = new MsgWindow();
                msgW.Text = "Chat - " + MW.ReceiverTx;
                msgW.receiver = MW.ReceiverTx;
                msgW.sender = staticData.Username;
                staticData.ChatHeads.Add(msgW);
                msgW.Show();
            }
            msgW.MsgB = "You:    " + MW.MessageTx;
            msgW.MsgB = Environment.NewLine;
            if (msgW.Visible == false) msgW.Show();
        }

        public async void AddFriend()
        {
            if (MW.FriendTxt == string.Empty)
            {
                MessageBox.Show("Field must be filled", "Field empty", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            await staticData.Xmpp.addFriend(staticData.Username, MW.FriendTxt);
            await getFriends();
        }
    }
}

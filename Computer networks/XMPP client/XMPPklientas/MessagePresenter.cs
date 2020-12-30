using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMPPklientas
{ 
    class MessagePresenter
    {
        MsgWindow MW;

        public MessagePresenter(MsgWindow MW)
        {
            this.MW = MW;
        }

        public void sendMsg()
        {
            if (MW.TypeB == string.Empty) return;
            staticData.Xmpp.sendChatMsgAsync(MW.TypeB, MW.sender, MW.receiver);
            MW.MsgB = "You:    " + MW.TypeB;
            MW.MsgB = Environment.NewLine;
        }

    }
}

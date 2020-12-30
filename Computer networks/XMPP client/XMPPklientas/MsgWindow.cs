using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace XMPPklientas
{
    public partial class MsgWindow : Form
    {
        public string receiver;
        public string sender;
        public List<string> Msgs = new List<string>();

        MessagePresenter MP;

        public string MsgB { set => MsgBoxx.AppendText(value); }
        public string TypeB { get => TypeBox.Text; }

        public MsgWindow()
        {
            InitializeComponent();
            MP = new MessagePresenter(this);
            this.FormClosing += (y, x) => {
                if (x.CloseReason == CloseReason.UserClosing)
                {
                    x.Cancel = true;
                    Hide();
                }
            };
        }

        private void button1_Click(object sender, EventArgs e)
        {
            MP.sendMsg();
        }

    }
}

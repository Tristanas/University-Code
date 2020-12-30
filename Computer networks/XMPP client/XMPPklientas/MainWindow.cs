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
    public partial class MainWindow : Form
    {
        public string UsernameField { set => UsernameLab.Text = value; }
        public string ServerField { set => ServerLab.Text = value; }
        public string ResourceField { set => ResourceLab.Text = value; }
        public string ChangPass1 { get => Pass1B.Text; }
        public string ChangPass2 { get => Pass2B.Text; }
        public string FriendTxt { get => FriendField.Text; }
        public ListBox list { get => FriendsList; }
        public string ReceiverTx { get => ReceiverBox.Text; }
        public string MessageTx { get => MessageBox.Text; }


        MainPresenter MP;

        public MainWindow()
        {
            InitializeComponent();
            this.Text = "Mif Client";
            MP = new MainPresenter(this);
            this.FormClosed += (x, y) => Application.Exit();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            MP.ChangePass();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            MP.AddFriend();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            MP.getFriends();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            MP.removeF();
        }

        private void button5_Click(object sender, EventArgs e)
        {
            MP.SendChat();
        }

        private void button6_Click(object sender, EventArgs e)
        {
            MP.DeleteAccount();
        }

    }
}

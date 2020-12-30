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
    public partial class Login : Form
    {
        public string nameTx { get => NameTxt.Text; }
        public string passTx { get => PassTxt.Text; }

        LoginPresenter LP;

        public Login()
        {
            InitializeComponent();
            LP = new LoginPresenter(this);
        }

        private void RegisterBtn_Click(object sender, EventArgs e)
        {
            LP.initRegister();
        }

        private void LoginBtn_Click(object sender, EventArgs e)
        {
            LP.LoginToServerAsync();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
    }
}

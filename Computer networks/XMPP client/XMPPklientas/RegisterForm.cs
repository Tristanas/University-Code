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
    public partial class RegisterForm : Form
    {
        public string NameTx { get => NameTxt.Text; }
        public string Pass1Txt { get => FirstPass.Text; }
        public string Pass2Txt { get => ConfirmPass.Text; }

        RegisterPresenter RP;

        public RegisterForm()
        {
            InitializeComponent();
            RP = new RegisterPresenter(this);
        }

        private void RegisterBtn_Click(object sender, EventArgs e)
        {
            RP.RegisterToServerAsync();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}

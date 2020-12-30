using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace XMPPklientas
{
    class RegisterPresenter
    {
        RegisterForm RF;

        public RegisterPresenter(RegisterForm RF)
        {
            this.RF = RF;
            RF.Text = "Registration";
        }

        public async void RegisterToServerAsync()
        {
            if (RF.Pass1Txt == string.Empty || RF.Pass2Txt == string.Empty || RF.NameTx == string.Empty)
            {
                MessageBox.Show("All the fields must be filled", "Empty field", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if(RF.Pass2Txt != RF.Pass1Txt)
            {
                MessageBox.Show("Passwords do not match", "No match", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            //logic
            TcpClient TC = new TcpClient("127.0.0.1", 5222);
            NetworkStream stream = TC.GetStream();
            XMPPclient Xmpp = new XMPPclient(stream, TC);
            try
            {
                int x = await Xmpp.registerAsync(RF.NameTx, RF.Pass1Txt);
                if (x == 409)
                {
                    MessageBox.Show("The Username is already in use", "Username in use", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    stream.Close();
                    TC.Close();
                }
                else if(x == 500)
                {
                    MessageBox.Show("Can't create accounts so fast. Wait a moment.", "WOOOSH! Too fast bruh!", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    stream.Close();
                    TC.Close();
                    Application.Exit();
                }
                else if(x > 0)
                {
                    MessageBox.Show("Unknown error occured. Try again later.", "Oh snap!", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    stream.Close();
                    TC.Close();
                }
                else
                {
                    MessageBox.Show("Account successfuly created", "Registrations success", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    stream.Close();
                    TC.Close();
                    RF.Close();
                }
            }
            catch(Exception e)
            {
                Console.WriteLine("Except");
                Console.WriteLine(await Xmpp.receiveMsgAsync());
            }
            
        }
    }
}

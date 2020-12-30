using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace XMPPklientas
{
    class LoginPresenter
    {
        Login loginForm;

        public LoginPresenter(Login loginForm)
        {
            this.loginForm = loginForm;
            loginForm.Text = "Client login";
        }

        public void initRegister()
        {
            loginForm.Hide();
            var RF = new RegisterForm();
            RF.Show();
            RF.FormClosed += (x, y) => { enableThis(); };
        }

        public void enableThis()
        {
            loginForm.Show();
        }

        public async void LoginToServerAsync()
        {
            if (loginForm.nameTx == string.Empty || loginForm.passTx == string.Empty)
            {
                MessageBox.Show("All the fields must be filled", "Empty field", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            TcpClient TC = new TcpClient("127.0.0.1", 5222);
            NetworkStream stream = TC.GetStream();
            XMPPclient Xmpp = new XMPPclient(stream, TC);
            int x = await Xmpp.auth2Async(loginForm.nameTx, loginForm.passTx);
            if(x == 1)
            {
                //failure
                MessageBox.Show("Username or Password incorrect", "Incorrect credentials",MessageBoxButtons.OK, MessageBoxIcon.Information);
                TC.Close();
                stream.Close();
            }
            else if(x == 0)
            {
                staticData.TC = TC;
                staticData.stream = stream;
                staticData.Xmpp = Xmpp;
                staticData.Server = Xmpp.server;
                staticData.Resource = Xmpp.Resource;
                staticData.Username = loginForm.nameTx;
                loginForm.Hide();
                var main = new MainWindow();
                main.Show();
            }

        }
    }
}

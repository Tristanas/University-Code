namespace XMPPklientas
{
    partial class MsgWindow
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.MsgBoxx = new System.Windows.Forms.TextBox();
            this.TypeBox = new System.Windows.Forms.TextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // MsgBoxx
            // 
            this.MsgBoxx.Location = new System.Drawing.Point(12, 12);
            this.MsgBoxx.Multiline = true;
            this.MsgBoxx.Name = "MsgBoxx";
            this.MsgBoxx.ReadOnly = true;
            this.MsgBoxx.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.MsgBoxx.Size = new System.Drawing.Size(776, 285);
            this.MsgBoxx.TabIndex = 0;
            // 
            // TypeBox
            // 
            this.TypeBox.Location = new System.Drawing.Point(12, 303);
            this.TypeBox.Multiline = true;
            this.TypeBox.Name = "TypeBox";
            this.TypeBox.Size = new System.Drawing.Size(776, 102);
            this.TypeBox.TabIndex = 1;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(368, 415);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 2;
            this.button1.Text = "Send";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // MsgWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.TypeBox);
            this.Controls.Add(this.MsgBoxx);
            this.Name = "MsgWindow";
            this.Text = "MsgWindow";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox MsgBoxx;
        private System.Windows.Forms.TextBox TypeBox;
        private System.Windows.Forms.Button button1;
    }
}
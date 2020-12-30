using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMPPklientas
{
    // NOTE: Generated code may require at least .NET Framework 4.5 or .NET Core/Standard 2.0.
    /// <remarks/>
    [System.SerializableAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "urn:ietf:params:xml:ns:xmpp-sasl")]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "urn:ietf:params:xml:ns:xmpp-sasl", IsNullable = false)]
    public partial class failure
    {

        private object notauthorizedField;

        private failureText textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("not-authorized")]
        public object notauthorized
        {
            get
            {
                return this.notauthorizedField;
            }
            set
            {
                this.notauthorizedField = value;
            }
        }

        /// <remarks/>
        public failureText text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.SerializableAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true, Namespace = "urn:ietf:params:xml:ns:xmpp-sasl")]
    public partial class failureText
    {

        private string langField;

        private string valueField;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(Form = System.Xml.Schema.XmlSchemaForm.Qualified, Namespace = "http://www.w3.org/XML/1998/namespace")]
        public string lang
        {
            get
            {
                return this.langField;
            }
            set
            {
                this.langField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string Value
        {
            get
            {
                return this.valueField;
            }
            set
            {
                this.valueField = value;
            }
        }
    }



}

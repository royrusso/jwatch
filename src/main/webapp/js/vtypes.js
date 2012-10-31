/**
 * From http://www.sencha.com/forum/showthread.php?4271-More-VTypes!
 */
Ext.form.VTypes["hostnameVal1"] = /^[a-zA-Z][-.a-zA-Z0-9]{0,254}$/;
Ext.form.VTypes["hostnameVal2"] = /^[a-zA-Z]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9]){0,1}([.][a-zA-Z]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9]){0,1}){0,}$/;
Ext.form.VTypes["ipVal"] = /^([1-9][0-9]{0,1}|1[013-9][0-9]|12[0-689]|2[01][0-9]|22[0-3])([.]([1-9]{0,1}[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])){2}[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-4])$/;
Ext.form.VTypes["netmaskVal"] = /^(128|192|224|24[08]|25[245].0.0.0)|(255.(0|128|192|224|24[08]|25[245]).0.0)|(255.255.(0|128|192|224|24[08]|25[245]).0)|(255.255.255.(0|128|192|224|24[08]|252))$/;
Ext.form.VTypes["portVal"] = /^(0|[1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/;
Ext.form.VTypes["multicastVal"] = /^((22[5-9]|23[0-9])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])){3})|(224[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])){2})|(224[.]0[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])))$/;
Ext.form.VTypes["usernameVal"] = /^[a-zA-Z][-_.a-zA-Z0-9]{0,30}$/;
Ext.form.VTypes["passwordVal1"] = /^.{6,31}$/;
Ext.form.VTypes["passwordVal2"] = /[^a-zA-Z].*[^a-zA-Z]/;
Ext.form.VTypes["hostname"] = function(v)
{
    if (!Ext.form.VTypes["hostnameVal1"].test(v))
    {
        Ext.form.VTypes["hostnameText"] = "Must begin with a letter and not exceed 255 characters"
        return false;
    }
    Ext.form.VTypes["hostnameText"] = "L[.L][.L][.L][...] where L begins with a letter, ends with a letter or number, and does not exceed 63 characters";
    return Ext.form.VTypes["hostnameVal2"].test(v);
}
Ext.form.VTypes["hostnameText"] = "Invalid Hostname"
Ext.form.VTypes["hostnameMask"] = /[-.a-zA-Z0-9]/;
Ext.form.VTypes["ip"] = function(v)
{
    return Ext.form.VTypes["ipVal"].test(v);
}
Ext.form.VTypes["ipText"] = "1.0.0.1 - 223.255.255.254 excluding 127.x.x.x"
Ext.form.VTypes["ipMask"] = /[.0-9]/;
Ext.form.VTypes["netmask"] = function(v)
{
    return Ext.form.VTypes["netmaskVal"].test(v);
}
Ext.form.VTypes["netmaskText"] = "128.0.0.0 - 255.255.255.252"
Ext.form.VTypes["netmaskMask"] = /[.0-9]/;
Ext.form.VTypes["port"] = function(v)
{
    return Ext.form.VTypes["portVal"].test(v);
}
Ext.form.VTypes["portText"] = "0 - 65535"
Ext.form.VTypes["portMask"] = /[0-9]/;
Ext.form.VTypes["multicast"] = function(v)
{
    return Ext.form.VTypes["multicastVal"].test(v);
}
Ext.form.VTypes["multicastText"] = "224.0.1.0 - 239.255.255.255"
Ext.form.VTypes["multicastMask"] = /[.0-9]/;
Ext.form.VTypes["username"] = function(v)
{
    return Ext.form.VTypes["usernameVal"].test(v);
}
Ext.form.VTypes["usernameText"] = "Username must begin with a letter and cannot exceed 255 characters"
Ext.form.VTypes["usernameMask"] = /[-_.a-zA-Z0-9]/;
Ext.form.VTypes["password"] = function(v)
{
    if (!Ext.form.VTypes["passwordVal1"].test(v))
    {
        Ext.form.VTypes["passwordText"] = "Password length must be 6 to 31 characters long";
        return false;
    }
    Ext.form.VTypes["passwordText"] = "Password must include atleast 2 numbers or symbols";
    return Ext.form.VTypes["passwordVal2"].test(v);
}
Ext.form.VTypes["passwordText"] = "Invalid Password"
Ext.form.VTypes["passwordMask"] = /./;
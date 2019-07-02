Dim message, sapi
Set sapi=CreateObject("sapi.spvoice")
sapi.Speak WScript.Arguments.Item(0)
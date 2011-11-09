set LOCAL_CLASSPATH=
for %%i in (*.jar) do call lcp.bat %%i
for %%i in (lib/*.jar) do call lcp.bat lib/%%i
java -jar watchmaker-examples-0.7.2.jar smartant
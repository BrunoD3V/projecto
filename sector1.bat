cls


							title sector 1
cd C:\Users\pedro\Documents\GitHub\projeto\projecto\NodeGest\dist

start java -jar NodeGest.jar 127.0.0.1 1112 1111 1

							title zone 1 sector 1
cd C:\Users\pedro\Documents\GitHub\projeto\projecto\Node\dist
start java -jar Node.jar 127.0.0.1  1113 1112 1
cd C:\Users\pedro\Documents\GitHub\projeto\projecto\Sensor\dist
start java -jar Sensor.jar 127.0.0.1 1113 Temp
start java -jar Sensor.jar 127.0.0.1 1113 Humi
start java -jar Sensor.jar 127.0.0.1 1113 Radi


							title zone 2 sector 1
cd C:\Users\pedro\Documents\GitHub\projeto\projecto\Node\dist
start java -jar Node.jar 127.0.0.1 1114 1112 2
cd C:\Users\pedro\Documents\GitHub\projeto\projecto\Sensor\dist
start java -jar Sensor.jar 127.0.0.1 1114 Temp
start java -jar Sensor.jar 127.0.0.1 1114 Humi
start java -jar Sensor.jar 127.0.0.1 1114 Radi